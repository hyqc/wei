package com.wei.admin.service;

import com.wei.admin.common.UserCommon;
import com.wei.admin.common.UserDetails;
import com.wei.admin.dao.mysql.AdminMenuDao;
import com.wei.admin.po.AdminRolePo;
import com.wei.admin.po.AdminUserPo;
import com.wei.common.ErrorCode;
import com.wei.common.Result;
import com.wei.core.aop.LogExecutionTime;
import com.wei.core.config.StoreConfig;
import com.wei.core.config.UploadConfig;
import com.wei.core.exception.AuthException;
import com.wei.core.exception.ParamException;
import com.wei.admin.bo.*;
import com.wei.admin.constant.CommonConstant;
import com.wei.admin.dao.mysql.AdminPermissionDao;
import com.wei.admin.dao.mysql.AdminRoleDao;
import com.wei.admin.dao.mysql.AdminUserDao;
import com.wei.admin.dto.*;
import com.wei.admin.bo.LoginInfo;
import com.wei.admin.po.AdminMenuPo;
import com.wei.admin.po.AdminPermissionPo;
import com.wei.common.Pager;
import com.wei.common.CodeEnum;
import com.wei.security.JwtTokenUtil;
import com.wei.util.CommonUtil;
import com.wei.util.TreeUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private AdminMenuDao adminMenuDao;

    @Autowired
    private AdminPermissionDao adminPermissionDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private StoreConfig storeConfig;

    /**
     * 获取用户信息
     *
     * @param adminUsername
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String adminUsername) throws UsernameNotFoundException {
        AdminUserPo adminUserPo = adminUserDao.findAdminUserInfoByUsername(adminUsername);
        ErrorCode res = UserCommon.checkAccountValidByAccount(adminUserPo);
        if (!CodeEnum.SUCCESS.equals(res)) {
            throw new AuthException(CodeEnum.UNAUTHORIZED.getMessage());
        }
        return new UserDetails(adminUserPo, this);
    }

    /**
     * 获取权限对应的API接口地址
     *
     * @param adminId
     * @return
     */
    public Collection<? extends GrantedAuthority> getAccountPermissionApiKey(Integer adminId) {
        List<AdminPermissionPo> adminPermissionPoList = getAccountPermission(adminId, null);
        // 获取权限ID
        List<Integer> permissionsId = adminPermissionPoList.stream().map(AdminPermissionPo::getId).distinct().collect(Collectors.toList());
        List<String> apiKeys = new ArrayList<>();
        if (permissionsId.size() > 0) {
            apiKeys = adminPermissionDao.selectAllAccessApiKeys(permissionsId);
        }
        return apiKeys == null || apiKeys.size() == 0 ? new ArrayList<>() : apiKeys.stream().map(SimpleGrantedAuthority::new).distinct().collect(Collectors.toList());
    }

    /**
     * 获取权限信息
     *
     * @param adminId
     * @param menuId
     * @return
     */
    private List<AdminPermissionPo> getAccountPermission(Integer adminId, Integer menuId) {
        List<AdminPermissionPo> adminPermissionPoList;
        if (CommonConstant.isAdministrator(adminId)) {
            // 超管
            adminPermissionPoList = adminPermissionDao.selectPermissionsByAdminister();
        } else {
            // 普通用户
            adminPermissionPoList = adminPermissionDao.selectPermissions(adminId, menuId);
        }
        return adminPermissionPoList;
    }

    /**
     * 获取当前登录用户指定菜单下的权限，菜单menuId未指定则获取该用户全部的权限，指定了则获取指定菜单下的权限
     *
     * @param menuId
     * @return
     */
    private List<AccountPermissionItem> getMyPermissions(Integer menuId) {
        UserDetails userDetails = getAdminUserDetails();
        AdminUserPo adminUserPo = userDetails.getAdminUsersPo();
        Integer adminId = adminUserPo.getId();
        List<AdminPermissionPo> adminPermissionPoList = getAccountPermission(adminId, menuId);

        return adminPermissionPoList.stream().map(adminPermissionPo -> {
            AccountPermissionItem accountPermissionItem = new AccountPermissionItem();
            accountPermissionItem.setAccountPermissionItem(adminPermissionPo);
            return accountPermissionItem;
        }).collect(Collectors.toList());
    }

    @LogExecutionTime
    public Result<List<AccountPermissionItem>> getAccountPermission(AccountPermissionParams params) {
        return Result.success(getMyPermissions(params.getMenuId()));
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    @LogExecutionTime
    public static UserDetails getAdminUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private String handleLastLoginIp(String oldIp, String ip) {
        String[] ips = oldIp.split(",");
        return ips.length == 0 ? ip : ips[ips.length - 1] + "," + ip;
    }

    private boolean checkLoginPassword(String loginPassword, String encodePassword) {
        return passwordEncoder.matches(loginPassword, encodePassword);
    }

    @LogExecutionTime
    public Result login(AccountLoginParams params, HttpServletRequest request) {
        AdminUserPo adminUserPo = adminUserDao.findAdminUserInfoByUsername(params.getUsername());
        if (adminUserPo == null) {
            return Result.accountOrPasswordError();
        }
        if (!checkLoginPassword(params.getPassword(), adminUserPo.getPassword())) {
            return Result.accountOrPasswordError();
        }
        ErrorCode res = UserCommon.checkAccountValidByAccount(adminUserPo);
        if (!CodeEnum.SUCCESS.equals(res)) {
            return Result.failed(res);
        }
        UserDetails userDetails = new UserDetails(adminUserPo, this);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String ip = CommonUtil.getClientIp(request);
        ip = handleLastLoginIp(adminUserPo.getLastLoginIp(), ip);
        try {
            // 更新最后登录时间，IP，登录总次数
            adminUserDao.updateLastLoginInfo(params.getUsername(), new LoginInfo(LocalDateTime.now(), ip));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("登录失败");
        }

        AccountInfoItem data = getMyInfo(true);
        return Result.success(data);
    }

    @LogExecutionTime
    public Result register(AccountRegisterParams params) {
        if (!params.confirmPasswordEqualPassword()) {
            return Result.confirmPasswordFailed();
        }
        LocalDateTime date = LocalDateTime.now();
        AdminUserPo adminUserPo = new AdminUserPo();
        adminUserPo.setUsername(params.getUsername());
        adminUserPo.setNickname(params.getUsername());
        adminUserPo.setPassword(passwordEncoder.encode(params.getPassword()));
        adminUserPo.setCreateTime(date);
        adminUserPo.setModifyTime(date);
        try {
            adminUserDao.registerAdminUser(adminUserPo);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            if (Objects.requireNonNull(e.getMessage()).contains("uk_username")) {
                return Result.accountExisted();
            } else {
                return Result.failed("注册失败");
            }
        }
        return Result.success("注册成功");
    }

    private AccountInfoItem getMyInfo(boolean refreshToken) {
        UserDetails userDetails = getAdminUserDetails();
        AdminUserPo adminUserPo = userDetails.getAdminUsersPo();

        AccountInfoItem accountInfoItem = new AccountInfoItem();
        accountInfoItem.setAccountInfoItem(adminUserPo);
        // 最后登录地址
        String[] ips = accountInfoItem.getLastLoginIp().split(",");
        accountInfoItem.setLastLoginIp(ips.length == 0 ? "" : ips[0]);
        // 头像
        UploadConfig.UploadFieldItem uploadFieldItem = uploadConfig.getAvatar();
        accountInfoItem.setAvatar(storeConfig.getPresignedObjectUrl(uploadFieldItem, accountInfoItem.getAvatar()));
        List<MenuItem> menuItems = getMyMenus();
        Map<String, MenuItem> menus = menuItems.stream().collect(Collectors.toMap(AdminMenuPo::getKey, item -> item));
        accountInfoItem.setMenus(menus);
        List<AccountPermissionItem> accountPermissionItems = getMyPermissions(0);
        Map<String, String> permissions = accountPermissionItems.stream().collect(Collectors.toMap(AccountPermissionItem::getKey, AccountPermissionItem::getName));
        accountInfoItem.setPermissions(permissions);
        if (refreshToken) {
            String token = jwtTokenUtil.generateToken(userDetails);
            accountInfoItem.setToken(token);
            accountInfoItem.setExpire(jwtTokenUtil.getExpiration());
        }
        return accountInfoItem;
    }

    @LogExecutionTime
    public Result<AccountInfoItem> getAccountDetail(AccountDetailParams params) {
        return Result.success(getMyInfo(params.getRefreshToken()));
    }

    @LogExecutionTime
    public Result editAccountDetail(AccountEditParams params) {
        if (params.getNickname() == null && params.getAvatar() == null) {
            return Result.success("没有任何更新");
        }
        UserDetails userDetails = getAdminUserDetails();
        AdminUserPo adminUserPo = userDetails.getAdminUsersPo();

        adminUserPo.setNickname(params.getNickname());
        adminUserPo.setAvatar(params.getAvatar());
        adminUserPo.setEmail(params.getEmail());
        adminUserPo.setModifyTime(LocalDateTime.now());
        adminUserDao.updateAccountInfo(adminUserPo);
        return Result.success("保存成功");
    }

    @LogExecutionTime
    public Result updateAccountPassword(AccountPasswordEditParams params) {
        if (params.checkUpdatePassword()) {
            return Result.passwordNotUpdate();
        }
        if (!params.confirmPasswordEqualPassword()) {
            return Result.confirmPasswordFailed();
        }
        AdminUserPo adminUserPo = getAdminUserDetails().getAdminUsersPo();
        if (!passwordEncoder.matches(params.getOldPassword(), adminUserPo.getPassword())) {
            return Result.passwordError();
        }
        adminUserDao.updatePassword(adminUserPo.getId(), passwordEncoder.encode(params.getPassword()));
        return Result.success("修改密码成功");
    }

    /**
     * 获取登录账号可以访问的菜单列表
     *
     * @return
     */
    private List<MenuItem> getMyMenus() {
        UserDetails userDetails = getAdminUserDetails();
        AdminUserPo adminUserPo = userDetails.getAdminUsersPo();
        Integer adminId = adminUserPo.getId();
        Set<Integer> menuIds = new HashSet<>();
        List<AdminPermissionPo> adminPermissionPoList = getAccountPermission(adminId, null);
        menuIds = adminPermissionPoList.stream().map(AdminPermissionPo::getMenuId).collect(Collectors.toSet());
        // 权限对应的页面ID
        List<MenuItem> allMenus = adminMenuDao.selectAllValidMenus();
        Map<Integer, MenuItem> allMenusMap = allMenus.stream().collect(Collectors.toMap(AdminMenuPo::getId, item -> item));
        List<MenuItem> result = new ArrayList<>();
        Map<Integer, MenuItem> resultMap = new HashMap<>();
        TreeUtil.getAllRelatedMenusByPageIds(result, allMenusMap, new ArrayList<>(menuIds)).forEach(item->{
            if(!resultMap.containsKey(item.getId())){
                resultMap.put(item.getId(),item);
            }
        });
        return new ArrayList<>(resultMap.values());
    }

    @LogExecutionTime
    public Result<List<MenuItem>> getAccountMenus() {
        return Result.success(getMyMenus());
    }

    protected String getLastLoginIp(String ip) {
        if (ip != null && ip.length() > 0) {
            String[] ips = ip.split(",");
            return ips[ips.length - 1];
        }
        return "";
    }

    @LogExecutionTime
    public Result<Pager> selectAdminUserList(UserListParams params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<UserListItem> adminUserPoList = adminUserDao.selectAdminUserList(params);
        Pager result = Pager.restPage(adminUserPoList);
        // 获得用户ID
        Set<Integer> adminIds = adminUserPoList.stream().map(UserListItem::getAdminId).collect(Collectors.toSet());
        // 获取角色信息
        Map<Integer, List<UserRoleItem>> adminRolePoMap = new HashMap<>();
        if (adminIds.size() > 0) {
            List<UserRoleItem> adminRolePos = adminRoleDao.selectAdminUserRolesByAdminIds(adminIds);
            adminRolePoMap = adminRolePos.stream().collect(Collectors.groupingBy(UserRoleItem::getAdminId));
        }

        UploadConfig.UploadFieldItem uploadFieldItem = uploadConfig.getAvatar();
        Map<Integer, List<UserRoleItem>> finalAdminRolePoMap = adminRolePoMap;
        adminUserPoList.forEach(prop -> {
            prop.setAvatar(storeConfig.getPresignedObjectUrl(uploadFieldItem, prop.getAvatar()));
            prop.setRoles(finalAdminRolePoMap.containsKey(prop.getAdminId()) ? finalAdminRolePoMap.get(prop.getAdminId()) : new ArrayList<>());
        });
        result.setRows(adminUserPoList);
        return Result.success(result);
    }

    @LogExecutionTime
    public Result getAdminUserDetail(UserDetailParams params) {
        AdminUserPo adminUserPo = adminUserDao.findAdminUserDetailByAdminId(params.getAdminId());
        UserListItem userListItem = new UserListItem();
        if (adminUserPo != null) {
            List<UserRoleItem> adminRolePos = adminRoleDao.selectAdminUserRolesByAdminIds(new HashSet<Integer>() {{
                add(adminUserPo.getId());
            }});
            BeanUtils.copyProperties(adminUserPo, userListItem);
            userListItem.setRoles(adminRolePos);
            userListItem.setAdminId(adminUserPo.getId());
            userListItem.setLastLoginIp(getLastLoginIp(adminUserPo.getLastLoginIp()));
            UploadConfig.UploadFieldItem uploadFieldItem = uploadConfig.getAvatar();
            userListItem.setAvatar(storeConfig.getPresignedObjectUrl(uploadFieldItem, adminUserPo.getAvatar()));
        } else {
            throw new ParamException("账号不存在或已删除");
        }
        return Result.success(userListItem);
    }

    @LogExecutionTime
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result addAdminUser(UserAddParams params) {
        // 创建账号
        AdminUserPo adminUserPo = new AdminUserPo();
        adminUserPo.setUsername(params.getUsername());
        adminUserPo.setNickname(params.getNickname() == null ? params.getUsername() : params.getNickname());
        adminUserPo.setEnabled(params.getEnabled());
        adminUserPo.setEmail(params.getEmail() == null ? "" : params.getEmail());
        adminUserPo.setAvatar(params.getAvatar() == null ? "" : params.getAvatar());
        adminUserPo.setCreateTime(LocalDateTime.now());
        adminUserPo.setModifyTime(adminUserPo.getCreateTime());
        adminUserPo.setPassword(passwordEncoder.encode(params.getPassword()));

        try {
            adminUserDao.addAdminUser(adminUserPo);
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new ParamException("账号已存在");
        }
        return Result.success("创建用户成功");
    }

    @LogExecutionTime
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result editAdminUser(UserEditParams params) {
        // 编辑用户信息
        AdminUserPo adminUserPo = new AdminUserPo();
        adminUserPo.setId(params.getAdminId());
        boolean updateFlag = false;
        if (params.getUsername() != null && params.getUsername().length() > 0) {
            adminUserPo.setUsername(params.getUsername());
            updateFlag = true;
        }
        if (params.getNickname() != null) {
            adminUserPo.setNickname(params.getNickname());
            updateFlag = true;
        }
        if (params.getEnabled() != null) {
            adminUserPo.setEnabled(params.getEnabled());
            updateFlag = true;
        }
        if (params.getPassword() != null) {
            adminUserPo.setPassword(passwordEncoder.encode(params.getPassword()));
            updateFlag = true;
        }
        if (params.getEmail() != null) {
            adminUserPo.setEmail(params.getEmail());
            updateFlag = true;
        }
        if (params.getAvatar() != null) {
            adminUserPo.setAvatar(params.getAvatar());
            updateFlag = true;
        }
        if (updateFlag) {
            adminUserPo.setModifyTime(LocalDateTime.now());
            adminUserDao.editAdminUser(adminUserPo);
            return Result.success("更新用户成功");
        }
        return Result.success("没有任何更新");
    }

    @LogExecutionTime
    public Result enableAdminUser(UserUpdateEnabledParams params) {
        adminUserDao.updateAdminUserIsEnabled(params.getAdminId(), params.getEnabled());
        String msg = params.getEnabled() ? "启用成功" : "禁用成功";
        return Result.success(msg);
    }

    @LogExecutionTime
    public Result bindAdminRoles(UserBindRolesParams params) {
        List<Integer> roleIds = params.getRoleIds().stream().filter(id -> id > 0).distinct().collect(Collectors.toList());
        if (roleIds.size() == 0) {
            return Result.failed("没有有效的角色");
        }
        AdminUserPo adminUserPo = adminUserDao.findAdminUserDetailByAdminId(params.getAdminId());
        if (adminUserPo == null || adminUserPo.getId() < 1) {
            return Result.failed("账号不存在或已被删除");
        }
        if (!adminUserPo.getEnabled()) {
            return Result.failed("账号已被禁用");
        }
        adminUserDao.addAdminUserRoles(params.getAdminId(), params.getRoleIds());
        return Result.success();
    }

    @LogExecutionTime
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result deleteAdminUser(UserDeleteParams params) {
        AdminUserPo po = adminUserDao.findAdminUserDetailByAdminId(params.getAdminId());
        if (po == null) {
            return Result.failed("账号不存在或已被删除");
        }
        if (po.getEnabled()) {
            return Result.failed("启用状态的账号不能删除");
        }
        adminUserDao.deleteAdminUser(params.getAdminId());
        adminUserDao.deleteAdminUserRoles(params.getAdminId());
        return Result.success("删除账号成功");
    }
}
