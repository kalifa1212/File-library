package com.hforge.config;

public final class VideoConversionOptions {

    private final int width;
    private final int height;
    private final int videoBitRate;
    private final int audioBitRate;
    private final int audioChannels;
    private final int audioSamplingRate;
    private final int frameRate;

    public VideoConversionOptions(
            int width,
            int height,
            int videoBitRate,
            int audioBitRate,
            int audioChannels,
            int audioSamplingRate,
            int frameRate
    ) {
        this.width = width;
        this.height = height;
        this.videoBitRate = videoBitRate;
        this.audioBitRate = audioBitRate;
        this.audioChannels = audioChannels;
        this.audioSamplingRate = audioSamplingRate;
        this.frameRate = frameRate;
    }

    public static VideoConversionOptions web720p() {
        return new VideoConversionOptions(
                1280,
                720,
                1_500_000,
                128_000,
                2,
                44_100,
                30
        );
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVideoBitRate() {
        return videoBitRate;
    }

    public int getAudioBitRate() {
        return audioBitRate;
    }

    public int getAudioChannels() {
        return audioChannels;
    }

    public int getAudioSamplingRate() {
        return audioSamplingRate;
    }

    public int getFrameRate() {
        return frameRate;
    }
}
