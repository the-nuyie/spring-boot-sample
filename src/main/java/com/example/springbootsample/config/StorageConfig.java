package com.example.springbootsample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    Logger logger = LoggerFactory.getLogger(StorageConfig.class);

    @Value("${storage.mode}")
    private String storageMode;

    @Value("${storage.path}")
    private String storagePath;


    public String getStorageMode() {
        return storageMode;
    }
    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
    }

    public String getStoragePath() {
        return storagePath;
    }
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
