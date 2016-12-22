var  pages = 1;
var currentPageAR = 0;
var dataAR = null;

updateReportAR = function(){
    var assrules = "", pagingInfo = "";
    pages = dataAR.length / 7;
    if(dataAR.length % 7 != 0){
        pages++;
    }
    pagingInfo += '<li id="prev_page" onclick="prevPageAR();" '
     if(currentPageAR - 1 < 0){
        pagingInfo += 'disabled="true" >';
     } else {
        pagingInfo += '>';
     }

    pagingInfo += '<a href="#" >&laquo;</a></li>';
    pagingInfo += '<li><a href="#">'+(currentPageAR + 1)+'</a></li>';
    pagingInfo += '<li id="next_page" onclick="nextPageAR();"><a href="#" >&raquo;</a></li>'

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
    document.getElementById('accordion').innerHTML = assrules;
}

var ARcallback = function(error, data, response){
    console.log(data);
    dataAR = data['data'];
    updateReportAR();
}
var ARApi = new trajectoryApi();

nextPageAR = function(){
    if(currentPageAR + 1 < pages){
        currentPageAR++;
        updateReportAR();
    }
}

prevPageAR = function(){
    if(currentPageAR - 1 >= 0){
        currentPageAR--;
        updateReportAR();
    }
}


showAssociationRules = function(batchId){
    clearTimeouts();
    dataAR = null;
    currentPageAR = 0;
    ARApi.getAssociationRulesByParameters(1, batchId, ARcallback);
}

