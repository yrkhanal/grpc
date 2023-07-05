package com.woodpecker.grpcblob.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


@RestController
@RequestMapping("blob")
public class BlobController {

    @Value("https://ocsyhoty2k.blob.core.windows.net/grpc/applicationinsights.log?sp=r&st=2023-06-29T13:42:37Z&se=2023-06-30T21:42:37Z&spr=https&sv=2022-11-02&sr=b&sig=2nwU7m0ImqfC%2BNSiIGUtT0Gb3AgIKztjg%2BXDg5ixkbE%3D")
    private Resource blobFile;

   // @Value("https://ocsyhoty2k.blob.core.windows.net/write?sp=rw&st=2023-06-28T19:12:53Z&se=2023-06-29T03:12:53Z&sv=2022-11-02&sr=c&sig=Ruidzk3nZcM5aht8CXE4U9P0pJQ2Q61ZTXlck0gnbyc%3D")
    @Value("https://ocsyhoty2k.blob.core.windows.net/write/yogi.txt?sp=r&st=2023-06-29T13:47:08Z&se=2023-06-30T21:47:08Z&spr=https&sv=2022-11-02&sr=b&sig=qWSCfZiRbx26Txulq0hkVfpDM7XrwFw%2BCmB1Y%2B2%2B1%2Fo%3D")
    private Resource writeHere;

    @GetMapping("/readBlobFile")
    public String readBlobFile() throws IOException {
        return StreamUtils.copyToString(
                this.blobFile.getInputStream(),
                Charset.defaultCharset());
    }

    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestBody String data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(data.getBytes())) {
            baos.write(dataStream.readAllBytes());
        }
        return "file was updated";
    }
}
