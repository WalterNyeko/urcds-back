/**
 * 
 */
package com.sweroad.model;

import javax.persistence.*;

/**
 * @author Frank
 *
 */
@Entity
@Table(name = "police_station")
@NamedQueries({
        @NamedQuery(name = PoliceStation.FIND_POLICE_STATIONS_ORDER_BY_NAME, query = "from PoliceStation d order by d.name")})
public class PoliceStation extends BaseModel {

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

	@Override
	public String toString() {
		return String.format("Police Station: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof PoliceStation)) {
            return false;
        }

        final PoliceStation policeStation = (PoliceStation) o;

        return policeStation != null ? id.equals(policeStation.getId()) : false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}
