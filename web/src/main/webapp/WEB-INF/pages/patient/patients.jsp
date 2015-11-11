<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="menu.patients" /></title>
    <meta name="menu" content="PatientMenu" />
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/theme.ice.css'/>">
    <link class="theme ice" rel="stylesheet" href="<c:url value='/styles/tablesorter/addons/pager/jquery.tablesorter.pager.css'/>">
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/jquery.tablesorter.widgets.min.js'/>"></script>
    <script src="<c:url value='/scripts/tablesorter/addons/pager/jquery.tablesorter.pager.min.js'/>"></script>
</head>
<div class="col-sm-15">
    <h2>
        <fmt:message key="patient.crashPatients" />
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
            <a href="<c:url value='/patientform'/>">
                <fmt:message key="button.addPatient" />
            </a>
        </security:authorize>
    </div>
    <div class="content-wrapper">
        <div class="tablesorter-wrapper">
            <table class="tablesorter tablesorter-ice" width="100%" id="patientList">
                <thead>
                    <tr>
                        <th><fmt:message key="patient.patientNo"/></th>
                        <th><fmt:message key="patient.gender"/></th>
                        <th><fmt:message key="patient.age"/></th>
                        <th><fmt:message key="patient.hospital"/></th>
                        <th><fmt:message key="patient.village"/></th>
                        <th><fmt:message key="crash.district"/></th>
                        <th><fmt:message key="patient.dateInjured"/></th>
                        <th><fmt:message key="patient.transportMode"/></th>
                        <th data-filter="false" data-sorter="false"><fmt:message key="rcds.actions"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="patient" items="${patients}" varStatus="status">
                        <tr>
                            <td>${empty(patient.hospitalInpatientNo) ? patient.hospitalOutpatientNo : patient.hospitalInpatientNo}</td>
                            <td align="center">${patient.gender}</td>
                            <td align="right">${patient.age}</td>
                            <td>${patient.hospital.name}</td>
                            <td>${patient.village}</td>
                            <td>${patient.district.name}</td>
                            <td align="right">${patient.injuryDateTimeString}</td>
                            <td>${patient.transportMode.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${patient.removed eq true}">
                                        <a href="<c:url value='/patientview'/>?id=${patient.id}" class="show-loading" alt="View patient" onclick="setAccessedObject(this)" data-patients-id="${patient.id}">
                                            <img src="<c:url value='/images/bt_View.gif'/>" alt="View" title="View" hspace="4">
                                        </a>
                                        <a href="<c:url value='/patientrestore'/>?id=${patient.id}" alt="Restore patient" onclick="setAccessedObject(this); return ui.confirmDialog({message : 'Restore patient?', aLink : this});">
                                            <img src="<c:url value='/images/bt_Restore.gif'/>" alt="Restore" title="Restore" hspace="4">
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/patientview'/>?id=${patient.id}" class="show-loading" alt="View patient" onclick="setAccessedObject(this)" data-patients-id="${patient.id}">
                                            <img src="<c:url value='/images/bt_View.gif'/>" alt="View" title="View" hspace="4">
                                        </a>
                                        <c:if test="${patient.editable}">
                                            <a href="<c:url value='/patientform'/>?id=${patient.id}" class="show-loading" alt="Edit patient" onclick="setAccessedObject(this)">
                                                <img src="<c:url value='/images/bt_Edit.gif'/>" alt="Edit" title="Edit" hspace="4">
                                            </a>
                                        </c:if>
                                        <c:if test="${patient.removable}">
                                            <a href="<c:url value='/patientremove'/>?id=${crash.id}" alt="Remove patient" onclick="setAccessedObject(this); return ui.confirmDialog({message : 'Remove patient?', aLink : this});">
                                                <img src="<c:url value='/images/bt_Remove.gif'/>" alt="Remove" title="Remove" hspace="4">
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
    ui.initLastAccessedObject();
    $(document).ready(function() {
        util.initTableSorter('#patientList', 'patients');
    });
</script>