package com.hforge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "h-storage")
public class StorageProperties {
    private String rootPath = "./uploads";

    private boolean createDirectories = true;

    private boolean overwriteExisting = false;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public boolean isCreateDirectories() {
        return createDirectories;
    }

    public void setCreateDirectories(boolean createDirectories) {
        this.createDirectories = createDirectories;
    }

    public boolean isOverwriteExisting() {
        return overwriteExisting;
    }

    public void setOverwriteExisting(boolean overwriteExisting) {
        this.overwriteExisting = overwriteExisting;
    }
}
