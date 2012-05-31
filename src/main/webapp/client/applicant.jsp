<html>
<head>
    <title>Applicant Control Panel - Application Tracking System</title>
</head>
<body>
    <h2>Search</h2>
    <form action="/search" name="">
        <select name="searchKey" class="">
            <option class="" value="title">Job Title</option>
            <option class="" value="location">Job Location</option>
            <option class="" value="recuriterName">Recuriter Name</option>
            <option class="" value="salary">Salary Range</option>
            <option class="" value="status">Job Status</option>
        </select>
        <input type="text" />
        <input type="submit">
    </form>
    <h2>All Jobs</h2>
    <a href="controller?scope=viewAllJobs">View all jobs</a>
    <h2>Create Application</h2>
    <form action="controller" method="POST">
        <input type="hidden" name="scope" value="createApplication"/>
        Job ID: <input type="text" name="jobId"/> <br/>
        Brief Bio: <input type="text" name="briefBio"/> <br/>
        Expect Salary: <input type="text" name="salary"/> <br/>
        <input type="submit" title="Submit"/>
    </form>
    <h2>My Applications</h2>
    <a href="controller?scope=myApplications">View my applications</a>
</body>
</html>