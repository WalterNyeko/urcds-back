<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashForm.title" /></title>
    <meta name="menu" content="CrashMenu" />
    <script type="text/javascript" src="<c:url value='/scripts/crash-validator.js'/>"></script>
    <script type="text/javascript">
        $( document ).ready(function() {
            initVehicleAndCasualtyValidation();
        });
    </script>
</head>
<div class="col-sm-15">
<c:url value="/crashformsubmit" var="formUrl" />
	<form:form commandName="crash" method="post" action="${formUrl}"
		id="crashform" autocomplete="off" cssClass="well">
        <h3>
            <fmt:message key="crashForm.heading" />
        </h3>
        <appfuse:label styleClass="control-label" key="crashForm.tarNo" />: ${crash.tarNo}
        <form:hidden path="dirty" id="dirty" />
        <form:hidden path="crashSeverity.id" id="crashSeverityId" />
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
                                            <c:if test="${vehicle.driver ne null and vehicle.driver.casualtyType ne null }">
                                                ${vehicle.driver.casualtyType.name}
                                                <input type="hidden" data-name="casualtyType" value="${vehicle.driver.casualtyType.id}">
                                            </c:if>
                                        </td>
                                        <td class="padd2" align="center">
                                            <a href="" onclick="unbindBeforeUnload(); return loadVehicleForm({url: '<c:url value="/crashformvehicle?id=${vehicle.id}"/>'});">
                                                <i class="icon-edit"></i>
                                                <fmt:message key="button.edit" />
                                            </a>
                                            |
                                            <a href="<c:url value='/crashformvehicledelete?id=${vehicle.id}'/>"
                                               onclick="return confirmDialog({message : '<fmt:message key="rcds.confirmRemove" />', aLink : this});">
                                                <i class="icon-delete"></i>
                                                <fmt:message key="button.remove" />
                                            </a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td>
                        <a href="" onclick="unbindBeforeUnload(); return loadVehicleForm({url: '<c:url value="/crashformvehicle"/>'});">
                            <i class="icon-ok"></i>
                            <fmt:message key="button.addVehicle" />
					    </a>
                    </td>
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
                                            <td class="padd2">
                                                ${casualty.casualtyType.name}
                                                <input type="hidden" data-name="casualtyType" value="${casualty.casualtyType.id}">
                                            </td>
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
                                                <a href="" onclick="unbindBeforeUnload(); return loadCasualtyForm({url: '<c:url value="/crashformcasualty?id=${casualty.id}"/>'});">
                                                    <i class="icon-edit"></i>
                                                    <fmt:message key="button.edit" />
                                                </a> |
                                                <a href="<c:url value='/crashformcasualtydelete?id=${casualty.id}'/>"
                                                onclick="return confirmDialog({message : '<fmt:message key="rcds.confirmRemove" />', aLink : this});">
                                                    <i class="icon-delete"></i> <fmt:message
                                                        key="button.remove" />
                                            </a></td>
                                        </tr>
                                    </c:forEach>
							</table>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
                        <a href="" onclick="unbindBeforeUnload(); return loadCasualtyForm({url: '<c:url value="/crashformcasualty"/>'});">
                            <i class="icon-ok"></i>
							<fmt:message key="button.addCasualty" />
					    </a>
                    </td>
				</tr>
				<tr>
                    <td>
                        <table width="100%">
                            <tr>
                                <td width="50%">
                                    <a class="btn btn-default" href="<c:url value='/crashform?id=${crash.id}&back=true' />" onclick="unbindBeforeUnload()">
                                        <i class="icon-ok"></i>
                                        <fmt:message key="button.back" />
                                    </a>
                                </td>
                                <td width="50%" align="right">
                                    <a class="btn btn-primary" href="" onclick="return onSubmit()">
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
<div id="warning-modal" class="modal fade" tabindex="-1" style="display: none; margin-top: 40px; color: #000;" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
        <h4 class="modal-title" style="color: #800000; font-weight: bold;">Warning</h4>
    </div>
    <div class="modal-body">
        <div class="row">
            <div id="warning-content" class="col-md-15">
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn btn-default">Back</button>
        <button type="button" class="btn btn-primary" onclick="submitForm()">Proceed Anyway</button>
    </div>
</div>
<script type="text/javascript">
    function onSubmit() {
        if(validateCrashData()) {
            submitForm();
        }
        return false;
    }
    function submitForm() {
        unbindBeforeUnload();
        window.location.href = '<c:url value="/crashformsubmit" />';
    }
</script>