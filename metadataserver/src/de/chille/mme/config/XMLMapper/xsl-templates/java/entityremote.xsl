<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="text" indent="no"/>

<xsl:template match="class"><root>
<xsl:variable name="classId" select="@id"/>
package <xsl:copy-of select="package_name"/>;

import java.*;


public class <xsl:copy-of select="class_name"/>
<xsl:variable name="extId" select="extends/generalclass/@xmi"/>
<xsl:if test="contains($extId, 'id')">
extends <xsl:copy-of select="./extends/generalclass"/>
</xsl:if>
{

<xsl:for-each select="//association">
	<xsl:call-template name="variable">
	</xsl:call-template>
</xsl:for-each>

<xsl:for-each select="//association">
	<xsl:call-template name="association">
	</xsl:call-template>
</xsl:for-each>

}
</root>
</xsl:template>

<xsl:template name="variable">
	<xsl:for-each select="association_end">
		<xsl:copy></xsl:copy> 
		<xsl:variable name="kind" select="@aggregation"/>
		<xsl:if test="not(contains($kind, 'none'))"></xsl:if>
		private <xsl:value-of select="association_type"/> _<xsl:value-of select="association_type"/>Attr;
	</xsl:for-each>	
</xsl:template>

<xsl:template name="association">
	<xsl:for-each select="association_end">
		<xsl:variable name="aggregation" select="@aggregation"/>
		<xsl:if test="contains($aggregation, 'composite')">			

	   /*
		* composition
		*/
		public void set<xsl:value-of select="association_type"/>Attr(<xsl:value-of select="association_type"/> _<xsl:value-of select="association_type"/>Attr)){
			this._<xsl:value-of select="association_type"/>Attr = _<xsl:value-of select="association_type"/>Attr
		}
		
		public <xsl:value-of select="association_type"/> get<xsl:value-of select="association_type"/>Attr(){
			return _<xsl:value-of select="association_type"/>Attr;
		}			
		</xsl:if>
		<xsl:if test="contains($aggregation, 'aggregate')">

	   /*
		* agregation
		*/		
		public void set<xsl:value-of select="association_type"/>Attr(<xsl:value-of select="association_type"/> _<xsl:value-of select="association_type"/>Attr)){
			this._<xsl:value-of select="association_type"/>Attr = _<xsl:value-of select="association_type"/>Attr
		}
		
		public <xsl:value-of select="association_type"/> get<xsl:value-of select="association_type"/>Attr(){
			return _<xsl:value-of select="association_type"/>Attr;
		}
		</xsl:if>				
	</xsl:for-each>
</xsl:template>

<xsl:template name="composite">
	<xsl:for-each select="association_type">

	   /*
		* composition
		*/
		public void set<xsl:value-of select="association_type"/>(<xsl:value-of select="association_type"/> <xsl:value-of select="association_type"/>Attr)){
			this._<xsl:value-of select="association_type"/>Attr = <xsl:value-of select="association_type"/>Attr
		}
		
		public <xsl:value-of select="association_type"/> get<xsl:value-of select="association_type"/>Attr(){
			return _<xsl:value-of select="association_type"/>Attr;
		}
	</xsl:for-each>
</xsl:template>

<xsl:template name="aggregation">
	<xsl:for-each select="association_type">

	   /*
		* agregation
		*/		
		public void set<xsl:value-of select="association_type"/>(<xsl:value-of select="association_type"/> <xsl:value-of select="association_type"/>Attr)){
			this._<xsl:value-of select="association_type"/>Attr = <xsl:value-of select="association_type"/>Attr
		}
		
		public <xsl:value-of select="association_type"/> get<xsl:value-of select="association_type"/>Attr(){
			return _<xsl:value-of select="association_type"/>Attr;
		}
	</xsl:for-each>	
</xsl:template>

</xsl:stylesheet>
