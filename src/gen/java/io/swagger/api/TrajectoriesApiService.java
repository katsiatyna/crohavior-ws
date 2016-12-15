package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;


import io.swagger.model.TrajectoryGridCollection;

import java.util.List;

import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public abstract class TrajectoriesApiService {
    public abstract Response getTrajectoriesByParameters(Integer projectId, String batchId, String apiKey, UriInfo uri)
            throws NotFoundException;

    public abstract Response getTrajectoriesBatches(Integer projectId, String apiKey, UriInfo uri)
            throws NotFoundException;
}
