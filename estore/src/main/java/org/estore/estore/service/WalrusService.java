package org.estore.estore.service;

import lombok.extern.slf4j.Slf4j;
import org.estore.estore.dto.response.walrus.WalrusUploadResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.PUT;

@Component
@Slf4j
public class WalrusService {
    public String upload(MultipartFile file) {
        String walrusUrl = "https://publisher.walrus-testnet.walrus.space/v1/blobs";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // RequestEntity<MultipartFile> requestEntity = new RequestEntity<>(file, headers, PUT, URI.create(walrusUrl));

        Map<String, Object> params = new HashMap<>();
        params.put("epochs", 5);
        params.put("send_object_to", "0x7a26fe751dbdc0f43e63a51712738241ef7f91e6e9a8a256a958ebd3d565b27e");

        Resource resource = file.getResource();
        HttpEntity<?> requestEntity = new HttpEntity<>(resource, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WalrusUploadResponse> response = restTemplate.exchange(walrusUrl,PUT, requestEntity, WalrusUploadResponse.class, params);
        WalrusUploadResponse walrusUploadResponse = response.getBody();

        boolean isFileAlreadyExists = walrusUploadResponse != null && walrusUploadResponse.getNewlyCreated() == null;

        if (isFileAlreadyExists) {
            return walrusUploadResponse.getAlreadyCertified().getBlobId();
        }
        assert walrusUploadResponse != null;
        return walrusUploadResponse.getNewlyCreated().getBlobObject().getBlobId();
    }

    public byte[] getFileBy(String blobId) {
        String walrusAggregatorUrl = "https://aggregator.walrus-testnet.walrus.space/v1/blobs/";

        try {
            log.info("Downloading blob: {}", blobId);
            log.info("URL: {}", walrusAggregatorUrl + blobId);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    walrusAggregatorUrl + blobId,
                    HttpMethod.GET,
                    entity,
                    byte[].class
            );

            log.info("Response status: {}", response.getStatusCode());
            log.info("Content length: {}", response.getBody() != null ? response.getBody().length : "null");

            return response.getBody();

        } catch (Exception e) {
            log.error("Download failed: {}", e.getMessage(), e);
            return null;
        }
    }
}
