package com.sweroad.service.count.impl;

import com.sweroad.model.AttributeType;
import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.service.count.CountService;
import com.sweroad.service.count.CountServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countService")
public class CountServiceImpl implements CountService {

    @Autowired
    private CountServiceFactory countServiceFactory;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes, AttributeType attributeType) {
        return countServiceFactory.getCountService(attributeType).countCrashes(crashes);
    }
}