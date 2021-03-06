function validateFields() {
    var valMessages = "";
    var rbName = "";
    $(".req-val").each(function () {
        var val = $(this).val();
        if (val == "") {
            valMessages += "- <b><i>" + $(this).attr("data-labelName") + "</i></b> is required.<br>";
        }
    });
    $(".rb-helper").each(function () {
        rbName = $(this).val();
        var selected = $("input[name='" + rbName + "']:checked").val();
        if (!selected) {
            valMessages += "- <b><i>" + $(this).attr("data-labelName") + "</i></b> is required.<br>";
        }
    });
    var crashDate = getCrashDate();
    if (crashDate != null) {
        if (util.isFutureDate(crashDate)) {
            valMessages += "- <b><i>Crash date</i></b> cannot be a future date.<br/>";
        }
    }
    if ($("#gMaps")) {
        var latitude = parseFloat($("#tdLat").attr('data-lat-val'));
        var longitude = parseFloat($("#tdLon").attr('data-lon-val'));
        if(latitude <= -2 || latitude >= 3) {
            valMessages += "- <b><i>GPS latitude</i></b> value provided seems to be outside the country.<br/>";
        }
        if(longitude < 29.5 || longitude > 34.8) {
            valMessages += "- <b><i>GPS longitude</i></b> value provided seems to be outside the country.<br/>";
        }
    }
    if (valMessages != "") {
        ui.alertDialog({message: valMessages});
        return false;
    }
    return true;;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        validationWarningMessagevalidationWarningMessagevalidationWarningMessagevalidationWarningMessage;
}

function validateInteger(num) {
    var rx = RegExp('^([\-\+]?)[0-9]*$');
    return rx.test(num);
}

function getCrashInfoContent(crash) {
    var crashViewUrl = util.basePath() + 'crashview';
    var content = "<table class='infoWindow'><tr><th colspan='2'>";
    content = content.concat(generateCrashCode(crash))
        .concat("</th></tr><tr><td class='boldText'>Tar No.</td><td>").concat(crash.tarNo)
        .concat("</td></tr><tr><td class='boldText'>Date & Time</td><td>").concat(crash.crashDateTimeString)
        .concat("</td></tr><tr><td class='boldText'>Police Station</td><td>").concat(crash.policeStation.name)
        .concat("</td></tr><tr><td class='boldText'>District</td><td>").concat(crash.policeStation.district.name)
        .concat("</td></tr><tr><td class='boldText'>Crash Severity</td><td>").concat(crash.crashSeverity ? crash.crashSeverity.name : "")
        .concat("</td></tr><tr><td class='boldText'>Collision Type</td><td>").concat(crash.collisionType ? crash.collisionType.name : "")
        .concat("</td></tr><tr><td class='boldText'>Latitude</td><td>").concat(displayCoordinate(crash.latitude))
        .concat("</td></tr><tr><td class='boldText'>Longitude</td><td>").concat(displayCoordinate(crash.longitude))
        .concat("</td></tr><tr><td class='boldText'>Vehicles</td><td>").concat(crash.vehicles ? crash.vehicles.length : "")
        .concat("</td></tr><tr><td class='boldText'>Casualties</td><td>").concat(crash.casualties ? crash.casualties.length : "")
        .concat("</td></tr><tr><td><a href='").concat(crashViewUrl).concat("?id=").concat(crash.id).concat("'>View Crash</a>")
        .concat("</td></tr></table>");
    return content;
}

function generateCrashCode(crash) {
    var uniqueCode = "Crash-";
    uniqueCode = uniqueCode.concat(crash.id).concat(" [").concat(crash.tarNo).concat("]");
    return uniqueCode;
}

function displayCoordinate(coordinate) {
    if($.trim(coordinate) == "") {
        return "";
    }
    var coordParts = coordinate.split(" ");
    return coordParts[0].concat("&deg ").concat(coordParts[1]).concat("\'");
}

/***
 Last accessed highlight functions
 ***/
function setAccessedObject(element) {

    if(isBrowserIE7OrLower()) {
        return;
    }

    var attrName = $("#accessAttributeName").val();
    var tr = getElementRow(element);
    var dataElement = $(tr).find("a["+attrName+"]");
    var idValue = $(dataElement).attr(attrName);
    setIdByAttributeName(attrName, idValue);
    clearHighlight();
    $(tr).find('td').each(function(){
        if($(this).is(":visible")) {
            $(this).addClass("borderHighlight");
        }
    });

    //Remove inherited border highlight from cell contents
    if(browserIsIE()){
        removeInheritedHighlightForIE(tr[0]);
    } else {
        removeInheritedHighlight(tr);
    }
}

