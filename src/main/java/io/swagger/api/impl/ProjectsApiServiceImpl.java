package io.swagger.api.impl;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import io.swagger.api.*;
import io.swagger.api.dal.ProjectDao;
import io.swagger.model.*;


import io.swagger.model.Project;

import java.sql.SQLException;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import static org.apache.hadoop.hbase.Version.user;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class ProjectsApiServiceImpl extends ProjectsApiService {
    private RepresentationFactory factory = new StandardRepresentationFactory();
      @Override
      public Response createProject(Project body,SecurityContext securityContext)
      throws NotFoundException {
          try{
              if(body == null){
                  return Response.status(Response.Status.BAD_REQUEST).
                          entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "User update object is not provided!")).
                          build();
              }
              if(body.getId() == null || body.getProjectName() == null || body.getMaxLatitude() == null
                      || body.getMaxLongitude() == null || body.getMinLatitude() == null || body.getMinLongitude() == null
                      || body.getUserId() == null){
                  return Response.status(Response.Status.BAD_REQUEST).
                          entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "ALL fields should be set!")).
                          build();
              }
              String message = ProjectDao.createProject(body);
              if(!message.equals("ok")){
                  return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, message )).build();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return Response.ok().build();
  }
      @Override
      public Response deleteProject(Integer projectId,SecurityContext securityContext)
      throws NotFoundException {
          try {
              if(!ProjectDao.deleteProjectById(projectId)){
                  return Response.status(Response.Status.NOT_FOUND).build();
              }
          } catch (SQLException e) {
              e.printStackTrace();
              return Response.serverError().build();
          }
          return Response.ok().build();
  }
      @Override
      public Response getProjectById(Integer projectId, SecurityContext securityContext, UriInfo uri)
      throws NotFoundException {
          Project project = null;
          try {
              project = ProjectDao.getProjectById(projectId);
              if(project == null){
                  return Response.status(Response.Status.NOT_FOUND).build();
              }
          } catch (SQLException e) {
              e.printStackTrace();
              return Response.serverError().build();
          }
          Representation ProjectRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                  path(ProjectsApi.class).
                  path(ProjectsApi.class, "getProjectById").
                  build(projectId)).withBean(project);
          ProjectRepr = ProjectRepr.withLink("PUT", uri.getBaseUriBuilder().
                  path(ProjectsApi.class).
                  path(ProjectsApi.class, "updateProject").
                  build(projectId).toString(), "update", "Update Project", "", "");

          ProjectRepr = ProjectRepr.withLink("DELETE", uri.getBaseUriBuilder().
                  path(ProjectsApi.class).
                  path(ProjectsApi.class, "deleteProject").

                  build(projectId).toString(), "delete", "Delete Project", "", "");

          return Response.ok().entity(ProjectRepr.toString(RepresentationFactory.HAL_JSON)).build();
  }
      @Override
      public Response updateProject(Integer projectId,Project body,SecurityContext securityContext)
      throws NotFoundException {
          try{
          if(body == null){
              return Response.status(Response.Status.BAD_REQUEST).
                      entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "User update object is not provided!")).
                      build();
          }
          Project project = ProjectDao.getProjectById(projectId);
          if(body.getId() != null && project != null && !project.getId().equals(body.getId())){
              return Response.status(Response.Status.BAD_REQUEST).
                      entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Cannot change ID!")).
                      build();
          }
          if(!ProjectDao.updateProjectById(body, projectId)){
              return Response.status(Response.Status.NOT_FOUND).
                      entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "User does not exist!")).
                      build();
          }
      } catch (SQLException e) {
        e.printStackTrace();
    }
          return Response.ok().build();
  }
}
