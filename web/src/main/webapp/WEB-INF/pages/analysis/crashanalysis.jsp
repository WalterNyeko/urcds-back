<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="crashAnalysis.heading" /></title>
<meta name="menu" content="AnalysisMenu" />
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="crashAnalysis.heading" />
	</h2>
	<div id="actions" class="btn=group" style="padding-bottom: 2px;">
        <a href="" onclick="return loadSelectCrash({url: '/analysiscrashselect'});">
            <img src="/images/simple-search.jpg" title="<fmt:message key="crashAnalysis.Select" />" height="20"/>
		</a>|
        <a href="" onclick="return false;">
            <img src="/images/query-icon.png" title="<fmt:message key="crashAnalysis.AdvancedSelect" />" height="20"/>
        </a>|
        <%--<a href="" onclick="return false;">--%>
            <%--<img src="/images/pivot-table-icon.jpg" title="<fmt:message key="crashAnalysis.PivotTables" />" height="20"/>--%>
        <%--</a>|--%>
        <a href="" onclick="return false;">
            <img src="/images/crosstab-icon.png" title="<fmt:message key="crashAnalysis.CrossTabulation" />" height="20"/>
        </a>|
		<a href="<c:url value='/analysisdownloadexcel'/>">
            <img src="/images/excel-icon.png" title="<fmt:message key="button.exportToExcel" />" height="20"/>
		</a>
	</div>
    <div class="content-wrapper">
        <display:table name="crashList"
            class="table table-condensed table-striped table-hover" requestURI=""
            id="crashList" export="false" pagesize="50" decorator="com.sweroad.webapp.decorator.CrashAnalysisDecorator">
            <display:column property="tarNo" sortable="true"
                titleKey="crash.tarNo" />
            <display:column property="townOrVillage" sortable="true"
                titleKey="crash.townOrVillage" />
            <display:column property="road" sortable="true" titleKey="crash.road" />
            <display:column property="crashSeverity.name" sortable="true" titleKey="crash.severity" />
            <display:column property="vehicleCount" sortable="true"
                style="text-align: center;" titleKey="crash.vehicles" />
            <display:column property="casualtyCount" sortable="true"
                style="text-align: center;" titleKey="crash.casualties" />
            <display:column property="crashDisplayDate" sortable="true"
                style="text-align: center;" titleKey="crash.date" />
            <display:column property="policeStation.name" sortable="true"
                titleKey="crash.policeStation" />
            <display:column property="policeStation.district.name" sortable="true"
                titleKey="crash.district" />
            <display:column property="actions" sortable="true" titleKey="rcds.actions" />
            <display:setProperty name="paging.banner.item_name">
                <fmt:message key="crashList.crash" />
            </display:setProperty>
            <display:setProperty name="paging.banner.items_name">
                <fmt:message key="crashList.crashes" />
            </display:setProperty>
            <display:setProperty name="export.excel.filename">
                <fmt:message key="crashList.title" />.xls</display:setProperty>
            <display:setProperty name="export.csv.filename">
                <fmt:message key="crashList.title" />.csv</display:setProperty>
            <display:setProperty name="export.pdf.filename">
                <fmt:message key="crashList.title" />.pdf</display:setProperty>
        </display:table>
    </div>
    <p>&nbsp;</p>
</div>