package com.hforge.Services.impl;

import com.hforge.Services.VideoToolsService;
import com.hforge.config.ThumbnailOptions;
import com.hforge.config.VideoConversionOptions;
import com.hforge.config.VideoMetadata;
import com.hforge.exceptions.VideoProcessingException;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VideoToolsServiceImpl implements VideoToolsService {
    private final Encoder encoder;

    public VideoToolsServiceImpl() {
        this.encoder = new Encoder();
    }

    @Override
    public void generateThumbnail(
            Path sourceVideo,
            Path targetThumbnail,
            ThumbnailOptions options
    ) throws VideoProcessingException {

        validateSource(sourceVideo);

        try {
            createParentDirectories(targetThumbnail);

            File source = sourceVideo.toFile();
            File target = targetThumbnail.toFile();

            VideoAttributes video =
                    new VideoAttributes();

            video.setCodec(options.getCodec());

            video.setSize(
                    new VideoSize(
                            options.getWidth(),
                            options.getHeight()
                    )
            );

            video.setFrameRate(1);

            EncodingAttributes attributes =
                    new EncodingAttributes();

            attributes.setOutputFormat(
                    options.getFormat()
            );

            attributes.setOffset(
                    options.getTimestamp()
            );

            attributes.setDuration(
                    0.01f
            );

            attributes.setVideoAttributes(video);

            encoder.encode(
                    new MultimediaObject(source),
                    target,
                    attributes
            );

        } catch (EncoderException | IOException e) {

            throw new VideoProcessingException(
                    "Impossible de générer le thumbnail : "
                            + sourceVideo,
                    e
            );
        }
    }

    @Override
    public void convertToWebMp4(
            Path sourceVideo,
            Path targetMp4,
            VideoConversionOptions options
    ) throws VideoProcessingException {

        validateSource(sourceVideo);

        try {
            createParentDirectories(targetMp4);

            AudioAttributes audio =
                    createAudioAttributes(options);

            VideoAttributes video =
                    createVideoAttributes(options);

            EncodingAttributes attributes =
                    new EncodingAttributes();

            attributes.setOutputFormat("mp4");

            attributes.setAudioAttributes(audio);

            attributes.setVideoAttributes(video);

            attributes.setMapMetaData(true);

            encoder.encode(
                    new MultimediaObject(
                            sourceVideo.toFile()
                    ),
                    targetMp4.toFile(),
                    attributes
            );

        } catch (EncoderException | IOException e) {

            throw new VideoProcessingException(
                    "Impossible de convertir la vidéo : "
                            + sourceVideo,
                    e
            );
        }
    }

    @Override
    public VideoMetadata getMetadata(
            Path sourceVideo
    ) throws VideoProcessingException {

        validateSource(sourceVideo);

        try {

            MultimediaObject multimediaObject =
                    new MultimediaObject(
                            sourceVideo.toFile()
                    );

            var info =
                    multimediaObject.getInfo();

            VideoMetadata metadata =
                    new VideoMetadata();

            metadata.setDuration(
                    info.getDuration()
            );

            if (info.getVideo() != null) {

                metadata.setWidth(
                        info.getVideo().getSize().getWidth()
                );

                metadata.setHeight(
                        info.getVideo().getSize().getHeight()
                );

                metadata.setFrameRate(
                        info.getVideo().getFrameRate()
                );

                metadata.setCodec(
                        info.getVideo().getDecoder()
                );
            }

            return metadata;

        } catch (Exception e) {

            throw new VideoProcessingException(
                    "Impossible de lire les métadonnées vidéo",
                    e
            );
        }
    }

    @Override
    public boolean isValidVideo(Path sourceVideo) {
        return false;
    }

    @Override
    public void extractPreviewFrames(Path sourceVideo, Path outputDirectory, int numberOfFrames) throws VideoProcessingException {

    }

    private AudioAttributes createAudioAttributes(
            VideoConversionOptions options
    ) {

        AudioAttributes audio =
                new AudioAttributes();

        audio.setCodec("aac");

        audio.setBitRate(
                options.getAudioBitRate()
        );

        audio.setChannels(
                options.getAudioChannels()
        );

        audio.setSamplingRate(
                options.getAudioSamplingRate()
        );

        return audio;
    }

    private VideoAttributes createVideoAttributes(
            VideoConversionOptions options
    ) {

        VideoAttributes video =
                new VideoAttributes();

        video.setCodec("libx264");

        video.setBitRate(
                options.getVideoBitRate()
        );

        video.setFrameRate(
                options.getFrameRate()
        );

        video.setSize(
                new VideoSize(
                        options.getWidth(),
                        options.getHeight()
                )
        );

        return video;
    }

    private void validateSource(
            Path source
    ) throws VideoProcessingException {

        if (source == null) {
            throw new VideoProcessingException(
                    "Le fichier source est null"
            );
        }

        if (!Files.exists(source)) {
            throw new VideoProcessingException(
                    "Le fichier source n'existe pas : "
                            + source
            );
        }

        if (!Files.isRegularFile(source)) {
            throw new VideoProcessingException(
                    "Le chemin source n'est pas un fichier : "
                            + source
            );
        }

        if (!Files.isReadable(source)) {
            throw new VideoProcessingException(
                    "Le fichier source n'est pas lisible : "
                            + source
            );
        }
    }

    private void createParentDirectories(
            Path target
    ) throws IOException {

        Path parent =
                target.toAbsolutePath()
                        .getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
