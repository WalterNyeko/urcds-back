var util = (function () {
    var util = Object.create(null);

    util.scheduleDeferredFunctionExecution = function(waitTime, f) {
        setTimeout(function() {
            f.call();
        }, waitTime);
        return false;
    }
    util.sendRequest = function(params) {
        $.ajax({
            url: params.url,
            success: function (result) {
                var response = $(result);
                if (params.rootElementId)
                    response = $(result).find('[id=' + params.rootElementId + ']');
                if(params.responseDiv)
                    params.responseDiv.html(response);
                if (params.modal) {
                    params.modal.body = response;
                    ui.popup(params.modal);
                }
                if (params.callback)
                    params.callback();
            }
        });
    }
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
        var basePath = window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/') + 1);
        constants.nonBasePath.map(function(x) {
            if (basePath.endsWith(x + '/'))
                basePath = basePath.replace(x + '/', '');
        });
        return basePath;
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
    util.runQuery = function (queryId) {
        util.sendRequest({
            url: this.basePath() + 'crashqueryform?id=' + queryId,
            responseDiv: $('#form-container'),
            rootElementId: 'crashQuery',
            callback: function () {
                ui.loadQueryForm();
                util.persistQuery();
                $('#crashQuery').submit();
            }
        });
        return false;
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
            ui.closeNotification();
            return 'You have unsaved changes. They will be lost if you go ahead and leave this page without saving.';
        });
    }
    util.unbindBeforeUnload = function () {
        $(window).off('beforeunload');
    }
    util.fetchCrashData = function(callback) {
        util.sendRequest({
            callback: callback,
            rootElementId: 'crashdata',
            responseDiv: $('div#crashdata'),
            url: util.basePath() + 'analysisdata'
        });
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
    util.initCrashAnalysis = function (itemName) {
        $(document).ready(function () {
            ui.renderQuerySummary();
            ui.initLastAccessedObject();
            ui.toggleMenuItem($('.toggle-menu div#crashes > a:first'));
            util.initTableSorter('table.tablesorter', itemName || 'crashes');
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
    util.validate24HrTime = function(time) {
        var rx = RegExp('([01]?[0-9]|2[0-3]):[0-5][0-9]');
        return rx.test(time);
    }

    return util;
})();

var ui = (function () {
    var ui = Object.create(null);

    ui.init = function () {
        $(window).load(function () {
            ui.initSearchButton();
            $('a.non-click').click(function (evt) {
                evt.stopImmediatePropagation();
                return false;
            });
            $('a[title=Logout]').click(function () { sessionStorage.clear() });
            $('.show-loading, .navbar-nav a:not(.dropdown-toggle)').click(function() {
                ui.loadingNotification();
                if ($(this).is('.auto-hide'))
                    ui.closeNotification(4000);
            });
            $('input.int-val').on('blur', function() {
                if (isNaN(parseInt($(this).val())))
                    $(this).val('');
            });
        });
        return this;
    }
    ui.initDatePicker = function (selectDate) {
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
            onSelect: selectDate
        });
        $('.time-control').on('blur focusout', selectDate);
    }
    ui.initSearchButton = function () {
        $('#searchButton').click(function () {
            var searchTerm = $.trim($('#searchTerm').val());
            if (searchTerm) {
                window.location.href = util.basePath() + 'crashsearch?tarNo=' + searchTerm;
            }
        });
    }
    ui.loadingNotification = function(message) {
        ui.clearModal();
        message = message || 'Please wait...'
        var gif = util.basePath() + 'images/loading.gif';
        var loading = $('<div class="modal loading" data-backdrop="static" data-keyboard="false">');
        loading.append($('<div class="modal-header">').append('<h4 class="waitMessage">' + message + '</h4>'));
        loading.append($('<div class="modal-body">').append('<img src="' + gif + '" height="80">'));
        loading.modal();
        ui.centerDialog(loading);
    }
    ui.closeNotification = function(lag) {
        lag = lag || 100;
        util.scheduleDeferredFunctionExecution(lag, function() {
            ui.clearModal();
            $('.modal-backdrop').remove();
            $('body').removeClass('modal-open');
        });
    }
    ui.clearModal = function() {
        $('.modal, .modal-backdrop.in, .modal-scrollable').remove();
        $('body').removeClass('modal-open').removeClass('page-overflow');
    }
    ui.dialogContent = function () {
        return window.dialog;
    }
    ui.centerDialog = function (dialog) {
        dialog.css('left', 0);
        var diff = $(window).width() - dialog.outerWidth();
        dialog.css('left', diff/2);
    }
    ui.popup = function(params) {
        ui.clearModal();
        var popup = $('<div id="rcds-modal" class="modal fade" aria-hidden="true" data-backdrop="static" data-keyboard="false">');
        var header = $('<div class="modal-header">');
        header.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>');
        header.append('<h4 class="modal-title"></h4>');
        header.find('.modal-title').text(params.header);
        var body = $('<div class="modal-body"><div class="row"></div></div>');
        body.find('.row').html(params.body);
        var footer = $('<div class="modal-footer">');
        var okButton = $('<button type="button" class="btn btn-primary"></button>').text(params.okButtonText ? params.okButtonText : 'OK');
        var cancelButton = $('<button type="button" data-dismiss="modal" class="btn btn-default"></button>').text(params.cancelButtonText ? params.cancelButtonText : 'Cancel');
        okButton.click(function() { params.okFunction() });
        footer.append(cancelButton).append(okButton);
        popup.append(header).append(body).append(footer);
        popup.modal();
        ui.centerDialog(popup);
        popup.find('button[data-dismiss]').click(function() { ui.clearModal()});
        popup.draggable({ containment: 'window', handle: '.modal-header', cursor: 'move' });
        return popup;
    }
    ui.confirmDialog = function(params) {
        var modal = Object.create(null);
        modal.header = 'Confirm Action';
        modal.body = params.message;
        modal.okButtonText = 'Yes';
        modal.cancelButtonText = 'No';
        modal.okFunction =  function() {
            if(params.aLink){
                var href = $(params.aLink).attr('href');
                ui.loadingNotification();
                util.unbindBeforeUnload();
                window.location.href = href;
            }
            params.callback && params.callback();
        }
        ui.popup(modal);
        return false;
    }
    ui.alertDialog = function(params) {
        if ($('.alert-dialog').length)
            return false;
        var modal = Object.create(null);
        modal.header = 'Notification';
        modal.body = params.message;
        modal.okFunction =  function() {
            if (params.focusTarget) {
                $(params.focusTarget).focus();
                $(params.focusTarget).select();
            }
            ui.clearModal();
        }
        ui.popup(modal).addClass('alert-dialog').find('button[data-dismiss]').hide();
        return false;
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
        table.append('<tr><td>Query Name:</td><td><input id="name" name="name" class="form-control"><input type="hidden" id="id" name="id" /></td></tr>');
        table.find('tr:last td:last input#id').val(query.id || '');
        table.find('tr:last td:last input#name').val(query.name || '');
        table.append('<tr><td>Description:</td><td><textarea id="description" name="description" class="form-control"></textarea></td></tr>');
        table.find('textarea').text(query.description);
        table.append('<tr><td colspan="2"></td></tr>');
        return form.append(table);
    }
    ui.loadQueryForm = function () {
        var queryData = $('#queryData').val().trim();
        if (queryData) {
            var query = new CrashQuery(queryData);
            query.loadForm();
        }
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
            $('.tablesorter-wrapper').height($(window).height() - 290);
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
        select.append('<option value="timeRange" data-range="timeRange" data-attr-type="crash">Time of Day</option>');
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
    ui.loadSelectCrash = function(params) {
        params.modal = {
            okButtonText: 'Search',
            header: 'Select Crashes',
            okFunction: function () {
                if(validateCrashSearch()) {
                    var form = $("#selectCrashForm");
                    ui.loadingNotification();
                    form.submit();
                }
            }
        };
        params.rootElementId = "selectCrashForm";
        params.callback = ui.initDatePicker;
        util.sendRequest(params);
        return false;
    }
    ui.loadVehicleForm = function(params) {
        params.modal = {
            okButtonText: 'Save',
            header: 'Vehicle Form',
            okFunction: function () {
                var form = $("#vehicleform");
                ui.loadingNotification();
                form.submit();
            }
        };
        params.rootElementId = "vehicleform";
        util.sendRequest(params);
        return false;
    }
    ui.loadCasualtyForm = function(params) {
        params.modal = {
            okButtonText: 'Save',
            header: 'Casualty Form',
            okFunction: function () {
                if (validateFields()) {
                    var form = $("#casualtyform");
                    ui.loadingNotification();
                    form.submit();
                }
            }
        };
        params.rootElementId = "casualtyform";
        util.sendRequest(params);
        return false;
    }
    ui.toggleCrashMenu = function() {
        var allCrashes = window.location.href.indexOf('?all=true') > -1;
        var removedCrashes = window.location.href.indexOf('?removed=true') > -1;
        if (allCrashes)
            ui.toggleMenuItem($('#all-data > a'));
        else if (removedCrashes)
            ui.toggleMenuItem($('#removed-data > a'));
        else
            ui.toggleMenuItem($('#summary > a'));
        $('.toggle-menu div:not(.active), .toggle-menu div:not(.active) > a').click(function() {
            var item = $(this).is('a') ? $(this) : $(this).find('a:first');
            ui.toggleMenuItem(item);
            if ($(this).is('div'))
                window.location.href = item.attr('href');
        });
    }
    ui.toggleMenuItem = function(item) {
        $('.toggle-menu .active').removeClass('active');
        $('.toggle-menu .non-click').removeClass('non-click');
        item.addClass('non-click');
        item.parent().addClass('active');
    }
    ui.setCrashTime = function() {
        if (!$('#crashTime').length)
            return;
        var time = $.trim($('#crashTime').val());
        if (time == '')
            return;
        if (!util.validate24HrTime(time)) {
            ui.alertDialog({ message: 'Crash time must be in 24-hour format', focusTarget: $('#crashTime') });
            return;
        }
        if (time.length == 4)
            time = '0' + time;
        var date = $('#crashDateTimeString').val();
        if (date.trim() == '')
            return;
        var dateParts = date.split(' ');
        if (dateParts.length > 1)
            date = dateParts[0];
        date = date + " " + time;
        $('#crashDateTimeString').val(date);
    }
    ui.loadCrashTime = function() {
        var date = $('#crashDateTimeString').val();
        if (date.trim() == '')
            return;
        var dateParts = date.split(' ');
        if (dateParts.length > 1) {
            var time = $.trim(dateParts[1]);
            $('#crashTime').val(time);
        }
    }
    ui.setInjuryDateTime = function() {
        var date = $.trim($('#injuryDate').val());
        var time = $.trim($('#injuryTime').val());
        if (date.length == 0)
            return;
        if (time) {
            if (!util.validate24HrTime(time)) {
                ui.alertDialog({ message: 'Crash time must be in 24-hour format', focusTarget: $('#injuryTime') });
                return;
            }
            if (time.length == 4)
                time = '0' + time;
        }
        date = date + " " + time;
        $('#injuryDateTimeString').val(date);
    }
    ui.loadInjuryDateTime = function() {
        var datetime = $.trim($('#injuryDateTimeString').val());
        if (datetime.length == 0)
            return;
        var dateParts = datetime.split(' ');
        $('#injuryDate').val(dateParts[0])
        if (dateParts.length > 1)
            $('#injuryTime').val(dateParts[1]);
    }
    return ui.init();
})();

var constants = {
    nonBasePath: ['analysis'],
    HTML_SPACE: '&nbsp;&nbsp;',
    NOT_SPECIFIED: 'Not Specified',
    valueRanges: ['timeRange', 'weight', 'year'],
    dayList: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    monthList: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
};