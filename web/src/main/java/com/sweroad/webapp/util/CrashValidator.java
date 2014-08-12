package com.sweroad.webapp.util;

import java.util.List;

import com.sweroad.model.Casualty;
import com.sweroad.model.Vehicle;

public class CrashValidator {
	public static boolean vehicleAlreadyExistsInList(List<Vehicle> vehicleList,
			Vehicle vehicle) {
		for (Vehicle v : vehicleList) {
			if (v.equals(vehicle)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean casualtyAlreadyExistsInList(List<Casualty> casualtyList,
			Casualty casualty) {
		for (Casualty c : casualtyList) {
			if (c.equals(casualty)) {
				return true;
			}
		}
		return false;
	}
}
