package com.hforge.dto;

import lombok.Data;

import java.util.Map;

@Data
public class FileMetadataDto {


    private Integer width;


    private Integer height;


    private Long duration;


    private Integer pages;


    private String codec;


    private Long bitrate;


    private String author;


    private String title;


    private String language;


    private Map<String,Object> customMetadata;


}