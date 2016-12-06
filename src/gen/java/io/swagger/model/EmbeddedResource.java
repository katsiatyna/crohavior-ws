package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.IResource;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class EmbeddedResource   {
  
  private Boolean isSourceAnArray = null;
  private List<IResource> resources = new ArrayList<IResource>();

  /**
   **/
  
  @JsonProperty("IsSourceAnArray")
  public Boolean getIsSourceAnArray() {
    return isSourceAnArray;
  }
  public void setIsSourceAnArray(Boolean isSourceAnArray) {
    this.isSourceAnArray = isSourceAnArray;
  }

  /**
   **/
  
  @JsonProperty("Resources")
  public List<IResource> getResources() {
    return resources;
  }
  public void setResources(List<IResource> resources) {
    this.resources = resources;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmbeddedResource embeddedResource = (EmbeddedResource) o;
    return Objects.equals(isSourceAnArray, embeddedResource.isSourceAnArray) &&
        Objects.equals(resources, embeddedResource.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isSourceAnArray, resources);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmbeddedResource {\n");
    
    sb.append("    isSourceAnArray: ").append(toIndentedString(isSourceAnArray)).append("\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
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

