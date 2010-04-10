<?xml version="1.0"?>
  
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:Date="http://www.oracle.com/XSL/Transform/java/java.util.Date"
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >
   
	<xsl:include href="home.xsl" />
   
	<xsl:template name="index">
		<xsl:call-template name="output">
			<xsl:with-param name="file">index</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="home" />
			
				<table style="width: 100%">
					<tr>
						<td valign="top">
							<table class="stats2" style="border: 1px solid black;" border="0" cellspacing="1" cellpadding="3">
								<caption>Player Rankings - <xsl:value-of select="sw:season-title($current-season)" />
								| <a href="net-history-all.html">All Time</a>
								| <a href="net-history-12.html">Historical</a></caption>
								<xsl:call-template name="net-history">
									<xsl:with-param name="match-set" select="//match[@seasonId = $current-season]" />
									<xsl:with-param name="min-games" select="3" />
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
								<xsl:copy-of select="$doc" />
							</td>
						</tr>
						
						<tr>
							<td class="footer" colspan="2">
								Contact: <a href="mailto:steamboatwilliesicc@gmail.com">steamboatwilliesicc@gmail.com</a> | 
								Wikipedia: <a href="http://en.wikipedia.org/wiki/Steamboat_Willies_ICC">Steamboat Willies ICC</a>
								<br/>
								<small>
									&#169; 2005-2010 <xsl:value-of select="$team-name" /> (Toot toot!) |
									Generated: <xsl:value-of select="Date:new()" /><br/>
								</small>
							</td>
						</tr>
					</table>
					
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-13271565-1");
pageTracker._trackPageview();
} catch(err) {}</script>

				</body>
			</html>
		</xsl:result-document>
	</xsl:template>
	
	<xsl:template name="navbar">
		<ul class="makeMenu">
		
			<li><a href="index.html">Home</a></li>
			<li><a href="results.html">Results</a></li>
			
			<li><a href="http://playonsports.spawtz.com/SpawtzSkin/Fixtures/Fixtures.aspx?LeagueId=58&amp;SeasonId=38">Fixtures »</a></li>
			<li><a href="http://playonsports.spawtz.com/SpawtzSkin/Fixtures/Standings.aspx?LeagueId=58&amp;SeasonId=38">Table »</a></li>

			<!-- menu items are now defined in 'menus.xsl' -->
			<xsl:call-template name="items-profiles" />
			<xsl:call-template name="items-batting" />
			<xsl:call-template name="items-bowling" />
			<xsl:call-template name="items-general" />
			
		</ul>
	</xsl:template>
</xsl:stylesheet>