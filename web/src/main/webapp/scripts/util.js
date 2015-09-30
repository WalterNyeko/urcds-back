var util = (function () {
    var util = Object.create(null);

    util.nameValuePair = function (name) {
        return { name: name, value: null};
    }
    util.nameListPair = function (name) {
        return { name: name, list: [] };
    }
    util.propertyListToString = function (propertyList) {
        var text = propertyList[0].name;
        propertyList.map(function (prop, index) {
            if (index > 0)
                text += ', ' + prop.name;
        });
        return text;
    }
    util.basePath = function () {
        return window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/') + 1);
    }
    util.yearList = function () {
        var years = [];
        var year = 2014; //Start year
        var currentYear = (new Date()).getFullYear();
        while (year <= currentYear) {
            years.push(year++);
        }
        return years;
    }
    util.loadQueryForm = function () {
        var queryData = $('#queryData').val().trim();
        if (queryData) {
            var query = new CrashQuery(queryData);
            query.loadForm();
        }
    }
    util.runQuery = function (queryId) {
        return loadQueryForm({
            url: this.basePath() + 'crashqueryform?id=' + queryId,
            responseDiv: $('#form-container'),
            rootElementId: 'crashQuery',
            callback: function () {
                util.loadQueryForm();
                util.persistQuery();
                $('#crashQuery').submit();
            }
        });
    }
    util.persistQuery = function () {
        var query = new CrashQuery();
        query.dirty = util.formEdited();
        sessionStorage.setItem('crashQuery', JSON.stringify(query));
    }
    util.initFormChangeDetection = function (formName) {
        var form = $(formName);
        form.find(':text').each(function () {
            this.oldValue = this.value;
        });
        form.find('select').each(function () {
            this.oldValue = this.value;
        });
        form.find(':text').on('blur focusout paste', function () {
            util.detectTextChange(this);
        });
        form.find('select').change(function () {
            util.detectTextChange(this);
        });
        form.find(':radio').change(function () {
            util.editForm();
            util.bindBeforeUnload();
        });
        form.find(':checkbox').change(function () {
            util.editForm();
            util.bindBeforeUnload();
        });
        if (util.formEdited()) {
            util.bindBeforeUnload();
        }
    }
    util.detectTextChange = function (element) {
        if (element.oldValue !== element.value) {
            util.editForm();
            util.bindBeforeUnload();
        }
    }
    util.formEdited = function () {
        return $('#dirty').val() === 'true';
    }
    util.editForm = function () {
        $('#dirty').val('true');
    }
    util.bindBeforeUnload = function () {
        $(window).on('beforeunload', function () {
            return 'You have unsaved changes. They will be lost if you go ahead and leave this page without saving.';
        });
    }
    util.unbindBeforeUnload = function () {
        $(window).off('beforeunload');
    }
    util.initCrashData = function () {
        if ($('#crashesJSON').length)
            window.crashes = JSON.parse($.trim($("#crashesJSON").val()));
        if ($('#crashAttributesJSON').length) {
            window.crashAttributes = JSON.parse($.trim($("#crashAttributesJSON").val()));
            window.crashAttributes.casualtyClass.unshift({ id: 0, name: 'Driver' });
            window.crashAttributes.day = constants.dayList;
        }
    }
    util.initCrashAnalysis = function () {
        $(document).ready(function () {
            $("#gMaps").hide();
            util.initTableSorter('table.tablesorter', 'crashes');
            if (window.crashes && window.crashes.length)
                $("#gMaps").show();
            ui.renderQuerySummary();
        });
    }
    util.initTableSorter = function (tableSelector, itemsName) {
        var pagerOptions = {
            container: $(".pager"),
            output: '{filteredRows} ' + itemsName + ' found, displaying {startRow} to {endRow}.',
            fixedHeight: true,
            removeRows: true,
            size: 50,
            cssGoto: '.gotoPage'
        };
        $(tableSelector).tablesorter({
            showProcessing: true,
            headerTemplate: '{content} {icon}',
            widthFixed: true,
            widgets: ['zebra', 'stickyHeaders', 'filter', 'saveSort', 'resizable'],
            widgetOptions: {
                stickyHeaders_attachTo: '.tablesorter-wrapper',
                saveSort: true,
                resizable: true
            }
        }).tablesorterPager(pagerOptions);
    }
    util.capitalizeFirst = function(text) {
        return text.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
    }
    util.injuredCasualty = function(casualtyType) {
        return casualtyType && casualtyType.id < 4;
    }
    util.driverToCasualty = function(vehicle) {
        var driver = vehicle.driver;
        if (!driver || !this.injuredCasualty(driver.casualtyType))
            return null;
        var casualty = Object.create(null);
        casualty.casualtyClass = { id: 0, name: 'Driver'};
        casualty.age = driver.age;
        casualty.gender = driver.gender;
        casualty.vehicle = vehicle;
        casualty.beltOrHelmetUsed = driver.beltOrHelmetUsed;
        casualty.casualtyType = driver.casualtyType;
        return casualty;
    }
    util.isCasualtyAttribute = function(selector) {
        return $(selector).find('option:selected').attr('data-attr-type') == 'casualty';
    }
    util.isCrashAttribute = function(selector) {
        return $(selector).find('option:selected').attr('data-attr-type') == 'crash';
    }
    util.isVehicleAttribute = function(selector) {
        return $(selector).find('option:selected').attr('data-attr-type') == 'vehicle';
    }
    util.isValueRange = function(range) {
        return constants.valueRanges.indexOf(range) > -1;
    }
    util.getAttributes = function(name, appendNull) {
        var attributes = window.crashAttributes[name];
        if (attributes.length && !attributes[0].name && attributes[0].label)
            attributes.map(function(x) { x.name = x.label });
        appendNull && attributes.push(util.nullAttribute());
        return attributes;
    }
    util.nullAttribute = function() {
        return { id: null, name: constants.NOT_SPECIFIED }
    }
    util.isNullAttribute = function(attribute) {
        return attribute.id === null && attribute.name === constants.NOT_SPECIFIED;
    }
    return util;
})();

