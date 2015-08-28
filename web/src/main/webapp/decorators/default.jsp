<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
    <head>
        <meta http-equiv="Cache-Control" content="no-store" />
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="<c:url value='/images/favicon.ico'/>" />
        <title><decorator:title /> | <fmt:message key="webapp.name" /></title>
        <t:assets />
        <script type="text/javascript" src="<c:url value='/scripts/script.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/util.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/gis-helper.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/ajax.js'/>"></script>
        <decorator:head />
        <script type="text/javascript">
            ui.init();
        </script>
    </head>
    <body
        <decorator:getProperty property="body.id" writeEntireProperty="true"/>
        <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
        <c:set var="currentMenu" scope="request">
            <decorator:getProperty property="meta.menu" />
        </c:set>

        <div class="navbar navbar-inverse navbar-fixed-top drop-shadow" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#navbar">
                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value='/'/>"><fmt:message
                        key="webapp.name" /></a>
            </div>

            <%@ include file="/common/menu.jsp"%>
            <c:if test="${pageContext.request.locale.language ne 'en'}">
                <div id="switchLocale">
                    <a href="<c:url value='/?locale=en'/>"> <fmt:message
                            key="webapp.name" /> in English
                    </a>
                </div>
            </c:if>
        </div>

        <div class="container" id="content">
            <%@ include file="/common/messages.jsp"%>
            <div class="row">
                <decorator:body />

                <c:if test="${currentMenu == 'AdminMenu'}">
                    <div class="col-sm-2">
                        <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm"
                            permissions="rolesAdapter">
                            <menu:displayMenu name="AdminMenu" />
                        </menu:useMenuDisplayer>
                    </div>
                </c:if>
                <%@ include file="/common/session.jsp"%>
            </div>
        </div>

        <div id="footer" class="container navbar-fixed-bottom">
            <span class="col-sm-6 text-left"><fmt:message
                    key="webapp.version" /> <c:if
                    test="${pageContext.request.remoteUser != null}">
                | <fmt:message key="user.status" /> ${pageContext.request.remoteUser}
                </c:if> </span>
            <span class="col-sm-6 text-right">
                <img src="<c:url value='/images/ugandan.png'/>" height="30" title="Proudly Ugandan" />
                <img src="<c:url value='/images/proudly_ugandan.png'/>" height="20" title="Proudly Ugandan" />
            </span>
        </div>
    </body>
</html>
