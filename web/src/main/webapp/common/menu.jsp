<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm"
	permissions="rolesAdapter">
	<div class="collapse navbar-collapse" id="navbar">
		<ul class="nav navbar-nav">
			<c:if test="${empty pageContext.request.remoteUser}">
				<li class="active"><a href="<c:url value="/login"/>"><fmt:message
							key="login.title" /></a></li>
			</c:if>
			<menu:displayMenu name="Home" />
			<menu:displayMenu name="CrashMenu" />
            <menu:displayMenu name="InjuryMenu" />
            <menu:displayMenu name="AnalysisMenu" />
            <menu:displayMenu name="MappingMenu" />
            <menu:displayMenu name="ReportsMenu" />
            <menu:displayMenu name="AdminMenu" />
			<menu:displayMenu name="UserMenu" />
			<menu:displayMenu name="Logout" />
		</ul>
	</div>
</menu:useMenuDisplayer>
