<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="patient.hospital" var="hospitalLabel" />
<head>
    <title><fmt:message key="patient.patientForm" /></title>
    <meta name="menu" content="PatientMenu" />
    <script type="text/javascript" src="<c:url value='/scripts/patient/patient.js' />"></script>
    <script type="text/javascript">
        var patient;
        $(document).ready(function() {
            patient = new Patient(this.getElementById('patientJSON').value, this);
            patient.view.render();
            ui.initDatePicker(function() {
                ui.setInjuryDateTime();
                patient.view.set30DayStatusReadonly();
            });
        });
    </script>
</head>
<div class="col-sm-15">
    <c:url value="/patientform" var="formUrl" />
	<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
	<form:form commandName="patient" method="post" action="${formUrl}"
		id="patientForm" autocomplete="off" cssClass="well">
        <h3>
            <fmt:message key="patient.patientForm" />
        </h3>
		<div class="col-sm-15">
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
                    <td>
                        <form:select path="hospital.id" cssClass="form-control req-val" data-labelName="${hospitalLabel}">
                            <form:option value="">
                                <fmt:message key="rcds.pleaseSelect" />
                            </form:option>
                            <form:options items="${hospitals}" itemValue="id" itemLabel="name" />
                        </form:select>
                        <form:hidden path="id" />
                        <form:hidden path="dateCreated" />
                        <input type="hidden" id="patientJSON" value="${patientJSON}"/>
                    </td>
                    <td><form:input cssClass="form-control" path="hospitalOutpatientNo" id="hospitalOutpatientNo" /></td>
                    <td><form:input cssClass="form-control" path="hospitalInpatientNo" id="hospitalInpatientNo" /></td>
                </tr>
            </table>
            <br>
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
                        <form:radiobutton path="gender" value="M" />&nbsp;
                        <label for="gender1" class="form-label"><fmt:message key="rcds.male" /></label>&nbsp;&nbsp;&nbsp;
                        <form:radiobutton path="gender" value="F" />&nbsp;
                        <label for="gender2" class="form-label"><fmt:message key="rcds.female" /></label>
                    </td>
                    <td><form:input cssClass="form-control right-al int-val" path="age" id="age" /></td>
                    <td>
                        <form:select path="district.id" cssClass="form-control">
                            <form:option value="">
                                <fmt:message key="rcds.pleaseSelect" />
                            </form:option>
                            <form:options items="${districts}" itemValue="id" itemLabel="name" />
                        </form:select>
                    </td>
                    <td><form:input cssClass="form-control" path="village" id="village" /></td>
                </tr>
            </table>
            <br>
            <table width="100%" class="crashform-blue">
                <tr>
                    <th colspan="2">
                        <appfuse:label styleClass="control-label" key="rcds.dateAndTime" />
                    </th>
                </tr>
                <tr>
                    <td>
                        <input type="text" id="injuryDate" name="injuryDate" class="form-control dtpicker right-al" value=""
                               readonly="readonly" style="background-color: #FFFFFF; cursor: pointer;" placeholder="YYYY-MM-DD"/>
                        <form:hidden path="injuryDateTimeString" id="injuryDateTimeString" />
                    </td>
                    <td>
                        <input type="text" id="injuryTime" name="injuryTime" class="form-control right-al time-form" placeholder="HH:MM" />
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
	                            &nbsp;<form:radiobutton path="transportMode.id" value="${transportMode.id}" />&nbsp;
	                            <label for="transportMode.id${transportMode.id}" class="form-label">${transportMode.name}</label>
	                            <br/>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="roadUserType" items="${roadUserTypes}" varStatus="status">
                            &nbsp;<form:radiobutton path="roadUserType.id" value="${roadUserType.id}" />&nbsp;
                            <label for="roadUserType.id${roadUserType.id}" class="form-label">${roadUserType.name}</label>
                            <br/>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="transportMode" items="${transportModes}" varStatus="status">
                            &nbsp;<form:radiobutton path="counterpartTransportMode.id" value="${transportMode.id}" />&nbsp;
                            <label for="counterpartTransportMode.id${transportMode.id}" class="form-label">${transportMode.name}</label>
                            <br/>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="beltUsed" items="${beltUsedOptions}" varStatus="status">
                            &nbsp;<form:radiobutton path="beltUsed" value="${beltUsed.value}" />&nbsp;
                            <label for="beltUsed${beltUsed.value}" class="form-label">${beltUsed.label}</label>
                            <br/>
                        </c:forEach>
                    </td>
                </tr>
            </table>
            <br>
            <table width="100%" class="crashform-blue injury-types">
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
                            <form:checkbox path="patientInjuryTypes[${status.index}].injuryType.id" value="${injuryType.id}" id="injuryType${injuryType.id}" />&nbsp;
                            <label for="injuryType${injuryType.id}" class="form-label">${injuryType.name}</label>
                        </td>
                        <td>
                            <form:checkbox path="patientInjuryTypes[${status.index}].ais" id="injuryTypeAis${injuryType.id}" cssClass="ais" />&nbsp;
                        </td>
                    </c:forEach>
                </tr>
            </table>
            <br>
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
                            <form:radiobutton path="patientDisposition.id" value="${patientDisposition.id}" />&nbsp;
                            <label for="patientDisposition.id${patientDisposition.id}" class="form-label">${patientDisposition.name}</label><br>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="patientStatus" items="${patientStatuses}" varStatus="status">
                            <form:radiobutton path="patientStatus.id" value="${patientStatus.id}" />&nbsp;
                            <label for="patientStatus.id${patientStatus.id}" class="form-label">${patientStatus.name}</label><br>
                        </c:forEach>
                    </td>
                </tr>
            </table>
            <br>
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
                    <td><form:input cssClass="form-control" path="formFilledBy" id="formFilledBy" /></td>
                    <td>
                        <input type="text" id="formFilledOn" name="formFilledOn" class="form-control dtpicker right-al" placeholder="YYYY-MM-DD"
                               readonly="readonly" value="${patient.formFilledOn}" style="background-color: #FFFFFF; cursor: pointer;"/>
                    </td>
                    <td><form:input cssClass="form-control" path="formCheckedBy" id="formCheckedBy" /></td>
                    <td>
                        <input type="text" id="formCheckedOn" name="formCheckedOn" class="form-control dtpicker right-al" placeholder="YYYY-MM-DD"
                               readonly="readonly" value="${patient.formCheckedOn}" style="background-color: #FFFFFF; cursor: pointer;"/>
                    </td>
                </tr>
            </table>
            <br>
            <table width="100%">
				<tr>
					<td width="50%">
						<a class="btn btn-default show-loading" href="<c:url value='/patients'/>" onclick="bCancel=true; ui.loadingNotification()">
							<i class="icon-ok"></i>
							<fmt:message key="button.cancel" />
						</a>
					</td>
					<td width="50%" align="right">
                        <input type="button" class="btn btn-primary submit" value="<fmt:message key='button.save'/>" onclick="bCancel=false; return submitForm();">
                    </td>
				</tr>
			</table>
		</div>
	</form:form>
</div>
<script type="text/javascript">
    function submitForm() {
        if(validateFields()) {
            ui.loadingNotification();
            util.unbindBeforeUnload();
            document.getElementById('patientForm').submit();
        }
        return false;
    }
</script>