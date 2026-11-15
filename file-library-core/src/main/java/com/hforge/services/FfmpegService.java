package com.hforge.services;

import java.io.IOException;
import java.nio.file.Path;

public interface FfmpegService {
    void generateHls(Path videoDirectory,Path absolutePath) throws IOException, InterruptedException;
}
