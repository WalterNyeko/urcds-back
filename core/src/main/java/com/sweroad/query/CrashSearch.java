package com.sweroad.query;

import com.sweroad.model.*;
import com.sweroad.query.service.AgeQueryableService;
import com.sweroad.query.service.BooleanTypeQueryableService;
import com.sweroad.query.service.CustomQueryableService;
import com.sweroad.query.service.GenderQueryableService;
import com.sweroad.service.LookupManager;
import com.sweroad.service.impl.LookupManagerImpl;
import org.apache.avro.generic.GenericData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Frank on 12/19/14.
 */
public class CrashSearch {

    private LookupManager lookupManager;
    private List<CustomQueryableService> customQueryableServices;
    private Date startDate;
    private Date endDate;
    private List<CrashSeverity> crashSeverities = new ArrayList<CrashSeverity>();
    private List<CollisionType> collisionTypes = new ArrayList<CollisionType>();
    private List<CrashCause> crashCauses = new ArrayList<CrashCause>();
    private List<VehicleFailureType> vehicleFailureTypes = new ArrayList<VehicleFailureType>();
    private List<Weather> weathers = new ArrayList<Weather>();
    private List<SurfaceCondition> surfaceConditions = new ArrayList<SurfaceCondition>();
    private List<RoadSurface> roadSurfaces = new ArrayList<RoadSurface>();
    private List<SurfaceType> surfaceTypes = new ArrayList<SurfaceType>();
    private List<RoadwayCharacter> roadwayCharacters = new ArrayList<RoadwayCharacter>();
    private List<JunctionType> junctionTypes = new ArrayList<JunctionType>();
    private List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
    private List<LabelValue> driverLicenseTypes = new ArrayList<LabelValue>();
    private List<LabelValue> driverGenders = new ArrayList<LabelValue>();
    private List<LabelValue> driverBeltUsedOptions = new ArrayList<LabelValue>();
    private List<LabelValue> driverAgeRanges = new ArrayList<LabelValue>();
    private List<CasualtyType> driverCasualtyTypes = new ArrayList<CasualtyType>();
    private List<CasualtyType> casualtyTypes = new ArrayList<CasualtyType>();
    private List<CasualtyClass> casualtyClasses = new ArrayList<CasualtyClass>();
    private List<LabelValue> casualtyGenders = new ArrayList<LabelValue>();
    private List<LabelValue> casualtyAgeRanges = new ArrayList<LabelValue>();
    private List<LabelValue> casualtyBeltUsedOptions = new ArrayList<LabelValue>();
    private List<District> districts = new ArrayList<District>();
    private List<PoliceStation> policeStations = new ArrayList<PoliceStation>();

