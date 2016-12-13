package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class HeatmapGrid extends MapGrid  {
  
  private Long minCount = null;
  private Long maxCount = null;
  private List<HeatmapPoint> data = new ArrayList<HeatmapPoint>();

  /**
   **/
  
  @JsonProperty("minCount")
  public Long getMinCount() {
    return minCount;
  }
  public void setMinCount(Long minCount) {
    this.minCount = minCount;
  }

  /**
   **/
  
  @JsonProperty("maxCount")
  public Long getMaxCount() {
    return maxCount;
  }
  public void setMaxCount(Long maxCount) {
    this.maxCount = maxCount;
  }

  /**
   **/
  
  @JsonProperty("data")
  public List<HeatmapPoint> getData() {
    return data;
  }
  public void setData(List<HeatmapPoint> data) {
    this.data = data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HeatmapGrid heatmapGrid = (HeatmapGrid) o;
    return Objects.equals(minCount, heatmapGrid.minCount) &&
        Objects.equals(maxCount, heatmapGrid.maxCount) &&
        Objects.equals(data, heatmapGrid.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minCount, maxCount, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HeatmapGrid {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    minCount: ").append(toIndentedString(minCount)).append("\n");
    sb.append("    maxCount: ").append(toIndentedString(maxCount)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

