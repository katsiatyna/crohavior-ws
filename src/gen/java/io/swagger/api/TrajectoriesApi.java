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

@Path("/trajectories")


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class TrajectoriesApi  {
   private final TrajectoriesApiService delegate = TrajectoriesApiServiceFactory.getTrajectoriesApi();

    @GET
    @Path("/{projectId}")
    
    @Produces({ "application/hal+json" })
    public Response getTrajectoriesByParameters( @PathParam("projectId") Long projectId, @QueryParam("startTime") Integer startTime, @QueryParam("endTime") Integer endTime,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getTrajectoriesByParameters(projectId,startTime,endTime,securityContext);
    }
}
