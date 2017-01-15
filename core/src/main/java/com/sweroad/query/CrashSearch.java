package com.sweroad.query;

import com.sweroad.model.*;
import com.sweroad.query.service.AgeQueryableService;
import com.sweroad.query.service.QuadstateQueryableService;
import com.sweroad.query.service.CustomQueryableService;
import com.sweroad.query.service.GenderQueryableService;
import com.sweroad.service.DateRangeService;
import com.sweroad.service.LookupManager;
import com.sweroad.service.impl.DateRangeServiceImpl;
import com.sweroad.service.impl.LookupManagerImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Frank on 12/19/14.
 */
public class CrashSearch implements DateRangable {

    private DateRangeService dateRangeService;
    private LookupManager lookupManager;
    private List<CustomQueryableService> customQueryableServices;
    private Date startDate;
    private Date endDate;
    private Integer startYear;
    private Integer startMonth;
    private Integer endYear;
    private Integer endMonth;
    private Integer startHour;
    private Integer endHour;
    private String startDateString;
    private String endDateString;
    private List<Weather> weathers = new ArrayList<>();
    private List<District> districts = new ArrayList<>();
    private List<CrashCause> crashCauses = new ArrayList<>();
    private List<RoadSurface> roadSurfaces = new ArrayList<>();
    private List<SurfaceType> surfaceTypes = new ArrayList<>();
    private List<VehicleType> vehicleTypes = new ArrayList<>();
    private List<LabelValue> driverGenders = new ArrayList<>();
    private List<LabelValue> driverAgeRanges = new ArrayList<>();
    private List<JunctionType> junctionTypes = new ArrayList<>();
    private List<CasualtyType> casualtyTypes = new ArrayList<>();
    private List<LabelValue> casualtyGenders = new ArrayList<>();
    private List<Quadstate> driverLicenseTypes = new ArrayList<>();
    private List<CollisionType> collisionTypes = new ArrayList<>();
    private List<LabelValue> casualtyAgeRanges = new ArrayList<>();
    private List<PoliceStation> policeStations = new ArrayList<>();
    private List<CrashSeverity> crashSeverities = new ArrayList<>();
    private List<CasualtyClass> casualtyClasses = new ArrayList<>();
    private List<Quadstate> driverBeltUsedOptions = new ArrayList<>();
    private List<CasualtyType> driverCasualtyTypes = new ArrayList<>();
    private List<Quadstate> casualtyBeltUsedOptions = new ArrayList<>();
    private List<RoadwayCharacter> roadwayCharacters = new ArrayList<>();
    private List<SurfaceCondition> surfaceConditions = new ArrayList<>();
    private List<VehicleFailureType> vehicleFailureTypes = new ArrayList<>();
    private List<QuadstateWrapper> driverLicenseTypeValues = new ArrayList();
    private List<QuadstateWrapper> driverBeltUsedOptionValues = new ArrayList();
    private List<QuadstateWrapper> casualtyBeltUsedOptionValues = new ArrayList();

