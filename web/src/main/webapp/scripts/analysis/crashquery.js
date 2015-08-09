/**
 * Created by Frank on 6/24/15.
 */
var CrashQuery = (function() {
    return function CrashQuery(queryJson) {
        var query = Object.create(null);
        if (queryJson)
            query = JSON.parse(queryJson);
        else {
            query.startYear = util.nameValuePair('StartYear');
            query.startMonth = util.nameValuePair('Start Month');
            query.startDate = util.nameValuePair('Start Date');
            query.endYear = util.nameValuePair('End Year');
            query.endMonth = util.nameValuePair('End Month');
            query.endDate = util.nameValuePair('End Date');
            query.district = util.nameListPair('District');
            query.policeStation = util.nameListPair('Police Station');
            query.crashSeverity = util.nameListPair('Crash Severity');
            query.collisionType = util.nameListPair('Collision Type');
            query.crashCause = util.nameListPair('Crash Cause');
            query.vehicleFailureType = util.nameListPair('Vehicle Failure Type');
            query.weather = util.nameListPair('Weather');
            query.surfaceCondition = util.nameListPair('Surface Condition');
            query.roadSurface = util.nameListPair('Road Surface');
            query.surfaceType = util.nameListPair('Surface Type');
            query.roadwayCharacter = util.nameListPair('Character of Roadway');
            query.junctionType = util.nameListPair('Junction Type');
            query.vehicleType = util.nameListPair('Vehicle Type');
            query.licenseType = util.nameListPair('License Type');
            query.driverGender = util.nameListPair('Driver Sex');
            query.driverAgeRange = util.nameListPair('Driver Age Range');
            query.driverBeltUsed = util.nameListPair('Driver Used Belt/Helmet');
            query.driverCasualtyType = util.nameListPair('Driver Casualty');
            query.casualtyClass = util.nameListPair('Casualty Class');
            query.casualtyType = util.nameListPair('Casualty Type');
            query.casualtyGender = util.nameListPair('Casualty Sex');
            query.casualtyAgeRange = util.nameListPair('Casualty Age Range');
            query.casualtyBeltUsed = util.nameListPair('Casualty Used Belt/Helmet');
        }

        query.addCheckedToList = function(tableId, list) {
            $('#' + tableId).find('input:checked').each(function() {
                var id = $(this).val();
                var label = $('label[for=' + $(this).attr('id') + ']');
                if (label.length)
                    list.push({ id: id, name: label.text()});
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
            if ($('#startYear').val())
                this.startYear = $('#startYear option:selected').text();
            if ($('#startMonth').val())
                this.startMonth = $('#startMonth option:selected').text();
            if ($('#startDateString').val())
                this.startDate = $('#startDateString').val();
            if ($('#endYear').val())
                this.endYear = $('#endYear option:selected').text();
            if ($('#endMonth').val())
                this.endMonth = $('#endMonth option:selected').text();
            if ($('#endDateString').val())
                this.endDate = $('#endDateString').val();
        }

        query.getTimeDimensionText = function(prop) {
            switch(prop) {
                case 'startYear':
                    return 'Start Year';
                case 'startMonth':
                    return 'Start Month';
                case 'startDate':
                    return 'Start Date';
                case 'endYear':
                    return 'End Year';
                case 'endMonth':
                    return 'End Month';
                case 'endDate':
                    return 'End Date';
                default:
                    return null;
            }
        }

        query.render = function() {
            var summaryTable = $('<table>');
            var count = 0;
            var row = $('<tr>');
            for (var prop in this) {
                var property = this[prop];
                if (property) {
                    var cell = $('<td width="33%"><div class="query-label"></div><div class="query-value"></div></td>');
                    if (property.list && Array.isArray(property.list) && property.list.length) {
                        cell.find('div:first').text(property.name + ':');
                        cell.find('div:last').text(util.propertyListToString(property.list));
                    } else if (typeof property === 'string') {
                        var text = this.getTimeDimensionText(prop);
                        if (!text)
                            continue;
                        cell.find('div:last').text(property);
                        cell.find('div:first').text(text + ':');
                    } else {
                        continue;
                    }
                    count++;
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
                $('#query-summary').append($('<div class="save-query"><input id="saveQuery" type="button" value="Save Query">'));
                $('#saveQuery').click(function() {
                    loadDialog({
                        width: 'auto',
                        height: 'auto',
                        message: "Loading form...",
                        dialogTitle: "Save Crash Query",
                        dialogButtons: ui.queryFormButtons()
                    });
                    var queryForm = ui.createQueryForm();
                    queryForm.find('table tr:last td').append(summaryTable.clone());
                    $('#map-canvas').html(queryForm);
                });
            }
            else
                $('#query-summary').css('border', 'none');
        }

        query.build = function() {
            this.setTimeDimension();
            this.setDistricts();
            this.setPoliceStations();
            this.setCrashSeverities();
            this.setCollisionTypes();
            this.setCrashCauses();
            this.setVehicleFailureTypes();
            this.setWeathers();
            this.setSurfaceConditions();
            this.setRoadSurfaces();
            this.setSurfaceType();
            this.setRoadwayCharacters();
            this.setJunctionTypes();
            this.setVehicleType();
            this.setLicenseTypes();
            this.setDriverGenders();
            this.setDriverAgeRanges();
            this.setDriverBeltUseds();
            this.setDriverCasualtyTypes();
            this.setCasualtyClasses();
            this.setCasualtyTypes();
            this.setCasualtyGenders();
            this.setCasualtyAgeRanges();
            this.setCasualtyBeltUseds();
            return this;
        }

        return queryJson ? query : query.build();
    }
})();