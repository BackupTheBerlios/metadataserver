<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="text" indent="no"/>

<xsl:template match="class"><root>
<xsl:variable name="classId" select="@id"/>
package <xsl:copy-of select="package_name"/>;

import java.*;


public class <xsl:copy-of select="class_name"/>
<xsl:variable name="extId" select="./extends/generalclass/@Id"/>
<xsl:if test="contains($extId, 'xmi')"> extends <xsl:copy-of select="./extends/generalclass"/>
</xsl:if>
{



<xsl:for-each select="//association_end">
	<xsl:call-template name="asso">
		<xsl:with-param name="ass" select="$classId"/>	
	</xsl:call-template>
</xsl:for-each>

<xsl:for-each select="//association_end">
	<xsl:call-template name="assoFunk">
		<xsl:with-param name="ass" select="$classId"/>	
	</xsl:call-template>
</xsl:for-each>

}
</root>
</xsl:template>

<xsl:template name="asso">
<xsl:param name="ass"/>
	<xsl:for-each select="association_type">
		<xsl:variable name="assoId" select="@Id"/>		
		<xsl:if test="not(contains($ass, $assoId))">			
			private <xsl:copy-of select="."/> _<xsl:copy-of select="."/>;
		</xsl:if>	
	</xsl:for-each>
</xsl:template>

<xsl:template name="assoFunk">
<xsl:param name="ass"/>
	<xsl:for-each select="association_type">
		<xsl:variable name="assoId" select="@Id"/>		
		<xsl:if test="not(contains($ass, $assoId))">			
			public void set_<xsl:copy-of select="."/>(<xsl:copy-of select="."/> arg){
				_<xsl:copy-of select="."/> = arg;
			}
			
			public <xsl:copy-of select="."/> get_<xsl:copy-of select="."/>(){
				return _<xsl:copy-of select="."/>;
			}
		</xsl:if>	
	</xsl:for-each>
</xsl:template>

	
</xsl:stylesheet>
