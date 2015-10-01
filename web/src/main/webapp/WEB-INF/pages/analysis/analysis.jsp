<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="rcds.crashStatistics" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script src="<c:url value='/scripts/highcharts.js'/>"></script>
    <script src="<c:url value='/scripts/themes/grid.js'/>"></script>
    <script src="<c:url value='/scripts/modules/exporting.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/analysis.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crosstab.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/charting.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/statistics.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashquery.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashfilter.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashtrends.js'/>"></script>
</head>
<div class="col-sm-15">
    <div style="float: left">
        <h2 class="analysis-header">

        </h2>
    </div>
    <div id="query-summary">
    </div>
    <br/>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="60%">
                <c:import url="analysismenu.jsp" />
                <br/>
            </td>
            <td width="40%">
                &nbsp;
            </td>
        </tr>
    </table>
    <div class="analysis-tools"></div>
    <div class="content-wrapper">

    </div>
    <p>&nbsp;</p>
</div>
<input id="crashstats-header" type="hidden" value="<fmt:message key="rcds.crashStatistics" />" />
<input id="crashtrends-header" type="hidden" value="<fmt:message key="rcds.CrashTrends" />" />
<input id="crosstabs-header" type="hidden" value="<fmt:message key="rcds.CrossTabulations" />" />
<div id="crashdata"></div>