package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.TrajectoriesApiService;
import io.swagger.api.factories.TrajectoriesApiServiceFactory;

import io.swagger.model.TrajectoryGridCollection;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

@Path("/trajectories")


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class TrajectoriesApi  {
   private final TrajectoriesApiService delegate = TrajectoriesApiServiceFactory.getTrajectoriesApi();

    @GET
    @Path("/{projectId}")
    
    @Produces({ "application/hal+json" })
    public Response getTrajectoriesByParameters(@PathParam("projectId") Integer projectId,
                                                @QueryParam("batchId") String batchId, @QueryParam("api_key") String apiKey,
                                                @Context UriInfo uri)
    throws NotFoundException {
        return delegate.getTrajectoriesByParameters(projectId, batchId,apiKey, uri);
    }

    @GET
    @Path("/{projectId}/ar")

    @Produces({ "application/hal+json" })
    public Response getAssociationRulesByParameters(@PathParam("projectId") Integer projectId,
                                                @QueryParam("batchId") String batchId, @QueryParam("api_key") String apiKey,
                                                @Context UriInfo uri)
            throws NotFoundException {
        return delegate.getAssociationRulesByParameters(projectId, batchId,apiKey, uri);
    }

    @GET
    @Path("/batches/{projectId}")

    @Produces({ "application/hal+json" })
    public Response getTrajectoriesBatches(@PathParam("projectId") Integer projectId,
                                                @QueryParam("api_key") String apiKey,
                                                @Context UriInfo uri)
            throws NotFoundException {
        return delegate.getTrajectoriesBatches(projectId, apiKey, uri);
    }

    @GET
    @Path("/batches/{projectId}/request")

    @Produces({ "application/hal+json" })
    public Response requestBatch(@PathParam("projectId") Integer projectId,
                                           @QueryParam("api_key") String apiKey,
                                           @QueryParam("startTs") Long startTimestamp,
                                           @QueryParam("endTs") Long endTimestamp,
                                           @QueryParam("support") Double support,
                                           @QueryParam("conf") Double confidence,
                                           @Context UriInfo uri)
            throws NotFoundException {
        return delegate.requestBatch(projectId, apiKey, startTimestamp, endTimestamp, support, confidence, uri);
    }
}
