<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>
                    <xsl:value-of select="unsw.ats.entities.Job/jobId"/>
                </title>
            </head>
            <body>
                <h2>
                    <xsl:value-of select="unsw.ats.entities.Job/jobId"/>
                </h2>
                <table border="1">
                    <tr>
                        <td bgcolor="#9acd32">Recuriter Name</td>
                        <td>
                            <xsl:value-of select="unsw.ats.entities.Job/recuriter/recruiterName"/>
                        </td>
                    </tr>
                    <tr>
                        <td bgcolor="#9acd32">Job Title</td>
                        <td>
                            <xsl:value-of select="unsw.ats.entities.Job/jobTitle"/>
                        </td>
                    </tr>
                    <tr>
                        <td bgcolor="#9acd32">Job Description</td>
                        <td>
                            <xsl:value-of select="unsw.ats.entities.Job/jobDesc"/>
                        </td>
                    </tr>
                    <tr>
                        <td bgcolor="#9acd32">Salary</td>
                        <td>
                            <xsl:value-of select="unsw.ats.entities.Job/salary"/>
                        </td>
                    </tr>
                    <tr>
                        <td bgcolor="#9acd32">Status</td>
                        <td>
                            <xsl:value-of select="unsw.ats.entities.Job/status"/>
                        </td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>


        <!--<?xml version="1.0" encoding="ISO-8859-1"?>-->
        <!--<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">-->
        <!--<xsl:output method="html" indent="no"/>-->
        <!--<xsl:strip-space elements="*"/>-->
        <!--<xsl:template match="/">-->
        <!--<html>-->
        <!--<head>-->
        <!--<title><xsl:value-of select="unsw.ats.entities.Job/jobId"/></title>-->
        <!--</head>-->
        <!--<body>-->
        <!--<h2><xsl:value-of select="unsw.ats.entities.Job/jobId"/></h2>-->
        <!--<table border="1">-->
        <!--<tr>-->
        <!--<td>Recuriter Name</td>-->
        <!--<td><xsl:value-of select="unsw.ats.entities.Job/recuriter/recuriterName"/>-->
        <!--</td></tr>-->
        <!--<tr>-->
        <!--<td>Job Title</td>-->
        <!--<td><xsl:value-of select="unsw.ats.entities.Job/jobTitle"/>-->
        <!--</td></tr>-->
        <!--<tr>-->
        <!--<td>Job Description</td>-->
        <!--<td><xsl:value-of select="unsw.ats.entities.Job/jobDesc"/>-->
        <!--</td></tr>-->
        <!--<tr>-->
        <!--<td>Salary</td>-->
        <!--<td><xsl:value-of select="unsw.ats.entities.Job/salary"/>-->
        <!--</td></tr>-->
        <!--<tr>-->
        <!--<td>Status</td>-->
        <!--<td><xsl:value-of select="unsw.ats.entities.Job/status"/>-->
        <!--</td></tr>-->
        <!--</table>-->
        <!--</body>-->
        <!--</html>-->
        <!--</xsl:template>-->

        <!--</xsl:stylesheet>-->

