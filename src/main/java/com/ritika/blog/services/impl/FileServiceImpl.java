package com.ritika.blog.services.impl;

import com.ritika.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        // 1. Check if file is empty
        if (file.isEmpty()) {
            throw new IOException("Cannot upload an empty file.");
        }

        // 2. Validate content type (only allow images)
        String contentType = file.getContentType();
        if (!(contentType.equals("image/png") ||
                contentType.equals("image/jpeg") ||
                contentType.equals("image/jpg"))) {
            throw new IOException("Only PNG, JPEG and JPG images are allowed.");
        }

        // 3. Optional: Check file size (max 5MB)
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (file.getSize() > maxSize) {
            throw new IOException("File size exceeds 5MB limit.");
        }

        // 4. Generate random file name with correct extension
        String name = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId + name.substring(name.lastIndexOf("."));

        // 5. Create full path
        String filePath = path + File.separator + fileName1;

        // 6. Create folder if not exists
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        // 7. Save file to path
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }


    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        File file = new File(fullPath);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found at " + fullPath);
        }

        return new FileInputStream(file);
    }


}
