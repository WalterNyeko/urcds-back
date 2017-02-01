package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.*;
import com.sweroad.service.GenericService;
import com.sweroad.service.SystemParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Frank on 2/3/16.
 */
@Service("systemParameterService")
public class SystemParameterServiceImpl extends GenericServiceImpl<SystemParameter, Long> implements SystemParameterService {

    @Autowired
    private GenericService<Weather, Long> weatherService;
    @Autowired
    private GenericService<District, Long> districtService;
    @Autowired
    private GenericService<Hospital, Long> hospitalService;
    @Autowired
    private GenericService<InjuryType, Long> injuryTypeService;
    @Autowired
    private GenericService<CrashCause, Long> crashCauseService;
    @Autowired
    private GenericService<RoadSurface, Long> roadSurfaceService;
    @Autowired
    private GenericService<VehicleType, Long> vehicleTypeService;
    @Autowired
    private GenericService<SurfaceType, Long> surfaceTypeService;
    @Autowired
    private GenericService<CasualtyType, Long> casualtyTypeService;
    @Autowired
    private GenericService<JunctionType, Long> junctionTypeService;
    @Autowired
    private GenericService<RoadUserType, Long> roadUserTypeService;
    @Autowired
    private GenericService<CasualtyClass, Long> casualtyClassService;
    @Autowired
    private GenericService<CollisionType, Long> collisionTypeService;
    @Autowired
    private GenericService<CrashSeverity, Long> crashSeverityService;
    @Autowired
    private GenericService<PatientStatus, Long> patientStatusService;
    @Autowired
    private GenericService<PoliceStation, Long> policeStationService;
    @Autowired
    private GenericService<TransportMode, Long> transportModeService;
    @Autowired
    private GenericService<RoadwayCharacter, Long> roadwayCharacterService;
    @Autowired
    private GenericService<SurfaceCondition, Long> surfaceConditionService;
    @Autowired
    private GenericService<VehicleFailureType, Long> vehicleFailureTypeService;
    @Autowired
    private GenericService<PatientDisposition, Long> patientDispositionService;

    @Autowired
    public SystemParameterServiceImpl(GenericDao<SystemParameter, Long> systemParameterDao) {
        super(systemParameterDao);
    }

