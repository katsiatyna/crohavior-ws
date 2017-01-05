
var json = [{"items":[{"latitude":39.985,"longitude":116.327}],"frequency":1971},
    {"items":[{"latitude":39.976,"longitude":116.331}],"frequency":1725},
    {"items":[{"latitude":39.985,"longitude":116.332}],"frequency":1663},
    {"items":[{"latitude":39.985,"longitude":116.332},{"latitude":39.985,"longitude":116.327}],"frequency":865},
    {"items":[{"latitude":39.975,"longitude":116.331}],"frequency":1652},
    {"items":[{"latitude":39.975,"longitude":116.331},{"latitude":39.976,"longitude":116.331}],"frequency":960},
    {"items":[{"latitude":39.975,"longitude":116.33}],"frequency":1626},
    {"items":[{"latitude":39.975,"longitude":116.33},{"latitude":39.975,"longitude":116.331}],"frequency":1198},
    {"items":[{"latitude":39.975,"longitude":116.328}],"frequency":1599}]
var fields = Object.keys(json[0])
var replacer = function(key, value) { return value === null ? '' : value }
var csv = json.map(function(row){
    return fields.map(function(fieldName){
        return JSON.stringify(row[fieldName], replacer)
    }).join(',')
})
csv.unshift(fields.join(',')) // add header column
console.log(csv.join('\r\n'))
