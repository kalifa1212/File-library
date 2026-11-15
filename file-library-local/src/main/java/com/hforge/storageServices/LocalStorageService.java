package com.hforge.storageServices;

import com.hforge.config.StorageProperties;
import com.hforge.dto.ProcessedFile;
import com.hforge.dto.StorageResultDto;
import com.hforge.dto.UploadRequestDto;
import com.hforge.enums.StorageType;
import com.hforge.exceptions.FileNotFoundException;
import com.hforge.model.FileInfo;
import com.hforge.services.StorageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.time.Duration;
import java.util.List;

public class LocalStorageService implements StorageService{
    //private final Path root = Paths.get("uploads");
    private final Path root ;

    public LocalStorageService(StorageProperties properties) {
        this.root = Paths.get(properties.getRootPath())
                .toAbsolutePath()
                .normalize();
    }


    @Override
    public StorageResultDto store(ProcessedFile file) {
        Path filePath = root.resolve(file.getPath());
        try {
            Files.createDirectories(filePath.getParent());

            System.out.println("Enregistrement du fichier");
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Correction du NullPointerException
            StorageResultDto resultDto = new StorageResultDto();
            resultDto.setMessage("Uploaded");
            resultDto.setPath(filePath.toString());
            resultDto.setSuccess(true);
            return resultDto;

        } catch (IOException e) {
            throw new RuntimeException("Erreur upload fichier", e);
        }
    }

    @Override
    public Path absolutePath() {
        return this.root;
    }

    @Override
    public StorageResultDto store(List<FileInfo> file) {
        return null;
    }

    @Override
    public Resource getRessource(String fileRelativePath) {

        Path filePath = resolvePath(fileRelativePath);

        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            throw new FileNotFoundException(
                    "File not found: " + fileRelativePath
            );
        }

        return resource;
    }

    @Override
    public InputStream getStream(String fileRelativePath) {
        Path filePath = resolvePath(fileRelativePath);

        try {
            return Files.newInputStream(
                    filePath,
                    StandardOpenOption.READ
            );

        } catch (NoSuchFileException e) {

            throw new FileNotFoundException(
                    "File not found: " + fileRelativePath,
                    e
            );

        } catch (IOException e) {

            throw new FileNotFoundException(
                    "Unable to open file: " + fileRelativePath,
                    e
            );
        }
    }

    @Override
    public InputStream getStreamRange(String fileRelativePath, long start, long length) {
        Path filePath = resolvePath(fileRelativePath);

        try {
            SeekableByteChannel channel =
                    Files.newByteChannel(
                            filePath,
                            StandardOpenOption.READ
                    );

            channel.position(start);

            return new LimitedInputStream(
                    Channels.newInputStream(channel),
                    length
            );

        } catch (IOException e) {
            throw new FileNotFoundException(
                    "Unable to open file",
                    e
            );
        }
    }

    @Override
    public Path getPath(String fileRelativePath) {
        Path filePath = resolvePath(fileRelativePath);
        return filePath;
    }

    @Override
    public StorageResultDto store(UploadRequestDto request) {
        return null;
    }

    @Override
    public StorageResultDto store(UploadRequestDto request, String filename) {
        return null;
    }

    @Override
    public byte[] readBytes(String fileId) {
        return new byte[0];
    }

    @Override
    public void delete(String fileId) {

    }

    @Override
    public void delete(List<String> fileIds) {

    }

    @Override
    public boolean exists(String fileId) {
        return false;
    }

    @Override
    public StorageResultDto get(String fileId) {
        return null;
    }

    @Override
    public long size(String fileId) {
        return 0;
    }

    @Override
    public StorageResultDto copy(String sourceFileId, String destination) {
        return null;
    }

    @Override
    public StorageResultDto rename(String fileId, String newName) {
        return null;
    }

    @Override
    public String getUrl(String fileId) {
        return null;
    }

    @Override
    public String getPresignedUrl(String fileId, Duration expiration) {
        return null;
    }

    @Override
    public StorageType getStorageType() {
        return null;
    }

    @Override
    public boolean healthCheck() {
        return false;
    }
    private Path resolvePath(String fileRelativePath) {

        Path path = root
                .resolve(fileRelativePath)
                .normalize();

        if (!path.startsWith(root)) {
            throw new SecurityException(
                    "Invalid file path"
            );
        }

        return path;
    }
}