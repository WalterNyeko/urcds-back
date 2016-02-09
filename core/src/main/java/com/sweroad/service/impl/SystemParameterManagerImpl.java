package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.dao.hibernate.GenericDaoHibernate;
import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
import com.sweroad.service.SystemParameterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.Surface;

import java.util.List;

/**
 * Created by Frank on 2/3/16.
 */
@Service("systemParameterManager")
public class SystemParameterManagerImpl extends GenericManagerImpl<SystemParameter, Long> implements SystemParameterManager {

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
    public SystemParameterManagerImpl(GenericDao<SystemParameter, Long> systemParameterDao) {
        super(systemParameterDao);
    }

    @Override
    public List<? extends NameIdModel> getParameters(String type) throws Exception{
        switch(type) {
            case "weather":
                return weatherManager.getAllDistinct();
            case "district":
                return districtManager.getAllDistinct();
            case "hospital":
                return hospitalManager.getAllDistinct();
            case "injuryType":
                return injuryTypeManager.getAllDistinct();
            case "crashCause":
                return crashCauseManager.getAllDistinct();
            case "vehicleType":
                return vehicleTypeManager.getAllDistinct();
            case "roadSurface":
                return roadSurfaceManager.getAllDistinct();
            case "roadUserType":
                return roadUserTypeManager.getAllDistinct();
            case "junctionType":
                return junctionTypeManager.getAllDistinct();
            case "casualtyType":
                return casualtyTypeManager.getAllDistinct();
            case "casualtyClass":
                return casualtyClassManager.getAllDistinct();
            case "collisionType":
                return collisionTypeManager.getAllDistinct();
            case "crashSeverity":
                return crashSeverityManager.getAllDistinct();
            case "patientStatus":
                return patientStatusManager.getAllDistinct();
            case "policeStation":
                return policeStationManager.getAllDistinct();
            case "transportMode":
                return transportModeManager.getAllDistinct();
            case "roadwayCharacter":
                return roadwayCharacterManager.getAllDistinct();
            case "surfaceCondition":
                return surfaceConditionManager.getAllDistinct();
            case "patientDisposition":
                return patientDispositionManager.getAllDistinct();
            case "vehicleFailureType":
                return vehicleFailureTypeManager.getAllDistinct();
            default:
                throw new Exception("Unknown parameter type: [" + type + "]");
        }
    }

    @Override
    public void updateParameterName(Long id, String name, String code) throws Exception {
        switch(code) {
            case "weather":
                this.updateParameter(id, name, weatherManager);
                break;
            case "district":
                this.updateParameter(id, name, districtManager);
                break;
            case "hospital":
                this.updateParameter(id, name, hospitalManager);
                break;
            case "injuryType":
                this.updateParameter(id, name, injuryTypeManager);
                break;
            case "crashCause":
                this.updateParameter(id, name, crashCauseManager);
                break;
            case "vehicleType":
                this.updateParameter(id, name, vehicleTypeManager);
                break;
            case "roadSurface":
                this.updateParameter(id, name, roadSurfaceManager);
                break;
            case "roadUserType":
                this.updateParameter(id, name, roadUserTypeManager);
                break;
            case "junctionType":
                this.updateParameter(id, name, junctionTypeManager);
                break;
            case "casualtyType":
                this.updateParameter(id, name, casualtyTypeManager);
                break;
            case "casualtyClass":
                this.updateParameter(id, name, casualtyClassManager);
                break;
            case "collisionType":
                this.updateParameter(id, name, collisionTypeManager);
                break;
            case "crashSeverity":
                this.updateParameter(id, name, crashSeverityManager);
                break;
            case "patientStatus":
                this.updateParameter(id, name, patientStatusManager);
                break;
            case "policeStation":
                this.updateParameter(id, name, policeStationManager);
                break;
            case "transportMode":
                this.updateParameter(id, name, transportModeManager);
                break;
            case "roadwayCharacter":
                this.updateParameter(id, name, roadwayCharacterManager);
                break;
            case "surfaceCondition":
                this.updateParameter(id, name, surfaceConditionManager);
                break;
            case "patientDisposition":
                this.updateParameter(id, name, patientDispositionManager);
                break;
            case "vehicleFailureType":
                this.updateParameter(id, name, vehicleFailureTypeManager);
                break;
            default:
                throw new Exception("Unknown parameter type: [" + code + "]");
        }
    }

    private void updateParameter(Long id, String name, GenericManager manager) {
        NameIdModel param = (NameIdModel)manager.get(id);
        param.setName(name);
        manager.save(param);
    }

    @Override
    public void setParameterActive(Long id, boolean active, String code) throws Exception {
        switch(code) {
            case "weather":
                this.setParameterActive(id, active, weatherManager);
                break;
            case "district":
                this.setParameterActive(id, active, districtManager);
                break;
            case "hospital":
                this.setParameterActive(id, active, hospitalManager);
                break;
            case "injuryType":
                this.setParameterActive(id, active, injuryTypeManager);
                break;
            case "crashCause":
                this.setParameterActive(id, active, crashCauseManager);
                break;
            case "vehicleType":
                this.setParameterActive(id, active, vehicleTypeManager);
                break;
            case "roadSurface":
                this.setParameterActive(id, active, roadSurfaceManager);
                break;
            case "roadUserType":
                this.setParameterActive(id, active, roadUserTypeManager);
                break;
            case "junctionType":
                this.setParameterActive(id, active, junctionTypeManager);
                break;
            case "casualtyType":
                this.setParameterActive(id, active, casualtyTypeManager);
                break;
            case "casualtyClass":
                this.setParameterActive(id, active, casualtyClassManager);
                break;
            case "collisionType":
                this.setParameterActive(id, active, collisionTypeManager);
                break;
            case "crashSeverity":
                this.setParameterActive(id, active, crashSeverityManager);
                break;
            case "patientStatus":
                this.setParameterActive(id, active, patientStatusManager);
                break;
            case "policeStation":
                this.setParameterActive(id, active, policeStationManager);
                break;
            case "transportMode":
                this.setParameterActive(id, active, transportModeManager);
                break;
            case "roadwayCharacter":
                this.setParameterActive(id, active, roadwayCharacterManager);
                break;
            case "surfaceCondition":
                this.setParameterActive(id, active, surfaceConditionManager);
                break;
            case "patientDisposition":
                this.setParameterActive(id, active, patientDispositionManager);
                break;
            case "vehicleFailureType":
                this.setParameterActive(id, active, vehicleFailureTypeManager);
                break;
            default:
                throw new Exception("Unknown parameter type: [" + code + "]");
        }
    }

    private void setParameterActive(Long id, boolean active, GenericManager manager) {
        NameIdModel param = (NameIdModel)manager.get(id);
        param.setActive(active);
        manager.save(param);
    }
}
