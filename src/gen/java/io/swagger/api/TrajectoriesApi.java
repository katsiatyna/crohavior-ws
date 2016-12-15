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
                                                @QueryParam("batchId") String batchId, @Context SecurityContext securityContext,
                                                @Context UriInfo uri)
    throws NotFoundException {
        return delegate.getTrajectoriesByParameters(projectId, batchId,securityContext, uri);
    }

    @GET
    @Path("/batches/{projectId}")

    @Produces({ "application/hal+json" })
    public Response getTrajectoriesBatches(@PathParam("projectId") Integer projectId,
                                                @Context SecurityContext securityContext,
                                                @Context UriInfo uri)
            throws NotFoundException {
        return delegate.getTrajectoriesBatches(projectId, securityContext, uri);
    }
}
