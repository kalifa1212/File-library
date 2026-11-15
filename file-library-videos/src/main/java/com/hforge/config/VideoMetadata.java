package com.hforge.config;

import lombok.Data;

@Data
public class VideoMetadata {

    private long duration;

    private int width;

    private int height;

    private float frameRate;

    private String codec;

    // getters/setters
}