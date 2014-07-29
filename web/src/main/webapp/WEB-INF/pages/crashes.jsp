<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="crashList.title" /></title>
	<meta name="menu" content="CrashMenu" />
</head>
<div class="col-sm-10">
	<h2><fmt:message key="crashList.heading" /></h2>	
	<div id="actions" class="btn=group">
		<a class="btn btn-primary" href="<c:url value='/crashform'/>">
			<i class="icon-plus icon-white"></i><fmt:message key="button.addCrash"/>
		</a>
		<a class="btn btn-default" href="<c:url value='/mainMenu'/>">
			<i class="icon-ok"></i><fmt:message key="button.done"/>
		</a>
	</div>
	<display:table name="crashList" class="table table-condensed table-striped table-hover" requestURI="" id="crashList" export="true" pagesize="25">
		<display:column property="id" sortable="true" href="crashreport" media="html" paramId="id" paramProperty="id" titleKey="crash.id"/>
		<display:column property="tarNo" sortable="true" titleKey="crash.tarNo"/>
		<display:column property="townOrVillage" sortable="true" titleKey="crash.townOrVillage"/>
		<display:column property="road" sortable="true" titleKey="crash.road"/>
		<display:column property="crashDateTime" sortable="true" titleKey="crash.crashDateTime"/>
		<display:column property="policeStation.name" sortable="true" titleKey="crash.policeStation"/>
		<display:column property="policeStation.district.name" sortable="true" titleKey="crash.district"/>
		<display:setProperty name="paging.banner.item_name"><fmt:message key="crashList.crash"/></display:setProperty>
		<display:setProperty name="paging.banner.items_name"><fmt:message key="crashList.crashes"/></display:setProperty>
		<display:setProperty name="export.excel.filename"><fmt:message key="crashList.title"/>.xls</display:setProperty>
		<display:setProperty name="export.csv.filename"><fmt:message key="crashList.title"/>.csv</display:setProperty>
		<display:setProperty name="export.pdf.filename"><fmt:message key="crashList.title"/>.pdf</display:setProperty>
	</display:table>
</div>