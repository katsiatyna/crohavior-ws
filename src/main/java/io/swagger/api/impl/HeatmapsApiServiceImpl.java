package io.swagger.api.impl;

import edu.upc.bip.batch.HBaseUtils;
import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.HeatmapGridCollection;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class HeatmapsApiServiceImpl extends HeatmapsApiService {

    public static final String TABLE_NAME = "heatmap";
      @Override
      public Response getHeatmapsByParameters(Long projectId,Long startTime,Long endTime, Integer interval,
                                              Integer pageNmb,
                                              SecurityContext securityContext)
      throws NotFoundException {
          List<String> values = new ArrayList<>();
          try {
              DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
              String startDateStr =  interval + "s" + sdf.format(new Date(startTime));
              String endDateStr =  interval + "s" + sdf.format(new Date(endTime));
              System.out.println("Start: " + startDateStr + ", End: " + endDateStr);
              values = HBaseUtils.getRecordRangeValues(TABLE_NAME, startDateStr, endDateStr );
              System.out.println(values.size());
          } catch (IOException e) {
              e.printStackTrace();
          }
          return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, values.get(0))).build();
  }
}
