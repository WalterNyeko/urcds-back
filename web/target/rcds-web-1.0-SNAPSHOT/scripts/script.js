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
