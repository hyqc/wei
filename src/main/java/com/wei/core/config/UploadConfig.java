package com.wei.core.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {

    /**
     * 头像，Logo类上传
     */
    private UploadFieldItem avatar;

    @Getter
    @Setter
    public final static class UploadFieldItem {
        /**
         * 存储前缀，拼接在basePath后
         */
        private String baseDir;
        /**
         * 上传的文件的最大大小
         */
        private Long maxSize;
        /**
         * 过期时间秒
         */
        private Integer expire;
    }
}
