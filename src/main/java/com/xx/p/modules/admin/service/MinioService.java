package com.xx.p.modules.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    String upload(String url);

    String upload(MultipartFile url);
}
