package com.sweroad.model;

import javax.persistence.*;

@Entity
@Table(name = "district")
@NamedQueries({
        @NamedQuery(name = District.FIND_DISTRICTS_ORDER_BY_NAME, query = "from District d order by d.name")})
public class District extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268584529429360770L;
    public static final String FIND_DISTRICTS_ORDER_BY_NAME = "findDistrictsOrderByName";
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
		return String.format("District: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }

        final District district = (District) o;

        return district != null ? id.equals(district.getId()) : false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
