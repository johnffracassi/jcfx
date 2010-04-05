<?xml version="1.0"?>
  
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:Date="http://www.oracle.com/XSL/Transform/java/java.util.Date"
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >
   
	<xsl:template name="menu-pages">
		<xsl:call-template name="menu-profiles" />
		<xsl:call-template name="menu-batting" />
		<xsl:call-template name="menu-bowling" />
		<xsl:call-template name="menu-general" />
	</xsl:template>
	
	
	<xsl:template name="menu-profiles">
		<xsl:call-template name="output">
			<xsl:with-param name="file">menu-profiles</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="items-profiles" />
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="items-profiles">
		<li><a href="menu-profiles.html">Profiles (Current)</a>
			<ul>
				<xsl:for-each select="//player[@current = 'Y']">
					<xsl:sort select="sw:realname(@id)" />
					<li><a href="profile-{@id}.html"><xsl:value-of select="sw:realname(@id)" /></a></li>
				</xsl:for-each>
			</ul>
		</li>
		
		<li><a href="menu-profiles.html">Profiles (RIP)</a>
			<ul>
				<xsl:for-each select="//player[@current = 'N']">
					<xsl:sort select="sw:realname(@id)" />
					<li><a href="profile-{@id}.html"><xsl:value-of select="sw:realname(@id)" /></a></li>
				</xsl:for-each>
			</ul>
		</li>
	</xsl:template>
	
	
	<xsl:template name="menu-batting">
		<xsl:call-template name="output">
			<xsl:with-param name="file">menu-batting</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="items-batting" />
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="items-batting">
		<li><a href="menu-batting.html">Batting Records</a>
		<ul>
			<li><a href="bat-summary-career.html">Career</a></li>
			<li><a href="bat-summary-season.html">Season</a></li>
			<li><a href="bat-inns-best.html">Best Innings</a></li>
			<li><a href="bat-inns-worst.html">Worst Innings</a></li>
			<li><a href="bat-most-runs.html">Most Runs</a></li>
			<li><a href="bat-best-over.html">Most Runs Off An Over</a></li>
			<li><a href="bat-ps-best.html">Best Partnerships</a></li>
			<li><a href="bat-ps-worst.html">Worst Partnerships</a></li>
			<li><a href="bat-most-ps-runs.html">Most Partnership Runs</a></li>
			<li><a href="bat-ps-combo-agg.html">Most Successful Partnerships</a></li>
			<li><a href="bat-most-bf.html">Most Balls Faced (Career)</a></li>
			<li><a href="bat-most-bf-inns.html">Most Balls Faced (Inns)</a></li>
			<li><a href="bat-least-bf-inns.html">Least Balls Faced (Inns)</a></li>
			<li><a href="bat-most-6s.html">Most 6s Hit</a></li>
			<li><a href="bat-most-dots.html">Most Dot Balls</a></li>
			<li><a href="bat-most-outs.html">Most Dismissed</a></li>
			<li><a href="bat-run-outs.html">Run-out Liability Factor</a></li>
			<li><a href="most-lbws.html">Most LBWs</a></li>
		</ul>
	</li>
	</xsl:template>


	<xsl:template name="menu-bowling">
		<xsl:call-template name="output">
			<xsl:with-param name="file">menu-bowling</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="items-bowling" />
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="items-bowling">
		<li><a href="menu-bowling.html">Bowling Records</a>
			<ul>
				<li><a href="bowl-summary-career.html">Career</a></li>
				<li><a href="bowl-summary-season.html">Season</a></li>
				<li><a href="bowl-figures-best.html">Best Match Figures</a></li>
				<li><a href="bowl-figures-worst.html">Worst Match Figures</a></li>
				<li><a href="bowl-over-best.html">Best Overs</a></li>
				<li><a href="bowl-over-worst.html">Worst Overs</a></li>
				<li><a href="bowl-most-outs.html">Most Wickets</a></li>
				<li><a href="bowl-most-runs.html">Most Runs Conceeded</a></li>
				<li><a href="bowl-most-6s.html">Most 6s Conceeded</a></li>
				<li><a href="bowl-most-dots.html">Most Dots Bowled</a></li>
				<li><a href="bowl-most-extras.html">Most Extras Conceeded</a></li>
				<li><a href="over-breakdown.html">Over by Over</a></li>
				<li><a href="bat-ps-best-against.html">Best Partnerships Against</a></li>
				<li><a href="bat-ps-worst-against.html">Worst Partnerships Against</a></li>
			</ul>
		</li>
	</xsl:template>


	<xsl:template name="menu-general">
		<xsl:call-template name="output">
			<xsl:with-param name="file">menu-general</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="items-general" />
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="items-general">
		<li><a href="menu-general.html">General Records</a>
			<ul>
				<li><a href="net-career.html">Net Runs (Career)</a></li>
				<li><a href="net-season.html">Net Runs (Season)</a></li>
				<li><a href="net-best.html">Best Performances</a></li>
				<li><a href="net-worst.html">Worst Performances</a></li>
				<li><a href="most-nicknames.html">Most Nicknames</a></li>
				<li><a href="most-beers-won.html">Most Beers Won</a></li>
				<li><a href="ps-skin-win.html">Best Skins</a></li>
				<li><a href="ps-skin-loss.html">Worst Skins</a></li>
			</ul>
		</li>
	</xsl:template>
	
</xsl:stylesheet>