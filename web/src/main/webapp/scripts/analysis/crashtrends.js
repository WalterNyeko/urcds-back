/**
 * Created by Frank on 3/26/15.
 */
var CrashTrend = (function () {
    function CrashTrend() {
        window.crashTend = this;
        this.crashes = JSON.parse(localStorage.crashesJSON).crashes;
        this.crashAttributes = JSON.parse(localStorage.crashAttributesJSON);
        this.attributeCounts = [];
        this.monthOfYear = {
            name: 'Month',
            filter: Object.getPrototypeOf(this).isInMonth,
            list: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        };
        this.dayOfWeek = {
            name: 'Day of Week',
            filter: Object.getPrototypeOf(this).isOnDay,
            list: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']
        };
        this.crashYear = {
            name: 'Year',
            filter: Object.getPrototypeOf(this).isInYear,
            list: [2014, 2015]
        };
    }
    CrashTrend.prototype.countCrashes = function (viewBy, yAttribute, yCrashProp) {
        var ctx = this;
        this.attributeCounts.length = 0;
        var xAttribute = this[viewBy];
        var yAttributes = this.crashAttributes[yAttribute];

        xAttribute.list.map(function (xAttr, index) {
            var xAttributeCount = { xName: xAttr, yAttributeCounts: [] };
            yAttributes.map(function (yAttr) {
                xAttributeCount.yAttributeCounts.push({
                    yName: yAttr.name,
                    count: ctx.crashes.filter(function (c) {
                        var yCrashAttr = yCrashProp ? c[yCrashProp][yAttribute] : c[yAttribute];
                        return xAttribute.filter.call(ctx, c, index) &&
                            yCrashAttr && yCrashAttr['id'] === yAttr.id
                    }).length
                });
            });
            ctx.attributeCounts.push(xAttributeCount);
        });
        charting.createTrendLineGraph(this, xAttribute.list, $('#yCrashAttribute option:selected').text() + ' by ' + xAttribute.name,  'crashtrend-chart');
    }
    CrashTrend.prototype.isOnDay = function(crash, day) {
        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
        return crashDate && crashDate.getDay() == day;
    }
    CrashTrend.prototype.isInMonth = function(crash, month) {
        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
        return crashDate && crashDate.getMonth() == month;
    }
    CrashTrend.prototype.isInYear = function(crash, year) {
        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
        return crashDate && crashDate.getFullYear() == this.crashYear.list[year];
    }
    return CrashTrend;
})();