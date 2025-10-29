/* eslint-disable */
var params = {
  LAYERS: 'network:navigationView',
  FORMAT: 'image/png'
};
var result;
var startPoint;
var destPoint;
var vectorLayer;

function initMap() {
  startPoint = new ol.Feature();
  destPoint = new ol.Feature();

	// The vector layer used to display the "start" and "destination" features.
	vectorLayer = new ol.layer.Vector({
		source: new ol.source.Vector({
		features: [startPoint, destPoint]
		}),
		style:new ol.style.Style({
			image:new ol.style.Icon(({
				size:[32,32],
				anchor:[0.5,0.75],
				anchorXUnits:'fraction',
				anchorYUnits:'fraction',
				src:'/static/map/images/defaultPoint.png'
			}))
		})
	});
	map.addLayer(vectorLayer);
	map.on('click', clickMap);

	//清空路径规划结果
	var clearButton = document.getElementById('clear');
	clearButton.addEventListener('click', function(event) {
		// Reset the "start" and "destination" features.
    clearResult();
  });
}

function clearResult() {
	startPoint.setGeometry(null);
	destPoint.setGeometry(null);
	// Remove the result layer.
	map.removeLayer(result);
}

function clickMap(event) {
  if (startPoint.getGeometry() != null && destPoint.getGeometry() != null) {
    clearResult();
  }

	if (startPoint.getGeometry() == null) {
    // First click.
    startPoint.setGeometry(new ol.geom.Point(event.coordinate));
    console.info(event.coordinate);
  } else if (destPoint.getGeometry() == null) {
    // Second click.
    destPoint.setGeometry(new ol.geom.Point(event.coordinate));
    console.info(event.coordinate);
    // Transform the coordinates from the map projection (EPSG:3857)
    // to the server projection (EPSG:4326).
    var startCoord = (startPoint.getGeometry().getCoordinates());
    var destCoord = (destPoint.getGeometry().getCoordinates());
    var viewparams = [
      'x1:' + startCoord[0], 'y1:' + startCoord[1],
      'x2:' + destCoord[0], 'y2:' + destCoord[1],
	  'styles:{width:5}'
	  //'x1:' + 12952117.2529, 'y1:' + 4836395.5717,
      //'x2:' + 12945377.2585, 'y2:' + 4827305.7549
    ];
    params.viewparams = viewparams.join(';');
	/*
	var netStyle = new ol.style.Style({
      stroke: new ol.style.Stroke({
        width: 3,
        color: [255, 0, 0, 1]
      })
    })
	params.STYLES = netStyle
	*/
    result = new ol.layer.Image({
      source: new ol.source.ImageWMS({
        url:ServerUrl + '/geoserver/network/wms',
        params: params
      })
    });
	   console.info(result);
    map.addLayer(result);
  }
}
