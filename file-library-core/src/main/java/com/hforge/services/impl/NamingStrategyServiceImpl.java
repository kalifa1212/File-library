package com.hforge.services.impl;

import com.hforge.dto.UploadRequestDto;
import com.hforge.services.NamingStrategyService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class NamingStrategyServiceImpl implements NamingStrategyService {



    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy/MM/dd");


    @Override
    public String generateFileName(String OriginalFileName) {

        String extension = getExtension(OriginalFileName);

        String uniqueName = generateUniqueName(OriginalFileName);

        if(extension.isEmpty()) {
            return uniqueName;
        }

        return uniqueName + "." + extension;
    }


    /**
     * Génère un nom unique basé sur UUID + date
     *
     * Exemple:
     * 2026_07_11_550e8400-e29b-41d4-a716-446655440000
     */
    @Override
    public String generateUniqueName(String originalFilename) {


        String date = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));


        String uuid = UUID.randomUUID()
                .toString();


      //  String baseName = getBaseName(originalFilename);


      //  baseName = sanitize(baseName);


        return date
                + "_"
                + uuid;
             //   + "_"
               // + baseName;
    }



    /**
     * Récupère l'extension
     *
     * photo.jpg -> jpg
     */
    @Override
    public String getExtension(String filename) {

        if(filename == null || filename.isBlank()) {
            return "";
        }


        int index = filename.lastIndexOf(".");


        if(index == -1 || index == filename.length()-1) {
            return "";
        }


        return filename
                .substring(index + 1)
                .toLowerCase();
    }



    /**
     * Vérifie si le nom est valide
     */
    @Override
    public boolean isValid(String filename) {

        if(filename == null || filename.isBlank()) {
            return false;
        }


        // caractères interdits Windows/Linux
        String forbidden = "[\\\\/:*?\"<>|]";


        return !filename.matches(".*" + forbidden + ".*");
    }



    /**
     * Ajoute un préfixe
     *
     * prefix_photo.jpg
     */
    @Override
    public String addPrefix(String filename, String prefix) {

        if(prefix == null || prefix.isBlank()) {
            return filename;
        }


        return prefix + "_" + filename;
    }



    /**
     * Ajoute un suffixe avant extension
     *
     * photo_backup.jpg
     */
    @Override
    public String addSuffix(String filename, String suffix) {


        if(suffix == null || suffix.isBlank()) {
            return filename;
        }


        String extension = getExtension(filename);


        String name = getBaseName(filename);


        if(extension.isEmpty()) {

            return name + "_" + suffix;
        }


        return name
                + "_"
                + suffix
                + "."
                + extension;
    }



    /**
     * Coupe un nom trop long
     */
    @Override
    public String truncate(String filename, int maxLength) {


        if(filename == null) {
            return null;
        }


        if(filename.length() <= maxLength) {
            return filename;
        }


        String extension = getExtension(filename);


        String name = getBaseName(filename);


        int maxNameLength =
                maxLength - extension.length() - 1;


        if(maxNameLength <= 0) {
            return filename.substring(0,maxLength);
        }


        return name.substring(0,maxNameLength)
                + "."
                + extension;
    }



    private String getBaseName(String filename) {

        if(filename == null) {
            return "";
        }


        int index = filename.lastIndexOf(".");


        if(index == -1) {
            return filename;
        }


        return filename.substring(0,index);
    }



    private String sanitize(String value) {

        return value
                .replaceAll("[^a-zA-Z0-9-_]", "_")
                .toLowerCase();
    }
}
