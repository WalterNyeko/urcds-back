<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="patient.hospital" var="hospitalLabel" />
<head>
    <title><fmt:message key="patient.patientViewTitle" /></title>
    <meta name="menu" content="PatientMenu" />
</head>
<div class="col-sm-15">
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="60%">
                <h3>
                    <fmt:message key="patient.patientViewTitle" />
                </h3>
            </td>
            <td width="40%" align="right">
                <c:if test="${patient.editable and !patient.removed}">
                    <a href="<c:url value='/patientform?id=${patient.id}'/>" class="show-loading">
                        <img src="<c:url value='/images/bt_Edit.gif' />" hspace="4" title="Edit Patient" />
                    </a>
                </c:if>
                <c:if test="${patient.removable and !patient.removed}">
                    <a href="<c:url value='/patientremove?id=${patient.id}'/>" onclick="return ui.confirmDialog({message : 'Remove patient?', aLink : this});">
                        <img src="<c:url value='/images/bt_Remove.gif' />" hspace="4" title="Remove Patient" />
                    </a>
                </c:if>
                <c:if test="${patient.removable and patient.removed}">
                    <a href="<c:url value='/patientrestore?id=${patient.id}'/>" onclick="return ui.confirmDialog({message : 'Restore patient?', aLink : this});">
                        <img src="<c:url value='/images/bt_Restore.gif' />" hspace="4" title="Restore Patient" />
                    </a>
                </c:if>
                <a href="<c:url value='/patients'/>" class="show-loading"><fmt:message key="button.backToPatients" /></a>
            </td>
        </tr>
    </table>
    <div class="content-wrapper">
        <table cellpadding="10" width="100%">
            <tr>
                <td width="100%">
                    <table width="100%" class="crashform-blue">
                        <tr>
                            <th width="33.3%">
                                <appfuse:label styleClass="control-label" key="patient.hospital" />
                            </th>
                            <th width="33.3%">
                                <appfuse:label styleClass="control-label" key="patient.outpatientNo" />
                            </th>
                            <th width="33.3%">
                                <appfuse:label styleClass="control-label" key="patient.inpatientNo" />
                            </th>
                        </tr>
                        <tr>
                            <td align="center">${patient.hospital.name}</td>
                            <td align="center">${patient.hospitalOutpatientNo}</td>
                            <td align="center">${patient.hospitalInpatientNo}</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" class="crashform-gray">
                        <tr>
                            <th width="50%" colspan="2">
                                <appfuse:label styleClass="control-label" key="patient.patientInformation" />
                            </th>
                            <th width="50%" colspan="2">
                                <appfuse:label styleClass="control-label" key="patient.injuryPlace" />
                            </th>
                        </tr>
                        <tr>
                            <td width="25%" class="blue-header">
                                <appfuse:label styleClass="control-label" key="patient.gender" />
                            </td>
                            <td width="25%" class="blue-header">
                                <appfuse:label styleClass="control-label" key="patient.age" />
                            </td>
                            <td width="25%" class="blue-header">
                                <appfuse:label styleClass="control-label" key="crash.district" />
                            </td>
                            <td width="25%" class="blue-header">
                                <appfuse:label styleClass="control-label" key="patient.village" />
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center; vertical-align: middle">
                                <c:choose>
                                    <c:when test="${patient.gender eq 'M'}">
                                        <appfuse:label styleClass="form-label" key="rcds.male" />
                                    </c:when>
                                    <c:when test="${patient.gender eq 'F'}">
                                        <appfuse:label styleClass="form-label" key="rcds.female" />
                                    </c:when>
                                    <c:otherwise/>
                                </c:choose>
                            </td>
                            <td style="text-align: center; vertical-align: middle"> ${patient.age}</td>
                            <td style="text-align: center; vertical-align: middle">
                                <c:if test="${patient.district ne null}">
                                    ${patient.district.name}
                                </c:if>
                            </td>
                            <td style="text-align: center; vertical-align: middle">
                                ${patient.village}
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" class="crashform-blue">
                        <tr>
                            <th colspan="2">
                                <appfuse:label styleClass="control-label" key="rcds.dateAndTime" />
                            </th>
                        </tr>
                        <tr>
                            <td align="right">
                                <fmt:formatDate pattern="yyyy-MM-dd" value="${patient.injuryDateTime}" />
                            </td>
                            <td>
                                <fmt:formatDate type="time" pattern="HH:mm" value="${patient.injuryDateTime}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="blue-header" width="25%">
                                <appfuse:label styleClass="control-label" key="patient.modeOfTransport" />
                            </td>
                            <td class="blue-header" width="25%">
                                <appfuse:label styleClass="control-label" key="patient.roadUser" />
                            </td>
                            <td class="blue-header" width="25%">
                                <appfuse:label styleClass="control-label" key="patient.counterpart" />
                            </td>
                            <td class="blue-header" width="25%">
                                <appfuse:label styleClass="control-label" key="patient.beltOrHelmetUsed" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="transportMode" items="${transportModes}" varStatus="status">
                                    <c:if test="${ transportMode.id gt 0}">
                                        &nbsp;
                                        <input type="radio" disabled value="${transportMode.id}"
                                            ${patient.transportMode ne null and patient.transportMode.id eq transportMode.id ? "checked" : ""} />&nbsp;
                                        ${transportMode.name}
                                        <br/>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="roadUserType" items="${roadUserTypes}" varStatus="status">
                                    &nbsp;
                                    <input type="radio" disabled value="${roadUserType.id}"
                                        ${patient.roadUserType ne null and patient.roadUserType.id eq roadUserType.id ? "checked" : ""} />&nbsp;
                                    ${roadUserType.name}
                                    <br/>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="transportMode" items="${transportModes}" varStatus="status">
                                    &nbsp;
                                    <input type="radio" disabled value="${transportMode.id}"
                                        ${patient.counterpartTransportMode ne null and patient.counterpartTransportMode.id eq transportMode.id ? "checked" : ""} />&nbsp;
                                    ${transportMode.name}
                                    <br/>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="beltUsed" items="${beltUsedOptions}" varStatus="status">
                                    &nbsp;
                                    <input type="radio" disabled value="${beltUsed.value}"
                                        ${patient.beltUsed eq beltUsed.value ? "checked" : ""} />&nbsp;
                                    ${beltUsed.label}
                                    <br/>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" class="crashform-blue">
                        <tr>
                            <th colspan="6">
                                <appfuse:label styleClass="control-label" key="patient.bodyInjuries" />
                            </th>
                        </tr>
                        <tr>
                            <td width="28%" style="border-right-width: 0px;">&nbsp; </td>
                            <td width="5%" style="border-left-width: 0px;"><appfuse:label styleClass="control-label" key="patient.ais" /></td>
                            <td width="28%" style="border-right-width: 0px;">&nbsp; </td>
                            <td width="5%" style="border-left-width: 0px;"><appfuse:label styleClass="control-label" key="patient.ais" /></td>
                            <td width="28%" style="border-right-width: 0px;">&nbsp; </td>
                            <td width="5%" style="border-left-width: 0px;"><appfuse:label styleClass="control-label" key="patient.ais" /></td>
                        </tr>
                        <tr>
                            <c:forEach var="injuryType" items="${injuryTypes}"
                                       varStatus="status">
                            <c:if test="${ status.index % 3 == 0 }">
                        </tr>
                        <tr>
                            </c:if>
                            <td>
                                <c:set var="checked"  value=""/>
                                <c:set var="aisChecked"  value=""/>
                                <c:forEach var="patientInjury" items="${patient.patientInjuryTypes}">
                                    <c:if test="${patientInjury.injuryType.id eq injuryType.id}">
                                        <c:set var="checked"  value="checked"/>
                                        <c:if test="${patientInjury.ais}">
                                            <c:set var="aisChecked"  value="checked"/>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                                <input type="checkbox" disabled value="${injuryType.id}" ${checked}/>&nbsp;
                                    ${injuryType.name}
                            </td>
                            <td>
                                <input type="checkbox" disabled ${aisChecked}/>
                            </td>
                            </c:forEach>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" class="crashform-blue">
                        <tr>
                            <th width="50%">
                                <appfuse:label styleClass="control-label" key="patient.patientDisposition" />
                            </th>
                            <th width="50%">
                                <appfuse:label styleClass="control-label" key="patient.statusAfterThirty" />
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="patientDisposition" items="${patientDispositions}" varStatus="status">
                                    &nbsp;
                                    <input type="radio" disabled value="${patientDisposition.id}"
                                        ${patient.patientDisposition ne null and patient.patientDisposition.id eq patientDisposition.id ? "checked" : ""} />&nbsp;
                                    ${patientDisposition.name}
                                    <br/>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="patientStatus" items="${patientStatuses}" varStatus="status">
                                    &nbsp;
                                    <input type="radio" disabled value="${patientStatus.id}"
                                        ${patient.patientStatus ne null and patient.patientStatus.id eq patientStatus.id ? "checked" : ""} />&nbsp;
                                    ${patientStatus.name}
                                    <br/>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" class="crashform-gray">
                        <tr>
                            <th colspan="2"><appfuse:label styleClass="control-label" key="patient.formFilledBy" /></th>
                            <th colspan="2"><appfuse:label styleClass="control-label" key="patient.formCheckedBy" /></th>
                        </tr>
                        <tr>
                            <td width="30%" class="blue-header"><appfuse:label styleClass="control-label" key="crashForm.name" /></td>
                            <td width="20%" class="blue-header"><appfuse:label styleClass="control-label" key="crashForm.date" /></td>
                            <td width="30%" class="blue-header"><appfuse:label styleClass="control-label" key="crashForm.name" /></td>
                            <td width="20%" class="blue-header"><appfuse:label styleClass="control-label" key="crashForm.date" /></td>
                        </tr>
                        <tr>
                            <td align="center">${formFilledBy}</td>
                            <td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${patient.formFilledOn}" /></td>
                            <td align="center">${formCheckedBy}</td>
                            <td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${patient.formCheckedOn}" /></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="50%" style="padding-left: 8px;">
                                <table cellpadding="2" style="border: 1px solid #000;">
                                    <tr>
                                        <td align="right">
                                            <appfuse:label
                                                    styleClass="control-label" key="crashForm.CreatedBy" />:
                                        </td>
                                        <td>
                                            <i>
                                                <c:if test="${patient.createdBy ne null}">
                                                    ${patient.createdBy.fullName}
                                                </c:if>
                                            </i>
                                        </td>
                                        <td>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                        </td>
                                        <td>
                                            <appfuse:label
                                                    styleClass="control-label" key="crashForm.on" />:
                                        </td>
                                        <td>
                                            <i>
                                                <c:if test="${patient.dateCreated ne null}">
                                                    <fmt:formatDate type="both" value="${patient.dateCreated}" />
                                                </c:if>
                                            </i>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                            <appfuse:label styleClass="control-label" key="crashForm.LastUpdatedBy" />:
                                        </td>
                                        <td>
                                            <i>
                                                <c:if test="${patient.updatedBy ne null}">
                                                    ${patient.updatedBy.fullName}
                                                </c:if>
                                            </i>
                                        </td>
                                        <td>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                        </td>
                                        <td>
                                            <appfuse:label
                                                    styleClass="control-label" key="crashForm.on" />:
                                        </td>
                                        <td>
                                            <i>
                                                <c:if test="${patient.dateUpdated ne null}">
                                                    <fmt:formatDate type="both" value="${patient.dateUpdated}" />
                                                </c:if>
                                            </i>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td width="50%" align="right" valign="bottom">
                                <a href="<c:url value='/patients'/>" class="show-loading"> <fmt:message key="button.backToPatients" /></a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>