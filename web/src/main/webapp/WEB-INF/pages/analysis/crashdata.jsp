<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="crashdata">
    <c:if test="${not empty crashesJSON}">
        <input id='crashesJSON' type='hidden' value='${crashesJSON}' />
    </c:if>
    <c:if test="${not empty crashAttributesJSON}">
        <input id='crashAttributesJSON' type='hidden' value='${crashAttributesJSON}' />
    </c:if>
</div>