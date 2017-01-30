package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
import com.sweroad.service.LookupService;
import com.sweroad.service.PatientService;
import com.sweroad.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Frank on 10/23/15.
 */
@Service("patientService")
public class PatientServiceImpl extends GenericManagerImpl<Patient, Long> implements PatientService {

    @Autowired
    private UserManager userManager;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private GenericManager<Hospital, Long> hospitalService;
    @Autowired
    private GenericManager<District, Long> districtService;
    @Autowired
    private GenericManager<InjuryType, Long> injuryTypeService;
    @Autowired
    private GenericManager<RoadUserType, Long> roadUserTypeService;
    @Autowired
    private GenericManager<TransportMode, Long> transportModeService;
    @Autowired
    private GenericManager<PatientStatus, Long> patientStatusService;
    @Autowired
    private GenericManager<PatientDisposition, Long> patientDispositionService;
    @Autowired
    private GenericManager<PatientInjuryType, PatientInjuryTypeId> patientInjuryService;

    @Autowired
    public PatientServiceImpl(GenericDao<Patient, Long> patientDao) {
        super(patientDao);
    }

    @Override
    public Map<String, List> getReferenceData() {
        Map<String, List> referenceData = new HashMap<>();
        List<District> districts = districtService.getAllDistinct();
        List<Hospital> hospitals = hospitalService.getAllDistinct();
        Collections.sort(districts);
        Collections.sort(hospitals);
        referenceData.put("districts", districts);
        referenceData.put("hospitals", hospitals);
        referenceData.put("genders", lookupService.getAllGenders());
        referenceData.put("injuryTypes", injuryTypeService.getAllDistinct());
        referenceData.put("roadUserTypes", roadUserTypeService.getAllDistinct());
        referenceData.put("transportModes", transportModeService.getAllDistinct());
        referenceData.put("patientStatuses", patientStatusService.getAllDistinct());
        referenceData.put("beltUsedOptions", lookupService.getAllQuadstateOptions(true));
        referenceData.put("patientDispositions", patientDispositionService.getAllDistinct());
        return referenceData;
    }

    @Override
    public Patient getPatientForView(Long id) {
        return this.get(id);
    }

    @Override
    public Patient savePatient(Patient patient) {
        User currentUser = userManager.getCurrentUser();
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
            for (PatientInjuryType patientInjuryType : dbPatient.getPatientInjuryTypes()) {
                if (!patient.getPatientInjuryTypes().contains(patientInjuryType)) {
                    patientInjuryService.remove(patientInjuryType);
                }
            }
            patient.setDateCreated(dbPatient.getDateCreated());
            patient.setCreatedBy(dbPatient.getCreatedBy());
            patient.setDateUpdated(new Date());
            patient.setUpdatedBy(currentUser);
        }
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
            patient.setHospital(hospitalService.get(patient.getHospital().getId()));
        }
        if (patient.getDistrict() != null && patient.getDistrict().getId() != null) {
            patient.setDistrict(districtService.get(patient.getDistrict().getId()));
        }
        if (patient.getTransportMode() != null && patient.getTransportMode().getId() != null) {
            patient.setTransportMode(transportModeService.get(patient.getTransportMode().getId()));
        }
        if (patient.getRoadUserType() != null && patient.getRoadUserType().getId() != null) {
            patient.setRoadUserType(roadUserTypeService.get(patient.getRoadUserType().getId()));
        }
        if (patient.getCounterpartTransportMode() != null && patient.getCounterpartTransportMode().getId() != null) {
            patient.setCounterpartTransportMode(transportModeService.get(patient.getCounterpartTransportMode().getId()));
        }
        if (patient.getPatientDisposition() != null && patient.getPatientDisposition().getId() != null) {
            patient.setPatientDisposition(patientDispositionService.get(patient.getPatientDisposition().getId()));
        }
        if (patient.getPatientStatus() != null && patient.getPatientStatus().getId() != null) {
            patient.setPatientStatus(patientStatusService.get(patient.getPatientStatus().getId()));
        }
        List<PatientInjuryType> patientInjuryTypes = new ArrayList<>();
        patientInjuryTypes.addAll(patient.getPatientInjuryTypes());
        patient.clearPatientInjuryTypes();
        this.setPatientInjuries(patient, patientInjuryTypes);
    }

    private void setPatientInjuries(Patient patient, List<PatientInjuryType> patientInjuryTypes) {
        patientInjuryTypes.stream()
                .filter(patientInjuryType -> patientInjuryType.getInjuryType() != null
                        && patientInjuryType.getInjuryType().getId() != null)
                .forEach(patientInjuryType -> {
                    patientInjuryType.setInjuryType(injuryTypeService.get(patientInjuryType.getInjuryType().getId()));
                    patientInjuryType.setPatient(patient);
                    patient.addPatientInjuryType(patientInjuryType);
                });
    }
}
