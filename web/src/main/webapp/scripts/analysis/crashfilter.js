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
        filter.match = function(x, attribute, attributeName) {
            switch(this.type) {
                case 'crash':
                    return this.matchCrash(x, attribute, attributeName);
                case 'vehicle':
                    return this.matchVehicle(x, attribute, attributeName);
                case 'casualty':
                    return this.matchCasualty(x, attribute, attributeName);
            }
        }
        filter.matchCrash = function(crash, attribute, attributeName) {
            if (util.isNullAttribute(attribute)) {
                if (attributeName == 'district')
                    attributeName = 'policeStation';
                return !crash[attributeName];
            }
            var crashAttr = attributeName == 'district' ? crash['policeStation']['district'] : crash[attributeName];
            return crashAttr && crashAttr['id'] === attribute.id;
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
                    crashFilter.matchCrash = function(crash, day) {
                        if (util.isNullAttribute(day))
                            return Object.getPrototypeOf(this).matchCrash(crash, day, 'crashDateTime');
                        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
                        return crashDate && crashDate.getDay() == day;
                    }
                    break;
                case 'month':
                    crashFilter.matchCrash = function(crash, month) {
                        if (util.isNullAttribute(month))
                            return Object.getPrototypeOf(this).matchCrash(crash, month, 'crashDateTime');
                        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
                        return crashDate && crashDate.getMonth() == month;
                    }
                    break;
                case 'year':
                    crashFilter.matchCrash = function(crash, year) {
                        if (util.isNullAttribute(year))
                            return Object.getPrototypeOf(this).matchCrash(crash, year, 'crashDateTime');
                        var crashDate = crash.crashDateTime ? new Date(crash.crashDateTime) : null;
                        return crashDate && crashDate.getFullYear() == year;
                    }
                    break;
                case 'weight':
                    crashFilter.matchCrash = function(crash, weight) {
                        if (util.isNullAttribute(weight))
                            return Object.getPrototypeOf(this).matchCrash(crash, weight, 'weight');
                        var weightRange = weight;
                        if (c.weight) {
                            if (weightRange.maxWeight)
                                return (crash.weight >= weightRange.minWeight && crash.weight <= weightRange.maxWeight);
                            else
                                return crash.weight >= weightRange.minWeight;
                        }
                        return false;
                    }
            }
        }
        crashFilter.filterCrashes = function(params) {
            var attribute = params[0], attributeName =  params[1];
            return this.crashes.filter(function(c) {
                return this.matchCrash(c, attribute, attributeName);
            }, this);
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
            var nullAttribute = util.isNullAttribute(attribute);
            switch(attributeName) {
                case 'vehicleType':
                    if (nullAttribute)
                        return vehicle && !vehicle.vehicleType;
                    return vehicle && vehicle.vehicleType && vehicle.vehicleType.id == attribute.id;
                case 'ageRange':
                    if (nullAttribute)
                        return vehicle && (!vehicle.driver || !vehicle.driver.age);
                    return vehicle && vehicle.driver && vehicle.driver.age && vehicle.driver.age >= attribute.minAge && vehicle.driver.age <= attribute.maxAge;
                case 'gender':
                    if (nullAttribute)
                        return vehicle && (!vehicle.driver || !vehicle.driver.gender);
                    return vehicle && vehicle.driver && vehicle.driver.gender == attribute.value;
                case 'licenseType':
                    if (nullAttribute)
                        return false;
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
                    if (nullAttribute)
                        return false;
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
                    if (nullAttribute)
                        return vehicle && (!vehicle.driver || !vehicle.driver.casualtyType);
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
            var nullAttribute = util.isNullAttribute(attribute);
            switch(attributeName) {
                case 'casualtyType':
                    if (nullAttribute)
                        return casualty && !casualty.casualtyType;
                    return casualty && casualty.casualtyType && casualty.casualtyType.id == attribute.id;
                case 'casualtyClass':
                    if (nullAttribute)
                        return casualty && !casualty.casualtyClass;
                    return casualty && casualty.casualtyClass && casualty.casualtyClass.id == attribute.id;
                case 'gender':
                    if (nullAttribute)
                        return casualty && !casualty.gender;
                    return casualty && casualty.gender && casualty.gender == attribute.value;
                case 'ageRange':
                    if (nullAttribute)
                        return casualty && !casualty.age;
                    return casualty && casualty.age && casualty.age >= attribute.minAge && casualty.age <= attribute.maxAge;
                case 'beltUsedOption':
                    if (nullAttribute)
                        return false;
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