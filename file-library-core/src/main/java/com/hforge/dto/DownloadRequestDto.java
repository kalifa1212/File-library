package com.hforge.dto;

import lombok.Data;

@Data
public class DownloadRequestDto {


    private String fileId;


    private String downloadName;


    private Boolean inline;


    private Integer version;


}