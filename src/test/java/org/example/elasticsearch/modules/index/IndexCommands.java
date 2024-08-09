package org.example.elasticsearch.modules.index;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.elasticsearch.pojo.IndexPojo;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.example.elasticsearch.utils.Certificate.readCertificate;
import static org.example.elasticsearch.utils.Certificate.readKey;

public class IndexCommands {
  /**
   * Static method to assist when there is a precondition that requires
   * an index to exist before running the actual test
   */
  public static Response createIndex(IndexPojo indexPojo) {
    try {
      return given()
        .auth().certificate(readCertificate(), readKey())
        .contentType(ContentType.JSON)
        .auth().basic("elastic", "elk2324")
      .when()
        .put("/" + indexPojo.getName());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Response createIndexWithMappings(IndexPojo indexPojo) {
    try {
      return given()
        .auth().certificate(readCertificate(), readKey())
        .contentType(ContentType.JSON)
        .auth().basic("elastic", "elk2324")
        .body(indexPojo.getMappings())
      .when()
        .put("/" + indexPojo.getName());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Response deleteIndex(IndexPojo indexPojo) {
    try {
      return given()
        .auth().certificate(readCertificate(), readKey())
        .contentType(ContentType.JSON)
        .auth().basic("elastic", "elk2324")
      .when()
        .delete("/" + indexPojo.getName());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Response getIndexSettings(IndexPojo indexPojo) {
    try {
      return given()
        .auth().certificate(readCertificate(), readKey())
        .contentType(ContentType.JSON)
        .auth().basic("elastic", "elk2324")
      .when()
        .get("/" + indexPojo.getName() + "/_settings");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
