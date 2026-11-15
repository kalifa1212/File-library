package com.hforge.dto;

import lombok.Data;

import java.io.InputStream;
import java.util.Map;

@Data
public class UploadRequestDto {

    private String originalFileName;

    private String contentType;

    private InputStream inputStream;

    private String path;

    private Boolean overwrite;



}