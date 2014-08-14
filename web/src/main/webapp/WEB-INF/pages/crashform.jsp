<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="crashForm.title" /></title>
<meta name="menu" content="CrashMenu" />
</head>
<div class="col-sm-2">
	<h2>
		<fmt:message key="crashForm.heading" />
	</h2>
</div>
<div class="col-sm-10">
	<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
	<form:form commandName="crash" method="post" action="/crashform2"
		id="crashform" autocomplete="off" cssClass="well"
		onsubmit="return validateCrash(this)">
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
				<tr>
					<td width="40%" valign="top">
						<table width="100%" style="border-bottom: solid 1px black;">
							<tr>
								<td width="50%"><appfuse:label styleClass="form-label"
										key="crashForm.tarNo" /></td>
								<td width="50%"><spring:bind path="crash.tarNo">
										<div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
									</spring:bind> <form:input cssClass="form-control" path="tarNo" id="tarNo"
										autofocus="true" /> <form:errors path="tarNo" cssClass="help-block" /> <form:hidden path="id" />
									</div></td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.policeStation" /></td>
								<td><spring:bind path="crash.policeStation">
										<div
											class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
									</spring:bind> <form:select path="policeStation.id" cssClass="form-control">
										<form:option value="">
											<fmt:message key="rcds.pleaseSelect" />
										</form:option>
										<form:options items="${policeStations}" itemValue="id"
											itemLabel="name" />
									</form:select> <form:errors path="policeStation" cssClass="help-block" />
									</div></td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.district" /></td>
								<td><spring:bind path="crash.policeStation.district">
										<div
											class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
									</spring:bind> <form:select path="policeStation.district"
										cssClass="form-control">
										<form:option value="">
											<fmt:message key="rcds.pleaseSelect" />
										</form:option>
										<form:options items="${districts}" itemValue="id"
											itemLabel="name" />
									</form:select> <form:errors path="policeStation.district"
										cssClass="help-block" />
									</div></td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.townOrVillage" /></td>
								<td>
									<div
										class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
										<form:input cssClass="form-control" path="townOrVillage"
											id="townOrVillage" />
									</div>
								</td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.road" /></td>
								<td>
									<div
										class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
										<form:input cssClass="form-control" path="road" id="road" />
									</div>
								</td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.roadNumber" /></td>
								<td>
									<div
										class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
										<form:input cssClass="form-control" path="roadNumber"
											id="roadNumber" />
									</div>
								</td>
							</tr>
							<tr>
								<td><appfuse:label styleClass="form-label"
										key="crashForm.crashPlace" /></td>
								<td>
									<div
										class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
										<form:input cssClass="form-control" path="crashPlace"
											id="crashPlace" />
									</div>
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
									<%-- <spring:bind path="crash.crashDateTimeString">
										<div
											class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
									</spring:bind> <form:input cssClass="form-control" path="crashDateTimeString"
										id="crashDateTime" autofocus="true" /> <form:errors
										path="crashDateTimeString" cssClass="help-block" />
									</div> --%>
									<div id="datetimepicker1" class="input-group date">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td>
													<input type="text" id="crashDateTimeString" name="crashDateTimeString" class="form-control" value="${crash.crashDateTimeString}"/>
												</td>
												<td>
													<span class="input-group-addon">
														<span class="glyphicon glyphicon-calendar"></span>
                    								</span>											
												</td>
											</tr>
										</table>
									</div>
									<script type="text/javascript">
									  $(function() {
									    $('#datetimepicker1').datetimepicker({
									    	format: "dd/mm/yyyy hh:mm"									    	
									    });
									  });
									</script>
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
										key="crashForm.gpsCoordinates.longitude" /></td>
							</tr>
							<tr>
								<td>
									<div class="col-sm-15 form-group">
										<form:input cssClass="form-control"
											path="gpsCoordinate.latitude" id="gpsCoordinate.latitude" />
									</div>
								</td>
								<td>
									<div class="col-sm-15 form-group">
										<form:input cssClass="form-control"
											path="gpsCoordinate.longitude" id="gpsCoordinate.longitude" />
									</div>
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
					<td align="right"><input type="submit" class="btn btn-primary"
						value="<fmt:message key='button.next'/>" onclick="bCancel=false;"></td>
				</tr>
			</table>
		</div>
	</form:form>
</div>
<v:javascript formName="crash" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>