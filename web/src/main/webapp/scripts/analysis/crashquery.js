/**
 * Created by Frank on 6/24/15.
 */
var CrashQuery = (function() {
    return function CrashQuery(queryJson) {
        var query = queryJson ? JSON.parse(queryJson) : Object.create(null);
        if (!queryJson) {
            query.weather = util.nameListPair('Weather');
            query.endYear = util.nameValuePair('End Year');
            query.endDate = util.nameValuePair('End Date');
            query.district = util.nameListPair('District');
            query.endMonth = util.nameValuePair('End Month');
            query.startYear = util.nameValuePair('StartYear');
            query.startDate = util.nameValuePair('Start Date');
            query.crashCause = util.nameListPair('Crash Cause');
            query.startMonth = util.nameValuePair('Start Month');
            query.driverGender = util.nameListPair('Driver Sex');
            query.roadSurface = util.nameListPair('Road Surface');
            query.surfaceType = util.nameListPair('Surface Type');
            query.vehicleType = util.nameListPair('Vehicle Type');
            query.licenseType = util.nameListPair('License Type');
            query.junctionType = util.nameListPair('Junction Type');
            query.casualtyType = util.nameListPair('Casualty Type');
            query.casualtyGender = util.nameListPair('Casualty Sex');
            query.policeStation = util.nameListPair('Police Station');
            query.collisionType = util.nameListPair('Collision Type');
            query.crashSeverity = util.nameListPair('Crash Severity');
            query.casualtyClass = util.nameListPair('Casualty Class');
            query.driverAgeRange = util.nameListPair('Driver Age Range');
            query.surfaceCondition = util.nameListPair('Surface Condition');
            query.driverCasualtyType = util.nameListPair('Driver Casualty');
            query.casualtyAgeRange = util.nameListPair('Casualty Age Range');
            query.roadwayCharacter = util.nameListPair('Character of Roadway');
            query.driverBeltUsed = util.nameListPair('Driver Used Belt/Helmet');
            query.vehicleFailureType = util.nameListPair('Vehicle Failure Type');
            query.casualtyBeltUsed = util.nameListPair('Casualty Used Belt/Helmet');
        }

        query.addCheckedToList = function(id, list) {
            $('#' + id).find('input:checked').each(function() {
                var label = $('label[for=' + $(this).attr('id') + ']');
                if (label.length)
                    list.push(label.text());
            });
        }

        query.setWeathers = function() {
            this.addCheckedToList('weather', this.weather.list);
        }

        query.setDistricts = function() {
            this.addCheckedToList('district', this.district.list);
        }

        query.setCrashCauses = function() {
            this.addCheckedToList('crashCause', this.crashCause.list);
        }

        query.setRoadSurfaces = function() {
            this.addCheckedToList('roadSurface', this.roadSurface.list);
        }

        query.setSurfaceType = function() {
            this.addCheckedToList('surfaceType', this.surfaceType.list);
        }

        query.setVehicleType = function() {
            this.addCheckedToList('vehicleType', this.vehicleType.list);
        }

        query.setLicenseTypes = function() {
            this.addCheckedToList('licenseType', this.licenseType.list);
        }

        query.setJunctionTypes = function() {
            this.addCheckedToList('junctionType', this.junctionType.list);
        }

        query.setDriverGenders = function() {
            this.addCheckedToList('driverGender', this.driverGender.list);
        }

        query.setCasualtyTypes = function() {
            this.addCheckedToList('casualtyType', this.casualtyType.list);
        }

        query.setPoliceStations = function() {
            this.addCheckedToList('policeStation', this.policeStation.list);
        }

        query.setCollisionTypes = function() {
            this.addCheckedToList('collisionType', this.collisionType.list);
        }

        query.setCrashSeverities = function() {
            this.addCheckedToList('crashSeverity', this.crashSeverity.list);
        }

        query.setDriverBeltUseds = function() {
            this.addCheckedToList('driverBeltUsed', this.driverBeltUsed.list);
        }

        query.setCasualtyClasses = function() {
            this.addCheckedToList('casualtyClass', this.casualtyClass.list);
        }

        query.setCasualtyGenders = function() {
            this.addCheckedToList('casualtyGender', this.casualtyGender.list);
        }

        query.setDriverAgeRanges = function() {
            this.addCheckedToList('driverAgeRange', this.driverAgeRange.list);
        }

        query.setCasualtyBeltUseds = function() {
            this.addCheckedToList('casualtyBeltUsed', this.casualtyBeltUsed.list);
        }

        query.setCasualtyAgeRanges = function() {
            this.addCheckedToList('casualtyAgeRange', this.casualtyAgeRange.list);
        }

        query.setSurfaceConditions = function() {
            this.addCheckedToList('surfaceCondition', this.surfaceCondition.list);
        }

        query.setRoadwayCharacters = function() {
            this.addCheckedToList('roadwayCharacter', this.roadwayCharacter.list);
        }

        query.setVehicleFailureTypes = function() {
            this.addCheckedToList('vehicleFailureType', this.vehicleFailureType.list);
        }

        query.setDriverCasualtyTypes = function() {
            this.addCheckedToList('driverCasualtyType', this.driverCasualtyType.list);
        }

        query.setTimeDimension = function() {
            if ($('#endDateString').val())
                this.endDate = $('#endDateString').val();
            if ($('#startDateString').val())
                this.startDate = $('#startDateString').val();
            if ($('#endYear').val())
                this.endYear = $('#endYear option:selected').text();
            if ($('#endMonth').val())
                this.endMonth = $('#endMonth option:selected').text();
            if ($('#startYear').val())
                this.startYear = $('#startYear option:selected').text();
            if ($('#startMonth').val())
                this.startMonth = $('#startMonth option:selected').text();
        }

        query.render = function() {
            var summaryTable = $('<table>');
            var count = 0;
            var row = $('<tr>');
            for (var prop in this) {
                var property = this[prop];
                if (property && property.list && Array.isArray(property.list) && property.list.length) {
                    count++;
                    var cell = $('<td width="33%"><div class="query-label"></div><div class="query-value"></div></td>');
                    cell.find('div:first').text(property.name + ':');
                    cell.find('div:last').text(property.list.toString().replace(/,/g, ', '));
                    row.append(cell);
                    if (count % 3 == 0) {
                        summaryTable.append(row);
                        row = $('<tr>');
                    }
                }
            }
            if (count % 3 != 0)
                summaryTable.append(row);
            if (count) {
                $('#query-summary').html('<h6>Query Summary</h6>').append(summaryTable);
                $('#query-summary').css('border', 'solid 1px #8C8615');
            }
            else
                $('#query-summary').css('border', 'none');
        }

        query.build = function() {
            this.setWeathers();
            this.setDistricts();
            this.setCrashCauses();
            this.setSurfaceType();
            this.setVehicleType();
            this.setLicenseTypes();
            this.setRoadSurfaces();
            this.setJunctionTypes();
            this.setDriverGenders();
            this.setCasualtyTypes();
            this.setTimeDimension();
            this.setPoliceStations();
            this.setCollisionTypes();
            this.setCrashSeverities();
            this.setDriverBeltUseds();
            this.setCasualtyClasses();
            this.setCasualtyGenders();
            this.setDriverAgeRanges();
            this.setCasualtyBeltUseds();
            this.setCasualtyAgeRanges();
            this.setSurfaceConditions();
            this.setRoadwayCharacters();
            this.setVehicleFailureTypes();
            this.setDriverCasualtyTypes();

            return this;
        }

        return queryJson ? query : query.build();
    }
})();