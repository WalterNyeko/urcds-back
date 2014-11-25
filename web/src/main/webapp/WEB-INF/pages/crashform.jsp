<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.tarNo" var="tarNoLabel" />
<fmt:message key="crashForm.policeStation" var="policeStationLabel" />
<head>
    <title><fmt:message key="crashForm.title" /></title>
    <meta name="menu" content="CrashMenu" />
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdGBHIqR--XabhAy6UddDj4toKlEyJzAA">
    </script>
    <script type="text/javascript">
        $( document ).ready(function() {
            $(".submit").click(function(){
                return validateFields();
            });
            loadGpsCoordinates();
            loadCrashTime();
            generateCoordDegrees();
        });
    </script>
</head>
<div class="col-sm-2">
	<h2>
		<fmt:message key="crashForm.heading" />
	</h2>
</div>
<div class="col-sm-10">
	<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
	<form:form commandName="crash" method="post" action="/crashform2"
		id="crashForm" autocomplete="off" cssClass="well">
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
				<tr>
					<td width="40%" valign="top">
						<table width="100%" style="border-bottom: solid 1px black;">
							<tr>
								<td width="40%"><appfuse:label styleClass="form-label"
										key="crashForm.tarNo" /></td>
								<td width="60%">
									<form:input cssClass="form-control req-val" path="tarNo" id="tarNo" data-labelName="${tarNoLabel}"
										autofocus="true" /><form:hidden path="id" />
									</td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.district" /></td>
								<td><form:select path="policeStation.district"
										cssClass="form-control">
										<form:option value="">
											<fmt:message key="rcds.pleaseSelect" />
										</form:option>
										<form:options items="${districts}" itemValue="id"
											itemLabel="name" />
									</form:select>
								</td>
							</tr>
                            <tr>
                                <td><appfuse:label styleClass="form-label"
                                                   key="crashForm.policeStation" /></td>
                                <td>
                                    <form:select path="policeStation.id" cssClass="form-control req-val" data-labelName="${policeStationLabel}">
                                        <form:option value="">
                                            <fmt:message key="rcds.pleaseSelect" />
                                        </form:option>
                                        <form:options items="${policeStations}" itemValue="id"
                                                      itemLabel="name" />
                                    </form:select>
                                </td>
                            </tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.townOrVillage" /></td>
								<td>
									<form:input cssClass="form-control" path="townOrVillage"
											id="townOrVillage" />
								</td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.road" /></td>
								<td>
									<form:input cssClass="form-control" path="road" id="road" />
								</td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.roadNumber" /></td>
								<td>
                                        <form:input cssClass="form-control" path="roadNumber"
											id="roadNumber" />
								</td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.crashPlace" /></td>
								<td>
									<form:input cssClass="form-control" path="crashPlace"
											id="crashPlace" />
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
								<td>
									<input type="text" id="crashDateTimeString" name="crashDateTimeString" class="form-control dtpicker right-al" value="${crash.crashDateTimeString}"
                                           readonly="readonly" style="background-color: #FFFFFF; cursor: pointer;"/>
                                    <input type="text" id="crashTime" name="crashTime" class="form-control right-al" placeholder="Enter time in 24hr" onblur="defineCrashTime();"/>
								</td>
							</tr>
							<tr>
								<th colspan="2"><appfuse:label styleClass="control-label"
										key="crashForm.gpsCoordinates" /></th>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.gpsCoordinates.latitude" /></td>
								<td width="50%"><appfuse:label styleClass="form-label"
										key="crashForm.gpsCoordinates.longitude" />
                                    &nbsp;
                                    <img id="gMaps" src="/images/gglMap.png" alt="<fmt:message key='maps.viewInGoogleMaps'/>" title="<fmt:message key='maps.viewInGoogleMaps'/>" width="20"
                                             style="cursor: pointer;" onclick="javascript:loadInGoogleMaps();"/>
                                </td>
							</tr>
							<tr>
								<td>
                                    <table cellpadding="0" cellspacing="0" class="innerTable" width="100%">
                                        <tr>
                                            <td style="min-width: 50px;" id="tdLat" data-lat-val="">
                                                <select id="latLetter" class="form-control" onchange="defineGpsCoord('lat')">
                                                    <option value="N">N</option>
                                                    <option value="S">S</option>
                                                </select>
                                            </td>
                                            <td>
                                                <input id="latDegs" type="text" class="form-control" onblur="defineGpsCoord('lat')" />
                                            </td>
                                            <td>
                                                &deg;
                                            </td>
                                            <td>
                                                <input id="latMins" type="text" class="form-control" onblur="defineGpsCoord('lat')" />
                                            </td>
                                            <td>
                                                '
                                            </td>
                                        </tr>
                                    </table>
									<form:hidden path="latitude" id="latitude" />
								</td>
								<td>
                                    <table cellpadding="0" cellspacing="0" class="innerTable" width="100%">
                                        <tr>
                                            <td style="min-width: 50px;" id="tdLon" data-lon-val="">
                                                <select id="lonLetter" class="form-control" disabled style="background-color: #FFFFFF; cursor: default;" onchange="defineGpsCoord('lon')">
                                                    <option value="E">E</option>
                                                    <option value="W">W</option>
                                                </select>
                                            </td>
                                            <td>
                                                <input id="lonDegs" type="text" class="form-control" onblur="defineGpsCoord('lon')" />
                                            </td>
                                            <td>
                                                &deg;
                                            </td>
                                            <td>
                                                <input id="lonMins" type="text" class="form-control" onblur="defineGpsCoord('lon')" />
                                            </td>
                                            <td>
                                                '
                                            </td>
                                        </tr>
                                    </table>
                                    <form:hidden path="longitude" id="longitude" />
								</td>
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
									<td><form:radiobutton path="crashSeverity.id"
											value="${crashSeverity.id}" />&nbsp;&nbsp;${crashSeverity.name}
									</td>
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
								<td width="50%"><form:radiobutton path="collisionType.id"
										value="${collisionType.id}" />&nbsp;&nbsp;${collisionType.name}
								</td>
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
								<td width="33.3%"><form:radiobutton
										path="mainCrashCause.id" value="${crashCause.id}" />&nbsp;&nbsp;${crashCause.name}
								</td>
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
								<td width="33.3%"><form:radiobutton
										path="vehicleFailureType.id" value="${vehicleFailureType.id}" />&nbsp;&nbsp;${vehicleFailureType.name}
								</td>
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
									<table width="100%" class="crashform-blue"
										style="border: none;">
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
													<form:radiobutton path="weather.id" value="${weather.id}" />&nbsp;&nbsp;${weather.name}
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
								<td width="4%">&nbsp;</td>
								<td width="22%" colspan="2">
									<table width="100%" class="crashform-blue"
										style="border: none;">
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
													<form:radiobutton path="surfaceCondition.id"
														value="${surfaceCondition.id}" />&nbsp;&nbsp;${surfaceCondition.name}
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
												<td style="border-right: none; border-left: none;"><form:radiobutton
														path="roadSurface.id" value="${roadSurface.id}" />&nbsp;&nbsp;${roadSurface.name}
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
								<td width="4%">&nbsp;</td>
								<td width="22%">
									<table width="100%" class="crashform-blue"
										style="border: none;">
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
													<form:radiobutton path="surfaceType.id"
														value="${surfaceType.id}" />&nbsp;&nbsp;${surfaceType.name}
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
											<c:forEach var="roadwayCharacter"
												items="${roadwayCharacters}" varStatus="status">
												<c:if test="${ status.index % 2 == 0 and status.index > 0}">
										</tr>
										<tr>
											</c:if>
											<td width="50%"
												style="border-left: none; border-bottom: none"><form:radiobutton
													path="roadwayCharacter.id" value="${roadwayCharacter.id}" />&nbsp;&nbsp;${roadwayCharacter.name}
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
												style="border-right: none; border-bottom: none"><form:radiobutton
													path="junctionType.id" value="${junctionType.id}" />&nbsp;&nbsp;${junctionType.name}
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
					<td>
						<a class="btn btn-default" href="<c:url value='/crashes'/>" onclick="bCancel=true;"> 
							<i class="icon-ok"></i>
							<fmt:message key="button.cancel" />
						</a>
					</td>
					<td align="right"><input type="submit" class="btn btn-primary submit"
						value="<fmt:message key='button.next'/>" onclick="bCancel=false; return validateGpsCoordinates();"></td>
				</tr>
			</table>
		</div>
	</form:form>
</div>