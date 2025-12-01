package com.example.banner.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir = Paths.get(System.getProperty("user.dir") + "/uploads");

    public FileStorageService() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public String save(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + filename;
    }

    public void deleteFiles(String file){
        if(file != null && !file.isEmpty()){
                try{
                    Path path = Paths.get(System.getProperty("user.dir") + file);
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        }
    }
}
