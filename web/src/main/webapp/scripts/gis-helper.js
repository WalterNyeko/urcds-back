/**
 * Created by Frank on 11/16/14.
 */
/** Contains GIS specific functions ...*/
function loadInGoogleMaps() {
    loadDialog({message: "Loading map...", dialogTitle: "Crash Location - Google Maps" });
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
    var crashesJSON = JSON.parse(localStorage.crashesJSON);
    var crashes = crashesJSON ? crashesJSON.crashes : undefined;
    var markers = getCrashMarkers(crashes);
    var center = markers.length ? markers[0].getPosition() : getDefaultPosition();
    var map = initGoogleMap(center, 8);
    $(markers).each(function () {
        this.setMap(map);
        google.maps.event.addListener(this, 'click', function () {
            this.infoWindow.open(map, this);
        });
    });
}

function getDefaultPosition() {
    return new google.maps.LatLng(0.317416, 32.5943618);
}

function getCrashMarkers(crashes) {
    var markers = [];
    if (crashes) {
        crashes.map(function (crash) {
            if (crash.latitudeNumeric && crash.longitudeNumeric) {
                var coordinates = new google.maps.LatLng(parseFloat(crash.latitudeNumeric), parseFloat(crash.longitudeNumeric));
                var marker = getCrashStyledMarker(crash, coordinates);
                marker.infoWindow = new google.maps.InfoWindow({
                    content: getCrashInfoContent(crash)
                });
                markers.push(marker);
            }
        });
    }
    return markers;
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
    if (crash.crashSeverity) {
        switch (crash.crashSeverity.id) {
            case 1:
                color = "FF0000";
                text = "F";
                break;
            case 2:
                color = "FFA500";
                text = "S";
                break;
            case 3:
                color = "FFFF00";
                text = "S";
                break;
            case 4:
                color = "00FF00";
                text = "D";
                break;
        }
    }
    return new StyledIcon(StyledIconTypes.MARKER, {color: color, text: text});
}

function quickMapView(crashTitle, latitude, longitude) {
    loadDialog({message: "Loading map...", dialogTitle: "Crash Location - Google Maps" });
    var coordinates = new google.maps.LatLng(latitude, longitude);
    initMap(coordinates, crashTitle);
    return false;
}

function addPolygon(map) {
    var busega = new google.maps.LatLng(0.3122877, 32.5209336);
    var mityana = new google.maps.LatLng(0.391336, 32.120147);
    var buwama = new google.maps.LatLng(0.1194276, 32.2561031);
    var polygon = [busega, mityana, buwama];

    var mpigiPolygon = new google.maps.Polygon({
        path: polygon,
        strokeColor: "#0000FF",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#0000FF",
        fillOpacity: 0.2
    });
    mpigiPolygon.setMap(map);
}