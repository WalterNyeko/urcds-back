package com.sweroad.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.CasualtyClass;
import com.sweroad.model.CasualtyType;
import com.sweroad.model.CollisionType;
import com.sweroad.model.Crash;
import com.sweroad.model.CrashCause;
import com.sweroad.model.CrashSeverity;
import com.sweroad.model.District;
import com.sweroad.model.JunctionType;
import com.sweroad.model.PoliceStation;
import com.sweroad.model.RoadSurface;
import com.sweroad.model.RoadwayCharacter;
import com.sweroad.model.SurfaceCondition;
import com.sweroad.model.SurfaceType;
import com.sweroad.model.VehicleFailureType;
import com.sweroad.model.VehicleType;
import com.sweroad.model.Weather;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;

@Controller
@RequestMapping("/crashform*")
public class CrashFormController extends BaseFormController {
	
	@Autowired
	private CrashManager crashManager;	
	@Autowired
	private GenericManager<CrashSeverity, Long> crashSeverityManager;
	@Autowired
	private GenericManager<CollisionType, Long> collisionTypeManager;
	@Autowired
	private GenericManager<CrashCause, Long> crashCauseManager;
	@Autowired
	private GenericManager<VehicleFailureType, Long> vehicleFailureTypeManager;
	@Autowired
	private GenericManager<Weather, Long> weatherManager;
	@Autowired
	private GenericManager<SurfaceCondition, Long> surfaceConditionManager;
	@Autowired
	private GenericManager<RoadSurface, Long> roadSurfaceManager;
	@Autowired
	private GenericManager<SurfaceType, Long> surfaceTypeManager;
	@Autowired
	private GenericManager<RoadwayCharacter, Long> roadwayCharacterManager;
	@Autowired
	private GenericManager<JunctionType, Long> junctionTypeManager;
	@Autowired
	private GenericManager<VehicleType, Long> vehicleTypeManager;
	@Autowired
	private GenericManager<CasualtyClass, Long> casualtyClassManager;
	@Autowired
	private GenericManager<CasualtyType, Long> casualtyTypeManager;
	@Autowired
	private GenericManager<PoliceStation, Long> policeStationManager;
	@Autowired
	private GenericManager<District, Long> districtManager;
	
	public CrashFormController() {
		setCancelView("redirect:crashes");
		setSuccessView("redirect:crashes");
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Crash crash;
		String id = request.getParameter("id");
		if(!StringUtils.isBlank(id)) {
			crash = crashManager.get(new Long(id));
		} else {
			crash = new Crash();
		}
		mav.addObject("crash", crash);
		mav.addAllObjects(referenceData(request));
		return mav;
	}

	public void onSubmit(Crash crash, BindingResult errors,
			HttpServletRequest request,
			HttpServletResponse response) {
		
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, List> referenceData(HttpServletRequest request) throws Exception {
		Map<String, List> referenceData = new HashMap<String, List>();
		//Add reference data to map for use in the UI
		List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
		List<CollisionType> collisionTypes = collisionTypeManager.getAllDistinct();
		List<CrashCause> crashCauses = crashCauseManager.getAllDistinct();
		List<VehicleFailureType> vehicleFailureTypes = vehicleFailureTypeManager.getAllDistinct();
		List<Weather> weathers = weatherManager.getAllDistinct();
		List<SurfaceCondition> surfaceConditions = surfaceConditionManager.getAllDistinct();
		List<RoadSurface> roadSurfaces = roadSurfaceManager.getAllDistinct();
		List<SurfaceType> surfaceTypes = surfaceTypeManager.getAllDistinct();
		List<RoadwayCharacter> roadwayCharacters = roadwayCharacterManager.getAllDistinct();
		List<JunctionType> junctionTypes = junctionTypeManager.getAllDistinct();
		List<PoliceStation> policeStations = policeStationManager.getAllDistinct();
		List<District> districts = districtManager.getAllDistinct();
		
		log.debug("I have managed to get them; CrashSeverities: " + crashSeverities.size());
		log.debug("I have managed to get them; Districts: " + districts.size());
		
		referenceData.put("crashSeverities", crashSeverities);
		referenceData.put("collisionTypes", collisionTypes);
		referenceData.put("crashCauses", crashCauses);
		referenceData.put("vehicleFailureTypes", vehicleFailureTypes);
		referenceData.put("weathers", weathers);
		referenceData.put("surfaceConditions", surfaceConditions);
		referenceData.put("roadSurfaces", roadSurfaces);
		referenceData.put("surfaceTypes", surfaceTypes);
		referenceData.put("roadwayCharacters", roadwayCharacters);
		referenceData.put("junctionTypes", junctionTypes);
		referenceData.put("districts", districts);
		referenceData.put("policeStations", policeStations);
		
		return referenceData;
	}
	
	

}
