package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class AssociationRulesCollection extends MapGrid  {
  
  private Integer nbTrajectories = null;
  private List<AssociationRule> data = new ArrayList<>();

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
  public List<AssociationRule> getData() {
    return data;
  }
  public void setData(List<AssociationRule> data) {
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
    AssociationRulesCollection trajectoryGrid = (AssociationRulesCollection) o;
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

