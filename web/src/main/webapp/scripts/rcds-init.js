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
    return util;
})();

var ui = (function() {
    var ui = Object.create(null);

    return ui;
})();