package com.sweroad.service;

import com.sweroad.model.NameIdModel;
import com.sweroad.model.SystemParameter;

import java.util.List;

/**
 * Created by Frank on 2/3/16.
 */
public interface SystemParameterManager extends GenericManager<SystemParameter, Long> {

    List<? extends NameIdModel> getParameters(String type) throws Exception;

    NameIdModel addParameter(String name, String code) throws Exception;

    void updateParameterName(Long id, String name, String code) throws Exception;

    void setParameterActive(Long id, boolean active, String code) throws Exception;
}
