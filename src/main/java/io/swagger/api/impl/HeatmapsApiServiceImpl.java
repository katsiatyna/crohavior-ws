package io.swagger.api.impl;

import edu.upc.bip.batch.HBaseUtils;
import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.HeatmapGridCollection;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import io.swagger.api.NotFoundException;
import org.apache.commons.collections.ArrayStack;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class HeatmapsApiServiceImpl extends HeatmapsApiService {

    public static final String TABLE_NAME = "heatmap";
      @Override
      public Response getHeatmapsByParameters(Long projectId,Long startTime,Long endTime, Integer interval,
                                              Long pageNmb,
                                              SecurityContext securityContext)
      throws NotFoundException {
          List<String> values = new ArrayList<>();
          List<Object> objValues = new ArrayList<>();
          ObjectMapper mapper = new ObjectMapper();
          mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
          Map<String, Object> resMap = new HashMap<>();
          resMap.put("startTime", startTime);
          resMap.put("endTime", endTime);
          resMap.put("intervalSec", 5);
          try {
              if(pageNmb == null){
                  pageNmb = 1l;
              }

              resMap.put("page", pageNmb);
              Long diff = endTime - startTime;
              Long diffMin = TimeUnit.MILLISECONDS.toMinutes(diff);
              System.out.println("Minutes: " + diffMin);
              Long pages = ((TimeUnit.MILLISECONDS.toSeconds(diff))%60 == 0) ? diffMin : diffMin + 1;
              System.out.println("Pages: " + pages);
              Long newStartTime, newEndTime;
              newStartTime = startTime + (pageNmb - 1)*1000*60;
              if(pageNmb != pages){
                  newEndTime = newStartTime + 60*1000;
              }else{
                  newEndTime = endTime;
              }

              DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
              String startDateStr =  interval + "s" + sdf.format(new Date(newStartTime));
              String endDateStr =  interval + "s" + sdf.format(new Date(newEndTime));
              System.out.println("Start: " + startDateStr + ", End: " + endDateStr);
              values = HBaseUtils.getRecordRangeValues(TABLE_NAME, startDateStr, endDateStr );
              System.out.println(values.size());
              resMap.put("nbEl",values.size());
              for(String val:values){
                  objValues.add(mapper.readValue(val, Object.class));
              }
              resMap.put("elements", objValues);
              return Response.ok().entity(mapper.writeValueAsString(resMap)).build();
          } catch (IOException e) {
              e.printStackTrace();
          }
          return Response.serverError().build();
  }
}
