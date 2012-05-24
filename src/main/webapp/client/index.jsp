<html>
<head>
    <title>Client - Application Tracking System</title>
</head>
<body>
Recuriter Id: 5e6d21e0-0132-4f3e-a868-7ce0de05f706 <br/>
Reviewer  Id: b8af6b41-0fda-495a-8deb-af539dddbe90 <br/>
Applicant Id: 3f9d6286-e131-42fe-a73f-d85f242bdf62 <br/>
    <form action="controller">
        <input type="hidden" name="scope" value="init"/>
        UserId: <input name="userId" type="text"/>
        <select name="userType" title="User Type">
            <option value="recuriter">recuriter</option>
            <option value="reviewer">reviewer</option>
            <option value="applicant">applicant</option>
        </select>
        <input type="submit" title="Submit"/>

    </form>
</body>
</html>
