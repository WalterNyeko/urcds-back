<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="menu.reports" /></title>
    <meta name="menu" content="ReportsMenu" />
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="menu.reports" />
	</h2>
</div>
    <div class="content-wrapper">
        <p>&nbsp;</p>
        <table width="100%" align="left" cellpadding="4">
            <tr>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=CrashCauseByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByCrashCause"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=CollisionTypeByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByCollisionType"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=DistrictByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByDistrict"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=JunctionTypeByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByJunctionType"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=PoliceStationByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByPoliceStation"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=RoadSurfaceByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByRoadSurface"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=RoadwayCharacterByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByRoadwayCharacter"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=SurfaceConditionByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityBySurfaceCondition"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=SurfaceTypeByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityBySurfaceType"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=TimeRangeByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByTimeRange"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=VehicleFailureTypeByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByVehicleFailureType"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=WeatherByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByWeather"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=CasualtyAgeByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByCasualtyAgeGender"/>
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/reportsgen?reportName=VehicleTypeByCrashSeverity' />" target="_blank">
                        <fmt:message key="reports.crashSeverityByVehicleType"/>
                    </a>
                </td>
            </tr>
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