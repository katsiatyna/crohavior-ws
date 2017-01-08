var  pages = 1, pagesSP = 1;
var currentPageAR = 0, currentPageSP = 0;
var dataAR = null, dataSP = null, dataSPfiltered = null;
var hex = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',  'c', 'd', 'e', 'f'];
var pos = [];
var drpTraj = $('#reservationtimeTrajectory').data('daterangepicker');
var ARApi = new trajectoryApi();
var support = 0.05;
var conf = 0.8;
var lengthSeq = 2;
var signSeq = ">=";
var currentBatch = null;
var batches = [];
var polylinesDisplay = [];

 var trajLatlng = new google.maps.LatLng(41.388451, 2.1124435);
  // map options,
  var trajOptions = {
    zoom: 17,
    center: trajLatlng
  };
  // standard map
  trajObj = new google.maps.Map(document.getElementById("world-map-traj"), trajOptions);

  /*updateReportAR = function(){
    var assrules = "", pagingInfo = "";
    pages = Math.ceil(dataAR.length / 7);
    if(dataAR.length % 7 != 0){
        pages++;
    }
    pagingInfo += '<li id="prev_page_ar" onclick="prevPageAR();" '
     if(currentPageAR - 1 < 0){
        pagingInfo += 'disabled="true" >';
     } else {
        pagingInfo += '>';
     }

    pagingInfo += '<a href="" >&laquo;</a></li>';
    pagingInfo += '<li><a href="">'+(currentPageAR + 1)+'</a></li>';
    pagingInfo += '<li id="next_page_ar" onclick="nextPageAR();"><a href="" >&raquo;</a></li>'

    document.getElementById('reportPaging').innerHTML = pagingInfo;
    for(var i = currentPageAR*7; i < currentPageAR*7 + 7; i++){
      assrules += '<div class="panel box box-primary"><div class="box-header with-border"><h4 class="box-title">';
      assrules += '<a data-toggle="collapse" data-parent="#accordion" href="#collapse' + i + '">#' + i + '</a></h4></div>';
      assrules += '<div id="collapse'+i+'" class="panel-collapse collapse '
       if(i == currentPageAR*7) {
        assrules += 'in">';
       }else {
       assrules +=  '">';
       }
      assrules += '<div class="box-body" style="color:black">';
      assrules += JSON.stringify(dataAR[i]) + ' </div></div></div>';

    }
    //document.getElementById('accordion').innerHTML = assrules;
}*/

