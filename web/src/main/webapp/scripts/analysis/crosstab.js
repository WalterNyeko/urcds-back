/**
 * Created by Frank on 3/26/15.
 */
var crosstabs = (function () {
    var crosstabs = Object.create(null);

    crosstabs.countCrashes = function () {
        var ctx = this;
        this.attributeCounts.length = 0;
        var unit = this.units();
        var xAttribute = $('#xCrashAttribute').val();
        var yAttribute = $('#yCrashAttribute').val()
        var xAttributes = util.getAttributes(xAttribute, true);
        var yAttributes = util.getAttributes(yAttribute, true);
        var xElement = $('#xCrashAttribute option:selected');
        var yElement = $('#yCrashAttribute option:selected');
        var xRangeAttribute = xElement.attr('data-range');
        var yRangeAttribute = yElement.attr('data-range');
        var xAttributeType = xElement.attr('data-attr-type');
        var yAttributeType = yElement.attr('data-attr-type');
        xAttributes.map(function (xAttr, xIndex) {
            var xFilterParams = [xAttr, xAttribute];
            if (xRangeAttribute && !util.isValueRange(xRangeAttribute) && !util.isNullAttribute(xAttr))
                xFilterParams[0] = xIndex;
            this.xCrashFilter = new CrashFilter(xAttributeType, xRangeAttribute, this.crashes);
            var xCrashes = this.xCrashFilter.filter(xFilterParams, 'crash');
            var xAttributeCount = { xName: xAttr.name || xAttr, yAttributeCounts: [] };
            yAttributes.map(function (yAttr, yIndex) {
                var count = 0;
                this.yCrashFilter = new CrashFilter(yAttributeType, yRangeAttribute, xCrashes);
                var yFilterParams = [yAttr, yAttribute];
                if (yRangeAttribute && !util.isValueRange(yRangeAttribute) && !util.isNullAttribute(yAttr))
                    yFilterParams[0] = yIndex;
                if (unit != 'crash' && this.xCrashFilter.type == unit) {
                    var filteredUnits = this.yCrashFilter.filter(yFilterParams, unit);
                    count = filteredUnits.filter(function(x) { return this.xCrashFilter.match(x, xFilterParams[0], xFilterParams[1]) }).length;
                } else {
                    count = this.yCrashFilter.filter(yFilterParams, unit).length;
                }
                xAttributeCount.yAttributeCounts.push({
                    yName: yAttr.name || yAttr,
                    count: count
                });
            }, this);
            ctx.attributeCounts.push(xAttributeCount);
        }, this);
        this.tabulateCounts(yAttributes);
        //pop nulls
        xAttributes.pop();
        yAttributes.pop();
    }
    crosstabs.tabulateCounts = function (yAttributes) {
        $('#crosstabs').html('');
        var xAttrName = $('#xCrashAttribute option:selected').text();
        var yAttrName = $('#yCrashAttribute option:selected').text();
        var table = $('<table class="crosstab-orange">');
        var thead = $('<thead>');
        var tbody = $('<tbody>');
        var tfoot = $('<tfoot><tr><td>Total</td></tr></tfoot>');
        var headerRow1 = $('<tr>');
        var xHeader = $('<th>').attr('rowspan', 2).append(xAttrName);
        var yColspan = yAttributes.length;
        var yHeader = $('<th>').attr('colspan', yColspan).append(yAttrName);
        var headerRow2 = $('<tr>');
        yAttributes.map(function(attr) {
            var yAttrHeader = $('<th>').append(attr.name);
            headerRow2.append(yAttrHeader);
        });
        headerRow1.append(xHeader).append(yHeader);
        headerRow1.append($('<th class="total">Total</th>').attr('rowspan', 2));
        thead.append(headerRow1).append(headerRow2);
        var xTotals = [];
        this.attributeCounts.map(function (xAttr) {
            var yTotal = 0;
            var row = $('<tr>');
            var xCell = $('<td class="orange-header">').append(xAttr.xName);
            row.append(xCell);
            xAttr.yAttributeCounts.map(function(yAttr, index) {
                var yCell = $('<td class="crosstab-cell">').append(yAttr.count);
                row.append(yCell);
                yTotal += yAttr.count;
                if(xTotals[index] === undefined)
                    xTotals[index] = yAttr.count;
                else
                    xTotals[index] += yAttr.count;
            });
            if (xTotals[xAttr.yAttributeCounts.length] === undefined)
                xTotals[xAttr.yAttributeCounts.length] = yTotal;
            else
                xTotals[xAttr.yAttributeCounts.length] += yTotal;
            row.append($('<td class="crosstab-cell total">').append(yTotal));
            tbody.append(row);
        });
        xTotals.map(function(x) {
            tfoot.find('tr').append($('<td></td>').append(x));
        })
        table.append(thead).append(tbody).append(tfoot);
        $('#crosstabs').append(table);
        charting.createBarChart(this, xAttrName + ' by ' + yAttrName, 'crosstab-chart');
    }
    crosstabs.units = function() {
        return $('#unit').val();
    }
    crosstabs.totalUnits = function() {
        return this.xCrashFilter.totalUnits($('#unit').val());
    }
    crosstabs.init = function() {
        ui.crosstabTools();
        ui.crosstabTable();
        crosstabs.attributeCounts = [];
        crosstabs.crashes = window.crashes;
        $('#xCrashAttribute, #yCrashAttribute, #unit').change(function () {
            if (!$('#xCrashAttribute').val()) {
                var defaultAttr = $('#xCrashAttribute').find('option:selected').attr('data-default');
                $('#xCrashAttribute').find('option[value=' + defaultAttr + ']').prop('selected', true);
            }
            if (!$('#yCrashAttribute').val()) {
                var defaultAttr = $('#yCrashAttribute').find('option:selected').attr('data-default');
                $('#yCrashAttribute').find('option[value=' + defaultAttr + ']').prop('selected', true);
            }
            crosstabs.countCrashes();
        });
        crosstabs.countCrashes();
        $('h2.analysis-header').text($('#crosstabs-header').val());
        document.title = $('#crosstabs-header').val() + ' | ' + document.title.split('|')[1].trim();
    }
    return crosstabs;
})();