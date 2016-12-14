package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.ProjectsApiService;
import io.swagger.api.factories.ProjectsApiServiceFactory;

import io.swagger.model.Project;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

@Path("/projects")


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class ProjectsApi  {
   private final ProjectsApiService delegate = ProjectsApiServiceFactory.getProjectsApi();

    @POST
    
    
    @Produces({ "application/hal+json" })
    public Response createProject( Project body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createProject(body,securityContext);
    }
    @DELETE
    @Path("/{projectId}")
    
    @Produces({ "application/hal+json" })
    public Response deleteProject( @PathParam("projectId") Integer projectId,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteProject(projectId,securityContext);
    }
    @GET
    @Path("/{projectId}")
    
    @Produces({ "application/hal+json" })
    public Response getProjectById( @PathParam("projectId") Integer projectId,@Context SecurityContext securityContext,
                                    @Context UriInfo uri)
    throws NotFoundException {
        return delegate.getProjectById(projectId,securityContext, uri);
    }
    @PUT
    @Path("/{projectId}")
    
    @Produces({ "application/hal+json" })
    public Response updateProject( @PathParam("projectId") Integer projectId, Project body,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.updateProject(projectId,body,securityContext);
    }
}
