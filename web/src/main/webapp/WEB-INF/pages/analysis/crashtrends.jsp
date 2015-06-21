<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="rcds.CrashTrends" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script src="<c:url value='/scripts/highcharts.js'/>"></script>
    <script src="<c:url value='/scripts/modules/exporting.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashtrends.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/charting.js'/>"></script>
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="rcds.CrashTrends" />
	</h2>
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
        <option value="dayOfWeek">Day of Week</option>
        <option selected value="monthOfYear">Month</option>
        <option value="crashYear">Year</option>
    </select>
    &nbsp;
    <appfuse:label styleClass="control-label" key="rcds.columns" />:&nbsp;
    <select id="yCrashAttribute">
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
    <appfuse:label styleClass="control-label" key="rcds.units" />:&nbsp;
    <fmt:message key="menu.crashes"/>
    <div class="content-wrapper">
        <p>&nbsp;</p>
        <table cellpadding="3" width="100%">
            <tr>
                <td width="100%">
                    <br/>
                    <div id="crashtrend-chart" style="width:100%">

                    </div>
                </td>
            </tr>
        </table>
    </div>
    <p>&nbsp;</p>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        new CrashTrend();
        crashTend.countCrashes('monthOfYear', 'crashSeverity');
        $('#xCrashAttribute, #yCrashAttribute').change(function() {
            var ySelectedOption = $('#yCrashAttribute').find('option:selected');
            crashTend.countCrashes($('#xCrashAttribute').val(), $('#yCrashAttribute').val(), ySelectedOption.attr('data-prefix'));
        })
    });
</script>