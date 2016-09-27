package com.sweroad.util;

import com.sweroad.model.AttributeType;

/**
 *
 * This class contains system-wide utility methods
 *
 * Created by Frank on 6/4/16.
 */
public class RcdsUtil {
    private RcdsUtil() { }

    public static AttributeType extractAttributeType(String code) throws IllegalArgumentException {
        return AttributeType.getValueOf(AttributeType.class, code);
    }
}
