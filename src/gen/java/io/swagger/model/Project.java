package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.EmbeddedResource;
import io.swagger.model.Link;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class Project   {
  
  private Long id = null;
  private String projectName = null;
  private Double minLatitude = null;
  private Double minLongitude = null;
  private Double maxLatitude = null;
  private Double maxLongitude = null;
  private Long userId = null;
  private List<Link> links = new ArrayList<Link>();
  private List<EmbeddedResource> embedded = new ArrayList<EmbeddedResource>();

  /**
   **/
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  
  @JsonProperty("projectName")
  public String getProjectName() {
    return projectName;
  }
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  /**
   **/
  
  @JsonProperty("minLatitude")
  public Double getMinLatitude() {
    return minLatitude;
  }
  public void setMinLatitude(Double minLatitude) {
    this.minLatitude = minLatitude;
  }

  /**
   **/
  
  @JsonProperty("minLongitude")
  public Double getMinLongitude() {
    return minLongitude;
  }
  public void setMinLongitude(Double minLongitude) {
    this.minLongitude = minLongitude;
  }

  /**
   **/
  
  @JsonProperty("maxLatitude")
  public Double getMaxLatitude() {
    return maxLatitude;
  }
  public void setMaxLatitude(Double maxLatitude) {
    this.maxLatitude = maxLatitude;
  }

  /**
   **/
  
  @JsonProperty("maxLongitude")
  public Double getMaxLongitude() {
    return maxLongitude;
  }
  public void setMaxLongitude(Double maxLongitude) {
    this.maxLongitude = maxLongitude;
  }

  /**
   **/
  
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   **/
  
  @JsonProperty("_links")
  public List<Link> getLinks() {
    return links;
  }
  public void setLinks(List<Link> links) {
    this.links = links;
  }

  /**
   **/
  
  @JsonProperty("_embedded")
  public List<EmbeddedResource> getEmbedded() {
    return embedded;
  }
  public void setEmbedded(List<EmbeddedResource> embedded) {
    this.embedded = embedded;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Project project = (Project) o;
    return Objects.equals(id, project.id) &&
        Objects.equals(projectName, project.projectName) &&
        Objects.equals(minLatitude, project.minLatitude) &&
        Objects.equals(minLongitude, project.minLongitude) &&
        Objects.equals(maxLatitude, project.maxLatitude) &&
        Objects.equals(maxLongitude, project.maxLongitude) &&
        Objects.equals(userId, project.userId) &&
        Objects.equals(links, project.links) &&
        Objects.equals(embedded, project.embedded);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, projectName, minLatitude, minLongitude, maxLatitude, maxLongitude, userId, links, embedded);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Project {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
    sb.append("    minLatitude: ").append(toIndentedString(minLatitude)).append("\n");
    sb.append("    minLongitude: ").append(toIndentedString(minLongitude)).append("\n");
    sb.append("    maxLatitude: ").append(toIndentedString(maxLatitude)).append("\n");
    sb.append("    maxLongitude: ").append(toIndentedString(maxLongitude)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    embedded: ").append(toIndentedString(embedded)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

