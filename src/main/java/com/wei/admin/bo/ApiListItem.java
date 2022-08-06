package com.wei.admin.bo;

import com.wei.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Data
public class ApiListItem {
    private Integer permissionId;
    private Integer id;
    private String path;
    private String key;
    private String name;
    private Boolean enabled;
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createTime;
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyTime;
}
