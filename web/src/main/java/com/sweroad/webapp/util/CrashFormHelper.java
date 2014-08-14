package com.sweroad.webapp.util;

import java.util.ArrayList;
import java.util.List;

import com.sweroad.model.Casualty;
import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;

public class CrashFormHelper {
	public static boolean vehicleAlreadyExistsInList(List<Vehicle> vehicleList,
			Vehicle vehicle) {
		for (Vehicle v : vehicleList) {
			if (v.equals(vehicle)) {
				return true;
			}
		}
		return false;
	}

	public static boolean casualtyAlreadyExistsInList(
			List<Casualty> casualtyList, Casualty casualty) {
		for (Casualty c : casualtyList) {
			if (c.equals(casualty)) {
				return true;
			}
		}
		return false;
	}

	public static Long getCrashsMaximumCasualtyId(Crash crash) {
		long max = 0;
		if (crash.getCasualties() != null) {
			for (Casualty casualty : crash.getCasualties()) {
				if (casualty.getId().longValue() > max) {
					max = casualty.getId();
				}
			}
		} else {
			crash.setCasualties(new ArrayList<Casualty>());
		}
		return max;
	}

	public static Long getCrashsMaximumVehicleId(Crash crash) {
		long max = 0;
		if (crash.getVehicles() != null) {
			for (Vehicle vehicle : crash.getVehicles()) {
				if (vehicle.getId().longValue() > max) {
					max = vehicle.getId();
				}
			}
		} else {
			crash.setVehicles(new ArrayList<Vehicle>());
		}
		return max;
	}

	public static void resetVehicleNumbers(Crash crash) {
		int num = 1;
		for (Vehicle vehicle : crash.getVehicles()) {
			vehicle.setNumber(num++);
		}
	}
}
