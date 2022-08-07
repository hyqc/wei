package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Data
public class CommonUploadFileParam {

    @NotNull(message = "上传文件类型无效")
    @Range(min = 1, max = 1, message = "上传文件类型无效")
    @ApiParam(value = "上传类型", required = true)
    private Integer fileType;
}
