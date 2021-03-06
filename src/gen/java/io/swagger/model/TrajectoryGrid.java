package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class TrajectoryGrid extends MapGrid  {
  
  private Integer nbTrajectories = null;
  private List<Trajectory> data = new ArrayList<Trajectory>();

  /**
   **/
  
  @JsonProperty("nbTrajectories")
  public Integer getNbTrajectories() {
    return nbTrajectories;
  }
  public void setNbTrajectories(Integer nbTrajectories) {
    this.nbTrajectories = nbTrajectories;
  }

  /**
   **/
  
  @JsonProperty("data")
  public List<Trajectory> getData() {
    return data;
  }
  public void setData(List<Trajectory> data) {
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
    TrajectoryGrid trajectoryGrid = (TrajectoryGrid) o;
    return Objects.equals(nbTrajectories, trajectoryGrid.nbTrajectories) &&
        Objects.equals(data, trajectoryGrid.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nbTrajectories, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrajectoryGrid {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    nbTrajectories: ").append(toIndentedString(nbTrajectories)).append("\n");
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

