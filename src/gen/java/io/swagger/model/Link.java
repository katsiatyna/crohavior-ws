package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.CuriesLink;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class Link   {
  
  private CuriesLink curie = null;
  private String rel = null;
  private String href = null;
  private String title = null;
  private String type = null;
  private String deprecation = null;
  private String name = null;
  private String profile = null;
  private String hrefLang = null;
  private Boolean isTemplated = null;

  /**
   **/
  
  @JsonProperty("Curie")
  public CuriesLink getCurie() {
    return curie;
  }
  public void setCurie(CuriesLink curie) {
    this.curie = curie;
  }

  /**
   **/
  
  @JsonProperty("Rel")
  public String getRel() {
    return rel;
  }
  public void setRel(String rel) {
    this.rel = rel;
  }

  /**
   **/
  
  @JsonProperty("Href")
  public String getHref() {
    return href;
  }
  public void setHref(String href) {
    this.href = href;
  }

  /**
   **/
  
  @JsonProperty("Title")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   **/
  
  @JsonProperty("Type")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   **/
  
  @JsonProperty("Deprecation")
  public String getDeprecation() {
    return deprecation;
  }
  public void setDeprecation(String deprecation) {
    this.deprecation = deprecation;
  }

  /**
   **/
  
  @JsonProperty("Name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  
  @JsonProperty("Profile")
  public String getProfile() {
    return profile;
  }
  public void setProfile(String profile) {
    this.profile = profile;
  }

  /**
   **/
  
  @JsonProperty("HrefLang")
  public String getHrefLang() {
    return hrefLang;
  }
  public void setHrefLang(String hrefLang) {
    this.hrefLang = hrefLang;
  }

  /**
   **/
  
  @JsonProperty("IsTemplated")
  public Boolean getIsTemplated() {
    return isTemplated;
  }
  public void setIsTemplated(Boolean isTemplated) {
    this.isTemplated = isTemplated;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return Objects.equals(curie, link.curie) &&
        Objects.equals(rel, link.rel) &&
        Objects.equals(href, link.href) &&
        Objects.equals(title, link.title) &&
        Objects.equals(type, link.type) &&
        Objects.equals(deprecation, link.deprecation) &&
        Objects.equals(name, link.name) &&
        Objects.equals(profile, link.profile) &&
        Objects.equals(hrefLang, link.hrefLang) &&
        Objects.equals(isTemplated, link.isTemplated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(curie, rel, href, title, type, deprecation, name, profile, hrefLang, isTemplated);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Link {\n");
    
    sb.append("    curie: ").append(toIndentedString(curie)).append("\n");
    sb.append("    rel: ").append(toIndentedString(rel)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    deprecation: ").append(toIndentedString(deprecation)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    profile: ").append(toIndentedString(profile)).append("\n");
    sb.append("    hrefLang: ").append(toIndentedString(hrefLang)).append("\n");
    sb.append("    isTemplated: ").append(toIndentedString(isTemplated)).append("\n");
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

