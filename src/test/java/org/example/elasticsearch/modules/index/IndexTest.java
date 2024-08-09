package org.example.elasticsearch.modules.index;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.example.elasticsearch.dataFactory.IndexDataFactory;
import org.example.elasticsearch.pojo.IndexPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class IndexTest {
  @BeforeEach
  public void setUp() {
    baseURI = "https://localhost:9200";
    useRelaxedHTTPSValidation();
    replaceFiltersWith(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @DisplayName("Deve criar um index com sucesso")
  @Test
  public void testShouldCreateIndex() {
    IndexPojo indexPojo = IndexDataFactory.createSampleIndex();
    Response response = IndexCommands.createIndex(indexPojo);

    response
      .then()
        .statusCode(200)
        .body("acknowledged", equalTo(true))
        .body("shards_acknowledged", equalTo(true))
        .body("index", equalTo(indexPojo.getName()));
  }

  @DisplayName("Deve criar um index com Mappings com sucesso")
  @Test
  public void testShouldCreateIndexWithMappings() {
    IndexPojo indexPojo = IndexDataFactory.createIndexWithSampleMappings();
    Response response = IndexCommands.createIndexWithMappings(indexPojo);

    response
      .then()
        .statusCode(200)
        .body("acknowledged", equalTo(true))
        .body("shards_acknowledged", equalTo(true))
        .body("index", equalTo(indexPojo.getName()));
  }

  @DisplayName("Deve deletar um index com sucesso")
  @Test
  public void testShouldDeleteIndex() {
    IndexPojo indexPojo = IndexDataFactory.createSampleIndex();
    IndexCommands.createIndex(indexPojo).then().statusCode(200);

    IndexCommands.deleteIndex(indexPojo)
      .then()
        .statusCode(200)
        .body("acknowledged", equalTo(true));
  }

  @DisplayName("Deve consultar as configurações de um index com sucesso")
  @Test
  public void testShouldGetIndexSettings() {
    IndexPojo indexPojo =IndexDataFactory.createSampleIndex();
    IndexCommands.createIndex(indexPojo).then().statusCode(200);

    IndexCommands.getIndexSettings(indexPojo)
      .then()
      .statusCode(200)
      .body(indexPojo.getName()+".settings.index.number_of_shards",equalTo("1"));
  }
}
