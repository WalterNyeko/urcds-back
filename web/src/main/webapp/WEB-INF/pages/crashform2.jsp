<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashForm.title" /></title>
    <meta name="menue" content="CrashMenu" />
    <script type="text/javascript">
        initPopupFormLinks();
    </script>
</head>
<div class="col-sm-3">
	<h3>
		<fmt:message key="crashForm.heading" />
	</h3>
	<p>
		<appfuse:label styleClass="control-label" key="crashForm.tarNo" />
		: ${crash.tarNo}
	</p>
</div>
<div class="col-sm-15">
	<form:form commandName="crash" method="post" action="/crashformsubmit"
		id="crashform" autocomplete="off" cssClass="well"
		onsubmit="return validateCrash(this)">
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
				<c:if test="${crash.vehicles ne null }">
					<c:forEach var="vehicle" items="${crash.vehicles}"
						varStatus="status">
						<tr>
                            <td width="100%" valign="top">
                                <table width="100%" class="crashform-gray">
                                    <tr>
                                        <th><appfuse:label styleClass="control-label"
                                                           key="crash.vehicle" />&nbsp;${vehicle.number}</th>
                                        <th colspan="5"><appfuse:label styleClass="control-label"
                                                           key="crash.driver" />&nbsp;${vehicle.number}</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                    <tr>
                                        <td width="25%" class="blue-header"><appfuse:label
                                                styleClass="control-label" key="crashForm.vehicleType" /></td>
                                        <td width="20%" class="blue-header"><appfuse:label
                                                styleClass="control-label"
                                                key="crashForm.licenseType" /></td>
                                        <td width="10%" class="blue-header"><appfuse:label
                                                styleClass="control-label"
                                                key="crashForm.driverSex" /></td>
                                        <td width="10%" class="blue-header"><appfuse:label
                                                styleClass="control-label"
                                                key="crashForm.driverAge" /></td>
                                        <td width="10%" class="blue-header"><appfuse:label
                                                styleClass="control-label" key="crashForm.driverBeltUsed" /></td>
                                        <td width="15%" class="blue-header"><appfuse:label
                                                styleClass="control-label"
                                                key="crashForm.driverCasualty" /></td>
                                        <td width="10%" class="blue-header"><appfuse:label
                                                styleClass="control-label" key="rcds.actions" /></td>
                                    </tr>
                                    <tr>
                                        <td class="padd2">
                                            <label for="company-name" class="form-label">${vehicle.vehicleType.name}</label>
                                            <c:if test="${not empty vehicle.companyName}">
                                                <br/>
                                                <i id="company-name">
                                                    <b style="font-weight: bold"><appfuse:label styleClass="form-label" key="crashForm.companyName" />:</b>
                                                    &nbsp;
                                                    ${vehicle.companyName}
                                                </i>
                                            </c:if>
                                        </td>
                                        <td class="padd2">
                                            <c:choose>
                                                <c:when
                                                        test="${vehicle.driver ne null and vehicle.driver.licenseValid ne null and vehicle.driver.licenseValid eq true}">
                                                    <appfuse:label styleClass="form-label"
                                                                   key="rcds.validLicense" />
                                                    <br />
                                                    <i>
                                                        <b style="font-weight: bold"><appfuse:label styleClass="form-label" key="crash.licenseNumber" />:</b>
                                                        &nbsp;${vehicle.driver.licenseNumber}</i>
                                                </c:when>
                                                <c:when
                                                        test="${vehicle.driver ne null and vehicle.driver.licenseValid ne null and vehicle.driver.licenseValid eq false}">
                                                    <appfuse:label styleClass="form-label"
                                                                   key="rcds.noValidLicense" />
                                                </c:when>
                                                <c:otherwise>
                                                    <appfuse:label styleClass="form-label"
                                                                   key="rcds.unknown" />
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="padd2">
                                            <c:choose>
                                                <c:when
                                                        test="${vehicle.driver ne null and vehicle.driver.gender ne null and vehicle.driver.gender eq 'M'}">
                                                    <appfuse:label styleClass="form-label" key="rcds.male" />
                                                </c:when>
                                                <c:when
                                                        test="${vehicle.driver ne null and vehicle.driver.gender ne null and vehicle.driver.gender eq 'F'}">
                                                    <appfuse:label styleClass="form-label" key="rcds.female" />
                                                </c:when>
                                                <c:otherwise>
                                                    <appfuse:label styleClass="form-label"
                                                                   key="rcds.unknown" />
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="padd2" align="right">${vehicle.driver.age}</td>
                                        <td class="padd2">
                                            <c:choose>
                                                <c:when
                                                        test="${vehicle.driver ne null and vehicle.driver.beltUsed ne null and vehicle.driver.beltUsed eq true}">
                                                    <appfuse:label styleClass="form-label" key="rcds.yes" />
                                                </c:when>
                                                <c:when
                                                        test="${vehicle.driver ne null and vehicle.driver.beltUsed ne null and vehicle.driver.beltUsed eq false}">
                                                    <appfuse:label styleClass="form-label" key="rcds.no" />
                                                </c:when>
                                                <c:otherwise>
                                                    <appfuse:label styleClass="form-label"
                                                                   key="rcds.unknown" />
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="padd2">
                                            <c:if
                                                    test="${vehicle.driver ne null and vehicle.driver.casualtyType ne null }">
                                                ${vehicle.driver.casualtyType.name}
                                            </c:if>
                                        </td>
                                        <td class="padd2" align="center">
                                            <a href="/crashformvehicle?id=${vehicle.id}" class="popup-form">
                                                <i class="icon-edit"></i>
                                                <fmt:message key="button.edit" />
                                            </a>
                                            |
                                            <a href="/crashformvehicledelete?id=${vehicle.id}" onclick="return confirmMessage('<fmt:message key="rcds.confirmDelete" />');">
                                                <i class="icon-delete"></i>
                                                <fmt:message key="button.delete" />
                                            </a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td><a href="/crashformvehicle" class="popup-form"> <i class="icon-ok"></i> <fmt:message
								key="button.addVehicle" />
					</a></td>
				</tr>
				<c:if test="${crash.casualties ne null and not empty crash.casualties}">
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
					<tr>
						<td>
							<table width="100%" class="crashform-gray">
								<tr>
									<th colspan="8" width="100%"><appfuse:label styleClass="control-label"
											key="crash.pedestrianAndPassengerCasualties" /></th>
								</tr>
								<tr>
                                    <td class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="crashForm.victim" /></td>
                                    <td class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="crashForm.pedestrianOrPassengerCasualty" /></td>
                                    <td class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="crashForm.pedestrianOrPassengerSex" /></td>
                                    <td class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="crashForm.pedestrianOrPassengerAge" /></td>
                                    <td class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="crashForm.pedestrianOrPassengerClass" /></td>
                                    <td class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="crashForm.passengerVehicleNo" /></td>
                                    <td class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="crashForm.passengerBeltUsed" /></td>
                                    <td width="10%" class="blue-header"><appfuse:label styleClass="control-label"
                                                       key="rcds.actions" /></td>
							    </tr>
                                    <c:forEach var="casualty" items="${crash.casualties}"
                                        varStatus="status">
                                        <tr>
                                            <td class="padd2"><appfuse:label styleClass="form-label"
                                                    key="crash.person" />&nbsp;${status.index + 1}</td>
                                            <td class="padd2">${casualty.casualtyType.name}</td>
                                            <td class="padd2" align="center">${casualty.gender}</td>
                                            <td class="padd2" align="right">${casualty.age}</td>
                                            <td class="padd2">${casualty.casualtyClass.name}</td>
                                            <td class="padd2"><c:if
                                                    test="${casualty.vehicle ne null and casualty.vehicle.id ne null}">
                                                    <appfuse:label styleClass="form-label"
                                                        key="crash.vehicle" />&nbsp;${casualty.vehicle.number}
                                                </c:if></td>
                                            <td class="padd2"><c:choose>
                                                    <c:when test="${casualty.beltOrHelmetUsed ne null}">
                                                        ${casualty.beltOrHelmetUsed eq true ? "Yes" : "No"}
                                                    </c:when>
                                                    <c:otherwise>
                                                        Unknown
                                                    </c:otherwise>
                                                </c:choose></td>
                                            <td class="padd2" align="center">
                                                <a href="/crashformcasualty?id=${casualty.id}" class="popup-form"> <i
                                                    class="icon-edit"></i> <fmt:message key="button.edit" />
                                            </a> | <a href="/crashformcasualtydelete?id=${casualty.id}"
                                                onclick="return confirmMessage('<fmt:message key="rcds.confirmDelete" />');">
                                                    <i class="icon-delete"></i> <fmt:message
                                                        key="button.delete" />
                                            </a></td>
                                        </tr>
                                    </c:forEach>
							</table>
						</td>
					</tr>
				</c:if>
				<tr>
					<td><a href="/crashformcasualty" class="popup-form"> <i class="icon-ok"></i>
							<fmt:message key="button.addCasualty" />
					</a></td>
				</tr>
				<tr>
                    <td>
                        <table width="100%">
                            <tr>
                                <td width="50%">
                                    <a class="btn btn-default" href="/crashform?id=${crash.id}&back=true">
                                        <i class="icon-ok"></i>
                                        <fmt:message key="button.back" />
                                    </a>
                                </td>
                                <td width="50%" align="right">
                                    <a class="btn btn-primary" href="/crashformsubmit">
                                        <i class="icon-ok"></i>
                                        <c:choose>
                                            <c:when test="${crash.id eq 0 }">
                                                <fmt:message key="button.saveCrashData" />
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="button.updateCrashData" />
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </td>
				</tr>
			</table>
		</div>
	</form:form>
</div>
<div id="ajax-modal" class="modal container fade" tabindex="-1" style="display: none; margin-top: 40px;" aria-hidden="true"></div>