<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="casulatyForm.title" /></title>
<meta name="menue" content="CrashMenu" />
</head>
<div class="col-sm-2">
	<h2>
		<fmt:message key="casulatyForm.heading" />
	</h2>
</div>
<div class="col-sm-10">
	<form:form commandName="casualty" method="post"
		action="/crashformcasualty" id="casualtyform" autocomplete="off"
		cssClass="well" onsubmit="return validateCasualty(this)">
		<div class="col-sm-15">
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
											<th><appfuse:label styleClass="control-label"
													key="crashForm.pedestrianOrPassengerCasualty" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.pedestrianOrPassengerSex" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.pedestrianOrPassengerAge" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.pedestrianOrPassengerClass" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.passengerVehicleNo" /></th>
											<th><appfuse:label styleClass="control-label"
													key="crashForm.passengerBeltUsed" /></th>
										</tr>
										<tr>
											<td><c:forEach var="casualtyType"
													items="${casualtyTypes}">
													<c:if test="${casualtyType.id ne 4}">
														<form:radiobutton path="casualtyType.id"
															value="${casualtyType.id}" />&nbsp;&nbsp;${casualtyType.name}<br />
													</c:if>
												</c:forEach></td>
											<td><form:radiobutton path="gender" value="M" />
												<appfuse:label styleClass="form-label" key="rcds.male" /> <br />
												<form:radiobutton path="gender" value="F" />
												<appfuse:label styleClass="form-label" key="rcds.female" />
											</td>
											<td align="right"><appfuse:label styleClass="form-label"
													key="rcds.years" />:<br /> <form:input
													cssClass="form-control" path="age" id="age" /></td>
											<td><c:forEach var="casualtyClass"
													items="${casualtyClasses}">
													<form:radiobutton path="casualtyClass.id"
														value="${casualtyClass.id}" />&nbsp;&nbsp;${casualtyClass.name}<br />
												</c:forEach></td>
											<td><form:radiobutton path="vehicle.id" value="" />
												<appfuse:label styleClass="form-label" key="rcds.na" /><br />
												<c:forEach var="vehicle" items="${vehicles}">
													<form:radiobutton path="vehicle.id" value="${vehicle.id}" />&nbsp;&nbsp;<appfuse:label
														styleClass="form-label" key="crash.vehicle" />&nbsp;${vehicle.number}<br />
												</c:forEach></td>
											<td><form:radiobutton path="beltOrHelmetUsed" value="1" />
												<appfuse:label styleClass="form-label" key="rcds.yes" /><br />
												<form:radiobutton path="beltOrHelmetUsed" value="0" />
												<appfuse:label styleClass="form-label" key="rcds.no" /><br />
												<form:radiobutton path="beltOrHelmetUsed" value="-1" />
												<appfuse:label styleClass="form-label" key="rcds.unknown" /><br />
												<form:radiobutton path="beltOrHelmetUsed" value="" />
												<appfuse:label styleClass="form-label" key="rcds.na" /></td>
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
									class="btn btn-primary"
									value="<fmt:message key='button.save'/>"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</div>