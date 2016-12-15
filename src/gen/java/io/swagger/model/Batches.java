package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class Batches  {
  
  private List<String> batches = new ArrayList<>();


  /**
   **/
  
  @JsonProperty("batches")
  public List<String> getBatches() {
    return batches;
  }
  public void setBatches(List<String> batches) {
    this.batches = batches;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Batches trajectoryGrid = (Batches) o;
    return
        Objects.equals(batches, trajectoryGrid.batches);
  }

  @Override
  public int hashCode() {
    return Objects.hash(batches);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Batches {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    batches: ").append(toIndentedString(batches)).append("\n");
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

