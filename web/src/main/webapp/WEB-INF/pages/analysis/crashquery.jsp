<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.policeStation" var="policeStationLabel" />
<head>
    <title><fmt:message key="crashQuery.heading" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script type="text/javascript">
        $( document ).ready(function() {
            $(".submit").click(function(){
                return validateFields();
            });
        });
    </script>
</head>
<div class="col-sm-15">
	<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
	<form:form commandName="crashSearch" method="post" action="/crashqueryrun"
		id="crashForm" autocomplete="off" cssClass="well">
        <h3>
            <fmt:message key="crashQuery.heading" />
        </h3>
        <fmt:message key="crashQuery.subHeading" />
		<div class="col-sm-15">
			<table cellpadding="4" width="100%">
                <tr>
                    <td>
                        <table width="100%" class="crashform-gray">
                            <tr>
                                <td width="50%" class="blue-header"><appfuse:label styleClass="control-label"
                                                                key="crashForm.district" /></td>
                                <td width="50%" class="blue-header"><appfuse:label styleClass="control-label"
                                                               key="crashForm.policeStation" /></td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="height-130">
                                        <table class="crashform-blue" style="width: 100%; border-width: 0px !important;">
                                            <c:forEach var="district" items="${districts}" varStatus="status">
                                                <tr>
                                                    <c:if test="${status.index eq 0}">
                                                        <c:set var="borderWidth" value="border-top-width: 0px !important;" />
                                                    </c:if>
                                                    <c:if test="${status.last}">
                                                        <c:set var="borderWidth" value="border-bottom-width: 0px !important;" />
                                                    </c:if>
                                                    <td style="${borderWidth} border-left-width: 0px !important; border-right-width: 0px !important;">
                                                        <form:checkbox path="districts[${status.index}].id"
                                                                       value="${district.id}" />&nbsp;&nbsp;${district.name}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </td>
                                <td>
                                    <div class="height-130">
                                        <table class="crashform-blue" style="width: 100%; border-width: 0px !important;">
                                            <c:forEach var="policeStation" items="${policeStations}" varStatus="status">
                                                <tr>
                                                    <c:if test="${status.index eq 0}">
                                                        <c:set var="borderWidth" value="border-top-width: 0px !important;" />
                                                    </c:if>
                                                    <c:if test="${status.last}">
                                                        <c:set var="borderWidth" value="border-bottom-width: 0px !important;" />
                                                    </c:if>
                                                    <td style="${borderWidth} border-left-width: 0px !important; border-right-width: 0px !important;">
                                                        <form:checkbox path="policeStations[${status.index}].id"
                                                                       value="${policeStation.id}" />&nbsp;&nbsp;${policeStation.name}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </td>
                            </tr>

                        </table>
                    </td>
                    <td>

                    </td>
                </tr>
				<tr>
					<td width="40%">
						<table width="100%" class="crashform-blue">
							<tr>
								<th width="100%"><appfuse:label styleClass="control-label"
										key="crashForm.crashSeverity" /></th>
							</tr>
							<c:forEach var="crashSeverity" items="${crashSeverities}" varStatus="status">
								<tr>
									<td><form:checkbox path="crashSeverities[${status.index}].id"
											value="${crashSeverity.id}" />&nbsp;&nbsp;${crashSeverity.name}
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td width="60%">
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
								<td width="50%"><form:checkbox path="collisionTypes[${status.index}].id"
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
								<td width="33.3%"><form:checkbox
										path="crashCauses[${status.index}].id" value="${crashCause.id}" />&nbsp;&nbsp;${crashCause.name}
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
								<td width="33.3%"><form:checkbox
										path="vehicleFailureTypes[${status.index}].id" value="${vehicleFailureType.id}" />&nbsp;&nbsp;${vehicleFailureType.name}
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
										<c:forEach var="weather" items="${weathers}" varStatus="status">
											<tr>
												<td
													style="border-right: none; border-left: none; border-bottom: none;">
													<form:checkbox path="weathers[${status.index}].id" value="${weather.id}" />&nbsp;&nbsp;${weather.name}
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
										<c:forEach var="surfaceCondition" items="${surfaceConditions}" varStatus="status">
											<tr>
												<td
													style="border-right: none; border-left: none; border-bottom: none;">
													<form:checkbox path="surfaceConditions[${status.index}].id"
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
										<c:forEach var="roadSurface" items="${roadSurfaces}" varStatus="status">
											<tr>
												<td style="border-right: none; border-left: none;"><form:checkbox
														path="roadSurfaces[${status.index}].id" value="${roadSurface.id}" />&nbsp;&nbsp;${roadSurface.name}
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
										<c:forEach var="surfaceType" items="${surfaceTypes}" varStatus="status">
											<tr>
												<td
													style="border-right: none; border-left: none; border-bottom: none;">
													<form:checkbox path="surfaceTypes[${status.index}].id"
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
									<table width="100%" class="crashform-blue"
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
												style="border-left: none; border-bottom: none"><form:checkbox
													path="roadwayCharacters[${status.index}].id" value="${roadwayCharacter.id}" />&nbsp;&nbsp;${roadwayCharacter.name}
											</td>
											</c:forEach>
										</tr>
									</table>
								</td>
								<td colspan="6" align="right" style="border-left: none; padding-left: 2px">
									<table width="96%" class="crashform-blue"
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
												style="border-right: none; border-bottom: none"><form:checkbox
													path="junctionTypes[${status.index}].id" value="${junctionType.id}" />&nbsp;&nbsp;${junctionType.name}
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
                    <td colspan="9">
                        <table width="100%" class="crashform-gray">
                            <tr>
                                <th width="40%"><appfuse:label styleClass="control-label"
                                                               key="crash.vehicle" /></th>
                                <th width="60%"><appfuse:label styleClass="control-label"
                                                               key="crash.driver" /></th>
                            </tr>
                            <tr>
                                <td width="40%">
                                    <table width="100%" class="crashform-blue"
                                           style="border: none;">
                                        <tr>
                                            <th colspan="2" style="border-top: none; border-left: none; border-right: none;">
                                                <appfuse:label
                                                        styleClass="control-label" key="crashForm.vehicleType" />
                                            </th>
                                        </tr>
                                        <tr>

                                            <c:forEach var="vehicleType" items="${vehicleTypes}" varStatus="status">
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
                                                <td width="50%" style="border-left: none; border-bottom: none;${vehicleTypeRightBorderStyle} text-align: left;">
                                                    <form:checkbox path="vehicleTypes[${status.index}].id" value="${vehicleType.id}" />&nbsp;&nbsp;
                                                    ${vehicleType.name}
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </table>
                                </td>
                                <td width="100%">
                                    <table width="100%" class="crashform-blue"
                                           style="border: none;">
                                        <tr>
                                            <th style="border-top: none; border-left: none;">
                                                <appfuse:label
                                                        styleClass="control-label" key="crashForm.licenseType" />
                                            </th>
                                            <th style="border-top: none; border-left: none;">
                                                <appfuse:label
                                                        styleClass="control-label" key="crashForm.driverSex" />
                                            </th>
                                            <th style="border-top: none; border-left: none;">
                                                <appfuse:label
                                                        styleClass="control-label" key="crashForm.driverAge" />
                                            </th>
                                            <th style="border-top: none; border-left: none;">
                                                <appfuse:label
                                                        styleClass="control-label" key="crashForm.driverBeltUsed" />
                                            </th>
                                            <th style="border-top: none; border-left: none; border-right: none;">
                                                <appfuse:label
                                                        styleClass="control-label" key="crashForm.driverCasualty" />
                                            </th>
                                        </tr>
                                        <tr>
                                            <td style="border-top: none; border-left: none;">
                                                <c:forEach var="licenseType" items="${booleanTypes}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverLicenseTypes[${status.index}].value" value="${licenseType.value}" />&nbsp;&nbsp;${licenseType.label} <br/>
                                                </c:forEach>
                                            </td>
                                            <td style="border-top: none; border-left: none;">
                                                <c:forEach var="driverGender" items="${genders}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverGenders[${status.index}].value" value="${driverGender.value}" />&nbsp;&nbsp;${driverGender.label} <br/>
                                                </c:forEach>
                                            </td>
                                            <td style="border-top: none; border-left: none;">
                                                <c:forEach var="driverAgeRange" items="${ageRanges}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverAgeRanges[${status.index}].value" value="${driverAgeRange.value}" />&nbsp;&nbsp;${driverAgeRange.label} <br/>
                                                </c:forEach>
                                            </td>
                                            <td style="border-top: none; border-left: none;">
                                                <c:forEach var="driverBeltUsed" items="${beltUseds}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverBeltUsedOptions[${status.index}].value" value="${driverBeltUsed.value}" />&nbsp;&nbsp;${driverBeltUsed.label} <br/>
                                                </c:forEach>
                                            </td>
                                            <td style="border-top: none; border-left: none; border-right: none;">
                                                <c:forEach var="driverCasualtyType" items="${casualtyTypes}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverCasualtyTypes[${status.index}].id" value="${driverCasualtyType.id}" />&nbsp;&nbsp;${driverCasualtyType.name} <br/>
                                                </c:forEach>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="9">
                        <table width="100%" class="crashform-gray">
                            <tr>
                                <th width="100%" colspan="5">
                                    <appfuse:label styleClass="control-label"
                                                               key="crash.pedestrianAndPassengerCasualties" />
                                </th>
                            </tr>
                            <tr>
                                <td class="blue-header"><appfuse:label
                                        styleClass="control-label" key="crashForm.pedestrianOrPassengerClass" /></td>
                                <td class="blue-header"><appfuse:label
                                        styleClass="control-label"
                                        key="crashForm.pedestrianOrPassengerCasualty" /></td>
                                <td class="blue-header"><appfuse:label
                                        styleClass="control-label"
                                        key="crashForm.pedestrianOrPassengerSex" /></td>
                                <td class="blue-header"><appfuse:label
                                        styleClass="control-label"
                                        key="crashForm.pedestrianOrPassengerAge" /></td>
                                <td class="blue-header"><appfuse:label
                                        styleClass="control-label" key="crashForm.passengerBeltUsed" /></td>
                            </tr>
                            <tr>
                                <td class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyClass" items="${casualtyClasses}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyClasses[${status.index}].id" value="${casualtyClass.id}" />&nbsp;&nbsp;${casualtyClass.name} <br/>
                                    </c:forEach>
                                </td>
                                <td class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyType" items="${casualtyTypes}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyTypes[${status.index}].id" value="${casualtyType.id}" />&nbsp;&nbsp;${casualtyType.name} <br/>
                                    </c:forEach>
                                </td>
                                <td class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyGender" items="${genders}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyGenders[${status.index}].value" value="${casualtyGender.value}" />&nbsp;&nbsp;${casualtyGender.label} <br/>
                                    </c:forEach>
                                </td>
                                <td class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyAgeRange" items="${ageRanges}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyAgeRanges[${status.index}].value" value="${casualtyAgeRange.value}" />&nbsp;&nbsp;${casualtyAgeRange.label} <br/>
                                    </c:forEach>
                                </td>
                                <td class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyBeltUsed" items="${beltUseds}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyBeltUsedOptions[${status.index}].value" value="${casualtyBeltUsed.value}" />&nbsp;&nbsp;${casualtyBeltUsed.label} <br/>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
				<tr>
					<td>
						<a class="btn btn-default" href="<c:url value='/analysis'/>">
							<i class="icon-ok"></i>
							<fmt:message key="button.cancel" />
						</a>
					</td>
					<td align="right"><input type="submit" class="btn btn-primary submit"
						value="<fmt:message key='crashQuery.runQuery'/>" onclick="bCancel=false;"></td>
				</tr>
			</table>
		</div>
	</form:form>
</div>