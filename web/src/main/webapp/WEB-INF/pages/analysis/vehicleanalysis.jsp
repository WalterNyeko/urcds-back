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
    <br/>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="60%">
                <c:import url="analysismenu.jsp" />
            </td>
            <td width="40%">
                <ul class="nav nav-tabs" style="float: right;">
                    <li><a href="<c:url value='/analysis'/>"><fmt:message key="crashList.crashes" /></a></li>
                    <li class="active"><a href="" class="non-click"><fmt:message key="crashAnalysis.vehicles" /></a></li>
                    <li><a href="<c:url value='/analysiscasualties'/>"><fmt:message key="crashAnalysis.casualties"/></a></li>
                </ul>
            </td>
        </tr>
    </table>
    <div class="content-wrapper">
        <div class="tablesorter-wrapper">
            <table class="tablesorter tablesorter-ice" width="100%" id="vehicleList">
                <thead>
                <tr>
                    <th><fmt:message key="crash.crashNo"/></th>
                    <th><fmt:message key="crashAnalysis.vehicleNo"/></th>
                    <th><fmt:message key="crashForm.vehicleType"/></th>
                    <th><fmt:message key="crashAnalysis.driverLicense"/></th>
                    <th><fmt:message key="crashAnalysis.driverSex"/></th>
                    <th><fmt:message key="crashAnalysis.driverAge"/></th>
                    <th><fmt:message key="crashAnalysis.beltHelmetUsed"/></th>
                    <th><fmt:message key="crashAnalysis.casualtyType"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="vehicle" items="${vehicles}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value='/crashview?id='/>${vehicle.crash.id}" data-vehicle-id="${vehicle.id}">
                                ${vehicle.crash.uniqueCode}
                            </a>
                        </td>
                        <td>${vehicle.number}</td>
                        <td>${vehicle.vehicleType.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${vehicle.driver.licenseValid eq true}">
                                    Valid
                                </c:when>
                                <c:when test="${vehicle.driver.licenseValid eq false}">
                                    Not Valid
                                </c:when>
                                <c:otherwise>
                                    Unknown
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td align="center">${vehicle.driver.gender}</td>
                        <td align="right">${vehicle.driver.age}</td>
                        <td>
                            <c:choose>
                                <c:when test="${vehicle.driver.beltUsed eq true}">
                                    Yes
                                </c:when>
                                <c:when test="${vehicle.driver.beltUsed eq false}">
                                    No
                                </c:when>
                                <c:otherwise>
                                    Unknown
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${vehicle.driver.casualtyType.name}</td>
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
<input id="accessAttributeName" type="hidden" value="data-vehicle-id">
<script type="text/javascript">
    $(document).ready(function() {
        initCrashAnalysis();
    });
    jQuery(window).load(function() {
        highlightLastAccessedObject();
        $('.tablesorter-wrapper').height($(window).height() - 310);
    });
</script>