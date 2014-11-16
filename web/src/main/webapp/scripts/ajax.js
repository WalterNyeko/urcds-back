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
        height: 400,
        width: 850,
        buttons: {
            'Close': function () {
                $("#select-crash").remove();
            }
        },

        open: function (event, ui) {

            openDialog({
                dialogDiv: this,
                cancelButtonValue: "Close"
            });
        }
    });
    loadSelectCrashForm(params);
    return false;
}

function loadSelectCrashForm(params) {
    $.ajax({
        url:params.url,
        success:function(result){
            $("#select-crash").html(result);
        }
    });
}
