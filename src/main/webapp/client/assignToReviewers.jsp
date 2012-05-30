<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Assign Application to Reviewers - Application Tracking System</title>
</head>
<body>
    <h2>Assign application ${requestScope.id} to: </h2>
    <form action="controller?scope=doAssign" method="POST">
        <select name="reviewer" multiple="multiple">
            <c:forEach items="${reviewers}" var="reviewer">
                <option value="${reviewer.id}">${reviewer.reviewerName}</option>
            </c:forEach>
        </select>
        <input type="submit"/>

    </form>

</body>
</html>