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

        query.build = function() {
            this.setId();
            this.setName();
            this.setDescription();
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

        query.setId = function() {
            this.id = $('#queryId').val();
        }

        query.setName = function() {
            this.name = $('#queryName').val();
        }

        query.setDescription = function() {
            this.description = $('#queryDescription').val();
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

        query.addCheckedToList = function(tableId, list) {
            $('#' + tableId).find('input:checked').each(function() {
                var id = $(this).val();
                var label = $('label[for=' + $(this).attr('id') + ']');
                if (label.length)
                    list.push({ id: id, name: label.text()});
            });
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
            var count = 0;
            var ctx = this;
            var row = $('<tr>');
            var summaryTable = $('<table>');
            var querySummary = $('#query-summary');
            ui.appendQueryHeader(this, summaryTable);
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
                var buttonCaption = this.dirty && this.id ? 'Update Query' : 'Save Query';
                querySummary.html('<h6>Query Summary</h6>').append(summaryTable);
                if (this.dirty || !this.id)
                    querySummary.append($('<div class="save-query"><input id="saveQuery" type="button" value="' + buttonCaption + '">'));
                $('#saveQuery').click(function() {
                    loadDialog({
                        width: 'auto',
                        height: 'auto',
                        message: "Loading form...",
                        dialogTitle: "Save Crash Query",
                        dialogButtons: ui.queryFormButtons()
                    });
                    var queryForm = ui.createQueryForm(ctx);
                    queryForm.find('table tr:last td').append(summaryTable.clone());
                    ui.dialogContent().html(queryForm);
                });
                querySummary.show();
            }
            else
                querySummary.hide();
        }

        query.loadForm = function() {
            this.loadTimeDimension();
            this.loadDistricts();
            this.loadPoliceStations();
            this.loadCrashSeverities();
            this.loadCollisionTypes();
            this.loadCrashCauses();
            this.loadVehicleFailureTypes();
            this.loadWeathers();
            this.loadSurfaceConditions();
            this.loadRoadSurfaces();
            this.loadSurfaceType();
            this.loadRoadwayCharacters();
            this.loadJunctionTypes();
            this.loadVehicleType();
            this.loadLicenseTypes();
            this.loadDriverGenders();
            this.loadDriverAgeRanges();
            this.loadDriverBeltUseds();
            this.loadDriverCasualtyTypes();
            this.loadCasualtyClasses();
            this.loadCasualtyTypes();
            this.loadCasualtyGenders();
            this.loadCasualtyAgeRanges();
            this.loadCasualtyBeltUseds();
            crashQueryFilterPoliceStations();
        }

        query.loadTimeDimension = function() {
            if (this.startYear)
                $('#startYear option:contains("' + this.startYear + '")').prop('selected', true);
            if (this.startMonth)
                $('#startMonth option:contains("' + this.startMonth + '")').prop('selected', true);
            if (this.startDate)
                $('#startDateString option:contains("' + this.startDate + '")').prop('selected', true);
            if (this.endYear)
                $('#endYear option:contains("' + this.endYear + '")').prop('selected', true);
            if (this.endMonth)
                $('#endMonth option:contains("' + this.endMonth + '")').prop('selected', true);
            if (this.endDate)
                $('#endDateString option:contains("' + this.endDate + '")').prop('selected', true);
        }

        query.checkListed = function(tableId, list) {
            list.map(function(item) {
                $('#' + tableId).find('input[type=checkbox][value=' + item.id + ']').prop('checked', true);
            });
        }

        query.loadWeathers = function() {
            this.checkListed('weather', this.weather.list);
        }

        query.loadDistricts = function() {
            this.checkListed('district', this.district.list);
        }

        query.loadCrashCauses = function() {
            this.checkListed('crashCause', this.crashCause.list);
        }

        query.loadRoadSurfaces = function() {
            this.checkListed('roadSurface', this.roadSurface.list);
        }

        query.loadSurfaceType = function() {
            this.checkListed('surfaceType', this.surfaceType.list);
        }

        query.loadVehicleType = function() {
            this.checkListed('vehicleType', this.vehicleType.list);
        }

        query.loadLicenseTypes = function() {
            this.checkListed('licenseType', this.licenseType.list);
        }

        query.loadJunctionTypes = function() {
            this.checkListed('junctionType', this.junctionType.list);
        }

        query.loadDriverGenders = function() {
            this.checkListed('driverGender', this.driverGender.list);
        }

        query.loadCasualtyTypes = function() {
            this.checkListed('casualtyType', this.casualtyType.list);
        }

        query.loadPoliceStations = function() {
            this.checkListed('policeStation', this.policeStation.list);
        }

        query.loadCollisionTypes = function() {
            this.checkListed('collisionType', this.collisionType.list);
        }

        query.loadCrashSeverities = function() {
            this.checkListed('crashSeverity', this.crashSeverity.list);
        }

        query.loadDriverBeltUseds = function() {
            this.checkListed('driverBeltUsed', this.driverBeltUsed.list);
        }

        query.loadCasualtyClasses = function() {
            this.checkListed('casualtyClass', this.casualtyClass.list);
        }

        query.loadCasualtyGenders = function() {
            this.checkListed('casualtyGender', this.casualtyGender.list);
        }

        query.loadDriverAgeRanges = function() {
            this.checkListed('driverAgeRange', this.driverAgeRange.list);
        }

        query.loadCasualtyBeltUseds = function() {
            this.checkListed('casualtyBeltUsed', this.casualtyBeltUsed.list);
        }

        query.loadCasualtyAgeRanges = function() {
            this.checkListed('casualtyAgeRange', this.casualtyAgeRange.list);
        }

        query.loadSurfaceConditions = function() {
            this.checkListed('surfaceCondition', this.surfaceCondition.list);
        }

        query.loadRoadwayCharacters = function() {
            this.checkListed('roadwayCharacter', this.roadwayCharacter.list);
        }

        query.loadVehicleFailureTypes = function() {
            this.checkListed('vehicleFailureType', this.vehicleFailureType.list);
        }

        query.loadDriverCasualtyTypes = function() {
            this.checkListed('driverCasualtyType', this.driverCasualtyType.list);
        }

        return queryJson ? query : query.build();
    }
})();