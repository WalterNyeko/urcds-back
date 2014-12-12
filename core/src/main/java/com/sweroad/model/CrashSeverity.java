package com.sweroad.model;

import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="crash_severity")
public class CrashSeverity extends BaseModel implements Comparable<CrashSeverity>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("CrashSeverity {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof CrashSeverity)) {
            return false;
        }

        final CrashSeverity crashSeverity = (CrashSeverity) o;

        return crashSeverity != null ? id.equals(crashSeverity.getId()) : false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

    @Override
    public int compareTo(CrashSeverity cs) {
        if (cs == null || cs.getName() == null) {
            return -1;
        }
        return this.name.compareTo(cs.getName());
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }
}
