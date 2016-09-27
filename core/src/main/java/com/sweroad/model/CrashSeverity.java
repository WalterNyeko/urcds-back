package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name="crash_severity")
public class CrashSeverity extends NameIdModel implements Comparable<CrashSeverity>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
    @Column
    private BigDecimal weight;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public CrashSeverity() { }

    public CrashSeverity(Long id) { this.setId(id); }

    public CrashSeverity(String name) {
        this.setName(name);
        this.setActive(true);
    }

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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
	public String toString() {
		return String.format("CrashSeverity {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CrashSeverity)) {
            return false;
        }
        return this.id != null && this.id.equals(((CrashSeverity)o).getId());
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

    /**
     * Gets name to be used within query from Crash POV.
     *
     * @return name for query
     */
    @Override
    public String getNameForQuery() {
        return Crash.CRASH_ALIAS_DOT
                .concat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getEntityName()));
    }
}
