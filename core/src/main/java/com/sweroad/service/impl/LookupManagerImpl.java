package com.sweroad.service.impl;

import com.sweroad.dao.LookupDao;
import com.sweroad.model.AgeRange;
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
    public List<LabelValue> getFilteredLicenseTypes(List<LabelValue> selectedValues) {
        List<LabelValue> filteredLicenseTypes = new ArrayList<LabelValue>();
        for(LabelValue labelValue : getAllLicenseTypes()) {
            for(LabelValue selectedValue : selectedValues) {
                if(selectedValue.getValue() != null && selectedValue.getValue().equals(labelValue.getValue())) {
                    filteredLicenseTypes.add(labelValue);
                }
            }
        }
        return filteredLicenseTypes;
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
    public List<LabelValue> getFilteredGenders(List<LabelValue> selectedValues) {
        List<LabelValue> filteredGenders = new ArrayList<LabelValue>();
        for(LabelValue labelValue : getAllGenders()) {
            for(LabelValue selectedValue : selectedValues) {
                if(selectedValue.getValue() != null && selectedValue.getValue().equals(labelValue.getValue())) {
                    filteredGenders.add(labelValue);
                }
            }
        }
        return filteredGenders;
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
    public List<LabelValue> getFilteredBeltUsedOptions(List<LabelValue> selectedValues) {
        List<LabelValue> filteredBeltUsedOptions = new ArrayList<LabelValue>();
        for(LabelValue labelValue : getAllBeltUsedOptions()) {
            for(LabelValue selectedValue : selectedValues) {
                if(selectedValue.getValue() != null && selectedValue.getValue().equals(labelValue.getValue())) {
                    filteredBeltUsedOptions.add(labelValue);
                }
            }
        }
        return filteredBeltUsedOptions;
    }

    @Override
    public List<LabelValue> getAllAgeRanges() {
        List<LabelValue> ageRanges = new ArrayList<LabelValue>();
        ageRanges.add(new AgeRange(1L, 0, 9));
        ageRanges.add(new AgeRange(2L, 10, 19));
        ageRanges.add(new AgeRange(3L, 20, 29));
        ageRanges.add(new AgeRange(4L, 30, 39));
        ageRanges.add(new AgeRange(5L, 40, 49));
        ageRanges.add(new AgeRange(6L, 50, 59));
        ageRanges.add(new AgeRange(7L, 60, 69));
        ageRanges.add(new AgeRange(8L, 70, null));
        return ageRanges;
    }

    @Override
    public List<LabelValue> getFilteredAgeRanges(List<LabelValue> selectedValues) {
        List<LabelValue> filteredAgeRanges = new ArrayList<LabelValue>();
        for(LabelValue labelValue : getAllAgeRanges()) {
            for(LabelValue selectedValue : selectedValues) {
                if(selectedValue.getValue() != null && selectedValue.getValue().equals(labelValue.getValue())) {
                    filteredAgeRanges.add(labelValue);
                }
            }
        }
        return filteredAgeRanges;
    }

    private void addLabelValueToList(String label, String value, List<LabelValue> labelValues) {
        LabelValue labelValue = new LabelValue();
        labelValue.setLabel(label);
        labelValue.setValue(value);
        labelValues.add(labelValue);
    }
}