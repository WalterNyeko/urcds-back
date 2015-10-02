/**
 * Created by Frank on 9/30/15.
 */
$(function() {
    $(document).ready(function() {
        ui.loadingNotification();
        util.fetchCrashData(function() {
            util.initCrashData();
            displayAnalysis();
        });
        $('a.analysis').click(function() {
            ui.loadingNotification();
            displayAnalysis($(this).attr('data-type'));
            ui.closeNotification(500);
            return false;
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
