package com.sweroad.webapp.controller;

import com.sweroad.model.Crash;
import com.sweroad.service.CrashService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Frank on 3/18/15.
 */
@Controller
@RequestMapping("/crashsearch*")
public class CrashSearchController extends BaseFormController {

    @Autowired
    private CrashService crashService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView searchByTarNo(HttpServletRequest request) throws Exception {
        String tarNo = request.getParameter("tarNo");
        if(!StringUtils.isBlank(tarNo)) {
            List<Crash> crashes = crashService.findByTarNo(tarNo);
            ModelAndView mav = new ModelAndView("searchresults");
            mav.addObject("crashes", crashes);
            return mav;
        }
        return new ModelAndView("redirect:/home");
    }
}
