package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
import com.sweroad.service.SystemParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Frank on 2/3/16.
 */
@Service("systemParameterService")
public class SystemParameterServiceImpl extends GenericManagerImpl<SystemParameter, Long> implements SystemParameterService {

    @Autowired
    private GenericManager<Weather, Long> weatherManager;
    @Autowired
    private GenericManager<District, Long> districtManager;
    @Autowired
    private GenericManager<Hospital, Long> hospitalManager;
    @Autowired
    private GenericManager<InjuryType, Long> injuryTypeManager;
    @Autowired
    private GenericManager<CrashCause, Long> crashCauseManager;
    @Autowired
    private GenericManager<RoadSurface, Long> roadSurfaceManager;
    @Autowired
    private GenericManager<VehicleType, Long> vehicleTypeManager;
    @Autowired
    private GenericManager<CasualtyType, Long> casualtyTypeManager;
    @Autowired
    private GenericManager<JunctionType, Long> junctionTypeManager;
    @Autowired
    private GenericManager<RoadUserType, Long> roadUserTypeManager;
    @Autowired
    private GenericManager<CasualtyClass, Long> casualtyClassManager;
    @Autowired
    private GenericManager<CollisionType, Long> collisionTypeManager;
    @Autowired
    private GenericManager<CrashSeverity, Long> crashSeverityManager;
    @Autowired
    private GenericManager<PatientStatus, Long> patientStatusManager;
    @Autowired
    private GenericManager<PoliceStation, Long> policeStationManager;
    @Autowired
    private GenericManager<TransportMode, Long> transportModeManager;
    @Autowired
    private GenericManager<RoadwayCharacter, Long> roadwayCharacterManager;
    @Autowired
    private GenericManager<SurfaceCondition, Long> surfaceConditionManager;
    @Autowired
    private GenericManager<VehicleFailureType, Long> vehicleFailureTypeManager;
    @Autowired
    private GenericManager<PatientDisposition, Long> patientDispositionManager;

    @Autowired
    public SystemParameterServiceImpl(GenericDao<SystemParameter, Long> systemParameterDao) {
        super(systemParameterDao);
    }

