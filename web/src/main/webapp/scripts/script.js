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
	$(".req-val").each(function() {
		var val = $(this).val();
		if (val == "") {
			valMessages += $(this).attr("data-labelName") + " is required.\n";
		}
	});
	$(".rb-helper").each(function() {
		rbName = $(this).val();
		var selected = $("input[name='" + rbName + "']:checked").val();
		if (!selected) {
			valMessages += $(this).attr("data-labelName") + " is required.\n";
		}
	});
	if (valMessages != "") {
		alert(valMessages);
		return false;
	}
	return true;
}

function createOptions(divId, chartTitle) {
	var options = {
		chart : {
			renderTo : divId,
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		title : {
			text : chartTitle
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 2)
						+ ' %';
			}
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					formatter : function() {
						return '<b>' + this.point.name + '</b>: '
								+ Highcharts.numberFormat(this.percentage, 2) + ' %';
					}
				}
			}
		},
		series : []
	};
	return options;
}

function loadCrashSeverityChart() {
	$.ajax({
		url : "/crashchartseverity",
		success : function(result) {
			var options = createOptions("container-severity",
					"Crash Severity");
			options.series = $.parseJSON(result);
			var chart = new Highcharts.Chart(options);
		}
	});
}

function loadCrashCauseChart() {
	$.ajax({
		url : "/crashchartcause",
		success : function(result) {
			var options = createOptions("container-cause",
					"Main Cause of Crash");
			options.series = $.parseJSON(result);
			var chart = new Highcharts.Chart(options);
		}
	});
}

function loadInGoogleMaps() {
    loadDialog({message: "Loading map..."});
    initializeMap();
}

function loadDialog(params) {
    $("<div id='map-canvas' title='Google Map'>" +
        "<div style='clear: both; margin-top: 2%; text-align: justify'>" +
        params.message +
        "</div></div>").appendTo("body");

    $("#map-canvas").dialog({
        autoOpen : true,
        closeOnEscape: false,
        modal : true,
        height: 600,
        width: 850,
        buttons:{
            'Close': function() {
                $("#map-canvas").remove();
            }
        },

        open: function(event, ui) {

            openDialog({
                dialogDiv: this,
                cancelButtonValue: "Close",
                okButtonValue: null
            });
        }
    });
    return false;
}

function openDialog(params) {
    $(".ui-dialog-titlebar-close", $(params.dialogDiv).parent()).hide();
    $('.ui-dialog-titlebar').css('border', '1px Solid #2C6CAF');
    $('.ui-widget-content').css('border', '0');
    $('.ui-dialog').zIndex(2000);
    $('.ui-dialog .ui-dialog-titlebar').find('span').css('color', "#2C6CAF");
}

function setButtonAttributes(buttonValue){

    $('.ui-dialog-buttonpane').find('button:contains("' + buttonValue + '")')
        .removeAttr('class').addClass('button_medium').css('width', '120px').css('float', 'left').css('line-height', '0').css('padding','8px 0');
    $('.ui-dialog-buttonpane').find('button:contains("' + buttonValue + '")')
        .mouseover(function() {$(this).removeClass("ui-state-hover");})
        .focus(function () {$(this).removeClass("ui-state-focus");});
    $('.ui-dialog-buttonpane').find('button:contains("' + buttonValue + '")').blur();
}

function setDialogTheme(params) {
    if (params.cancelButtonValue)
        $('.ui-dialog-buttonpane').find('button:contains("' + params.cancelButtonValue + '")').css('background-image', 'url(images/button_medium_BG.gif)');
    if (params.okButtonValue)
        $('.ui-dialog-buttonpane').find('button:contains("' + params.okButtonValue + '")').css('background-image', 'url(images/button_medium_BG.gif)');

    $('.ui-dialog .ui-dialog-titlebar').find('span').css('color', "#2C6CAF");
}

function initializeMap() {
    if($("#gMaps")) {
        var latitude = parseFloat($("#tdLat").attr('data-lat-val'));
        var longitude = parseFloat($("#tdLon").attr('data-lon-val'));
        var crashTitle = "Crash TAR No.: " + $("#tdTarNo").attr("data-crash-tarNo");
        var coordinates = new google.maps.LatLng(latitude,longitude);
        var mapOptions = {
            center: coordinates,
            zoom: 15,
            mapTypeId:google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
        var marker = new google.maps.Marker({
            position: coordinates,
            map: map,
            animation: google.maps.Animation.DROP,
            title: crashTitle
        });
    }
}

function defineLat() {
    var latitude = $("#latLetter").val();
    var latDeg = "" + $("#latDeg").val();
    if(latDeg.length > 0) {
        var degNum = parseInt(latDeg);
        if (isNaN(degNum)) {
            alert("Latitude degrees value must be numeric");
            $("#latDeg").select();
            return;
        }
        if (degNum > 90 || degNum < -90) {
            alert("Latitude degrees value must be between -90 and 90");
            $("#latDeg").select();
            return;
        }
        if (degNum < 10 || degNum > -10) {
            latitude += "0" + degNum;
        } else {
            latitude += "" + degNum;
        }
        var latMins = "" + $("#latMins").val();
        if(latMins.length > 0) {
            var minNum = parseFloat(latMins);
            if(isNaN(minNum)) {
                alert("Latitude minutes value must be numeric");
                return;
            }
            if(minNum >= 60) {
                alert("Latitude minutes value cannot be greater than 60.");
                return;
            }
            if(minNum < 10) {
                latitude += " 0" + minNum;
            } else {
                latitude += " " + minNum;
            }
        } else {
            latitude += "00 00"
        }
    } else {
        latitude = "";
    }
    document.getElementById("latitude").value = latitude;
}
