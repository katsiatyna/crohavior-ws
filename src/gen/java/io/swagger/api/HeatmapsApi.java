package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.HeatmapsApiService;
import io.swagger.api.factories.HeatmapsApiServiceFactory;

import io.swagger.model.HeatmapGridCollection;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

@Path("/heatmaps")


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class HeatmapsApi  {
    private final HeatmapsApiService delegate = HeatmapsApiServiceFactory.getHeatmapsApi();

    @GET
    @Path("/{projectId}")

    @Produces({ "application/hal+json" })
    public Response getHeatmapsByParameters(@PathParam("projectId") Integer projectId, @QueryParam("startTime") Long startTime,
                                            @QueryParam("endTime") Long endTime, @QueryParam("interval") Integer interval,
                                            @QueryParam("pageNmb") Long pageNmb,
                                            @Context SecurityContext securityContext, @Context UriInfo uri)
            throws NotFoundException {
        return delegate.getHeatmapsByParameters(projectId,startTime, endTime, interval, pageNmb, securityContext, uri);
    }
}