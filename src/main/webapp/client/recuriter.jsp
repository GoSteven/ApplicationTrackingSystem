<html>
<head>
    <title>Recuriter Control Panel - Application Tracking System</title>
</head>
<body>
    <h2>Create Job</h2>
    <form action="controller" method="post">
        <input type="hidden" name="scope" value="createJob"/>
        Job Title: <input name="jobTitle" type="text"/>
        Description: <input name="jobDesc" type="text"/>
        Salary: <input name="salary" type="text"/>
        Location: <input name="location" type="text"/>
        Closing Date: <input name="closingDate" type="text"/>
        <input type="submit" title="Submit"/>
    </form>

    <h2>Assign Applications</h2>
    ....
</body>
</html>