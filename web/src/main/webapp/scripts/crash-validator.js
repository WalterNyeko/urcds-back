/**
 * Created by Frank on 2/17/15.
 */

var validationFunctions = [];
var validationWarningMessage = [];
function initCrashValidation() {
    validationFunctions.push(checkDate);
    validationFunctions.push(checkGpsCoordinateLimits);
    validationFunctions.push(checkWeather);
    validationFunctions.push(checkRoadwayCharacter);
}

var validateCrashData = function() {
    validationWarningMessage.length = 0;
    $(validationFunctions).each(function() {
        this.call();
    });

    if(validationWarningMessage.length) {
        var warningHtml = "<div id='warning-message'>Some irregularities were noted with your data. Please see below";
        warningHtml += "<ul>";
        $(validationWarningMessage).each(function() {
            warningHtml += "<li>" + this + "</li>";
        });
        warningHtml += "</ul></div>";
        clearWarningMessage();
        $("#warning-content").append(warningHtml);
        var $modal = $('#warning-modal');
        $modal.modal();
        return false;
    }
    return true;
}

var clearWarningMessage = function(){
    var p = $("#warning-message");
    if(p.length) {
        p.remove();
    }
}

var checkDate = function() {
    var crashDate = getCrashDate();
    var today = new Date();
    if (crashDate > today) {
        validationWarningMessage.push("The crash date specified is a future date.");
    }
}

var getCrashDate = function() {
    var dateString = $("#crashDateTimeString").val();
    var dataSplit = dateString.split('/');
    var dateConverted;

    if (dataSplit[2].split(" ").length > 1) {

        var hora = dataSplit[2].split(" ")[1].split(':');
        dataSplit[2] = dataSplit[2].split(" ")[0];
        dateConverted = new Date(dataSplit[2], dataSplit[1] - 1, dataSplit[0], hora[0], hora[1]);

    } else {
        dateConverted = new Date(dataSplit[2], dataSplit[1] - 1, dataSplit[0]);
    }
    return dateConverted;
}

var checkGpsCoordinateLimits = function() {
    if ($("#gMaps")) {
        var latitude = parseFloat($("#tdLat").attr('data-lat-val'));
        var longitude = parseFloat($("#tdLon").attr('data-lon-val'));
        if(latitude <= -2 || latitude >= 3) {
            validationWarningMessage.push("The GPS latitude value provided seems to be outside the country.");
        }
        if(longitude < 30 || longitude > 33) {
            validationWarningMessage.push("The GPS longitude value provided seems to be outside the country.");
        }
    }
}

var checkWeather = function() {
    if($("#crashForm input[name='weather.id']:checked").val() == 2 && $("#crashForm input[name='roadSurface.id']:checked").val() == 2) {
        validationWarningMessage.push("You specified weather as <i>Rain</i> and Road surface as <i>Dry</i>");
    }
}

var checkRoadwayCharacter = function() {
    if($("#crashForm input[name='roadwayCharacter.id']:checked").val() == 1 && $("#crashForm input[name='junctionType.id']:checked").val() == 4) {
        validationWarningMessage.push("You specified Character of Roadway as <i>Straight slight curve</i> and Junction Type as <i>Roundabout</i>");
    }
}