package org.estore.estore.controller;

import lombok.AllArgsConstructor;
import org.estore.estore.service.WalrusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/media")
@AllArgsConstructor
public class MediaController {

    private final WalrusService walrusService;


    @GetMapping(produces = {MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> getMedia(@RequestParam String blobId) {
        return ResponseEntity.status(HttpStatus.OK).body(walrusService.getFileBy(blobId));
    }
}
