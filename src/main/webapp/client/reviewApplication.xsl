<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Review Application</h2>
                <form action="controller" method="POST">
                    <input type="hidden" name="scope" value="updateApplication"/>
                    Job ID: <xsl:value-of select="unsw.ats.entities.Application/job/jobId" /> <br/>
                    Job Title: <xsl:value-of select="unsw.ats.entities.Application/job/jobTitle" /> <br/>
                    <input type="hidden" name="id">
                        <xsl:attribute name="value">
                            <xsl:value-of select="unsw.ats.entities.Application/applicationId" />
                        </xsl:attribute>
                    </input>
                    Brief Bio:
                    <input type="text" name="briefBio">
                        <xsl:attribute name="value">
                            <xsl:value-of select="unsw.ats.entities.Application/briefBio" />
                        </xsl:attribute>
                    </input> <br/>
                    Expect Salary:
                    <input type="text" name="salary">
                        <xsl:attribute name="value">
                            <xsl:value-of select="unsw.ats.entities.Application/salary" />
                        </xsl:attribute>
                    </input> <br/>
                    <input type="submit" title="Submit"/>
                </form>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>