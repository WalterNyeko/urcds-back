package com.sweroad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="gps_coordinate")
public class GpsCoordinate extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String latitude;
	@Column(nullable = false)
	private String longitude;

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
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return String.format("GpsCoordinate {%s, %s}", latitude, longitude);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof GpsCoordinate)) {
            return false;
        }

        final GpsCoordinate gpsCoordinate = (GpsCoordinate) o;

        return gpsCoordinate != null ? id.equals(gpsCoordinate.getId()) : false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}