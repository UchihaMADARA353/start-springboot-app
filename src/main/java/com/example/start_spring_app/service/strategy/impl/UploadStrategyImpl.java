package com.example.start_spring_app.service.strategy.impl;

import com.example.start_spring_app.service.strategy.UploadStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("uploadStrategy") // corrige le nom pour être cohérent
public class UploadStrategyImpl implements UploadStrategy {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // Chemin absolu du répertoire de travail
            String baseDir = new File(".").getCanonicalPath();

            // Résoudre le chemin absolu de uploads/
            Path uploadPath = Paths.get(baseDir, UPLOAD_DIR);

            // Créer le dossier s'il n'existe pas
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Vérifier le nom du fichier
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new IOException("Nom de fichier invalide !");
            }

            // Construire le chemin complet du fichier
            Path filePath = uploadPath.resolve(fileName);

            System.out.println("➡️ Sauvegarde dans : " + filePath);

            // Enregistrer le fichier
            file.transferTo(filePath.toFile());

            return fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
}
