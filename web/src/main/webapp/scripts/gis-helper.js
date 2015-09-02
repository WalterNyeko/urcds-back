/**
 * Created by Frank on 11/16/14.
 */
/** Contains GIS specific functions ...*/
function loadInGoogleMaps() {
    loadDialog({
        width: 850,
        height: 600,
        message: "Loading map...",
        dialogTitle: "Crash Location - Google Maps"
    });
    initializeSingleCrashMap();
}

function validateGpsCoordinates() {
    return defineGpsCoord("lat") && defineGpsCoord("lon");
}

function loadGpsCoordinates() {
    var latitude = $.trim($("#latitude").val());
    var longitude = $.trim($("#longitude").val());
    if (latitude.length > 0) {
        var latparts = latitude.split(" ");
        $("#latDegs").val(latparts[0].substr(1));
        $("#latMins").val(latparts[1]);
        $("#latLetter").val(latparts[0].substr(0, 1));
    }
    if (longitude.length > 0) {
        var lonparts = longitude.split(" ");
        $("#lonDegs").val(lonparts[0].substr(1));
        $("#lonMins").val(lonparts[1]);
    }
}

function initGoogleMap(coordinates, zoom) {
    var mapOptions = {
        center: coordinates,
        zoom: zoom,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById('map-canvas'),
        mapOptions);
    return map;
}
function initMap(coordinates, crashTitle) {
    var map = initGoogleMap(coordinates, 10);
    var marker = new google.maps.Marker({
        position: coordinates,
        map: map,
        animation: google.maps.Animation.DROP,
        title: crashTitle
    });
}
function initializeSingleCrashMap() {
    if ($("#gMaps")) {
        var latitude = parseFloat($("#tdLat").attr('data-lat-val'));
        var longitude = parseFloat($("#tdLon").attr('data-lon-val'));
        var crashTitle = "Crash TAR No.: " + $("#tdTarNo").attr("data-crash-tarNo");
        var coordinates = new google.maps.LatLng(latitude, longitude);
        initMap(coordinates, crashTitle);
    }
}

function defineGpsCoord(coord) {
    var isLat = coord == "lat";
    var coordinate = isLat ? $("#latLetter").val() : $("#lonLetter").val();
    var degTb = isLat ? $("#latDegs") : $("#lonDegs");
    var minTb = isLat ? $("#latMins") : $("#lonMins");
    var maxDegs = isLat ? 90 : 180;
    var coordText = isLat ? "Latitude" : "Longitude";
    var coordDegs = $.trim($(degTb).val());

    if (coordDegs.length > 0) {
        if (!validateInteger(coordDegs)) {
            alertDialog({ message: coordText + " degrees value must be numeric", focusTarget: $(degTb) });
            mapButton(false);
            return false;
        }
        if (coordDegs.indexOf("+") > -1) {
            coordDegs = coordDegs.substr(1);
            $(degTb).val(coordDegs);
        }
        var degNum = parseInt(coordDegs, 10);
        if (degNum > maxDegs || degNum < 0) {
            alertDialog({ message: coordText + " degrees value must be between 0 and " + maxDegs, focusTarget: $(degTb) });
            mapButton(false);
            return false;
        }
        if (isLat && degNum < 10) {
            coordinate += "0" + degNum;
        } else if (!isLat && degNum >= 10 && degNum < 100) {
            coordinate += "0" + degNum;
        } else if (degNum < 10) {
            coordinate += "00" + degNum;
        } else {
            coordinate += "" + degNum;
        }
        var coordMins = "" + $(minTb).val();
        if (coordMins.length > 0) {
            var minNum = parseFloat(coordMins);
            if (isNaN(minNum)) {
                alertDialog({ message: coordText + " minutes value must be numeric", focusTarget: $(minTb) });
                mapButton(false);
                return false;
            }
            if (minNum >= 60) {
                alertDialog({ message: coordText + " minutes value cannot be greater than 60.", focusTarget: $(minTb) });
                mapButton(false);
                return false;
            }
            var minNumStr = "" + minNum.toFixed(3);
            if (minNum < 10) {
                coordinate += " 0" + minNumStr;
            } else {
                coordinate += " " + minNumStr;
            }
        } else {
            coordinate += " 00.000"
        }
    } else {
        coordinate = "";
    }
    document.getElementById(coordText.toLowerCase()).value = coordinate;
    generateCoordDegrees();
    return true;
}

