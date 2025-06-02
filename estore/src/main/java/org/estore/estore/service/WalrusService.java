package org.estore.estore.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
@Component
public class WalrusService {
    public String upload(MultipartFile file) {
        String walrusUrl = "https://publisher.walrus-testnet.walrus.space/v1/blobs";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("walrusUrl", walrusUrl);
    }
}
