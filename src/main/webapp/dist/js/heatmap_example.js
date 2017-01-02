var projectId = 789; // {Number} id of the project of logged in user
var frame = 5;
var cur_ts = 0;
var isRealTime = true;
var drp = $('#reservationtime').data('daterangepicker');
var startTime = 1224716400000;
var endTime = 1224802559000; // {Number} End date/time
var speedTimeout = null, batchTimeout = null;

//set up elasticsearch client
var client = new elasticsearch.Client({
    host: 'http://localhost:9200',
    log: 'info'
  });

//var myLatlng = new google.maps.LatLng(39.905, 116.375);
  var myLatlng = new google.maps.LatLng(39.979, 116.327);
  // map options,
  var myOptions = {
    zoom: 12,
    center: myLatlng
  };
  // standard map
  heatmapObj = new google.maps.Map(document.getElementById("world-map"), myOptions);
  // heatmap layer
  var heatmapMap = new HeatmapOverlay(heatmapObj,
    {
      // radius should be small ONLY if scaleRadius is true (or small radius is intended)
      "radius": 20,
      "maxOpacity": 1,
      // scales the radius based on map zoom
      "scaleRadius": false,
      // if set to false the heatmap uses the global maximum for colorization
      // if activated: uses the data maximum within the current map boundaries
      //   (there will always be a red spot with useLocalExtremas true)
      "useLocalExtrema": true,
      // which field name in your data represents the latitude - default "lat"
      latField: 'a',
      // which field name in your data represents the longitude - default "lng"
      lngField: 'o',
      // which field name in your data represents the data value - default "value"
      valueField: 'c'
    }
  );

  var speedData = {data:""};
  var indexName = 'heatmap';

  function updateSpeedView(){
  client.search({
    index: indexName,
    type:frame,
    size:0,
    body: {
        aggs: {
            max_ts : { "max" : { "field" : "ts" } }
        }
        // End query.
            }
  }, function (error, response) {
    // ...
    if (error){
            console.log(error.message);
        } else{
            var max_ts = response.aggregations.max_ts.value;
            console.log(max_ts);
            if(max_ts > cur_ts){
                cur_ts = max_ts;
                client.search({
                    index: indexName,
                    type:frame.toString(),
                    size:10000,
                    _source: [
                          "o", "a", "c", "ts", "rdd"
                      ],
                    q: 'ts:' +  max_ts
                  }, function (error, response) {
                    // ...
                    if (error){
                            console.log(error.message);
                        } else{
                            hits =  response['hits']['hits'].map(function(i){
                                           return i['_source'];
                                       });
                            speedData.data = hits;
                            console.log(speedData);
                        }
                  });
              }
        }
  });
  heatmapMap.setData(speedData);
  document.getElementById("ts").innerHTML='REAL-TIME: ' + cur_ts;
  }




function speedLoop () {           //  create a loop function
     speedTimeout = setTimeout(function () {    //  call a 3s setTimeout when the loop is called
        if(isRealTime){
          updateSpeedView();
            speedLoop();
      }//  ..  again which will trigger another
     }, (frame -2)*1000)
  }




var HMApi = new heatmapsApi();

doAnalysis = function(){
    if(isRealTime){
        speedLoop();
    } else{

        var res = {};
        var elements = null, elementsNext = null;
        var callback = function(error, data, response) {
          if (error) {
            console.error(error);
          } else {
            console.log('API called successfully. Returned data: ' + data);
            res = data;
            elements = res['elements'];
            //set the data for the first time
            var i = 0, iGlobal = 0;                     //  set your counter to 1
            var nextRetrieved = false;
            var nextLink = res['_links']['next'][0]['href'];
            var currentPage = res['page'];
            var pages = res['nmbPages'];
            function batchLoop () {           //  create a loop function
               batchTimeout = setTimeout(function () {
                  if(elements != null && elements[i] != null && elements[i]['data'].length != 0){//  call a 3s setTimeout when the loop is called
                        heatmapMap.setData(elements[i]);
                        //setReportTime(elements[i]);
                        document.getElementById("ts").innerHTML=iGlobal.toString() + ': ' + (elements[i]['startTimestamp'] + ' - ' + elements[i]['endTimestamp']);
                  } else {
                        //setReportTime(null);
                        document.getElementById("ts").innerHTML=iGlobal.toString() + ': ' + 'no data';
                  }
                  console.log(i);
                  i++;
                  iGlobal++;
                  if(((elements == null || i >= (elements.length / 2)) && (nextRetrieved === false) && nextLink) && !isRealTime){ //half of the array is gone
                    //read next page
                    console.log('Retrieving next page...');
                    var callbackUri = function(error, dataUri, response){
                        if (error) {
                            console.error(error);
                        } else {
                            console.log('API NEXT called successfully. Returned data: ' + dataUri);
                            elementsNext = dataUri['elements'];
                            if(dataUri['_links']['next'] == undefined || dataUri['_links']['next'] == null){
                                nextLink = undefined;
                            } else {
                                nextLink = dataUri['_links']['next'][0]['href'];
                            }
                            currentPage = dataUri['page'];
                        }
                    }
                    HMApi.getHeatmapsByParametersPage(currentPage + 1, projectId, frame, startTime, endTime, callbackUri);
                    nextRetrieved = true;
                  }
                  if (elements != null && i < elements.length && !isRealTime) {            //  if the counter < 10, call the loop function
                     batchLoop();             //  ..  again which will trigger another
                  }else {
                    if(currentPage >= pages && !isRealTime){
                        console.log('Done!');
                        document.getElementById("ts").innerHTML='DONE!';
                    } else if(!isRealTime) {
                        //set elements to new array
                        elements = elementsNext;
                        //reset elementsNext
                        elementsNext = null;
                        //reset the counter
                        i = 0;
                        //reset nextRetrieved
                        nextRetrieved = false;
                        batchLoop();
                    }
                  }
               }, (frame)*1000)
            }
            if(!isRealTime){
                batchLoop();
            }
          }
        };
        HMApi.getHeatmapsByParameters(projectId, frame, startTime, endTime, callback);
    }

}


//doAnalysis();





setIsRealTime = function(state){
if (isRealTime != state){
    isRealTime = state;
    console.log(isRealTime.toString());
    //clear both timeouts
    clearTimeouts();
    doAnalysis();
    }
}

setFrame = function(val){
    if(frame != val){
        frame = val;
    }
    clearTimeouts();
    doAnalysis();
}

setDateRange = function(startTs, endTs){
    if(startTime != startTs){
        startTime = startTs;
    }
    if(endTime != endTs){
        endTime = endTs;
    }
    clearTimeouts();
    doAnalysis();
}

clearTimeouts = function(){
    heatmapMap.setData({data:[]});
    clearTimeout(speedTimeout);
    clearTimeout(batchTimeout);
    document.getElementById("ts").innerHTML = "";
}