/**
 * 
 */
package com.sweroad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Frank
 *
 */
@Entity(name = "weather")
public class Weather extends BaseModel implements Comparable<Weather> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6015115060520130082L;
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
		return String.format("Weather: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof Weather)) {
            return false;
        }

        final Weather weather = (Weather) o;

        return weather != null ? name.equalsIgnoreCase(weather.getName()) : false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(Weather w) {
        if (w == null || w.getName() == null) {
            return -1;
        }
        return this.name.compareTo(w.getName());
    }
}
