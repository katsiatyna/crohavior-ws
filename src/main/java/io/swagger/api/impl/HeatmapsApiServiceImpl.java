package io.swagger.api.impl;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.upc.bip.batch.MongoUtils;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.HeatmapsApi;
import io.swagger.api.HeatmapsApiService;
import io.swagger.api.NotFoundException;
import io.swagger.api.dal.Utils;
import io.swagger.model.HeatmapGrid;
import io.swagger.model.HeatmapGridCollection;
import io.swagger.model.User;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class HeatmapsApiServiceImpl extends HeatmapsApiService {

    public static final String TABLE_NAME = "heatmap";

    @Override
    public Response getHeatmapsByParameters(Integer projectId, Long startTime, Long endTime, Integer interval,
                                            Long pageNmb,
                                            String apiKey, UriInfo uri)
            throws NotFoundException {

        HeatmapGridCollection heatmapGridCollection = new HeatmapGridCollection();
        List<HeatmapGrid> elements = new ArrayList<>();
        List<String> values = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        heatmapGridCollection.setStartTime(startTime);
        heatmapGridCollection.setEndTime(endTime);
        heatmapGridCollection.setIntervalSec(interval);
        try {
            int auth = Utils.checkApiKeyAndRole(apiKey, User.UserRoleEnum.ADMIN);
            if (auth == 1) {
                return Response.status(Response.Status.FORBIDDEN).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Api key does not exist!")).build();
            }
            if (auth == 3) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Parameter api_key has to be provided")).build();
            }
            if (pageNmb == null) {
                pageNmb = 1l;
            }

            heatmapGridCollection.setPage(pageNmb);
            Long diff = endTime - startTime;
            Long diffMin = TimeUnit.MILLISECONDS.toMinutes(diff);
            System.out.println("Minutes: " + diffMin);
            Long pages = ((TimeUnit.MILLISECONDS.toSeconds(diff)) % 60 == 0) ? diffMin : diffMin + 1; //calcPages
            System.out.println("Pages: " + pages);
            Long newStartTime, newEndTime;
            System.out.println("PageNmb=" + pageNmb + ", pages=" + pages + ", Comparison !=" + (pageNmb != pages) +
                    ", Comparison equals = " + !pageNmb.equals(pages));
            if (pageNmb <= pages) {
                newStartTime = startTime + (pageNmb - 1) * 1000 * 60;
                if (!pageNmb.equals(pages)) {

                    newEndTime = newStartTime + 60 * 1000;
                } else {
                    newEndTime = endTime;
                }
            } else {
                return Response.noContent().build();
            }

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String startDateStr = interval + "s" + sdf.format(new Date(newStartTime));
            String endDateStr = interval + "s" + sdf.format(new Date(newEndTime));
            System.out.println("Start: " + startDateStr + ", End: " + endDateStr);
            values = MongoUtils.getRecordRangeValues(TABLE_NAME, startDateStr, endDateStr);
            System.out.println(values.size());
            heatmapGridCollection.setNbEl(values.size());
            for (String val : values) {
                HeatmapGrid obj = mapper.readValue(val, HeatmapGrid.class);
                obj.setProjectId(projectId);
                obj.setIntervalMs(interval);
                elements.add(obj);
            }

            heatmapGridCollection.setElements(elements);

            RepresentationFactory factory = new StandardRepresentationFactory();
            //"/heatmaps/123?interval=5&startTime=1224726940000&endTime=1224726960000&pageNmb=2"
            Representation heatmapCollectionRepr = factory.newRepresentation(uri.getBaseUriBuilder().
                    path(HeatmapsApi.class).
                    path(HeatmapsApi.class, "getHeatmapsByParameters").
                    queryParam("startTime", startTime).
                    queryParam("endTime", endTime).
                    queryParam("interval", interval).
                    queryParam("pageNmb", pageNmb).
                    queryParam("api_key", apiKey).
                    build(projectId)).withBean(heatmapGridCollection);
            if (pageNmb < pages) {
                heatmapCollectionRepr = heatmapCollectionRepr.withLink("next", uri.getBaseUriBuilder().
                        path(HeatmapsApi.class).
                        path(HeatmapsApi.class, "getHeatmapsByParameters").
                        queryParam("startTime", startTime).
                        queryParam("endTime", endTime).
                        queryParam("interval", interval).
                        queryParam("pageNmb", pageNmb + 1).
                        queryParam("api_key", apiKey).
                        build(projectId));
            }
            if (pageNmb > 1) {
                heatmapCollectionRepr = heatmapCollectionRepr.withLink("prev", uri.getBaseUriBuilder().
                        path(HeatmapsApi.class).
                        path(HeatmapsApi.class, "getHeatmapsByParameters").
                        queryParam("startTime", startTime).
                        queryParam("endTime", endTime).
                        queryParam("interval", interval).
                        queryParam("pageNmb", pageNmb - 1).
                        queryParam("api_key", apiKey).
                        build(projectId));
            }
            return Response.ok().entity(heatmapCollectionRepr.toString(RepresentationFactory.HAL_JSON)).build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }
}