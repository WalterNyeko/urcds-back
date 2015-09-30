<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="<c:url value='/scripts/crash-validator.js'/>"></script>
<div id="actions" class="btn=group" style="padding-bottom: 2px;">
    <a href="" onclick="return loadSelectCrash({url: '<c:url value="/analysiscrashselect" />'});">
        <fmt:message key="crashAnalysis.Select" />
    </a>|
    <a href="<c:url value='/crashquery'/>">
        <fmt:message key="crashAnalysis.AdvancedSelect" />
    </a>|
    <a href="<c:url value='/analysis'/>">
        <fmt:message key="menu.crashes" />
    </a>|
    <a href="<c:url value='/analysis/crashstats'/>" class="analysis" data-type="crashstats">
        <fmt:message key="rcds.crashStatistics" />
    </a>|
    <a href="<c:url value='/analysis/crosstabs'/>" class="analysis" data-type="crosstabs">
        <fmt:message key="rcds.CrossTabulations" />
    </a>|
    <a href="<c:url value='/analysis/crashtrends'/>" class="analysis" data-type="crashtrends">
        <fmt:message key="rcds.CrashTrends" />
    </a>|
    <a href="<c:url value='/analysisdownloadexcel'/>">
        <fmt:message key="button.exportToExcel" />
    </a>|
    <a id='gMaps' href="<c:url value='/mapping'/>">
        <fmt:message key="maps.viewInGoogleMaps"/>
    </a>
</div>
