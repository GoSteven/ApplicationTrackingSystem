<html>
<head>
    <title>Applicant Control Panel - Application Tracking System</title>
</head>
<body>
    <h2>Search</h2>
    <form action="./controller" method="GET">
        <input type="hidden" name="scope" value="viewAllJobs"/>
        Title<input type="text" name="title"/>
        From<input type="text" name="from"/>
        To<input type="text" name="to"/>
        Location<input type="text" name="location"/>
        State
        <select name="state">
            <option value="Open">Open</option>
            <option value="Closed">Closed</option>
        </select>
        <input type="submit" name="Search"/>
    </form>
    <%--<form action="/search" name="">--%>
        <%--<select name="searchKey" class="">--%>
            <%--<option class="" value="title">Job Title</option>--%>
            <%--<option class="" value="location">Job Location</option>--%>
            <%--<option class="" value="recuriterName">Recuriter Name</option>--%>
            <%--<option class="" value="salary">Salary Range</option>--%>
            <%--<option class="" value="status">Job Status</option>--%>
        <%--</select>--%>
        <%--<input type="text" />--%>
        <%--<input type="submit">--%>
    <%--</form>--%>
    <h2>All Jobs</h2>
    <a href="controller?scope=viewAllJobs">View all jobs</a>

    <h2>My Applications</h2>
    <a href="controller?scope=myApplications">View my applications</a>
</body>
</html>