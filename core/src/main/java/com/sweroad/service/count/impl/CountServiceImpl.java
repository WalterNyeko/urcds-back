package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.service.count.CountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countService")
public class CountServiceImpl implements CountService {

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes, String attribute) {
        return null;
    }
}