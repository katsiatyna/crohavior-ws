
writeCSV = function(){
    var json = [{"antecedent":[{"latitude":39.983,"longitude":116.327},{"latitude":39.98,"longitude":116.327}],"consequent":[{"latitude":39.981,"longitude":116.327}],"confidence":0.9295908658420552},{"antecedent":[{"latitude":39.983,"longitude":116.327},{"latitude":39.98,"longitude":116.327}],"consequent":[{"latitude":39.984,"longitude":116.327}],"confidence":0.9419600380589914},{"antecedent":[{"latitude":39.983,"longitude":116.327},{"latitude":39.98,"longitude":116.327}],"consequent":[{"latitude":39.985,"longitude":116.327}],"confidence":0.8049476688867745}];
    var fields = Object.keys(json[0]);
    var replacer = function(key, value) { return value === null ? '' : value };
    var csv = json.map(function(row){
        return fields.map(function(fieldName){
            return JSON.stringify(row[fieldName], replacer);
        }).join(',');
    });
    csv.unshift(fields.join(',')); // add header column
    console.log(csv.join('\r\n'));
    var downloadLink = document.createElement("a");
    var blob = new Blob(["\ufeff", csv]);
    var url = URL.createObjectURL(blob);
    downloadLink.href = url;
    downloadLink.download = "data.csv";

    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}

writeCSV();
