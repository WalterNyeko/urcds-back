package com.sweroad.service;

import com.sweroad.model.Patient;

import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 10/23/15.
 */
public interface PatientManager extends GenericManager<Patient, Long> {

    Map<String, List> getReferenceData();
}
