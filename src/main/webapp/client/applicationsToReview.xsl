<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Applications to review</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Application Id</th>
                        <th>Applicant Name</th>
                        <th>Brief Bio</th>
                        <th>Expected Salary</th>
                        <th>Status</th>
                        <th>View Job Detail</th>
                        <th></th>
                    </tr>
                    <xsl:for-each select="list/unsw.ats.entities.Application">
                    <tr>
                        <td><xsl:value-of select="applicationId" /></td>
                        <td><xsl:value-of select="applicant/applicantName" /></td>
                        <td><xsl:value-of select="briefBio" /></td>
                        <td><xsl:value-of select="salary" /></td>
                        <td><xsl:value-of select="status" /></td>
                        <td>
                            <a href="controller?scope=viewJob&amp;id={job/jobId}">
                                <xsl:value-of select="job/jobTitle" />
                            </a>
                        </td>
                        <td>
                            <a href="controller?scope=reviewApplication&amp;id={applicationId}">
                                Review
                            </a>
                        </td>

                        <!--<td> <a href=controller?scope=editApplication&id=<xsl:value-of select="job/jobId"/> >Edit Application</xsl:value-of> </td>-->
                    </tr>
                    </xsl:for-each>
>             </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>