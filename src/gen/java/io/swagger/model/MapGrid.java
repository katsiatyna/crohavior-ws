package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class MapGrid   {
  
  private Long nmCells = null;
  private Long projectId = null;
  private Integer startTimestamp = null;
  private Integer endTimestamp = null;
  private Integer intervalMs = null;

  /**
   **/
  
  @JsonProperty("nmCells")
  public Long getNmCells() {
    return nmCells;
  }
  public void setNmCells(Long nmCells) {
    this.nmCells = nmCells;
  }

  /**
   **/
  
  @JsonProperty("projectId")
  public Long getProjectId() {
    return projectId;
  }
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MapGrid mapGrid = (MapGrid) o;
    return Objects.equals(nmCells, mapGrid.nmCells) &&
        Objects.equals(projectId, mapGrid.projectId) &&
        Objects.equals(startTimestamp, mapGrid.startTimestamp) &&
        Objects.equals(endTimestamp, mapGrid.endTimestamp) &&
        Objects.equals(intervalMs, mapGrid.intervalMs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nmCells, projectId, startTimestamp, endTimestamp, intervalMs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MapGrid {\n");
    
    sb.append("    nmCells: ").append(toIndentedString(nmCells)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    startTimestamp: ").append(toIndentedString(startTimestamp)).append("\n");
    sb.append("    endTimestamp: ").append(toIndentedString(endTimestamp)).append("\n");
    sb.append("    intervalMs: ").append(toIndentedString(intervalMs)).append("\n");
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

