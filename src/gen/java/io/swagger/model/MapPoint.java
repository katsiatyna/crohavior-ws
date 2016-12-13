package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class MapPoint   {

  private Double a = null;
  private Double o = null;

  /**
   **/

  @JsonProperty("a")
  public Double getA() {
    return a;
  }
  public void setA(Double a) {
    this.a = a;
  }

  /**
   **/

  @JsonProperty("o")
  public Double getO() {
    return o;
  }
  public void setO(Double o) {
    this.o = o;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MapPoint mapPoint = (MapPoint) o;
    return Objects.equals(a, mapPoint.a) &&
            Objects.equals(this.o, mapPoint.o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, o);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MapPoint {\n");

    sb.append("    a: ").append(toIndentedString(a)).append("\n");
    sb.append("    o: ").append(toIndentedString(o)).append("\n");
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
