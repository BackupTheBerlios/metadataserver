<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:UML="org.omg/UML1.3">
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
    <project>
    <package>
    	<package_name>no</package_name>
    	<package_stereotype>no</package_stereotype>
    </package>
    <xsl:for-each select="XMI/XMI.content">
        <xsl:call-template name="class"/>
    </xsl:for-each>
    </project>
</xsl:template>

<xsl:template name="class">
    <xsl:for-each select="/XMI/XMI.content/UML:Class">
        <xsl:variable name="myClassId" select="@xmi.id"/>    	
        <class id="{$myClassId}">
			<class_name>
				<xsl:value-of select="@name"/>
			</class_name>
			<xsl:for-each select="UML:Namespace.ownedElement/UML:Generalization">
			<extends>
				<xsl:variable name="parentId" select="@parent"/>			
				<generalclass id="{$parentId}" xmi="id">
					<xsl:call-template name="findParentName">
						<xsl:with-param name="parent" select="$parentId"/>
					</xsl:call-template>
				</generalclass>
			</extends>			
			</xsl:for-each>			
			<xsl:call-template name="asso">
				<xsl:with-param name="classId" select="@xmi.id"/>
			</xsl:call-template>			
        </class>       
    </xsl:for-each>
</xsl:template>


<xsl:template name="findParentName">
<xsl:param name="parent"/>
	<xsl:for-each select="//UML:Class">
		<xsl:variable name="parentXmiId" select="@xmi.id"/>
		<xsl:if test="contains($parentXmiId, $parent)">
			<xsl:value-of select="@name"/>
		</xsl:if>
	</xsl:for-each>
</xsl:template>
	
<xsl:template name="asso">
	<xsl:param name="classId"/>
    <xsl:for-each select="//UML:Association">
	<xsl:variable name="firstAssoId" select="UML:Association.connection/UML:AssociationEnd[position() = 1]/@type"/>
	<xsl:variable name="secondAssoId" select="UML:Association.connection/UML:AssociationEnd[position() = 2]/@type"/>
		<xsl:if test="contains($classId, $firstAssoId)">
   		    <association>
   			    <xsl:for-each select="UML:Association.connection/UML:AssociationEnd[position() = 2]">
   			    	<xsl:variable name="association1" select="../../UML:Association.connection/UML:AssociationEnd[position() = 1]/@aggregation"/>
	    	        <association_end aggregation="{$association1}">
	    	        	<xsl:variable name="assoId1" select="../../@xmi.id"/>
	    	            <association_name id="{$assoId1}"><xsl:value-of select="../../@name"/></association_name>
	    	            <association_navigable></association_navigable>
	    	            <association_multiplicy_lower></association_multiplicy_lower>
	    	            <association_multiplicy_upper></association_multiplicy_upper>
        	       		<association_type>
        	       			<xsl:value-of select="@type"/>
						</association_type>	                	
	        	    </association_end>	     
   	    		</xsl:for-each>
  	    	</association>
		</xsl:if>
		<xsl:if test="contains($classId, $secondAssoId)">
   		    <association>
   			    <xsl:for-each select="UML:Association.connection/UML:AssociationEnd[position() = 1]">
   			    	<xsl:variable name="association2" select="../../UML:Association.connection/UML:AssociationEnd[position() = 2]/@aggregation"/>
	    	        <association_end aggregation="{$association2}">
	    	        	<xsl:variable name="assoId2" select="../../@xmi.id"/>
	    	            <association_name id="{$assoId2}"><xsl:value-of select="../../@name"/></association_name>
	    	            <association_navigable></association_navigable>
	    	            <association_multiplicy_lower></association_multiplicy_lower>
	    	            <association_multiplicy_upper></association_multiplicy_upper>
        	       		<association_type>
        	       			<xsl:value-of select="@type"/>
						</association_type>	                	
	        	    </association_end>	     
   	    		</xsl:for-each>
  	    	</association>
		</xsl:if>		
    </xsl:for-each>
</xsl:template>
</xsl:stylesheet>
