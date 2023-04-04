package com.xx.p.modules.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.xx.p.modules.admin.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
@Service
public class MinioServiceImpl implements MinioService {
    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @Value("${minio.tempPath}")
    private String tempPath;

    @Override
    public String upload(String imgUrl) {

        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient =MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();

            File imgFile = HttpUtil.downloadFileFromUrl(imgUrl, tempPath);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + imgFile.getName();

            // 使用putObject上传一个文件到存储桶中
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .stream(FileUtil.getInputStream(imgFile), imgFile.length(), -1).build();
            minioClient.putObject(putObjectArgs);
            String minioURL = ENDPOINT + "/" + BUCKET_NAME + "/" + objectName;
            log.info("文件上传成功, minioURL:{}!", minioURL);
            return minioURL;
        } catch (Exception e) {
            log.info("上传到minio文件出现异常",  e);
        }
        return null;
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient =MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + file.getOriginalFilename();

            // 使用putObject上传一个文件到存储桶中
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).build();
            minioClient.putObject(putObjectArgs);
            String minioURL = ENDPOINT + "/" + BUCKET_NAME + "/" + objectName;
            log.info("文件上传成功, minioURL:{}!", minioURL);
            return minioURL;
        } catch (Exception e) {
            log.info("上传到minio文件出现异常",  e);
        }
        return null;
    }
}
