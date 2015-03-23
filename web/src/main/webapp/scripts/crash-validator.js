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
    initFormChangeDetection('#crashForm');
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
        validationWarningMessage.push("The crash date specified is a future date.");
    }
}

var getCrashDate = function() {
    var dateString = $.trim($("#crashDateTimeString").val());
    if (!dateString.length) {
        return null;
    }
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
var initFormChangeDetection = function(formName) {
    var doc = $(document);
    var form = $(formName);
    form.find(':text').each(function() {
        this.oldValue = this.value;
    });
    form.find('select').each(function() {
        this.oldValue = this.value;
    });
    form.find(':text').on('blur focusout paste', function() {
        detectTextChange(this);
    });
    form.find('select').change(function() {
        detectTextChange(this);
    });
    form.find(':radio').change(function() {
        dirtyFormObject();
        bindBeforeUnload();
    });
    form.find(':checkbox').change(function() {
        dirtyFormObject();
        bindBeforeUnload();
    });
    if(isFormObjectDirty()) {
        bindBeforeUnload();
    }
}

var detectTextChange = function(element) {
    if(element.oldValue !== element.value) {
        dirtyFormObject();
        bindBeforeUnload();
    }
}

var isFormObjectDirty = function() {
    return $('#dirty').val() === 'true';
}

var dirtyFormObject = function() {
    $('#dirty').val('true');
}

var bindBeforeUnload = function() {
    $(window).on('beforeunload', function() {
        return 'You have unsaved changes. They will be lost if you go ahead and leave this page without saving.';
    });
}

var unbindBeforeUnload = function() {
    $(window).off('beforeunload');
}

var crashSeverityValidation = (function() {
    var crashSeverityValidation = Object.create(null);
    crashSeverityValidation.severityValidations = [];
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.fatalCrashValidator());
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.seriousCrashValidator());
    crashSeverityValidation.severityValidations.push(crashSeverityValidation.slightCrashValidator());

    crashSeverityValidation.fatalCrashValidator = function() {
        return {
            severityId : 1,
            allowedCasualtyTypes : [1, 2, 3, 4, 5],
            mustHaveCasualtyType : 1,
            warningMessages : [],
            validate : function(casualtyTypes) {
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
            allowedCasualtyTypes : [2, 3, 4, 5],
            mustHaveCasualtyType : 2,
            mustNotHaveCasualtyTypes : [1],
            casualtyTypeInvalid : function(casualtyTypeId) {
                return this.mustNotHaveCasualtyTypes.indexOf(casualtyTypeId) > -1;
            },
            warningMessages : [],
            validate : function(casualtyTypes) {
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
            allowedCasualtyTypes : [3, 4, 5],
            mustHaveCasualtyType : 3,
            mustNotHaveCasualtyTypes : [1, 2],
            casualtyTypeInvalid : function(casualtyTypeId) {
                return this.mustNotHaveCasualtyTypes.indexOf(casualtyTypeId) > -1;
            },
            warningMessages : [],
            validate : function(casualtyTypes) {
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
            allowedCasualtyTypes : [4, 5],
            mustHaveCasualtyType : 4,
            mustNotHaveCasualtyTypes : [1, 2, 3],
            casualtyTypeInvalid : function(casualtyTypeId) {
                return this.mustNotHaveCasualtyTypes.indexOf(casualtyTypeId) > -1;
            },
            warningMessages : [],
            validate : function(casualtyTypes) {
                var ctx = this;
                if (!casualtyTypes.filter(function(x) { return x === ctx.mustHaveCasualtyType }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Damage Only</i></b> but at least one of the casualties or drivers is injured.');
                }
                if (casualtyTypes.filter(function(x) { return ctx.casualtyTypeInvalid(x) }).length) {
                    ctx.warningMessages.push('Crash severity is <b><i>Damage Only</i></b> but at least one of the casualties or drivers is fatally or seriously injured.');
                }
            }
        };
    }
    return crashSeverityValidation;
})();