/**
 * Created by Frank on 3/26/15.
 */
(function () {
    var crossTabs = Object.create(null);

    crossTabs.countCrashes = function (xAttribute, yAttribute, xCrashProp, yCrashProp) {
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
        this.tabulateCounts(yAttributes);
    }

    crossTabs.tabulateCounts = function (yAttributes) {
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
        table.append(thead).append(tbody);//.append(tfoot);
        $('#crosstabs').append(table);
        charting.createBarChart(this, xAttrName + ' by ' + yAttrName, 'crosstab-chart');
    }
    crossTabs.init = function() {
        $(document).ready(function() {
            util.initCrashData();
            crossTabs.attributeCounts = [];
            crossTabs.crashes = window.crashes
            crossTabs.crashAttributes = window.crashAttributes;
            crossTabs.countCrashes('crashSeverity', 'collisionType');
            $('#xCrashAttribute, #yCrashAttribute').change(function() {
                var xSelectedOption = $('#xCrashAttribute').find('option:selected');
                var ySelectedOption = $('#yCrashAttribute').find('option:selected');
                crossTabs.countCrashes($('#xCrashAttribute').val(), $('#yCrashAttribute').val(), xSelectedOption.attr('data-prefix'), ySelectedOption.attr('data-prefix'));
            });
            ui.renderQuerySummary();
        });
    }
    crossTabs.init();
})();