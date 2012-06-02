<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Create Application</h2>
<form action="controller" method="POST">
    <input type="hidden" name="scope" value="createApplication"/>
    Job ID: <input type="text" name="jobId" value="${requestScope.jobId}"/> <br/>
    Brief Bio: <input type="text" name="briefBio"/> <br/>
    Expect Salary: <input type="text" name="salary"/> <br/>
    <input type="submit" title="Submit"/>
</form>