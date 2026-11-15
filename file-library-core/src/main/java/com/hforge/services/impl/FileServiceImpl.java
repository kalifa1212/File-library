package com.hforge.services.impl;

import com.hforge.dto.ProcessedFile;
import com.hforge.dto.StorageResultDto;
import com.hforge.dto.UploadRequestDto;
import com.hforge.enums.FileType;
import com.hforge.exceptions.ErrorCodes;
import com.hforge.exceptions.FileNotFoundException;
import com.hforge.model.FileInfo;
import com.hforge.services.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;


public class FileServiceImpl implements FileService {

    private final List<FileProcessor> processors;
    private final FileTypeDetector fileTypeDetector;
    private final StorageService storageService;
    private final FfmpegService ffmpegService;


    public FileServiceImpl(List<FileProcessor> processors, FileTypeDetector fileTypeDetector,
                           StorageService storageService, FfmpegService ffmpegService) {
        this.processors = processors;
        this.fileTypeDetector = fileTypeDetector;
        this.storageService = storageService;
        this.ffmpegService= ffmpegService;
    }
    @Override
    public FileInfo upload(MultipartFile file) throws IOException, InterruptedException {
        System.out.println("file service ");
        FileType typeFichier=fileTypeDetector.detect(file);

        System.out.println("recherche  du bon process");
        FileProcessor processor = processors.stream()
                .filter(p -> p.supports(typeFichier))
                .findFirst()
                .orElseThrow(() -> new FileNotFoundException("Type de fichier non supporté : " + typeFichier, ErrorCodes.INVALID_FILE_TYPE));
        FileInfo fileInfo = processor.process(file);
        fileInfo.setMimeType(fileTypeDetector.mimeType(file));
        System.out.println("Lancement de l'anregistrement");
        ProcessedFile processedFile = new ProcessedFile();
        processedFile.setPath(fileInfo.getStoragePath());
        processedFile.setInputStream(file.getInputStream());
        processedFile.setOverwrite(true);
        processedFile.setStoreName(fileInfo.getStoredName());
        StorageResultDto storageResult = storageService.store(processedFile);
        System.out.println(fileInfo.getStoragePath());
        System.out.println(fileInfo.getStoredName());
        System.out.println("Operation terminer");
        if(typeFichier==FileType.VIDEO){
            ffmpegService.generateHls(Path.of(fileInfo.getStoragePath()),storageService.absolutePath());
            FileInfo thumbnailInfo = processor.thumnailGeneration(fileInfo);
        }
        return  fileInfo;
    }

    @Override
    public List<FileInfo> upload(List<UploadRequestDto> requests) {
        return null;
    }

    @Override
    public InputStream getStream(String filePath) {
        return storageService.getStream(filePath);
    }

    @Override
    public InputStream getStreamRange(String path, long start, long length) {
        return storageService.getStreamRange(
                path,
                start,
                length
        );
    }

    @Override
    public Path getPath(String filePath) {
        return storageService.getPath(filePath);
    }

    @Override
    public Resource getResource(String filePath) {
        System.out.println(filePath);
        return storageService.getRessource(filePath);
    }

    @Override
    public FileInfo get(String fileId) {
        return null;
    }

    @Override
    public List<FileInfo> getAll() {
        return null;
    }

    @Override
    public boolean exists(String fileId) {
        return false;
    }

    @Override
    public void delete(String fileId) {

    }

    @Override
    public void delete(List<String> fileIds) {

    }

    @Override
    public void rename(String fileId, String newName) {

    }

    @Override
    public FileInfo metadata(String fileId) {
        return null;
    }
}
