<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="rcds.crashStatistics" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script src="<c:url value='/scripts/highcharts.js'/>"></script>
    <script src="<c:url value='/scripts/themes/grid.js'/>"></script>
    <script src="<c:url value='/scripts/modules/exporting.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/statistics.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/charting.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashquery.js'/>"></script>
</head>
<div class="col-sm-15">
    <div style="float: left">
        <h2>
            <fmt:message key="rcds.crashStatistics" />
        </h2>
    </div>
    <div id="query-summary">
    </div>
    <br/>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="60%">
                <c:import url="analysismenu.jsp" />
            </td>
            <td width="40%">
                &nbsp;
            </td>
        </tr>
    </table>
    <select id="crashAttribute">
        <option value="collisionType">Collision Type</option>
        <option value="crashCause">Crash Cause</option>
        <option selected value="crashSeverity">Crash Severity</option>
        <option value="weightRange" data-range="weight">Crash Weight</option>
        <option value="district" data-prefix="policeStation">District</option>
        <option value="junctionType">Junction Type</option>
        <option value="month" data-range="month">Month</option>
        <option value="policeStation">Police Station</option>
        <option value="roadSurface">Road Surface</option>
        <option value="roadwayCharacter">Roadway Character</option>
        <option value="surfaceCondition">Surface Condition</option>
        <option value="surfaceType">Surface Type</option>
        <option value="vehicleFailureType">Vehicle Failure Type</option>
        <option value="weather">Weather</option>
        <option value="year" data-range="year">Year</option>
    </select>
    <div class="content-wrapper">
        <table cellpadding="3" width="100%" class="stats-tab">
            <tr>
                <td width="40%" id="stats" style="padding-right: 30px;">

                </td>
                <td width="60%">
                    <div id="stat-chart" style="width:100%">

                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <div id="stat-column" style="width:100%">

                    </div>
                </td>
            </tr>
        </table>
    </div>
    <p>&nbsp;</p>
</div>
<%@ include file="/common/session.jsp"%>