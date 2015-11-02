<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.pedestrianOrPassengerCasualty" var="casualtyTypeLabel" />
<fmt:message key="crashForm.pedestrianOrPassengerClass" var="casualtyClassLabel" />
<fmt:message key="crashForm.enterDriverAge" var="enterDriverAge" />
<c:url value="/crashformcasualty" var="formUrl" />
<form:form commandName="casualty" method="post"
    action="${formUrl}" id="casualtyform" autocomplete="off">
    <table cellpadding="4" width="100%">
        <tr>
            <td>
                <table width="100%" class="crashform-gray">
                    <tr>
                        <th><appfuse:label styleClass="control-label"
                                key="crash.casualty" /></th>
                    </tr>
                    <tr>
                        <td width="100%">
                            <table width="100%" class="crashform-blue">
                                <tr>
                                    <th width="15%">
                                        <appfuse:label styleClass="control-label"
                                            key="crashForm.pedestrianOrPassengerCasualty" />
                                    </th>
                                    <th width="15%">
                                        <appfuse:label styleClass="control-label"
                                            key="crashForm.pedestrianOrPassengerSex" />
                                    </th>
                                    <th width="15%">
                                        <appfuse:label styleClass="control-label"
                                            key="crashForm.driverAge" />
                                    </th>
                                    <th width="25%">
                                        <appfuse:label styleClass="control-label"
                                            key="crashForm.pedestrianOrPassengerClass" />
                                    </th>
                                    <th width="15%">
                                        <appfuse:label styleClass="control-label"
                                            key="crashForm.passengerVehicleNo" />
                                    </th>
                                    <th width="15%">
                                        <appfuse:label styleClass="control-label"
                                            key="crashForm.passengerBeltUsed" />
                                    </th>
                                </tr>
                                <tr>
                                    <td>
                                         <form:hidden path="id" />
                                         <form:hidden path="dateCreated"/>
                                         <input type="hidden" value="casualtyType.id" class="rb-helper" data-labelName="${casualtyTypeLabel}" />
                                        <c:set var="ctIndex" value="${1}"/>
                                        <c:forEach var="casualtyType"
                                            items="${casualtyTypes}" varStatus="status">
                                            <c:if test="${casualtyType.id ne 4}">
                                                <form:radiobutton path="casualtyType.id" value="${casualtyType.id}" />&nbsp;&nbsp;
                                                <label for="casualtyType.id${ctIndex}" class="form-label">${casualtyType.name}</label><br />
                                                <c:set var="ctIndex" value="${ctIndex + 1}" />
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td><form:radiobutton path="gender" value="M" />&nbsp;
                                        <label for="gender1" class="form-label"><fmt:message key="rcds.male" /></label><br/>
                                        <form:radiobutton path="gender" value="F" />&nbsp;
                                        <label for="gender2" class="form-label"><fmt:message key="rcds.female" /></label><br/>
                                    </td>
                                    <td align="right">
                                        <form:input cssClass="form-control right-al int-val" path="age" id="age" placeholder="${enterDriverAge}" />
                                    </td>
                                    <td>
                                        <input type="hidden" value="casualtyClass.id" class="rb-helper" data-labelName="${casualtyClassLabel}" />
                                        <c:forEach var="casualtyClass"
                                            items="${casualtyClasses}">
                                            <form:radiobutton path="casualtyClass.id" value="${casualtyClass.id}" />&nbsp;&nbsp;
                                            <label for="casualtyClass.id${casualtyClass.id}" class="form-label">${casualtyClass.name}</label><br />
                                        </c:forEach></td>
                                    <td>
                                        <form:radiobutton path="vehicle.id" value="" />&nbsp;
                                        <label for="vehicle.id1" class="form-label"><fmt:message key="rcds.na" /></label><br/>
                                        <c:forEach var="vehicle" items="${vehicles}" varStatus="status">
                                            <form:radiobutton path="vehicle.id" value="${vehicle.id}" />&nbsp;&nbsp;
                                            <label for="vehicle.id${status.index + 2}" class="form-label"><fmt:message key="crash.vehicle"/> ${vehicle.number}</label><br/>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <form:radiobutton path="beltOrHelmetUsed" value="1" />&nbsp;
                                        <label for="beltOrHelmetUsed1" class="form-label"><fmt:message key="rcds.yes" /></label><br/>
                                        <form:radiobutton path="beltOrHelmetUsed" value="0" />&nbsp;
                                        <label for="beltOrHelmetUsed2" class="form-label"><fmt:message key="rcds.no" /></label><br/>
                                        <form:radiobutton path="beltOrHelmetUsed" value="" />&nbsp;
                                        <label for="beltOrHelmetUsed3" class="form-label"><fmt:message key="rcds.unknown" /></label><br/>
                                        <form:radiobutton path="beltOrHelmetUsed" value="" />&nbsp;
                                        <label for="beltOrHelmetUsed4" class="form-label"><fmt:message key="rcds.na" /></label><br/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form:form>