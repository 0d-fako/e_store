package org.estore.estore.service;

import org.estore.estore.dto.response.walrus.WalrusUploadResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class WalrusService {
    public String upload(MultipartFile file) {
        String walrusUrl = "https://publisher.walrus-testnet.walrus.space/v1/blobs";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        RequestEntity<MultipartFile> requestEntity = new RequestEntity<>(file, headers, HttpMethod.PUT, URI.create(walrusUrl));
        Map<String, Object> params = new HashMap<>();
        params.put("epochs", 5);
        params.put("send_object_to", "0x7a26fe751dbdc0f43e63a51712738241ef7f91e6e9a8a256a958ebd3d565b27e");

        Resource resource = file.getResource();
        HttpEntity<?> httpEntity = new HttpEntity(resource, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WalrusUploadResponse> response = restTemplate.exchange(URI.create(walrusUrl), HttpMethod.PUT, requestEntity, WalrusUploadResponse.class);
        WalrusUploadResponse walrusUploadResponse = response.getBody();
        boolean isFileAlreadyExists = walrusUploadResponse != null && walrusUploadResponse.get

        if (isFileAlreadyExists) {
            return walrusUploadResponse.getBlobObject().getBlodId;
        }
        return walrusUploadResponse.getNewlyCreated().getBlobObject().getBlodId;
    }
}
