package com.springapi.springapi.controllers.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/administrator/random")
public class RandomController {

    private static final String UPLOAD_PATH = "/home/faridhr/Pictures/EEB/uploadPath/";

    @PostMapping("/upload")
    public Boolean upload(@RequestParam("file") MultipartFile requestFile) {
        String[] allowedFileExtension = {
                "pdf", "jpg", "jpeg", "png", "html"
        };
        if (requestFile.isEmpty()) {
            return false;
        }
        try {
            String fileName = requestFile.getOriginalFilename();
            String extensionFile = fileName.substring(fileName.indexOf(".") + 1);
            while (extensionFile.contains(".")) {
                extensionFile = extensionFile.substring(extensionFile.indexOf(".") + 1);
            }
            for (int i = 0; i < allowedFileExtension.length; i++) {
                if (extensionFile.equals(allowedFileExtension[i])){
                    String documentId = UUID.randomUUID().toString() + "." + extensionFile;
                    Path path = Paths.get(UPLOAD_PATH + documentId);
                    Files.write(path, requestFile.getBytes());

                    FileChannel fileChannel = FileChannel.open(path);
                    //Handle more than 10MB
                    if (fileChannel.size() > 10_480_000) {

                    }
                    break;
                } else if (i == allowedFileExtension.length - 1) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
