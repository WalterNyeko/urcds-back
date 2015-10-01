<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashMapping.heading" /></title>
    <meta name="menu" content="MappingMenu" />
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdGBHIqR--XabhAy6UddDj4toKlEyJzAA&v=3.exp&libraries=drawing,geometry">
    </script>
    <script type="text/javascript" src="<c:url value='/scripts/marker-constants.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/StyledMarker.js'/>"></script>
</head>
<div class="col-sm-15 height-100">
    <table width="100%" cellpadding="0" cellspacing="0" class="height-100">
        <tr>
            <td width="80%" class="height-100">
                <div id="map-canvas" class="height-100 border-solid"></div>
            </td>
            <td width="20%" valign="top" style="padding: 5px 0px 0px 3px;">
                <p>
                    <select id="crashAttribute">
                        <option value="collisionType">Collision Type</option>
                        <option value="crashCause">Crash Cause</option>
                        <option selected value="crashSeverity">Crash Severity</option>
                        <option value="weightRange" data-weight="weight">Crash Weight</option>
                        <option value="junctionType">Junction Type</option>
                        <option value="policeStation">Police Station</option>
                        <option value="roadSurface">Road Surface</option>
                        <option value="roadwayCharacter">Roadway Character</option>
                        <option value="surfaceCondition">Surface Condition</option>
                        <option value="surfaceType">Surface Type</option>
                        <option value="vehicleFailureType">Vehicle Failure Type</option>
                        <option value="weather">Weather</option>
                    </select>
                </p>
                <table id="marker-legend">
                    <c:forEach var="severity" items="${crashSeverities}">
                        <tr>
                            <td width="30" style="border: Solid #000 1px; color: #000; font-weight: bold;" align="center" bgcolor="${appfuse:getCrashSeverityColor(severity.id)}">
                                    ${fn:substring(severity.name, 0, 1)}
                            </td>
                            <td>- ${severity.name}</td>
                        </tr>
                    </c:forEach>
                    <tr class="not-spec">
                        <td style="border: Solid #000 1px; color: #000;" bgcolor="#E0FFFF">&nbsp;</td>
                        <td>- <fmt:message key="rcds.notSpecified"/></td>
                    </tr>
                </table>
                <hr/>
                <table>
                    <tr>
                        <th>Show selected layers</th>
                    </tr>
                    <tr>
                        <td>
                            <input class="kml-layer" type="checkbox" id="police-regions" />
                            <label for="police-regions" class="form-label">Police Regions</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="kml-layer" type="checkbox" id="unra-a" />
                            <label for="unra-a" class="form-label">UNRA Class A Roads</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input class="kml-layer" type="checkbox" id="unra-b" />
                            <label for="unra-b" class="form-label">UNRA Class B Roads</label>
                        </td>
                    </tr>
                </table>
                <hr/>
                <table>
                    <tr>
                        <td>
                            <input type="checkbox" id="draw-enable" />
                            <label for="draw-enable" class="form-label">Enable drawing</label>
                        </td>
                    </tr>
                    <tr class="drawing-actions">
                        <td>
                            <a href="" class="non-click" onclick="clearDrawings()">Clear drawings</a>
                        </td>
                    </tr>
                    <tr class="drawing-actions">
                        <td>
                            <a href="" onclick="return analyzeFiltered()">Show in Analysis</a>
                            <c:url value="/analysisgisselect" var="formUrl" />
                            <form method="post" action="${formUrl}" id="selection-form" style="visibility: hidden">
                                <input type="hidden" id="crashIds" name="crashIds"/>
                            </form>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        util.initCrashData();
        $('.kml-layer').click(showCrashesInGoogleMaps);
        $('#crashAttribute').change(showCrashesInGoogleMaps);
        $('#draw-enable').change(function(evt) {
            showDrawingManager(this.checked, evt);
        });
        ui.initMappingSurface();
        showCrashesInGoogleMaps();
    });
</script>
<div id="crashdata"></div>