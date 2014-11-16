<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.vehicleType" var="vehicleTypeLabel" />
<fmt:message key="crashForm.driverCasualtyLabel" var="driverCasualtyTypeLabel" />
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
<div class="col-sm-2">
	<h2>
		<fmt:message key="vehicleForm.heading" />
	</h2>
</div>
<div class="col-sm-10">
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
								<th><appfuse:label styleClass="control-label"
										key="crash.vehicle" /></th>
							</tr>
							<tr>
								<td width="100%">
									<table width="100%" class="crashform-blue">
										<tr>
											<td colspan="2">
												<appfuse:label styleClass="control-label" key="crashForm.vehicleType" />
												<input type="hidden" value="vehicleType.id" class="rb-helper" data-labelName="${vehicleTypeLabel}" />
											</td>
										</tr>
										<tr>
											<c:forEach var="vehicleType" items="${vehicleTypes}"
												varStatus="status">
												<c:if test="${ status.index % 2 == 0 and status.index > 0}">
										</tr>
										<tr>
											</c:if>
											<td width="50%"
												style="border-left: none; border-bottom: none"><form:radiobutton
													path="vehicleType.id" value="${vehicleType.id}" />&nbsp;&nbsp;${vehicleType.name}
											</td>
											</c:forEach>
											
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="color: #000;"><appfuse:label
							styleClass="form-label" key="crashForm.driverIfHeavyOminbus" />:&nbsp;&nbsp;
						<form:input cssClass="form-control" path="companyName"
							id="companyName" /></td>
				</tr>
				<tr>
					<td>
						<table width="100%" class="crashform-gray">
							<tr>
								<th><appfuse:label styleClass="control-label"
										key="crash.driver" />&nbsp;${vehicle.number}</th>
							</tr>
							<tr>
								<td width="100%">
									<table width="100%" class="crashform-blue">
										<tr>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.licenseType" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.driverSex" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.driverAge" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.driverBeltUsed" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.driverCasualty" /></th>
										</tr>
										<tr>
											<td><form:radiobutton path="driver.licenseValid"
													value="1" />
												&nbsp;<appfuse:label styleClass="form-label"
													key="rcds.validLicense" /></td>
											<td><form:radiobutton path="driver.gender" value="M" />
												&nbsp;<appfuse:label styleClass="form-label" key="rcds.male" /></td>
											<td><appfuse:label styleClass="form-label"
													key="rcds.years" /></td>
											<td><form:radiobutton path="driver.beltUsed" value="1" />
												&nbsp;<appfuse:label styleClass="form-label" key="rcds.yes" /></td>
											<td rowspan="4"><c:forEach var="casualtyType"
													items="${casualtyTypes}">
													<form:radiobutton path="driver.casualtyType.id"
														value="${casualtyType.id}" />&nbsp;&nbsp;${casualtyType.name}<br />
												</c:forEach>
												<input type="hidden" value="driver.casualtyType.id" class="rb-helper" data-labelName="${driverCasualtyTypeLabel}" />	
												</td>
										</tr>
										<tr>
											<td><appfuse:label styleClass="form-label"
													key="crash.licenseNumber" /><br /> <form:input
													cssClass="form-control" path="driver.licenseNumber"
													id="licenseNumber" /></td>
											<td><form:radiobutton path="driver.gender" value="F" />
												&nbsp;<appfuse:label styleClass="form-label" key="rcds.female" />
											</td>
											<td><form:input cssClass="form-control"
													path="driver.age" id="age" /></td>
											<td><form:radiobutton path="driver.beltUsed" value="0" />
												&nbsp;<appfuse:label styleClass="form-label" key="rcds.no" /></td>
										</tr>
										<tr>
											<td><form:radiobutton path="driver.licenseValid"
													value="0" />
												&nbsp;<appfuse:label styleClass="form-label"
													key="rcds.noValidLicense" /></td>
											<td><form:radiobutton path="driver.gender" value="" />
												&nbsp;<appfuse:label styleClass="form-label" key="rcds.unknown" />
											</td>
											<td>&nbsp;</td>
											<td><form:radiobutton path="driver.beltUsed" value="" />
												&nbsp;<appfuse:label styleClass="form-label" key="rcds.unknown" />
											</td>
										</tr>
										<tr>
											<td><form:radiobutton path="driver.licenseValid"
													value="" />
												&nbsp;<appfuse:label styleClass="form-label" key="rcds.unknown" />
											</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
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