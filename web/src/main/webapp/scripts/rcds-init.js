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
        var searchTerm = $.trim($('#searchTerm').val());
        if(searchTerm) {
            window.location.href = '/crashsearch?tarNo=' + searchTerm;
        }
    });
}