    public CrashSearch() {
        this.lookupManager = new LookupManagerImpl();
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<CrashSeverity> getCrashSeverities() {
        return crashSeverities;
    }

    public void setCrashSeverities(List<CrashSeverity> crashSeverities) {
        this.crashSeverities = crashSeverities;
    }

    public List<CollisionType> getCollisionTypes() {
        return collisionTypes;
    }

    public void setCollisionTypes(List<CollisionType> collisionTypes) {
        this.collisionTypes = collisionTypes;
    }

    public List<CrashCause> getCrashCauses() {
        return crashCauses;
    }

    public void setCrashCauses(List<CrashCause> crashCauses) {
        this.crashCauses = crashCauses;
    }

    public List<VehicleFailureType> getVehicleFailureTypes() {
        return vehicleFailureTypes;
    }

    public void setVehicleFailureTypes(List<VehicleFailureType> vehicleFailureTypes) {
        this.vehicleFailureTypes = vehicleFailureTypes;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public List<SurfaceCondition> getSurfaceConditions() {
        return surfaceConditions;
    }

    public void setSurfaceConditions(List<SurfaceCondition> surfaceConditions) {
        this.surfaceConditions = surfaceConditions;
    }

    public List<RoadSurface> getRoadSurfaces() {
        return roadSurfaces;
    }

    public void setRoadSurfaces(List<RoadSurface> roadSurfaces) {
        this.roadSurfaces = roadSurfaces;
    }

    public List<SurfaceType> getSurfaceTypes() {
        return surfaceTypes;
    }

    public void setSurfaceTypes(List<SurfaceType> surfaceTypes) {
        this.surfaceTypes = surfaceTypes;
    }

    public List<RoadwayCharacter> getRoadwayCharacters() {
        return roadwayCharacters;
    }

    public void setRoadwayCharacters(List<RoadwayCharacter> roadwayCharacters) {
        this.roadwayCharacters = roadwayCharacters;
    }

    public List<JunctionType> getJunctionTypes() {
        return junctionTypes;
    }

    public void setJunctionTypes(List<JunctionType> junctionTypes) {
        this.junctionTypes = junctionTypes;
    }

    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public List<CasualtyType> getCasualtyTypes() {
        return casualtyTypes;
    }

    public void setCasualtyTypes(List<CasualtyType> casualtyTypes) {
        this.casualtyTypes = casualtyTypes;
    }

    public List<CasualtyClass> getCasualtyClasses() {
        return casualtyClasses;
    }

    public void setCasualtyClasses(List<CasualtyClass> casualtyClasses) {
        this.casualtyClasses = casualtyClasses;
    }

    public List<LabelValue> getDriverLicenseTypes() {
        return driverLicenseTypes;
    }

    public void setDriverLicenseTypes(List<LabelValue> driverLicenseTypes) {
        this.driverLicenseTypes = driverLicenseTypes;
    }

    public List<LabelValue> getDriverGenders() {
        return driverGenders;
    }

    public void setDriverGenders(List<LabelValue> driverGenders) {
        this.driverGenders = driverGenders;
    }

    public List<LabelValue> getDriverBeltUsedOptions() {
        return driverBeltUsedOptions;
    }

    public void setDriverBeltUsedOptions(List<LabelValue> driverBeltUsedOptions) {
        this.driverBeltUsedOptions = driverBeltUsedOptions;
    }

    public List<LabelValue> getDriverAgeRanges() {
        return driverAgeRanges;
    }

    public void setDriverAgeRanges(List<LabelValue> driverAgeRanges) {
        this.driverAgeRanges = driverAgeRanges;
    }

    public List<CasualtyType> getDriverCasualtyTypes() {
        return driverCasualtyTypes;
    }

    public void setDriverCasualtyTypes(List<CasualtyType> driverCasualtyTypes) {
        this.driverCasualtyTypes = driverCasualtyTypes;
    }

    public List<LabelValue> getCasualtyGenders() {
        return casualtyGenders;
    }

    public void setCasualtyGenders(List<LabelValue> casualtyGenders) {
        this.casualtyGenders = casualtyGenders;
    }

    public List<LabelValue> getCasualtyAgeRanges() {
        return casualtyAgeRanges;
    }

    public void setCasualtyAgeRanges(List<LabelValue> casualtyAgeRanges) {
        this.casualtyAgeRanges = casualtyAgeRanges;
    }

    public List<LabelValue> getCasualtyBeltUsedOptions() {
        return casualtyBeltUsedOptions;
    }

    public void setCasualtyBeltUsedOptions(List<LabelValue> casualtyBeltUsedOptions) {
        this.casualtyBeltUsedOptions = casualtyBeltUsedOptions;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<PoliceStation> getPoliceStations() {
        return policeStations;
    }

    public void setPoliceStations(List<PoliceStation> policeStations) {
        this.policeStations = policeStations;
    }

    public CrashQuery toQuery() {
        return new QueryGenerator().generateQuery();
    }

    private class QueryGenerator {
        private CrashQuery generateQuery() {
            CrashQuery.CrashQueryBuilder crashQueryBuilder = new CrashQuery.CrashQueryBuilder();
            addQueryables(crashQueryBuilder);
            addCustomQueryables(crashQueryBuilder);
            addLiteralQueryables(crashQueryBuilder);
            addJoins(crashQueryBuilder);
            return crashQueryBuilder.build();
        }

        private void addQueryables(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
            crashQueryBuilder.addQueryable(crashSeverities)
                    .addQueryable(collisionTypes)
                    .addQueryable(crashCauses)
                    .addQueryable(vehicleFailureTypes)
                    .addQueryable(weathers)
                    .addQueryable(surfaceConditions)
                    .addQueryable(roadSurfaces)
                    .addQueryable(surfaceTypes)
                    .addQueryable(roadwayCharacters)
                    .addQueryable(junctionTypes)
                    .addQueryable(vehicleTypes)
                    .addQueryable(casualtyClasses)
                    .addQueryable(casualtyTypes);
        }

        private void addCustomQueryables(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
            addCustomQueryableForDriverCasualtyType(crashQueryBuilder);
            addCustomQueryableServices();
            for (CustomQueryableService customQueryableService : customQueryableServices) {
                customQueryableService.addToCrashQueryBuilder(crashQueryBuilder);
            }
        }

        private void addLiteralQueryables(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
            new AgeQueryableService(driverAgeRanges, CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)
                    .addToCrashQueryBuilder(crashQueryBuilder);
            new AgeQueryableService(casualtyAgeRanges, CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY)
                    .addToCrashQueryBuilder(crashQueryBuilder);
        }

        private void addJoins(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
            crashQueryBuilder
                    .joinVehicles(joinVehicles())
                    .joinCasualties(joinCasualties());
        }

        private boolean joinVehicles() {
            return (vehicleTypes != null && vehicleTypes.size() > 0) ||
                    (driverLicenseTypes != null && driverLicenseTypes.size() > 0) ||
                    (driverGenders != null && driverGenders.size() > 0) ||
                    (driverAgeRanges != null && driverAgeRanges.size() > 0) ||
                    (driverBeltUsedOptions != null && driverBeltUsedOptions.size() > 0) ||
                    (driverCasualtyTypes != null && driverCasualtyTypes.size() > 0);
        }

        private boolean joinCasualties() {
            return (casualtyTypes != null && casualtyTypes.size() > 0) ||
                    (casualtyClasses != null && casualtyClasses.size() > 0) ||
                    (casualtyGenders != null && casualtyGenders.size() > 0) ||
                    (casualtyAgeRanges != null && casualtyAgeRanges.size() > 0) ||
                    (casualtyBeltUsedOptions != null && casualtyBeltUsedOptions.size() > 0);
        }

        private void addCustomQueryableServices() {
            customQueryableServices = new ArrayList<CustomQueryableService>();
            customQueryableServices.add(new BooleanTypeQueryableService(driverLicenseTypes, lookupManager.getAllLicenseTypes(),
                    CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE, "licenseValid", "driver"));
            customQueryableServices.add(new GenderQueryableService(driverGenders, lookupManager,
                    CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE));
            customQueryableServices.add(new GenderQueryableService(casualtyGenders, lookupManager,
                    CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY));
            customQueryableServices.add(new BooleanTypeQueryableService(driverBeltUsedOptions, lookupManager.getAllBeltUsedOptions(),
                    CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE, "beltUsed", "driver"));
            customQueryableServices.add(new BooleanTypeQueryableService(casualtyBeltUsedOptions, lookupManager.getAllBeltUsedOptions(),
                    CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY, "beltOrHelmetUsed", ""));
        }

        private void addCustomQueryableForDriverCasualtyType(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
            if (driverCasualtyTypes != null && driverCasualtyTypes.size() > 0) {
                crashQueryBuilder.addCustomQueryable(new CustomQueryable.CustomQueryableBuilder()
                        .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)
                        .addProperty("driver.casualtyType")
                        .addComparison(Comparison.IN)
                        .addParameterName("driverCasualtyType")
                        .addParameterValue(driverCasualtyTypes)
                        .shouldEncloseInParenthesis(true)
                        .build());
            }
        }
    }
}