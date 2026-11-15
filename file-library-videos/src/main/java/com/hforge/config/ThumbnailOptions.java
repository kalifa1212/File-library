package com.hforge.config;

import lombok.Data;


public final class ThumbnailOptions {

    private final int width;
    private final int height;
    private final float timestamp;
    private final String format;
    private final String codec;

    public ThumbnailOptions(
            int width,
            int height,
            float timestamp,
            String format,
            String codec
    ) {
        if (width <= 0) {
            throw new IllegalArgumentException(
                    "La largeur doit être supérieure à 0"
            );
        }

        if (height <= 0) {
            throw new IllegalArgumentException(
                    "La hauteur doit être supérieure à 0"
            );
        }

        if (timestamp < 0) {
            throw new IllegalArgumentException(
                    "Le timestamp ne peut pas être négatif"
            );
        }

        this.width = width;
        this.height = height;
        this.timestamp = timestamp;
        this.format = format;
        this.codec = codec;
    }

    public static ThumbnailOptions webp() {
        return new ThumbnailOptions(
                640,
                360,
                2.0f,
                "webp",
                "libwebp"
        );
    }

    public static ThumbnailOptions jpeg() {
        return new ThumbnailOptions(
                640,
                360,
                2.0f,
                "mjpeg",
                "mjpeg"
        );
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public String getFormat() {
        return format;
    }

    public String getCodec() {
        return codec;
    }
}