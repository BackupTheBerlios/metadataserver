<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/"><xsl:copy-of select="."/>
<xsl:apply-templates select="*|@*"/>
</xsl:template>

<xsl:template match="text()">
<xsl:value-of select="."/>
</xsl:template>

<xsl:template match="node()">
<xsl:copy-of select="."/>
</xsl:template>

</xsl:stylesheet>
