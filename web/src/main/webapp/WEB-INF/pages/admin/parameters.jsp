<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="rcds.systemParameters" /></title>
<meta name="menu" content="AdminMenu" />
</head>
<body id="systemParameters">

	<div class="col-sm-10">
		<h2>
			<fmt:message key="rcds.systemParameters" />
		</h2>

        <display:table name="parameters" cellspacing="0" cellpadding="0"
                       requestURI="" defaultsort="1" id="parameters" pagesize="50"
                       class="table table-condensed table-striped table-hover">
            <display:column property="name" escapeXml="true" sortable="true"
                            titleKey="rcds.parameter" style="width: 90%" />
            <display:column sortProperty="manage"
                            titleKey="rcds.manage" style="width: 10%;"
                            media="html">
                <a href="/admin/paramslist?type=<c:out value="${parameters.code}"/>">
                    <fmt:message key="rcds.manage" />
                </a>
            </display:column>
            <display:column property="manage" titleKey="rcds.manage"
                            media="csv xml excel pdf" />

        </display:table>
	</div>
</body>