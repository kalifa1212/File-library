package com.hforge.services;

import com.hforge.dto.StorageResultDto;
import com.hforge.dto.UploadRequestDto;
import com.hforge.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface FileService {

    FileInfo upload(MultipartFile request) throws IOException, InterruptedException;

    List<FileInfo> upload(List<UploadRequestDto> requests);

    InputStream getStream(String fileId);

    InputStream getStreamRange(
            String path,
            long start,
            long length
    );
    Path getPath(String fileId);

    Resource getResource(String fileId);

    FileInfo get(String fileId);

    List<FileInfo> getAll();

    boolean exists(String fileId);

    void delete(String fileId);

    void delete(List<String> fileIds);

    void rename(String fileId, String newName);

    FileInfo metadata(String fileId);


}
