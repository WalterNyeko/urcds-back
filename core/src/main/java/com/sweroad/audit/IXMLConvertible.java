package com.sweroad.audit;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 10/13/14.
 */
public interface IXMLConvertible extends Serializable {

    /**
     * Gets class alias.
     *
     * @return the class alias
     */
    String getClassAlias();

    /**
     * Gets the fields to be omitted.
     *
     * @return the fields to be omitted
     */
    List<String> getFieldsToBeOmitted();

    /**
     * Gets the field aliases.
     *
     * @return the field aliases
     */
    Map<String, String> getFieldsAliases();
}
