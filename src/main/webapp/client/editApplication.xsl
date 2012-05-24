<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Edit Application</h2>
                <form action="controller" method="POST">
                    <input type="hidden" name="scope" value="submitEditApplication"/>
                    Job ID: <input type="text" name="jobId"/> <br/>
                    Brief Bio: <input type="text" name="briefBio"/> <br/>
                    Expect Salary: <input type="text" name="salary"/> <br/>
                    <input type="submit" title="Submit"/>
                </form>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Application Id</th>
                        <th>Brief Bio</th>
                        <th>Expected Salary</th>
                        <th>Status</th>
                        <th>View Job Detail</th>
                    </tr>
                    <xsl:for-each select="list/unsw.ats.entities/Application">
                        <tr>
                            <th><xsl:value-of select="applicationId" /></th>
                            <th><xsl:value-of select="briefBio" /></th>
                            <th><xsl:value-of select="salary" /></th>
                            <th><xsl:value-of select="status" /></th>
                            <th><xsl:value-of select="job/jobId" /></th>
                        </tr>
                    </xsl:for-each>
                    >             </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>