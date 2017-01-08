package io.swagger.api;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class RestApplication extends Application {
    HashSet<Object> singletons = new HashSet<Object>();

    public RestApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8002");
        beanConfig.setBasePath("/api");
        beanConfig.setFilterClass("io.swagger.sample.util.ApiAuthorizationFilterImpl");
        beanConfig.setResourcePackage("io.swagger.sample.resource");
        beanConfig.setScan(true);
        init();
    }

    private void init() {
        //recreate the index for elasticsearch


        try {
            String command = "curl -XDELETE '127.0.0.1 :9200/heatmap?pretty'";
            Process delete = Runtime.getRuntime().exec(command);
            String commandEScreate = "curl -XPUT 'localhost:9200/heatmap?pretty' -d' {    \"mappings\" : {        \"5\" : {            \"properties\" : {                \"a\" : { \"type\" : \"double\", \"include_in_all\":false,\"index\": false },                \"o\" : { \"type\" : \"double\",\"include_in_all\": false,\"index\": false },                \"c\" : { \"type\" : \"integer\",\"include_in_all\": false,\"index\": false },                \"ts\" : { \"type\" : \"long\" },\t\t\"rdd\" : { \"type\" : \"integer\" },                \"type\" : { \"type\" :\"keyword\"}            }        },\t\"10\" : {            \"properties\" : {                \"a\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\": false },                \"o\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\":false },                \"c\" : { \"type\" : \"integer\", \"include_in_all\":false,\"index\": false},                \"ts\" : { \"type\" : \"long\" },\t\t\"rdd\" : { \"type\" : \"integer\" },                \"type\" : { \"type\" :\"keyword\"}            }        },\t\"15\" : {            \"properties\" : {                \"a\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\": false },                \"o\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\":false },                \"c\" : { \"type\" : \"integer\", \"include_in_all\":false,\"index\": false },                \"ts\" : { \"type\" : \"long\" },\t\t\"rdd\" : { \"type\" : \"integer\" },                \"type\" : { \"type\" :\"keyword\"}            }        }    }}'";
            Process create = Runtime.getRuntime().exec(commandEScreate);
            String commandStreaming = "";
            Process streaming = Runtime.getRuntime().exec(commandStreaming);
            String commandProducer = "";
            Process producer = Runtime.getRuntime().exec(commandProducer);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<Class<?>>();

        set.add(HeatmapsApi.class);
        set.add(TrajectoriesApi.class);
        set.add(UsersApi.class);
        set.add(ProjectsApi.class);

        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return set;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}