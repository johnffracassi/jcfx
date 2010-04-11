<?xml version="1.0"?>
  
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common" xmlns:sw="http://www.zendurl.com/sw" extension-element-prefixes="exsl" >
   
	<xsl:include href="constants.xsl" />
	<xsl:include href="functions.xsl" />
	<xsl:include href="common.xsl" />
	<xsl:include href="webpage.xsl" />
	<xsl:include href="match-results.xsl" />
	<xsl:include href="match-result.xsl" />
	<xsl:include href="net-records.xsl" />
	<xsl:include href="net-history.xsl" />
	<xsl:include href="random-records.xsl" />
	<xsl:include href="bowling-records.xsl" />
	<xsl:include href="batting-records.xsl" />
	<xsl:include href="ps-records.xsl" />
	<xsl:include href="player-profile.xsl" />
	<xsl:include href="team-bowling.xsl" />
	<xsl:include href="weighted-pts.xsl" />
	<xsl:include href="menus.xsl" />
	<xsl:include href="rnrr.xsl" />

	
	<xsl:template match="/">
		<xsl:comment><xsl:value-of select="count($doc)" /></xsl:comment>
		<xsl:call-template name="rnrr" />
		<xsl:apply-templates select="//matches" mode="results" />
		<xsl:apply-templates select="//match" mode="match-result" />
		<xsl:apply-templates select="//players" />
		<xsl:call-template name="index" />
		<xsl:call-template name="weighted-all" />
		<xsl:call-template name="net-history-all" />
		<xsl:call-template name="net-all" />
		<xsl:call-template name="bat-all" />
		<xsl:call-template name="random-all" />
		<xsl:call-template name="ps-all" />
		<xsl:call-template name="bowl-all" />
		<xsl:call-template name="team-bowl-all" />
		<xsl:call-template name="menu-pages" />
	</xsl:template>
</xsl:stylesheet>