package org.estore.estore.service;

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
public class WalrusServiceTest {
    @Autowired
    private WalrusService walrusService;


    @Test
    void testCanUploadFile(){
        String filelocation = "C:\\Users\\Dell\\Desktop\\PROJECTS\\estore\\estore\\src\\main\\resources\\assets\\iphone.jpg";

        Path path = Paths.get(filelocation);

        try(var inputStream = Files.newInputStream(path)) {
            MultipartFile file = new MockMultipartFile("image", inputStream);
            String data = walrusService.upload(file);
            assertThat(data).isNotNull();
            assertThat(data).isNotEmpty();

        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

}