    @Override
    public List<? extends NameIdModel> getParameters(AttributeType type) throws Exception{
        switch(type) {
            case WEATHER:
                return weatherManager.getAllDistinct();
            case DISTRICT:
                return districtManager.getAllDistinct();
            case HOSPITAL:
                return hospitalManager.getAllDistinct();
            case INJURY_TYPE:
                return injuryTypeManager.getAllDistinct();
            case CRASH_CAUSE:
                return crashCauseManager.getAllDistinct();
            case VEHICLE_TYPE:
                return vehicleTypeManager.getAllDistinct();
            case ROAD_SURFACE:
                return roadSurfaceManager.getAllDistinct();
            case ROAD_USER_TYPE:
                return roadUserTypeManager.getAllDistinct();
            case JUNCTION_TYPE:
                return junctionTypeManager.getAllDistinct();
            case CASUALTY_TYPE:
                return casualtyTypeManager.getAllDistinct();
            case CASUALTY_CLASS:
                return casualtyClassManager.getAllDistinct();
            case COLLISION_TYPE:
                return collisionTypeManager.getAllDistinct();
            case CRASH_SEVERITY:
                return crashSeverityManager.getAllDistinct();
            case PATIENT_STATUS:
                return patientStatusManager.getAllDistinct();
            case POLICE_STATION:
                return policeStationManager.getAllDistinct();
            case TRANSPORT_MODE:
                return transportModeManager.getAllDistinct();
            case ROADWAY_CHARACTER:
                return roadwayCharacterManager.getAllDistinct();
            case SURFACE_CONDITION:
                return surfaceConditionManager.getAllDistinct();
            case PATIENT_DISPOSITION:
                return patientDispositionManager.getAllDistinct();
            case VEHICLE_FAILURE_TYPE:
                return vehicleFailureTypeManager.getAllDistinct();
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    @Override
    public NameIdModel addParameter(String name, AttributeType type) throws Exception {
        switch(type) {
            case WEATHER:
                return weatherManager.save(new Weather(name));
            case DISTRICT:
                return districtManager.save(new District(name));
            case HOSPITAL:
                return hospitalManager.save(new Hospital(name));
            case INJURY_TYPE:
                return injuryTypeManager.save(new InjuryType(name));
            case CRASH_CAUSE:
                return crashCauseManager.save(new CrashCause(name));
            case VEHICLE_TYPE:
                return vehicleTypeManager.save(new VehicleType(name));
            case ROAD_SURFACE:
                return roadSurfaceManager.save(new RoadSurface(name));
            case ROAD_USER_TYPE:
                return roadUserTypeManager.save(new RoadUserType(name));
            case JUNCTION_TYPE:
                return junctionTypeManager.save(new JunctionType(name));
            case CASUALTY_TYPE:
                return casualtyTypeManager.save(new CasualtyType(name));
            case CASUALTY_CLASS:
                return casualtyClassManager.save(new CasualtyClass(name));
            case COLLISION_TYPE:
                return collisionTypeManager.save(new CollisionType(name));
            case CRASH_SEVERITY:
                return crashSeverityManager.save(new CrashSeverity(name));
            case PATIENT_STATUS:
                return patientStatusManager.save(new PatientStatus(name));
            case POLICE_STATION:
                return policeStationManager.save(new PoliceStation(name));
            case TRANSPORT_MODE:
                return transportModeManager.save(new TransportMode(name));
            case ROADWAY_CHARACTER:
                return roadwayCharacterManager.save(new RoadwayCharacter(name));
            case SURFACE_CONDITION:
                return surfaceConditionManager.save(new SurfaceCondition(name));
            case PATIENT_DISPOSITION:
                return patientDispositionManager.save(new PatientDisposition(name));
            case VEHICLE_FAILURE_TYPE:
                return vehicleFailureTypeManager.save(new VehicleFailureType(name));
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    @Override
    public void updateParameterName(Long id, String name, AttributeType type) throws Exception {
        switch(type) {
            case WEATHER:
                this.updateParameter(id, name, weatherManager);
                break;
            case DISTRICT:
                this.updateParameter(id, name, districtManager);
                break;
            case HOSPITAL:
                this.updateParameter(id, name, hospitalManager);
                break;
            case INJURY_TYPE:
                this.updateParameter(id, name, injuryTypeManager);
                break;
            case CRASH_CAUSE:
                this.updateParameter(id, name, crashCauseManager);
                break;
            case VEHICLE_TYPE:
                this.updateParameter(id, name, vehicleTypeManager);
                break;
            case ROAD_SURFACE:
                this.updateParameter(id, name, roadSurfaceManager);
                break;
            case ROAD_USER_TYPE:
                this.updateParameter(id, name, roadUserTypeManager);
                break;
            case JUNCTION_TYPE:
                this.updateParameter(id, name, junctionTypeManager);
                break;
            case CASUALTY_TYPE:
                this.updateParameter(id, name, casualtyTypeManager);
                break;
            case CASUALTY_CLASS:
                this.updateParameter(id, name, casualtyClassManager);
                break;
            case COLLISION_TYPE:
                this.updateParameter(id, name, collisionTypeManager);
                break;
            case CRASH_SEVERITY:
                this.updateParameter(id, name, crashSeverityManager);
                break;
            case PATIENT_STATUS:
                this.updateParameter(id, name, patientStatusManager);
                break;
            case POLICE_STATION:
                this.updateParameter(id, name, policeStationManager);
                break;
            case TRANSPORT_MODE:
                this.updateParameter(id, name, transportModeManager);
                break;
            case ROADWAY_CHARACTER:
                this.updateParameter(id, name, roadwayCharacterManager);
                break;
            case SURFACE_CONDITION:
                this.updateParameter(id, name, surfaceConditionManager);
                break;
            case PATIENT_DISPOSITION:
                this.updateParameter(id, name, patientDispositionManager);
                break;
            case VEHICLE_FAILURE_TYPE:
                this.updateParameter(id, name, vehicleFailureTypeManager);
                break;
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    private void updateParameter(Long id, String name, GenericManager manager) {
        NameIdModel param = (NameIdModel)manager.get(id);
        param.setName(name);
        manager.save(param);
    }

    @Override
    public void setParameterActive(Long id, boolean active, AttributeType type) throws Exception {
        switch(type) {
            case WEATHER:
                this.setParameterActive(id, active, weatherManager);
                break;
            case DISTRICT:
                this.setParameterActive(id, active, districtManager);
                break;
            case HOSPITAL:
                this.setParameterActive(id, active, hospitalManager);
                break;
            case INJURY_TYPE:
                this.setParameterActive(id, active, injuryTypeManager);
                break;
            case CRASH_CAUSE:
                this.setParameterActive(id, active, crashCauseManager);
                break;
            case VEHICLE_TYPE:
                this.setParameterActive(id, active, vehicleTypeManager);
                break;
            case ROAD_SURFACE:
                this.setParameterActive(id, active, roadSurfaceManager);
                break;
            case ROAD_USER_TYPE:
                this.setParameterActive(id, active, roadUserTypeManager);
                break;
            case JUNCTION_TYPE:
                this.setParameterActive(id, active, junctionTypeManager);
                break;
            case CASUALTY_TYPE:
                this.setParameterActive(id, active, casualtyTypeManager);
                break;
            case CASUALTY_CLASS:
                this.setParameterActive(id, active, casualtyClassManager);
                break;
            case COLLISION_TYPE:
                this.setParameterActive(id, active, collisionTypeManager);
                break;
            case CRASH_SEVERITY:
                this.setParameterActive(id, active, crashSeverityManager);
                break;
            case PATIENT_STATUS:
                this.setParameterActive(id, active, patientStatusManager);
                break;
            case POLICE_STATION:
                this.setParameterActive(id, active, policeStationManager);
                break;
            case TRANSPORT_MODE:
                this.setParameterActive(id, active, transportModeManager);
                break;
            case ROADWAY_CHARACTER:
                this.setParameterActive(id, active, roadwayCharacterManager);
                break;
            case SURFACE_CONDITION:
                this.setParameterActive(id, active, surfaceConditionManager);
                break;
            case PATIENT_DISPOSITION:
                this.setParameterActive(id, active, patientDispositionManager);
                break;
            case VEHICLE_FAILURE_TYPE:
                this.setParameterActive(id, active, vehicleFailureTypeManager);
                break;
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    private void setParameterActive(Long id, boolean active, GenericManager manager) {
        NameIdModel param = (NameIdModel)manager.get(id);
        param.setActive(active);
        manager.save(param);
    }
}
