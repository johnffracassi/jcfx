<?xml version="1.0"?>
  
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >
   
	<xsl:variable name="doc" 			select="/" />
	<xsl:variable name="ind-row-count" 	select="40" />
	<xsl:variable name="ps-row-count"  	select="$ind-row-count" />
	<xsl:variable name="current-season" select="10" />
	<xsl:variable name="sponsor" 		select="'http://www.usairways.com/awa/resources/_images/homepage_logo.gif'" />
	<xsl:variable name="team-name" 		select="'Steamboat Willies ICC'" />

</xsl:stylesheet>