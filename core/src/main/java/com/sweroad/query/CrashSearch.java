package com.sweroad.query;

import com.sweroad.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Frank on 12/19/14.
 */
public class CrashSearch {

    private Date startDate;
    private Date endDate;
    private List<CrashSeverity> crashSeverities =  new ArrayList<CrashSeverity>();
    private List<CollisionType> collisionTypes =  new ArrayList<CollisionType>();
    private List<CrashCause> crashCauses =  new ArrayList<CrashCause>();
    private List<VehicleFailureType> vehicleFailureTypes =  new ArrayList<VehicleFailureType>();
    private List<Weather> weathers =  new ArrayList<Weather>();
    private List<SurfaceCondition> surfaceConditions =  new ArrayList<SurfaceCondition>();
    private List<RoadSurface> roadSurfaces =  new ArrayList<RoadSurface>();
    private List<SurfaceType> surfaceTypes =  new ArrayList<SurfaceType>();
    private List<RoadwayCharacter> roadwayCharacters =  new ArrayList<RoadwayCharacter>();
    private List<JunctionType> junctionTypes =  new ArrayList<JunctionType>();
    private List<VehicleType> vehicleTypes =  new ArrayList<VehicleType>();
    private List<CasualtyType> casualtyTypes =  new ArrayList<CasualtyType>();
    private List<CasualtyClass> casualtyClasses =  new ArrayList<CasualtyClass>();

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

    public CrashQuery toQuery() {
        return this.generateQuery();
    }

    private CrashQuery generateQuery() {
        return new CrashQuery.CrashQueryBuilder()
                .addQueryable(crashSeverities)
                .addQueryable(collisionTypes)
                .addQueryable(crashCauses)
                .addQueryable(vehicleFailureTypes)
                .addQueryable(weathers)
                .addQueryable(surfaceConditions)
                .addQueryable(roadSurfaces)
                .addQueryable(surfaceTypes)
                .addQueryable(roadwayCharacters)
                .addQueryable(junctionTypes)
                .build();
    }
}