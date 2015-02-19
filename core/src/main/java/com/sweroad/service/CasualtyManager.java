package com.sweroad.service;

import java.util.List;

import com.sweroad.model.Casualty;
import com.sweroad.model.Crash;

public interface CasualtyManager extends GenericManager<Casualty, Long> {

    List<Casualty> extractCasualtiesFromCrashList(List<Crash> crashes);
}