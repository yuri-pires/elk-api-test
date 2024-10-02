package org.example.elasticsearch.modules.document;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.elasticsearch.pojo.DocumentPojo;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.example.elasticsearch.utils.Certificate.readCertificate;
import static org.example.elasticsearch.utils.Certificate.readKey;

public class DocumentCommands {
  public static Response createSampleDocumentWithDocResource(DocumentPojo documentPojo, String indexName) {
    try {
      return given()
          .filter(new AllureRestAssured())
          .auth().certificate(readCertificate(), readKey())
          .contentType(ContentType.JSON)
          .auth().basic("elastic", "elk2324")
          .body(documentPojo)
          .when()
          .post("/" + indexName + "/_doc");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Response updateSampleDocumentWithDocResource(DocumentPojo documentPojo, String indexName,
      String documentId) {
    documentPojo.setName("updated-" + UUID.randomUUID().toString());
    documentPojo.setEmail("updated@gmail.com");
    documentPojo.setAge(new Random().nextInt(100));

    try {
      return given()
          .filter(new AllureRestAssured())
          .auth().certificate(readCertificate(), readKey())
          .contentType(ContentType.JSON)
          .auth().basic("elastic", "elk2324")
          .body(documentPojo)
          .when()
          .put("/" + indexName + "/_doc/" + documentId);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
