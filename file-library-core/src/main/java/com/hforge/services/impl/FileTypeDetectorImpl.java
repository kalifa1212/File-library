package com.hforge.services.impl;

import com.hforge.enums.FileType;
import com.hforge.services.FileTypeDetector;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileTypeDetectorImpl implements FileTypeDetector {

    private final Tika tika = new Tika();

    @Override
    public FileType detect(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return FileType.UNKNOWN;
        }

        String mimeType;

        try (InputStream inputStream = file.getInputStream()) {
            // 1. Détection robuste via la signature binaire (magic bytes) + nom
            mimeType = tika.detect(inputStream, file.getOriginalFilename());
        } catch (IOException e) {
            // 2. Fallback sur le Content-Type envoyé par le client si le flux échoue
            mimeType = file.getContentType();
        }

        if (mimeType == null) {
            return FileType.UNKNOWN;
        }

        // 3. Mapping souple basé sur les préfixes / familles de MIME types
        return resolveFileType(mimeType.toLowerCase());
    }

    @Override
    public String mimeType(MultipartFile file) throws IOException {
        return tika.detect(file.getInputStream());
    }

    private FileType resolveFileType(String mimeType) {
        if (mimeType.startsWith("image/")) {
            return FileType.IMAGE;
        }

        if (mimeType.startsWith("video/")) {
            return FileType.VIDEO;
        }

        if (mimeType.equals("application/pdf")) {
            return FileType.PDF;
        }

        return FileType.UNKNOWN;
    }
}
