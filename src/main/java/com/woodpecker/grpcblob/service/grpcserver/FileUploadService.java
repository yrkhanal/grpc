package com.woodpecker.grpcblob.service.grpcserver;

import com.google.protobuf.ByteString;
import com.woodpecker.FileServiceGrpc;
import com.woodpecker.FileUploadRequest;
import com.woodpecker.FileUploadResponse;
import com.woodpecker.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.apache.logging.log4j.util.Strings.isBlank;

@GrpcService
@Slf4j
public class FileUploadService extends FileServiceGrpc.FileServiceImplBase {

    private static final Path SERVER_BASE_PATH = Paths.get("src/main/resources");

    @Override
    public StreamObserver<FileUploadRequest> upload(StreamObserver<FileUploadResponse> responseObserver) {
        return new StreamObserver<>() {
            OutputStream writer;
            Status status = Status.IN_PROGRESS;

            @Override
            public void onNext(FileUploadRequest fileUploadRequest) {

                try {
                    if(!isBlank(fileUploadRequest.getMetadata().getName())){
                        writer = getFilePath(fileUploadRequest);
                    } else {
                        writeFile(writer, fileUploadRequest.getFile().getContent());
                    }

                } catch (IOException e) {
                    this.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                status = Status.FAILED;
                this.onCompleted();
            }

            @Override
            public void onCompleted() {
                closeFile(writer);
                status = Status.IN_PROGRESS.equals(status) ? Status.SUCCESS : status;
                FileUploadResponse response = FileUploadResponse.newBuilder()
                        .setStatus(status)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    private OutputStream getFilePath(FileUploadRequest request) throws IOException {
        var fileName = request.getMetadata().getName() + "." + request.getMetadata().getType();
        return Files.newOutputStream(SERVER_BASE_PATH.resolve(fileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private void writeFile(OutputStream writer, ByteString content) throws IOException {
        writer.write(content.toByteArray());
        writer.flush();
    }

    private void closeFile(OutputStream writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
