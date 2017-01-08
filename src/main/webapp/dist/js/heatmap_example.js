var projectId = 789; // {Number} id of the project of logged in user
var frame = 5;
var cur_ts = 0;
var isRealTime = ($("[name='my-checkbox']").val() === "on");
var drp = $('#reservationtime').data('daterangepicker');
var startTime = 0;
var endTime = 0; // {Number} End date/time
var speedTimeout = null, batchTimeout = null;

//set up elasticsearch client
var client = new elasticsearch.Client({
    host: 'http://localhost:9200',
    log: 'info'
  });

//var myLatlng = new google.maps.LatLng(39.905, 116.375);
  //2.1124435
  //var myLatlng = new google.maps.LatLng(39.979, 116.327);
  var myLatlng = new google.maps.LatLng(41.388451, 2.1124435);
  // map options,
  var myOptions = {
    zoom: 17,
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
  if(speedData != null && speedData["data"] != ""){
    fillInReport(speedData['data'], cur_ts, false);
  }
  }

  resetReport = function(){
    $('#startTsStat').text('no data');
    $('#endTsStat').text('no data');
    $('#pointsStat').text('no data');
    $('#minCountStat').text('no data');
    $('#maxCountStat').text('no data');
    $('#sumPeopleStat').text('no data');
    $('#avgPeopleStat').text('no data');
  }

  fillInReport = function(data, ts, is_ts_start){
    var startTsStat = (is_ts_start) ? ts : (ts - 1000*frame);
    var endTsStat = (is_ts_start) ? (ts + 1000*frame) : ts;
    $('#startTsStat').text(moment(startTsStat, 'x').format('MM/DD/YYYY HH:mm:ss'));
    $('#endTsStat').text(moment(endTsStat, 'x').format('MM/DD/YYYY HH:mm:ss'));
    $('#pointsStat').text(data.length);
    $('#minCountStat').text(Math.min.apply(this, $.map(data, function(o){ return o.c; })));
    $('#maxCountStat').text(Math.max.apply(this, $.map(data, function(o){ return o.c; })));
    var sumPeople = 0;
    for(var i = 0; i < data.length; i++){
        sumPeople += data[i].c;
    }
    $('#sumPeopleStat').text(sumPeople);
    $('#avgPeopleStat').text(sumPeople / data.length);
  }




function speedLoop () {           //  create a loop function
     speedTimeout = setTimeout(function () {    //  call a 3s setTimeout when the loop is called
        if(isRealTime){
          updateSpeedView();
            speedLoop();
      }//  ..  again which will trigger another
     }, (5 -2)*1000)
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
                        fillInReport(elements[i]['data'], elements[i]['startTimestamp'], true);
                        //setReportTime(elements[i]);
                        //document.getElementById("ts").innerHTML=iGlobal.toString() + ': ' + (elements[i]['startTimestamp'] + ' - ' + elements[i]['endTimestamp']);
                  } else {
                        //setReportTime(null);
                        //document.getElementById("ts").innerHTML=iGlobal.toString() + ': ' + 'no data';
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
                        //document.getElementById("ts").innerHTML='DONE!';
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
        if(startTime == 0){
            startTime = $('#reservationtime').data('daterangepicker').startDate.format('x');
        }
        if(endTime == 0){
            endTime = $('#reservationtime').data('daterangepicker').endDate.format('x');
        }
        HMApi.getHeatmapsByParameters(projectId, frame, startTime, endTime, callback);
    }

}


doAnalysis();


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
    resetReport();
    //document.getElementById("ts").innerHTML = "";
}

/*
<tr>
    <td><a href="pages/examples/invoice.html">OR9842</a></td>
    <td>Call of Duty IV</td>
    <td><span class="label label-success">Shipped</span></td>
    <td>
        <div class="sparkbar" data-color="#00a65a" data-height="20">90,80,90,-70,61,-83,63</div>
    </td>
</tr>*/

