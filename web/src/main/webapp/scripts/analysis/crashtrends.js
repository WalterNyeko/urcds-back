/**
 * Created by Frank on 3/26/15.
 */
(function () {
    var crashTrends = Object.create(null);

    crashTrends.countCrashes = function (viewBy, yAttribute, yCrashProp) {
        this.attributeCounts.length = 0;
        var xAttributes = util.getAttributes(viewBy);
        var yAttributes = util.getAttributes(yAttribute);
        var weightRange = $('#yCrashAttribute option:selected').val() == 'weightRange';
        this.attributeType = $('#yCrashAttribute option:selected').attr('data-attr-type');
        $('#unit').html(pluralize(util.capitalizeFirst(this.attributeType)));

        xAttributes.map(function (xAttribute, index) {
            var filterParams = [];
            filterParams.push(util.isValueRange(viewBy) ? xAttribute : index);
            var xAttributeCount = { xName: xAttribute, yAttributeCounts: [] };
            var crashFilter = new CrashFilter('crash', viewBy, this.crashes);
            var xCrashes = crashFilter.filter(filterParams);
            yAttributes.map(function (yAttr) {
                crashFilter = new CrashFilter(this.attributeType, weightRange ? 'weight' : null, xCrashes);
                filterParams = util.pushArray([], yAttr, yAttribute, yCrashProp);
                xAttributeCount.yAttributeCounts.push({
                    yName: yAttr.name,
                    count: crashFilter.filter(filterParams).length
                });
            }, this);
            this.attributeCounts.push(xAttributeCount);
        }, this);
        charting.createTrendLineGraph(this, xAttributes, $('#yCrashAttribute option:selected').text() + ' by ' + $('#xCrashAttribute option:selected').text(), 'crashtrend-chart');
    }
    crashTrends.totalUnits = function() {
        var crashFilter =  new CrashFilter(this.attributeType, null, this.crashes);
        return crashFilter.totalUnits();
    }
    crashTrends.init = function() {
        $(document).ready(function() {
            util.initCrashData();
            crashTrends.attributeCounts = [];
            crashTrends.crashes = window.crashes;
            crashTrends.countCrashes('month', 'crashSeverity');
            $('#xCrashAttribute, #yCrashAttribute').change(function () {
                var ySelectedOption = $('#yCrashAttribute').find('option:selected');
                crashTrends.countCrashes($('#xCrashAttribute').val(), $('#yCrashAttribute').val(), ySelectedOption.attr('data-prefix'));
            });
            ui.renderQuerySummary();
        });
    }
    crashTrends.init();
})();