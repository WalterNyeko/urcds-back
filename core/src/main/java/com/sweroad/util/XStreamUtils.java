package com.sweroad.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 10/13/14.
 */
public class XStreamUtils {

    public static String getXMLFromObject(Object tobeConverted,
                                          String classNameAlias,
                                          Map<String, String> fieldAlias,
                                          List<String> fieldsToBeOmitted) {
        StringBuilder objectAsXML = new StringBuilder();
        if (tobeConverted != null) {
            XStream xStream = new XStream(new DomDriver());
            if (StringUtils.isNotEmpty(classNameAlias)) {
                xStream.alias(classNameAlias, tobeConverted.getClass());
            }
            if (fieldAlias != null && !fieldAlias.isEmpty()) {
                for (Map.Entry<String, String> entry : fieldAlias.entrySet()) {
                    xStream.aliasField(entry.getKey(), tobeConverted.getClass(), entry.getValue());
                }
            }
            if(CollectionUtils.isNotEmpty(fieldsToBeOmitted)) {
                for(String fieldToBeOmitted : fieldsToBeOmitted) {
                    xStream.omitField(tobeConverted.getClass(), fieldToBeOmitted);
                }
            }
            objectAsXML.append(xStream.toXML(tobeConverted));
        }
       return objectAsXML.toString();
    }
}
