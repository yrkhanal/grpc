package com.woodpecker.grpcblob.controller;

import com.woodpecker.grpcblob.service.grpcclient.FileClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/upload-file")
@RequiredArgsConstructor
public class ClientFileUploadController {

    private final FileClient fileClient;

    @PostMapping
    public ResponseEntity<Void> uploadFile(@RequestParam String filePath) throws IOException {
        fileClient.sendFile(filePath);
        return ResponseEntity.ok().build();
    }
}