updateReportSP = function(data){
    var spatterns = "", pagingInfo = "";
    pagesSP = Math.ceil(polylinesDisplay.length / 10);

    pagingInfo += '<li id="prev_page_sp" class="page-item '
     if(currentPageSP - 1 < 0){
        pagingInfo += ' disabled" >';
     } else {
        pagingInfo += '">';
     }

    pagingInfo += '<a class="page-link" href="#" >&laquo;</a></li>';
    pagingInfo += '<li id="cur_page_sp"><a class="page-link" href="#">'+(currentPageSP + 1)+'</a></li>';
    pagingInfo += '<li id="next_page_sp" class="page-item ';

    if(currentPageSP == (pagesSP - 1)){
        pagingInfo += ' disabled">';
    } else {
        pagingInfo += '">'
    }

    pagingInfo += '<a href="#" class="page-link">&raquo;</a></li>';
    document.getElementById('reportPaging').innerHTML = pagingInfo;
    for(var i = currentPageSP*10; i < currentPageSP*10 + 10; i++){
      if(data[i] == null){
        continue;
      }
      spatterns += '<li '
      if(polylinesDisplay[i].displayed){
        spatterns += '>';
      } else {
        spatterns += 'class="done">';
      }

      spatterns += '<span class="handle"><i class="fa fa-ellipsis-v"></i><i class="fa fa-ellipsis-v"></i></span>';
      spatterns += '<input style="width: 22px;height: 22px;" type="checkbox" value="'+ i +'" name="iCheckBox"';
      if(polylinesDisplay[i].displayed){
        spatterns += ' checked>';
      } else {
        spatterns += '>';
      }
      spatterns += '<small class="label label-warning">'+ JSON.stringify(data[i].frequency)+'</small>';

      var long = (data[i].items.length > 2);
      var seq = "";
      for(var j = 0; j < (long ? 2 : data[i].items.length); j++){
        seq += "(" + data[i].items[j].a + "," + data[i].items[j].o + ")";
        if(j < data[i].items.length - 1){
            seq += '<i class="fa fa-long-arrow-right"></i>'
        }
      }
      var seqHidden = "";
      if(long){
        for(var j = 2; j < data[i].items.length; j++){
          seqHidden += "(" + data[i].items[j].a + "," + data[i].items[j].o + ")";
          if(j < data[i].items.length - 1){
              seqHidden += '<i class="fa fa-long-arrow-right"></i>'
          }
        }
      }
      spatterns += '<small class="text">'+ seq +'</small>';
      if(long){
          spatterns += '<a  href="#" id="moreLink'+ i +'" class="seemore">...</a>';
          spatterns += '<small class="text" style="display:none" id="collapseExample' + i + '" >' + seqHidden + '</small>';
      }
      spatterns += '</li>';


    }
    document.getElementById('patternsList').innerHTML = spatterns;
    $("#cur_page_sp a").click(function(e){
        e.preventDefault();

    });
    $("[name='iCheckBox']").iCheck({
        checkboxClass: 'icheckbox_flat-blue'//,
        //increaseArea: '20%'
    });

    $("#next_page_sp a").click(function(e){
        e.preventDefault();
        if(currentPageSP != (pagesSP - 1)){
            nextPageSP();
        } else {
            return false;
        }
    });

    $(".seemore").click(function(e){
        e.preventDefault();
        var id = $(this).attr('id');
        $("#collapseExample" + id.substring(8, id.length)).toggle();
    });

    $("#prev_page_sp a").click(function(e){
        e.preventDefault();
        if(currentPageSP != 0){
            prevPageSP();
        } else {
            return false;
        }
    });


    // For oncheck callback
    $('[name="iCheckBox"]').on('ifChecked', function (e) { //Do your code
        var index = $( this ).val();
        polylinesDisplay[index].poly.setMap(trajObj);
        polylinesDisplay[index].displayed = true;
        b=$( this ).parents("li").first();
        b.removeClass("done");
    });

    // For onUncheck callback
    $('[name="iCheckBox"]').on('ifUnchecked', function (e) { //Do your code
        var index = $( this ).val();
        polylinesDisplay[index].poly.setMap(null);
        polylinesDisplay[index].displayed = false;
        b=$( this ).parents("li").first();
        b.toggleClass("done");
    });

}


updateMapSPInternal = function(patterns){
    var items = [];
    for(var j = 0; j < polylinesDisplay.length; j++){
        polylinesDisplay[j].displayed = false;
        polylinesDisplay[j].poly.setMap(null);
    }
    //trajObj.clear();
    polylinesDisplay = [];
    var freqMin = Math.min.apply(this, $.map(patterns, function(o){ return o.frequency; }));
    var freqMax = Math.max.apply(this, $.map(patterns, function(o){ return o.frequency; }));
    var rangeMin = 5, rangeMax = 20;
    for(var j = 0; j < patterns.length; j++){
        var pattern = patterns[j];
        items = pattern['items'];
        /*if(items.length < 2){
            continue;
        }*/
        //range of strokes:[1,20]
        //find min and max of frequencies

        var patternCoordinates = [];
        for(var i=0; i < items.length; i++){
            patternCoordinates[i] = {'lat':items[i]['a'], 'lng':items[i]['o']};
        }
        var lineWidth = ((rangeMax-rangeMin)*(pattern.frequency - freqMin)/(freqMax - freqMin)) + rangeMin;
         var lineSymbol = {
            path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
            scale: 5
          };
        var seqPath = new google.maps.Polyline({
            path: patternCoordinates,
            geodesic: true,
            strokeColor: hexCode(),
            strokeOpacity: 0.5,
            strokeWeight: lineWidth ,
            icons: [{
              icon: lineSymbol,
              offset: '100%'

            }]
          });
          createInfoWindow(seqPath, pattern.frequency);

          seqPath.setMap(trajObj);
          polylinesDisplay[j] = {poly:seqPath ,displayed:true};
      }
      updateReportSP(patterns);
}

