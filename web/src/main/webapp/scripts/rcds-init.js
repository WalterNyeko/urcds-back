function systemWideInit() {
    $(window).load(function(){
        initNoneClickAnchor();
        initSearchButton();
    });
}

function initNoneClickAnchor() {
    $('a.non-click').click(function() {
        return false;
    });
}

function initSearchButton() {
    $('#searchButton').click(function() {
        var basePath = window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/') + 1);
        var searchTerm = $.trim($('#searchTerm').val());
        if(searchTerm) {
            window.location.href = basePath + 'crashsearch?tarNo=' + searchTerm;
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
    return util;
})();

var ui = (function() {
    var ui = Object.create(null);

    ui.createQueryForm = function() {
        var action = util.basePath() + 'crashquerysave';
        var form = $('<form id="query-form" method="post" action="' + action + '">');
        var table = $('<table class="query-form">');
        table.append('<tr><td>Query Name:</td><td><input id="name" name="name"><input type="hidden" id="id" name="id" /></td></tr>');
        table.append('<tr><td>Description:</td><td><textarea id="description" name="description"></textarea></td></tr>');
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
            var queryData = JSON.stringify(window.query);
            $('#query-form').append($('<input type="hidden" id="queryData" name="queryData">').val(queryData));
            $('#query-form').submit();
        } else {

        }
    }
    return ui;
})();