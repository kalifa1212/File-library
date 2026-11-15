package com.hforge.services.impl;

import com.hforge.services.FfmpegService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FfmpegServiceImpl implements FfmpegService {
    @Override
    public void generateHls(Path videoPath, Path absolutePath) throws IOException, InterruptedException {
        Path hlsDirectory = videoPath.getParent().resolve("hls");
        Path absoluteParentPath =absolutePath.resolve(hlsDirectory);
        Path absoluteVideoPath =absolutePath.resolve(videoPath);
        Files.createDirectories(absoluteParentPath);
//        String command = String.format(
//                "ffmpeg -i %s -c:v libx264 -c:a aac -g 48 -keyint_min 48 -sc_threshold 0 -hls_time 2 -hls_list_size 0 -f hls %s/index.m3u8",
//                absoluteVideoPath.toAbsolutePath(),
//                absoluteParentPath.toAbsolutePath()
//        );

        // Execute the FFmpeg command
//        ProcessBuilder pb= new ProcessBuilder("bash","-c", ffmpegCmd);
//        pb.redirectErrorStream(true);
       // pb.inheritIO().start().waitFor();


        List<String> command = List.of(
                "ffmpeg",
                "-i",
                absoluteVideoPath.toString(),
                "-c:v",
                "libx264",
                "-c:a",
                "aac",
                "-hls_time",
                "6",
                "-hls_playlist_type",
                "vod",
                "-hls_segment_filename",
                absoluteParentPath.resolve("segment%03d.ts").toString(),
                absoluteParentPath.resolve("playlist.m3u8").toString()
        );
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {

                System.out.println(line);

            }

        }
        int exitCode = process.waitFor(); //TODO if equal to zero is done
        Path playlist =hlsDirectory.resolve("playlist.m3u8");
        Files.exists(playlist);//deuxieme verification
    }
}
