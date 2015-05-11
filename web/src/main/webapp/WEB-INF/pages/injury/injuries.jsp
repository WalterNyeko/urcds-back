<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="menu.injuries" /></title>
    <meta name="menu" content="InjuryMenu" />
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="injury.injuryList" />
	</h2>
    <div id="actions" class="btn=group">
    <security:authorize url="/app/crashform*">
        <a href="<c:url value='/injuryform'/>" onclick="return false;">
            <fmt:message key="button.addInjury" />
        </a>
    </security:authorize>
</div>
    <div class="content-wrapper">
        <table class="table table-condensed table-striped table-hover">
            <thead>
                <tr>
                    <th class="sortable"><a href="" onclick="return false">Patient No.</a></th>
                    <th class="sortable"><a href="" onclick="return false">Gender</a></th>
                    <th class="sortable"><a href="" onclick="return false">Age</a></th>
                    <th class="sortable"><a href="" onclick="return false">Hospital</a></th>
                    <th class="sortable"><a href="" onclick="return false">Village</a></th>
                    <th class="sortable"><a href="" onclick="return false">District</a></th>
                    <th class="sortable"><a href="" onclick="return false">Date Injured</a></th>
                    <th class="sortable"><a href="" onclick="return false">Mode of Transport</a></th>
                    <th class="sortable"><a href="" onclick="return false">Actions</a></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </tbody>
        </table>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </div>
</div>