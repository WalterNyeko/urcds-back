package com.sweroad.model;

/**
 * Created by Frank on 6/4/16.
 */
public enum AttributeType {

    WEATHER("weather"),
    DISTRICT("district"),
    HOSPITAL("hospital"),
    DRIVER_AGE("driverAge"),
    INJURY_TYPE("injuryType"),
    CRASH_CAUSE("crashCause"),
    VEHICLE_TYPE("vehicleType"),
    ROAD_SURFACE("roadSurface"),
    SURFACE_TYPE("surfaceType"),
    DRIVER_GENDER("driverGender"),
    JUNCTION_TYPE("junctionType"),
    CASUALTY_TYPE("casualtyType"),
    ROAD_USER_TYPE("roadUserType"),
    CASUALTY_CLASS("casualtyClass"),
    COLLISION_TYPE("collisionType"),
    CRASH_SEVERITY("crashSeverity"),
    PATIENT_STATUS("patientStatus"),
    POLICE_STATION("policeStation"),
    TRANSPORT_MODE("transportMode"),
    DRIVER_BELT_USE("driverBeltUse"),
    ROADWAY_CHARACTER("roadwayCharacter"),
    SURFACE_CONDITION("surfaceCondition"),
    DRIVER_LICENSE_TYPE("driverLicenseType"),
    PATIENT_DISPOSITION("patientDisposition"),
    DRIVER_CASUALTY_TYPE("driverCasualtyType"),
    VEHICLE_FAILURE_TYPE("vehicleFailureType");

    private final String typeCode;

    private AttributeType(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public static AttributeType getValueOf(Class<AttributeType> attributeTypeClass, String code) {
        for (AttributeType attributeType : attributeTypeClass.getEnumConstants()) {
            if (attributeType.getTypeCode().equals(code)) {
                return attributeType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + attributeTypeClass.getName() + "for code \'" + code + "\'");
    }
}
