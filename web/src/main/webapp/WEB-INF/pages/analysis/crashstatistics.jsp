<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashAnalysis.heading" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script src="/scripts/highcharts.js"></script>
    <script src="/scripts/themes/grid.js"></script>
    <script src="/scripts/modules/exporting.js"></script>
    <script type="text/javascript" src="/scripts/analysis/tabulation.js"></script>
    <script type="text/javascript" src="/scripts/analysis/charting.js"></script>
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="rcds.crashStatistics" />
	</h2>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="60%">
                <c:import url="analysismenu.jsp" />
            </td>
            <td width="40%">
                <%--<ul class="nav nav-tabs" style="float: right;">--%>
                    <%--<li class="active"><a href=""  class="non-click"><fmt:message key="crashList.crashes" /></a></li>--%>
                    <%--<li><a href="/analysisvehicles"><fmt:message key="crashAnalysis.vehicles" /></a></li>--%>
                    <%--<li><a href="/analysiscasualties"><fmt:message key="crashAnalysis.casualties"/></a></li>--%>
                <%--</ul>--%>
            </td>
        </tr>
    </table>
    <select id="crashAttribute">
        <option value="collisionType">Collision Type</option>
        <option value="crashCause">Crash Cause</option>
        <option selected value="crashSeverity">Crash Severity</option>
        <option value="junctionType">Junction Type</option>
        <option value="roadSurface">Road Surface</option>
        <option value="roadwayCharacter">Roadway Character</option>
        <option value="surfaceCondition">Surface Condition</option>
        <option value="surfaceType">Surface Type</option>
        <option value="vehicleFailureType">Vehicle Failure Type</option>
        <option value="weather">Weather</option>
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
        </table>
    </div>
    <p>&nbsp;</p>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        var tabulation = new Tabulation();
        tabulation.countCrashes('crashSeverity');
        $('#crashAttribute').change(function() {
            tabulation.countCrashes($(this).val());
        })
    });
</script>