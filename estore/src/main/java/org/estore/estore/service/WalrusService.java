package org.estore.estore.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;

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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("epochs", 5);
        params.put("send_object_to", );

        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.exchange(walrusUrl, HttpMethod.PUT, requestEntity, WalrusService.class);
        return "";
    }
}
