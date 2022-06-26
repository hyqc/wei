package com.wei.core.config;

import com.wei.core.exception.BusinessException;
import com.wei.core.exception.ParamException;
import io.jsonwebtoken.lang.Strings;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author Administrator
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "store")
public class StoreConfig {

    /**
     * 对象存储服务的url
     */
    private String endpoint;
    /**
     * key
     */
    private String accessKey;
    /**
     * 秘钥
     */
    private String secretKey;
    /**
     * 路径名称
     */
    private String bucket;
    /**
     * 节点
     */
    private String region;

    /**
     * 存储地址，路径名称之后
     */
    private String path;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String getStorePath() {
        return Strings.replace(path + "/" + LocalDateTime.now().format(dateTimeFormatter) + "/", "//", "/");
    }

    public String setStoreFilename(String filename) {
        String name = String.valueOf(Instant.now().getEpochSecond());
        String fileSuffix = filename.substring(filename.lastIndexOf("."));
        return Arrays.toString(DigestUtils.md5Digest((UUID.randomUUID() + name).getBytes(StandardCharsets.UTF_8))) + fileSuffix;
    }

    /**
     * @param file 接收的上传文件
     */
    public String fileUploader(UploadConfig.UploadFieldItem uploadConfig, MultipartFile file) {
        try {
            MinioClient client = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).region(region).build();
            // 检查存储桶是否存在
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucket).build();
            if (!client.bucketExists(bucketExistsArgs)) {
                throw new ParamException("存储桶不存在");
            }
            String filename = file.getOriginalFilename();
            // 设置存储对象名称
            assert filename != null;
            String objectName = Strings.replace(getStorePath() + setStoreFilename(filename), "//", "/");
            ObjectWriteResponse res = client.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE)
                            .contentType(file.getContentType())
                            .build()
            );
            String url = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .region(region)
                    .expiry(uploadConfig.getExpire())
                    .method(Method.GET)
                    .build());
            return url;
        } catch (IOException | NoSuchAlgorithmException | XmlParserException | ServerException | InsufficientDataException | InvalidKeyException | InvalidResponseException | ErrorResponseException | InternalException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 通过URL获取可访问的URL地址
     *
     * @param uploadConfig
     * @param url
     * @return
     */
    public String getPresignedObjectUrl(UploadConfig.UploadFieldItem uploadConfig, String url) {
        try {
            url = url == null ? "" : url;
            if ("".equals(url)) {
                return "";
            }
            URL oldLink = new URL(url);
            MinioClient client = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).region(region).build();
            String objectName = oldLink.getPath().substring(1).replaceFirst(bucket + "/", "");
            return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .region(region)
                    .expiry(uploadConfig.getExpire())
                    .method(Method.GET)
                    .build());
        } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException e) {
            log.error(e.getMessage());
            return "";
        }
    }
}
