package com.creative.ekart.service;

import com.creative.ekart.exception.ApiException;
import com.creative.ekart.service.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    public String uploadImage(MultipartFile image,String path) throws IOException {
        if(image==null || image.isEmpty()){
            throw new ApiException("Image is empty");
        }

        String originalFilename = image.getOriginalFilename();
        if(originalFilename==null || originalFilename.lastIndexOf(".")==-1){
            throw new ApiException("Image name is empty or not properly formated {eg : name.ext}");
        }
        String newName = UUID.randomUUID().toString()
                + originalFilename.substring(originalFilename.lastIndexOf("."));
        File folder = new File(path);

        if(!folder.exists()){
            folder.mkdirs();
        }
        String dest = folder + File.separator + newName;
        Files.copy(image.getInputStream(), Path.of(dest));
        return newName;
    }
}
