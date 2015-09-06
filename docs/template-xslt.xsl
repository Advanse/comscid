<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output encoding="ISO-8859-1" method="xml" indent="yes" />

	<xsl:template match="/">
		<xsl:call-template name="Heading" />
		<xsl:for-each select="classes/class">			
			<xsl:element name="ownedMember">				
				<xsl:attribute name="type">Class</xsl:attribute>
				<xsl:attribute name="name"><xsl:value-of select="name" /></xsl:attribute>
				<xsl:for-each select="attributes/attribute">
					<xsl:element name="ownedAttribute">
						<xsl:attribute name="name"><xsl:value-of
							select="name" /></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="Heading">
		<cabecalho>Cabecalho</cabecalho> 
	</xsl:template>
	
	<xsl:template name="ClassJude">
		<cabecalho>Cabecalho</cabecalho> 
	</xsl:template>
	<xsl:template name="ClassTogether">
		<cabecalho>Cabecalho</cabecalho> 
	</xsl:template>

</xsl:stylesheet>

