<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="login.title" /></title>
<meta name="menu" content="Login" />
</head>
<body id="login">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td width="20%" valign="top"><img src="<c:url value='/images/ug-coa.jpg'/>" /><br />
				- <fmt:message key="rcds.ministryOfWorks" /><br /> - <fmt:message
					key="rcds.ministryOfHealth" /></td>
			<td width="60%" valign="top" align="center">
                <br/> <br/> <br/> <br/> <br/>
                <h2 class="form-signin-heading form-login-header" style="text-align: center;">
                    <fmt:message key="webapp.country" />&nbsp;
                    <fmt:message key="rcds.fullName" />
                </h2>
                <br />
                <form method="post" id="loginForm"
                    action="<c:url value='/j_security_check'/>"
                    onsubmit="sessionStorage.clear(); ui.loadingNotification();"
                    class="form-signin" autocomplete="off">
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissable">
                            <fmt:message key="errors.password.mismatch" />
                        </div>
                    </c:if>
                    <input type="text" name="j_username" id="j_username"
                        class="form-control"
                        placeholder="<fmt:message key="label.username"/>" required
                        tabindex="1">
                    <input type="password" class="form-control"
                        name="j_password" id="j_password" tabindex="2"
                        placeholder="<fmt:message key="label.password"/>" required>

                    <c:if test="${appConfig['rememberMeEnabled']}">
                        <label for="rememberMe" class="checkbox" style="text-align: left;"> <input
                            type="checkbox" name="_spring_security_remember_me"
                            id="rememberMe" tabindex="3" /> <fmt:message
                                key="login.rememberMe" /></label>
                    </c:if>

                    <button type="submit" class="btn btn-lg btn-primary btn-block"
                        name="login" tabindex="4">
                        <fmt:message key='button.login' />
                    </button>
                    <p style="text-align: left;">
                        <fmt:message key="login.passwordHint" />
                    </p>
                </form>
                <p style="display: none;">
                    <fmt:message key="login.signup">
                        <fmt:param>
                            <c:url value="/signup" />
                        </fmt:param>
                    </fmt:message>
                </p>
                <c:set var="scripts" scope="request">
                    <%@ include file="/scripts/login.js"%>
                </c:set>
                <p style="display: none;">
                    <fmt:message key="updatePassword.requestRecoveryTokenLink" />
                </p>
			</td>
			<td width="20%" valign="top"><img src="<c:url value='/images/upf_logo.png'/>" />
			</td>
		</tr>
	</table>
</body>