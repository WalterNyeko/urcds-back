/**
 * Created by Frank on 3/18/15.
 */

(function() {
    var statistics = Object.create(null);

    statistics.countCrashes = function() {
        this.attributeCounts = [];
        var attrElement = $('#crashAttribute');
        var attribute = attrElement.val();
        var attributes = util.getAttributes(attribute);
        var selected = attrElement.find('option:selected');
        var crashProp = selected.attr('data-prefix');
        var rangeAttribute = selected.attr('data-range');
        var attributeType = attrElement.find('option:selected').attr('data-attr-type');
        this.crashFilter = new CrashFilter(attributeType, rangeAttribute, this.crashes);
        this.countAttributes(rangeAttribute, attributes, attribute, crashProp);
        if (this.canSum()) {
            var totalStats = this.attributeCounts.reduce(function(total, b) {
                return total + b.count;
            }, 0);
            var notSpec = this.totalUnits() - totalStats;
            if (notSpec) {
               this.attributeCounts.push({"name" : "Not specified", "count" : notSpec});
            }
        }
        this.tabulateCounts();
    }
    statistics.canSum = function() {
        return util.isCrashAttribute('#crashAttribute') || this.units() == $('#crashAttribute').find('option:selected').attr('data-attr-type');
    }
    statistics.units = function() {
        return $('#unit').val();
    }
    statistics.totalUnits = function() {
        return this.crashFilter.totalUnits($('#unit').val());
    }
    statistics.countAttributes = function(rangeAttribute, attributes, attribute, crashProp) {
        attributes.map(function(attr, index) {
            var params = [];
            if (util.isCrashAttribute('#crashAttribute')) {
                if (rangeAttribute) {
                    if (util.isValueRange(rangeAttribute))
                        params.push(attr);
                    else
                        params.push(index);
                } else
                    params = util.pushArray(params, attr, attribute, crashProp);
            } else
                params = util.pushArray(params, attr, attribute);
            this.attributeCounts.push({
                name: attr.name || attr,
                count: this.countUnits(params)
            });
        }, this);
    }
    statistics.countUnits = function(params) {
        this.count = 0;
        var unit = $('#unit').val();
        switch(unit) {
            case 'crash':
                this.count = this.crashFilter.filterCrashes(params).length;
                break;
            case 'vehicle':
                this.count = this.crashFilter.filterVehicles(params).length;
                break;
            case 'casualty':
                this.count = this.crashFilter.filterCasualties(params).length;
                break;
        }
        return this.count;
    }
    statistics.tabulateCounts = function() {
        $('#stats').html('');
        var unitName= $('#unit option:selected').text();
        var attrName = $('#crashAttribute option:selected').text();
        var table = $('<table class="table table-condensed table-striped table-hover">');
        var thead = $('<thead>');
        var tbody = $('<tbody>');
        var tfoot = $('<tfoot style="font-weight: bold;">');
        var headerRow = $('<tr>');
        var header1 = $('<a href="" onclick="return false">').append(attrName);
        var headerCell1 = $('<th>').append(header1);
        var header2 = $('<a href="" onclick="return false">').append(unitName);
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
        var footerCell2 = $('<td align="right">').append(total);
        tfoot.append(footerRow.append(footerCell1).append(footerCell2));
        table.append(thead).append(tbody);
        this.canSum() && table.append(tfoot);
        $('#stats').append(table);
        charting.createPieChart(this, attrName, 'stat-chart');
        charting.createColumnChart(this, attrName, 'stat-column');
    }
    statistics.init = function() {
        $(document).ready(function() {
            util.initCrashData();
            statistics.attributeCounts = [];
            statistics.crashes = window.crashes;
            $('#crashAttribute, #unit').change(function () {
                if ($(this).val())
                    statistics.countCrashes();
                else {
                    var defaultAttr = $(this).find('option:selected').attr('data-default');
                    $(this).find('option[value=' + defaultAttr + ']').prop('selected', true);
                    statistics.countCrashes();
                }
            });
            $('#crashAttribute').trigger('change')
            ui.renderQuerySummary();
        });
    }
    statistics.init();
})();
