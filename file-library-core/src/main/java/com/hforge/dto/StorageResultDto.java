package com.hforge.dto;

import com.hforge.enums.StorageType;
import lombok.Data;

@Data
public class StorageResultDto {


    private Boolean success;


    private StorageType storageType;


    private String path;


    private String url;


    private String checksum;


    private Long size;


    private String message;


}