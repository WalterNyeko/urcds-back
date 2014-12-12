<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashMapping.heading" /></title>
    <meta name="menu" content="MappingMenu" />
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdGBHIqR--XabhAy6UddDj4toKlEyJzAA">
    </script>
    <script type="text/javascript" src="/scripts/StyledMarker.js"></script>
</head>
<div class="col-sm-15 height-100">
    <table width="100%" cellpadding="0" cellspacing="0" class="height-100">
        <tr>
            <td width="80%" class="height-100">
                <div id="map-canvas" class="height-100 border-solid"></div>
            </td>
            <td width="20%" valign="top" style="padding: 5px 0px 0px 3px;">
                <table>
                    <c:forEach var="severity" items="${crashSeverities}">
                        <tr>
                            <td width="30" style="border: Solid #000 1px; color: #000;" align="center" bgcolor="${appfuse:getCrashSeverityColor(severity.id)}">
                                    ${fn:substring(severity.name, 0, 1)}
                            </td>
                            <td>- ${severity.name}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td style="border: Solid #000 1px; color: #000;" bgcolor="#E0FFFF">&nbsp;</td>
                        <td>- <fmt:message key="rcds.notSpecified"/></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        initMappingSurface();
        showCrashesInGoogleMaps();
    });
</script>