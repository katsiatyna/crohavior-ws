package io.swagger.api;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class RestApplication extends Application {
    HashSet<Object> singletons = new HashSet<Object>();
    Process delete, create, streaming, producer;

    public RestApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8002");
        beanConfig.setBasePath("/api");
        beanConfig.setFilterClass("io.swagger.sample.util.ApiAuthorizationFilterImpl");
        beanConfig.setResourcePackage("io.swagger.sample.resource");
        beanConfig.setScan(true);
        //init();
    }

    private void init() {
        //recreate the index for elasticsearch


        try {
            /*String command = "curl -XDELETE '10.0.2.15:9200/heatmap?pretty > /home/osboxes/crohavior-ws-clean/delete.out'";
            delete = Runtime.getRuntime().exec(command);
            // any error message?
            StreamGobbler errorGobbler = new
                    StreamGobbler(delete.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new
                    StreamGobbler(delete.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = delete.waitFor();
            //delete.waitFor();
            System.out.println("Delete done." + exitVal);
            String commandEScreate = "curl -XPUT 'localhost:9200/heatmap?pretty' -d' {    \"mappings\" : {        \"5\" : {            \"properties\" : {                \"a\" : { \"type\" : \"double\", \"include_in_all\":false,\"index\": false },                \"o\" : { \"type\" : \"double\",\"include_in_all\": false,\"index\": false },                \"c\" : { \"type\" : \"integer\",\"include_in_all\": false,\"index\": false },                \"ts\" : { \"type\" : \"long\" },\t\t\"rdd\" : { \"type\" : \"integer\" },                \"type\" : { \"type\" :\"keyword\"}            }        },\t\"10\" : {            \"properties\" : {                \"a\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\": false },                \"o\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\":false },                \"c\" : { \"type\" : \"integer\", \"include_in_all\":false,\"index\": false},                \"ts\" : { \"type\" : \"long\" },\t\t\"rdd\" : { \"type\" : \"integer\" },                \"type\" : { \"type\" :\"keyword\"}            }        },\t\"15\" : {            \"properties\" : {                \"a\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\": false },                \"o\" : { \"type\" : \"double\", \"include_in_all\": false,\"index\":false },                \"c\" : { \"type\" : \"integer\", \"include_in_all\":false,\"index\": false },                \"ts\" : { \"type\" : \"long\" },\t\t\"rdd\" : { \"type\" : \"integer\" },                \"type\" : { \"type\" :\"keyword\"}            }        }    }}' > /home/osboxes/crohavior-ws-clean/create.out";
            create = Runtime.getRuntime().exec(commandEScreate);
            create.waitFor();
            System.out.println("Create done.");*/
            String commandStreaming = "/home/osboxes/spark-2.0.1-bin-hadoop2.7/bin/spark-submit --class edu.upc.bip.streaming.StreamingDemo --master local[8] /home/osboxes/crohavior-speed/target/crohavior-speed-1.0-SNAPSHOT-jar-with-dependencies.jar > /home/osboxes/crohavior-ws-clean/spark-submit.out";
            /*streaming = Runtime.getRuntime().exec(commandStreaming);
            StreamGobbler errorGobbler = new
                    StreamGobbler(streaming.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new
                    StreamGobbler(streaming.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();*/
            String commandProducer = "java -cp /home/osboxes/kafka-producer/target/kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.mapr.examples.Producer_new > /home/osboxes/crohavior-ws-clean/producer.out";
            //producer = Runtime.getRuntime().exec(commandProducer);
            /*StreamGobbler errorGobblerP = new
                    StreamGobbler(producer.getErrorStream(), "ERROR PRODUCER");

            // any output?
            StreamGobbler outputGobblerP = new
                    StreamGobbler(producer.getInputStream(), "OUTPUT PRODUCER");
            errorGobblerP.start();
            outputGobblerP.start();*/


        } catch (Exception e) {
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

    class StreamGobbler extends Thread
    {
        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type)
        {
            this.is = is;
            this.type = type;
        }

        public void run()
        {
            try
            {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line=null;
                while ( (line = br.readLine()) != null)
                    System.out.println(type + ">" + line);
            } catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
    }
}