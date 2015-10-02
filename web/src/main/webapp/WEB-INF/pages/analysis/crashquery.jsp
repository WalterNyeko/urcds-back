<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="crashForm.policeStation" var="policeStationLabel" />
<c:choose>
    <c:when test="${query ne null}">
        <c:set var="pageHeader" value="${query.name}" />
        <c:set var="subHeader" value="${query.description}" />
    </c:when>
    <c:otherwise>
        <c:set var="pageHeader">
            <fmt:message key="crashQuery.heading" />
        </c:set>
        <c:set var="subHeader">
            <fmt:message key="crashQuery.subHeading" />
        </c:set>
    </c:otherwise>
</c:choose>

<head>
    <title>${pageHeader}</title>
    <meta name="menu" content="AnalysisMenu" />
    <script type="text/javascript" src="<c:url value='/scripts/crash-validator.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashquery.js'/>"></script>
    <script type="text/javascript">
        $( document ).ready(function() {
            $('form').submit(function() {
                ui.loadingNotification();
                util.unbindBeforeUnload();
                util.persistQuery();
            });
            $('.year-month-range').change(function() {
                validateYearMonthRange(displayYearMonthRangeError);
                showHideDateControls();
            });
            $('.district').click(crashQueryFilterPoliceStations);
            util.loadQueryForm();
            util.initFormChangeDetection('#crashQuery');
        });
    </script>