function mapButton(show) {
    if (show) {
        $("#gMaps").show();
    } else {
        $("#gMaps").hide();
    }
}

function generateCoordDegrees() {
    var latDegs = $.trim($("#latDegs").val());
    var latMins = $.trim($("#latMins").val());
    var lonDegs = $.trim($("#lonDegs").val());
    var lonMins = $.trim($("#lonMins").val());

    if (latDegs.length == 0 || latMins.length == 0
        || lonDegs.length == 0 || lonMins.length == 0) {
        mapButton(false);
        return;
    }
    var latDecimals = ConvertDMSToDD({
        degrees: parseInt(latDegs),
        minutes: parseFloat(latMins),
        direction: $("#latLetter").val()
    });
    var lonDecimals = ConvertDMSToDD({
        degrees: parseInt(lonDegs),
        minutes: parseFloat(lonMins),
        direction: $("#lonLetter").val()
    });
    $("#tdLat").attr('data-lat-val', latDecimals);
    $("#tdLon").attr('data-lon-val', lonDecimals);
    mapButton(true);
}

function ConvertDMSToDD(params) {
    var dd = params.degrees;
    if (params.minutes) {
        dd += (params.minutes / 60);
    }
    if (params.seconds) {
        dd += params.seconds / (60 * 60);
    }
    dd = parseFloat(dd);
    if (params.direction == "S" || params.direction == "W") {
        dd *= -1;
    }
    return dd;
}

function showCrashesInGoogleMaps() {
    if (crashes) {
        defineCrashMarkers();
        var center = markers.length ? markers[0].getPosition() : getDefaultPosition();
        var map = initGoogleMap(center, 8);
        $(markers).each(function () {
            this.setMap(map);
            google.maps.event.addListener(this, 'click', function () {
                markers.map(function(x) {x.infoWindow.close() });
                this.infoWindow.open(map, this);
            });
        });
        addKmlLayers(map);
        generateLegend();
        showDrawingManager(false);
        $('#draw-enable').attr('checked', false);
        window.map = map;
    }
}

function getDefaultPosition() {
    return new google.maps.LatLng(0.317416, 32.5943618);
}

function defineCrashMarkers() {
    markers = [];
    if (crashes) {
        crashes.map(function (crash) {
            if (crash.latitudeNumeric && crash.longitudeNumeric) {
                var coordinates = new google.maps.LatLng(parseFloat(crash.latitudeNumeric), parseFloat(crash.longitudeNumeric));
                var marker = getCrashStyledMarker(crash, coordinates);
                marker.crashId = crash.id;
                marker.infoWindow = new google.maps.InfoWindow({
                    content: getCrashInfoContent(crash)
                });
                markers.push(marker);
            }
        });
    }
}

function getCrashStyledMarker(crash, coordinates) {
    return new StyledMarker({
        styleIcon: getCrashStyledIcon(crash),
        position: coordinates,
        animation: google.maps.Animation.DROP,
        title: crash.tarNo});
}

function getCrashStyledIcon(crash) {
    var color = "E0FFFF";
    var text = "";
    var crashAttribute = $('#crashAttribute').val();
    var attributes = window.crashAttributes[crashAttribute];
    var crashWeight = crashAttribute == 'weightRange';
    if (crash[crashAttribute] || crashWeight) {
        var attribute = attributes.filter(function(x) {
            var match = false;
            if (crashWeight) {
                if (x.maxWeight)
                    match = (crash.weight >= x.minWeight && crash.weight <= x.maxWeight);
                else
                    match = crash.weight >= x.minWeight;
            } else
                match = x.id == crash[crashAttribute]['id'];
            return match;
        })[0];
        if (attribute) {
            color = markerColors[attribute.id - 1];
            text = attribute.name ? attribute.name.charAt(0) : attribute.id + '';
        }
    }
    return new StyledIcon(StyledIconTypes.MARKER, {color: color, text: text});
}

