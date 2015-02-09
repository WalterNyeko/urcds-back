<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.vehicleType" var="vehicleTypeLabel" />
<fmt:message key="crashForm.driverCasualtyLabel" var="driverCasualtyTypeLabel" />
<fmt:message key="crashForm.enterLicenseNumber" var="enterLicenseNumber" />
<fmt:message key="crashForm.enterDriverAge" var="enterDriverAge" />
<head>
<title><fmt:message key="vehicleForm.title" /></title>
<meta name="menue" content="CrashMenu" />
<script type="text/javascript">
	$( document ).ready(function() {
		$(".submit").click(function(){
		    //return validateFields();
            return true;
		});	
	});		
</script>
</head>
<div class="col-sm-3">
	<h3>
		<fmt:message key="vehicleForm.heading" />
	</h3>
</div>
<div class="col-sm-15">
	<form:form commandName="vehicle" method="post"
		action="/crashformvehicle" id="vehicleform" autocomplete="off"
		cssClass="well" onsubmit="return validateVehicle(this)">
		<form:hidden path="id" />
	    <form:hidden path="dateCreated"/>
	    <form:hidden path="number" />
	    <form:hidden path="driver.id" />
	    <form:hidden path="driver.dateCreated" />
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
				<tr>
					<td width="100%" valign="top">
						<table width="100%" class="crashform-gray">
							<tr>
								<th width="40%" style="border-right-width: medium;"><appfuse:label styleClass="control-label"
										key="crash.vehicle" /></th>
                                <th width="60%"><appfuse:label styleClass="control-label"
                                        key="crash.driver" /></th>
                            </tr>
							<tr>
								<td style="border-right-width: medium;">
									<table width="100%" class="crashform-blue" style="border: none;">
										<tr>
											<td colspan="2" style="border-style: none none solid none;">
												<appfuse:label styleClass="control-label" key="crashForm.vehicleType" />
												<input type="hidden" value="vehicleType.id" class="rb-helper" data-labelName="${vehicleTypeLabel}" />
											</td>
										</tr>
										<tr>
											<c:forEach var="vehicleType" items="${vehicleTypes}"
												varStatus="status">
                                                <c:set var="vehicleTypeRightBorderStyle" value="" />
                                                <c:choose>
                                                    <c:when  test="${ status.index % 2 == 0 and status.index > 0}">
                                                        </tr>
                                                        <tr>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="vehicleTypeRightBorderStyle" value=" border-right: none;" />
                                                    </c:otherwise>
                                                </c:choose>
											<td width="50%"
												style="border-left: none; border-bottom: none;${vehicleTypeRightBorderStyle}"><form:radiobutton
													path="vehicleType.id" value="${vehicleType.id}" />&nbsp;&nbsp;${vehicleType.name}
											</td>
											</c:forEach>
											
										</tr>
									</table>
								</td>
                                <td>
                                    <table width="100%" class="crashform-blue" style="border: none;">
                                        <tr>
                                            <td class="blue-header" style="border-style: none solid solid none;">
                                                <appfuse:label styleClass="control-label"
                                                               key="crashForm.licenseType" /></td>
                                            <td class="blue-header"  style="border-style: none solid solid none;">
                                                <appfuse:label styleClass="control-label"
                                                               key="crashForm.driverSex" /></td>
                                            <td class="blue-header"  style="border-style: none solid solid none;" width="100">
                                                <appfuse:label styleClass="control-label"
                                                               key="crashForm.driverAge" /></td>
                                            <td class="blue-header"  style="border-style: none solid solid none;">
                                                <appfuse:label styleClass="control-label"
                                                               key="crashForm.driverBeltUsed" /></td>
                                            <td class="blue-header"  style="border-style: none none solid none;">
                                                <appfuse:label styleClass="control-label"
                                                               key="crashForm.driverCasualty" /></td>
                                        </tr>
                                        <tr>
                                            <td style="border-style: none solid solid none;">
                                                <form:radiobutton path="driver.licenseValid" value="1" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.validLicense" /> <br/>
                                                <form:input cssClass="form-control" path="driver.licenseNumber" id="licenseNumber" placeholder="${enterLicenseNumber}" />
                                                <form:radiobutton path="driver.licenseValid" value="0" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.noValidLicense" /><br/>
                                                <form:radiobutton path="driver.licenseValid" value="" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.unknown" />
                                            </td>
                                            <td>
                                                <form:radiobutton path="driver.gender" value="M" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.male" /><br/>
                                                <form:radiobutton path="driver.gender" value="F" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.female" /><br/>
                                                <form:radiobutton path="driver.gender" value="" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.unknown" />
                                            </td>
                                            <td>
                                                <form:input cssClass="form-control" path="driver.age" id="age" placeholder="${enterDriverAge}" />
                                            </td>
                                            <td>
                                                <form:radiobutton path="driver.beltUsed" value="1" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.yes" /><br/>
                                                <form:radiobutton path="driver.beltUsed" value="0" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.no" /><br/>
                                                <form:radiobutton path="driver.beltUsed" value="" />&nbsp;
                                                <appfuse:label styleClass="form-label" key="rcds.unknown" />
                                            </td>
                                            <td style="border: none none solid none;">
                                                <c:forEach var="casualtyType"
                                                                       items="${casualtyTypes}">
                                                <form:radiobutton path="driver.casualtyType.id"
                                                                  value="${casualtyType.id}" />&nbsp;&nbsp;${casualtyType.name}<br />
                                            </c:forEach>
                                                <input type="hidden" value="driver.casualtyType.id" class="rb-helper" data-labelName="${driverCasualtyTypeLabel}" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="color: #000;" colspan="2"><appfuse:label
							styleClass="form-label" key="crashForm.driverIfHeavyOminbus" />:&nbsp;&nbsp;
						<form:input cssClass="form-control" path="companyName"
							id="companyName" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td width="50%"><a class="btn btn-default"
									href="/crashform2"> <i class="icon-ok"></i> <fmt:message
											key="button.cancel" />
								</a></td>
								<td width="50%" align="right"><input type="submit"
									class="btn btn-primary submit"
									value="<fmt:message key='button.save'/>"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</div>