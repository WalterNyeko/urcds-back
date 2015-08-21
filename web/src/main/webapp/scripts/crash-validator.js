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
    util.initFormChangeDetection('#crashForm');
}

function initVehicleAndCasualtyValidation() {
    validationFunctions.push(checkCasualtyTypes);
    util.initFormChangeDetection('#crashForm');
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
    if (crashDate == null) {
        return;
    }
    var today = new Date();
    if (crashDate > today) {
        validationWarningMessage.push("The <b><i>crash date</i></b> specified is a future date.");
    }
}

var getCrashDate = function() {
    var dateString = $.trim($("#crashDateTimeString").val());
    if (!dateString.length) {
        return null;
    }
    var dataSplit = dateString.split('-');
    var dateConverted;

    if (dataSplit[2].split(" ").length > 1) {

        var hora = dataSplit[2].split(" ")[1].split(':');
        dataSplit[2] = dataSplit[2].split(" ")[0];
        dateConverted = new Date(dataSplit[0], dataSplit[1] - 1, dataSplit[2], hora[0], hora[1]);

    } else {
        dateConverted = new Date(dataSplit[0], dataSplit[1] - 1, dataSplit[2]);
    }
    return dateConverted;
}

var checkGpsCoordinateLimits = function() {
    if ($("#gMaps")) {
        var latitude = parseFloat($("#tdLat").attr('data-lat-val'));
        var longitude = parseFloat($("#tdLon").attr('data-lon-val'));
        if(latitude <= -2 || latitude >= 3) {
            validationWarningMessage.push("The <b><i>GPS latitude</i></b> value provided seems to be outside the country.");
        }
        if(longitude < 29.5 || longitude > 34.8) {
            validationWarningMessage.push("The <b><i>GPS longitude</i></b> value provided seems to be outside the country.");
        }
    }
}

var checkWeather = function() {
    if($("#crashForm input[name='weather.id']:checked").val() == 2 && $("#crashForm input[name='roadSurface.id']:checked").val() == 2) {
        validationWarningMessage.push("You specified weather as <b><i>Rain</i></b> and Road surface as <b><i>Dry</i></b>");
    }
}

var checkRoadwayCharacter = function() {
    if($("#crashForm input[name='roadwayCharacter.id']:checked").val() == 1 && $("#crashForm input[name='junctionType.id']:checked").val() == 4) {
        validationWarningMessage.push("You specified Character of Roadway as <b><i>Straight slight curve</i></b> and Junction Type as <b><i>Roundabout</i></b>");
    }
}

var checkCasualtyTypes = function() {
    if ($('input[type="hidden"][data-name="casualtyType"]').length) {
        var casualtyTypes = [];
        var crashSeverityId = parseInt($('#crashSeverityId').val());
        $('input[type="hidden"][data-name="casualtyType"]').each(function() {
           casualtyTypes.push(parseInt(this.value));
        });
        var validator = crashSeverityValidation.getValidator(crashSeverityId);
        if (validator) {
            validator.validate(casualtyTypes);
            validator.warningMessages.forEach(function(x) {
                validationWarningMessage.push(x);
            });
        }
    }
}

var validateYearMonthRange = function(displayError) {
    var startYear = $('#startYear').val().length ? parseInt($('#startYear').val()) : 0;
    var startMonth = $('#startMonth').val().length ? parseInt($('#startMonth').val()) : 0;
    var endYear = $('#endYear').val().length ? parseInt($('#endYear').val()) : 0;
    var endMonth = $('#endMonth').val().length ? parseInt($('#endMonth').val()) : 0;
    if(startYear > endYear && endYear != 0) {
        displayError('Start Year cannot be greater than End Year');
        return false;
    }
    if(startYear == endYear && startMonth > endMonth && endMonth != 0) {
        displayError('Start Month cannot be greater than End Month');
        return false;
    }
    if(startYear > 0 && startMonth > 0 && endMonth > 0 && endYear == 0) {
        displayError('Please select End Year');
        return false;
    }
    if(startYear == 0 && startMonth > 0 && endMonth > 0 && endYear > 0) {
        displayError('Please select Start Year');
        return false;
    }
    return true;
}

var displayYearMonthRangeError = function(message) {
    $("#year-month-range-error").children('td').html(message);
    $("#year-month-range-error").show();
    setTimeout(function() {
        $("#year-month-range-error").fadeOut(6000);
    }, 1000);
}
var validateCrashSearch = function() {
    if(!validateYearMonthRange(displayYearMonthRangeError)) {
        return false;
    }
    return true;
}

