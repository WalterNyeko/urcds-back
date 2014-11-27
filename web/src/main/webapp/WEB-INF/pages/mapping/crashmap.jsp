<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashAnalysis.heading" /></title>
    <meta name="menu" content="MappingMenu" />
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdGBHIqR--XabhAy6UddDj4toKlEyJzAA">
    </script>
</head>
<div class="col-sm-15 height-100">
    <table width="100%" cellpadding="0" cellspacing="0" class="height-100">
        <tr>
            <td width="80%" class="height-100">
                <div id="map-canvas" class="height-100 border-solid"></div>
            </td>
            <td width="20%">

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