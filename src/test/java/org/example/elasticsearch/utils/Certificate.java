package org.example.elasticsearch.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Certificate {
  public static String readCertificate() throws IOException {
    String certificateFilePath = "/certs/es01/es01.crt";
    String rootDir = System.getProperty("user.dir");
    String absoluteFilePath = rootDir + certificateFilePath;

    return new String(Files.readAllBytes(Paths.get(absoluteFilePath)));
  }

  public static String readKey() throws IOException {
    String keyFilePath = "/certs/es01/es01.key";
    String rootDir = System.getProperty("user.dir");
    String absoluteFilePath = rootDir + keyFilePath;

    return new String(Files.readAllBytes(Paths.get(absoluteFilePath)));
  }
}
