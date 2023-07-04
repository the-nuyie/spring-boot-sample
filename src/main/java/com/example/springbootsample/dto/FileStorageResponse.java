package com.example.springbootsample.dto;

public class FileStorageResponse {
    private String fileName;
    private String downloadUri;
    private long size;

    // getters and setters are not shown for brevity

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}