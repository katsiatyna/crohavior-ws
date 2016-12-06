package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.EmbeddedResource;
import io.swagger.model.Link;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class MapGridCollection   {
  
  private Long nbElements = null;
  private Integer startTimestamp = null;
  private Integer endTimestamp = null;
  private Integer intervalMs = null;
  private List<Link> links = new ArrayList<Link>();
  private List<EmbeddedResource> embedded = new ArrayList<EmbeddedResource>();

  /**
   **/
  
  @JsonProperty("nbElements")
  public Long getNbElements() {
    return nbElements;
  }
  public void setNbElements(Long nbElements) {
    this.nbElements = nbElements;
  }

  /**
   **/
  
  @JsonProperty("startTimestamp")
  public Integer getStartTimestamp() {
    return startTimestamp;
  }
  public void setStartTimestamp(Integer startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  /**
   **/
  
  @JsonProperty("endTimestamp")
  public Integer getEndTimestamp() {
    return endTimestamp;
  }
  public void setEndTimestamp(Integer endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  /**
   **/
  
  @JsonProperty("intervalMs")
  public Integer getIntervalMs() {
    return intervalMs;
  }
  public void setIntervalMs(Integer intervalMs) {
    this.intervalMs = intervalMs;
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
    MapGridCollection mapGridCollection = (MapGridCollection) o;
    return Objects.equals(nbElements, mapGridCollection.nbElements) &&
        Objects.equals(startTimestamp, mapGridCollection.startTimestamp) &&
        Objects.equals(endTimestamp, mapGridCollection.endTimestamp) &&
        Objects.equals(intervalMs, mapGridCollection.intervalMs) &&
        Objects.equals(links, mapGridCollection.links) &&
        Objects.equals(embedded, mapGridCollection.embedded);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nbElements, startTimestamp, endTimestamp, intervalMs, links, embedded);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MapGridCollection {\n");
    
    sb.append("    nbElements: ").append(toIndentedString(nbElements)).append("\n");
    sb.append("    startTimestamp: ").append(toIndentedString(startTimestamp)).append("\n");
    sb.append("    endTimestamp: ").append(toIndentedString(endTimestamp)).append("\n");
    sb.append("    intervalMs: ").append(toIndentedString(intervalMs)).append("\n");
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

