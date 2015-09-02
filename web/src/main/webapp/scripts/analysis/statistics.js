/**
 * Created by Frank on 3/18/15.
 */

(function() {
    var statistics = Object.create(null);

    statistics.countCrashes = function(element) {
        this.attributeCounts = [];
        var attribute = element.val();
        var selected = element.find('option:selected');
        var crashProp = selected.attr('data-prefix');
        var rangeAttribute = selected.attr('data-range');
        var attributes = crashAttributes[attribute];
        if (rangeAttribute)
            this.countRangeAttributes(attributes, rangeAttribute);
        else
            this.countAttributes(attributes, attribute, crashProp);
        var notSpec = this.crashes.length - this.attributeCounts.reduce(function(total, b) {
            return total + b.count;
        }, 0);
        if (notSpec) {
           this.attributeCounts.push({"name" : "Not specified", "count" : notSpec});
        }
        this.tabulateCounts(!rangeAttribute);
    }

    statistics.countAttributes = function(attributes, attribute, crashProp) {
        attributes.map(function(attr) {
            this.attributeCounts.push({
                name : attr.name,
                count : this.crashes.filter(function(c) {
                    var crashAttr = crashProp ? c[crashProp][attribute] : c[attribute];
                    return crashAttr && crashAttr['id'] === attr.id
                }).length});
        }, this);
    }

    statistics.countRangeAttributes = function(attributes, rangeType) {
        if (rangeType == 'month') {
            attributes.map(function(month, index) {
                this.attributeCounts.push({
                    name : month,
                    count : this.crashes.filter(function(c) {
                        var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                        return crashDate && crashDate.getMonth() == index;
                    }).length });
            }, this);
        } else if (rangeType == 'year') {
            attributes.map(function(year) {
                this.attributeCounts.push({
                    name : year,
                    count : this.crashes.filter(function(c) {
                        var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                        return crashDate && crashDate.getFullYear() == year;
                    }).length });
            }, this);
        } else if (rangeType == 'weight') {
            attributes.map(function(weightRange) {
                this.attributeCounts.push({
                    name : weightRange.label,
                    count : this.crashes.filter(function(c) {
                        if (c.weight) {
                            if (weightRange.maxWeight)
                                return (c.weight >= weightRange.minWeight && c.weight <= weightRange.maxWeight);
                            else
                                return c.weight >= weightRange.minWeight;
                        }
                        return false;
                    }).length });
            }, this);
        }
    }

    statistics.tabulateCounts = function(slice) {
        $('#stats').html('');
        var attrName = $('#crashAttribute option:selected').text();
        var table = $('<table class="table table-condensed table-striped table-hover">');
        var thead = $('<thead>');
        var tbody = $('<tbody>');
        var tfoot = $('<tfoot style="font-weight: bold;">');
        var headerRow = $('<tr>');
        var header1 = $('<a href="" onclick="return false">').append(attrName);
        var headerCell1 = $('<th>').append(header1);
        var header2 = $('<a href="" onclick="return false">').append('Crashes');
        var headerCell2 = $('<th style="text-align: right !important;">').append(header2);
        thead.append(headerRow.append(headerCell1).append(headerCell2));
        var total = 0;
        this.attributeCounts.forEach(function(attr) {
            var row = $('<tr>');
            var cell1 = $('<td>').append(attr.name);
            var cell2 = $('<td align="right">').append(attr.count);
            tbody.append(row.append(cell1).append(cell2));
            total += attr.count;
        });
        var footerRow = $('<tr>');
        var footerCell1 = $('<td>').append('Total');
        var footerCell2 = $('<td align="right">').append(this.crashes.length);
        tfoot.append(footerRow.append(footerCell1).append(footerCell2));
        table.append(thead).append(tbody).append(tfoot);
        $('#stats').append(table);
        charting.createPieChart(this, attrName, 'stat-chart', slice);
        charting.createColumnChart(this, attrName, 'stat-column');
    }
    statistics.init = function() {
        $(document).ready(function() {
            util.initCrashData();
            statistics.attributeCounts = [];
            statistics.crashes = window.crashes
            statistics.crashAttributes = window.crashAttributes;
            $('#crashAttribute').change(function () {
                statistics.countCrashes($(this));
            }).trigger('change');
            ui.renderQuerySummary();
        });
    }
    statistics.init();
})();
