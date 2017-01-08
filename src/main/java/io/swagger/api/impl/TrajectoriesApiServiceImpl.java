package io.swagger.api.impl;

import com.google.common.collect.Lists;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.upc.bip.batch.HBaseUtils;
import edu.upc.bip.batch.Operation;
import io.swagger.api.*;
import io.swagger.api.dal.Utils;
import io.swagger.model.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class TrajectoriesApiServiceImpl extends TrajectoriesApiService {
    public static final String TABLE_NAME = "dataminingupc";

    @Override
    public Response getTrajectoriesByParameters(Integer projectId, String batchId, String apiKey, UriInfo uri)
            throws NotFoundException {
        TrajectoryGrid trajectoryGrid = new TrajectoryGrid();
        List<String> values = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            int auth = Utils.checkApiKeyAndRole(apiKey, User.UserRoleEnum.ADMIN);
            if (auth == 1) {
                return Response.status(Response.Status.FORBIDDEN).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Api key does not exist!")).build();
            }
            if (auth == 3) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Parameter api_key has to be provided")).build();
            }
            values = HBaseUtils.getRecordRangeValues(TABLE_NAME, batchId, batchId);
            System.out.println(values);
            if (values == null || values.size() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Batch does not exist OR the parameter is wrong!")).build();
            }
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String[] idSplit = batchId.split("e");
            System.out.println("Split 1: " + idSplit);
            String[] startTimeStr = idSplit[0].split("s");
            System.out.println("Split 2: " + startTimeStr);
            Date startTime = sdf.parse(startTimeStr[1]);
            Date endTime = sdf.parse(idSplit[1]);
            trajectoryGrid.setStartTimestamp(startTime.getTime());
            trajectoryGrid.setEndTimestamp(endTime.getTime());

            Trajectory[] objArray = mapper.readValue(values.get(0), Trajectory[].class);
            List<Trajectory> obj = Arrays.asList(objArray);
            trajectoryGrid.setData(obj);
            trajectoryGrid.setNbTrajectories(obj.size());
            trajectoryGrid.setProjectId(projectId);
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation trajectoryGridRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                    path(TrajectoriesApi.class).
                    path(TrajectoriesApi.class, "getTrajectoriesByParameters").
                    queryParam("batchId", batchId).
                    queryParam("api_key", apiKey).
                    build(projectId)).withBean(trajectoryGrid);
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps5s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 5).
                    queryParam("api_key", apiKey).
                    build(projectId));
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps10s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 10).
                    queryParam("api_key", apiKey).
                    build(projectId));
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps15s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 15).
                    queryParam("api_key", apiKey).
                    build(projectId));

            return Response.ok().entity(trajectoryGridRepr.toString(RepresentationFactory.HAL_JSON)).build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    @Override
    public Response getTrajectoriesBatches(Integer projectId, String apiKey, UriInfo uri) throws NotFoundException {

        List<String> values = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            int auth = Utils.checkApiKeyAndRole(apiKey, User.UserRoleEnum.ADMIN);
            if (auth == 1) {
                return Response.status(Response.Status.FORBIDDEN).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Api key does not exist!")).build();
            }
            if (auth == 3) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Parameter api_key has to be provided")).build();
            }
            values = HBaseUtils.getAllRowIDs(TABLE_NAME);
            System.out.println(values);
            Batches batches = new Batches();
            batches.setBatches(values);
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation trajectoryGridRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                    path(TrajectoriesApi.class).
                    path(TrajectoriesApi.class, "getTrajectoriesBatches").
                    queryParam("api_key", apiKey).
                    build(projectId)).withBean(batches);
            for (String val : values) {
                trajectoryGridRepr = trajectoryGridRepr.withLink("trajectories", uri.getBaseUriBuilder().
                        path(TrajectoriesApi.class).
                        path(TrajectoriesApi.class, "getTrajectoriesByParameters").
                        queryParam("batchId", val).
                        queryParam("api_key", apiKey).
                        build(projectId));
            }

            return Response.ok().entity(trajectoryGridRepr.toString(RepresentationFactory.HAL_JSON)).build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    @Override
    public Response getAssociationRulesByParameters(Integer projectId, String batchId, String apiKey, UriInfo uri) throws NotFoundException {
        AssociationRulesCollection associationRulesCollection = new AssociationRulesCollection();
        List<String> values = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            int auth = Utils.checkApiKeyAndRole(apiKey, User.UserRoleEnum.ADMIN);
            if (auth == 1) {
                return Response.status(Response.Status.FORBIDDEN).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Api key does not exist!")).build();
            }
            if (auth == 3) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Parameter api_key has to be provided")).build();
            }
            values = HBaseUtils.getRecordRangeValues(TABLE_NAME, batchId, batchId);
            System.out.println(values);
            if (values == null || values.size() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Batch does not exist OR the parameter is wrong!")).build();
            }
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String[] idSplit = batchId.split("e");
            System.out.println("Split 1: " + idSplit);
            String[] startTimeStr = idSplit[0].split("r");
            System.out.println("Split 2: " + startTimeStr);
            Date startTime = sdf.parse(startTimeStr[1]);
            Date endTime = sdf.parse(idSplit[1]);
            associationRulesCollection.setStartTimestamp(startTime.getTime());
            associationRulesCollection.setEndTimestamp(endTime.getTime());

            AssociationRule[] objArray = mapper.readValue(values.get(0), AssociationRule[].class);
            List<AssociationRule> obj = Arrays.asList(objArray);
            associationRulesCollection.setData(obj);
            associationRulesCollection.setNbTrajectories(obj.size());
            associationRulesCollection.setProjectId(projectId);
            RepresentationFactory factory = new StandardRepresentationFactory();
            Representation trajectoryGridRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                    path(TrajectoriesApi.class).
                    path(TrajectoriesApi.class, "getAssociationRulesByParameters").
                    queryParam("batchId", batchId).
                    queryParam("api_key", apiKey).
                    build(projectId)).withBean(associationRulesCollection);
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps5s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 5).
                    queryParam("api_key", apiKey).
                    build(projectId));
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps10s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 10).
                    queryParam("api_key", apiKey).
                    build(projectId));
            trajectoryGridRepr = trajectoryGridRepr.withLink("heatmaps15s", uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime.getTime()).
                    queryParam("endTime", endTime.getTime()).
                    queryParam("interval", 15).
                    queryParam("api_key", apiKey).
                    build(projectId));

            return Response.ok().entity(trajectoryGridRepr.toString(RepresentationFactory.HAL_JSON)).build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    @Override
    public Response requestBatch(Integer projectId, String apiKey, Long startTimestamp, Long endTimestamp, Double support, Double confidence, UriInfo uri) {
        try {
            if(!Operation.addToOperationsTable(new Date(startTimestamp), new Date(endTimestamp), support, confidence)){
                return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.INFO, "DUPLICATE")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "ERROR")).build();
        }
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.INFO, "OK")).build();
    }
}

