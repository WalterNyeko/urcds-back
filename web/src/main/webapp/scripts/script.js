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

function confirmDelete(obj) {
    var msg = "Are you sure you want to delete this " + obj + "?";
    ans = confirm(msg);
    return ans;
}

// 18n version of confirmDelete. Message must be already built.
function confirmMessage(obj) {
    var msg = "" + obj;
    ans = confirm(msg);
    return ans;
}

function validateFields() {
    var valMessages = "";
    var rbName = "";
    $(".req-val").each(function () {
        var val = $(this).val();
        if (val == "") {
            valMessages += "- " + $(this).attr("data-labelName") + " is required.<br>";
        }
    });
    $(".rb-helper").each(function () {
        rbName = $(this).val();
        var selected = $("input[name='" + rbName + "']:checked").val();
        if (!selected) {
            valMessages += "- " + $(this).attr("data-labelName") + " is required.<br>";
        }
    });
    if (valMessages != "") {
        alertDialog({message: valMessages});
        return false;
    }
    return true;
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
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function () {
                        return '<b>' + this.point.name + '</b>: '
                            + Highcharts.numberFormat(this.percentage, 2) + ' %';
                    }
                }
            }
        },
        series: []
    };
    return options;
}

function loadCrashSeverityChart() {
    $.ajax({
        url: "/crashchartseverity",
        success: function (result) {
            var options = createOptions("container-severity",
                "Crash Severity");
            options.series = $.parseJSON(result);
            var chart = new Highcharts.Chart(options);
        }
    });
}

function loadCrashCauseChart() {
    $.ajax({
        url: "/crashchartcause",
        success: function (result) {
            var options = createOptions("container-cause",
                "Main Cause of Crash");
            options.series = $.parseJSON(result);
            var chart = new Highcharts.Chart(options);
        }
    });
}

function loadDialog(params) {
    $("<div id='map-canvas' title='" + params.dialogTitle +"'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify'>" +
        params.message +
        "</div></div>").appendTo("body");

    $("#map-canvas").dialog({
        autoOpen: true,
        closeOnEscape: false,
        modal: true,
        height: 600,
        width: 850,
        buttons: {
            'Close': function () {
                $("#map-canvas").remove();
            }
        },

        open: function (event, ui) {

            openDialog({
                dialogDiv: this,
                cancelButtonValue: "Close"
            });
        }
    });
    return false;
}


function alertDialog(params) {

    $("<div id='alertDialog' title='Message Alert'>" +
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

        open: function (event, ui) {

            openDialog({
                dialogDiv: this
            });
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
        .concat("</td></tr><tr><td><a href='/crashview?id=").concat(crash.id).concat("'>View Crash</a>")
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