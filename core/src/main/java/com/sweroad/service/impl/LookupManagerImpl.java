package com.sweroad.service.impl;

import com.sweroad.Constants;
import com.sweroad.dao.LookupDao;
import com.sweroad.model.AgeRange;
import com.sweroad.model.LabelValue;
import com.sweroad.model.Role;
import com.sweroad.model.TimeRange;
import com.sweroad.query.service.AgeQueryableService;
import com.sweroad.service.LookupManager;
import com.sweroad.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        ageRanges.add(new AgeRange(1L, 0, 4));
        ageRanges.add(new AgeRange(2L, 5, 14));
        ageRanges.add(new AgeRange(3L, 15, 17));
        ageRanges.add(new AgeRange(4L, 18, 24));
        ageRanges.add(new AgeRange(5L, 25, 34));
        ageRanges.add(new AgeRange(6L, 35, 44));
        ageRanges.add(new AgeRange(7L, 45, 54));
        ageRanges.add(new AgeRange(8L, 55, 64));
        ageRanges.add(new AgeRange(9L, 65, 74));
        ageRanges.add(new AgeRange(10L, 75, null));
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

    @Override
    public AgeRange getAgeRangeByAge(int age) {
        for(LabelValue range : getAllAgeRanges()) {
            AgeRange ageRange = (AgeRange)range;
            if (ageRange.getMinAge() >= age && (ageRange.getMaxAge() == null || ageRange.getMaxAge() >= age)) {
                return ageRange;
            }
        }
        return null;
    }

    @Override
    public List<LabelValue> getAllTimeRanges() {
        List<LabelValue> timeRanges = new ArrayList<LabelValue>();
        for(int i = 0; i < Constants.HOURS_IN_DAY; i++) {
            timeRanges.add(new TimeRange(i));
        }
        return timeRanges;
    }

    @Override
    public TimeRange getTimeRangeByTime(Date dateTime) {
        int hour = DateUtil.getHourOfDay(dateTime);
        for(LabelValue timeRange : getAllTimeRanges()) {
            if (((TimeRange)timeRange).getHour() == hour) {
                return (TimeRange)timeRange;
            }
        }
        return new TimeRange(0);
    }


    private void addLabelValueToList(String label, String value, List<LabelValue> labelValues) {
        LabelValue labelValue = new LabelValue();
        labelValue.setLabel(label);
        labelValue.setValue(value);
        labelValues.add(labelValue);
    }
}