package com.hforge.processor;

import com.hforge.dto.ProcessedFile;
import com.hforge.enums.FileStatus;
import com.hforge.enums.FileType;
import com.hforge.enums.StorageType;
import com.hforge.enums.Visibility;
import com.hforge.model.FileInfo;
import com.hforge.services.FileProcessor;
import com.hforge.services.NamingStrategyService;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class ImageProcessor implements FileProcessor {
    private final NamingStrategyService namingStrategyService;
    private final com.hforge.services.FileTypeDetector fileTypeDetector;

    public ImageProcessor(NamingStrategyService namingStrategyService, com.hforge.services.FileTypeDetector fileTypeDetector) {
        this.namingStrategyService=namingStrategyService;
        this.fileTypeDetector=fileTypeDetector;

    }

    @Override
    public boolean supports(FileType type) {
        return type == FileType.IMAGE;
    }

    @Override
    public FileInfo process(MultipartFile file) {
        LocalDate today = LocalDate.now();

        String folder = Path.of(
                "images",
                String.valueOf(today.getYear()),
                String.format("%02d", today.getMonthValue())
        ).toString();
        String storedImageName = namingStrategyService.generateFileName(file.getOriginalFilename());
        Path relativePath = Path.of(folder, storedImageName);

            // TODO: Mettre ici ta logique de compression / resize en WebP
            System.out.println("image Processing");
            FileInfo result = new FileInfo();
            result.setFileType(fileTypeDetector.detect(file));
            result.setOriginalName(file.getOriginalFilename());
            result.setStoredName(storedImageName); // Ton processeur choisit le nom et l'extension cible
            result.setStoragePath(relativePath.toString());
            result.setMimeType(file.getContentType());
            result.setSize(file.getSize()); // À remplacer par la taille du fichier traité plus tard
            result.setStatus(FileStatus.PROCESSING); // Ou PROCESSED selon votre workflow
            result.setStorageType(StorageType.LOCAL); // Ou S3 / AZURE selon votre stack
            result.setVisibility(Visibility.PRIVATE);

            return result;
    }

    @Override
    public FileInfo thumnailGeneration(FileInfo fileInfo) {
        return null;
    }

}
