package com.netease.mall.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/29,0:26
 * @update :
 */
public interface FileUploadService {

    String save(MultipartFile file) throws IOException;
}
