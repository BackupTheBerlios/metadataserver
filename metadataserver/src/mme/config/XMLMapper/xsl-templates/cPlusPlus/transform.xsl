<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
    <project>
    <xsl:for-each select="XMI/XMI.content/Model_Management.Model">
        <xsl:call-template name="Owned"/>
    </xsl:for-each>
    </project>
</xsl:template>

<xsl:template name="Owned">
    <xsl:for-each select="Foundation.Core.Namespace.ownedElement/Model_Management.Package">
        <package>
            <package_name><xsl:value-of select="Foundation.Core.ModelElement.name"/></package_name>
            <package_stereotype><xsl:value-of select="Foundation.Core.ModelElement.name"/></package_stereotype>
            <xsl:call-template name="Owned"/>
        </package>
    </xsl:for-each>

    <xsl:for-each select="Foundation.Core.Namespace.ownedElement/Foundation.Core.Class">
    <!-- neu vom sterr start -->
	<xsl:variable name="classId" select="@xmi.id"/>
    <!-- class id="{@xmi.id}" -->
    <class id="{$classId}">
    <!-- neu vom sterr stop -->
        <class_name><xsl:value-of select="Foundation.Core.ModelElement.name"/></class_name>

         <!-- neu vom sterr start -->
        <xsl:call-template name = "generalization">
		<xsl:with-param name="classId" select="$classId"/>
        </xsl:call-template>
        <!-- neu vom sterr stop -->

        <xsl:for-each select="Foundation.Core.Classifier.feature/Foundation.Core.Attribute">
        <attribute>
            <attribute_name><xsl:value-of select="Foundation.Core.ModelElement.name"/></attribute_name>
            <attribute_name_ucase>
            <xsl:call-template name = "UCase" >
                 <xsl:with-param name="name" select="Foundation.Core.ModelElement.name"/>
            </xsl:call-template>
            </attribute_name_ucase>
            <xsl:call-template name = "QueryType" >
                 <xsl:with-param name="id" select="Foundation.Core.StructuralFeature.type/Foundation.Core.Classifier/@xmi.idref"/>
            </xsl:call-template>
        </attribute>
        </xsl:for-each>

        <xsl:for-each select="Foundation.Core.Classifier.feature/Foundation.Core.Operation">
        <operation>
            <operation_name><xsl:value-of select="Foundation.Core.ModelElement.name"/></operation_name>
            <xsl:for-each select="Foundation.Core.BehavioralFeature.parameter/Foundation.Core.Parameter">
                <parameter>
                    <parameter_name><xsl:value-of select="Foundation.Core.ModelElement.name"/></parameter_name>
                    <parameter_kind><xsl:value-of select="Foundation.Core.Parameter.kind/@xmi.value"/></parameter_kind>
                    <xsl:call-template name = "QueryParameter" >
                         <xsl:with-param name="id" select="Foundation.Core.Parameter.type/Foundation.Core.Classifier/@xmi.idref"/>
                    </xsl:call-template>
                </parameter>
            </xsl:for-each>
        </operation>
        </xsl:for-each>
    </class>
    </xsl:for-each>

    <xsl:for-each select="Foundation.Core.Namespace.ownedElement/Foundation.Core.Association">
        <association>
        <xsl:for-each select="Foundation.Core.Association.connection/Foundation.Core.AssociationEnd">
            <association_end>
                <association_name><xsl:value-of select="Foundation.Core.ModelElement.name"/></association_name>
                <association_navigable><xsl:value-of select="Foundation.Core.AssociationEnd.isNavigable/@xmi.value"/></association_navigable>
                <association_multiplicy_lower><xsl:value-of select="Foundation.Core.AssociationEnd.multiplicity/Foundation.Data_Types.Multiplicity/Foundation.Data_Types.Multiplicity.range/Foundation.Data_Types.MultiplicityRange/Foundation.Data_Types.MultiplicityRange.lower"/></association_multiplicy_lower>
                <association_multiplicy_upper><xsl:value-of select="Foundation.Core.AssociationEnd.multiplicity/Foundation.Data_Types.Multiplicity/Foundation.Data_Types.Multiplicity.range/Foundation.Data_Types.MultiplicityRange/Foundation.Data_Types.MultiplicityRange.upper"/></association_multiplicy_upper>
                <association_type><xsl:value-of select="Foundation.Core.AssociationEnd.type/Foundation.Core.Classifier/@xmi.idref"/></association_type>
            </association_end>
        </xsl:for-each>
        </association>
    </xsl:for-each>
