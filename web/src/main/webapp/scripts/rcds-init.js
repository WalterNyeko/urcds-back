function systemWideInit() {
    $(window).load(function(){
        initNoneClickAnchor();
        initSearchButton();
        $('a[title=Logout]').click(clearLastAccessedObjects);
    });
}

function initNoneClickAnchor() {
    $('a.non-click').click(function() {
        return false;
    });
}

function initSearchButton() {
    $('#searchButton').click(function() {
        var searchTerm = $.trim($('#searchTerm').val());
        if(searchTerm) {
            window.location.href = util.basePath() + 'crashsearch?tarNo=' + searchTerm;
        }
    });
}

function initTableSorter(tableSelector, itemsName) {
    var pagerOptions = {
        container: $(".pager"),
        output: '{filteredRows} ' + itemsName +  ' found, displaying {startRow} to {endRow}.',
        fixedHeight: true,
        removeRows: true,
        size: 50,
        cssGoto: '.gotoPage'
    };
    $(tableSelector).tablesorter({
        showProcessing: true,
        headerTemplate : '{content} {icon}',
        widthFixed : true,
        widgets: ['zebra', 'stickyHeaders', 'filter', 'saveSort', 'resizable'],
        widgetOptions: {
            stickyHeaders_attachTo : '.tablesorter-wrapper',
            saveSort : true,
            resizable: true
        }
    }).tablesorterPager(pagerOptions);
}

function initCrashAnalysis() {
    initTableSorter('#crashList', 'crashes');
    localStorage.setItem("crashesJSON", null);
    localStorage.setItem("crashAttributesJSON", null);
    $("#gMaps").hide();
    var crashJsonText = $.trim($("#crashesJSON").val());
    var attributesJsonText = $.trim($("#crashAttributesJSON").val());
    if(crashJsonText.length > 0) {
        var crashJson = '{"crashes" : ' + crashJsonText + '}';
        localStorage.setItem("crashesJSON", crashJson);
        localStorage.setItem("crashAttributesJSON", attributesJsonText);
        $("#gMaps").show();
    }
    renderQuerySummary();
}

var util = (function() {
    var util = Object.create(null);
    util.nameValuePair = function(name) {
        return { name : name, value :  null};
    }
    util.nameListPair = function(name) {
        return { name: name, list: [] };
    }
    util.propertyListToString = function(propertyList) {
        var text = propertyList[0].name;
        propertyList.map(function(prop, index) {
            if (index > 0)
                text += ', ' + prop.name;
        });
        return text;
    }
    util.basePath = function() {
        return window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/') + 1);
    }
    util.loadQueryForm = function() {
        var queryData = $('#queryData').val().trim();
        if (queryData) {
            var query = new CrashQuery(queryData);
            query.loadForm();
        }
    }
    util.runQuery = function(queryId) {
        return loadQueryForm({
            url: this.basePath() + 'crashqueryform?id=' + queryId,
            responseDiv: $('#form-container'),
            rootElementId: 'crashQuery',
            callback: function() {
                util.loadQueryForm();
                util.persistQuery();
                $('#crashQuery').submit();
            }
        });
    }
    util.persistQuery = function() {
        var query = new CrashQuery();
        query.dirty = util.formEdited();
        localStorage.setItem('crashQuery', JSON.stringify(query));
    }
    util.initFormChangeDetection = function(formName) {
        var form = $(formName);
        form.find(':text').each(function() {
            this.oldValue = this.value;
        });
        form.find('select').each(function() {
            this.oldValue = this.value;
        });
        form.find(':text').on('blur focusout paste', function() {
            util.detectTextChange(this);
        });
        form.find('select').change(function() {
            util.detectTextChange(this);
        });
        form.find(':radio').change(function() {
            util.editForm();
            util.bindBeforeUnload();
        });
        form.find(':checkbox').change(function() {
            util.editForm();
            util.bindBeforeUnload();
        });
        if(util.formEdited()) {
            util.bindBeforeUnload();
        }
    }

    util.detectTextChange = function(element) {
        if(element.oldValue !== element.value) {
            util.editForm();
            util.bindBeforeUnload();
        }
    }

    util.formEdited = function() {
        return $('#dirty').val() === 'true';
    }

    util.editForm = function() {
        $('#dirty').val('true');
    }

    util.bindBeforeUnload = function() {
        $(window).on('beforeunload', function() {
            return 'You have unsaved changes. They will be lost if you go ahead and leave this page without saving.';
        });
    }

    util.unbindBeforeUnload = function() {
        $(window).off('beforeunload');
    }
    return util;
})();

var ui = (function() {
    var ui = Object.create(null);

    ui.appendQueryHeader = function(query, table) {
        if (query.name && query.description) {
            var row = $('<tr><td></td><td colspan="2"></td></tr>');
            row.find('td:first').append('<div class="query-label">Query Name</div>');
            row.find('td:first').append('<div class="query-value">' + query.name + '</div>');
            row.find('td:last').append('<div class="query-label">Description</div>');
            row.find('td:last').append('<div class="query-value">' + query.description + '</div>');
            table.append(row);
        }
    }
    ui.createQueryForm = function(query) {
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
    ui.queryFormButtons = function() {
        return  {
            'Save' : function() { ui.saveQuery() },
            'Cancel': function () { $('#map-canvas').remove() }
        };
    }
    ui.saveQuery = function() {
        var queryName = $('#name').val().trim();
        var description = $('#description').val().trim();
        if (queryName && description && window.query) {
            window.query.dirty = false;
            var queryData = JSON.stringify(window.query);
            localStorage.setItem('crashQuery', queryData);
            $('#query-form').append($('<input type="hidden" id="queryData" name="queryData">').val(queryData));
            $('#query-form').submit();
        } else {

        }
    }
    ui.dialogContent = function() {
        return $('.ui-dialog-content');
    }
    ui.centerDialog = function() {
        ui.dialogContent().dialog("option", "position", { my: "center", at: "center", of: window });
    }
    return ui;
})();