</head>
<div class="col-sm-15">
    <c:url value="/crashqueryrun" var="formUrl" />
	<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
	<form:form commandName="crashSearch" method="post" action="${formUrl}"
		id="crashQuery" autocomplete="off" cssClass="well">
        <h3>${pageHeader}</h3>
        ${subHeader}
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
                                        <table id="district" class="crashform-blue" style="width: 100%; border-width: 0px !important;">
                                            <c:forEach var="district" items="${districts}" varStatus="status">
                                                <tr>
                                                    <c:if test="${status.index eq 0}">
                                                        <c:set var="borderWidth" value="border-top-width: 0px !important;" />
                                                    </c:if>
                                                    <c:if test="${status.last}">
                                                        <c:set var="borderWidth" value="border-bottom-width: 0px !important;" />
                                                    </c:if>
                                                    <td style="${borderWidth} border-left-width: 0px !important; border-right-width: 0px !important;">
                                                        <form:checkbox path="districts[${status.index}].id" cssClass="district"
                                                                       value="${district.id}" id="district${district.id}" data-id="${district.id}" />&nbsp;
                                                        <label for="district${district.id}" class="form-label">${district.name}</label>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </td>
                                <td>
                                    <div class="height-130">
                                        <table id="policeStation" class="crashform-blue" style="width: 100%; border-width: 0px !important;">
                                            <c:forEach var="policeStation" items="${policeStations}" varStatus="status">
                                                <tr>
                                                    <c:if test="${status.index eq 0}">
                                                        <c:set var="borderWidth" value="border-top-width: 0px !important;" />
                                                    </c:if>
                                                    <c:if test="${status.last}">
                                                        <c:set var="borderWidth" value="border-bottom-width: 0px !important;" />
                                                    </c:if>
                                                    <td style="${borderWidth} border-left-width: 0px !important; border-right-width: 0px !important;">
                                                        <form:checkbox path="policeStations[${status.index}].id" cssClass="policeStation"
                                                                       value="${policeStation.id}" id="policeStation${policeStation.id}" data-district-id="${policeStation.district.id}" />&nbsp;
                                                        <label for="policeStation${policeStation.id}" class="form-label">${policeStation.name}</label>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </td>
                            </tr>

                        </table>
                    </td>
                    <td valign="top">
                        <table width="100%" cellpadding="0" cellspacing="0" style="border: 1px solid #000; background-color: #ffffff">
                            <tr>
                                <td colspan="2" class="blue-header" style="border-bottom:  1px solid #000;">
                                    <appfuse:label styleClass="control-label"
                                                   key="crashAnalysis.timeDimension" />
                                </td>
                            </tr>
                            <tr>
                                <td width="50%">
                                    <table width="100%">
                                        <tr>
                                            <td width="20%" style="border-bottom:  1px solid #000; padding: 4px;">
                                                <appfuse:label styleClass="form-label"
                                                               key="rcds.from" />
                                            </td>
                                            <td width="80%" valign="top" style="border-bottom:  1px solid #000; border-right:  1px solid #000; padding: 4px;">
                                                <table width="100%">
                                                    <tr>
                                                        <td width="50%">
                                                            <form:select path="startYear"
                                                                         cssClass="form-control year-month-range" id="startYear">
                                                                <form:option value="" selected="selected">
                                                                    <fmt:message key="rcds.year" />
                                                                </form:option>
                                                                <form:options items="${years}" itemValue="value"
                                                                              itemLabel="label" />
                                                            </form:select>
                                                        </td>
                                                        <td width="50%">
                                                            <form:select path="startMonth"
                                                                         cssClass="form-control year-month-range" id="startMonth">
                                                                <form:option value="" selected="selected">
                                                                    <fmt:message key="rcds.month" />
                                                                </form:option>
                                                                <form:options items="${months}" itemValue="value"
                                                                              itemLabel="label" />
                                                            </form:select>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 4px;">
                                                <appfuse:label styleClass="form-label"
                                                               key="crashAnalysis.startDate" />
                                            </td>
                                            <td style="border-right:  1px solid #000; padding: 4px;">
                                                <input type="text" id="startDateString" name="startDateString" class="form-control dtpicker right-al"
                                                       readonly="readonly" style="background-color: #FFFFFF; cursor: pointer;"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="50%">
                                    <table width="100%">
                                        <tr>
                                            <td width="20%" style="border-bottom:  1px solid #000; padding: 4px;">
                                                <appfuse:label styleClass="form-label"
                                                               key="rcds.to" />
                                            </td>
                                            <td width="80%" style="border-bottom:  1px solid #000; padding: 4px;">
                                                <table width="100%">
                                                    <tr>
                                                        <td width="50%">
                                                            <form:select path="endYear"
                                                                         cssClass="form-control year-month-range" id="endYear">
                                                                <form:option value="" selected="selected">
                                                                    <fmt:message key="rcds.year" />
                                                                </form:option>
                                                                <form:options items="${years}" itemValue="value"
                                                                              itemLabel="label" />
                                                            </form:select>
                                                        </td>
                                                        <td width="50%">
                                                            <form:select path="endMonth"
                                                                         cssClass="form-control year-month-range" id="endMonth">
                                                                <form:option value="" selected="selected">
                                                                    <fmt:message key="rcds.month" />
                                                                </form:option>
                                                                <form:options items="${months}" itemValue="value"
                                                                              itemLabel="label" />
                                                            </form:select>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr id="year-month-range-error" style="display: none; color: red; font-style: italic;">
                                            <td colspan="2">

                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 4px;">
                                                <appfuse:label styleClass="form-label"
                                                               key="crashAnalysis.endDate" />
                                            </td>
                                            <td style="padding: 4px;">
                                                <input type="text" id="endDateString" name="endDateString" class="form-control dtpicker right-al"
                                                       readonly="readonly" style="background-color: #FFFFFF; cursor: pointer;"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
				<tr>
					<td width="40%">
						<table id="crashSeverity" width="100%" class="crashform-blue">
							<tr>
								<th width="100%"><appfuse:label styleClass="control-label"
										key="crashForm.crashSeverity" /></th>
							</tr>
							<c:forEach var="crashSeverity" items="${crashSeverities}" varStatus="status">
								<tr>
									<td><form:checkbox path="crashSeverities[${status.index}].id"
											value="${crashSeverity.id}" id="crashSeverity${crashSeverity.id}" />&nbsp;
                                        <label for="crashSeverity${crashSeverity.id}" class="form-label">${crashSeverity.name}</label>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td width="60%">
						<table id="collisionType" width="100%" class="crashform-blue">
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
										value="${collisionType.id}" id="collisionType${collisionType.id}" />&nbsp;
                                    <label for="collisionType${collisionType.id}" class="form-label">${collisionType.name}</label>
								</td>
								</c:forEach>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table id="crashCause" width="100%" class="crashform-blue">
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
										path="crashCauses[${status.index}].id" value="${crashCause.id}" id="crashCause${crashCause.id}" />&nbsp;
                                    <label for="crashCause${crashCause.id}" class="form-label">${crashCause.name}</label>
								</td>
								</c:forEach>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table id="vehicleFailureType" width="100%" class="crashform-blue">
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
										path="vehicleFailureTypes[${status.index}].id" value="${vehicleFailureType.id}" id="vehicleFailureType${vehicleFailureType.id}" />&nbsp;
                                    <label for="vehicleFailureType${vehicleFailureType.id}" class="form-label">${vehicleFailureType.name}</label>
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
									<table id="weather" width="100%" class="crashform-blue"
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
													<form:checkbox path="weathers[${status.index}].id" value="${weather.id}" id="weather${weather.id}" />&nbsp;
                                                    <label for="weather${weather.id}" class="form-label">${weather.name}</label>
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
								<td width="4%">&nbsp;</td>
								<td width="22%" colspan="2">
									<table id="surfaceCondition" width="100%" class="crashform-blue"
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
														value="${surfaceCondition.id}" id="surfaceCondition${surfaceCondition.id}" />&nbsp;
                                                    <label for="surfaceCondition${surfaceCondition.id}" class="form-label">${surfaceCondition.name}</label>
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
								<td width="4%">&nbsp;</td>
								<td width="22%">
									<table id="roadSurface" width="100%" class="crashform-blue"
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
														path="roadSurfaces[${status.index}].id" value="${roadSurface.id}" id="roadSurface${roadSurface.id}" />&nbsp;
                                                    <label for="roadSurface${roadSurface.id}" class="form-label">${roadSurface.name}</label>
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
								<td width="4%">&nbsp;</td>
								<td width="22%">
									<table id="surfaceType" width="100%" class="crashform-blue"
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
														value="${surfaceType.id}" id="surfaceType${surfaceType.id}" />&nbsp;
                                                    <label for="surfaceType${surfaceType.id}" class="form-label">${surfaceType.name}</label>
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
									<table id="roadwayCharacter" width="100%" class="crashform-blue"
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
													path="roadwayCharacters[${status.index}].id" value="${roadwayCharacter.id}" id="roadwayCharacter${roadwayCharacter.id}" />&nbsp;
                                                <label for="roadwayCharacter${roadwayCharacter.id}" class="form-label">${roadwayCharacter.name}</label>
											</td>
											</c:forEach>
										</tr>
									</table>
								</td>
								<td colspan="6" align="right" style="border-left: none; padding-left: 2px">
									<table id="junctionType" width="96%" class="crashform-blue"
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
													path="junctionTypes[${status.index}].id" value="${junctionType.id}" id="junctionType${junctionType.id}" />&nbsp;
                                                <label for="junctionType${junctionType.id}" class="form-label">${junctionType.name}</label>
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
                                    <table id="vehicleType" width="100%" class="crashform-blue"
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
                                                    <form:checkbox path="vehicleTypes[${status.index}].id" value="${vehicleType.id}" id="vehicleType${vehicleType.id}" />&nbsp;
                                                    <label for="vehicleType${vehicleType.id}" class="form-label">${vehicleType.name}</label>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </table>
                                </td>
                                <td width="60%">
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
                                            <td id="licenseType" style="border-top: none; border-left: none;">
                                                <c:forEach var="licenseType" items="${licenseTypes}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverLicenseTypes[${status.index}].value" value="${licenseType.value}" id="licenseType${licenseType.value}" />&nbsp;
                                                    <label for="licenseType${licenseType.value}" class="form-label">${licenseType.label}</label>
                                                    <br/>
                                                </c:forEach>
                                            </td>
                                            <td id="driverGender" style="border-top: none; border-left: none;">
                                                <c:forEach var="driverGender" items="${genders}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverGenders[${status.index}].value" value="${driverGender.value}" id="driverGender${driverGender.value}" />&nbsp;
                                                    <label for="driverGender${driverGender.value}" class="form-label">${driverGender.label}</label><br/>
                                                </c:forEach>
                                            </td>
                                            <td id="driverAgeRange" style="border-top: none; border-left: none;">
                                                <c:forEach var="driverAgeRange" items="${ageRanges}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverAgeRanges[${status.index}].value" value="${driverAgeRange.value}" id="driverAgeRange${driverAgeRange.value}" />&nbsp;
                                                    <label for="driverAgeRange${driverAgeRange.value}" class="form-label">${driverAgeRange.label}</label>
                                                    <br/>
                                                </c:forEach>
                                            </td>
                                            <td id="driverBeltUsed" style="border-top: none; border-left: none;">
                                                <c:forEach var="driverBeltUsed" items="${beltUseds}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverBeltUsedOptions[${status.index}].value" value="${driverBeltUsed.value}" id="driverBeltUsed${driverBeltUsed.value}" />&nbsp;
                                                    <label for="driverBeltUsed${driverBeltUsed.value}" class="form-label">${driverBeltUsed.label}</label>
                                                    <br/>
                                                </c:forEach>
                                            </td>
                                            <td id="driverCasualtyType" style="border-top: none; border-left: none; border-right: none;">
                                                <c:forEach var="driverCasualtyType" items="${casualtyTypes}" varStatus="status">
                                                    <form:checkbox
                                                            path="driverCasualtyTypes[${status.index}].id" value="${driverCasualtyType.id}" id="driverCasualtyType${driverCasualtyType.id}" />&nbsp;
                                                    <label for="driverCasualtyType${driverCasualtyType.id}" class="form-label">${driverCasualtyType.name}</label>
                                                    <br/>
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
                                <td id="casualtyClass" class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyClass" items="${casualtyClasses}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyClasses[${status.index}].id" value="${casualtyClass.id}" id="casualtyClass${casualtyClass.id}" />&nbsp;
                                        <label for="casualtyClass${casualtyClass.id}" class="form-label">${casualtyClass.name}</label>
                                        <br/>
                                    </c:forEach>
                                </td>
                                <td id="casualtyType" class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyType" items="${casualtyTypes}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyTypes[${status.index}].id" value="${casualtyType.id}" id="casualtyType${casualtyType.id}" />&nbsp;
                                        <label for="casualtyType${casualtyType.id}" class="form-label">${casualtyType.name}</label>
                                        <br/>
                                    </c:forEach>
                                </td>
                                <td id="casualtyGender" class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyGender" items="${genders}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyGenders[${status.index}].value" value="${casualtyGender.value}" id="casualtyGender${casualtyGender.value}" />&nbsp;
                                        <label for="casualtyGender${casualtyGender.value}" class="form-label">${casualtyGender.label}</label>
                                        <br/>
                                    </c:forEach>
                                </td>
                                <td id="casualtyAgeRange" class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyAgeRange" items="${ageRanges}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyAgeRanges[${status.index}].value" value="${casualtyAgeRange.value}" id="casualtyAgeRange${casualtyAgeRange.value}" />&nbsp;
                                        <label for="casualtyAgeRange${casualtyAgeRange.value}" class="form-label">${casualtyAgeRange.label}</label>
                                        <br/>
                                    </c:forEach>
                                </td>
                                <td id="casualtyBeltUsed" class="padd2" style="border-top: none; border-left: none;">
                                    <c:forEach var="casualtyBeltUsed" items="${beltUseds}" varStatus="status">
                                        <form:checkbox
                                                path="casualtyBeltUsedOptions[${status.index}].value" value="${casualtyBeltUsed.value}" id="casualtyBeltUsed${casualtyBeltUsed.value}" />&nbsp;
                                        <label for="casualtyBeltUsed${casualtyBeltUsed.value}" class="form-label">${casualtyBeltUsed.label}</label>
                                        <br/>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
				<tr>
					<td>
						<a class="btn btn-default show-loading" href="<c:url value='/crashquery'/>">
							<i class="icon-ok"></i>
							<fmt:message key="button.cancel" />
						</a>
					</td>
					<td align="right"><input type="submit" class="btn btn-primary submit"
						value="<fmt:message key='crashQuery.runQuery'/>"></td>
				</tr>
			</table>
            <input id="queryId" type="hidden" value="${query.id}" />
            <input id="queryName" type="hidden" value="${query.name}" />
            <input id="queryDescription" type="hidden" value="${query.description}" />
            <input id="queryData" type="hidden" value="${query.queryData}" />
            <input id="dirty" type="hidden"/>
		</div>
	</form:form>
</div>
