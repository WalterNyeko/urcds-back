/**
 * Created by Frank on 3/18/15.
 */

(function() {
    var statistics = Object.create(null);
    statistics.filterCrashes = new Function();
    statistics.filterVehicles = new Function();
    statistics.filterCasualties = new Function();

    statistics.countCrashes = function() {
        this.attributeCounts = [];
        var attrElement = $('#crashAttribute');
        var attribute = attrElement.val();
        var selected = attrElement.find('option:selected');
        var crashProp = selected.attr('data-prefix');
        var rangeAttribute = selected.attr('data-range');
        var attributes = crashAttributes[attribute];
        this.setFilters(rangeAttribute);
        this.countAttributes(rangeAttribute, attributes, attribute, crashProp);
        var totalStats = this.attributeCounts.reduce(function(total, b) {
            return total + b.count;
        }, 0);
        var notSpec = this.totalUnits() - totalStats;
        if (notSpec) {
           this.attributeCounts.push({"name" : "Not specified", "count" : notSpec});
        }
        this.tabulateCounts();
    }
    statistics.units = function() {
        return $('#unit').val();
    }
    statistics.totalUnits = function() {
        var unit = $('#unit').val();
        switch(unit) {
            case 'vehicles':
                return this.crashes.reduce(function(sum, b) { return sum + b.vehicles.length }, 0);
            case 'casualties':
                var count = this.crashes.reduce(function(sum, b) { return sum + b.casualties.length }, 0);
                this.crashes.map(function(c) { count += c.vehicles.filter(function(v) { return util.injuredCasualty(v.driver.casualtyType) }).length }, this);
                return count;
            default :
                return this.crashes.length;
        }
    }
    statistics.setFilters = function(rangeAttribute) {
        if (this.crashAttribute())
            this.setCrashAttributeFilters(rangeAttribute);
        else if (this.vehicleAttribute()) {
            this.setVehicleAttributeFilters();
        } else if (this.casualtyAttribute())
            this.setCasualtyAttributeFilters(rangeAttribute);
    }
    statistics.setCrashAttributeFilters = function(rangeAttribute) {
        if (rangeAttribute) {
            switch(rangeAttribute) {
                case 'month':
                    this.filterCrashes = function(params) {
                        return this.crashes.filter(function(c) {
                            var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                            return crashDate && crashDate.getMonth() == params[0];
                        });
                    }
                    break;
                case 'year':
                    this.filterCrashes = function(params) {
                        return this.crashes.filter(function(c) {
                            var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                            return crashDate && crashDate.getFullYear() == params[0];
                        });
                    }
                    break;
                case 'weight':
                    this.filterCrashes = function(params) {
                        return this.crashes.filter(function(c) {
                            var weightRange = params[0];
                            if (c.weight) {
                                if (weightRange.maxWeight)
                                    return (c.weight >= weightRange.minWeight && c.weight <= weightRange.maxWeight);
                                else
                                    return c.weight >= weightRange.minWeight;
                            }
                            return false;
                        });
                    }
            }
        } else {
            this.filterCrashes = function(params) {
                var attr = params[0], attribute =  params[1], crashProp = params[2];
                return this.crashes.filter(function(c) {
                    var crashAttr = crashProp ? c[crashProp][attribute] : c[attribute];
                    return crashAttr && crashAttr['id'] === attr.id
                });
            }
        }
        this.filterVehicles = function(params) {
            return this.getCrashVehicles(this.filterCrashes(params));
        }
        this.filterCasualties = function(params) {
            return this.getCrashCasualties(this.filterCrashes(params));
        }
    }
    statistics.setVehicleAttributeFilters = function() {
        this.filterVehicles = function(params) {
            var vehicles = [];
            var attribute = params[0];
            var attributeName = params[1];
            this.crashes.map(function(c) {
                c.vehicles.map(function(v) {
                    this.matchVehicle(v, attribute, attributeName) && vehicles.push(v);
                }, this);
            }, this);
            return vehicles;
        }
        this.filterCrashes = function(params) {
            var vehicles = this.filterVehicles(params);
            return this.crashes.filter(function(c) {
                return c.vehicles.some(function(v) {
                    return vehicles.some(function(x) { return x.id == v.id });
                });
            });
        }
        this.filterCasualties = function(params) {
            return this.getCrashCasualties(this.filterCrashes(params));
        }
    }
    statistics.setCasualtyAttributeFilters = function() {
        this.filterCasualties = function(params) {
            var casualties = [];
            var attribute = params[0];
            var attributeName = params[1];
            this.crashes.map(function(c) {
                c.casualties.map(function(x) {
                    this.matchCasualty(x, attribute, attributeName) && casualties.push(x);
                }, this);
                c.vehicles.map(function(v) {
                    var casualty = util.driverToCasualty(v);
                    if (casualty && this.matchCasualty(casualty, attribute, attributeName))
                        casualties.push(casualty);
                }, this);
            }, this);
            return casualties;
        }
        this.filterCrashes = function(params) {
            var casualties = this.filterCasualties(params);
            return this.crashes.filter(function(c) {
                return c.casualties.some(function(v) {
                    return casualties.some(function(x) { return x.id == v.id });
                }) || c.vehicles.some(function(v) {
                    return casualties.some(function(x) { return x.vehicle && x.vehicle.id == v.id });
                });
            });
        }
        this.filterVehicles = function(params) {
            return this.getCrashVehicles(this.filterCrashes(params));
        }
    }
    statistics.getCrashCasualties = function(crashes) {
        var casualties = crashes.filter(function(c) { return c.casualties.length }).map(function(c) { return c.casualties }).reduce(function(a, b) { return a.concat(b) }, []);
        crashes.map(function(c) {
            c.vehicles.map(function(v) {
                var casualty = util.driverToCasualty(v);
                casualty && casualties.push(casualty);
            })
        });
        return casualties;
    }
    statistics.getCrashVehicles = function(crashes) {
        return crashes.filter(function(c) { return c.vehicles.length }).map(function(c) { return c.vehicles }).reduce(function(a, b) { return a.concat(b) }, []);
    }
    statistics.matchVehicle = function(vehicle, attribute, attributeName) {
        switch(attributeName) {
            case 'vehicleType':
                return vehicle && vehicle.vehicleType && vehicle.vehicleType.id == attribute.id;
            case 'ageRange':
                return vehicle && vehicle.driver && vehicle.driver.age && vehicle.driver.age >= attribute.minAge && vehicle.driver.age <= attribute.maxAge;
            case 'gender':
                return vehicle && vehicle.driver && vehicle.driver.gender == attribute.value;
            case 'licenseType':
                if (vehicle && vehicle.driver) {
                    var type = null;
                    if (attribute.id == 1)
                        type = true;
                    else if (attribute.id == 0)
                        type = false;
                    return vehicle.driver.licenseValid == type;
                }
                return false;
            case 'beltUsedOption':
                if (vehicle && vehicle.driver) {
                    var type = null;
                    if (attribute.id == 1)
                        type = true;
                    else if (attribute.id == 0)
                        type = false;
                    return vehicle.driver.beltOrHelmetUsed == type;
                }
                return false;
            case 'casualtyType':
                return vehicle && vehicle.driver && vehicle.driver.casualtyType && vehicle.driver.casualtyType.id == attribute.id;
            default:
                return false;
        }
    }
    statistics.matchCasualty = function(casualty, attribute, attributeName) {
        switch(attributeName) {
            case 'casualtyType':
                return casualty && casualty.casualtyType && casualty.casualtyType.id == attribute.id;
            case 'casualtyClass':
                return casualty && casualty.casualtyClass && casualty.casualtyClass.id == attribute.id;
            case 'gender':
                return casualty && casualty.gender && casualty.gender == attribute.value;
            case 'ageRange':
                return casualty && casualty.age && casualty.age >= attribute.minAge && casualty.age <= attribute.maxAge;
            case 'beltUsedOption':
                var type = null;
                if (attribute.id == 1)
                    type = true;
                else if (attribute.id == 0)
                    type = false;
                return casualty.beltOrHelmetUsed == type;
            default:
                return false;
        }
    }
    statistics.countAttributes = function(rangeAttribute, attributes, attribute, crashProp) {
        attributes.map(function(attr, index) {
            var params = [];
            if (this.crashAttribute()) {
                if (rangeAttribute) {
                    if (rangeAttribute == 'month')
                        params.push(index);
                    else
                        params.push(attr);
                } else
                    params = util.pushArray(params, attr, attribute, crashProp);
            } else
                params = util.pushArray(params, attr, attribute);
            var name = attr.name ? attr.name : attr.label ? attr.label : attr;
            !isNaN(name) && (name = name.toString());
            this.attributeCounts.push({
                name: name,
                count: this.countUnits(params)
            });
        }, this);
    }
    statistics.countUnits = function(params) {
        this.count = 0;
        var unit = $('#unit').val();
        switch(unit) {
            case 'crashes':
                this.count = this.filterCrashes(params).length;
                break;
            case 'vehicles':
                this.count = this.filterVehicles(params).length;
                break;
            case 'casualties':
                this.count = this.filterCasualties(params).length;
                break;
        }
        return this.count;
    }
    statistics.tabulateCounts = function() {
        $('#stats').html('');
        var attrName = $('#crashAttribute option:selected').text();
        var unitName= $('#unit option:selected').text();
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
        var footerCell2 = $('<td align="right">').append(this.totalUnits());
        tfoot.append(footerRow.append(footerCell1).append(footerCell2));
        table.append(thead).append(tbody).append(tfoot);
        $('#stats').append(table);
        charting.createPieChart(this, attrName, 'stat-chart');
        charting.createColumnChart(this, attrName, 'stat-column');
    }
    statistics.crashAttribute = function() {
        return $('#crashAttribute').find('option:selected').attr('data-attr-type') == 'crash';
    }
    statistics.vehicleAttribute = function() {
        return $('#crashAttribute').find('option:selected').attr('data-attr-type') == 'vehicle';
    }
    statistics.casualtyAttribute = function() {
        return $('#crashAttribute').find('option:selected').attr('data-attr-type') == 'casualty';
    }
    statistics.init = function() {
        $(document).ready(function() {
            util.initCrashData();
            statistics.attributeCounts = [];
            statistics.crashes = window.crashes
            statistics.crashAttributes = window.crashAttributes;
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
