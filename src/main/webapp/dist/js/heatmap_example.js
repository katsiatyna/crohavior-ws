var projectId = 789; // {Number} id of the project of logged in user
var frame = 5;
var cur_ts = 0;
var isRealTime = true;




//set up elasticsearch client
var client = new elasticsearch.Client({
    host: 'http://localhost:9200',
    log: 'info'
  });

var myLatlng = new google.maps.LatLng(39.905, 116.375);
  //var myLatlng = new google.maps.LatLng(39.99900555, 116.4215235);
  // map options,
  var myOptions = {
    zoom: 11,
    center: myLatlng
  };
  // standard map
  map = new google.maps.Map(document.getElementById("world-map"), myOptions);
  // heatmap layer
  heatmap = new HeatmapOverlay(map,
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
  heatmap.setData(speedData);
  document.getElementById("ts").innerHTML='REAL-TIME: ' + cur_ts;
  }




function speedLoop () {           //  create a loop function
     setTimeout(function () {    //  call a 3s setTimeout when the loop is called
        if(isRealTime){
          updateSpeedView();
            speedLoop();
      }//  ..  again which will trigger another
     }, 3000)
  }




var api = new heatmapsApi();

doAnalysis = function(){
    if(isRealTime){
        speedLoop();
    } else{

        var startTime = 1224726800000; // {Number} Start date/time

        var interval = frame;

        var endTime = 1224726960000; // {Number} End date/time

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
            function batchLoop () {           //  create a loop function
               setTimeout(function () {
                  if(elements[i]['data'].length != 0){//  call a 3s setTimeout when the loop is called
                        heatmap.setData(elements[i]);
                        document.getElementById("ts").innerHTML=iGlobal.toString() + ': ' + (startTime + frame*iGlobal*1000);
                  } else {
                        document.getElementById("ts").innerHTML=iGlobal.toString() + ': ' + (startTime + frame*iGlobal*1000) + ': no data';
                  }
                  console.log(i);
                  i++;
                  iGlobal++;
                  if(i >= (elements.length / 2) && (nextRetrieved === false) && nextLink && !isRealTime){ //half of the array is gone
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
                    api.getHeatmapsByParametersPage(currentPage + 1, projectId, interval, startTime, endTime, callbackUri);
                    nextRetrieved = true;
                  }
                  if (i < elements.length && !isRealTime) {            //  if the counter < 10, call the loop function
                     batchLoop();             //  ..  again which will trigger another
                  }else {
                    if(elementsNext == null && !isRealTime){
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
        api.getHeatmapsByParameters(projectId, interval, startTime, endTime, callback);
    }

}
doAnalysis();
setIsRealTime = function(state){
    isRealTime = state;
    console.log(isRealTime.toString());
    doAnalysis();
}