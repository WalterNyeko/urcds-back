<%@ include file="/common/taglibs.jsp"%>
<div id="actions" class="btn=group" style="padding-bottom: 2px;">
    <a href="" onclick="return loadSelectCrash({url: '/analysiscrashselect'});">
        <%--<img src="/images/simple-search.jpg" title="<fmt:message key="crashAnalysis.Select" />" height="20"/>--%>
        <fmt:message key="crashAnalysis.Select" />
    </a>|
    <a href="/crashquery">
        <%--<img src="/images/query-icon.png" title="<fmt:message key="crashAnalysis.AdvancedSelect" />" height="20"/>--%>
        <fmt:message key="crashAnalysis.AdvancedSelect" />
    </a>|
    <a href="/crashstats">
        <fmt:message key="rcds.crashStatistics" />
    </a>|
    <a href="/crosstabs">
        <fmt:message key="rcds.CrossTabulations" />
    </a>|
    <a href="<c:url value='/analysisdownloadexcel'/>">
        <%--<img src="/images/excel-icon.png" title="<fmt:message key="button.exportToExcel" />" height="20"/>--%>
        <fmt:message key="button.exportToExcel" />
    </a>|
    <a id='gMaps' href="<c:url value='/mapping'/>">
        <%--<img src='/images/gglMap.png' alt='<fmt:message key="maps.viewInGoogleMaps"/>'--%>
             <%--title='<fmt:message key="maps.viewInGoogleMaps"/>' height='20'/>--%>
        <fmt:message key="maps.viewInGoogleMaps"/>
    </a>
</div>
