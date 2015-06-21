/**
 * Created by Frank on 3/18/15.
 */

var Tabulation = (function() {
    function Tabulation() {
        window.tabulation = this;
        this.crashes = JSON.parse(localStorage.crashesJSON).crashes;
        this.crashAttributes = JSON.parse(localStorage.crashAttributesJSON);
        this.attributeCounts = [];
    }

    Tabulation.prototype.countCrashes = function(element) {
        this.attributeCounts = [];
        var attribute = element.val();
        var selected = element.find('option:selected');
        var crashProp = selected.attr('data-prefix');
        var timeDimension = selected.attr('data-time');
        var attributes = this.crashAttributes[attribute];
        if (timeDimension)
            this.countTimeAttributes(attributes, timeDimension);
        else
            this.countAttributes(attributes, attribute, crashProp);
        var notSpec = this.crashes.length - this.attributeCounts.reduce(function(total, b) {
            return total + b.count;
        }, 0);
        if (notSpec) {
           this.attributeCounts.push({"name" : "Not specified", "count" : notSpec});
        }
        this.tabulateCounts(!timeDimension);
    }

    Tabulation.prototype.countAttributes = function(attributes, attribute, crashProp) {
        attributes.map(function(attr) {
            this.attributeCounts.push({
                name : attr.name,
                count : this.crashes.filter(function(c) {
                    var crashAttr = crashProp ? c[crashProp][attribute] : c[attribute];
                    return crashAttr && crashAttr['id'] === attr.id
                }).length});
        }, this);
    }

    Tabulation.prototype.countTimeAttributes = function(attributes, timeDimension) {
        if (timeDimension == 'month') {
            attributes.map(function(month, index) {
                this.attributeCounts.push({
                    name : month,
                    count : this.crashes.filter(function(c) {
                        var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                        return crashDate && crashDate.getMonth() == index;
                    }).length });
            }, this);
        } else {
            attributes.map(function(year) {
                this.attributeCounts.push({
                    name : year,
                    count : this.crashes.filter(function(c) {
                        var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                        return crashDate && crashDate.getFullYear() == year;
                    }).length });
            }, this);
        }
    }

    Tabulation.prototype.tabulateCounts = function(slice) {
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
    return Tabulation;
})();
