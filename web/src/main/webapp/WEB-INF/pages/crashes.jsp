<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="crashList.title" /></title>
<meta name="menu" content="CrashMenu" />
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="crashList.heading" />
	</h2>
	<div id="actions" class="btn=group">
		<security:authorize url="/app/crashform*">
		  <a href="/crashform"> <fmt:message
				key="button.addCrash" />
		</a> | 
		</security:authorize>
		<a href="<c:url value='/crashexcel'/>"> <fmt:message
				key="button.exportToExcel" />
		</a>
	</div>
    <div class="content-wrapper">
        <display:table name="crashList"
            class="table table-condensed table-striped table-hover" requestURI=""
            id="crashList" export="false" pagesize="25" decorator="com.sweroad.webapp.decorator.CrashDecorator">
            <%-- <display:column property="id" sortable="true" href="crashform"
                media="html" paramId="id" paramProperty="id" titleKey="crash.id" /> --%>
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