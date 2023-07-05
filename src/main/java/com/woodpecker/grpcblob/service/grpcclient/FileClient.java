package com.woodpecker.grpcblob.service.grpcclient;

import java.io.IOException;

public interface FileClient
{

     void sendFile(String filePath) throws IOException;

}