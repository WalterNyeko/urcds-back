package com.sweroad.service.impl;

import com.sweroad.Constants;
import com.sweroad.dao.LookupDao;
import com.sweroad.model.*;
import com.sweroad.service.LookupManager;
import com.sweroad.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {

    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    LookupDao dao;

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<>();
        roles.forEach(role -> list.add(new LabelValue(role.getName(), role.getName())));
        return list;
    }

    @Override
    public List<LabelValue> getAllLicenseTypes() {
        List<LabelValue> licenseTypes = new ArrayList<>();
        licenseTypes.add(new LicenseType("Valid License", "1"));
        licenseTypes.add(new LicenseType("No Valid License", "0"));
        licenseTypes.add(new LicenseType("Unknown", "-1"));
        return licenseTypes;
    }

    @Override
    public List<Quadstate> getFilteredLicenseTypes(List<QuadstateWrapper> selectedStates) {
        return getAllQuadstateOptions(true).stream()
                .filter(licenseType -> selectedStates.contains(licenseType))
                .map(QuadstateWrapper::getQuadstate)
                .collect(Collectors.toList());
    }

    @Override
    public List<LabelValue> getAllGenders() {
        List<LabelValue> genders = new ArrayList<>();
        genders.add(new Gender("Male", "M", 1L));
        genders.add(new Gender("Female", "F", 2L));
        genders.add(new Gender("Unknown", "-1", 3L));
        return genders;
    }

    @Override
    public List<QuadstateWrapper> getAllQuadstateOptions(boolean includeNA) {
        List<QuadstateWrapper> quadstates = new ArrayList<>();
        for(Quadstate quadstate : Quadstate.values()) {
            if (includeNA) {
                quadstates.add(new QuadstateWrapper(quadstate));
            } else if (!quadstate.equals(Quadstate.NOT_APPLICABLE)) {
                quadstates.add(new QuadstateWrapper(quadstate));
            }
        }
        return quadstates;
    }

    @Override
    public List<LabelValue> getFilteredGenders(List<LabelValue> selectedValues) {
        return getAllGenders().stream()
                .filter(gender -> selectedValues.contains(gender))
                .collect(Collectors.toList());
    }

    @Override
    public List<LabelValue> getAllBeltUsedOptions() {
        List<LabelValue> beltUsedOptions = new ArrayList<>();
        beltUsedOptions.add(new BeltUsedOption("Yes", "1"));
        beltUsedOptions.add(new BeltUsedOption("No", "0"));
        beltUsedOptions.add(new BeltUsedOption("Unknown", "-1"));
        return beltUsedOptions;
    }

    @Override
    public List<Quadstate> getFilteredBeltUsedOptions(List<QuadstateWrapper> selectedStates) {
        return getAllQuadstateOptions(true).stream()
                .filter(option -> selectedStates.contains(option))
                .map(QuadstateWrapper::getQuadstate)
                .collect(Collectors.toList());
    }

    @Override
    public List<LabelValue> getAllAgeRanges() {
        List<LabelValue> ageRanges = new ArrayList<>();
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
        return getAllAgeRanges().stream()
                .filter(range -> selectedValues.contains(range))
                .collect(Collectors.toList());
    }

    @Override
    public AgeRange getAgeRangeByAge(int age) {
        try {
            return (AgeRange)getAllAgeRanges().stream()
                    .filter(range -> ((AgeRange)range).contains(age))
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            log.error("Invalid age '" + age + "' detected: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<LabelValue> getAllWeightRanges() {
        List<LabelValue> weightRanges = new ArrayList<>();
        weightRanges.add(new WeightRange(1L, new BigDecimal(0), new BigDecimal(9999)));
        weightRanges.add(new WeightRange(2L, new BigDecimal(10000), new BigDecimal(19999)));
        weightRanges.add(new WeightRange(3L, new BigDecimal(20000), new BigDecimal(29999)));
        weightRanges.add(new WeightRange(4L, new BigDecimal(30000), new BigDecimal(39999)));
        weightRanges.add(new WeightRange(5L, new BigDecimal(40000), new BigDecimal(49999)));
        weightRanges.add(new WeightRange(6L, new BigDecimal(50000), new BigDecimal(59999)));
        weightRanges.add(new WeightRange(7L, new BigDecimal(60000), new BigDecimal(69999)));
        weightRanges.add(new WeightRange(8L, new BigDecimal(70000), new BigDecimal(79999)));
        weightRanges.add(new WeightRange(9L, new BigDecimal(80000), new BigDecimal(89999)));
        weightRanges.add(new WeightRange(10L, new BigDecimal(90000), new BigDecimal(99999)));
        weightRanges.add(new WeightRange(11L, new BigDecimal(100000), null));
        return weightRanges;
    }

    @Override
    public List<LabelValue> getAllTimeRanges() {
        List<LabelValue> timeRanges = new ArrayList<>();
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
}