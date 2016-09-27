<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="rcds.crashSelection" /></title>
    <meta name="menu" content="AnalysisMenu" />
</head>
<div class="col-sm-15">
    <div style="float: left">
        <h2>
            <fmt:message key="rcds.crashSelection" />
        </h2>
    </div>
    <br/><br/><br/>
    <div class="content-wrapper">
        <table align="center" cellpadding="10" cellspacing="10" class="selection">
            <tr>
                <td>
                    <a href="" onclick="return ui.loadSelectCrash({url: '<c:url value="/analysiscrashselect" />'});">
                        <fmt:message key="crashAnalysis.Select" />
                    </a>
                </td>
                <td>
                    <a href="<c:url value='/crashqueryform' />" class="show-loading"><fmt:message key="button.createQuery" /></a>
                </td>
                <td>
                    <a href="<c:url value='/crashquery'/>" class="show-loading">
                        <fmt:message key="crashQuery.runQuery" />
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="<c:url value='/crashselectioncrashes?recent=true' />" class="show-loading"><fmt:message key="crash.recentCrashes" /></a>
                </td>
                <td>
                    <a href="<c:url value='/crashselectioncrashes?recent=false' />" class="show-loading"><fmt:message key="crash.allCrashes" /></a>
                </td>
                <td>

                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <fmt:message key="crashSelection.selectCrashesBy" />
                </td>
            </tr>
            <tr>
                <td>
                    <table id="crashSeverity" class="crashform-blue">
                        <tr>
                            <th>
                                <appfuse:label styleClass="control-label" key="crashForm.crashSeverity" />
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="crashSeverity" items="${crashSeverities}" varStatus="status">
                                    <div class="selection-opt">
                                        <a href="<c:url value='/crashselectionseverity?id=' />${crashSeverity.id}">${crashSeverity.name}</a>
                                    </div>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table id="collisionType" class="crashform-blue">
                        <tr>
                            <th>
                                <appfuse:label styleClass="control-label" key="crashForm.collisionType" />
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="collisionType" items="${collisionTypes}" varStatus="status">
                                    <div class="selection-opt">
                                        <a href="<c:url value='/crashselectioncollisiontype?id=' />${collisionType.id}">${collisionType.name}</a>
                                    </div>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table id="casualtyClass" class="crashform-blue">
                        <tr>
                            <th>
                                <appfuse:label styleClass="control-label" key="crashAnalysis.casualtyClass" />
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="casualtyClass" items="${casualtyClasses}" varStatus="status">
                                    <div class="selection-opt">
                                        <a href="<c:url value='/crashselectioncasualtyclass?id=' />${casualtyClass.id}">${casualtyClass.name}</a>
                                    </div>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table id="district" class="crashform-blue">
                        <tr>
                            <th>
                                <appfuse:label styleClass="control-label" key="crashForm.district" />
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="district" items="${districts}" varStatus="status">
                                    <div class="selection-opt">
                                        <a href="<c:url value='/crashselectiondistrict?id=' />${district.id}">${district.name}</a>
                                    </div>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table id="vehicleType" class="crashform-blue">
                        <tr>
                            <th>
                                <appfuse:label styleClass="control-label" key="crashForm.vehicleType" />
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="vehicleType" items="${vehicleTypes}" varStatus="status">
                                    <div class="selection-opt">
                                        <a href="<c:url value='/crashselectionvehicletype?id=' />${vehicleType.id}">${vehicleType.name}</a>
                                    </div>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table id="crashCause" class="crashform-blue">
                        <tr>
                            <th>
                                <appfuse:label styleClass="control-label" key="crashForm.mainCauseOfCrash" />
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <c:forEach var="crashCause" items="${crashCauses}" varStatus="status">
                                    <div class="selection-opt">
                                        <a href="<c:url value='/crashselectioncause?id=' />${crashCause.id}">${crashCause.name}</a>
                                    </div>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">

</script>