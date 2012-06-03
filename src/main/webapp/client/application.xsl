<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>My Application</h2>
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
                        <tr>
                            <td>
                                <xsl:value-of select="//applicationId"/>
                            </td>
                            <td>
                                <xsl:value-of select="//applicant/applicantName"/>
                            </td>
                            <td>
                                <xsl:value-of select="//briefBio"/>
                            </td>
                            <td>
                                <xsl:value-of select="//unsw.ats.entities.Application/salary"/>
                            </td>
                            <td>
                                <xsl:value-of select="//status"/>
                            </td>
                            <td>
                                <a href="controller?scope=viewJob&amp;id={job/jobId}">
                                    <xsl:value-of select="//job/jobTitle"/>
                                </a>
                            </td>
                            <td>
                                <xsl:if test="//status='application received'">
                                    <a href="controller?scope=editApplication&amp;id={applicationId}">
                                        Edit
                                    </a>
                                    <a href="controller?scope=withdraw&amp;id={applicationId}">
                                        Withdraw
                                    </a>
                                </xsl:if>
                                <xsl:if test="//status='final decision made'">
                                    Closed:
                                    <xsl:if test="//finalIsAccepted='true'">
                                        Accepted
                                    </xsl:if>
                                    <xsl:if test="//finalIsAccepted='false'">
                                        Rejected
                                    </xsl:if>
                                </xsl:if>
                            </td>

                        </tr>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>