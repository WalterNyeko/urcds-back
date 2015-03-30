/**
 * Created by Frank on 3/26/15.
 */
var CrossTabulation = (function () {
    function CrossTabulation() {
        window.crossTabulation = this;
        this.crashes = JSON.parse(localStorage.crashesJSON).crashes;
        this.crashAttributes = JSON.parse(localStorage.crashAttributesJSON);
        this.attributeCounts = [];
    }

    CrossTabulation.prototype.countCrashes = function (xAttribute, yAttribute, xCrashProp, yCrashProp) {
        var ctx = this;
        this.attributeCounts.length = 0;
        var xAttributes = this.crashAttributes[xAttribute];
        var yAttributes = this.crashAttributes[yAttribute];
        xAttributes.forEach(function (xAttr) {
            var xAttributeCount = { xName: xAttr.name, yAttributeCounts: [] };
            yAttributes.forEach(function (yAttr) {
                xAttributeCount.yAttributeCounts.push({
                    yName: yAttr.name,
                    "count": ctx.crashes.filter(function (c) {
                        var xCrashAttr = xCrashProp ? c[xCrashProp][xAttribute] : c[xAttribute];
                        var yCrashAttr = yCrashProp ? c[yCrashProp][yAttribute] : c[yAttribute];
                        return xCrashAttr && xCrashAttr['id'] === xAttr.id &&
                            yCrashAttr && yCrashAttr['id'] === yAttr.id
                    }).length});
            });
            ctx.attributeCounts.push(xAttributeCount);
        });
//        var notSpec = this.crashes.length - this.attributeCounts.reduce(function (total, b) {
//            return total + b.count;
//        }, 0);
//        if (notSpec) {
//            this.attributeCounts.push({"name": "Not specified", "count": notSpec});
//        }
        this.tabulateCounts(yAttributes);
    }

    CrossTabulation.prototype.tabulateCounts = function (yAttributes) {
        $('#crosstabs').html('');
        var xAttrName = $('#xCrashAttribute option:selected').text();
        var yAttrName = $('#yCrashAttribute option:selected').text();
        var table = $('<table class="crosstab-orange">');
        var thead = $('<thead>');
        var tbody = $('<tbody>');
        var tfoot = $('<tfoot style="font-weight: bold;">');
        var headerRow1 = $('<tr>');
        var xHeader = $('<th>').attr('rowspan', 2).append(xAttrName);
        var yColspan = yAttributes.length;
        var yHeader = $('<th>').attr('colspan', yColspan).append(yAttrName);
        var headerRow2 = $('<tr>');
        yAttributes.forEach(function(attr) {
            var yAttrHeader = $('<th>').append(attr.name);
            headerRow2.append(yAttrHeader);
        });
        thead.append(headerRow1.append(xHeader).append(yHeader)).append(headerRow2);
        //var total = 0;
        this.attributeCounts.forEach(function (xAttr) {
            var row = $('<tr>');
            var xCell = $('<td class="orange-header" style="padding: 4px !important; text-align: left !important; font-weight: bold;">').append(xAttr.xName);
            row.append(xCell);
            xAttr.yAttributeCounts.forEach(function(yAttr) {
                var yCell = $('<td align="right" style="padding: 4px !important;">').append(yAttr.count);
                row.append(yCell);
            });
            tbody.append(row);
            //total += attr.count;
        });
//        var footerRow = $('<tr>');
//        var footerCell1 = $('<td>').append('Total');
//        var footerCell2 = $('<td align="right">').append(this.crashes.length);
//        tfoot.append(footerRow.append(footerCell1).append(footerCell2));
        table.append(thead).append(tbody);//.append(tfoot);
        $('#crosstabs').append(table);
        charting.createBarChart(this, xAttrName + ' by ' + yAttrName, 'crosstab-chart');
    }
    return CrossTabulation;
})();