package io.swagger.api.factories;

import io.swagger.api.ProjectsApiService;
import io.swagger.api.impl.ProjectsApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class ProjectsApiServiceFactory {

   private final static ProjectsApiService service = new ProjectsApiServiceImpl();

   public static ProjectsApiService getProjectsApi()
   {
      return service;
   }
}
