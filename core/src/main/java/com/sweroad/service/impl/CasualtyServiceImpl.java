package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.Casualty;
import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;
import com.sweroad.service.CasualtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 2/19/15.
 */
@Service("casualtyService")
public class CasualtyServiceImpl extends GenericManagerImpl<Casualty, Long> implements CasualtyService {

    @Autowired
    public CasualtyServiceImpl(GenericDao<Casualty, Long> casualtyDao) {
        super(casualtyDao);
    }

    @Override
    public List<Casualty> extractCasualtiesFromCrashList(List<Crash> crashes) {
        List<Casualty> casualties = new ArrayList<Casualty>();
        for (Crash crash : crashes) {
            addCrashCasualtiesToCasualtyList(crash, casualties);
        }
        return casualties;
    }

    private void addCrashCasualtiesToCasualtyList(Crash crash, List<Casualty> casualties) {
        addDriversInjuredInCrashToCasualtyList(crash, casualties);
        for(Casualty casualty : crash.getCasualties()){
            casualty.setCrash(crash);
            casualties.add(casualty);
        }
    }

    private void addDriversInjuredInCrashToCasualtyList(Crash crash, List<Casualty> casualties) {
        for (Vehicle vehicle : crash.getVehicles()) {
            Casualty injuredDriver = vehicle.getDriver().toCasualty(vehicle);
            if (injuredDriver != null) {
                injuredDriver.setCrash(crash);
                casualties.add(injuredDriver);
            }
        }
    }
}
