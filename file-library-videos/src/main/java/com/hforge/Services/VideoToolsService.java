package com.hforge.Services;

import com.hforge.config.ThumbnailOptions;
import com.hforge.config.VideoConversionOptions;
import com.hforge.config.VideoMetadata;
import com.hforge.exceptions.VideoProcessingException;

import java.nio.file.Path;

public interface VideoToolsService {
    void generateThumbnail(
            Path sourceVideo,
            Path targetThumbnail,
            ThumbnailOptions options
    ) throws VideoProcessingException;

    void convertToWebMp4(
            Path sourceVideo,
            Path targetMp4,
            VideoConversionOptions options
    ) throws VideoProcessingException;

    VideoMetadata getMetadata(
            Path sourceVideo
    ) throws VideoProcessingException;

    boolean isValidVideo(
            Path sourceVideo
    );

    void extractPreviewFrames(
            Path sourceVideo,
            Path outputDirectory,
            int numberOfFrames
    ) throws VideoProcessingException;
}