function generateLegend() {
    var crashAttribute = $('#crashAttribute').val();
    var attributes = window.crashAttributes[crashAttribute];
    var markerLegend = $('#marker-legend');
    markerLegend.find('td').css('color', '#000');
    markerLegend.find('tr:not(.not-spec)').remove();
    attributes.map(function(attribute) {
        var row = $('<tr><td></td><td></td></tr>');
        row.find('td:first').attr('width', 30)
            .attr('align', 'center')
            .css('font-weight', 'bold')
            .text(attribute.name ? attribute.name.charAt(0) : attribute.id)
            .css('border', 'Solid #000 1px')
            .attr('bgcolor', markerColors[attribute.id - 1]);
        row.find('td:last').text('- '.concat(attribute.name ? attribute.name : attribute.label));
        markerLegend.append(row);
    });
    markerLegend.find('tr:last').after(markerLegend.find('tr.not-spec'));
}

function addKmlLayers(map) {
    clearKmlLayers();
    $('.kml-layer').each(function() {
        var id = $(this).attr('id');
        showKmlLayer(id, this.checked, map);
    });
}

function showKmlLayer(id, show, map) {
    switch(id) {
        case 'police-regions':
            show && policeRegions.setMap(map);
            break;
        case 'unra-a':
            show && unraARoads.setMap(map);
            break;
        case 'unra-b':
            show && unraBRoads.setMap(map);
            break;
    }
}

function clearKmlLayers() {
    policeRegions.setMap(null);
    unraARoads.setMap(null);
    unraBRoads.setMap(null);
}

function quickMapView(crashTitle, latitude, longitude) {
    loadDialog({
        width: 850,
        height: 600,
        message: "Loading map...",
        dialogTitle: "Crash Location - Google Maps"
    });
    var coordinates = new google.maps.LatLng(latitude, longitude);
    initMap(coordinates, crashTitle);
    return false;
}

function showDrawingManager(show, evt) {
    if(show) {
        drawingManager.setMap(map);
    } else {
        if (evt && $(evt.target).is('#draw-enable') && shapesExist()) {
            clearDrawings(function() {
                drawingManager.setMap(null);
                $('.drawing-actions').hide();
            });
        } else {
            drawingManager.setMap(null);
        }
    }
}

function clearShapes() {
    if (window.circle)
        window.circle.setMap(null);
    if (window.polygon)
        window.polygon.setMap(null);
    if (window.polyline)
        window.polyline.setMap(null);
    if (window.rectangle)
        window.rectangle.setMap(null);
}

function shapesExist() {
    return window.circle || window.polygon || window.polyline || window.rectangle;
}

function showMarkersInCircle(circle) {
    markers.map(function(marker) {
        if ((google.maps.geometry.spherical.computeDistanceBetween(marker.position, circle.getCenter()) <= circle.getRadius()))
            marker.setMap(window.map);
        else
            marker.setMap(null);
    });
}

function showMarkersInPolygon(polygon) {
    markers.map(function(marker) {
        if (google.maps.geometry.poly.containsLocation(marker.position, polygon))
            marker.setMap(window.map);
        else
            marker.setMap(null);
    });
}

function showMarkersInRectangle(rectangle) {
    markers.map(function(marker) {
        if (rectangle.getBounds().contains(marker.position))
            marker.setMap(window.map);
        else
            marker.setMap(null);
    });
}

function clearDrawings(callback) {
    return confirmDialog({
        message: 'Are you sure you want to clear drawings?',
        callback: function() {
            clearShapes();
            markers.map(function(marker) { marker.setMap(window.map); callback && callback() });
            $('.drawing-actions').hide();
        }
    })
}

function analyzeFiltered() {
    var filteredCrashIds = [];
    markers.map(function(marker) { (marker.getMap() != null) && filteredCrashIds.push(marker.crashId) });
    if (filteredCrashIds.length) {
        $('#selection-form').find('#crashIds').val(filteredCrashIds.toString());
        $('#selection-form').submit();
    } else {
        alertDialog({ message: 'No crashes were selected.'});
    }
    return false;
}