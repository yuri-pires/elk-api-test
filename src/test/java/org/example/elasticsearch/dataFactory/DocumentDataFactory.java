package org.example.elasticsearch.dataFactory;

import org.example.elasticsearch.pojo.DocumentPojo;

import javax.swing.text.Document;
import java.util.Random;
import java.util.UUID;

public class DocumentDataFactory {
  public static DocumentPojo createDocument() {
    DocumentPojo documentPojo = new DocumentPojo();
    documentPojo.setName("document-" + UUID.randomUUID().toString());
    documentPojo.setAge(new Random().nextInt(100));
    documentPojo.setEmail(UUID.randomUUID().toString() + "@gmail.com");

    return documentPojo;
  }
}
