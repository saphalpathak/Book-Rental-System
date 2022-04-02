package com.wicc.brs.component;

import com.wicc.brs.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

@Component
@Slf4j
public class FileComponent {
    public ResponseDto storeFile(MultipartFile multipartFile) throws IOException {
        //creating the folder to store image
        String fileDir = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "brs";
        File directoryPath = new File(fileDir);
        if (!directoryPath.exists()) {
            boolean mkdirs = directoryPath.mkdirs();
        } else {
            log.info("File already exists!");
        }
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        assert ext != null;
        //finding the extension and checking if it is acceptable ot not
        if (ext.equalsIgnoreCase("jpg") ||
                ext.equalsIgnoreCase("png") ||
                ext.equalsIgnoreCase("jpeg")) {
            UUID uuid = UUID.randomUUID();
            //creating unique file name
            String filePath = fileDir + File.separator + uuid + "-" + multipartFile.getOriginalFilename();
            File newFile = new File(filePath);
            //saving image to folder
            //if image is saved to folder response dto return file path and true response
            multipartFile.transferTo(newFile);
            return ResponseDto.builder()
                    .status(true)
                    .message(filePath)
                    .build();
        } else {
            //if file not saved to folder response dto return invalid message and status false
            return ResponseDto.builder()
                    .status(false)
                    .message("Invalid extension")
                    .build();
        }
    }

    //converting file to base64 code
    public String base64Encoded(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }
}
