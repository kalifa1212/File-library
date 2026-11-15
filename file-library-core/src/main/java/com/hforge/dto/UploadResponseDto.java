package com.hforge.dto;

import com.hforge.enums.StorageType;
import lombok.Data;

import java.time.Instant;

@Data
public class UploadResponseDto {


    private Boolean success;


    private String message;


    private String fileId;


    private String storedName;


    private String url;


    private StorageType storageType;


    private Long size;


    private String checksum;


    private Instant uploadedAt;


}