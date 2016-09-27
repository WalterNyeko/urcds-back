/**
 * Created by Frank on 3/26/15.
 */
var crashtrends = (function () {
    var crashtrends = Object.create(null);

    crashtrends.countCrashes = function () {
        this.attributeCounts.length = 0;
        var viewBy = $('#xCrashAttribute').val();
        var yAttribute = $('#yCrashAttribute').val();
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
                filterParams = [yAttr, yAttribute];
                xAttributeCount.yAttributeCounts.push({
                    yName: yAttr.name,
                    count: crashFilter.filter(filterParams).length
                });
            }, this);
            this.attributeCounts.push(xAttributeCount);
        }, this);
        charting.createTrendLineGraph(this, xAttributes,
                $('#yCrashAttribute option:selected').text() + ' by ' + $('#xCrashAttribute option:selected').text(), 'crashtrend-chart');
    }
    crashtrends.totalUnits = function() {
        var crashFilter =  new CrashFilter(this.attributeType, null, this.crashes);
        return crashFilter.totalUnits();
    }
    crashtrends.init = function() {
        ui.trendTools();
        ui.trendTable();
        crashtrends.attributeCounts = [];
        crashtrends.crashes = window.crashes;
        $('#xCrashAttribute, #yCrashAttribute').change(function () {
            if (!$('#yCrashAttribute').val()) {
                var defaultAttr = $('#yCrashAttribute').find('option:selected').attr('data-default');
                $('#yCrashAttribute').find('option[value=' + defaultAttr + ']').prop('selected', true);
            }
            crashtrends.countCrashes();
        });
        crashtrends.countCrashes();
        $('h2.analysis-header').text($('#crashtrends-header').val());
        document.title = $('#crashtrends-header').val() + ' | ' + document.title.split('|')[1].trim();
    }
    return crashtrends;
})();