var ARcallback = function(error, data, response){
console.log(data);

}
var ARApi = new trajectoryApi();






showAssociationRules = function(batchId){
    ARApi.getAssociationRulesByParameters(1, batchId, ARcallback);
}