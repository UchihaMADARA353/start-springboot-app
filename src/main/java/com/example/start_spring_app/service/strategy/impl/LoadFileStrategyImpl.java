package com.example.start_spring_app.service.strategy.impl;

import com.example.start_spring_app.service.strategy.LoadFileStrategy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LoadFileStrategyImpl implements LoadFileStrategy {
    private static final String UPLOAD_DIRd = "uploads";

    @Override
    public Resource loadFile(String filename) {
        try {
            String baseDir = new File(".").getCanonicalPath();
            Path filePath = Paths.get(baseDir, UPLOAD_DIRd).resolve(filename);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while loading file: " + filename, e);
        }
    }
}
