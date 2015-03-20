<%-- Error Messages --%>
<c:if test="${not empty errors}">
	<div class="alert alert-danger alert-dismissable notification">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:forEach var="error" items="${errors}">
			<c:out value="${error}" />
			<br />
		</c:forEach>
	</div>
	<c:remove var="errors" />
</c:if>

<%-- Success Messages --%>
<c:if test="${not empty successMessages}">
	<div class="alert alert-success alert-dismissable notification auto-dismiss">
        <b>Success!</b>&nbsp;
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:forEach var="msg" items="${successMessages}">
			<c:out value="${msg}" />
			<br />
		</c:forEach>
	</div>
	<c:remove var="successMessages" scope="session" />
    <script>
        $(window).load(function() {
            setTimeout(function() {
                $(".auto-dismiss").fadeOut(2000);
            }, 7000);
        });
    </script>
</c:if>