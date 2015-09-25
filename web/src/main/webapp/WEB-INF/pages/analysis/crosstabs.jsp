<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="rcds.CrossTabulations" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script src="<c:url value='/scripts/highcharts.js'/>"></script>
    <script src="<c:url value='/scripts/modules/exporting.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashfilter.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crosstab.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/charting.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashquery.js'/>"></script>
</head>
<div class="col-sm-15">
    <div style="float: left">
        <h2>
            <fmt:message key="rcds.CrossTabulations" />
        </h2>
    </div>
    <div id="query-summary">
    </div>
    <br/>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="100%">
                <c:import url="analysismenu.jsp" />
                <br/>

            </td>
        </tr>
    </table>
    <appfuse:label styleClass="control-label" key="rcds.rows" />:&nbsp;
    <select id="xCrashAttribute">
        <option value=""data-default="crashSeverity">==== Crash Attributes ====</option>
        <option value="collisionType" data-attr-type="crash">Collision Type</option>
        <option value="crashCause" data-attr-type="crash">Crash Cause</option>
        <option selected value="crashSeverity" data-attr-type="crash">Crash Severity</option>
        <option value="weightRange" data-range="weight" data-attr-type="crash">Crash Weight</option>
        <option value="day" data-range="day" data-attr-type="crash">Day of Week</option>
        <option value="district" data-prefix="policeStation" data-attr-type="crash">District</option>
        <option value="junctionType" data-attr-type="crash">Junction Type</option>
        <option value="month" data-range="month" data-attr-type="crash">Month</option>
        <option value="policeStation" data-attr-type="crash">Police Station</option>
        <option value="roadSurface" data-attr-type="crash">Road Surface</option>
        <option value="roadwayCharacter" data-attr-type="crash">Roadway Character</option>
        <option value="surfaceCondition" data-attr-type="crash">Surface Condition</option>
        <option value="surfaceType" data-attr-type="crash">Surface Type</option>
        <option value="vehicleFailureType" data-attr-type="crash">Vehicle Failure Type</option>
        <option value="weather" data-attr-type="crash">Weather</option>
        <option value="year" data-range="year" data-attr-type="crash">Year</option>
        <option value="" data-default="vehicleType">==== Vehicle Attributes ====</option>
        <option value="vehicleType" data-attr-type="vehicle">Vehicle Type</option>
        <option value="ageRange" data-attr-type="vehicle">Driver Age</option>
        <option value="gender" data-attr-type="vehicle">Driver Sex</option>
        <option value="licenseType" data-attr-type="vehicle">License Type</option>
        <option value="beltUsedOption" data-attr-type="vehicle">Belt/Helmet Used (Driver)</option>
        <option value="casualtyType" data-attr-type="vehicle">Driver Casualty</option>
        <option value="" data-default="casualtyType">==== Casualty Attributes ====</option>
        <option value="casualtyType" data-attr-type="casualty">Casualty Type</option>
        <option value="casualtyClass" data-attr-type="casualty">Casualty Class</option>
        <option value="ageRange" data-attr-type="casualty">Casualty Age</option>
        <option value="gender" data-attr-type="casualty">Casualty Sex</option>
        <option value="beltUsedOption" data-attr-type="casualty">Belt/Helmet Used (Casualty)</option>
    </select>
    &nbsp;
    <appfuse:label styleClass="control-label" key="rcds.columns" />:&nbsp;
    <select id="yCrashAttribute">
        <option value=""data-default="crashSeverity">==== Crash Attributes ====</option>
        <option selected value="collisionType" data-attr-type="crash">Collision Type</option>
        <option value="crashCause" data-attr-type="crash">Crash Cause</option>
        <option value="crashSeverity" data-attr-type="crash">Crash Severity</option>
        <option value="weightRange" data-range="weight" data-attr-type="crash">Crash Weight</option>
        <option value="day" data-range="day" data-attr-type="crash">Day of Week</option>
        <option value="district" data-prefix="policeStation" data-attr-type="crash">District</option>
        <option value="junctionType" data-attr-type="crash">Junction Type</option>
        <option value="month" data-range="month" data-attr-type="crash">Month</option>
        <option value="policeStation" data-attr-type="crash">Police Station</option>
        <option value="roadSurface" data-attr-type="crash">Road Surface</option>
        <option value="roadwayCharacter" data-attr-type="crash">Roadway Character</option>
        <option value="surfaceCondition" data-attr-type="crash">Surface Condition</option>
        <option value="surfaceType" data-attr-type="crash">Surface Type</option>
        <option value="vehicleFailureType" data-attr-type="crash">Vehicle Failure Type</option>
        <option value="weather" data-attr-type="crash">Weather</option>
        <option value="year" data-range="year" data-attr-type="crash">Year</option>
        <option value="" data-default="vehicleType">==== Vehicle Attributes ====</option>
        <option value="vehicleType" data-attr-type="vehicle">Vehicle Type</option>
        <option value="ageRange" data-attr-type="vehicle">Driver Age</option>
        <option value="gender" data-attr-type="vehicle">Driver Sex</option>
        <option value="licenseType" data-attr-type="vehicle">License Type</option>
        <option value="beltUsedOption" data-attr-type="vehicle">Belt/Helmet Used (Driver)</option>
        <option value="casualtyType" data-attr-type="vehicle">Driver Casualty</option>
        <option value="" data-default="casualtyType">==== Casualty Attributes ====</option>
        <option value="casualtyType" data-attr-type="casualty">Casualty Type</option>
        <option value="casualtyClass" data-attr-type="casualty">Casualty Class</option>
        <option value="ageRange" data-attr-type="casualty">Casualty Age</option>
        <option value="gender" data-attr-type="casualty">Casualty Sex</option>
        <option value="beltUsedOption" data-attr-type="casualty">Belt/Helmet Used (Casualty)</option>
    </select>
    &nbsp;
    <appfuse:label styleClass="control-label" key="rcds.units" />:&nbsp;
    <select id="unit">
        <option value="casualty"><fmt:message key="crashAnalysis.casualties"/></option>
        <option selected value="crash"><fmt:message key="crashList.crashes"/></option>
        <option value="vehicle"><fmt:message key="crashAnalysis.vehicles"/></option>
    </select>
    <div class="content-wrapper">
        <table cellpadding="3" width="100%" class="cross-tab">
            <tr>
                <td width="100%" id="crosstabs">

                </td>
            </tr>
            <tr>
                <td width="100%">
                    <br/>
                    <div id="crosstab-chart" style="width:100%">

                    </div>
                </td>
            </tr>
        </table>
    </div>
    <p>&nbsp;</p>
</div>
<%@ include file="/common/session.jsp"%>