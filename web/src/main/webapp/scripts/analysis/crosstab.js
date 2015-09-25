/**
 * Created by Frank on 3/26/15.
 */
(function () {
    var crossTabs = Object.create(null);

    crossTabs.countCrashes = function (xAttribute, yAttribute, xCrashProp, yCrashProp) {
        var ctx = this;
        this.attributeCounts.length = 0;
        var unit = $('#unit option:selected').val();
        var xAttributes = util.getAttributes(xAttribute);
        var yAttributes = util.getAttributes(yAttribute);
        var xElement = $('#xCrashAttribute option:selected');
        var yElement = $('#yCrashAttribute option:selected');
        var xRangeAttribute = xElement.attr('data-range');
        var yRangeAttribute = yElement.attr('data-range');
        var xAttributeType = xElement.attr('data-attr-type');
        var yAttributeType = yElement.attr('data-attr-type');
        xAttributes.map(function (xAttr, xIndex) {
            var filterParams = util.pushArray([], xAttr, xAttribute, xCrashProp);;
            if (xRangeAttribute)
                filterParams = util.pushArray([], (util.isValueRange(xRangeAttribute) ? xAttr : xIndex), xAttribute, xCrashProp);
            var crashFilter = new CrashFilter(xAttributeType, xRangeAttribute, this.crashes);
            var xCrashes = crashFilter.filter(filterParams);
            var xAttributeCount = { xName: xAttr.name || xAttr, yAttributeCounts: [] };
            yAttributes.map(function (yAttr, yIndex) {
                crashFilter = new CrashFilter(yAttributeType, yRangeAttribute, xCrashes);
                filterParams = util.pushArray([], yAttr, yAttribute, yCrashProp);;
                if (yRangeAttribute)
                    filterParams = util.pushArray([], (util.isValueRange(yRangeAttribute) ? yAttr : yIndex), yAttribute, yCrashProp);
                xAttributeCount.yAttributeCounts.push({
                    yName: yAttr.name || yAttr,
                    count: crashFilter.filter(filterParams, unit).length
                });
            }, this);
            ctx.attributeCounts.push(xAttributeCount);
        }, this);
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
            crossTabs.crashes = window.crashes;
            crossTabs.countCrashes('crashSeverity', 'collisionType');
            $('#xCrashAttribute, #yCrashAttribute, #unit').change(function() {
                var xSelectedOption = $('#xCrashAttribute').find('option:selected');
                var ySelectedOption = $('#yCrashAttribute').find('option:selected');
                crossTabs.countCrashes($('#xCrashAttribute').val(), $('#yCrashAttribute').val(), xSelectedOption.attr('data-prefix'), ySelectedOption.attr('data-prefix'));
            });
            ui.renderQuerySummary();
        });
    }
    crossTabs.init();
})();