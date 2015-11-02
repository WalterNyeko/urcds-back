<%@ include file="/common/taglibs.jsp"%>
<fmt:message key="patient.hospital" var="hospitalLabel" />
<head>
    <title><fmt:message key="patient.patientForm" /></title>
    <meta name="menu" content="PatientMenu" />
    <script type="text/javascript" src="<c:url value='/scripts/crash-validator.js' />"></script>
    <script type="text/javascript">
        $(document).ready(function() { ui.initDatePicker() });
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