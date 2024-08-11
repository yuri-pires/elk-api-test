package org.example.elasticsearch.dataFactory;

import org.example.elasticsearch.pojo.IndexPojo;
import org.example.elasticsearch.pojo.MappingsPojo;
import org.example.elasticsearch.pojo.PropertiesPojo;
import org.example.elasticsearch.pojo.PropertyPojo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IndexDataFactory {
  public static IndexPojo createSampleIndex() {
    IndexPojo indexPojo = new IndexPojo();
    indexPojo.setName("elastic-" + UUID.randomUUID().toString());
    return indexPojo;
  }

  public static IndexPojo createIndexWithSampleMappings() {
    IndexPojo indexPojo = new IndexPojo();
    indexPojo.setName("elastic-" + UUID.randomUUID().toString());

    PropertiesPojo propertiesPojo = new PropertiesPojo();
    Map<String, PropertyPojo> properties = new HashMap<>();
    properties.put("name", new PropertyPojo("text"));
    properties.put("age", new PropertyPojo("integer"));
    properties.put("email", new PropertyPojo("text"));
    propertiesPojo.setProperties(properties);

    MappingsPojo mappingsPojo = new MappingsPojo();
    mappingsPojo.setMappings(propertiesPojo);
    indexPojo.setMappings(mappingsPojo);

    return indexPojo;
  }
}
