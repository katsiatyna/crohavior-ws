package io.swagger.api.impl;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.upc.bip.batch.HBaseUtils;
import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.TrajectoryGridCollection;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.swagger.api.NotFoundException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class TrajectoriesApiServiceImpl extends TrajectoriesApiService {
    public static final String TABLE_NAME = "datamining";

    @Override
    public Response getTrajectoriesByParameters(Integer projectId, String batchId, SecurityContext securityContext, UriInfo uri)
            throws NotFoundException {
        TrajectoryGrid trajectoryGrid = new TrajectoryGrid();
        List<String> values = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            values = HBaseUtils.getRecordRangeValues(TABLE_NAME, batchId, batchId);
            System.out.println(values);
            if(values == null || values.size() == 0){
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Batch does not exist OR the parameter is wrong!")).build();
            }
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String[] idSplit = batchId.split("e");
            System.out.println("Split 1: " + idSplit);
            String[] startTimeStr = idSplit[0].split("ss");
            System.out.println("Split 2: " + startTimeStr);
            Date startTime = sdf.parse(startTimeStr[1]);
            Date endTime = sdf.parse(idSplit[1]);
            trajectoryGrid.setStartTimestamp(startTime.getTime());
            trajectoryGrid.setEndTimestamp(endTime.getTime());
            TrajectoryGrid obj = mapper.readValue(values.get(0), TrajectoryGrid.class);
            trajectoryGrid.setTrajectories(obj.getTrajectories());
            trajectoryGrid.setNbTrajectories(obj.getTrajectories().size());
            trajectoryGrid.setProjectId(projectId);
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation trajectoryGridRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                    path(TrajectoriesApi.class).
                    path(TrajectoriesApi.class, "getTrajectoriesByParameters").
                    queryParam("startTime", startTime).
                    queryParam("endTime", endTime).
                    build(projectId)).withBean(trajectoryGrid);
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps5s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 5).
                    build(projectId));
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps10s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 10).
                    build(projectId));
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps15s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 15).
                    build(projectId));

            return Response.ok().entity(trajectoryGridRepr.toString(RepresentationFactory.HAL_JSON)).build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    @Override
    public Response getTrajectoriesBatches(Integer projectId, SecurityContext securityContext, UriInfo uri) throws NotFoundException {
        List<String> values = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            values = HBaseUtils.getAllRowIDs(TABLE_NAME);
            System.out.println(values);
            Batches batches  = new Batches();
            batches.setBatches(values);
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation trajectoryGridRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                    path(TrajectoriesApi.class).
                    path(TrajectoriesApi.class, "getTrajectoriesBatches").
                    build(projectId)).withBean(batches);
            for(String val: values) {
                trajectoryGridRepr = trajectoryGridRepr.withLink("trajectories", uri.getBaseUriBuilder().
                        path(TrajectoriesApi.class).
                        path(TrajectoriesApi.class, "getTrajectoriesByParameters").
                        queryParam("batchId", val).
                        build(projectId));
            }

            return Response.ok().entity(trajectoryGridRepr.toString(RepresentationFactory.HAL_JSON)).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }
}