var crashSeverityValidation = (function() {
    var crashSeverityValidation = Object.create(null);
    crashSeverityValidation.severityValidations = [];

    crashSeverityValidation.fatalCrashValidator = function() {
        return {
            severityId : 1,
            mustHaveCasualtyType : 1,
            warningMessages : [],
            validate : function(casualtyTypes) {
                this.warningMessages.length = 0;
                var ctx = this;
                if (!casualtyTypes.filter(function(x) { return x === ctx.mustHaveCasualtyType }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Fatal</i></b> but none of the casualties or drivers is fatally injured');
                }
            }
        };
    }
    crashSeverityValidation.seriousCrashValidator = function() {
        return {
            severityId : 2,
            mustHaveCasualtyType : 2,
            mustNotHaveCasualtyTypes : [1],
            casualtyTypeInvalid : function(casualtyTypeId) {
                return this.mustNotHaveCasualtyTypes.indexOf(casualtyTypeId) > -1;
            },
            warningMessages : [],
            validate : function(casualtyTypes) {
                this.warningMessages.length = 0;
                var ctx = this;
                if (!casualtyTypes.filter(function(x) { return x === ctx.mustHaveCasualtyType }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Serious</i></b> but none of the casualties or drivers is seriously injured.');
                }
                if (casualtyTypes.filter(function(x) { return ctx.casualtyTypeInvalid(x) }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Serious</i></b> but at least one of the casualties or drivers is fatally injured.');
                }
            }
        };
    }
    crashSeverityValidation.slightCrashValidator = function() {
        return {
            severityId : 3,
            mustHaveCasualtyType : 3,
            mustNotHaveCasualtyTypes : [1, 2],
            casualtyTypeInvalid : function(casualtyTypeId) {
                return this.mustNotHaveCasualtyTypes.indexOf(casualtyTypeId) > -1;
            },
            warningMessages : [],
            validate : function(casualtyTypes) {
                this.warningMessages.length = 0;
                var ctx = this;
                if (!casualtyTypes.filter(function(x) { return x === ctx.mustHaveCasualtyType }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Slight</i></b> but none of the casualties or drivers is slightly injured.');
                }
                if (casualtyTypes.filter(function(x) { return ctx.casualtyTypeInvalid(x) }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Slight</i></b> but at least one of the casualties or drivers is fatally or seriously injured.');
                }
            }
        };
    }
    crashSeverityValidation.damageOnlyCrashValidator = function() {
        return {
            severityId : 4,
            mustHaveCasualtyType : 4,
            mustNotHaveCasualtyTypes : [1, 2, 3],
            casualtyTypeInvalid : function(casualtyTypeId) {
                return this.mustNotHaveCasualtyTypes.indexOf(casualtyTypeId) > -1;
            },
            warningMessages : [],
            validate : function(casualtyTypes) {
                this.warningMessages.length = 0;
                var ctx = this;
                if (!casualtyTypes.filter(function(x) { return x === ctx.mustHaveCasualtyType }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Damage Only</i></b> but none one of the casualties or drivers is not injured.');
                }
                if (casualtyTypes.filter(function(x) { return ctx.casualtyTypeInvalid(x) }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Damage Only</i></b> but at least one of the casualties or drivers is fatally, seriously or slightly injured.');
                }
            }
        };
    }

    crashSeverityValidation.unknownCrashSeverityValidator = function() {
        return {
            severityId : 5,
            mustHaveCasualtyType : 5,
            mustNotHaveCasualtyTypes : [1, 2, 3, 4],
            casualtyTypeInvalid : function(casualtyTypeId) {
                return this.mustNotHaveCasualtyTypes.indexOf(casualtyTypeId) > -1;
            },
            warningMessages : [],
            validate : function(casualtyTypes) {
                this.warningMessages.length = 0;
                var ctx = this;
                if (casualtyTypes.filter(function(x) { return ctx.casualtyTypeInvalid(x) }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Unknown</i></b> but a known casualty type has been specified for at least one of the casualties or drivers.');
                }
            }
        };
    }
    crashSeverityValidation.getValidator = function(crashSeverityId) {
        return this.severityValidations.filter(function(x) { return x.severityId === crashSeverityId; })[0];
    }
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.fatalCrashValidator());
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.seriousCrashValidator());
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.slightCrashValidator());
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.damageOnlyCrashValidator());
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.unknownCrashSeverityValidator());
    return crashSeverityValidation;
})();