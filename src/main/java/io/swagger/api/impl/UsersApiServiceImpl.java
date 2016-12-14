package io.swagger.api.impl;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.api.UsersApi;
import io.swagger.api.UsersApiService;
import io.swagger.api.dal.UserDal;
import io.swagger.model.User;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class UsersApiServiceImpl extends UsersApiService {
    private RepresentationFactory factory = new StandardRepresentationFactory();

      @Override
      public Response createUser(User body,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response deleteUser(String username,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
          try {
              if(!UserDal.deleteUserByName(username)){
                  return Response.status(Response.Status.NOT_FOUND).build();
              }
          } catch (SQLException e) {
              e.printStackTrace();
              return Response.serverError().build();
          }
          return Response.ok().build();
  }
      @Override
      public Response getUserByName(String username,SecurityContext securityContext, UriInfo uri)
      throws NotFoundException {
          User user = null;
          try {
              user = UserDal.getUserByName(username);
              if(user == null){
                  return Response.status(Response.Status.NOT_FOUND).build();
              }
          } catch (SQLException e) {
              e.printStackTrace();
              return Response.serverError().build();
          }
          Representation userRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                  path(UsersApi.class).
                  path(UsersApi.class, "getUserByName").
                  build(username)).withBean(user);
          userRepr = userRepr.withLink("PUT", uri.getBaseUriBuilder().
                  path(UsersApi.class).
                  path(UsersApi.class, "updateUser").
                  build(username).toString(), "update", "Update User", "", "");

          userRepr = userRepr.withLink("DELETE", uri.getBaseUriBuilder().
                  path(UsersApi.class).
                  path(UsersApi.class, "deleteUser").

                  build(username).toString(), "delete", "Delete User", "", "");

          return Response.ok().entity(userRepr.toString(RepresentationFactory.HAL_JSON)).build();
  }
      @Override
      public Response loginUser(String username,String password,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response logoutUser(SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
      @Override
      public Response updateUser(String username,User body,SecurityContext securityContext)
      throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
  }
}
