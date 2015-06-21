/**
 * Created by Frank on 5/24/15.
 */
var markers = [];
var policeRegions = new google.maps.KmlLayer({
    url: 'https://sites.google.com/site/hagafrank/kml/PoliceRegion.kmz'
});
var unraARoads = new google.maps.KmlLayer({
    url: 'https://sites.google.com/site/hagafrank/kml/UNRA_A_Road.kmz'
});
var unraBRoads = new google.maps.KmlLayer({
    url: 'https://sites.google.com/site/hagafrank/kml/UNRA_B_Road.kmz'
});
var crashAttributes = JSON.parse(localStorage.crashAttributesJSON);
var markerColors = [];
markerColors.push('FF0000');
markerColors.push('FFA500');
markerColors.push('FFFF00');
markerColors.push('00FF00');
markerColors.push('00FFFF');
markerColors.push('ADD8E6');
markerColors.push('00FF00');
markerColors.push('C0C0C0');
markerColors.push('E5E4E2');
markerColors.push('CFECEC');
markerColors.push('E0FFFF');
markerColors.push('EBF4FA');
markerColors.push('F0F8FF');
markerColors.push('F0FFFF');
markerColors.push('CCFFFF');
markerColors.push('FFF380');
markerColors.push('FFFFC2');
markerColors.push('FFF8C6');
markerColors.push('FFF8DC');
markerColors.push('F5F5DC');
markerColors.push('FAEBD7');
markerColors.push('F7E7CE');
markerColors.push('F3E5AB');
markerColors.push('ECE5B6');
markerColors.push('FFE5B4');
markerColors.push('FDEEF4');
markerColors.push('FFF5EE');
markerColors.push('FEFCFF');

var drawingManager = drawingManager = new google.maps.drawing.DrawingManager({
    drawingMode: google.maps.drawing.OverlayType.MARKER,
    drawingControl: true,
    drawingControlOptions: {
        position: google.maps.ControlPosition.TOP_CENTER,
        drawingModes: [
            google.maps.drawing.OverlayType.MARKER,
            google.maps.drawing.OverlayType.CIRCLE,
            google.maps.drawing.OverlayType.POLYGON,
            google.maps.drawing.OverlayType.POLYLINE,
            google.maps.drawing.OverlayType.RECTANGLE
        ]
    },
    circleOptions: {
        fillColor: '#6AA121',
        fillOpacity: 0.2,
        strokeWeight: 2,
        strokeOpacity: 0.8,
        clickable: true,
        editable: true,
        zIndex: 1
    },
    polygonOptions: {
        fillColor: '#6AA121',
        fillOpacity: 0.2,
        strokeWeight: 2,
        strokeOpacity: 0.8,
        clickable: true,
        editable: true,
        zIndex: 1
    },
    rectangleOptions: {
        fillColor: '#6AA121',
        fillOpacity: 0.2,
        strokeWeight: 2,
        strokeOpacity: 0.8,
        clickable: true,
        editable: true,
        zIndex: 1
    }
});
google.maps.event.addListener(drawingManager, 'circlecomplete', function(circle) {
    alert('Complete');
    google.maps.event.addListener(circle, 'center_changed', function() {
        alert('Circle Moved');
    });
    google.maps.event.addListener(circle, 'radius_changed', function() {
        alert('Radius Changed');
    });
});