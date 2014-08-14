<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="crashForm.title" /></title>
<meta name="menue" content="CrashMenu" />
</head>
<div class="col-sm-2">
	<h2>
		<fmt:message key="crashForm.heading" />
	</h2>
	<p>
		<appfuse:label styleClass="control-label" key="crashForm.tarNo" />
		: ${crash.tarNo}
	</p>
</div>
<div class="col-sm-10">
	<form:form commandName="crash" method="post" action="/crashformsubmit"
		id="crashform" autocomplete="off" cssClass="well"
		onsubmit="return validateCrash(this)">
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
				<c:if test="${crash.vehicles ne null }">
					<c:forEach var="vehicle" items="${crash.vehicles}"
						varStatus="status">
						<tr>
							<td width="25%" valign="top" align="center"><c:if
									test="${status.index > 0}">
									<br />
								</c:if>
								<table width="100%" class="crashform-gray">
									<tr>
										<th><appfuse:label styleClass="control-label"
												key="crash.vehicle" />&nbsp;${vehicle.number}</th>
									</tr>
									<tr>
										<td width="100%">
											<table width="100%" class="crashform-blue">
												<tr>
													<th><appfuse:label styleClass="control-label"
															key="crashForm.vehicleType" /></th>
												</tr>
												<tr>
													<td width="100%"
														style="border-left: none; border-bottom: none">
														${vehicle.vehicleType.name}</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<a href="/crashformvehicle?id=${vehicle.id}">
									<i class="icon-edit"></i>
									<fmt:message key="button.edit" />
								</a>
								|
								<a href="/crashformvehicledelete?id=${vehicle.id}" onclick="return confirmMessage('<fmt:message key="rcds.confirmDelete" />');">
									<i class="icon-delete"></i>
									<fmt:message key="button.delete" />
								</a>
							</td>
							<td width="75%" valign="top"><c:if
									test="${status.index > 0}">
									<br />
								</c:if>
								<table width="100%" class="crashform-gray">
									<tr>
										<th><appfuse:label styleClass="control-label"
												key="crash.driver" />&nbsp;${vehicle.number}</th>
									</tr>
									<tr>
										<td width="100%">
											<table width="100%" class="crashform-blue">
												<tr>
													<th width="30%"><appfuse:label
															styleClass="control-label" key="crashForm.licenseType" /></th>
													<th width="15%"><appfuse:label
															styleClass="control-label" key="crashForm.driverSex" /></th>
													<th width="15%"><appfuse:label
															styleClass="control-label" key="crashForm.driverAge" /></th>
													<th width="15%"><appfuse:label
															styleClass="control-label" key="crashForm.driverBeltUsed" /></th>
													<th width="25%"><appfuse:label
															styleClass="control-label" key="crashForm.driverCasualty" /></th>
												</tr>
												<tr>
													<td><c:choose>
															<c:when
																test="${vehicle.driver ne null and vehicle.driver.licenseValid ne null and vehicle.driver.licenseValid eq true}">
																<appfuse:label styleClass="form-label"
																	key="rcds.validLicense" />
																<br />
																<i><appfuse:label styleClass="form-label"
																		key="crash.licenseNumber" />:&nbsp;&nbsp;${vehicle.driver.licenseNumber}</i>
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
														</c:choose></td>
													<td><c:choose>
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
														</c:choose></td>
													<td align="right">${vehicle.driver.age}</td>
													<td><c:choose>
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
														</c:choose></td>
													<td rowspan="4"><c:if
															test="${vehicle.driver ne null and vehicle.driver.casualtyType ne night }">
															${vehicle.driver.casualtyType.name}
														</c:if></td>
												</tr>
											</table>
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td colspan="2"
								style="color: #000; border-bottom: 1px solid #000;"><appfuse:label
									styleClass="form-label" key="crashForm.driverIfHeavyOminbus" />:&nbsp;&nbsp;
								${vehicle.companyName}</td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td><a href="/crashformvehicle"> <i class="icon-ok"></i> <fmt:message
								key="button.addVehicle" />
					</a></td>
				</tr>
				<c:if
					test="${crash.casualties ne null and not empty crash.casualties}">
					<tr>
						<td colspan="2">
							<table width="100%" class="crashform-gray">
								<tr>
									<th width="100%"><appfuse:label styleClass="control-label"
											key="crash.pedestrianAndPassengerCasualties" /></th>
								</tr>
								<tr>
									<td width="100%">
										<table width="100%" class="crashform-blue">
											<tr>
												<th><appfuse:label styleClass="control-label"
														key="crashForm.victim" /></th>
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
												<th><appfuse:label styleClass="control-label"
														key="rcds.actions" /></th>
											</tr>
											<c:forEach var="casualty" items="${crash.casualties}"
												varStatus="status">
												<tr>
													<td><appfuse:label styleClass="form-label"
															key="crash.person" />&nbsp;${status.index + 1}</td>
													<td>${casualty.casualtyType.name}</td>
													<td align="center">${casualty.gender}</td>
													<td align="right">${casualty.age}</td>
													<td>${casualty.casualtyClass.name}</td>
													<td><c:if test="${casualty.vehicle ne null and casualty.vehicle.id ne null}">
															<appfuse:label styleClass="form-label"
																key="crash.vehicle" />&nbsp;${casualty.vehicle.number}
														</c:if></td>
													<td>	
														<c:choose>
															<c:when test="${casualty.beltOrHelmetUsed ne null}">
																${casualty.beltOrHelmetUsed eq true ? "Yes" : "No"}
															</c:when>
															<c:otherwise>
																Unknown
															</c:otherwise>
														</c:choose>
													</td>
													<td align="center">
														<a href="/crashformcasualty?id=${casualty.id}">
															<i class="icon-edit"></i>
															<fmt:message key="button.edit" />
														</a>
														|
														<a href="/crashformcasualtydelete?id=${casualty.id}" onclick="return confirmMessage('<fmt:message key="rcds.confirmDelete" />');">
															<i class="icon-delete"></i>
															<fmt:message key="button.delete" />
														</a>
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
					<td><a href="/crashformcasualty"> <i class="icon-ok"></i>
							<fmt:message key="button.addCasualty" />
					</a></td>
					<td></td>
				</tr>
				<tr>
					<td>
						<a class="btn btn-default" href="/crashform?id=${crash.id}&back=true"> 
							<i class="icon-ok"></i>
							<fmt:message key="button.back" />
						</a>
					</td>
					<td align="right">						
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
		</div>
	</form:form>
</div>