function getElementRow(element) {
    var tr = $(element).closest("tr");
    while ($(tr).hasClass("innerTable")) {
        var table = $(tr).closest("table");
        tr = $(table).closest("tr");
    }
    return tr;
}

function removeInheritedHighlight(tr) {

    $(tr).find('td').each(function() {
        removeHighlightFromCellDescendants(this);
    });
}

function removeInheritedHighlightForIE(row){

    for (var i = 0; i < row.cells.length; i++) {
        removeHighlightFromCellDescendants(row.cells[i]);
    }
}

function clearHighlight(withRowSpan) {

    $("td.borderHighlight").each(function(){
        $(this).removeClass("borderHighlight");
    });
    if(withRowSpan) {
        $("td.borderHighlightTop").each(function(){
            $(this).removeClass("borderHighlightTop");
        });
        $("td.borderHighlightBottom").each(function(){
            $(this).removeClass("borderHighlightBottom");
        });
    }
}

function highlightLastAccessedObject() {

    var attrName = $("#accessAttributeName").val();
    if(attrName) {
        var dataElement = $("a[" + attrName + "='" + getIdByAttributeName(attrName) + "']");
        if(dataElement && $(dataElement).is("a")) {
            setAccessedObject(dataElement);
        }
    }
}

function getIdByAttributeName(attrName) {
    if(!isBrowserIE7OrLower()){
        return sessionStorage[attrName];
    }
}

function setIdByAttributeName(attrName, idValue) {
    if(!isBrowserIE7OrLower()){
        sessionStorage[attrName] = idValue;
    }
}

function removeHighlightFromCellDescendants(td){

    $(td).find(".borderHighlight").each(function(){
        $(this).removeClass("borderHighlight");
    });
}

function isBrowserIE7OrLower(){
    if(browserIsIE()  && browserIsIE() < 8) {
        return true;
    }
    return false;
}

/**
 * detect IE
 * returns version of IE or false, if browser is not Internet Explorer
 */
function browserIsIE() {
    var ua = window.navigator.userAgent;

    var msie = ua.indexOf('MSIE ');
    if (msie > 0) {
        // IE 10 or older => return version number
        return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
    }

    var trident = ua.indexOf('Trident/');
    if (trident > 0) {
        // IE 11 => return version number
        var rv = ua.indexOf('rv:');
        return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
    }

    var edge = ua.indexOf('Edge/');
    if (edge > 0) {
        // IE 12 => return version number
        return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
    }

    // other browser
    return false;
}

/***
 End of Last accessed highlight functions
 ***/

function initDistrictSelectChange() {
    var policeStationJson ='{"policeStations" : ' + $("#policeStationJson").val() + '}';
    var policeStations = $(JSON.parse(policeStationJson).policeStations);
    var selectedPoliceStation = $(".police-station-select").val();
    $(".district-select").change(function() {
        var selectedDistrict = $(this).val();
        var policeStationSelect = $(".police-station-select");
        policeStationSelect.find("option:not(:first)").remove();
        policeStations.map(function() {
            if(this.district.id == selectedDistrict) {
                policeStationSelect.append("<option value=\"" + this.id + "\">" + this.name + "</option>");
            }
        });
    });
    $(".district-select").trigger("change");
    $(".police-station-select").val(selectedPoliceStation);
}

function crashQueryFilterPoliceStations() {
    if ($('.district:checked').length) {
        $('.district').each(function(){
            var districtId = $(this).attr('data-id');
            var selected = this.checked;
            $('.policeStation[data-district-id=' + districtId + ']').each(function() {
                if (selected)
                    $(this).closest('tr').show('slow');
                else
                    $(this).closest('tr').hide('slow');
            })
        });
    } else {
        $('.policeStation').closest('tr').show('slow');
    }
}

function showHideDateControls() {
    var selected = $('.year-month-range option:selected').filter(function(){
        return $(this).val().length > 0;
    });
    if (selected.length) {
        $('.dtpicker').closest('tr').hide('slow');
    } else {
        $('.dtpicker').closest('tr').show('slow');
    }
}