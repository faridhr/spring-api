package com.springapi.springapi.controllers.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/administrator/random")
public class RandomController {

    private static final String UPLOAD_PATH = "/home/faridhr/Pictures/EEB/uploadPath/";
    private static final DecimalFormat df = new DecimalFormat("0.00");

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

    @PostMapping("/convert-grayscale")
    public ResponseEntity<Map<String, String>> convertToGrayscale(@RequestParam("file") MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        if (file.isEmpty()){
            result.put("statusCode", HttpStatus.BAD_REQUEST.toString());
            result.put("message", "File is empty");
        }

        String originalPath = "convert-file/original/";
        String convertPath = "convert-file/converted/";
        String fileName = "";

        try {
            fileName = file.getOriginalFilename();
            String extensionFile = fileName.substring(fileName.indexOf(".") + 1);
            while (extensionFile.contains(".")) {
                extensionFile = extensionFile.substring(extensionFile.indexOf(".") + 1);
            }
            fileName = UUID.randomUUID() + "." + extensionFile;
            Path orgPath = Paths.get(UPLOAD_PATH + originalPath + fileName);
            Files.write(orgPath, file.getBytes());
            File orgFile = orgPath.toFile();

            /**
             * Creating image to Grayscale
             */
            BufferedImage input = ImageIO.read(file.getInputStream());
            BufferedImage resultImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = resultImage.createGraphics();
            graphics2D.drawImage(input, 0, 0, Color.WHITE, null);
            for (int i = 0; i < resultImage.getHeight(); i++) {
                for (int j = 0; j < resultImage.getWidth(); j++) {
                    Color color = new Color(resultImage.getRGB(j, i));
                    int red = (int) (color.getRed() * 0.299);
                    int green = (int) (color.getGreen() * 0.587);
                    int blue = (int) (color.getBlue() * 0.114);
                    Color newColor = new Color(
                            red + green + blue,
                            red + green + blue,
                            red + green + blue
                    );
                    resultImage.setRGB(j, i, newColor.getRGB());
                }
            }
            File convertedFile = new File(UPLOAD_PATH + convertPath + fileName);
            ImageIO.write(resultImage, extensionFile, convertedFile);

            Double orgSize = (double) orgFile.length() / 1000;
            Double convertedSize = (double) convertedFile.length() / 1000;
            result.put("originalSize", df.format(orgSize) + " KB");
            result.put("convertedSize", df.format(convertedSize) + " KB");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            result.put("message", "Cannot convert image to grayscale");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
