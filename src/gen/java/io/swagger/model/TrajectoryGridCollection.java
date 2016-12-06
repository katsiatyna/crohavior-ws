package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.EmbeddedResource;
import io.swagger.model.Link;
import io.swagger.model.MapGridCollection;
import io.swagger.model.TrajectoryGrid;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class TrajectoryGridCollection extends MapGridCollection  {
  
  private List<TrajectoryGrid> elements = new ArrayList<TrajectoryGrid>();

  /**
   **/
  
  @JsonProperty("elements")
  public List<TrajectoryGrid> getElements() {
    return elements;
  }
  public void setElements(List<TrajectoryGrid> elements) {
    this.elements = elements;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrajectoryGridCollection trajectoryGridCollection = (TrajectoryGridCollection) o;
    return Objects.equals(elements, trajectoryGridCollection.elements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(elements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrajectoryGridCollection {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    elements: ").append(toIndentedString(elements)).append("\n");
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

