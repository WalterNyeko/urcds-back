package com.sweroad.service.impl;

import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
import com.sweroad.service.LookupManager;
import com.sweroad.service.PatientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 10/23/15.
 */
@Service("patientManager")
public class PatientManagerImpl extends GenericManagerImpl<Patient, Long> implements PatientManager {

    @Autowired
    private LookupManager lookupManager;
    @Autowired
    private GenericManager<Hospital, Long> hospitalManager;
    @Autowired
    private GenericManager<District, Long> districtManager;
    @Autowired
    private GenericManager<InjuryType, Long> injuryTypeManager;
    @Autowired
    private GenericManager<RoadUserType, Long> roadUserTypeManager;
    @Autowired
    private GenericManager<TransportMode, Long> transportModeManager;
    @Autowired
    private GenericManager<PatientStatus, Long> patientStatusManager;
    @Autowired
    private GenericManager<PatientDisposition, Long> patientDispositionManager;

    @Override
    public Map<String, List> getReferenceData() {
        Map<String, List> referenceData = new HashMap<>();
        List<District> districts = districtManager.getAllDistinct();
        List<Hospital> hospitals = hospitalManager.getAllDistinct();
        Collections.sort(districts);
        Collections.sort(hospitals);
        referenceData.put("districts", districts);
        referenceData.put("hospitals", hospitals);
        referenceData.put("genders", lookupManager.getAllGenders());
        referenceData.put("injuryTypes", injuryTypeManager.getAllDistinct());
        referenceData.put("roadUserTypes", roadUserTypeManager.getAllDistinct());
        referenceData.put("transportModes", transportModeManager.getAllDistinct());
        referenceData.put("patientStatuses", patientStatusManager.getAllDistinct());
        referenceData.put("patientDispositions", patientDispositionManager.getAllDistinct());
        return referenceData;
    }
}