</xsl:template>

<xsl:template name = "QueryType" >
    <xsl:param name = "id" />
    <attribute_type>
        <xsl:call-template name="ConvertType">
        <xsl:with-param name="type" select = "/XMI/XMI.content/Model_Management.Model/Foundation.Core.Namespace.ownedElement/Foundation.Core.DataType[@xmi.id=$id]/Foundation.Core.ModelElement.name"/>
        </xsl:call-template>
    </attribute_type>
    <attribute_type_sql>
        <xsl:call-template name="ConvertTypeSql">
        <xsl:with-param name="type" select = "/XMI/XMI.content/Model_Management.Model/Foundation.Core.Namespace.ownedElement/Foundation.Core.DataType[@xmi.id=$id]/Foundation.Core.ModelElement.name"/>
        </xsl:call-template>
    </attribute_type_sql>
</xsl:template>

<xsl:template name = "QueryParameter" >
    <xsl:param name = "id" />
    <parameter_type>
        <xsl:call-template name="ConvertType">
        <xsl:with-param name="type" select = "/XMI/XMI.content/Model_Management.Model/Foundation.Core.Namespace.ownedElement/Foundation.Core.DataType[@xmi.id=$id]/Foundation.Core.ModelElement.name"/>
        </xsl:call-template>
    </parameter_type>
</xsl:template>

<xsl:template name = "QueryStereoType" >
    <xsl:param name = "id" />
    <package_stereotype>
        <xsl:value-of select="/XMI/XMI.content/Model_Management.Model/Foundation.Core.Namespace.ownedElement/Foundation.Extension_Mechanisms.Stereotype[@xmi.id=$id]/Foundation.Core.ModelElement.name"/>
    </package_stereotype>
</xsl:template>

<xsl:template name="ConvertType">
<xsl:param name="type"/>
    <xsl:choose>
        <xsl:when test = "starts-with($type,'date')">Timestamp</xsl:when>
        <xsl:when test = "starts-with($type,'string')">String</xsl:when>
        <xsl:when test = "starts-with($type,'char')">String</xsl:when>
        <xsl:when test = "starts-with($type,'int')">Integer</xsl:when>
        <xsl:when test = "starts-with($type,'long')">BigDecimal</xsl:when>
        <xsl:when test = "starts-with($type,'Date')">Timestamp</xsl:when>
        <xsl:when test = "starts-with($type,'String')">String</xsl:when>
        <xsl:when test = "starts-with($type,'Integer')">Integer</xsl:when>
        <xsl:when test = "starts-with($type,'Long')">BigDecimal</xsl:when>
        <xsl:otherwise>Object</xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name="ConvertTypeSql">
<xsl:param name="type"/>
    <xsl:choose>
        <xsl:when test = "starts-with($type,'string')">varchar(255)</xsl:when>
        <xsl:when test = "starts-with($type,'String')">varchar(255)</xsl:when>
        <xsl:when test = "starts-with($type,'char')">varchar(255)</xsl:when>
        <xsl:when test = "starts-with($type,'Integer')">int</xsl:when>
        <xsl:when test = "starts-with($type,'Date')">timestamp</xsl:when>
        <xsl:when test = "starts-with($type,'Long')">long</xsl:when>
        <xsl:otherwise><xsl:value-of select="$type"/></xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name="UCase">
<xsl:param name="name"/>
    <xsl:value-of select="translate(substring($name,1,1),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/><xsl:value-of select="substring($name,2)"/>
</xsl:template>

<!-- neu vom sterr start -->
<xsl:template name="generalization">
<xsl:param name="classId"/>
	<extends>
		<xsl:for-each select="//Foundation.Core.Namespace.ownedElement">
			<xsl:for-each select="Foundation.Core.Generalization">
			<xsl:variable name="child" select="Foundation.Core.Generalization.child/Foundation.Core.GeneralizableElement/@xmi.idref"/>
			<xsl:if test="contains($classId, $child)">
				<generalclass Id="{Foundation.Core.Generalization.parent/Foundation.Core.GeneralizableElement/@xmi.idref}"/>
			</xsl:if>
			</xsl:for-each>
		</xsl:for-each>
		
	</extends>
</xsl:template>
<!-- neu vom sterr stop -->

</xsl:stylesheet>
