package org.example.elasticsearch.pojo;

public class IndexPojo {
  private String name;
  private MappingsPojo mappings;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MappingsPojo getMappings() {
    return mappings;
  }

  public void setMappings(MappingsPojo mappings) {
    this.mappings = mappings;
  }

  @Override
  public String toString() {
    return "IndexPojo{" +
      "name='" + name + '\'' +
      ", mappings=" + mappings +
      '}';
  }
}
