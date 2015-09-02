/**
 * Created by Frank on 3/26/15.
 */
(function () {
    var crashTrends = Object.create(null);

    crashTrends.countCrashes = function (viewBy, yAttribute, yCrashProp) {
        this.attributeCounts.length = 0;
        var xAttribute = this[viewBy];
        var yAttributes = this.getAttributes(yAttribute);
        var weightRange = $('#yCrashAttribute option:selected').attr('data-weight');

        xAttribute.list.map(function (xAttr, index) {
            var xAttributeCount = { xName: xAttr, yAttributeCounts: [] };
            yAttributes.map(function (yAttr) {
                xAttributeCount.yAttributeCounts.push({
                    yName: yAttr.name,
                    count: this.crashes.filter(function (c) {
                        var yMatch = false;
                        var yCrashAttr = yCrashProp ? c[yCrashProp][yAttribute] : c[yAttribute];
                        if (weightRange) {
                            if (yAttr.maxWeight)
                                yMatch = (c.weight >= yAttr.minWeight && c.weight <= yAttr.maxWeight);
                            else
                                yMatch = c.weight >= yAttr.minWeight;
                        } else
                            yMatch = yCrashAttr && yCrashAttr['id'] === yAttr.id;
                        return xAttribute.filter.call(this, c, index) && yMatch;
                    }, this).length
                });
            }, this);
            this.attributeCounts.push(xAttributeCount);
        }, this);
        charting.createTrendLineGraph(this, xAttribute.list, $('#yCrashAttribute option:selected').text() + ' by ' + xAttribute.name, 'crashtrend-chart');
    }
    crashTrends.getAttributes = function(attributeName) {
        var attributes = this.crashAttributes[attributeName];
        if (attributes.length && ! attributes[0].name && attributes[0].label)
            attributes.map(function(attr) { attr.name = attr.label });
        return attributes;
    }
    crashTrends.isOnDay = function (crash, day) {
        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
        return crashDate && crashDate.getDay() == day;
    }
    crashTrends.isInMonth = function (crash, month) {
        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
        return crashDate && crashDate.getMonth() == month;
    }
    crashTrends.isInYear = function (crash, year) {
        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
        return crashDate && crashDate.getFullYear() == this.crashYear.list[year];
    }
    crashTrends.monthOfYear = {
        name: 'Month',
        filter: crashTrends.isInMonth,
        list: constants.monthList
    };
    crashTrends.dayOfWeek = {
        name: 'Day of Week',
        filter: crashTrends.isOnDay,
        list: constants.dayList
    };
    crashTrends.crashYear = {
        name: 'Year',
        filter: crashTrends.isInYear,
        list: util.yearList()
    };

    crashTrends.init = function() {
        $(document).ready(function() {
            util.initCrashData();
            crashTrends.attributeCounts = [];
            crashTrends.crashes = window.crashes
            crashTrends.crashAttributes = window.crashAttributes;
            crashTrends.countCrashes('monthOfYear', 'crashSeverity');
            $('#xCrashAttribute, #yCrashAttribute').change(function () {
                var ySelectedOption = $('#yCrashAttribute').find('option:selected');
                crashTrends.countCrashes($('#xCrashAttribute').val(), $('#yCrashAttribute').val(), ySelectedOption.attr('data-prefix'));
            });
            ui.renderQuerySummary();
        });
    }
    crashTrends.init();
})();