package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.TrajectoryPoint;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class Trajectory   {
  
  private Long nmPeople = null;
  private List<TrajectoryPoint> points = new ArrayList<TrajectoryPoint>();

  /**
   **/
  
  @JsonProperty("nmPeople")
  public Long getNmPeople() {
    return nmPeople;
  }
  public void setNmPeople(Long nmPeople) {
    this.nmPeople = nmPeople;
  }

  /**
   **/
  
  @JsonProperty("points")
  public List<TrajectoryPoint> getPoints() {
    return points;
  }
  public void setPoints(List<TrajectoryPoint> points) {
    this.points = points;
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
    return Objects.equals(nmPeople, trajectory.nmPeople) &&
        Objects.equals(points, trajectory.points);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nmPeople, points);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Trajectory {\n");
    
    sb.append("    nmPeople: ").append(toIndentedString(nmPeople)).append("\n");
    sb.append("    points: ").append(toIndentedString(points)).append("\n");
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

