package com.hforge.processor;


import com.hforge.Services.VideoToolsService;
import com.hforge.config.ThumbnailOptions;
import com.hforge.enums.FileStatus;
import com.hforge.enums.FileType;
import com.hforge.enums.StorageType;
import com.hforge.enums.Visibility;
import com.hforge.model.FileInfo;
import com.hforge.services.FileProcessor;
import com.hforge.services.FileTypeDetector;
import com.hforge.services.NamingStrategyService;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class VideosProcessor implements FileProcessor {

    private final NamingStrategyService namingStrategyService;
    private final FileTypeDetector fileTypeDetector;
    private final VideoToolsService videoToolsService;
    public VideosProcessor(NamingStrategyService namingStrategyService, FileTypeDetector fileTypeDetector,VideoToolsService videoToolsService) {
        this.namingStrategyService=namingStrategyService;
        this.fileTypeDetector=fileTypeDetector;
        this.videoToolsService=videoToolsService;

    }
    @Override
    public boolean supports(FileType type) {
        return type == FileType.VIDEO;
    }

    @Override
    public FileInfo process(MultipartFile file) {

        LocalDate today = LocalDate.now();
        UUID videoId = UUID.randomUUID();

        String folder = Path.of(
                "videos",
                String.valueOf(today.getYear()),
                String.format("%02d", today.getMonthValue()),
                videoId.toString()
        ).toString();
        String storedVideoName = namingStrategyService.generateFileName(file.getOriginalFilename());
        Path relativePath = Path.of(folder, storedVideoName);

        // TODO: Mettre ici ta logique de compression / resize en WebP
        System.out.println("videos Processing");

        FileInfo result = new FileInfo();
        result.setId(videoId);
        result.setFileType(fileTypeDetector.detect(file));
        result.setOriginalName(file.getOriginalFilename());
        result.setStoredName(storedVideoName); // Ton processeur choisit le nom et l'extension cible
        result.setStoragePath(relativePath.toString());
        result.setMimeType(file.getContentType());
        result.setSize(file.getSize()); // À remplacer par la taille du fichier traité plus tard
        result.setStatus(FileStatus.PROCESSING); // Ou PROCESSED selon votre workflow
        result.setStorageType(StorageType.LOCAL); // Ou S3 / AZURE selon votre stack
        result.setVisibility(Visibility.PRIVATE);
        // TODO Nouvelles propriétés pour le thumbnail dans FileInfo


        return result;
    }

    @Override
    public FileInfo thumnailGeneration(FileInfo fileInfo) {
        Path totalpath= Path.of(fileInfo.getStoragePath());
        String getParent= String.valueOf(totalpath.getParent());
        String baseName = fileInfo.getStoredName().contains(".")
                ? fileInfo.getStoredName().substring(0, fileInfo.getStoredName().lastIndexOf("."))
                : fileInfo.getStoredName();
        String storedThumbName = baseName + "_thumb.webp";
        Path relativeThumbPath = Path.of(getParent, storedThumbName);
        /*
         * Génération du thumbnail
         */

        ThumbnailOptions thumbnailOptions = ThumbnailOptions.webp();
        Path root = Paths.get("uploads");
        Path filePath = root.resolve(fileInfo.getStoragePath());
        Path thumbnailPath=root.resolve(relativeThumbPath);

        videoToolsService.generateThumbnail(
                filePath,
                thumbnailPath,
                thumbnailOptions
        );
        fileInfo.setThumbnailPath(relativeThumbPath.toString());
        return fileInfo;
    }

}
