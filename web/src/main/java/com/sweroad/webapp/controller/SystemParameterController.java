package com.sweroad.webapp.controller;

import com.sweroad.model.AttributeType;
import com.sweroad.model.NameIdModel;
import com.sweroad.model.SystemParameter;
import com.sweroad.service.SystemParameterService;
import com.sweroad.util.RcdsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Created by Frank on 1/27/16.
 */
@Controller
@RequestMapping("/admin/params*")
public class SystemParameterController extends BaseFormController {

    @Autowired
    private SystemParameterService systemParameterService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showParams(HttpServletRequest request) throws Exception {
        try {
            List<SystemParameter> systemParameters = systemParameterService.getAllDistinct();
            Collections.sort(systemParameters);
            return new ModelAndView("admin/parameters").addObject("parameters", systemParameters);
        } catch (Exception e) {
            logException(request, e, "Loading system parameters encountered a problem");
            return new ModelAndView("admin/parameters");
        }
    }

    @RequestMapping(value = "/admin/paramslist", method = RequestMethod.GET)
    public ModelAndView listParameterAttributes(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam("id") Long paramTypeId) throws Exception {
        try {
            SystemParameter systemParameter = systemParameterService.get(paramTypeId);
            List<? extends NameIdModel> parameters = systemParameterService
                    .getParameters(RcdsUtil.extractAttributeType(systemParameter.getCode()));
            String view = "admin/paramlist";
            if (systemParameter.getCode().equals("policeStation")) {
                view = "admin/policestations";
            }
            ModelAndView mav = new ModelAndView(view);
            mav.addObject("systemParameter", systemParameter);
            mav.addObject("parameters", parameters);
            return mav;
        } catch (Exception e) {
            logException(request, e, "Loading system parameters encountered a problem: " + e.getLocalizedMessage());
            response.sendRedirect(request.getContextPath() + "/admin/params");
            return null;
        }
    }

    @RequestMapping(value = "/admin/paramsadd", method = RequestMethod.GET)
    public
    @ResponseBody
    String addParameter(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        NameIdModel parameter = systemParameterService.addParameter(name, RcdsUtil.extractAttributeType(code));
        return parameter.getId().toString();
    }

    @RequestMapping(value = "/admin/paramsupdate", method = RequestMethod.GET)
    public void updateParameter(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String paramId = request.getParameter("id");
        systemParameterService.updateParameterName(Long.parseLong(paramId), name, RcdsUtil.extractAttributeType(code));
    }

    @RequestMapping(value = "/admin/paramsremove", method = RequestMethod.GET)
    public void removeParameter(HttpServletRequest request) throws Exception {
        String paramId = request.getParameter("id");
        String code = request.getParameter("code");
        systemParameterService.setParameterActive(Long.parseLong(paramId), false, RcdsUtil.extractAttributeType(code));
    }

    @RequestMapping(value = "/admin/paramsrestore", method = RequestMethod.GET)
    public void restoreParameter(HttpServletRequest request) throws Exception {
        String paramId = request.getParameter("id");
        String code = request.getParameter("code");
        systemParameterService.setParameterActive(Long.parseLong(paramId), true, RcdsUtil.extractAttributeType(code));
    }
}
