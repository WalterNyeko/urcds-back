<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashList.title" /></title>
    <meta name="menu" content="CrashMenu" />
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdGBHIqR--XabhAy6UddDj4toKlEyJzAA">
    </script>
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/theme.ice.css'/>">
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/addons/pager/jquery.tablesorter.pager.css'/>">
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.widgets.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/addons/pager/jquery.tablesorter.pager.min.js'/>"></script>
    <script src="<c:url value='/scripts/crash-validator.js'/>"></script>
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="crashList.heading" />
	</h2>
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
        <security:authorize url="/app/crashform*">
            <a href="<c:url value='/crashform' />" id="addCrash"> <fmt:message
                    key="button.addCrash" />
            </a>
        </security:authorize>
    </div>
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
                                <c:choose>
                                    <c:when test="${crash.removed eq true}">
                                        <a href="<c:url value='/crashrestore'/>?id=${crash.id}" alt="Restore crash" onclick="setAccessedObject(this); return confirmDialog({message : 'Restore crash?', aLink : this});">
                                            <img src="<c:url value='/images/bt_Restore.gif'/>" alt="Restore" title="Restore" hspace="4">
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/crashview'/>?id=${crash.id}" alt="View crash" onclick="setAccessedObject(this)" data-crashes-id="${crash.id}">
                                            <img src="<c:url value='/images/bt_View.gif'/>" alt="View" title="View" hspace="4">
                                        </a>
                                        <c:if test="${crash.editable}">
                                            <a href="<c:url value='/crashform'/>?id=${crash.id}" alt="Edit crash" onclick="setAccessedObject(this)">
                                                <img src="<c:url value='/images/bt_Edit.gif'/>" alt="Edit" title="Edit" hspace="4">
                                            </a>
                                        </c:if>
                                        <c:if test="${crash.removable}">
                                            <a href="<c:url value='/crashremove'/>?id=${crash.id}" alt="Remove crash" onclick="setAccessedObject(this); return confirmDialog({message : 'Remove crash?', aLink : this});">
                                                <img src="<c:url value='/images/bt_Remove.gif'/>" alt="Remove" title="Remove" hspace="4">
                                            </a>
                                        </c:if>
                                        <c:if test="${crash.latitudeNumeric ne null and crash.longitudeNumeric ne null}">
                                            <a href="" alt="View on Map" onclick="setAccessedObject(this); return quickMapView('${crash.uniqueCode}', ${crash.latitudeNumeric}, ${crash.longitudeNumeric});">
                                                <img src="<c:url value='/images/gglMap.png'/>" alt="View on Map" title="View on Map" hspace="4" height="18">
                                            </a>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>

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
<script type="text/javascript">

    $(window).load(function(){
        highlightLastAccessedObject();
        $('.tablesorter-wrapper').height($(window).height() - 320);
    });
    $(document).ready(function() {
        util.initTableSorter('#crashList', 'crashes');
    });
</script>
<input id="accessAttributeName" type="hidden" value="data-crashes-id">