    public CrashSearch() {
        this.dateRangeService = new DateRangeServiceImpl();
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

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    @Override
    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    @Override
    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
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

    public List<Quadstate> getDriverLicenseTypes() {
        return this.driverLicenseTypes;
    }

    public void setDriverLicenseTypes(List<Quadstate> driverLicenseTypes) {
        this.driverLicenseTypes = driverLicenseTypes;
    }

    public List<QuadstateWrapper> getDriverLicenseTypeValues() { return this.driverLicenseTypeValues; }

    public void setDriverLicenseTypeValues(List<QuadstateWrapper> driverLicenseTypeValues) {
        this.driverLicenseTypeValues = driverLicenseTypeValues;
        this.driverLicenseTypes.clear();
        this.driverLicenseTypeValues.forEach(value -> this.driverLicenseTypes.add(value.getQuadstate()));
    }

    public List<LabelValue> getDriverGenders() {
        return driverGenders;
    }

    public void setDriverGenders(List<LabelValue> driverGenders) {
        this.driverGenders = driverGenders;
    }

    public List<Quadstate> getDriverBeltUsedOptions() {
        return driverBeltUsedOptions;
    }

    public void setDriverBeltUsedOptions(List<Quadstate> driverBeltUsedOptions) {
        this.driverBeltUsedOptions = driverBeltUsedOptions;
    }

    public List<QuadstateWrapper> getDriverBeltUsedOptionValues() {
        return this.driverBeltUsedOptionValues;
    }

    public void setDriverBeltUsedOptionValues(List<QuadstateWrapper> driverBeltUsedOptionValues) {
        this.driverBeltUsedOptionValues = driverBeltUsedOptionValues;
        this.driverBeltUsedOptions.clear();
        this.driverBeltUsedOptionValues.forEach(value -> this.driverBeltUsedOptions.add(value.getQuadstate()));
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

    public List<Quadstate> getCasualtyBeltUsedOptions() {
        return casualtyBeltUsedOptions;
    }

    public void setCasualtyBeltUsedOptions(List<Quadstate> casualtyBeltUsedOptions) {
        this.casualtyBeltUsedOptions = casualtyBeltUsedOptions;
    }

    public List<QuadstateWrapper> getCasualtyBeltUsedOptionValues() {
        return this.casualtyBeltUsedOptionValues;
    }

    public void setCasualtyBeltUsedOptionValues(List<QuadstateWrapper> casualtyBeltUsedOptionValues) {
        this.casualtyBeltUsedOptionValues = casualtyBeltUsedOptionValues;
        this.casualtyBeltUsedOptions.clear();
        this.casualtyBeltUsedOptionValues.forEach(value -> this.casualtyBeltUsedOptions.add(value.getQuadstate()));
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
            addDateRange(crashQueryBuilder);
            addTimeRange(crashQueryBuilder);
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
                    .addQueryable(casualtyTypes)
                    .addQueryable(policeStations)
                    .addQueryable(districts);
        }

        private void addDateRange(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
            if (dateRangeService.bothMonthProvidedButNoYears(CrashSearch.this)) {
                crashQueryBuilder.addStartMonth(CrashSearch.this.getStartMonth())
                        .addEndMonth(CrashSearch.this.getEndMonth());
            } else if (dateRangeService.onlyStartMonthProvided(CrashSearch.this)) {
                crashQueryBuilder.addStartMonth(CrashSearch.this.getStartMonth());
            } else if (dateRangeService.onlyEndMonthProvided(CrashSearch.this)) {
                crashQueryBuilder.addEndMonth(CrashSearch.this.getEndMonth());
            } else if (dateRangeService.atLeastOneYearMonthProvided(CrashSearch.this)) {
                dateRangeService.setDatesBasedOnYearMonthCriteria(CrashSearch.this);
                crashQueryBuilder.addStartDate(CrashSearch.this.getStartDate())
                        .addEndDate(CrashSearch.this.getEndDate());
            } else {
                crashQueryBuilder.addStartDate(CrashSearch.this.getStartDate());
                crashQueryBuilder.addEndDate(CrashSearch.this.getEndDate());
            }
        }

        private void addTimeRange(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
            if (CrashSearch.this.getStartHour() != null) {
                crashQueryBuilder.addStartHour(CrashSearch.this.getStartHour());
            }
            if (CrashSearch.this.getEndHour() != null) {
                crashQueryBuilder.addEndHour(CrashSearch.this.getEndHour());
            }
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
            customQueryableServices = new ArrayList<>();
            customQueryableServices.add(new QuadstateQueryableService(driverLicenseTypes,
                    CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE, "licenseValid", "driver"));
            customQueryableServices.add(new GenderQueryableService(driverGenders, lookupManager,
                    CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE));
            customQueryableServices.add(new GenderQueryableService(casualtyGenders, lookupManager,
                    CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY));
            customQueryableServices.add(new QuadstateQueryableService(driverBeltUsedOptions,
                    CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE, "beltUsed", "driver"));
            customQueryableServices.add(new QuadstateQueryableService(casualtyBeltUsedOptions,
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