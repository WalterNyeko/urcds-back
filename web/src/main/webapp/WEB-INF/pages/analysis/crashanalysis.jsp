<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashAnalysis.heading" /></title>
    <meta name="menu" content="AnalysisMenu" />
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdGBHIqR--XabhAy6UddDj4toKlEyJzAA"></script>
    <script type="text/javascript" src="/scripts/crash-validator.js"></script>
</head>
<div class="col-sm-15">
	<h2>
		<fmt:message key="crashAnalysis.heading" />
	</h2>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="60%">
                <c:import url="analysismenu.jsp" />
            </td>
            <td width="40%">
                <ul class="nav nav-tabs" style="float: right;">
                    <li class="active"><a href=""  class="non-click"><fmt:message key="crashList.crashes" /></a></li>
                    <li><a href="/analysisvehicles"><fmt:message key="crashAnalysis.vehicles" /></a></li>
                    <li><a href="/analysiscasualties"><fmt:message key="crashAnalysis.casualties"/></a></li>
                </ul>
            </td>
        </tr>
    </table>
    <div class="content-wrapper">
        <display:table name="crashList"
            class="table table-condensed table-striped table-hover" requestURI=""
            id="crashList" export="false" pagesize="50" decorator="com.sweroad.webapp.decorator.CrashAnalysisDecorator">
            <display:column property="tarNo" sortable="true"
                titleKey="crash.tarNo" />
            <display:column property="townOrVillage" sortable="true"
                titleKey="crash.townOrVillage" />
            <display:column property="road" sortable="true" titleKey="crash.road" />
            <display:column property="crashSeverity.name" sortable="true" titleKey="crash.severity" />
            <display:column property="vehicleCount" sortable="true"
                style="text-align: center;" titleKey="crash.vehicles" />
            <display:column property="casualtyCount" sortable="true"
                style="text-align: center;" titleKey="crash.casualties" />
            <display:column property="crashDisplayDate" sortable="true"
                style="text-align: center;" titleKey="crash.date" />
            <display:column property="policeStation.name" sortable="true"
                titleKey="crash.policeStation" />
            <display:column property="policeStation.district.name" sortable="true"
                titleKey="crash.district" />
            <display:column property="actions" sortable="true" titleKey="rcds.actions" />
            <display:setProperty name="paging.banner.item_name">
                <fmt:message key="crashList.crash" />
            </display:setProperty>
            <display:setProperty name="paging.banner.items_name">
                <fmt:message key="crashList.crashes" />
            </display:setProperty>
            <display:setProperty name="export.excel.filename">
                <fmt:message key="crashList.title" />.xls</display:setProperty>
            <display:setProperty name="export.csv.filename">
                <fmt:message key="crashList.title" />.csv</display:setProperty>
            <display:setProperty name="export.pdf.filename">
                <fmt:message key="crashList.title" />.pdf</display:setProperty>
        </display:table>
    </div>
    <p>&nbsp;</p>
</div>
<input id='crashesJSON' type='hidden' value='${crashesJSON}' />
<input id='crashAttributesJSON' type='hidden' value='${crashAttributesJSON}' />
<input id="accessAttributeName" type="hidden" value="data-crashanalysis-id">
<script type="text/javascript">
    $(document).ready(function() {
        localStorage.setItem("crashesJSON", null);
        localStorage.setItem("crashAttributesJSON", null);
        $("#gMaps").hide();
        var crashJsonText = $.trim($("#crashesJSON").val());
        var attributesJsonText = $.trim($("#crashAttributesJSON").val());
        if(crashJsonText.length > 0) {
            var crashJson = '{"crashes" : ' + crashJsonText + '}';
            localStorage.setItem("crashesJSON", crashJson);
            localStorage.setItem("crashAttributesJSON", attributesJsonText);
            $("#gMaps").show();
        }
    });
    jQuery(window).load(function(){
        highlightLastAccessedObject();
    });
</script>