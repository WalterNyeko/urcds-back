/**
 * Created by Frank on 9/30/15.
 */
$(function() {
    $(document).ready(function() {
        sendRequest({
            rootElementId: 'crashdata',
            responseDiv: $('div#crashdata'),
            url: util.basePath().replace('analysis/', 'analysisdata'),
            callback: function() {
                util.initCrashData();
                $('a.analysis').click(function() {
                    displayAnalysis($(this).attr('data-type'));
                    return false;
                });
                displayAnalysis();
            }
        });
        ui.renderQuerySummary();
    });

    function displayAnalysis(type) {
        type = !type ? window.location.pathname.substr(window.location.pathname.lastIndexOf('/') + 1) : type;
        switch(type) {
            case 'crashstats':
                statistics.init();
                break;
            case 'crosstabs':
                crosstabs.init();
                break;
            case 'crashtrends':
                crashtrends.init();
                break;
        }
    }
});
