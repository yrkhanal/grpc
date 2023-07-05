package com.woodpecker.grpcblob.service.grpcclient;

import com.woodpecker.FileUploadResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileUploadObserver implements StreamObserver<FileUploadResponse> {

    @Override
    public void onNext(FileUploadResponse fileUploadResponse) {
        log.info("File upload status {}", fileUploadResponse.getStatus());
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error occurs while uploading file", throwable);
    }

    @Override
    public void onCompleted() {
        log.info("File upload completed");
    }

}
