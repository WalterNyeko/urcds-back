/**
 * Created by Frank on 9/22/15.
 */
var CrashFilter = (function() {
    function CrashFilter(type, rangeAttribute, crashes) {
        this.type = type;
        this.crashes = crashes;
        switch(type) {
            case 'crash':
                return this.createCrashFilter(rangeAttribute);
            case 'vehicle':
                return this.createVehicleFilter();
            case 'casualty':
                return this.createCasualtyFilter();
        }
    }
    CrashFilter.prototype.createFilter = function() {
        var filter = Object.create(null);
        filter.type = this.type;
        filter.crashes = this.crashes;

        filter.filter = function(params, type) {
            type = type ? type : this.type;
            switch(type) {
                case 'crash':
                    return this.filterCrashes(params);
                case 'vehicle':
                    return this.filterVehicles(params);
                case 'casualty':
                    return this.filterCasualties(params);
            }
        }
        filter.totalUnits = function(unit) {
            unit = unit ? unit : this.type;
            switch(unit) {
                case 'crash' :
                    return this.crashes.length;
                case 'vehicle':
                    return this.crashes.reduce(function(sum, b) { return sum + b.vehicles.length }, 0);
                case 'casualty':
                    var count = this.crashes.reduce(function(sum, b) { return sum + b.casualties.length }, 0);
                    this.crashes.map(function(c) { count += c.vehicles.filter(function(v) { return util.injuredCasualty(v.driver.casualtyType) }).length }, this);
                    return count;
            }
        }
        filter.getCrashCasualties = function(crashes) {
            var casualties = crashes.filter(function(c) { return c.casualties.length }).map(function(c) { return c.casualties }).reduce(function(a, b) { return a.concat(b) }, []);
            crashes.map(function(c) {
                c.vehicles.map(function(v) {
                    var casualty = util.driverToCasualty(v);
                    casualty && casualties.push(casualty);
                })
            });
            return casualties;
        }
        filter.getCrashVehicles = function(crashes) {
            return crashes.filter(function(c) { return c.vehicles.length }).map(function(c) { return c.vehicles }).reduce(function(a, b) { return a.concat(b) }, []);
        }
        return filter;
    }
    CrashFilter.prototype.createCrashFilter = function(rangeAttribute) {
        var crashFilter = Object.create(this.createFilter());

        if (rangeAttribute) {
            switch(rangeAttribute) {
                case 'day':
                    crashFilter.filterCrashes = function(params) {
                        return this.crashes.filter(function(c) {
                            var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                            return crashDate && crashDate.getDay() == params[0];
                        });
                    }
                    break;
                case 'month':
                    crashFilter.filterCrashes = function(params) {
                        return this.crashes.filter(function(c) {
                            var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                            return crashDate && crashDate.getMonth() == params[0];
                        });
                    }
                    break;
                case 'year':
                    crashFilter.filterCrashes = function(params) {
                        return this.crashes.filter(function(c) {
                            var crashDate = c.crashDateTime ? new Date(c.crashDateTime) : null;
                            return crashDate && crashDate.getFullYear() == params[0];
                        });
                    }
                    break;
                case 'weight':
                    crashFilter.filterCrashes = function(params) {
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
            crashFilter.filterCrashes = function(params) {
                var attr = params[0], attribute =  params[1], crashProp = params[2];
                return this.crashes.filter(function(c) {
                    var crashAttr = crashProp ? c[crashProp][attribute] : c[attribute];
                    return crashAttr && crashAttr['id'] === attr.id
                });
            }
        }
        crashFilter.filterVehicles = function(params) {
            return this.getCrashVehicles(this.filterCrashes(params));
        }
        crashFilter.filterCasualties = function(params) {
            return this.getCrashCasualties(this.filterCrashes(params));
        }
        return crashFilter;
    }
    CrashFilter.prototype.createVehicleFilter = function() {
        var vehicleFilter = Object.create(this.createFilter());

        vehicleFilter.filterVehicles = function(params) {
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
        vehicleFilter.filterCrashes = function(params) {
            var vehicles = this.filterVehicles(params);
            return this.crashes.filter(function(c) {
                return c.vehicles.some(function(v) {
                    return vehicles.some(function(x) { return x.id == v.id });
                });
            });
        }
        vehicleFilter.filterCasualties = function(params) {
            return this.getCrashCasualties(this.filterCrashes(params));
        }
        vehicleFilter.matchVehicle = function(vehicle, attribute, attributeName) {
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
        return vehicleFilter
    }
    CrashFilter.prototype.createCasualtyFilter = function() {
        var casualtyFilter = Object.create(this.createFilter());

        casualtyFilter.filterCasualties = function(params) {
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
        casualtyFilter.filterCrashes = function(params) {
            var casualties = this.filterCasualties(params);
            return this.crashes.filter(function(c) {
                return c.casualties.some(function(v) {
                    return casualties.some(function(x) { return x.id == v.id });
                }) || c.vehicles.some(function(v) {
                    return casualties.some(function(x) { return x.vehicle && x.vehicle.id == v.id });
                });
            });
        }
        casualtyFilter.filterVehicles = function(params) {
            return this.getCrashVehicles(this.filterCrashes(params));
        }
        casualtyFilter.matchCasualty = function(casualty, attribute, attributeName) {
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
        return casualtyFilter;
    }
    return CrashFilter;
})();