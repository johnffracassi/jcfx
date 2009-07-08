<?xml version="1.0"?>
  
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:Date="http://www.oracle.com/XSL/Transform/java/java.util.Date"
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >
   
	<xsl:template name="index">
		<xsl:call-template name="output">
			<xsl:with-param name="file">index</xsl:with-param>
			<xsl:with-param name="doc">
				<center>
					<table style="width: 100%">
						<tr>
							<td valign="top">
								<table class="stats2" style="border: 1px solid black;" border="0" cellspacing="1" cellpadding="3">
									<caption><xsl:value-of select="sw:season-title($current-season)" /> - Player Rankings</caption>
									<xsl:call-template name="net-history">
										<xsl:with-param name="match-set" select="//match[@seasonId = $current-season]" />
									</xsl:call-template>
								</table>
							</td>
							<!-- 
							<td valign="top">
								<table class="stats2" style="border: 1px solid black;" border="0" cellspacing="1" cellpadding="3">
									<caption><xsl:value-of select="sw:season-title($current-season + 1)" /> - Player Rankings</caption>
									<xsl:call-template name="net-history">
										<xsl:with-param name="match-set" select="//match[@seasonId = $current-season + 1]" />
									</xsl:call-template>
								</table>
							</td>
							 -->
						</tr>
					</table>
				</center>
			</xsl:with-param>			
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="output">
		<xsl:param name="doc" />
		<xsl:param name="file" />
	
		<xsl:result-document href="{concat('bin/app/', $file, '.html')}">
			<html>
				<head>
					<title>Official Website of <xsl:value-of select="$team-name" /></title>
					<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
					<meta name="description" content="The official homepage for the Steamboat Willies Indoor Cricket Club (London branch)." />
					<meta name="keywords" content="Steamboat Willies, indoor cricket, steamboat willies icc, indoor, cricket, london, toot toot, play on sports, canary wharf, stats" />
					<link rel="stylesheet" type="text/css" href="style/steamboat.css" />
					<link rel="stylesheet" type="text/css" href="style/shadow.css" />
					<link rel="stylesheet" type="text/css" href="style/sort.css" />
					<script type="text/javascript" src="script/sorter.js"></script>					
					
					<script type="text/javascript">
						<![CDATA[
						function init()
						{
							if(document.getElementById('sortTable'))
							{
								var TableSorter1 = new TSorter;
								TableSorter1.init('sortTable');
							}
						}
						
						function load()
						{
							init();
						
							if(document.playerSelect)
							{
								document.playerSelect.player.selectedIndex = 0;
								playerChanged();
							}
						}
						
						function playerChanged()
						{
							var playerIdx = document.playerSelect.player.selectedIndex;
							var playerId = document.playerSelect.player.options[playerIdx].value;
							var el = document.getElementsByTagName("tr");
					
							for(var i=0; i < el.length; i++)
							{
								if(el[i].childNodes[0].tagName != "th")
								{
									if(el[i].id.indexOf(playerId) != -1)
									{
										el[i].className = 'focus';
									}
									else
									{
										if(i % 2 == 1) 
											el[i].className = 'odd';
										else 
											el[i].className = 'even';
									}
								}
							}
						} 
						]]>
					</script>
				</head>
			
				<body onLoad="load()">
					<table class="layout">
						<tr>
							<td class="header">
								<a href="index.html">
									<img src="images/sw-small.png" style="border: 0px;" />
								</a>
							</td>
							<td class="header">
								<xsl:value-of select="$team-name" />
							</td>
						</tr>
						
						<tr>
							<td class="navbar">
								<xsl:call-template name="navbar" />
							</td>
							
							<td class="content">
								<br/>
									<center>
										<img align="center" valign="top" src="{$sponsor}" />
									</center>
								<br/>
								<hr/>
			
								<xsl:copy-of select="$doc" />
							</td>
						</tr>
						
						<tr>
							<td class="footer" colspan="2">
								&#169; 2005-2008 <xsl:value-of select="$team-name" /> (Toot toot!)<br/>
								<small>Generated: <xsl:value-of select="Date:new()" /></small>
							</td>
						</tr>
					</table>
				</body>
			</html>
		</xsl:result-document>
	</xsl:template>
	
	<xsl:template name="navbar">
		<ul class="makeMenu">
		
			<li><a href="index.html">Home</a></li>
			<li><a href="results.html">Results</a></li>
			
			<li><a href="http://playonsports.spawtz.com/SpawtzSkin/Fixtures/Fixtures.aspx?LeagueId=58&amp;SeasonId=25">Fixtures »</a></li>
			<li><a href="http://playonsports.spawtz.com/SpawtzSkin/Fixtures/Standings.aspx?LeagueId=58&amp;SeasonId=25">Table »</a></li>

			<li>Profiles (Current)
				<ul>
					<xsl:for-each select="//player[@current = 'Y']">
						<xsl:sort select="sw:realname(@id)" />
						<li><a href="profile-{@id}.html"><xsl:value-of select="sw:realname(@id)" /></a></li>
					</xsl:for-each>
				</ul>
			</li>
			
			<li>Profiles (RIP)
				<ul>
					<xsl:for-each select="//player[@current = 'N']">
						<xsl:sort select="sw:realname(@id)" />
						<li><a href="profile-{@id}.html"><xsl:value-of select="sw:realname(@id)" /></a></li>
					</xsl:for-each>
				</ul>
			</li>
			
			<li>Batting Records
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
			
			<li>Bowling Records
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
					
			<li>General Records
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
			
		</ul>
	</xsl:template>
</xsl:stylesheet>