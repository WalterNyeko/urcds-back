<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="<c:url value='/scripts/crash-validator.js'/>"></script>
<div class="toggle-menu">
    <div id="selection">
        <a href="<c:url value='/crashselection'/>" class="show-loading">
            <fmt:message key="rcds.crashSelection" />
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
        <a href="<c:url value='/analysisdownloadexcel'/>" class="show-loading auto-hide">
            <fmt:message key="button.exportToExcel" />
        </a>
    </div>
    <div id="maps">
        <a id='gMaps' href="<c:url value='/mapping'/>" class="show-loading">
            <fmt:message key="maps.viewInGoogleMaps"/>
        </a>
    </div>
</div>
