package com.sweroad.model;

/**
 * Created by Frank on 6/4/16.
 */
public enum AttributeType {

    WEATHER ("weather"),
    DISTRICT ("district"),
    HOSPITAL ("hospital"),
    INJURY_TYPE ("injuryType"),
    CRASH_CAUSE ("crashCause"),
    VEHICLE_TYPE ("vehicleType"),
    ROAD_SURFACE ("roadSurface"),
    JUNCTION_TYPE ("junctionType"),
    CASUALTY_TYPE ("casualtyType"),
    ROAD_USER_TYPE ("roadUserType"),
    CASUALTY_CLASS ("casualtyClass"),
    COLLISION_TYPE ("collisionType"),
    CRASH_SEVERITY ("crashSeverity"),
    PATIENT_STATUS ("patientStatus"),
    POLICE_STATION ("policeStation"),
    TRANSPORT_MODE ("transportMode"),
    ROADWAY_CHARACTER ("roadwayCharacter"),
    SURFACE_CONDITION ("surfaceCondition"),
    PATIENT_DISPOSITION ("patientDisposition"),
    VEHICLE_FAILURE_TYPE ("vehicleFailureType");

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
