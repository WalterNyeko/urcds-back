<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.tarNo" var="tarNoLabel" />
<fmt:message key="crashForm.policeStation" var="policeStationLabel" />
<head>
<title><fmt:message key="crashView.title" /></title>
<meta name="menu" content="CrashMenu" />
</head>

<div class="col-sm-10">
	<h2>
		<fmt:message key="crashView.title" />
	</h2>
	<div class="col-sm-15">
		<table cellpadding="4" width="100%">
			<tr>
				<td width="40%" valign="top">
					<table width="100%">
						<tr>
							<td width="50%"><appfuse:label
									styleClass="form-label boldText" key="crashForm.tarNo" /></td>
							<td width="50%" class="underlined">
								${crash.tarNo}
							</td>
						</tr>
						<tr>
							<td><appfuse:label styleClass="form-label boldText"
									key="crashForm.district" /></td>
							<td class="underlined">
								${crash.policeStation.district.name}
							</td>
						</tr>
						<tr>
							<td><appfuse:label styleClass="form-label boldText"
									key="crashForm.policeStation" /></td>
							<td class="underlined">
								${crash.policeStation.name}
							</td>
						</tr>
						<tr>
							<td><appfuse:label styleClass="form-label boldText"
									key="crashForm.townOrVillage" /></td>
							<td class="underlined">
								${crash.townOrVillage}
							</td>
						</tr>
						<tr>
							<td><appfuse:label styleClass="form-label boldText"
									key="crashForm.road" /></td>
							<td class="underlined">
								${crash.road}
							</td>
						</tr>
						<tr>
							<td><appfuse:label styleClass="form-label boldText"
									key="crashForm.roadNumber" /></td>
							<td class="underlined">
								${crash.roadNumber}
							</td>
						</tr>
						<tr>
							<td><appfuse:label styleClass="form-label boldText"
									key="crashForm.crashPlace" /></td>
							<td class="underlined">
								${crash.crashPlace}
							</td>
						</tr>
					</table>
				</td>
				<td width="60%" valign="top">
					<table width="100%" class="crashform-blue">
						<tr>
							<th width="50%"><appfuse:label styleClass="control-label"
									key="crashForm.crashDateTime" /></th>
						</tr>
						<tr>
							<td>${crash.crashDateTimeString}</td>
						</tr>
						<tr>
							<th colspan="2"><appfuse:label styleClass="control-label"
									key="crashForm.gpsCoordinates" /></th>
						</tr>
						<tr>
							<td><appfuse:label styleClass="form-label"
									key="crashForm.gpsCoordinates.latitude" /></td>
							<td width="50%"><appfuse:label styleClass="form-label"
									key="crashForm.gpsCoordinates.longitude" /></td>
						</tr>
						<tr>
							<td>${crash.gpsCoordinate.latitude}</td>
							<td>${crash.gpsCoordinate.longitude}</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="80%" class="crashform-blue">
						<tr>
							<th width="100%"><appfuse:label styleClass="control-label"
									key="crashForm.crashSeverity" /></th>
						</tr>
						<c:forEach var="crashSeverity" items="${crashSeverities}">
							<tr>
								<td><input type="radio" disabled ${crash.crashSeverity ne
									null and crash.crashSeverity.id eq
									crashSeverity.id ? "checked" : ""} value="${crashSeverity.id}" />
									&nbsp;&nbsp;${crashSeverity.name}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td>
					<table width="100%" class="crashform-blue">
						<tr>
							<th width="100%" colspan="2"><appfuse:label
									styleClass="control-label" key="crashForm.collisionType" /></th>
						</tr>
						<tr>
							<c:forEach var="collisionType" items="${collisionTypes}"
								varStatus="status">
								<c:if test="${ status.index % 2 == 0 and status.index > 0}">
						</tr>
						<tr>
							</c:if>
							<td width="50%"><input type="radio"
								disabled ${crash.collisionType ne null and
								crash.collisionType.id eq
								collisionType.id ? "checked" : ""} value="${collisionType.id}" />
								&nbsp;&nbsp;${collisionType.name}</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" class="crashform-blue">
						<tr>
							<th width="100%" colspan="3"><appfuse:label
									styleClass="control-label" key="crashForm.mainCauseOfCrash" />
							</th>
						</tr>
						<tr>
							<c:forEach var="crashCause" items="${crashCauses}"
								varStatus="status">
								<c:if test="${ status.index % 3 == 0 }">
						</tr>
						<tr>
							</c:if>
							<td width="33.3%"><input type="radio"
								disabled ${crash.mainCrashCause ne null and
								crash.mainCrashCause.id eq
								crashCause.id ? "checked" : ""} value="${crashCause.id}" />
								&nbsp;&nbsp;${crashCause.name}</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" class="crashform-blue">
						<tr>
							<th width="100%" colspan="3"><appfuse:label
									styleClass="control-label" key="crashForm.vehicleFailureType" />
							</th>
						</tr>
						<tr>
							<c:forEach var="vehicleFailureType"
								items="${vehicleFailureTypes}" varStatus="status">
								<c:if test="${ status.index % 3 == 0 }">
						</tr>
						<tr>
							</c:if>
							<td width="33.3%"><input type="radio"
								disabled ${crash.vehicleFailureType ne null and
								crash.vehicleFailureType.id eq
								vehicleFailureType.id ? "checked" : ""} value="${vehicleFailureType.id}" />
								&nbsp;&nbsp;${vehicleFailureType.name}</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" class="crashform-gray" cellpadding="0"
						cellspacing="0">
						<tr>
							<th width="100%" colspan="9"><appfuse:label
									styleClass="control-label" key="crashForm.roadEnvironment" />
							</th>
						</tr>
						<tr>
							<td width="22%">
								<table width="100%" class="crashform-blue" style="border: none;">
									<tr>
										<th
											style="border-top: none; border-right: none; border-left: none;">
											<appfuse:label styleClass="control-label"
												key="crashForm.weather" />
										</th>
									</tr>
									<c:forEach var="weather" items="${weathers}">
										<tr>
											<td
												style="border-right: none; border-left: none; border-bottom: none;">
												<input type="radio" disabled ${crash.weather ne null and
												crash.weather.id eq
												weather.id ? "checked" : ""} value="${weather.id}" />
												&nbsp;&nbsp;${weather.name}
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td width="4%">&nbsp;</td>
							<td width="22%" colspan="2">
								<table width="100%" class="crashform-blue" style="border: none;">
									<tr>
										<th
											style="border-top: none; border-right: none; border-left: none;">
											<appfuse:label styleClass="control-label"
												key="crashForm.surfaceCondition" />
										</th>
									</tr>
									<c:forEach var="surfaceCondition" items="${surfaceConditions}">
										<tr>
											<td
												style="border-right: none; border-left: none; border-bottom: none;">
												<input type="radio" disabled ${crash.surfaceCondition ne
												null and crash.surfaceCondition.id eq
												surfaceCondition.id ? "checked" : ""} value="${surfaceCondition.id}" />
												&nbsp;&nbsp;${surfaceCondition.name}
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td width="4%">&nbsp;</td>
							<td width="22%">
								<table width="100%" class="crashform-blue"
									style="border-top: none; border-right: none; border-left: none;">
									<tr>
										<th
											style="border-top: none; border-right: none; border-left: none;">
											<appfuse:label styleClass="control-label"
												key="crashForm.roadSurface" />
										</th>
									</tr>
									<c:forEach var="roadSurface" items="${roadSurfaces}">
										<tr>
											<td style="border-right: none; border-left: none;"><input
												type="radio" disabled ${crash.roadSurface ne null and
												crash.roadSurface.id eq
												roadSurface.id ? "checked" : ""} value="${roadSurface.id}" />
												&nbsp;&nbsp;${roadSurface.name}</td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td width="4%">&nbsp;</td>
							<td width="22%">
								<table width="100%" class="crashform-blue" style="border: none;">
									<tr>
										<th
											style="border-top: none; border-right: none; border-left: none;">
											<appfuse:label styleClass="control-label"
												key="crashForm.surfaceType" />
										</th>
									</tr>
									<c:forEach var="surfaceType" items="${surfaceTypes}">
										<tr>
											<td
												style="border-right: none; border-left: none; border-bottom: none;">
												<input type="radio" disabled ${crash.surfaceType ne null and
												crash.surfaceType.id eq
												surfaceType.id ? "checked" : ""} value="${surfaceType.id}" />
												&nbsp;&nbsp;${surfaceType.name}
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="9">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="3" style="border-right: none;">
								<table width="98%" class="crashform-blue"
									style="border-top: none; border-left: none; border-bottom: none;">
									<tr>
										<th colspan="2" style="border-top: none; border-left: none;">
											<appfuse:label styleClass="control-label"
												key="crashForm.roadwayCharacter" />
										</th>
									</tr>
									<tr>
										<c:forEach var="roadwayCharacter" items="${roadwayCharacters}"
											varStatus="status">
											<c:if test="${ status.index % 2 == 0 and status.index > 0}">
									</tr>
									<tr>
										</c:if>
										<td width="50%" style="border-left: none; border-bottom: none">
											<input type="radio" disabled ${crash.roadwayCharacter ne null
											and crash.roadwayCharacter.id eq
											roadwayCharacter.id ? "checked" : ""} value="${roadwayCharacter.id}" />
											&nbsp;&nbsp;${roadwayCharacter.name}
										</td>
										</c:forEach>
									</tr>
								</table>
							</td>
							<td colspan="6" align="right" style="border-left: none;">
								<table width="98%" class="crashform-blue"
									style="border-top: none; border-right: none; border-bottom: none;">
									<tr>
										<th colspan="3" style="border-top: none; border-right: none;">
											<appfuse:label styleClass="control-label"
												key="crashForm.junctionType" />
										</th>
									</tr>
									<tr>
										<c:forEach var="junctionType" items="${junctionTypes}"
											varStatus="status">
											<c:if test="${ status.index % 3 == 0 }">
									</tr>
									<tr>
										</c:if>
										<td width="33.3%"
											style="border-right: none; border-bottom: none"><input
											type="radio" disabled ${crash.junctionType ne null and
											crash.junctionType.id eq
											junctionType.id ? "checked" : ""} value="${junctionType.id}" />
											&nbsp;&nbsp;${junctionType.name}</td>
										</c:forEach>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="9">&nbsp;</td>
			</tr>			
			<tr>
				<td colspan="2">
					<table width="100%" cellpadding="0" cellspacing="0">
						
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>