package com.sweroad.service.impl;

import com.sweroad.dao.LookupDao;
import com.sweroad.model.LabelValue;
import com.sweroad.model.Role;
import com.sweroad.service.LookupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }

    @Override
    public List<LabelValue> getAllLicenseTypes() {
        List<LabelValue> licenseTypes = new ArrayList<LabelValue>();
        addLabelValueToList("Valid License", "1", licenseTypes);
        addLabelValueToList("No Valid License", "0", licenseTypes);
        addLabelValueToList("Unknown", "-1", licenseTypes);
        return licenseTypes;
    }

    @Override
    public List<LabelValue> getAllGenders() {
        List<LabelValue> genders = new ArrayList<LabelValue>();
        addLabelValueToList("Male", "M", genders);
        addLabelValueToList("Female", "F", genders);
        addLabelValueToList("Unknown", "-1", genders);
        return genders;
    }

    @Override
    public List<LabelValue> getAllBeltUsedOptions() {
        List<LabelValue> beltusedOptions = new ArrayList<LabelValue>();
        addLabelValueToList("Yes", "1", beltusedOptions);
        addLabelValueToList("No", "0", beltusedOptions);
        addLabelValueToList("Unknown", "-1", beltusedOptions);
        return beltusedOptions;
    }

    @Override
    public List<LabelValue> getAllAgeRanges() {
        List<LabelValue> ageRanges = new ArrayList<LabelValue>();
        addLabelValueToList("Below 10", "1", ageRanges);
        addLabelValueToList("10-19", "2", ageRanges);
        addLabelValueToList("20-29", "3", ageRanges);
        addLabelValueToList("30-39", "4", ageRanges);
        addLabelValueToList("40-49", "5", ageRanges);
        addLabelValueToList("50-59", "6", ageRanges);
        addLabelValueToList("60-69", "7", ageRanges);
        addLabelValueToList("70 and above", "8", ageRanges);
        return ageRanges;
    }

    private void addLabelValueToList(String label, String value, List<LabelValue> labelValues) {
        LabelValue licenseType = new LabelValue();
        licenseType.setLabel(label);
        licenseType.setValue(value);
        labelValues.add(licenseType);
    }

}
