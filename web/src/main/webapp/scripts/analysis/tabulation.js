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

    Tabulation.prototype.countCrashes = function(attribute) {
        var ctx = this;
        this.attributeCounts = [];
        var attributes = this.crashAttributes[attribute];
        attributes.forEach(function(attr) {
            ctx.attributeCounts.push({
                "name" : attr.name,
                "count" : ctx.crashes.filter(function(c) {
                var crashAttr = c[attribute];
                return crashAttr && crashAttr['id'] === attr.id
            }).length});
        });
        var totalCount = ctx.attributeCounts.reduce(function(a, b) {
            return a.count + b.count;
        }, 0);
        if (totalCount) {
           this.attributeCounts.push({"name" : "Not specified", "count" : totalCount});
        }
        this.tabulateCounts(attribute);
    }

    Tabulation.prototype.tabulateCounts = function(attribute) {
        $('#stats').html('');
        var attrName = $('#crashAttribute option:selected').text();
        var table = $('<table class="tabulated-data">');
        var thead = $('<thead>');
        var tbody = $('<tbody>');
        var tfoot = $('<tfoot>');
        var headerRow = $('<tr>');
        var headerCell1 = $('<th>').append(attrName);
        var headerCell2 = $('<th>').append('Crashes');
        thead.append(headerRow.append(headerCell1).append(headerCell2));
        var total = 0;
        this.attributeCounts.forEach(function(attr) {
            var row = $('<tr>');
            var cell1 = $('<td>').append(attr.name);
            var cell2 = $('<td align="right">').append(attr.count);
            tbody.append(row.append(cell1).append(cell2));
            total += attr.count;
        });
//        var notSpecRow = $('<tr>');
//        var notSpecCell1 = $('<td style="font-style: italic;">').append('Not Specified');
//        var notSpecCell2 = $('<td style="font-style: italic;" align="right">').append(this.crashes.length - total);
//        tbody.append(notSpecRow.append(notSpecCell1).append(notSpecCell2));
        var footerRow = $('<tr>');
        var footerCell1 = $('<td>').append('Total');
        var footerCell2 = $('<td align="right">').append(this.crashes.length);
        tfoot.append(footerRow.append(footerCell1).append(footerCell2));
        table.append(thead).append(tbody).append(tfoot);
        $('#stats').append(table);
        charting.createPieChart(this, attrName, 'stat-chart');
    }
    return Tabulation;
})();
