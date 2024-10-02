package org.example.elasticsearch.modules.index;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.elasticsearch.pojo.IndexPojo;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.example.elasticsearch.utils.Certificate.readCertificate;
import static org.example.elasticsearch.utils.Certificate.readKey;

public class IndexCommands {
  /**
   * Static methods to assist when there is a precondition that requires
   * an index to exist before running the actual test or you need to search/delete
   * a index.
   *
   * In a real-world scenario, it's crucial to hide the basic password. Instead,
   * you should pass it as an environment
   * variable, for example:
   * String password = System.getenv("your_environment_variable_name_here");
   */
  public static Response createIndex(IndexPojo indexPojo) {
    try {
      return given()
          .filter(new AllureRestAssured())
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
          .filter(new AllureRestAssured())
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

  /**
   * All indices created here have the prefix elastic-UUID
   * The _cat api can locate this data by this prefix
   */
  public static Response listAllIndices() {
    try {
      return given()
          .auth().certificate(readCertificate(), readKey())
          .contentType(ContentType.JSON)
          .auth().basic("elastic", "elk2324")
          .when()
          .get("/_cat/indices/elastic*?format=json");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * tearDown method to clean up the environment
   */
  public static void deleteAllIndices() {
    IndexPojo indexPojo = new IndexPojo();
    Response response = listAllIndices().then().extract().response();
    JsonPath jsonPath = response.jsonPath();

    List<String> indexNames = jsonPath.getList("index");

    for (String indexName : indexNames) {
      indexPojo.setName(indexName);
      deleteIndex(indexPojo);
    }
  }
}
