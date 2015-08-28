// This function is used by the login screen to validate user/pass
// are entered.
function validateRequired(form) {
    var bValid = true;
    var focusField = null;
    var i = 0;
    var fields = new Array();
    oRequired = new required();

    for (x in oRequired) {
        if ((form[oRequired[x][0]].type == 'text'
            || form[oRequired[x][0]].type == 'textarea'
            || form[oRequired[x][0]].type == 'select-one'
            || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password')
            && form[oRequired[x][0]].value == '') {
            if (i == 0)
                focusField = form[oRequired[x][0]];

            fields[i++] = oRequired[x][1];

            bValid = false;
        }
    }

    if (fields.length > 0) {
        focusField.focus();
        alert(fields.join('\n'));
    }

    return bValid;
}

// This function is a generic function to create form elements
function createFormElement(element, type, name, id, value, parent) {
    var e = document.createElement(element);
    e.setAttribute("name", name);
    e.setAttribute("type", type);
    e.setAttribute("id", id);
    e.setAttribute("value", value);
    parent.appendChild(e);
}

// 18n version of confirmDelete. Message must be already built.
function confirmMessage(obj) {
    var msg = "" + obj;
    return confirm(msg);
}

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
        var today = new Date();
        if (crashDate > today) {
            valMessages += "- <b><i>Crash date</i></b> cannot be a future date.<br/>";
        }
    }
    if (valMessages != "") {
        alertDialog({message: valMessages});
        return false;
    }
    return true;;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        validationWarningMessagevalidationWarningMessagevalidationWarningMessagevalidationWarningMessage;
}

function createOptions(divId, chartTitle) {
    var options = {
        chart: {
            renderTo: divId,
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: chartTitle
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 2)
                    + ' %';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function () {
                        return '<b>' + this.point.name + '</b>: '
                            + Highcharts.numberFormat(this.percentage, 2) + ' %';
                    },
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                },
                showInLegend: true
            }
        },
        legend : {
            labelFormatter: function() {
                return this.name + ' (' + Highcharts.numberFormat(this.percentage, 2) + ' %)';
            }
        },
        series: []
    };
    return options;
}

function loadCrashSeverityChart() {
    var chartUrl = util.basePath() + 'crashchartseverity';
    $.ajax({
        url: chartUrl,
        success: function (result) {
            var options = createOptions("container-severity",
                "Crash Severity");
            options.series = $.parseJSON(result);
            $('.crashCount').html('(based on ' + options.series[0].crashCount + ' crashes)');
            $('.crashCount').css('margin-top', '0px');
            var chart = new Highcharts.Chart(options);
        }
    });
}

function loadCrashCauseChart() {
    var chartUrl = util.basePath() + 'crashchartcause';
    $.ajax({
        url: chartUrl,
        success: function (result) {
            var options = createOptions("container-cause",
                "Main Cause of Crash");
            options.series = $.parseJSON(result)
            var chart = new Highcharts.Chart(options);
        }
    });
}

function loadDialog(params) {
    $("<div id='map-canvas' title='" + params.dialogTitle +"'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify'>" +
        params.message +
        "</div></div>").appendTo("body");
    var dialogButtons = params.dialogButtons ? params.dialogButtons : { 'Close': function () { $('#map-canvas').remove() } };
    $('#map-canvas').dialog({
        modal: true,
        autoOpen: true,
        width: params.width,
        closeOnEscape: false,
        height: params.height,
        buttons: dialogButtons,
        open: function () {
            openDialog({
                dialogDiv: this
            });
        }
    });
    return false;
}

function alertDialog(params) {

    $("<div id='alertDialog' title='Message Alert' style='z-index: 3000;'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify;'>" +
        params.message +
        "</div></div>").appendTo("body");

    var height = params.height ? params.height : 180;
    var width = params.weight ? params.width : 400;

    $("#alertDialog").dialog({
        autoOpen: true,
        closeOnEscape: false,
        modal: true,
        height: height,
        width: width,
        buttons: {
            'OK': function () {
                $("#alertDialog").remove();
                if (params.focusTarget) {
                    $(params.focusTarget).focus();
                    $(params.focusTarget).select();
                }
            }
        },
        open: function () {
            openDialog({
                dialogDiv: this
            });
        }
    });
    return false;
}

function confirmDialog(params){

    $("<div id='confirmDialog' title='Confirm Action'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify'>" +
        params.message +
        "</div></div>").appendTo("body");

    var height = params.height ? params.height : 180;
    var width = params.weight ? params.width : 400;

    $("#confirmDialog").dialog({
        autoOpen : true,
        closeOnEscape: false,
        modal : true,
        height: height,
        width: width,
        buttons:{
            'Yes': function() {
                $("#confirmDialog").remove();
                if(params.aLink){
                    var href = $(params.aLink).attr('href');
                    util.unbindBeforeUnload();
                    window.location.href = href;
                } else if (params.redirectLink){
                    window.location.href = params.redirectLink;
                } else if (params.checkbox) {
                    var isChecked = $(params.checkbox).is(':checked');
                    $(params.checkbox).attr("checked", !isChecked);
                }
                params.callback && params.callback();
            },
            'No': function() {
                $("#confirmDialog").remove();
            }
        },
        open: function() {
            openDialog({
                dialogDiv: this
            });
            if(params.aLink && params.highlightRow) {
                setAccessedObjected(params.aLink);
            }
        }
    });
    return false;
}

function openDialog(params) {
    if(!params.showClose) {
        $(".ui-dialog-titlebar-close", $(params.dialogDiv).parent()).hide();
    }
    $('.ui-dialog-titlebar').css('border', '1px Solid #2C6CAF');
    $('.ui-widget-content').css('border', '0');
    $('.ui-dialog').zIndex(2000);
    $('.ui-dialog .ui-dialog-titlebar').find('span').css('color', "#2C6CAF");
    $('.ui-widget-overlay').zIndex(100);
}

function validateInteger(num) {
    var rx = RegExp('^([\-\+]?)[0-9]*$');
    return rx.test(num);
}

function validate24HrTime(time) {
    var rx = RegExp('([01]?[0-9]|2[0-3]):[0-5][0-9]');
    return rx.test(time);
}

function defineCrashTime() {
    if (!$("#crashTime").length) {
        return;
    }
    var time = $.trim($("#crashTime").val());
    if (time == "") {
        return;
    }
    if (!validate24HrTime(time)) {
        alertDialog({ message: "Crash time must be in 24-hour format", focusTarget: $("#crashTime") });
        return;
    }

    if (time.length == 4) {
        time = "0" + time;
    }

    var date = $('#crashDateTimeString').val();
    if (date.trim() == "") {
        return;
    }
    var dateParts = date.split(" ");
    if (dateParts.length > 1) {
        date = dateParts[0];
    }
    date = date + " " + time;
    $('#crashDateTimeString').val(date);

}

function loadCrashTime() {
    var date = $('#crashDateTimeString').val();
    if (date.trim() == "") {
        return;
    }
    var dateParts = date.split(" ");
    if (dateParts.length > 1) {
        var time = $.trim(dateParts[1]);
        $("#crashTime").val(time);
    }
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