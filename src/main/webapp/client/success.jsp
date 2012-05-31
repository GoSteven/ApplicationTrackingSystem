<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2></h2><c:out value="${requestScope.successMessage}"/></h2>
<br />
<a href="${requestScope.successHtml}" >${requestScope.linkName}</a>