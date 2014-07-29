<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="crashForm.title"/></title>
	<meta name="menue" content="CrashMenu"/>
</head>
<div class="col-sm-2">
	<h2><fmt:message key="crashForm.heading"/></h2>
</div>
<div class="col-sm-10">
	<form:form commandName="crash" method="post" action="crashform" id="crashform" autocomplete="off" cssClass="well" onsubmit="return validateCrash(this)">
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
				<tr>
					<td width="40%" valign="top">
						<table width="100%" style="border-bottom: solid 1px black;">
							<tr>
								<td width="50%">
									<appfuse:label styleClass="form-label" key="crashForm.tarNo"/>
								</td>
								<td width="50%">
									<spring:bind path="crash.tarNo">
						            <div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						            </spring:bind>			                
						                <form:input cssClass="form-control" path="tarNo" id="tarNo" autofocus="true"/>
						                <form:errors path="tarNo" cssClass="help-block"/>
						            </div>
								</td>
							</tr>
							<tr>
								<td>
									<appfuse:label styleClass="form-label" key="crashForm.policeStation"/>
								</td>
								<td>
									<spring:bind path="crash.policeStation">
						            <div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						            </spring:bind>
						               	<form:select path="policeStation">
						               		<form:option value=""><fmt:message key="rcds.pleaseSelect"/></form:option>
						               		<form:options items="${policeStations}" itemValue="id" itemLabel="name"/>
						               	</form:select>           
						                <form:errors path="policeStation" cssClass="help-block"/>
						            </div>
								</td>
							</tr>
							<tr>
								<td>
									<appfuse:label styleClass="form-label" key="crashForm.district"/>
								</td>
								<td>
									<spring:bind path="crash.policeStation.district">
						            <div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						            </spring:bind>			                
						                	<form:select path="policeStation.district">
							               		<form:option value=""><fmt:message key="rcds.pleaseSelect"/></form:option>
							               		<form:options items="${districts}" itemValue="id" itemLabel="name"/>
							               	</form:select>           
						                <form:errors path="policeStation.district" cssClass="help-block"/>
						            </div>
								</td>
							</tr>
							<tr>
								<td>
									<appfuse:label styleClass="form-label" key="crashForm.townOrVillage"/>
								</td>
								<td>
									<div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						            	<form:input cssClass="form-control" path="townOrVillage" id="townOrVillage"/>
						            </div>
								</td>
							</tr>
							<tr>
								<td>
									<appfuse:label styleClass="form-label" key="crashForm.road"/>
								</td>
								<td>
									<div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						            	<form:input cssClass="form-control" path="road" id="road"/>
						            </div>
								</td>
							</tr>
							<tr>
								<td>
									<appfuse:label styleClass="form-label" key="crashForm.roadNumber"/>
								</td>
								<td>
									<div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						            	<form:input cssClass="form-control" path="roadNumber" id="roadNumber"/>
						            </div>
								</td>
							</tr>
							<tr>
								<td>
									<appfuse:label styleClass="form-label" key="crashForm.crashPlace"/>
								</td>
								<td>
									<div class="col-sm-15 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						            	<form:input cssClass="form-control" path="crashPlace" id="crashPlace"/>
						            </div>
								</td>
							</tr>
						</table>
					</td>
					<td width="60%" valign="top">
						<table width="100%" class="crashform-blue">
							<tr>
								<th width="50%">
									<appfuse:label styleClass="control-label" key="crashForm.crashDateTime"/>
								</th>
							</tr>
							<tr>
								<td>
									<spring:bind path="crash.crashDateTime">
							        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
							        </spring:bind>							            
							            <form:input cssClass="form-control" path="crashDateTime" id="crashDateTime" autofocus="true"/>
							            <form:errors path="crashDateTime" cssClass="help-block"/>
							        </div>
								</td>
							</tr>
							<tr>
								<th colspan="2">
									<appfuse:label styleClass="control-label" key="crashForm.gpsCoordinates"/>
								</th>
							</tr>
							<tr>
								<td>
									<appfuse:label styleClass="form-label" key="crashForm.gpsCoordinates.latitude"/>
								</td>
								<td width="50%">
									<appfuse:label styleClass="form-label" key="crashForm.gpsCoordinates.longitude"/>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-sm-15 form-group">
						            	<form:input cssClass="form-control" path="gpsCoordinate.latitude" id="gpsCoordinate.latitude"/>
						            </div>
								</td>
								<td>
									<div class="col-sm-15 form-group">
						            	<form:input cssClass="form-control" path="gpsCoordinate.longitude" id="gpsCoordinate.longitude"/>
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
								<th width="100%">
									<appfuse:label styleClass="control-label" key="crashForm.crashSeverity"/>
								</th>
							</tr>
							<c:forEach var="crashSeverity" items="${crashSeverities}">
								<tr>
									<td>
										<form:radiobutton path="crashSeverity" value="${crashSeverity.id}"/>&nbsp;&nbsp;${crashSeverity.name} 
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td>
						<table width="100%" class="crashform-blue">
							<tr>
								<th width="100%" colspan="2">
									<appfuse:label styleClass="control-label" key="crashForm.collisionType"/>
								</th>
							</tr>
							<tr>
								<c:forEach var="collisionType" items="${collisionTypes}" varStatus="status">
									<c:if test="${ status.index % 2 == 0 and status.index > 0}">
										</tr><tr>
									</c:if>
									<td width="50%">
										<form:radiobutton path="collisionType" value="${collisionType.id}"/>&nbsp;&nbsp;${collisionType.name} 
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
								<th width="100%" colspan="3">
									<appfuse:label styleClass="control-label" key="crashForm.mainCauseOfCrash"/>
								</th>
							</tr>
							<tr>
								<c:forEach var="crashCause" items="${crashCauses}" varStatus="status">
									<c:if test="${ status.index % 3 == 0 }">
										</tr><tr>
									</c:if>
									<td width="33.3%">
										<form:radiobutton path="mainCrashCause" value="${crashCause.id}"/>&nbsp;&nbsp;${crashCause.name} 
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
								<th width="100%" colspan="3">
									<appfuse:label styleClass="control-label" key="crashForm.vehicleFailureType"/>
								</th>
							</tr>
							<tr>
								<c:forEach var="vehicleFailureType" items="${vehicleFailureTypes}" varStatus="status">
									<c:if test="${ status.index % 3 == 0 }">
										</tr><tr>
									</c:if>
									<td width="33.3%">
										<form:radiobutton path="vehicleFailureType" value="${vehicleFailureType.id}"/>&nbsp;&nbsp;${vehicleFailureType.name} 
									</td>
								</c:forEach>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</div>