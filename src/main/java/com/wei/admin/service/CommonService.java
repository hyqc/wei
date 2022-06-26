package com.wei.admin.service;

import com.wei.core.aop.LogExecutionTime;
import com.wei.core.config.StoreConfig;
import com.wei.core.config.UploadConfig;
import com.wei.core.exception.ParamException;
import com.wei.admin.dto.CommonUploadFileParam;
import com.wei.admin.dto.UploadFileTypeEnum;
import com.wei.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Administrator
 */
@Slf4j
@Service
public class CommonService {

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private StoreConfig storeConfig;

    @LogExecutionTime
    public Map<String, Object> upload(CommonUploadFileParam commonUploadFileParam, MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new ParamException("请选择上传文件");
        }
        if (UploadFileTypeEnum.getByCode(commonUploadFileParam.getFileType()) == UploadFileTypeEnum.IMAGE) {
            return uploadImage(multipartFile);
        }
        throw new ParamException("上传文件类型错误");
    }

    protected Map<String, Object> uploadImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ParamException("请选择图片");
        }
        Long objectSize = uploadConfig.getAvatar().getMaxSize();

        String errFileSizeMsg = "图片大小不能超过" + FileUtil.convertBitSizeToString(objectSize);
        if (multipartFile.getSize() > objectSize) {
            throw new ParamException(errFileSizeMsg);
        }
        BufferedImage bi = null;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            bi = ImageIO.read(inputStream);
        } catch (IOException exception) {
            throw new ParamException("上传图片失败");
        }
        if (bi == null) {
            throw new ParamException("请选择图片");
        }
        String oldFileName = multipartFile.getOriginalFilename();
        String url = storeConfig.fileUploader(uploadConfig.getAvatar(), multipartFile);
        Map<String, Object> data = new HashMap<>();
        data.put("url", url);
        return data;
    }
}
