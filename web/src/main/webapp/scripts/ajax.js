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
            }
        },
        open: function () {
            openDialog({
                dialogDiv: this,
                showClose: true
            });
        }
    });
    params.responseDiv = $("#select-crash");
    params.rootElementId = "selectCrashForm";
    params.dialogDiv = $("#select-crash");
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
        },
        complete: function() {
            if(params.dialogDiv) {
                params.dialogDiv.dialog("option", "position", { my: "center", at: "center", of: window });
            }
        }
    });
}
