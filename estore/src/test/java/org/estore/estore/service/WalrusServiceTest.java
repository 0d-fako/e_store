package org.estore.estore.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class WalrusServiceTest {
    @Autowired
    private WalrusService walrusService;


    @Test
    void testCanUploadFile(){
        String filelocation = "C:\\Users\\Dell\\Desktop\\PROJECTS\\estore\\estore\\src\\main\\resources\\assets\\iphone.jpg";

        Path path = Paths.get(filelocation);

        try(var inputStream = Files.newInputStream(path)) {
            MultipartFile file = new MockMultipartFile("image", inputStream);
            String blobId = walrusService.upload(file);
            log.info("blobId: {}", blobId);
            assertThat(blobId).isNotNull();
            assertThat(blobId).isNotEmpty();

        }catch (IOException exception){
            exception.printStackTrace();
        }
    }


    @Test
    void testCanGetFile(){
        String blobId = "J9cXuJjM3O71evQWejrzLCeSqYEjqDLErCLeAtHuL3I";
        byte[] fileContent = walrusService.getFileBy(blobId);
        log.info("data: {}", fileContent);
        assertThat(fileContent).isNotNull();
        assertThat(fileContent).isNotEmpty();



    }

}