function createInfoWindow(poly,content) {
    var iwOptions = {
    content:"<div style='color:#000000;'>"+ content +"</div>"

    };
    var myInfoWindow = new google.maps.InfoWindow(iwOptions);
    google.maps.event.addListener(poly, 'mouseover', function(event) {
        //infowindow.content = content;
        myInfoWindow.setPosition(event.latLng);
        myInfoWindow.open(trajObj);
    });
    google.maps.event.addListener(poly, 'mouseout', function() {
        myInfoWindow.close();
    });
}

filterPatterns = function(){
    dataSPfiltered = dataSP.filter(function (el) {
      if(signSeq === ">="){
        return el.items.length >= lengthSeq;
      } else if(signSeq === "<="){
        return el.items.length <= lengthSeq;
      } else{
        return el.items.length == lengthSeq;
      }
    });
    updateMapSPInternal(dataSPfiltered);
}

updateMapSP = function(){

    if(dataSP != null){
        //var patterns = dataSP;
        var maxLength = Math.max.apply(this,$.map(dataSP, function(o){ return o.items.length; }));
        $('#lengthInput').attr({
             "max" : maxLength
        });

        var pattern = null;//{"items":[{"a":39.979,"o":116.327,"order":null,"latitude":39.979,"longitude":116.327},{"a":39.981,"o":116.327,"order":null,"latitude":39.981,"longitude":116.327},{"a":39.982,"o":116.327,"order":null,"latitude":39.982,"longitude":116.327},{"a":39.98,"o":116.327,"order":null,"latitude":39.98,"longitude":116.327}],"frequency":896};
        filterPatterns();
        //console.log(patternCoordinates);
    }
}
updateMapSP();


function hexGen() {
    for (i = 0 ; i < 6 ; i++){
        pos[i] = hex[Math.floor(Math.random() * 16)];
    };
}

function hexCode() {
    hexGen();
    var output = "#"
    for (i = 0 ; i < pos.length ; i++){
        output += pos[i];
    };
    return output;
}

var ARcallback = function(error, data, response){
    console.log(data);
    if(data == null){
        dataAR = [{"antecedent":[{"a":41.387467,"o":2.112504},{"a":41.3875037058824,"o":2.11253158823529}],"consequent":[{"a":41.3875404117647,"o":2.11255917647059}],"confidence":0.9295908658420552},{"antecedent":[{"a":41.3875771176471,"o":2.11258676470588},{"a":41.3876138235294,"o":2.11261435294118}],"consequent":[{"a":41.3876505294118,"o":2.11264194117647}],"confidence":0.9419600380589914},{"antecedent":[{"a":41.3876872352941,"o":2.11266952941176},{"a":41.3877239411765,"o":2.11269711764706}],"consequent":[{"a":41.3877606470588,"o":2.11272470588235}],"confidence":0.8049476688867745}];
    } else{
        dataAR = data['data'];
    }
    //updateReportAR();
}


nextPageAR = function(){
    if(currentPageAR + 1 < pages){
        currentPageAR++;
        //updateReportAR();
    }
}

prevPageAR = function(){
    if(currentPageAR - 1 >= 0){
        currentPageAR--;
        //updateReportAR();
    }
}

nextPageSP = function(){
    if(currentPageSP + 1 < pagesSP){
        currentPageSP++;
        updateReportSP(dataSPfiltered);
    }
}

prevPageSP = function(){
    if(currentPageSP - 1 >= 0){
        currentPageSP--;
        updateReportSP(dataSPfiltered);
    }
}

