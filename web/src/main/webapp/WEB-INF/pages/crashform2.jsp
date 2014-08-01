<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="crashForm.title"/></title>
	<meta name="menue" content="CrashMenu"/>
</head>
<div class="col-sm-2">
	<h2><fmt:message key="crashForm.heading"/></h2>
</div>
<div class="col-sm-10">
	<form:form commandName="crash" method="post" action="/crashformsubmit" id="crashform" autocomplete="off" cssClass="well" onsubmit="return validateCrash(this)">
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
				<c:if test="${crash.vehicles ne null }">				
					<c:forEach var="vehicle" items="${crash.vehicles}" varStatus="status">
						<tr>
							<td width="25%" valign="top">
								<table width="100%" class="crashform-gray">
									<tr>
										<th>
											<appfuse:label styleClass="form-label" key="crash.vehicle"/>&nbsp; ${vehicle.number}
										</th>
									</tr>
									<tr>
										<td width="100%">
											<table width="100%" class="crashform-blue">
												<tr>
													<td colspan="2">
														<appfuse:label styleClass="form-label" key="crashForm.vehicleType"/>
													</td>
												</tr>
												<tr>
													<td width="50%" style="border-left: none; border-bottom: none">
														${vehicle.vehicleType.name} 
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="75%" valign="top">
								<table width="100%" class="crashform-gray">
									<tr>
										<th>
											<appfuse:label styleClass="form-label" key="crash.driver"/>&nbsp;${vehicle.number}
										</th>
									</tr>
									<tr>
										<td width="100%">
											<table width="100%" class="crashform-blue">
												<tr>
													<th width="30%"><appfuse:label styleClass="form-label" key="crashForm.licenseType"/></th>
													<th width="15%"><appfuse:label styleClass="form-label" key="crashForm.driverSex"/></th>
													<th width="15%"><appfuse:label styleClass="form-label" key="crashForm.driverAge"/></th>
													<th width="15%"><appfuse:label styleClass="form-label" key="crashForm.driverBeltUsed"/></th>
													<th width="25%"><appfuse:label styleClass="form-label" key="crashForm.driverCasualty"/></th>
												</tr>
												<tr>
													<td>
														<input type="radio" name="licenseType" ${vehicle.driver ne null and vehicle.driver.licenseValid ne null and vehicle.driver.licenseValid eq true ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.validLicense"/>
													</td>
													<td>
														<input type="radio" name="gender" ${vehicle.driver ne null and vehicle.driver.gender ne null and vehicle.driver.gender eq "M" ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.male"/>
													</td>
													<td>
														<appfuse:label styleClass="form-label" key="rcds.years"/>
													</td>
													<td>
														<input type="radio" name="beltUsed" ${vehicle.driver ne null and vehicle.driver.beltUsed ne null and vehicle.driver.beltUsed eq true ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.yes"/>
													</td>
													<td rowspan="4">
														<c:forEach var="casualtyType" items="${casualtyTypes}">
															<form:radiobutton path="vehicles[${status.index}].driver.casualtyType.id" value="${casualtyType.id}"/>&nbsp;&nbsp;${casualtyType.name}<br/>
														</c:forEach>
													</td>
												</tr>
												<tr>
													<td>
														<form:input cssClass="form-control" path="vehicles[${status.index}].driver.licenseNumber" id="licenseNumber"/>
													</td>
													<td>
														<input type="radio" name="gender" ${vehicle.driver ne null and vehicle.driver.gender ne null and vehicle.driver.gender eq "F" ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.female"/>
													</td>
													<td>
														<form:input cssClass="form-control" path="vehicles[${status.index}].driver.age" id="age"/>
													</td>
													<td>
														<input type="radio" name="beltUsed" ${vehicle.driver ne null and vehicle.driver.beltUsed ne null and vehicle.driver.beltUsed eq false ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.no"/>
													</td>
												</tr>
												<tr>
													<td>
														<input type="radio" name="licenseType" ${vehicle.driver ne null and vehicle.driver.licenseValid ne null and vehicle.driver.licenseValid eq false ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.noValidLicense"/>
													</td>
													<td>
														<input type="radio" name="gender" ${vehicle.driver ne null and vehicle.driver.gender eq null ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.unknown"/>
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														<input type="radio" name="beltUsed" ${vehicle.driver ne null and vehicle.driver.beltUsed eq null ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.unknown"/>
													</td>
												</tr>
												<tr>
													<td>
														<input type="radio" name="licenseType" ${vehicle.driver ne null and vehicle.driver.licenseValid eq null ? "checked" : ""}/><appfuse:label styleClass="form-label" key="rcds.unknown"/>
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
							<td colspan="2">
								<appfuse:label styleClass="form-label" key="crashForm.driverIfHeavyOminbus"/>:&nbsp;&nbsp;
								${vehicle.companyName}
							</td>
						</tr>						
					</c:forEach>
				</c:if>
				<tr>
					<td>
						<a class="btn btn-primary" href="/crashformvehicle">
					        <i class="icon-ok"></i> 
					        <fmt:message key="button.addVehicle"/>
				        </a>
					</td>
				</tr>
				<c:if test="${crash.casualties ne null}">					
					<tr>
						<td colspan="2">
							<table width="100%" class="crashform-gray">
								<tr>
									<th width="100%">
										<appfuse:label styleClass="form-label" key="crash.pedestrianAndPassengerCasualties"/>
									</th>
								</tr>
								<tr>
									<td width="100%">
										<table width="100%" class="crashform-blue">
											<tr>
												<th>&nbsp;</th>
												<th><appfuse:label styleClass="form-label" key="crashForm.pedestrianOrPassengerCasualty"/></th>
												<th><appfuse:label styleClass="form-label" key="crashForm.pedestrianOrPassengerSex"/></th>
												<th><appfuse:label styleClass="form-label" key="crashForm.pedestrianOrPassengerAge"/></th>
												<th><appfuse:label styleClass="form-label" key="crashForm.pedestrianOrPassengerClass"/></th>
												<th><appfuse:label styleClass="form-label" key="crashForm.passengerVehicleNo"/></th>
												<th><appfuse:label styleClass="form-label" key="crashForm.passengerBeltUsed"/></th>
											</tr>
											<c:forEach var="casualty" items="${crash.casualties}" varStatus="status">
												<tr>
													<td>
														<appfuse:label styleClass="form-label" key="crash.person"/>&nbsp;${status.index + 1}
													</td>
													<td>
														${casualty.casualtyType.name}
													</td>
													<td>
														${casualty.gender}
													</td>
													<td align="right">
														${casualty.age}
													</td>
													<td>
														${casualty.casualtyClass.name}
													</td>
													<td>
														<c:if test="${casualty.vehicle ne null}">
															${casualty.vehicle.number}
														</c:if>														
													</td>
													<td>
														<c:if test="${casualty.beltOrHelmetUsed ne null}">
															${casualty.beltOrHelmetUsed eq true ? "Yes" : "No"}
														</c:if>														
													</td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<a class="btn btn-primary" href="/crashformcasualty">
					        <i class="icon-ok"></i> 
					        <fmt:message key="button.addCasualty"/>
				        </a>
					</td>
					<td align="right">
						<a class="btn btn-primary" href="/crashformsubmit">
					        <i class="icon-ok"></i> 
					        <fmt:message key="button.saveCrashData"/>
				        </a>
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</div>