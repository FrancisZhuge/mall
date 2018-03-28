package com.netease.mall.service.impl;

import com.netease.mall.service.FileUploadService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/29,0:26
 * @update :
 */
@Service
public class FileUploadServiceImpl implements FileUploadService{
    //文件存储路径
    @Value("${img.local.path}")
    private String imgLocalPath;
    //文件网络访问路径
    @Value("${img.host}")
    private String imgHost;


    @Override
    public String save(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String contentType = file.getContentType();
        String suffix = contentType.substring(contentType.indexOf("/") + 1);
        String fileName = uuid + "."+suffix;
        String uploadDirName = imgLocalPath.substring(imgLocalPath.lastIndexOf("/"), imgLocalPath.length());
        FileCopyUtils.copy(file.getBytes(), new File(imgLocalPath + "/", fileName));
        return imgHost + uploadDirName + "/" + fileName;
    }
}
