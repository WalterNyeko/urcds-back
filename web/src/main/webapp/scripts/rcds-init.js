function systemWideInit() {
    $(window).load(function(){
        initNoneClickAnchor();
    });
}

function initNoneClickAnchor() {
    $('a.non-click').click(function() {
        return false;
    });
}