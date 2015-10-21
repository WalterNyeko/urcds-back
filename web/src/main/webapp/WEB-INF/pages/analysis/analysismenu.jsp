<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="<c:url value='/scripts/crash-validator.js'/>"></script>
<div class="toggle-menu">
    <div id="quick-search">
        <a href="" onclick="return ui.loadSelectCrash({url: '<c:url value="/analysiscrashselect" />'});">
            <fmt:message key="crashAnalysis.Select" />
        </a>
    </div>
    <div id="queries">
        <a href="<c:url value='/crashquery'/>" class="show-loading">
            <fmt:message key="crashAnalysis.AdvancedSelect" />
        </a>
    </div>
    <div id="crashes">
        <a href="<c:url value='/analysis'/>" class="show-loading">
            <fmt:message key="menu.crashes" />
        </a>
    </div>
    <div id="crashstats">
        <a href="<c:url value='/analysis/crashstats'/>" class="analysis" data-type="crashstats">
            <fmt:message key="rcds.crashStatistics" />
        </a>
    </div>
    <div id="crosstabs">
        <a href="<c:url value='/analysis/crosstabs'/>" class="analysis" data-type="crosstabs">
            <fmt:message key="rcds.CrossTabulations" />
        </a>
    </div>
    <div id="crashtrends">
        <a href="<c:url value='/analysis/crashtrends'/>" class="analysis" data-type="crashtrends">
            <fmt:message key="rcds.CrashTrends" />
        </a>
    </div>
    <div id="excel">
        <a href="<c:url value='/analysisdownloadexcel'/>" class="show-loading">
            <fmt:message key="button.exportToExcel" />
        </a>
    </div>
    <div id="maps">
        <a id='gMaps' href="<c:url value='/mapping'/>" class="show-loading">
            <fmt:message key="maps.viewInGoogleMaps"/>
        </a>
    </div>
</div>
