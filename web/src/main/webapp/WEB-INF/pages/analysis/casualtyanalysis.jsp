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
    <div id="query-summary">
    </div>
    <br/>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="75%">
                <c:import url="analysismenu.jsp" />
            </td>
            <td width="25%">
                <ul class="nav nav-tabs" style="float: right; margin-bottom: -1px;">
                    <li><a href="<c:url value='/analysis'/>" class="show-loading"><fmt:message key="crashList.crashes" /></a></li>
                    <li><a href="<c:url value='/analysisvehicles'/>" class="show-loading"><fmt:message key="crashAnalysis.vehicles" /></a></li>
                    <li class="active"><a href="" class="non-click"><fmt:message key="crashAnalysis.casualties"/></a></li>
                </ul>
            </td>
        </tr>
    </table>
    <div class="content-wrapper">
        <div class="tablesorter-wrapper">
            <table class="tablesorter tablesorter-ice" width="100%" id="casualtyList">
                <thead>
                <tr>
                    <th><fmt:message key="crash.crashNo"/></th>
                    <th><fmt:message key="crashAnalysis.casualtyClass"/></th>
                    <th><fmt:message key="crashAnalysis.casualtyType"/></th>
                    <th><fmt:message key="crashAnalysis.casualtySex"/></th>
                    <th><fmt:message key="crashAnalysis.casualtyAge"/></th>
                    <th><fmt:message key="crashAnalysis.fromVehicle"/></th>
                    <th><fmt:message key="crashForm.driverBeltUsed"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="casualty" items="${casualties}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value='/crashview?id='/>${casualty.crash.id}" class="show-loading" data-casualty-id="${casualty.id}">
                                ${casualty.crash.uniqueCode}
                            </a>
                        </td>
                        <td>
                            ${casualty.casualtyClass.name}
                        </td>
                        <td>${casualty.casualtyType.name}</td>
                        <td align="center">${casualty.gender}</td>
                        <td align="right">${casualty.age}</td>
                        <td align="right">
                            <c:choose>
                                <c:when test="${casualty.casualtyClass.id eq 1}">
                                    N/A
                                </c:when>
                                <c:when test="${casualty.vehicle ne null}">
                                    ${casualty.vehicle.number} [${casualty.vehicle.vehicleType.name}]
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${casualty.casualtyClass.id eq 1}">
                                    N/A
                                </c:when>
                                <c:when test="${casualty.beltOrHelmetUsed eq true}">
                                    Yes
                                </c:when>
                                <c:when test="${casualty.beltOrHelmetUsed eq false}">
                                    No
                                </c:when>
                                <c:otherwise>
                                    Unknown
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>

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
<input id="accessAttributeName" type="hidden" value="data-casualty-id">
<script type="text/javascript">
    util.initCrashAnalysis();
</script>