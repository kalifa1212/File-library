package com.hforge.model;

import com.hforge.enums.VideoFormat;

import java.util.UUID;

public class VideoInfo {


    private UUID id;


    private UUID fileId;


    private Long duration;


    private Integer width;


    private Integer height;


    private Double fps;


    private Long bitrate;


    private String codec;


    private String audioCodec;


    private VideoFormat format;


}