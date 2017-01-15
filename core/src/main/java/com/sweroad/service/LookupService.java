package com.sweroad.service;

import com.sweroad.model.*;

import java.util.Date;
import java.util.List;

/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupService {
    /**
     * Retrieves all possible roles from persistence layer
     * @return List of LabelValue objects
     */
    List<LabelValue> getAllRoles();

    List<LabelValue> getAllLicenseTypes();

    List<Quadstate> getFilteredLicenseTypes(List<QuadstateWrapper> selectedStates);

    List<LabelValue> getAllGenders();

    List<QuadstateWrapper> getAllQuadstateOptions(boolean includeNA);

    List<LabelValue> getFilteredGenders(List<LabelValue> selectedValues);

    List<LabelValue> getAllBeltUsedOptions();

    List<Quadstate> getFilteredBeltUsedOptions(List<QuadstateWrapper> selectedStates);

    List<LabelValue> getAllAgeRanges();

    List<LabelValue> getFilteredAgeRanges(List<LabelValue> selectedValues);

    AgeRange getAgeRangeByAge(int age);

    List<LabelValue> getAllWeightRanges();

    List<LabelValue> getAllTimeRanges();

    TimeRange getTimeRangeByTime(Date dateTime);
}
