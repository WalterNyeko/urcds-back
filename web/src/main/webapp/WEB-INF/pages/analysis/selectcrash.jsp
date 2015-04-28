<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.policeStation" var="policeStationLabel" />

<form:form commandName="criteria" method="post" action="/analysiscrashselect"
    id="selectCrashForm" autocomplete="off">
    <table cellpadding="4" width="100%">
        <tr>
            <td width="50%" valign="top" style="padding-right: 8px;">
                <table width="100%">
                    <tr>
                        <td width="40%">
                            <appfuse:label styleClass="form-label"
                                           key="rcds.from" />
                        </td>
                        <td width="60%">
                            <table width="100%">
                                <tr>
                                    <td width="50%">
                                        <form:select path="startYear"
                                                     cssClass="form-control year-month-range" id="startYear">
                                            <form:option value="" selected="selected">
                                                <fmt:message key="rcds.year" />
                                            </form:option>
                                            <form:options items="${years}" itemValue="value"
                                                          itemLabel="label" />
                                        </form:select>
                                    </td>
                                    <td width="50%">
                                        <form:select path="startMonth"
                                                     cssClass="form-control year-month-range" id="startMonth">
                                            <form:option value="" selected="selected">
                                                <fmt:message key="rcds.month" />
                                            </form:option>
                                            <form:options items="${months}" itemValue="value"
                                                          itemLabel="label" />
                                        </form:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td width="40%">
                            <appfuse:label styleClass="form-label"
                                key="crashAnalysis.startDate" />
                        </td>
                        <td width="60%">
                            <input type="text" id="startDateString" name="startDateString" class="form-control dtpicker right-al"
                                   readonly="readonly" style="background-color: #FFFFFF; cursor: pointer;"/>
                        </td>
                    </tr>
                    <tr>
                        <td><appfuse:label styleClass="form-label"
                                key="crashForm.district" /></td>
                        <td><form:select path="district.id"
                                cssClass="form-control district-select">
                                <form:option value="">
                                    <fmt:message key="crashAnalysis.any" />
                                </form:option>
                                <form:options items="${districts}" itemValue="id"
                                    itemLabel="name" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td><appfuse:label styleClass="form-label"
                                           key="crashForm.crashSeverity" /></td>
                        <td>
                            <form:select path="crash.crashSeverity.id"
                                         cssClass="form-control">
                                <form:option value="">
                                    <fmt:message key="crashAnalysis.any" />
                                </form:option>
                                <form:options items="${crashSeverities}" itemValue="id"
                                              itemLabel="name" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td><appfuse:label styleClass="form-label"
                                           key="crashForm.mainCauseOfCrash" /></td>
                        <td>
                            <form:select path="crash.crashCause.id" cssClass="form-control">
                                <form:option value="">
                                    <fmt:message key="crashAnalysis.any" />
                                </form:option>
                                <form:options items="${crashCauses}" itemValue="id"
                                              itemLabel="name" />
                            </form:select>
                        </td>
                    </tr>
                </table>
            </td>
            <td width="50%" valign="top"  style="padding-left: 8px;">
                <table width="100%">
                    <tr>
                        <td width="40%">
                            <appfuse:label styleClass="form-label"
                                           key="rcds.to" />
                        </td>
                        <td width="60%">
                            <table width="100%">
                                <tr>
                                    <td width="50%">
                                        <form:select path="endYear"
                                                     cssClass="form-control year-month-range" id="endYear">
                                            <form:option value="" selected="selected">
                                                <fmt:message key="rcds.year" />
                                            </form:option>
                                            <form:options items="${years}" itemValue="value"
                                                          itemLabel="label" />
                                        </form:select>
                                    </td>
                                    <td width="50%">
                                        <form:select path="endMonth"
                                                     cssClass="form-control year-month-range" id="endMonth">
                                            <form:option value="" selected="selected">
                                                <fmt:message key="rcds.month" />
                                            </form:option>
                                            <form:options items="${months}" itemValue="value"
                                                          itemLabel="label" />
                                        </form:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr id="year-month-range-error" style="display: none; color: red; font-style: italic;">
                        <td colspan="2">

                        </td>
                    </tr>
                    <tr>
                        <td width="40%">
                            <appfuse:label styleClass="form-label"
                                           key="crashAnalysis.endDate" />
                        </td>
                        <td width="60%">
                            <input type="text" id="endDateString" name="endDateString" class="form-control dtpicker right-al"
                                   readonly="readonly" style="background-color: #FFFFFF; cursor: pointer;"/>
                        </td>
                    </tr>
                    <tr>
                        <td><appfuse:label styleClass="form-label"
                                           key="crashForm.policeStation" /></td>
                        <td>
                            <form:select path="crash.policeStation.id" cssClass="form-control req-val police-station-select" data-labelName="${policeStationLabel}">
                                <form:option value="">
                                    <fmt:message key="crashAnalysis.any" />
                                </form:option>
                                <form:options items="${policeStations}" itemValue="id"
                                              itemLabel="name" />
                            </form:select>
                            <input id="policeStationJson" value="${police_stations_json}" type="hidden"/>
                        </td>
                    </tr>
                    <tr>
                        <td><appfuse:label styleClass="form-label"
                                           key="crashForm.collisionType" /></td>
                        <td>
                            <form:select path="crash.collisionType.id" cssClass="form-control">
                                <form:option value="">
                                    <fmt:message key="crashAnalysis.any" />
                                </form:option>
                                <form:options items="${collisionTypes}" itemValue="id"
                                              itemLabel="name" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td><appfuse:label styleClass="form-label"
                                           key="crashForm.vehicleType" /></td>
                        <td>
                            <form:select path="vehicleType.id" cssClass="form-control">
                                <form:option value="">
                                    <fmt:message key="crashAnalysis.any" />
                                </form:option>
                                <form:options items="${vehicleTypes}" itemValue="id"
                                              itemLabel="name" />
                            </form:select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form:form>

<script type="text/javascript">
    $(document).ready(function() {
       $('.year-month-range').change(function() {
           validateYearMonthRange(displayYearMonthRangeError);
           showHideDateControls();
       });
       initDistrictSelectChange();
    });
</script>