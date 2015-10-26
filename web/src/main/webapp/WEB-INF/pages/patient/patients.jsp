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
            <a href="<c:url value='/patientform'/>" class="non-click">
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
                        <th><fmt:message key="patient.modeOfTransport"/></th>
                        <th data-filter="false" data-sorter="false"><fmt:message key="rcds.actions"/></th>
                    </tr>
                </thead>
                <tbody>

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