package com.example.springbootsample.service;

import com.example.springbootsample.config.StorageConfig;
import com.example.springbootsample.dto.FileStorageResponse;
import com.example.springbootsample.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class FileStorageService {

    private static final Logger LOG = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    StorageConfig storageConfig;

    public FileStorageResponse save(final MultipartFile multipartFile) throws IOException {

        FileStorageResponse response = new FileStorageResponse();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String filecode = FileUploadUtil.saveFile(fileName, multipartFile, storageConfig.getStoragePath());

        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);

        return response;
    }
}
