/**
 * Created by Frank on 11/16/14.
 */
/** Contains Ajax specific functions ...*/

function loadSelectCrash(params) {
    $("<div id='select-crash' title='Select Crashes'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify'>Loading..." +
        "</div></div>").appendTo("body");

    $("#select-crash").dialog({
        autoOpen: true,
        closeOnEscape: false,
        modal: true,
        width: 850,
        buttons: {
            'Search': function () {
                if(validateCrashSearch()) {
                    $("#selectCrashForm").submit();
                }
            },
            'Cancel' : function () {
                $(".ui-dialog").remove();
                $(".ui-widget-overlay").remove();
            }
        },
        open: function () {
            openDialog({
                dialogDiv: this,
                showClose: false
            });
        }
    });
    params.responseDiv = $("#select-crash");
    params.rootElementId = "selectCrashForm";
    params.dialogDiv = $("#select-crash");
    sendRequest(params);
    return false;
}

function loadVehicleForm(params) {
    $("<div id='vehicle-dialog' style='min-width: 850px;' title='Vehicle Form'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify'>Loading..." +
        "</div></div>").appendTo("body");

    $("#vehicle-dialog").dialog({
        autoOpen: true,
        closeOnEscape: false,
        modal: true,
        width: 'auto',
        buttons: {
            'Save' : function () {
                $("#vehicleform").submit();

            },
            'Cancel' : function () {
                $(".ui-dialog").remove();
                $(".ui-widget-overlay").remove();
            }
        },
        open: function () {
            openDialog({
                dialogDiv: this,
                showClose: false
            });
        }
    });
    params.responseDiv = $("#vehicle-dialog");
    params.rootElementId = "vehicleform";
    params.dialogDiv = $("#vehicle-dialog");
    sendRequest(params);
    return false;
}

function loadCasualtyForm(params) {
    $("<div id='casualty-dialog' style='min-width: 1100px;' title='Casualty Form'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify'>Loading..." +
        "</div></div>").appendTo("body");

    $("#casualty-dialog").dialog({
        autoOpen: true,
        closeOnEscape: false,
        modal: true,
        width: 'auto',
        buttons: {
            'Save' : function () {
                if (validateFields()) {
                    $("#casualtyform").submit();
                }
            },
            'Cancel' : function () {
                $(".ui-dialog").remove();
                $(".ui-widget-overlay").remove();
            }
        },
        open: function () {
            openDialog({
                dialogDiv: this,
                showClose: false
            });
        }
    });
    params.responseDiv = $("#casualty-dialog");
    params.rootElementId = "casualtyform";
    params.dialogDiv = $("#casualty-dialog");
    sendRequest(params);
    return false;
}

function sendRequest(params) {
    $.ajax({
        url: params.url,
        success: function (result) {
            if(params.responseDiv) {
                $(params.responseDiv).html(result);
                if(params.rootElementId) {
                    var rootElement = $(params.responseDiv).find("#"+params.rootElementId);
                    $(params.responseDiv).html("");
                    $(params.responseDiv).append(rootElement);
                }
            }
            console.log(result);
        },
        complete: function() {
            if(params.dialogDiv) {
                params.dialogDiv.dialog("option", "position", { my: "center", at: "center", of: window });
            }
        }
    });
}
