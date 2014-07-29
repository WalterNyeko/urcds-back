package com.sweroad.service;

import java.util.List;

import com.sweroad.model.Casualty;

public interface CasualtyManager extends GenericManager<Casualty, Long> {

	List<Casualty> getAllCasualties();
}