<html>
<head>
    <title>Client - Application Tracking System</title>
</head>
<body>
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
