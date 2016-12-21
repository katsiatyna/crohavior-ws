package io.swagger.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;
import java.util.ArrayList;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class Trajectory   {
  
  private List<TrajectoryPoint> items = new ArrayList<TrajectoryPoint>();
  private Integer frequency = null;


  @JsonProperty("frequency")
  public Integer getFrequency() {
    return frequency;
  }

  public void setFrequency(Integer frequency) {
    this.frequency = frequency;
  }


  @JsonProperty("items")
  public List<TrajectoryPoint> getItems() {
    return items;
  }
  public void setItems(List<TrajectoryPoint> items) {
    this.items = items;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trajectory trajectory = (Trajectory) o;
    return         Objects.equals(items, trajectory.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Trajectory {\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