//"s2008-10-23T02:53:15e2008-10-23T04:34:50", "r2008-10-23T02:53:15e2008-10-23T04:34:50");
showPatterns = function(batchId, sign, length){
    //clearTimeouts();
    var readFromServer = false;
    if(sign != null){
        signSeq = sign;
    }
    if(length != null){
        lengthSeq = length;
    }
    if(currentBatch == null || currentBatch != batchId){
        currentBatch = batchId;
        readFromServer = true;
    }
    currentPageSP = 0;
    if(readFromServer){
        dataAR = null;
        currentPageAR = 0;
        dataSP = null;
        var sbatchId = 's'+ batchId;
        var rbatchId = 'r' + batchId;
        ARApi.getSequentialPatternsByParameters(projectId, sbatchId, SPcallback);
        ARApi.getAssociationRulesByParameters(projectId, rbatchId, ARcallback);
    } else {
        filterPatterns();
    }
}

var SPcallback = function(error, data, response){
    console.log(data);
    if(data == null){

    } else {
        dataSP = data['data'];
    }
    updateMapSP();
}


var requestBatchCallback = function(error, data, response){
    var alert = 'Request submitted';
    var alertType = 'success';

    if(error){
        alert = "Error on the server";
        alertType = 'danger';
        console.log(error);
    }
    if(response['text'].includes("DUPLICATE")){
        alert = 'The batch already exists or has been requested';
        alertType = 'warning';
    }
    //event.preventDefault();
    var notify = $.notify({
        icon: 'glyphicon glyphicon-warning-sign',
        title: 'Notification',
        message: alert,
        url: null,
        target: '_blank'
    },{
        type: alertType,
        allow_dismiss: true,
        newest_on_top: true,
        placement: {
            from: 'bottom',
            align: 'right'
        },
        offset: {
            x: '20',
            y: '20'
        },
        spacing: '10',
        z_index: '1031',
        delay: '5000',
        mouse_over: null
    });

}
requestBatch = function(startTs, endTs, sup, confi){
    if(sup != null && sup != support){
        support = sup;
    }
    if(confi != null && confi != conf){
        conf = confi;
    }
    ARApi.requestBatch(projectId, startTs, endTs, support, conf, requestBatchCallback);
}

var allBatchesCallback = function(error, data, response){

    if(error){
        console.log(error);
    } else{
        batches = [];
        var rawBatches = data['batches'];
        //var rawBatches = ["r2008-10-23T02:53:15e2008-10-23T04:34:50", "r2008-10-23T02:57:45e2008-10-23T02:59:20", "r2008-10-23T02:59:15e2008-10-23T03:05:10", "s2008-10-23T02:53:15e2008-10-23T04:34:50", "s2008-10-23T02:57:45e2008-10-23T02:59:20", "s2008-10-23T02:59:15e2008-10-23T03:05:10"];
        var batchesIndex = 0;
        for(var i = 0; i< rawBatches.length; i++){
            if(rawBatches[i].startsWith('r')){
                continue;
            }
            batches[batchesIndex] = {value:'', text:''};
            var value = rawBatches[i].substring(1, rawBatches[i].length);
            batches[batchesIndex].value = value;
            var parts = value.split('e');
            var startDate = moment(parts[0], "YYYY-MM-DD'T'HH:mm:ss");
            var endDate = moment(parts[1], "YYYY-MM-DD'T'HH:mm:ss");
            batches[batchesIndex].text =startDate.format('MM/DD/YYYY HH:mm:ss') + ' - ' + endDate.format('MM/DD/YYYY HH:mm:ss');
            batchesIndex++;
        }
        $.each(batches, function (i, item) {
            $('#batchSelector').append($('<option>', {
                value: item.value,
                text : item.text
            }));
        });
        $('#batchSelector').val(batches[1].value);
        //currentBatch = batches[0].value;
        showPatterns(batches[1].value, signSeq, lengthSeq);
    }
}

allBatches = function(){

    ARApi.getTrajectoriesBatches(projectId, allBatchesCallback);
}

allBatches();


