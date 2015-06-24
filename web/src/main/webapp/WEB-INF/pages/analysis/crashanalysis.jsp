<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashAnalysis.heading" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/theme.ice.css'/>">
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/addons/pager/jquery.tablesorter.pager.css'/>">
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.widgets.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/addons/pager/jquery.tablesorter.pager.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/analysis/crashquery.js'/>"></script>
</head>
<div class="col-sm-15">
    <div style="float: left">
        <h2>
            <fmt:message key="crashAnalysis.heading" />
        </h2>
    </div>
    <div id="query-summary" style="float: right">

    </div>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="60%">
                <c:import url="analysismenu.jsp" />
            </td>
            <td width="40%">
                <ul class="nav nav-tabs" style="float: right;">
                    <li class="active"><a href=""  class="non-click"><fmt:message key="crashList.crashes" /></a></li>
                    <li><a href="<c:url value='/analysisvehicles'/>"><fmt:message key="crashAnalysis.vehicles" /></a></li>
                    <li><a href="<c:url value='/analysiscasualties'/>"><fmt:message key="crashAnalysis.casualties"/></a></li>
                </ul>
            </td>
        </tr>
    </table>
    <div class="content-wrapper">
        <div class="tablesorter-wrapper">
            <table class="tablesorter tablesorter-ice" width="100%" id="crashList">
                <thead>
                <tr>
                    <th><fmt:message key="crash.crashNo"/></th>
                    <th><fmt:message key="crash.townOrVillage"/></th>
                    <th><fmt:message key="crash.road"/></th>
                    <th><fmt:message key="crash.severity"/></th>
                    <th><fmt:message key="crash.vehicles"/></th>
                    <th><fmt:message key="crash.casualties"/></th>
                    <th><fmt:message key="crash.date"/></th>
                    <th><fmt:message key="crash.policeStation"/></th>
                    <th><fmt:message key="crash.district"/></th>
                    <th data-filter="false" data-sorter="false"><fmt:message key="rcds.actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="crash" items="${crashes}" varStatus="status">
                    <tr>
                        <td>${crash.uniqueCode}</td>
                        <td>${crash.townOrVillage}</td>
                        <td>${crash.road}</td>
                        <td>${crash.crashSeverity.name}</td>
                        <td>${crash.vehicleCount}</td>
                        <td>${crash.casualtyCount}</td>
                        <td>${crash.crashDisplayDate}</td>
                        <td>${crash.policeStation.name}</td>
                        <td>${crash.policeStation.district.name}</td>
                        <td>
                            <a href="<c:url value='/crashview'/>?id=${crash.id}" alt="View crash" onclick="setAccessedObject(this)" data-crashanalysis-id="${crash.id}">
                                <img src="<c:url value='/images/bt_View.gif'/>" alt="View" title="View" hspace="4">
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
    <div class="pager bottom_pager" style="text-align: left;">
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
<input id='crashesJSON' type='hidden' value='${crashesJSON}' />
<input id='crashAttributesJSON' type='hidden' value='${crashAttributesJSON}' />
<input id="accessAttributeName" type="hidden" value="data-crashanalysis-id">
<script type="text/javascript">
    $(document).ready(function() {
        initCrashAnalysis();
    });
    jQuery(window).load(function(){
        highlightLastAccessedObject();
        $('.tablesorter-wrapper').height($(window).height() - 310);
    });
</script>