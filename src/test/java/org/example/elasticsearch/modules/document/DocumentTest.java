package org.example.elasticsearch.modules.document;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.example.elasticsearch.dataFactory.DocumentDataFactory;
import org.example.elasticsearch.dataFactory.IndexDataFactory;
import org.example.elasticsearch.modules.index.IndexCommands;
import org.example.elasticsearch.pojo.DocumentPojo;
import org.example.elasticsearch.pojo.IndexPojo;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * You can index a new JSON document with the _doc or _create resource. Using _create guarantees that the document is
 * only indexed if it does not already exist. To update an existing document, you must use the _doc resource.
 * <p>
 * To create a new document:
 * - Use POST /<target>/_doc/ to index the document, allowing automatic ID assignment.
 * - Use POST /<target>/_create/<_id> to index the document only if it does not already exist, specifying a unique ID.
 * <p>
 * To update an existing document:
 * - Use PUT /<target>/_doc/<_id> to update the document by specifying its ID.
 * - Note: The _create endpoint does not support updates. If the document already exists, a conflict error will be returned.
 *
 * @see https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html#docs-index-api-desc
 */
public class DocumentTest {
  private static IndexPojo indexPojo;

  @BeforeAll
  public static void beforeAll() {
    baseURI = "https://localhost:9200";
    useRelaxedHTTPSValidation();

    // We create a index to share with all Document`s test.
    indexPojo = IndexDataFactory.createIndexWithSampleMappings();
    Response response = IndexCommands.createIndexWithMappings(indexPojo);
    response.then().statusCode(200);
  }

  @BeforeEach
  public void setUp() {
    replaceFiltersWith(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @AfterAll
  public static void tearDown() {
    IndexCommands.deleteAllIndices();
  }

  @Test
  @DisplayName("Deve criar um novo documento utilizando o recurso _doc com sucesso")
  public void testShouldCreateDocumentWithDocRecurse() {
    DocumentPojo documentPojo = DocumentDataFactory.createDocument();
    Response response = DocumentCommands.createSampleDocumentWithDocResource(documentPojo, indexPojo.getName());
    response.then()
      .statusCode(201)
      .body("_index", equalTo(indexPojo.getName()))
      .body("_id", notNullValue())
      .body("_version", equalTo(1))
      .body("result", equalTo("created"));
  }

  @Test
  @DisplayName("Deve atualizar um documento existente utilizando o recurso _doc com sucesso")
  public void testShouldUpdateDocumentWithDocRecurse() {
    DocumentPojo documentPojo = DocumentDataFactory.createDocument();
    Response response = DocumentCommands.createSampleDocumentWithDocResource(documentPojo, indexPojo.getName());
    String documentId = response.then().statusCode(201).extract().jsonPath().get("_id");

    Response updateResponse = DocumentCommands.updateSampleDocumentWithDocResource(documentPojo, indexPojo.getName(), documentId);
    updateResponse.then()
      .body("_index", equalTo(indexPojo.getName()))
      .body("_id", notNullValue())
      .body("_version", equalTo(2))
      .body("result", equalTo("updated"));
  }
}
