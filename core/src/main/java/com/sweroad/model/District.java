package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.*;

@Entity
@Table(name = "district")
@NamedQueries({
        @NamedQuery(name = District.FIND_DISTRICTS_ORDER_BY_NAME, query = "from District d order by d.name")})
public class District extends NameIdModel implements Comparable<District>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268584529429360770L;
    public static final String FIND_DISTRICTS_ORDER_BY_NAME = "findDistrictsOrderByName";
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public District() { }

    public District(Long id) { this.setId(id); }

    public District(String name) {
        this.setName(name);
        this.setActive(true); }
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
	public String toString() {
		return String.format("District {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
        if (!(o instanceof District)) {
            return false;
        }
        return this.id != null && this.id.equals(((District)o).getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

    @Override
    public int compareTo(District d) {
        if (d == null || d.getName() == null) {
            return -1;
        }
        return this.name.compareTo(d.getName());
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
        return Crash.CRASH_ALIAS_DOT.concat("policeStation.")
                .concat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getEntityName()));
    }
}
