package com.hforge.services;

import com.hforge.dto.UploadRequestDto;

public interface NamingStrategyService {
    /**
     * Génère le nom final du fichier.
     */
    String generateFileName(String OriginalFileName);

    /**
     * Génère uniquement un nom unique.
     */
    String generateUniqueName(String originalFilename);

    /**
     * Retourne l'extension.
     */
    String getExtension(String filename);


    /**
     * Vérifie qu'un nom est valide.
     */
    boolean isValid(String filename);

    /**
     * Ajoute un préfixe.
     */
    String addPrefix(String filename, String prefix);

    /**
     * Ajoute un suffixe.
     */
    String addSuffix(String filename, String suffix);

    /**
     * Tronque un nom trop long.
     */
    String truncate(String filename, int maxLength);
}
