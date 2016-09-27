package com.sweroad.service.count;

import com.sweroad.model.AttributeType;
import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;

import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
public interface CountService {

    List<CountResult> countCrashes(List<Crash> crashes, AttributeType attributeType);
}
