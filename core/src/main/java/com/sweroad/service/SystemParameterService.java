package com.sweroad.service;

import com.sweroad.model.AttributeType;
import com.sweroad.model.NameIdModel;
import com.sweroad.model.SystemParameter;

import java.util.List;

/**
 * Created by Frank on 2/3/16.
 */
public interface SystemParameterService extends GenericManager<SystemParameter, Long> {

    List<? extends NameIdModel> getParameters(AttributeType type) throws Exception;

    NameIdModel addParameter(String name, AttributeType type) throws Exception;

    void updateParameterName(Long id, String name, AttributeType type) throws Exception;

    void setParameterActive(Long id, boolean active, AttributeType type) throws Exception;
}