<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="rcds.systemParameters" /></title>
<meta name="menu" content="AdminMenu" />
</head>
<body id="systemParameters">

	<div class="col-sm-10">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td width="60%">
                    <h3>
                        <fmt:message key="rcds.systemParameters" />
                    </h3>
                </td>
                <td>

                </td>
            </tr>
        </table>

        <display:table name="parameters" cellspacing="0" cellpadding="0"
                       requestURI="" defaultsort="1" id="parameters" pagesize="50"
                       class="table table-condensed table-striped table-hover">
            <display:column property="name" escapeXml="true" sortable="true"
                            titleKey="rcds.parameter" style="width: 90%" />
            <display:column sortProperty="manage"
                            titleKey="rcds.manage" style="width: 10%;"
                            media="html">
                <a href="<c:url value='/admin/paramslist?id=${parameters.id}'/>">
                    <fmt:message key="rcds.manage" />
                </a>
            </display:column>
            <display:column property="manage" titleKey="rcds.manage"
                            media="csv xml excel pdf" />

        </display:table>
	</div>
</body>