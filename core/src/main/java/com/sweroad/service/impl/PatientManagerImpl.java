package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
import com.sweroad.service.LookupManager;
import com.sweroad.service.PatientManager;
import com.sweroad.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Frank on 10/23/15.
 */
@Service("patientManager")
public class PatientManagerImpl extends GenericManagerImpl<Patient, Long> implements PatientManager {

    @Autowired
    private UserManager userManager;
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
    @Autowired
    private GenericManager<PatientInjuryType, PatientInjuryTypeId> patientInjuryManager;

    @Autowired
    public PatientManagerImpl(GenericDao<Patient, Long> patientDao) {
        super(patientDao);
    }

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
        referenceData.put("beltUsedOptions", lookupManager.getAllQuadrianOptions(true));
        referenceData.put("patientDispositions", patientDispositionManager.getAllDistinct());
        return referenceData;
    }

    @Override
    public Patient getPatientForView(Long id) {
        return this.get(id);
    }

    @Override
    public Patient savePatient(Patient patient) {
        User currentUser = userManager.getCurrentUser();
        List<PatientInjuryType> patientInjuryTypes = new ArrayList<>();
        patientInjuryTypes.addAll(patient.getPatientInjuryTypes());
        patient.setTempPatientInjuries(patientInjuryTypes);
        this.processPatient(patient);
        if (patient.getDateCreated() == null) {
            patient.setDateCreated(new Date());
            patient.setCreatedBy(currentUser);
            patient.setEditable(false);
            patient.setRemovable(false);
            patient.setRemoved(false);
            patient.setId(null);
        } else {
            Patient dbPatient = this.get(patient.getId());
            dbPatient.clearPatientInjuryTypes();
            patient.setDateCreated(dbPatient.getDateCreated());
            patient.setCreatedBy(dbPatient.getCreatedBy());
            patient.setDateUpdated(new Date());
            patient.setUpdatedBy(currentUser);
            super.save(dbPatient);
        }
        patient.clearPatientInjuryTypes();
        patient = super.save(patient);
        this.setPatientInjuries(patient, patientInjuryTypes);
        return super.save(patient);
    }

    @Override
    public List<Patient> getPatients() {
        return dao.findByNamedQuery(Patient.FIND_PATIENTS_ORDER_BY_DATE_DESC, null);
    }

    @Override
    public List<Patient> getAvailablePatients() {
        return dao.findByNamedQuery(Patient.FIND_AVAILABLE_PATIENT_ORDER_BY_DATE_DESC, null);
    }

    @Override
    public List<Patient> getRemovedPatients() {
        return dao.findByNamedQuery(Patient.FIND_REMOVED_PATIENTS_ORDER_BY_DATE_DESC, null);
    }

    private void processPatient(Patient patient) {
        if (patient.getHospital() != null && patient.getHospital().getId() != null) {
            patient.setHospital(hospitalManager.get(patient.getHospital().getId()));
        }
        if (patient.getDistrict() != null && patient.getDistrict().getId() != null) {
            patient.setDistrict(districtManager.get(patient.getDistrict().getId()));
        }
        if (patient.getTransportMode() != null && patient.getTransportMode().getId() != null) {
            patient.setTransportMode(transportModeManager.get(patient.getTransportMode().getId()));
        }
        if (patient.getRoadUserType() != null && patient.getRoadUserType().getId() != null) {
            patient.setRoadUserType(roadUserTypeManager.get(patient.getRoadUserType().getId()));
        }
        if (patient.getCounterpartTransportMode() != null && patient.getCounterpartTransportMode().getId() != null) {
            patient.setCounterpartTransportMode(transportModeManager.get(patient.getCounterpartTransportMode().getId()));
        }
        if (patient.getPatientDisposition() != null && patient.getPatientDisposition().getId() != null) {
            patient.setPatientDisposition(patientDispositionManager.get(patient.getPatientDisposition().getId()));
        }
        if (patient.getPatientStatus() != null && patient.getPatientStatus().getId() != null) {
            patient.setPatientStatus(patientStatusManager.get(patient.getPatientStatus().getId()));
        }
    }

    private void setPatientInjuries(Patient patient, List<PatientInjuryType> patientInjuryTypes) {
        for (PatientInjuryType patientInjuryType : patientInjuryTypes) {
            if (patientInjuryType.getInjuryType() != null && patientInjuryType.getInjuryType().getId() != null) {
                patientInjuryType.setInjuryType(injuryTypeManager.get(patientInjuryType.getInjuryType().getId()));
                patientInjuryType.setPatient(patient);
                patient.addPatientInjuryType(patientInjuryType);
            }
        }
    }
}
