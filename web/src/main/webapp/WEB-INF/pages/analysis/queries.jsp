<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashAnalysis.crashQueries" /></title>
    <meta name="menu" content="CrashMenu" />
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/theme.ice.css'/>">
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/addons/pager/jquery.tablesorter.pager.css'/>">
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.widgets.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/addons/pager/jquery.tablesorter.pager.min.js'/>"></script>
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="crashAnalysis.crashQueries" />
	</h2>
    <c:import url="analysismenu.jsp" />
    <div class="pager tablesorter-pager" style="text-align: left !important;">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/first.png'/>" class="first disabled" alt="First" title="First page" tabindex="0" aria-disabled="true">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/prev.png'/>" class="prev disabled" alt="Prev" title="Previous page" tabindex="0" aria-disabled="true">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/next.png'/>" class="next disabled" alt="Next" title="Next page" tabindex="0" aria-disabled="true">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/last.png'/>" class="last disabled" alt="Last" title="Last page" tabindex="0" aria-disabled="true">
        <select class="pagesize" aria-disabled="false">
            <option value="15">15</option>
            <option value="25">25</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        &nbsp;&nbsp;
        <a href="<c:url value='/crashqueryform' />" id="addQuery"><fmt:message key="button.addQuery" /></a>
    </div>
    <div class="content-wrapper">
        <div class="tablesorter-wrapper">
            <table class="tablesorter tablesorter-ice" width="100%" id="queryList">
                <thead>
                <tr>
                    <th><fmt:message key="crashAnalysis.queryName"/></th>
                    <th><fmt:message key="crashAnalysis.queryDescription"/></th>
                    <th><fmt:message key="rcds.dateCreated"/></th>
                    <th><fmt:message key="rcds.dateLastUpdated"/></th>
                    <th data-filter="false" data-sorter="false"><fmt:message key="rcds.actions"/></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="query" items="${queries}" varStatus="status">
                        <tr>
                            <td>${query.name}</td>
                            <td>${query.description}</td>
                            <td><fmt:formatDate value="${query.dateCreated}" type="both" dateStyle="medium" timeStyle="short" /></td>
                            <td>
                                <c:if test="${query.dateUpdated ne null}">
                                    <fmt:formatDate value="${query.dateCreated}" type="both" dateStyle="medium" timeStyle="short" />
                                </c:if>
                            </td>
                            <td>
                                <a href="#" alt="Run query">
                                    <img src="<c:url value='/images/run.png'/>" alt="Run query" title="Run query" hspace="4" width="15">
                                </a>
                                <a href="#" alt="Edit query">
                                    <img src="<c:url value='/images/bt_Edit.gif'/>" alt="Edit query" title="Edit query" hspace="4" width="25">
                                </a>
                                <a href="#" alt="Remove query">
                                    <img src="<c:url value='/images/bt_Remove.gif'/>" alt="Remove query" title="Remove query" hspace="4" width="25">
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="pager number_of_resutls">
        <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
    </div>
    <div class="pager bottom_pager" style="text-align: left; display: none;">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/first.png'/>" class="first disabled" alt="First" title="First page" tabindex="0" aria-disabled="true">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/prev.png'/>" class="prev disabled" alt="Prev" title="Previous page" tabindex="0" aria-disabled="true">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/next.png'/>" class="next disabled" alt="Next" title="Next page" tabindex="0" aria-disabled="true">
        <img src="<c:url value='/styles/tablesorter/addons/pager/icons/last.png'/>" class="last disabled" alt="Last" title="Last page" tabindex="0" aria-disabled="true">
        <select class="pagesize">
            <option value="15">15</option>
            <option value="25">25</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        initTableSorter('#queryList', 'queries');
        $('.tablesorter-wrapper').height($(window).height() - 320);
    });
</script>