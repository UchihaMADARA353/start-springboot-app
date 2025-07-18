package com.example.start_spring_app.service.strategy;

import org.springframework.core.io.Resource;

public interface LoadFileStrategy {
    Resource loadFile(String filename);
}
