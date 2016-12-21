package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class MapGridCollection   {

  private Integer nbEl = null;
  private Long startTime = null;
  private Long endTime = null;
  private Integer intervalSec = null;
  //private List<Link> links = new ArrayList<Link>();
  //private List<EmbeddedResource> embedded = new ArrayList<EmbeddedResource>();
  private Long page = 1l;
  private Long nmbPages = 1l;

  /**
   **/

  @JsonProperty("nbEl")
  public Integer getNbEl() {
    return nbEl;
  }
  public void setNbEl(Integer nbEl) {
    this.nbEl = nbEl;
  }

  /**
   **/

  @JsonProperty("startTime")
  public Long getStartTime() {
    return startTime;
  }
  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  /**
   **/

  @JsonProperty("endTime")
  public Long getEndTime() {
    return endTime;
  }
  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  /**
   **/

  @JsonProperty("intervalSec")
  public Integer getIntervalSec() {
    return intervalSec;
  }
  public void setIntervalSec(Integer intervalSec) {
    this.intervalSec = intervalSec;
  }

  /**
   **/

  /*@JsonProperty("_links")
  public List<Link> getLinks() {
    return links;
  }
  public void setLinks(List<Link> links) {
    this.links = links;
  }

  *//**
   **//*

  @JsonProperty("_embedded")
  public List<EmbeddedResource> getEmbedded() {
    return embedded;
  }
  public void setEmbedded(List<EmbeddedResource> embedded) {
    this.embedded = embedded;
  }
*/
  @JsonProperty("page")
  public Long getPage() {
    return page;
  }

  public void setPage(Long page) {
    this.page = page;
  }

  @JsonProperty("nmbPages")
  public Long getNmbPages() {
    return nmbPages;
  }

  public void setNmbPages(Long nmbPages) {
    this.nmbPages = nmbPages;
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
    return Objects.equals(nbEl, mapGridCollection.nbEl) &&
            Objects.equals(startTime, mapGridCollection.startTime) &&
            Objects.equals(endTime, mapGridCollection.endTime) &&
            Objects.equals(intervalSec, mapGridCollection.intervalSec) &&
            Objects.equals(page, mapGridCollection.page) &&
            Objects.equals(nmbPages, mapGridCollection.nmbPages);
    //Objects.equals(links, mapGridCollection.links) &&
    //Objects.equals(embedded, mapGridCollection.embedded);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nbEl, startTime, endTime, intervalSec, page, nmbPages); //, links, embedded);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MapGridCollection {\n");

    sb.append("    nbEl: ").append(toIndentedString(nbEl)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    intervalSec: ").append(toIndentedString(intervalSec)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    nmbPages: ").append(toIndentedString(nmbPages)).append("\n");
    //sb.append("    links: ").append(toIndentedString(links)).append("\n");
    //sb.append("    embedded: ").append(toIndentedString(embedded)).append("\n");
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
