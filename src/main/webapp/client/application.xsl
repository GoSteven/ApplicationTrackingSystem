<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>My Application</h2>
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