loadPDF = function(){
    var pdf = new jsPDF('p', 'pt', 'letter');
    // source can be HTML-formatted string, or a reference
    // to an actual DOM element from which the text will be scraped.
    var tableEl = '<table class="table no-margin" id="arTable"><thead><tr><th>Antecedent</th><th>Consequent</th><th>Confidence</th></tr></thead><tbody>';
    for(var i = 0; i < dataAR.length; i++){
        tableEl += '<tr><td>';
        for(var j = 0; j < dataAR[i].antecedent.length; j++){
            tableEl += '<pre>(' + dataAR[i].antecedent[j].a + ',' + dataAR[i].antecedent[j].o + ')</pre>';
        }
        tableEl += '</td><td>';
        for(var k = 0; k < dataAR[i].consequent.length; k++){
            tableEl += '<pre>(' + dataAR[i].consequent[k].a + ',' + dataAR[i].consequent[k].o + ')</pre>';
        }
        tableEl += '</td><td>' + dataAR[i].confidence + '</td></tr>';

    }
    tableEl += '</tbody></table>';
    document.getElementById("hidden").innerHTML = tableEl;
    var res = pdf.autoTableHtmlToJson(document.getElementById("arTable"));
    pdf.text(40, 35, 'Association Rules for ' + $("#batchSelector option:selected").text());
    pdf.autoTable(res.columns, res.data, {
    styles: {
      overflow: 'linebreak',
      columnWidth: 'wrap'
    },
    columnStyles: {
      0: {columnWidth: 'auto'},
      1: {columnWidth: 'auto'}
    }
  });
    document.getElementById("hidden").innerHTML = "";
    pdf.save("AssociationRules.pdf");
}

SPloadPDF = function(){
    var pdf = new jsPDF('p', 'pt', 'letter');
    // source can be HTML-formatted string, or a reference
    // to an actual DOM element from which the text will be scraped.
    var tableEl = '<table class="table no-margin" id="spTable"><thead><tr><th>Items</th><th>Frequency</th></tr></thead><tbody>';
    for(var i = 0; i < dataSPfiltered.length; i++){
        tableEl += '<tr><td>';
        for(var j = 0; j < dataSPfiltered[i].items.length; j++){
            tableEl += '<pre>(' + dataSPfiltered[i].items[j].a + ',' + dataSPfiltered[i].items[j].o + ')</pre>';
        }
        tableEl += '</td>';

        tableEl += '<td>' + dataSPfiltered[i].frequency + '</td></tr>';

    }
    tableEl += '</tbody></table>';
    document.getElementById("hidden").innerHTML = tableEl;
    var res = pdf.autoTableHtmlToJson(document.getElementById("spTable"));
    pdf.text(40, 35, 'Sequential Patterns for ' + $("#batchSelector option:selected").text());
    pdf.autoTable(res.columns, res.data, {
    styles: {
      overflow: 'linebreak',
      columnWidth: 'wrap'
    },
    columnStyles: {
      0: {columnWidth: 'auto'},
      1: {columnWidth: 'auto'}
    }
  });
    document.getElementById("hidden").innerHTML = "";
    pdf.save("SequentialPatterns.pdf");
}


loadCSV = function(){
    var json = dataAR;
    var fields = Object.keys(json[0]);
    var replacer = function(key, value) { return value === null ? '' : value };
    var csv = json.map(function(row){
        return fields.map(function(fieldName){
            return JSON.stringify(row[fieldName], replacer);
        }).join(';');
    });
    csv.unshift(fields.join(';')); // add header column
    csv = csv.join('\r\n');
    var downloadLink = document.createElement("a");
    var blob = new Blob(["\ufeff", csv]);
    var url = URL.createObjectURL(blob);
    downloadLink.href = url;
    downloadLink.download = "AssociationRules.csv";

    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}

SPloadCSV = function(){
    var json = dataSPfiltered;
    var fields = Object.keys(json[0]);
    var replacer = function(key, value) { return value === null ? '' : value };
    var csv = json.map(function(row){
        return fields.map(function(fieldName){
            return JSON.stringify(row[fieldName], replacer);
        }).join(';');
    });
    csv.unshift(fields.join(';')); // add header column
    csv = csv.join('\r\n');
    var downloadLink = document.createElement("a");
    var blob = new Blob(["\ufeff", csv]);
    var url = URL.createObjectURL(blob);
    downloadLink.href = url;
    downloadLink.download = "SequentialPatterns.csv";

    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}


