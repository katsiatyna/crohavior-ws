/*
 * Author: Abdullah A Almsaeed
 * Date: 4 Jan 2014
 * Description:
 *      This is a demo file used only for the main dashboard (index.html)
 **/

$(function () {

  "use strict";

  //Make the dashboard widgets sortable Using jquery UI
  $(".connectedSortable").sortable({
    placeholder: "sort-highlight",
    connectWith: ".connectedSortable",
    handle: ".box-header, .nav-tabs",
    forcePlaceholderSize: true,
    zIndex: 999999
  });
  $(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom").css("cursor", "move");

  //jQuery UI sortable for the todo list
  $(".todo-list").sortable({
    placeholder: "sort-highlight",
    handle: ".handle",
    forcePlaceholderSize: true,
    zIndex: 999999
  });

  //bootstrap WYSIHTML5 - text editor
  $(".textarea").wysihtml5();

  $('.daterange').daterangepicker({
    ranges: {
      'Today': [moment(), moment()],
      'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
      'Last 7 Days': [moment().subtract(6, 'days'), moment()],
      'Last 30 Days': [moment().subtract(29, 'days'), moment()],
      'This Month': [moment().startOf('month'), moment().endOf('month')],
      'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    },
    startDate: moment().subtract(29, 'days'),
    endDate: moment()
  }, function (start, end) {
    window.alert("You chose: " + start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
  });

  /* jQueryKnob */
  $(".knob").knob();

  //heatmap
  // don't forget to add gmaps-heatmap.js
 // var myLatlng = new google.maps.LatLng(25.6586, -80.3568);
  /*var myLatlng = new google.maps.LatLng(39.905, 116.375);
  //var myLatlng = new google.maps.LatLng(39.99900555, 116.4215235);
  // map options,
  var myOptions = {
    zoom: 10,
    center: myLatlng
  };
  // standard map
  map = new google.maps.Map(document.getElementById("world-map"), myOptions);
  // heatmap layer
  heatmap = new HeatmapOverlay(map,
    {
      // radius should be small ONLY if scaleRadius is true (or small radius is intended)
      "radius": 2,
      "maxOpacity": 1,
      // scales the radius based on map zoom
      "scaleRadius": true,
      // if set to false the heatmap uses the global maximum for colorization
      // if activated: uses the data maximum within the current map boundaries
      //   (there will always be a red spot with useLocalExtremas true)
      "useLocalExtrema": true,
      // which field name in your data represents the latitude - default "lat"
      latField: 'latitude',
      // which field name in your data represents the longitude - default "lng"
      lngField: 'longitude',
      // which field name in your data represents the data value - default "value"
      valueField: 'count'
    }
  );
  var testData = {
	  max: 100,
      data:[{latitude:39.97,count:555,longitude:116.29},{latitude:39.88,count:206,longitude:116.27},{latitude:39.92,count:44,longitude:116.25},{latitude:39.91,count:131,longitude:116.27},{latitude:39.95,count:2,longitude:116.49},{latitude:39.91,count:628,longitude:116.37},{latitude:39.96,count:1,longitude:116.42},{latitude:39.89,count:12,longitude:116.46},{latitude:39.88,count:208,longitude:116.47},{latitude:39.98,count:9,longitude:116.43},{latitude:39.99,count:46,longitude:116.4},{latitude:39.82,count:67,longitude:116.25},{latitude:39.95,count:300,longitude:116.33},{latitude:39.92,count:1,longitude:116.3},{latitude:39.99,count:137,longitude:116.41},{latitude:39.89,count:817,longitude:116.36},{latitude:39.99,count:9591,longitude:116.33},{latitude:39.95,count:1,longitude:116.48},{latitude:39.93,count:50,longitude:116.38},{latitude:39.82,count:51,longitude:116.39},{latitude:39.92,count:1,longitude:116.34},{latitude:39.94,count:83,longitude:116.46},{latitude:39.93,count:5,longitude:116.45},{latitude:39.9,count:162,longitude:116.35},{latitude:39.98,count:131,longitude:116.38},{latitude:40.0,count:464,longitude:116.34},{latitude:39.98,count:131,longitude:116.36},{latitude:39.96,count:8,longitude:116.37},{latitude:39.81,count:51,longitude:116.26},{latitude:39.95,count:75,longitude:116.31},{latitude:39.91,count:82,longitude:116.32},{latitude:39.92,count:20,longitude:116.5},{latitude:40.0,count:312,longitude:116.3},{latitude:39.99,count:1953,longitude:116.31},{latitude:39.97,count:8615,longitude:116.3},{latitude:39.82,count:77,longitude:116.34},{latitude:39.92,count:37,longitude:116.29},{latitude:39.96,count:1,longitude:116.47},{latitude:39.84,count:112,longitude:116.28},{latitude:39.95,count:1,longitude:116.41},{latitude:39.91,count:4,longitude:116.26},{latitude:39.97,count:448,longitude:116.35},{latitude:39.85,count:92,longitude:116.28},{latitude:39.91,count:3130,longitude:116.47},{latitude:39.97,count:1536,longitude:116.34},{latitude:39.97,count:20,longitude:116.44},{latitude:39.97,count:529,longitude:116.39},{latitude:39.93,count:76,longitude:116.46},{latitude:39.9,count:8,longitude:116.29},{latitude:40.0,count:1,longitude:116.44},{latitude:39.98,count:178,longitude:116.28},{latitude:39.9,count:31,longitude:116.34},{latitude:39.9,count:6,longitude:116.25},{latitude:39.9,count:277,longitude:116.39},{latitude:39.96,count:2270,longitude:116.32},{latitude:39.96,count:394,longitude:116.27},{latitude:40.0,count:75,longitude:116.35},{latitude:39.91,count:92,longitude:116.42},{latitude:39.87,count:4,longitude:116.33},{latitude:39.9,count:11,longitude:116.3},{latitude:40.0,count:16,longitude:116.29},{latitude:39.91,count:347,longitude:116.41},{latitude:39.99,count:500,longitude:116.3},{latitude:39.9,count:67,longitude:116.38},{latitude:39.91,count:69,longitude:116.31},{latitude:39.97,count:146,longitude:116.28},{latitude:39.89,count:157,longitude:116.27},{latitude:39.91,count:1472,longitude:116.49},{latitude:39.98,count:1,longitude:116.27},{latitude:40.0,count:1,longitude:116.38},{latitude:39.83,count:103,longitude:116.34},{latitude:39.99,count:40,longitude:116.35},{latitude:39.88,count:3,longitude:116.33},{latitude:39.82,count:96,longitude:116.28},{latitude:39.97,count:599,longitude:116.43},{latitude:39.98,count:5,longitude:116.26},{latitude:39.93,count:5,longitude:116.32},{latitude:39.98,count:4,longitude:116.42},{latitude:39.9,count:1,longitude:116.46},{latitude:39.95,count:1,longitude:116.44},{latitude:39.86,count:2,longitude:116.33},{latitude:40.0,count:1,longitude:116.43},{latitude:39.95,count:1,longitude:116.5},{latitude:39.87,count:10,longitude:116.25},{latitude:39.96,count:1,longitude:116.48},{latitude:39.93,count:33,longitude:116.37},{latitude:39.94,count:1,longitude:116.26},{latitude:39.83,count:4,longitude:116.29},{latitude:39.99,count:212,longitude:116.34},{latitude:39.83,count:49,longitude:116.35},{latitude:39.9,count:10,longitude:116.28},{latitude:39.98,count:147,longitude:116.37},{latitude:39.89,count:123,longitude:116.47},{latitude:39.97,count:472,longitude:116.36},{latitude:39.95,count:434,longitude:116.35},{latitude:39.92,count:1,longitude:116.45},{latitude:39.94,count:442,longitude:116.27},{latitude:39.87,count:179,longitude:116.39},{latitude:39.96,count:1235,longitude:116.33},{latitude:39.95,count:710,longitude:116.34},{latitude:39.98,count:993,longitude:116.32},{latitude:39.92,count:403,longitude:116.46},{latitude:39.97,count:678,longitude:116.38},{latitude:39.88,count:1,longitude:116.49},{latitude:39.97,count:1,longitude:116.45},{latitude:39.94,count:262,longitude:116.32},{latitude:39.96,count:1022,longitude:116.31},{latitude:39.95,count:4,longitude:116.29},{latitude:39.91,count:44,longitude:116.4},{latitude:39.83,count:123,longitude:116.39},{latitude:39.93,count:1,longitude:116.26},{latitude:39.91,count:1,longitude:116.33},{latitude:39.91,count:1440,longitude:116.48},{latitude:39.94,count:6,longitude:116.37},{latitude:39.87,count:4,longitude:116.34},{latitude:39.93,count:300,longitude:116.27},{latitude:39.99,count:443,longitude:116.29},{latitude:39.92,count:188,longitude:116.38},{latitude:39.92,count:18,longitude:116.28},{latitude:39.9,count:525,longitude:116.36},{latitude:39.97,count:660,longitude:116.42},{latitude:39.88,count:1517,longitude:116.35},{latitude:39.95,count:196,longitude:116.45},{latitude:39.86,count:243,longitude:116.39},{latitude:39.81,count:36,longitude:116.34},{latitude:39.98,count:4189,longitude:116.33},{latitude:39.81,count:22,longitude:116.29},{latitude:39.9,count:6,longitude:116.32},{latitude:39.96,count:690,longitude:116.34},{latitude:39.93,count:130,longitude:116.48},{latitude:39.83,count:45,longitude:116.38},{latitude:39.97,count:1501,longitude:116.32},{latitude:39.87,count:126,longitude:116.46},{latitude:39.96,count:1460,longitude:116.44},{latitude:39.93,count:24,longitude:116.49},{latitude:39.92,count:106,longitude:116.26},{latitude:39.98,count:4699,longitude:116.31},{latitude:39.86,count:13,longitude:116.25},{latitude:39.93,count:2,longitude:116.33},{latitude:40.0,count:7244,longitude:116.32},{latitude:39.87,count:272,longitude:116.45},{latitude:39.91,count:34,longitude:116.34},{latitude:39.97,count:207,longitude:116.27},{latitude:39.85,count:451,longitude:116.4},{latitude:39.83,count:172,longitude:116.28},{latitude:39.9,count:162,longitude:116.47},{latitude:39.9,count:827,longitude:116.42},{latitude:39.9,count:8,longitude:116.26},{latitude:39.94,count:6,longitude:116.31},{latitude:39.92,count:2,longitude:116.47},{latitude:39.87,count:35,longitude:116.28},{latitude:39.96,count:335,longitude:116.35},{latitude:39.96,count:951,longitude:116.3},{latitude:39.91,count:155,longitude:116.39},{latitude:39.97,count:373,longitude:116.37},{latitude:39.96,count:341,longitude:116.29},{latitude:39.89,count:3,longitude:116.33},{latitude:39.9,count:195,longitude:116.27},{latitude:39.92,count:187,longitude:116.27},{latitude:39.93,count:7,longitude:116.31},{latitude:39.99,count:12,longitude:116.38},{latitude:39.92,count:129,longitude:116.37},{latitude:39.96,count:3,longitude:116.39},{latitude:39.98,count:245,longitude:116.4},{latitude:40.0,count:5,longitude:116.37},{latitude:39.93,count:1,longitude:116.4},{latitude:39.94,count:291,longitude:116.33},{latitude:39.86,count:1,longitude:116.34},{latitude:39.91,count:6,longitude:116.3},{latitude:39.81,count:34,longitude:116.25},{latitude:39.99,count:13,longitude:116.43},{latitude:39.95,count:412,longitude:116.46},{latitude:39.83,count:44,longitude:116.36},{latitude:39.91,count:793,longitude:116.5},{latitude:39.95,count:15,longitude:116.28},{latitude:39.88,count:216,longitude:116.39},{latitude:39.88,count:64,longitude:116.45},{latitude:39.86,count:150,longitude:116.28},{latitude:40.0,count:13391,longitude:116.33},{latitude:39.98,count:471,longitude:116.29},{latitude:39.97,count:710,longitude:116.41},{latitude:39.83,count:44,longitude:116.37},{latitude:39.98,count:2,longitude:116.44},{latitude:39.98,count:169,longitude:116.39},{latitude:39.91,count:180,longitude:116.46},{latitude:39.82,count:21,longitude:116.41},{latitude:39.9,count:827,longitude:116.41},{latitude:39.92,count:4,longitude:116.49},{latitude:39.99,count:8,longitude:116.42},{latitude:39.91,count:3,longitude:116.28},{latitude:39.89,count:287,longitude:116.39},{latitude:39.85,count:307,longitude:116.39},{latitude:39.91,count:221,longitude:116.43},{latitude:39.94,count:43,longitude:116.3},{latitude:39.9,count:10,longitude:116.33},{latitude:39.96,count:72,longitude:116.36},{latitude:39.96,count:1999,longitude:116.28},{latitude:39.94,count:704,longitude:116.34},{latitude:40.0,count:1315,longitude:116.31},{latitude:40.0,count:30,longitude:116.4},{latitude:39.98,count:429,longitude:116.35},{latitude:39.98,count:984,longitude:116.3},{latitude:39.96,count:399,longitude:116.45},{latitude:39.87,count:122,longitude:116.27},{latitude:39.84,count:4,longitude:116.34},{latitude:39.93,count:6,longitude:116.34},{latitude:39.92,count:3,longitude:116.31},{latitude:39.92,count:1,longitude:116.33},{latitude:39.87,count:125,longitude:116.47},{latitude:39.84,count:137,longitude:116.39},{latitude:39.85,count:4,longitude:116.34},{latitude:39.93,count:3,longitude:116.3},{latitude:39.91,count:35,longitude:116.38},{latitude:39.9,count:8,longitude:116.31},{latitude:40.0,count:91,longitude:116.41},{latitude:39.95,count:2,longitude:116.26},{latitude:39.99,count:1,longitude:116.37},{latitude:39.86,count:112,longitude:116.45},{latitude:39.93,count:18,longitude:116.39},{latitude:39.81,count:28,longitude:116.28},{latitude:39.89,count:3,longitude:116.25},{latitude:39.86,count:170,longitude:116.46},{latitude:39.94,count:1,longitude:116.29},{latitude:39.87,count:49,longitude:116.26},{latitude:39.95,count:524,longitude:116.27},{latitude:39.97,count:10364,longitude:116.31},{latitude:39.99,count:2257,longitude:116.32},{latitude:39.97,count:562,longitude:116.4},{latitude:39.89,count:2,longitude:116.29},{latitude:39.96,count:167,longitude:116.43},{latitude:39.93,count:195,longitude:116.5},{latitude:39.98,count:269,longitude:116.34},{latitude:39.93,count:9,longitude:116.29},{latitude:39.97,count:1536,longitude:116.33},{latitude:39.95,count:393,longitude:116.32},{latitude:39.88,count:706,longitude:116.36},{latitude:39.94,count:2749,longitude:116.35},{latitude:39.91,count:85,longitude:116.36},{latitude:39.96,count:89,longitude:116.46}]
  }*/
  /*var testData1 = {
    max: 8,
    data: [{lat: 24.6408, lng:46.7728, count: 3},{lat: 50.75, lng:-1.55, count: 1},{lat: 52.6333, lng:1.75, count: 1},{lat: 48.15, lng:9.4667, count: 1},{lat: 52.35, lng:4.9167, count: 2},{lat: 60.8, lng:11.1, count: 1},{lat: 43.561, lng:-116.214, count: 1},{lat: 47.5036, lng:-94.685, count: 1},{lat: 42.1818, lng:-71.1962, count: 1},{lat: 42.0477, lng:-74.1227, count: 1},{lat: 40.0326, lng:-75.719, count: 1},{lat: 40.7128, lng:-73.2962, count: 2},{lat: 27.9003, lng:-82.3024, count: 1},{lat: 38.2085, lng:-85.6918, count: 1},{lat: 46.8159, lng:-100.706, count: 1},{lat: 30.5449, lng:-90.8083, count: 1},{lat: 44.735, lng:-89.61, count: 1},{lat: 41.4201, lng:-75.6485, count: 2},{lat: 39.4209, lng:-74.4977, count: 1},{lat: 39.7437, lng:-104.979, count: 1},{lat: 39.5593, lng:-105.006, count: 1},{lat: 45.2673, lng:-93.0196, count: 1},{lat: 41.1215, lng:-89.4635, count: 1},{lat: 43.4314, lng:-83.9784, count: 1},{lat: 43.7279, lng:-86.284, count: 1},{lat: 40.7168, lng:-73.9861, count: 1},{lat: 47.7294, lng:-116.757, count: 1},{lat: 47.7294, lng:-116.757, count: 2},{lat: 35.5498, lng:-118.917, count: 1},{lat: 34.1568, lng:-118.523, count: 1},{lat: 39.501, lng:-87.3919, count: 3},{lat: 33.5586, lng:-112.095, count: 1},{lat: 38.757, lng:-77.1487, count: 1},{lat: 33.223, lng:-117.107, count: 1},{lat: 30.2316, lng:-85.502, count: 1},{lat: 39.1703, lng:-75.5456, count: 8},{lat: 30.0041, lng:-95.2984, count: 2},{lat: 29.7755, lng:-95.4152, count: 1},{lat: 41.8014, lng:-87.6005, count: 1},{lat: 37.8754, lng:-121.687, count: 7},{lat: 38.4493, lng:-122.709, count: 1},{lat: 40.5494, lng:-89.6252, count: 1},{lat: 42.6105, lng:-71.2306, count: 1},{lat: 40.0973, lng:-85.671, count: 1},{lat: 40.3987, lng:-86.8642, count: 1},{lat: 40.4224, lng:-86.8031, count: 4},{lat: 47.2166, lng:-122.451, count: 1},{lat: 32.2369, lng:-110.956, count: 1},{lat: 41.3969, lng:-87.3274, count: 2},{lat: 41.7364, lng:-89.7043, count: 2},{lat: 42.3425, lng:-71.0677, count: 1},{lat: 33.8042, lng:-83.8893, count: 1},{lat: 36.6859, lng:-121.629, count: 2},{lat: 41.0957, lng:-80.5052, count: 1},{lat: 46.8841, lng:-123.995, count: 1},{lat: 40.2851, lng:-75.9523, count: 2},{lat: 42.4235, lng:-85.3992, count: 1},{lat: 39.7437, lng:-104.979, count: 2},{lat: 25.6586, lng:-80.3568, count: 7},{lat: 33.0975, lng:-80.1753, count: 1},{lat: 25.7615, lng:-80.2939, count: 1},{lat: 26.3739, lng:-80.1468, count: 1},{lat: 37.6454, lng:-84.8171, count: 1},{lat: 34.2321, lng:-77.8835, count: 1},{lat: 34.6774, lng:-82.928, count: 1},{lat: 39.9744, lng:-86.0779, count: 1},{lat: 35.6784, lng:-97.4944, count: 2},{lat: 33.5547, lng:-84.1872, count: 1},{lat: 27.2498, lng:-80.3797, count: 1},{lat: 41.4789, lng:-81.6473, count: 1},{lat: 41.813, lng:-87.7134, count: 1},{lat: 41.8917, lng:-87.9359, count: 1},{lat: 35.0911, lng:-89.651, count: 1},{lat: 32.6102, lng:-117.03, count: 1},{lat: 41.758, lng:-72.7444, count: 1},{lat: 39.8062, lng:-86.1407, count: 1},{lat: 41.872, lng:-88.1662, count: 1},{lat: 34.1404, lng:-81.3369, count: 1},{lat: 46.15, lng:-60.1667, count: 1},{lat: 36.0679, lng:-86.7194, count: 1},{lat: 43.45, lng:-80.5, count: 1},{lat: 44.3833, lng:-79.7, count: 1},{lat: 45.4167, lng:-75.7, count: 2},{lat: 43.75, lng:-79.2, count: 2},{lat: 45.2667, lng:-66.0667, count: 3},{lat: 42.9833, lng:-81.25, count: 2},{lat: 44.25, lng:-79.4667, count: 3},{lat: 45.2667, lng:-66.0667, count: 2},{lat: 34.3667, lng:-118.478, count: 3},{lat: 42.734, lng:-87.8211, count: 1},{lat: 39.9738, lng:-86.1765, count: 1},{lat: 33.7438, lng:-117.866, count: 1},{lat: 37.5741, lng:-122.321, count: 1},{lat: 42.2843, lng:-85.2293, count: 1},{lat: 34.6574, lng:-92.5295, count: 1},{lat: 41.4881, lng:-87.4424, count: 1},{lat: 25.72, lng:-80.2707, count: 1},{lat: 34.5873, lng:-118.245, count: 1},{lat: 35.8278, lng:-78.6421, count: 1}]
  };
  var testData2 = {
    max: 8,
    data: [{lat: 24.6408, lng:46.7728, count: 1000},{lat: 50.75, lng:-1.55, count: 5},{lat: 52.6333, lng:1.75, count: 3},{lat: 48.15, lng:9.4667, count: 10},{lat: 52.35, lng:4.9167, count: 4},{lat: 60.8, lng:11.1, count: 9},{lat: 43.561, lng:-116.214, count: 2},{lat: 47.5036, lng:-94.685, count: 2},{lat: 42.1818, lng:-71.1962, count: 4},{lat: 42.0477, lng:-74.1227, count: 10},{lat: 40.0326, lng:-75.719, count: 1},{lat: 40.7128, lng:-73.2962, count: 2},{lat: 27.9003, lng:-82.3024, count: 10},{lat: 38.2085, lng:-85.6918, count: 1},{lat: 46.8159, lng:-100.706, count: 2},{lat: 30.5449, lng:-90.8083, count: 6},{lat: 44.735, lng:-89.61, count: 7},{lat: 41.4201, lng:-75.6485, count: 2},{lat: 39.4209, lng:-74.4977, count: 3},{lat: 39.7437, lng:-104.979, count: 10},{lat: 39.5593, lng:-105.006, count: 1},{lat: 45.2673, lng:-93.0196, count: 1},{lat: 41.1215, lng:-89.4635, count: 1},{lat: 43.4314, lng:-83.9784, count: 1},{lat: 43.7279, lng:-86.284, count: 1},{lat: 40.7168, lng:-73.9861, count: 1},{lat: 47.7294, lng:-116.757, count: 1},{lat: 47.7294, lng:-116.757, count: 2},{lat: 35.5498, lng:-118.917, count: 1},{lat: 34.1568, lng:-118.523, count: 1},{lat: 39.501, lng:-87.3919, count: 3},{lat: 33.5586, lng:-112.095, count: 1},{lat: 38.757, lng:-77.1487, count: 1},{lat: 33.223, lng:-117.107, count: 1},{lat: 30.2316, lng:-85.502, count: 1},{lat: 39.1703, lng:-75.5456, count: 8},{lat: 30.0041, lng:-95.2984, count: 2},{lat: 29.7755, lng:-95.4152, count: 1},{lat: 41.8014, lng:-87.6005, count: 1},{lat: 37.8754, lng:-121.687, count: 7},{lat: 38.4493, lng:-122.709, count: 1},{lat: 40.5494, lng:-89.6252, count: 1},{lat: 42.6105, lng:-71.2306, count: 1},{lat: 40.0973, lng:-85.671, count: 1},{lat: 40.3987, lng:-86.8642, count: 1},{lat: 40.4224, lng:-86.8031, count: 4},{lat: 47.2166, lng:-122.451, count: 1},{lat: 32.2369, lng:-110.956, count: 1},{lat: 41.3969, lng:-87.3274, count: 2},{lat: 41.7364, lng:-89.7043, count: 2},{lat: 42.3425, lng:-71.0677, count: 1},{lat: 33.8042, lng:-83.8893, count: 1},{lat: 36.6859, lng:-121.629, count: 2},{lat: 41.0957, lng:-80.5052, count: 1},{lat: 46.8841, lng:-123.995, count: 1},{lat: 40.2851, lng:-75.9523, count: 2},{lat: 42.4235, lng:-85.3992, count: 1},{lat: 39.7437, lng:-104.979, count: 2},{lat: 25.6586, lng:-80.3568, count: 7},{lat: 33.0975, lng:-80.1753, count: 1},{lat: 25.7615, lng:-80.2939, count: 1},{lat: 26.3739, lng:-80.1468, count: 1},{lat: 37.6454, lng:-84.8171, count: 1},{lat: 34.2321, lng:-77.8835, count: 1},{lat: 34.6774, lng:-82.928, count: 1},{lat: 39.9744, lng:-86.0779, count: 1},{lat: 35.6784, lng:-97.4944, count: 2},{lat: 33.5547, lng:-84.1872, count: 1},{lat: 27.2498, lng:-80.3797, count: 1},{lat: 41.4789, lng:-81.6473, count: 1},{lat: 41.813, lng:-87.7134, count: 1},{lat: 41.8917, lng:-87.9359, count: 1},{lat: 35.0911, lng:-89.651, count: 1},{lat: 32.6102, lng:-117.03, count: 1},{lat: 41.758, lng:-72.7444, count: 1},{lat: 39.8062, lng:-86.1407, count: 1},{lat: 41.872, lng:-88.1662, count: 1},{lat: 34.1404, lng:-81.3369, count: 1},{lat: 46.15, lng:-60.1667, count: 1},{lat: 36.0679, lng:-86.7194, count: 1},{lat: 43.45, lng:-80.5, count: 1},{lat: 44.3833, lng:-79.7, count: 1},{lat: 45.4167, lng:-75.7, count: 2},{lat: 43.75, lng:-79.2, count: 2},{lat: 45.2667, lng:-66.0667, count: 3},{lat: 42.9833, lng:-81.25, count: 2},{lat: 44.25, lng:-79.4667, count: 3},{lat: 45.2667, lng:-66.0667, count: 2},{lat: 34.3667, lng:-118.478, count: 3},{lat: 42.734, lng:-87.8211, count: 1},{lat: 39.9738, lng:-86.1765, count: 1},{lat: 33.7438, lng:-117.866, count: 1},{lat: 37.5741, lng:-122.321, count: 1},{lat: 42.2843, lng:-85.2293, count: 1},{lat: 34.6574, lng:-92.5295, count: 1},{lat: 41.4881, lng:-87.4424, count: 1},{lat: 25.72, lng:-80.2707, count: 1},{lat: 34.5873, lng:-118.245, count: 1},{lat: 35.8278, lng:-78.6421, count: 1}]
  };*/

  //heatmap.setData(testData);

  var i = 1;                     //  set your counter to 1

  /*function myLoop () {           //  create a loop function
     setTimeout(function () {    //  call a 3s setTimeout when the loop is called
        if(i%2 == 0){
            heatmap.setData(testData);
        } else{
            heatmap.setData(testData);
        }
        i++;                     //  increment the counter
                    //  if the counter < 10, call the loop function
        myLoop();             //  ..  again which will trigger another
                               //  ..  setTimeout()
     }, 2000)
  }*/

  //myLoop();

    //Initialize Select2 Elements
    $(".select2").select2();

    //Datemask dd/mm/yyyy
    $("#datemask").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
    //Datemask2 mm/dd/yyyy
    $("#datemask2").inputmask("mm/dd/yyyy", {"placeholder": "mm/dd/yyyy"});
    //Money Euro
    $("[data-mask]").inputmask();

    //Date range picker
    $('#reservation').daterangepicker();
    //Date range picker with time picker
    $('#reservationtime').daterangepicker({
         "timePicker": true,
            "timePicker24Hour": true,
            "timePickerSeconds": true,
            "startDate": "01/01/2017 06:00:00",
            "endDate": "01/01/2017 23:55:59",
            "minDate": "01/01/2017 06:00:00",
            "maxDate": "01/01/2017 23:55:59",
            "locale": {
                        "format": "MM/DD/YYYY HH:mm:ss"
            }
        }, function(start, end, label) {
          console.log("New date range selected: " + start.format('x') + " to " + end.format('x') + ")");
          setDateRange(start.format('x'), end.format('x'));
        });
    $('#reservationtimeTrajectory').daterangepicker({
         "timePicker": true,
            "timePicker24Hour": true,
            "timePickerSeconds": true,
            "startDate": "01/01/2017 06:00:00",
            "endDate": "01/01/2017 23:55:59",
            "minDate": "01/01/2017 06:00:00",
            "maxDate": "01/01/2017 23:55:59",
            "locale": {
                        "format": "MM/DD/YYYY HH:mm:ss"
            }
        }, function(start, end, label) {
          console.log("New date range selected TRAJ: " + start.format('x') + " to " + end.format('x') + ")");
          //requestBatch(start.format('x'), end.format('x'));
        });

    $("#supportInput").val(support);
    $("#confInput").val(conf);
    $("#lengthInput").val(lengthSeq);
    $("#signSelector").val(signSeq);
    $( "#showassrules" ).click(function() {
      var batchId = $( "#batchSelector option:selected" ).val();
      //later put in the real formatted number
      var length = $("#lengthInput").val();
      var sign = $("#signSelector option:selected").val();
      showPatterns( batchId, sign, length);//"s2008-10-23T02:53:15e2008-10-23T04:34:50", "r2008-10-23T02:53:15e2008-10-23T04:34:50");
    });
    $('#requestbatchbtn').click(function(){
      var startTs = $('#reservationtimeTrajectory').data('daterangepicker').startDate.format('x');
      var endTs = $('#reservationtimeTrajectory').data('daterangepicker').endDate.format('x');
      var s = $("#supportInput").val();
      var c = $("#confInput").val();
      requestBatch(startTs, endTs, s, c);
    });


    //Date picker
    $('#datepicker').datepicker({
      autoclose: true
    });

    //Timepicker
    $(".timepicker").timepicker({
      showInputs: false
    });

  //Sparkline charts
  var myvalues = [1000, 1200, 920, 927, 931, 1027, 819, 930, 1021];
  $('#sparkline-1').sparkline(myvalues, {
    type: 'line',
    lineColor: '#92c1dc',
    fillColor: "#ebf4f9",
    height: '50',
    width: '80'
  });
  myvalues = [515, 519, 520, 522, 652, 810, 370, 627, 319, 630, 921];
  $('#sparkline-2').sparkline(myvalues, {
    type: 'line',
    lineColor: '#92c1dc',
    fillColor: "#ebf4f9",
    height: '50',
    width: '80'
  });
  myvalues = [15, 19, 20, 22, 33, 27, 31, 27, 19, 30, 21];
  $('#sparkline-3').sparkline(myvalues, {
    type: 'line',
    lineColor: '#92c1dc',
    fillColor: "#ebf4f9",
    height: '50',
    width: '80'
  });

  //The Calender
  $("#calendar").datepicker();

  //SLIMSCROLL FOR CHAT WIDGET
  $('#chat-box').slimScroll({
    height: '250px'
  });

  /* Morris.js Charts */
  // Sales chart
  var area = new Morris.Area({
    element: 'revenue-chart',
    resize: true,
    data: [
      {y: '2011 Q1', item1: 2666, item2: 2666},
      {y: '2011 Q2', item1: 2778, item2: 2294},
      {y: '2011 Q3', item1: 4912, item2: 1969},
      {y: '2011 Q4', item1: 3767, item2: 3597},
      {y: '2012 Q1', item1: 6810, item2: 1914},
      {y: '2012 Q2', item1: 5670, item2: 4293},
      {y: '2012 Q3', item1: 4820, item2: 3795},
      {y: '2012 Q4', item1: 15073, item2: 5967},
      {y: '2013 Q1', item1: 10687, item2: 4460},
      {y: '2013 Q2', item1: 8432, item2: 5713}
    ],
    xkey: 'y',
    ykeys: ['item1', 'item2'],
    labels: ['Item 1', 'Item 2'],
    lineColors: ['#a0d0e0', '#3c8dbc'],
    hideHover: 'auto'
  });
  var line = new Morris.Line({
    element: 'line-chart',
    resize: true,
    data: [
      {y: '2011 Q1', item1: 2666},
      {y: '2011 Q2', item1: 2778},
      {y: '2011 Q3', item1: 4912},
      {y: '2011 Q4', item1: 3767},
      {y: '2012 Q1', item1: 6810},
      {y: '2012 Q2', item1: 5670},
      {y: '2012 Q3', item1: 4820},
      {y: '2012 Q4', item1: 15073},
      {y: '2013 Q1', item1: 10687},
      {y: '2013 Q2', item1: 8432}
    ],
    xkey: 'y',
    ykeys: ['item1'],
    labels: ['Item 1'],
    lineColors: ['#efefef'],
    lineWidth: 2,
    hideHover: 'auto',
    gridTextColor: "#fff",
    gridStrokeWidth: 0.4,
    pointSize: 4,
    pointStrokeColors: ["#efefef"],
    gridLineColor: "#efefef",
    gridTextFamily: "Open Sans",
    gridTextSize: 10
  });

  //Donut Chart
  var donut = new Morris.Donut({
    element: 'sales-chart',
    resize: true,
    colors: ["#3c8dbc", "#f56954", "#00a65a"],
    data: [
      {label: "Download Sales", value: 12},
      {label: "In-Store Sales", value: 30},
      {label: "Mail-Order Sales", value: 20}
    ],
    hideHover: 'auto'
  });

  //Fix for charts under tabs
  $('.box ul.nav a').on('shown.bs.tab', function () {
    area.redraw();
    donut.redraw();
    line.redraw();
  });

  /* The todo list plugin */
  $(".todo-list").todolist({
    onCheck: function (ele) {
      window.console.log("The element has been checked");
      return ele;
    },
    onUncheck: function (ele) {
      window.console.log("The element has been unchecked");
      return ele;
    }
  });

});
