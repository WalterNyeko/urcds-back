/**
 * 
 */
package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.*;

/**
 * @author Frank
 *
 */
@Entity
@Table(name = "police_station")
@NamedQueries({
        @NamedQuery(name = PoliceStation.FIND_POLICE_STATIONS_ORDER_BY_NAME, query = "from PoliceStation d order by d.name")})
public class PoliceStation extends NameIdModel implements Comparable<PoliceStation>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7204210378164794420L;
    public static final String FIND_POLICE_STATIONS_ORDER_BY_NAME = "findPoliceStationsOrderByName";

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "district_id")
	private District district;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public PoliceStation() { }

    public PoliceStation(Long id) { this.setId(id); }

    public PoliceStation(String name) {
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

	/**
	 * @return the district
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(District district) {
		this.district = district;
	}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
	public String toString() {
		return String.format("Police Station {%s}", name);
	}

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder(super.toJSON());
        json.insert(json.lastIndexOf("}"), ",");
        json.insert(json.lastIndexOf("}"), toJsonProperty(this.district, "district"));
        return json.toString();
    }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PoliceStation)) {
            return false;
        }
        return this.id != null && this.id.equals(((PoliceStation)o).getId());
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(PoliceStation ps) {
        if (ps == null || ps.getName() == null) {
            return -1;
        }
        return this.name.compareTo(ps.getName());
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
