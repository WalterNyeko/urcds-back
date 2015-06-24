<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="rcds.CrossTabulations" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script src="<c:url value='/scripts/highcharts.js'/>"></script>
    <script src="<c:url value='/scripts/modules/exporting.js'/>"></script>
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
    <div id="query-summary" style="float: right">

    </div>
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
        <option value="collisionType">Collision Type</option>
        <option value="crashCause">Crash Cause</option>
        <option selected value="crashSeverity">Crash Severity</option>
        <option value="district" data-prefix="policeStation">District</option>
        <option value="junctionType">Junction Type</option>
        <option value="policeStation">Police Station</option>
        <option value="roadSurface">Road Surface</option>
        <option value="roadwayCharacter">Roadway Character</option>
        <option value="surfaceCondition">Surface Condition</option>
        <option value="surfaceType">Surface Type</option>
        <option value="vehicleFailureType">Vehicle Failure Type</option>
        <option value="weather">Weather</option>
    </select>
    &nbsp;
    <appfuse:label styleClass="control-label" key="rcds.columns" />:&nbsp;
    <select id="yCrashAttribute">
        <option selected value="collisionType">Collision Type</option>
        <option value="crashCause">Crash Cause</option>
        <option value="crashSeverity">Crash Severity</option>
        <option value="district" data-prefix="policeStation">District</option>
        <option value="junctionType">Junction Type</option>
        <option value="policeStation">Police Station</option>
        <option value="roadSurface">Road Surface</option>
        <option value="roadwayCharacter">Roadway Character</option>
        <option value="surfaceCondition">Surface Condition</option>
        <option value="surfaceType">Surface Type</option>
        <option value="vehicleFailureType">Vehicle Failure Type</option>
        <option value="weather">Weather</option>
    </select>
    &nbsp;
    <appfuse:label styleClass="control-label" key="rcds.units" />:&nbsp;
    <fmt:message key="menu.crashes"/>
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
<script type="text/javascript">
    $(document).ready(function() {
        new CrossTabulation();
        crossTabulation.countCrashes('crashSeverity', 'collisionType');
        $('#xCrashAttribute, #yCrashAttribute').change(function() {
            var xSelectedOption = $('#xCrashAttribute').find('option:selected');
            var ySelectedOption = $('#yCrashAttribute').find('option:selected');
            crossTabulation.countCrashes($('#xCrashAttribute').val(), $('#yCrashAttribute').val(), xSelectedOption.attr('data-prefix'), ySelectedOption.attr('data-prefix'));
        });
        renderQuerySummary();
    });
</script>