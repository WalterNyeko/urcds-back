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