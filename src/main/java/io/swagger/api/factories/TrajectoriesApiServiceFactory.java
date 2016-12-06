package io.swagger.api.factories;

import io.swagger.api.TrajectoriesApiService;
import io.swagger.api.impl.TrajectoriesApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class TrajectoriesApiServiceFactory {

   private final static TrajectoriesApiService service = new TrajectoriesApiServiceImpl();

   public static TrajectoriesApiService getTrajectoriesApi()
   {
      return service;
   }
}
