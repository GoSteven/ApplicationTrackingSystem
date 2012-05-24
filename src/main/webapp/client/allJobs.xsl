<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>All jobs</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>jobId</th>
                        <th>Recuriter Name</th>
                        <th>Job Title</th>
                        <th>Job Description</th>
                        <th>Salary</th>
                        <th>Status</th>
                    </tr>
                    <xsl:for-each select="list/unsw.ats.entities.Job">
                    <tr>
                        <td><xsl:value-of select="jobId" /></td>
                        <td><xsl:value-of select="recuriter/recruiterName" /></td>
                        <td><xsl:value-of select="jobTitle" /></td>
                        <td><xsl:value-of select="jobDesc" /></td>
                        <td><xsl:value-of select="salary" /></td>
                        <td><xsl:value-of select="status" /></td>
                    </tr>
                    </xsl:for-each>

                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>