    @Override
    public List<? extends NameIdModel> getParameters(AttributeType type) throws Exception{
        switch(type) {
            case WEATHER:
                return weatherService.getAllDistinct();
            case DISTRICT:
                return districtService.getAllDistinct();
            case HOSPITAL:
                return hospitalService.getAllDistinct();
            case INJURY_TYPE:
                return injuryTypeService.getAllDistinct();
            case CRASH_CAUSE:
                return crashCauseService.getAllDistinct();
            case VEHICLE_TYPE:
                return vehicleTypeService.getAllDistinct();
            case SURFACE_TYPE:
                return surfaceTypeService.getAllDistinct();
            case ROAD_SURFACE:
                return roadSurfaceService.getAllDistinct();
            case ROAD_USER_TYPE:
                return roadUserTypeService.getAllDistinct();
            case JUNCTION_TYPE:
                return junctionTypeService.getAllDistinct();
            case CASUALTY_TYPE:
                return casualtyTypeService.getAllDistinct();
            case CASUALTY_CLASS:
                return casualtyClassService.getAllDistinct();
            case COLLISION_TYPE:
                return collisionTypeService.getAllDistinct();
            case CRASH_SEVERITY:
                return crashSeverityService.getAllDistinct();
            case PATIENT_STATUS:
                return patientStatusService.getAllDistinct();
            case POLICE_STATION:
                return policeStationService.getAllDistinct();
            case TRANSPORT_MODE:
                return transportModeService.getAllDistinct();
            case ROADWAY_CHARACTER:
                return roadwayCharacterService.getAllDistinct();
            case SURFACE_CONDITION:
                return surfaceConditionService.getAllDistinct();
            case PATIENT_DISPOSITION:
                return patientDispositionService.getAllDistinct();
            case VEHICLE_FAILURE_TYPE:
                return vehicleFailureTypeService.getAllDistinct();
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    @Override
    public NameIdModel addParameter(String name, AttributeType type) throws Exception {
        switch(type) {
            case WEATHER:
                return weatherService.save(new Weather(name));
            case DISTRICT:
                return districtService.save(new District(name));
            case HOSPITAL:
                return hospitalService.save(new Hospital(name));
            case INJURY_TYPE:
                return injuryTypeService.save(new InjuryType(name));
            case CRASH_CAUSE:
                return crashCauseService.save(new CrashCause(name));
            case VEHICLE_TYPE:
                return vehicleTypeService.save(new VehicleType(name));
            case SURFACE_TYPE:
                return surfaceTypeService.save(new SurfaceType(name));
            case ROAD_SURFACE:
                return roadSurfaceService.save(new RoadSurface(name));
            case ROAD_USER_TYPE:
                return roadUserTypeService.save(new RoadUserType(name));
            case JUNCTION_TYPE:
                return junctionTypeService.save(new JunctionType(name));
            case CASUALTY_TYPE:
                return casualtyTypeService.save(new CasualtyType(name));
            case CASUALTY_CLASS:
                return casualtyClassService.save(new CasualtyClass(name));
            case COLLISION_TYPE:
                return collisionTypeService.save(new CollisionType(name));
            case CRASH_SEVERITY:
                return crashSeverityService.save(new CrashSeverity(name));
            case PATIENT_STATUS:
                return patientStatusService.save(new PatientStatus(name));
            case POLICE_STATION:
                return policeStationService.save(new PoliceStation(name));
            case TRANSPORT_MODE:
                return transportModeService.save(new TransportMode(name));
            case ROADWAY_CHARACTER:
                return roadwayCharacterService.save(new RoadwayCharacter(name));
            case SURFACE_CONDITION:
                return surfaceConditionService.save(new SurfaceCondition(name));
            case PATIENT_DISPOSITION:
                return patientDispositionService.save(new PatientDisposition(name));
            case VEHICLE_FAILURE_TYPE:
                return vehicleFailureTypeService.save(new VehicleFailureType(name));
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    @Override
    public void updateParameterName(Long id, String name, AttributeType type) throws Exception {
        switch(type) {
            case WEATHER:
                this.updateParameter(id, name, weatherService);
                break;
            case DISTRICT:
                this.updateParameter(id, name, districtService);
                break;
            case HOSPITAL:
                this.updateParameter(id, name, hospitalService);
                break;
            case INJURY_TYPE:
                this.updateParameter(id, name, injuryTypeService);
                break;
            case CRASH_CAUSE:
                this.updateParameter(id, name, crashCauseService);
                break;
            case VEHICLE_TYPE:
                this.updateParameter(id, name, vehicleTypeService);
                break;
            case SURFACE_TYPE:
                this.updateParameter(id, name, surfaceTypeService);
                break;
            case ROAD_SURFACE:
                this.updateParameter(id, name, roadSurfaceService);
                break;
            case ROAD_USER_TYPE:
                this.updateParameter(id, name, roadUserTypeService);
                break;
            case JUNCTION_TYPE:
                this.updateParameter(id, name, junctionTypeService);
                break;
            case CASUALTY_TYPE:
                this.updateParameter(id, name, casualtyTypeService);
                break;
            case CASUALTY_CLASS:
                this.updateParameter(id, name, casualtyClassService);
                break;
            case COLLISION_TYPE:
                this.updateParameter(id, name, collisionTypeService);
                break;
            case CRASH_SEVERITY:
                this.updateParameter(id, name, crashSeverityService);
                break;
            case PATIENT_STATUS:
                this.updateParameter(id, name, patientStatusService);
                break;
            case POLICE_STATION:
                this.updateParameter(id, name, policeStationService);
                break;
            case TRANSPORT_MODE:
                this.updateParameter(id, name, transportModeService);
                break;
            case ROADWAY_CHARACTER:
                this.updateParameter(id, name, roadwayCharacterService);
                break;
            case SURFACE_CONDITION:
                this.updateParameter(id, name, surfaceConditionService);
                break;
            case PATIENT_DISPOSITION:
                this.updateParameter(id, name, patientDispositionService);
                break;
            case VEHICLE_FAILURE_TYPE:
                this.updateParameter(id, name, vehicleFailureTypeService);
                break;
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    private void updateParameter(Long id, String name, GenericService manager) {
        NameIdModel param = (NameIdModel)manager.get(id);
        param.setName(name);
        manager.save(param);
    }

    @Override
    public void setParameterActive(Long id, boolean active, AttributeType type) throws Exception {
        switch(type) {
            case WEATHER:
                this.setParameterActive(id, active, weatherService);
                break;
            case DISTRICT:
                this.setParameterActive(id, active, districtService);
                break;
            case HOSPITAL:
                this.setParameterActive(id, active, hospitalService);
                break;
            case INJURY_TYPE:
                this.setParameterActive(id, active, injuryTypeService);
                break;
            case CRASH_CAUSE:
                this.setParameterActive(id, active, crashCauseService);
                break;
            case VEHICLE_TYPE:
                this.setParameterActive(id, active, vehicleTypeService);
                break;
            case SURFACE_TYPE:
                this.setParameterActive(id, active, surfaceTypeService);
                break;
            case ROAD_SURFACE:
                this.setParameterActive(id, active, roadSurfaceService);
                break;
            case ROAD_USER_TYPE:
                this.setParameterActive(id, active, roadUserTypeService);
                break;
            case JUNCTION_TYPE:
                this.setParameterActive(id, active, junctionTypeService);
                break;
            case CASUALTY_TYPE:
                this.setParameterActive(id, active, casualtyTypeService);
                break;
            case CASUALTY_CLASS:
                this.setParameterActive(id, active, casualtyClassService);
                break;
            case COLLISION_TYPE:
                this.setParameterActive(id, active, collisionTypeService);
                break;
            case CRASH_SEVERITY:
                this.setParameterActive(id, active, crashSeverityService);
                break;
            case PATIENT_STATUS:
                this.setParameterActive(id, active, patientStatusService);
                break;
            case POLICE_STATION:
                this.setParameterActive(id, active, policeStationService);
                break;
            case TRANSPORT_MODE:
                this.setParameterActive(id, active, transportModeService);
                break;
            case ROADWAY_CHARACTER:
                this.setParameterActive(id, active, roadwayCharacterService);
                break;
            case SURFACE_CONDITION:
                this.setParameterActive(id, active, surfaceConditionService);
                break;
            case PATIENT_DISPOSITION:
                this.setParameterActive(id, active, patientDispositionService);
                break;
            case VEHICLE_FAILURE_TYPE:
                this.setParameterActive(id, active, vehicleFailureTypeService);
                break;
            default:
                throw new Exception("Unknown parameter type: [" + type.getTypeCode() + "]");
        }
    }

    private void setParameterActive(Long id, boolean active, GenericService manager) {
        NameIdModel param = (NameIdModel)manager.get(id);
        param.setActive(active);
        manager.save(param);
    }
}
