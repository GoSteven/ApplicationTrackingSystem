<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Review Application</h2>
                <form action="controller" method="POST">
                    <input type="hidden" name="scope" value="doReview"/>
                    <input type="hidden" name="applicationId" value="{//unsw.ats.entities.Application/applicationId}"/>

                    Job ID: <xsl:value-of select="unsw.ats.entities.Application/job/jobId" /> <br/>
                    Job Title: <xsl:value-of select="unsw.ats.entities.Application/job/jobTitle" /> <br/>
                    Job Desc: <xsl:value-of select="unsw.ats.entities.Application/job/jobDesc" /> <br/>
                    <hr/>
                    Application ID: <xsl:value-of select="unsw.ats.entities.Application/applicationId" /> <br/>
                    Applicant Name: <xsl:value-of select="unsw.ats.entities.Application/applicant/applicantName" /> <br/>
                    Brief Bio: <xsl:value-of select="unsw.ats.entities.Application/briefBio" /> <br/>
                    Expect Salary: <xsl:value-of select="unsw.ats.entities.Application/salary" /> <br/>
                    <hr/>
                    Decision:
                    <select name="decision">
                        <option value="true">accepted</option>
                        <option value="false">discard</option>
                    </select>
                    <br/>
                    Recommendation:
                    <input type="text" name="recommendation" />
                    <br/>
                    <input type="submit" title="Submit"/>
                </form>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>