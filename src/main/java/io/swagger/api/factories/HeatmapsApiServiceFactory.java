package io.swagger.api.factories;

import io.swagger.api.HeatmapsApiService;
import io.swagger.api.impl.HeatmapsApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class HeatmapsApiServiceFactory {

   private final static HeatmapsApiService service = new HeatmapsApiServiceImpl();

   public static HeatmapsApiService getHeatmapsApi()
   {
      return service;
   }
}
