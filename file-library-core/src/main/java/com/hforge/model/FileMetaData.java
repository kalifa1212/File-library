package com.hforge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileMetaData {
    private String originalName;
    private String storedName;
    private String extension;
    private String mimeType;
    private Long size;
    private String path;
    private String url;
}
