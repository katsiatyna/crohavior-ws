package io.swagger.api.impl;

import com.google.common.collect.FluentIterable;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.upc.bip.batch.HBaseUtils;
import io.swagger.api.*;


import io.swagger.model.HeatmapGrid;
import io.swagger.model.HeatmapGridCollection;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import io.swagger.api.NotFoundException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

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
          System.out.println(FluentIterable.class.getProtectionDomain().getCodeSource().getLocation().toString());
          HeatmapGridCollection heatmapGridCollection = new HeatmapGridCollection();
          List<HeatmapGrid> elements = new ArrayList<>();
          List<String> values = new ArrayList<>();
          ObjectMapper mapper = new ObjectMapper();
          mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
          heatmapGridCollection.setStartTime(startTime);
          heatmapGridCollection.setEndTime(endTime);
          heatmapGridCollection.setIntervalSec(interval);
          try {
              if(pageNmb == null){
                  pageNmb = 1l;
              }

              heatmapGridCollection.setPage(pageNmb);
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
              heatmapGridCollection.setNbEl(values.size());
              for(String val:values){
                  HeatmapGrid obj = mapper.readValue(val, HeatmapGrid.class);
                  elements.add(obj);
              }

              heatmapGridCollection.setElements(elements);
              RepresentationFactory factory = new StandardRepresentationFactory();
              Representation heatmapCollectionRepr = factory.newRepresentation().withBean(heatmapGridCollection)
                      .withLink("next", "http://localhost:8080/api/heatmaps/123?interval=5&startTime=1224726940000&endTime=1224726960000&pageNmb=2");
              return Response.ok().entity(heatmapCollectionRepr.toString(RepresentationFactory.HAL_JSON)).build();
          } catch (IOException e) {
              e.printStackTrace();
          }
          return Response.serverError().build();
  }
}
