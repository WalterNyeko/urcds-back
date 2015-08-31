package com.sweroad.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.MappedSuperclass;


/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode().
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseModel implements Serializable {

    /**
     * Returns a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    public abstract String toString();

    /**
     * @return a JSON-style string of this object
     */
    public abstract String toJSON();

    /**
     * Compares object equality. When using Hibernate, the primary key should
     * not be a part of this comparison.
     *
     * @param o object to compare to
     * @return true/false based on equality tests
     */
    public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     *
     * @return hashCode
     */
    public abstract int hashCode();

    /**
     * Generates a JSON-style property string for a primitive (or String) variable
     *
     * @param var
     * @param name
     * @return
     */
    protected String toJsonProperty(Object var, String name) {
        StringBuilder jsonBuilder = new StringBuilder("\"" + name + "\":");
        if (var == null) {
            jsonBuilder.append("null");
        } else if (var instanceof String) {
            jsonBuilder.append("\"" + var + "\"");
        } else if (var instanceof BaseModel) {
            jsonBuilder.append(((BaseModel)var).toJSON());
        } else if (var instanceof List) {
            if (((List)var).size() > 0 && ((List)var).get(0) instanceof BaseModel) {
                jsonBuilder.append("[");
                for(Object item : (List)var) {
                    jsonBuilder.append(((BaseModel)item).toJSON()).append(",");
                }
                jsonBuilder.replace(jsonBuilder.lastIndexOf(","), jsonBuilder.lastIndexOf(",") + 1, "]");
            }
        } else {
            jsonBuilder.append(var);
        }
        return jsonBuilder.toString();
    }
}
