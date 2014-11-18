package com.sweroad.service;

import com.sweroad.model.Crash;
import com.sweroad.model.SearchCriteria;

import java.util.List;

/**
 * Created by Frank on 11/17/14.
 */
public interface SearchCriteriaManager {

    /**
     * Returns a list of crashes that meet the selection criteria. Crashes are ordered
     * by descending crash date
     * @param searchCriteria
     * @return
     */
    List<Crash> getCrashesByCriteria(SearchCriteria searchCriteria);
}
