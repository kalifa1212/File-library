package com.hforge.services;

import com.hforge.dto.ProcessedFile;
import com.hforge.dto.StorageResultDto;
import com.hforge.dto.UploadRequestDto;
import com.hforge.enums.StorageType;
import com.hforge.model.FileInfo;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

public interface StorageService {



    StorageResultDto store(ProcessedFile file);
    Path absolutePath();
    StorageResultDto store(List<FileInfo> file);

    /**
     * Stocker un fichier
     */
    StorageResultDto store(UploadRequestDto request);



    /**
     * Stocker un fichier avec un nom personnalisé
     */
    StorageResultDto store(
            UploadRequestDto request,
            String filename
    );

    /**
     * Lire un fichier sous forme de tableau bytes
     */
    byte[] readBytes(String fileId);



    /**
     * Supprimer un fichier
     */
    void delete(String fileId);



    /**
     * Supprimer plusieurs fichiers
     */
    void delete(List<String> fileIds);



    /**
     * Vérifier si un fichier existe
     */
    boolean exists(String fileId);



    /**
     * Récupérer les informations du fichier stocké
     */
    StorageResultDto get(String fileId);



    /**
     * Récupérer la taille du fichier
     */
    long size(String fileId);



    /**
     * Copier un fichier
     */
    StorageResultDto copy(
            String sourceFileId,
            String destination
    );

    /**
     * Renommer un fichier
     */
    StorageResultDto rename(
            String fileId,
            String newName
    );



    /**
     * Obtenir l'URL du fichier
     */
    String getUrl(String fileId);



    /**
     * Générer une URL temporaire
     * (MinIO / S3)
     */
    String getPresignedUrl(
            String fileId,
            Duration expiration
    );

    /**
     * Télécharger un fichier
     */
    Resource getRessource(String fileRelativePath);

    /**
     * Lire un fichier sous forme de flux
     */
    InputStream getStream(String fileRelativePath);

    InputStream getStreamRange(
            String path,
            long start,
            long length
    );

    /**
     * Obtenir le path global
     */
    Path getPath(String path);

    /**
     * Récupérer le type de stockage
     */
    StorageType getStorageType();



    /**
     * Vérifier la connexion au stockage
     */
    boolean healthCheck();



}