var ui = (function () {
    var ui = Object.create(null);

    ui.init = function () {
        $(window).load(function () {
            ui.initDatePicker();
            ui.initSearchButton();
            ui.initNoneClickAnchor();
            $('a[title=Logout]').click(ui.cleanUp);
        });
    }
    ui.cleanUp = function() {
        sessionStorage.clear();
    }
    ui.initDatePicker = function () {
        $('.dtpicker').datepicker({
            dateFormat: "yy-mm-dd",
            autoSize: true,
            inline: true,
            showOtherMonths: true,
            changeYear: true,
            changeMonth: true,
            yearRange: 'c-20:c',
            dayNamesMin: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            showAnim: "fold",
            onSelect: function () {
                defineCrashTime();
            }
        });
        $('.dtpicker').focus(function () {
            this.value = '';
        })
    }
    ui.initSearchButton = function () {
        $('#searchButton').click(function () {
            var searchTerm = $.trim($('#searchTerm').val());
            if (searchTerm) {
                window.location.href = util.basePath() + 'crashsearch?tarNo=' + searchTerm;
            }
        });
    }
    ui.initNoneClickAnchor = function () {
        $('a.non-click').click(function () {
            return false;
        });
    }
    ui.appendQueryHeader = function (query, table) {
        if (query.name && query.description) {
            var row = $('<tr><td></td><td colspan="2"></td></tr>');
            row.find('td:first').append('<div class="query-label">Query Name</div>');
            row.find('td:first').append('<div class="query-value">' + query.name + '</div>');
            row.find('td:last').append('<div class="query-label">Description</div>');
            row.find('td:last').append('<div class="query-value">' + query.description + '</div>');
            table.append(row);
        }
    }
    ui.createQueryForm = function (query) {
        var action = util.basePath() + 'crashquerysave';
        var form = $('<form id="query-form" method="post" action="' + action + '">');
        var table = $('<table class="query-form">');
        table.append('<tr><td>Query Name:</td><td><input id="name" name="name"><input type="hidden" id="id" name="id" /></td></tr>');
        table.find('tr:last td:last input#id').val(query.id || '');
        table.find('tr:last td:last input#name').val(query.name || '');
        table.append('<tr><td>Description:</td><td><textarea id="description" name="description"></textarea></td></tr>');
        table.find('textarea').text(query.description);
        table.append('<tr><td colspan="2"></td></tr>');
        return form.append(table);
    }
    ui.queryFormButtons = function () {
        return  {
            'Save': function () {
                ui.saveQuery()
            },
            'Cancel': function () {
                $('#map-canvas').remove()
            }
        };
    }
    ui.saveQuery = function () {
        var queryName = $('#name').val().trim();
        var description = $('#description').val().trim();
        if (queryName && description && window.query) {
            window.query.dirty = false;
            var queryData = JSON.stringify(window.query);
            sessionStorage.setItem('crashQuery', queryData);
            $('#query-form').append($('<input type="hidden" id="queryData" name="queryData">').val(queryData));
            $('#query-form').submit();
        } else {

        }
    }
    ui.dialogContent = function () {
        return $('.ui-dialog-content');
    }
    ui.centerDialog = function () {
        ui.dialogContent().dialog("option", "position", { my: "center", at: "center", of: window });
    }
    ui.initMappingSurface = function () {
        $('.drawing-actions').hide();
        $("html").css("height", "100%");
        $("body").css("height", "100%");
        $(".row").addClass("height-100");
        $("body").css("padding-top", "51px");
        $("#content").addClass("height-100");
        $("#content").addClass("bot-pad-36");
    }
    ui.renderQuerySummary = function () {
        if (sessionStorage.getItem('crashQuery')) {
            window.query = new CrashQuery(sessionStorage.getItem('crashQuery'));
            window.query.render();
        }
    }
    ui.initLastAccessedObject = function () {
        $(window).load(function () {
            highlightLastAccessedObject();
            $('.tablesorter-wrapper').height($(window).height() - 310);
        });
    }
    ui.attributeSelect = function(id, selected) {
        var select = $('<select>').attr('id', id);
        select.append('<option value="" data-default="crashSeverity">==== Crash Attributes ====</option>');
        select.append('<option value="collisionType" data-attr-type="crash">Collision Type</option>');
        select.append('<option value="crashCause" data-attr-type="crash">Crash Cause</option>');
        select.append('<option value="crashSeverity" data-attr-type="crash">Crash Severity</option>');
        select.append('<option value="weightRange" data-range="weight" data-attr-type="crash">Crash Weight</option>');
        select.append('<option value="day" data-range="day" data-attr-type="crash">Day of Week</option>');
        select.append('<option value="district" data-attr-type="crash">District</option>');
        select.append('<option value="junctionType" data-attr-type="crash">Junction Type</option>');
        select.append('<option value="month" data-range="month" data-attr-type="crash">Month</option>');
        select.append('<option value="policeStation" data-attr-type="crash">Police Station</option>');
        select.append('<option value="roadSurface" data-attr-type="crash">Road Surface</option>');
        select.append('<option value="roadwayCharacter" data-attr-type="crash">Roadway Character</option>');
        select.append('<option value="surfaceCondition" data-attr-type="crash">Surface Condition</option>');
        select.append('<option value="surfaceType" data-attr-type="crash">Surface Type</option>');
        select.append('<option value="vehicleFailureType" data-attr-type="crash">Vehicle Failure Type</option>');
        select.append('<option value="weather" data-attr-type="crash">Weather</option>');
        select.append('<option value="year" data-range="year" data-attr-type="crash">Year</option>');
        select.append('<option value="" data-default="vehicleType">==== Vehicle Attributes ====</option>');
        select.append('<option value="vehicleType" data-attr-type="vehicle">Vehicle Type</option>');
        select.append('<option value="ageRange" data-attr-type="vehicle">Driver Age</option>');
        select.append('<option value="gender" data-attr-type="vehicle">Driver Sex</option>');
        select.append('<option value="licenseType" data-attr-type="vehicle">License Type</option>');
        select.append('<option value="beltUsedOption" data-attr-type="vehicle">Belt/Helmet Used (Driver)</option>');
        select.append('<option value="casualtyType" data-attr-type="vehicle">Driver Casualty</option>');
        select.append('<option value="" data-default="casualtyType">==== Casualty Attributes ====</option>');
        select.append('<option value="casualtyType" data-attr-type="casualty">Casualty Type</option>');
        select.append('<option value="casualtyClass" data-attr-type="casualty">Casualty Class</option>');
        select.append('<option value="ageRange" data-attr-type="casualty">Casualty Age</option>');
        select.append('<option value="gender" data-attr-type="casualty">Casualty Sex</option>');
        select.append('<option value="beltUsedOption" data-attr-type="casualty">Belt/Helmet Used (Casualty)</option>');
        selected && select.find('option[value=' + selected +']').prop('selected', true);
        return select;
    }
    ui.unitSelect = function() {
        var select = $('<select id="unit">');
        select.append('<option value="casualty">Casualties</option>');
        select.append('<option selected value="crash">Crashes</option>');
        select.append('<option value="vehicle">Vehicles</option>');
        return select;
    }
    ui.trendSelect = function(id) {
        var select = $('<select>').attr('id', id);
        select.append('<option value="timeRange">Time of Day</option>');
        select.append('<option value="day">Day of Week</option>');
        select.append('<option selected value="month">Month</option>');
        select.append('<option value="year">Year</option>');
        return select;
    }
    ui.statisticsTable = function() {
        var div = $('div.content-wrapper');
        div.html('');
        var table = $('<table cellpadding="3" width="100%" class="stats-tab">');
        var row1 = $('<tr>');
        var row2 = $('<tr>');
        row1.append('<td width="40%" id="stats" style="padding-right: 30px;"></td>');
        row1.append($('<td width="60%">').append('<div id="stat-chart" style="width:100%"></div>'));
        row2.append($('<td colspan="2" align="center">').append('<div id="stat-column" style="width:100%"></div>'));
        table.append(row1).append(row2);
        div.append(table);
    }
    ui.trendTable = function() {
        var div = $('div.content-wrapper');
        div.html('');
        var table = $('<table cellpadding="3" width="100%">');
        var row1 = $('<tr>');
        row1.append($('<td width="100%">').append('<br/><div id="crashtrend-chart" style="width:100%"></div>'));
        table.append(row1);
        div.append($('<p>').html(constants.HTML_SPACE));
        div.append(table);
    }
    ui.crosstabTable = function() {var div = $('div.content-wrapper');
        div.html('');
        var table = $('<table cellpadding="3" width="100%" class="cross-tab">');
        var row1 = $('<tr>');
        var row2 = $('<tr>');
        row1.append('<td width="100%" id="crosstabs"></td>');
        row2.append($('<td width="100%">').append('<br/><div id="crosstab-chart" style="width:100%"></div>'));
        table.append(row1).append(row2);
        div.append(table);
    }
    ui.statisticsTools = function() {
        var div = $('div.analysis-tools');
        div.html('');
        div.append($('<label for="crashAttribute" class="control-label">').text('Attribute:')).append(constants.HTML_SPACE);
        div.append(ui.attributeSelect('crashAttribute', 'crashSeverity')).append(constants.HTML_SPACE);
        div.append($('<label for="unit" class="control-label">').text('Units:')).append(constants.HTML_SPACE);
        div.append(ui.unitSelect());
    }
    ui.trendTools = function() {
        var div = $('div.analysis-tools');
        div.html('');
        div.append($('<label for="xCrashAttribute" class="control-label">').text('Rows:')).append(constants.HTML_SPACE);
        div.append(ui.trendSelect('xCrashAttribute')).append(constants.HTML_SPACE);
        div.append($('<label for="yCrashAttribute" class="control-label">').text('Columns:')).append(constants.HTML_SPACE);
        div.append(ui.attributeSelect('yCrashAttribute', 'crashSeverity')).append(constants.HTML_SPACE);
        div.append($('<label for="unit" class="control-label">').text('Units:')).append(constants.HTML_SPACE);
        div.append($('<div id="unit" style="display: inline">'));
        div.find('select#yCrashAttribute option[data-range]').remove();
    }
    ui.crosstabTools = function() {
        var div = $('div.analysis-tools');
        div.html('');
        div.append($('<label for="xCrashAttribute" class="control-label">').text('Rows:')).append(constants.HTML_SPACE);
        div.append(ui.attributeSelect('xCrashAttribute', 'crashSeverity')).append(constants.HTML_SPACE);
        div.append($('<label for="yCrashAttribute" class="control-label">').text('Columns:')).append(constants.HTML_SPACE);
        div.append(ui.attributeSelect('yCrashAttribute', 'collisionType')).append(constants.HTML_SPACE);
        div.append($('<label for="unit" class="control-label">').text('Units:')).append(constants.HTML_SPACE);
        div.append(ui.unitSelect());
    }
    return ui;
})();

var constants = {
    HTML_SPACE: '&nbsp;&nbsp;',
    NOT_SPECIFIED: 'Not Specified',
    valueRanges: ['timeRange', 'weight', 'year'],
    dayList: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    monthList: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
};