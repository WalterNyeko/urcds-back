<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="crashAnalysis.heading" /></title>
    <meta name="menu" content="AnalysisMenu" />
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
                    <li><a href="/analysis"><fmt:message key="crashList.crashes" /></a></li>
                    <li class="active"><a href="" class="non-click"><fmt:message key="crashAnalysis.vehicles" /></a></li>
                    <li><a href="/analysiscasualties"><fmt:message key="crashAnalysis.casualties"/></a></li>
                </ul>
            </td>
        </tr>
    </table>
    <div class="content-wrapper">
        <display:table name="vehicleList"
                       class="table table-condensed table-striped table-hover" requestURI=""
                       id="vehicleList" export="false" pagesize="50" decorator="com.sweroad.webapp.decorator.VehicleAnalysisDecorator">
            <display:column property="crashCode" sortable="true"
                            titleKey="crash.crashNo" />
            <display:column property="number" sortable="true"
                            style="text-align: center;"  titleKey="crashAnalysis.vehicleNo" />
            <display:column property="vehicleType.name" sortable="true" titleKey="crashForm.vehicleType" />
            <display:column property="driverLicense" sortable="true" titleKey="crashAnalysis.driverLicense" />
            <display:column property="driver.gender" sortable="true"
                            style="text-align: center;" titleKey="crashAnalysis.driverSex" />
            <display:column property="driver.age" sortable="true"
                            style="text-align: center;" titleKey="crashAnalysis.driverAge" />
            <display:column property="driverBeltUsed" sortable="true" titleKey="crashAnalysis.beltHelmetUsed" />
            <display:column property="driver.casualtyType.name" sortable="true" titleKey="crashAnalysis.casualtyType" />
            <display:setProperty name="paging.banner.item_name">
                <fmt:message key="crash.vehicle" />
            </display:setProperty>
            <display:setProperty name="paging.banner.items_name">
                <fmt:message key="crashAnalysis.vehicles" />
            </display:setProperty>
        </display:table>
    </div>
    <p>&nbsp;</p>
</div>
<input id='crashesJSON' type='hidden' value='${crashesJSON}' />
<input id="accessAttributeName" type="hidden" value="data-vehicle-id">
<script type="text/javascript">
    $(document).ready(function() {
        localStorage.setItem("crashesJSON", null);
        $("#gMaps").hide();
        var jsonText = $.trim($("#crashesJSON").val());
        if(jsonText.length > 0) {
            var crashJson = '{"crashes" : ' + jsonText + '}';
            localStorage.setItem("crashesJSON", crashJson);
            $("#gMaps").show();
        }
    });
    jQuery(window).load(function(){
        highlightLastAccessedObject();
    });
</script>