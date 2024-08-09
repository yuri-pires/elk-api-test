package org.example.elasticsearch.pojo;

import java.util.Map;

public class PropertiesPojo {
  private Map<String, PropertyPojo> properties;

  public Map<String, PropertyPojo> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, PropertyPojo> properties) {
    this.properties = properties;
  }
}
