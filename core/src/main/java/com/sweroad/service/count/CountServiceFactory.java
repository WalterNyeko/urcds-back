package com.sweroad.service.count;

import com.sweroad.model.AttributeType;

/**
 * Created by Frank on 7/1/16.
 */
public interface CountServiceFactory {

    CountAttributeService getCountService(AttributeType attributeType);
}
