package com.sweroad.service;

import com.sweroad.model.AgeRange;
import com.sweroad.model.LabelValue;
import com.sweroad.model.TimeRange;

import java.util.Date;
import java.util.List;

/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupManager {
    /**
     * Retrieves all possible roles from persistence layer
     * @return List of LabelValue objects
     */
    List<LabelValue> getAllRoles();

    List<LabelValue> getAllLicenseTypes();

    List<LabelValue> getFilteredLicenseTypes(List<LabelValue> selectedValues);

    List<LabelValue> getAllGenders();

    List<LabelValue> getFilteredGenders(List<LabelValue> selectedValues);

    List<LabelValue> getAllBeltUsedOptions();

    List<LabelValue> getFilteredBeltUsedOptions(List<LabelValue> selectedValues);

    List<LabelValue> getAllAgeRanges();

    List<LabelValue> getFilteredAgeRanges(List<LabelValue> selectedValues);

    AgeRange getAgeRangeByAge(int age);

    List<LabelValue> getAllTimeRanges();

    TimeRange getTimeRangeByTime(Date dateTime);
}
