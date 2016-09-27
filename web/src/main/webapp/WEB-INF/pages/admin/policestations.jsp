<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="rcds.systemParameters" /></title>
    <meta name="menu" content="AdminMenu" />
</head>
<body id="systemParameters">
    <div class="col-sm-10">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td width="70%">
                    <h3>
                        <fmt:message key="rcds.systemParameters" /> - ${systemParameter.name}
                    </h3>
                    <input type="hidden" value="${systemParameter.code}" id="paramCode" />
                </td>
                <td width="30%" align="right" valign="bottom">

                </td>
            </tr>
            <tr>
                <td style="padding-left: 10px">
                    <a href="" class="addnew" onclick="return ui.addSystemParameter()">
                        <fmt:message key="rcds.addNew" />
                    </a>
                </td>
                <td align="right">
                    <a href="<c:url value='/admin/params'/>">
                        <fmt:message key="rcds.backToSystemParameters" />
                    </a>
                </td>
            </tr>
        </table>

        <display:table name="parameters" cellspacing="0" cellpadding="0"
                       requestURI="" defaultsort="1" id="parameters" pagesize="500"
                       class="table table-condensed table-striped table-hover">
            <display:column property="name" escapeXml="true" sortable="true"
                            title="${systemParameter.name}" style="width: 45%" />
            <display:column property="district.name" escapeXml="true" sortable="true"
                            titleKey="rcds.district" style="width: 45%" />
            <display:column sortProperty="manage"
                            titleKey="rcds.actions" style="width: 10%;"
                            media="html">
                <c:choose>
                    <c:when test="${parameters.active}">
                        <a href="" class="edit" onclick="return ui.editSystemParameter(${parameters.id}, this)">
                            <img src="<c:url value='/images/bt_Edit.gif'/>" alt="Edit parameter" title="Edit parameter" hspace="4">
                        </a>
                        <a href="" class="remove" onclick="return ui.confirmRemoveParameter(${parameters.id}, this)">
                            <img src="<c:url value='/images/bt_Remove.gif'/>" alt="Remove parameter" title="Remove parameter" hspace="4">
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="" class="restore" onclick="return ui.confirmRestoreParameter(${parameters.id}, this)">
                            <img src="<c:url value='/images/bt_Restore.gif'/>" alt="Restore parameter" title="Restore parameter" hspace="4">
                        </a>
                    </c:otherwise>
                </c:choose>

            </display:column>
        </display:table>
    </div>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#parameters').find('a.restore').each(function() {
                $(this).closest('tr').addClass('removed');
            });
        });
    </script>
</body>