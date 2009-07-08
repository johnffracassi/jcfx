<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw" 
				extension-element-prefixes="exsl" >

	<xsl:template name="net-all">
		<xsl:call-template name="output">
			<xsl:with-param name="file">net-career</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Career - Net Runs</caption>
					<xsl:call-template name="net-runs">
						<xsl:with-param name="match-set" select="//match" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">net-season</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Season - Net Runs</caption>
					<xsl:call-template name="net-runs">
						<xsl:with-param name="match-set" select="//match[@seasonId &gt;=  $current-season]" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
				

		<xsl:call-template name="output">
			<xsl:with-param name="file">net-best</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Net - Top <xsl:value-of select="$ind-row-count" /> Performances</caption>
					<xsl:call-template name="net-summary">
						<xsl:with-param name="order" select="'descending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">net-worst</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Net - Worst <xsl:value-of select="$ind-row-count" /> Performances</caption>
					<xsl:call-template name="net-summary">
						<xsl:with-param name="order" select="'ascending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-histogram</xsl:with-param>
			<xsl:with-param name="doc">
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Runs Histogram</caption>
					<xsl:call-template name="bat-ball-freq" />
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>


	<xsl:template name="net-header">
		<tr>
			<th class="number">#</th>
			<th class="text">Player</th>
			<th class="number">Bat</th>
			<th class="number">Bowl</th>
			<th class="number">Net Runs</th>
			<th class="number">Net Run Rate</th>
			<xsl:copy-of select="sw:match-header()" />
		</tr>
	</xsl:template>

	<xsl:template name="get-net">
		<xsl:param name="summary-set" select="//match" />
	
		<xsl:element name="results">
			<xsl:for-each select="$summary-set">
				<xsl:variable name="match" select="." />
				<xsl:for-each select="//player[@id != 'f1']">
					<xsl:variable name="id" select="@id" />
					<xsl:variable name="bat" select="$match/partnership/innings[@player = $id]/ball/@runs" />
					<xsl:variable name="bowl" select="$match/over[@player = $id]/ball/@runs" />
					
					<xsl:if test="count($bat) + count($bowl) &gt; 0">
						<xsl:element name="result">
							<xsl:attribute name="player"><xsl:value-of select="$id" /></xsl:attribute>
							<xsl:attribute name="bat"><xsl:value-of select="sum($bat)" /></xsl:attribute>
							<xsl:attribute name="bowl"><xsl:value-of select="sum($bowl)" /></xsl:attribute>
							<xsl:attribute name="bb"><xsl:value-of select="count($bowl)" /></xsl:attribute>
							<xsl:attribute name="bf"><xsl:value-of select="count($bat)" /></xsl:attribute>
							<xsl:attribute name="net"><xsl:value-of select="sum($bat) - sum($bowl)" /></xsl:attribute>
							<xsl:attribute name="netrr"><xsl:value-of select="sw:perc(sum($bat), count($bat), '0.000000') - sw:perc(sum($bowl), count($bowl), '0.000000')" /></xsl:attribute>
							<xsl:attribute name="batsr"><xsl:value-of select="sw:perc(sum($bat), count($bat), '0.000000')" /></xsl:attribute>
							<xsl:attribute name="bowlsr"><xsl:value-of select="sw:perc(sum($bowl), count($bowl), '0.000000')" /></xsl:attribute>
							<xsl:attribute name="team"><xsl:value-of select="$match/@us" /></xsl:attribute>
							<xsl:attribute name="opp"><xsl:value-of select="$match/@opposition" /></xsl:attribute>
							<xsl:attribute name="date"><xsl:value-of select="$match/@date" /></xsl:attribute>
							<xsl:attribute name="time"><xsl:value-of select="$match/@time" /></xsl:attribute>
							<xsl:attribute name="round"><xsl:value-of select="$match/@round" /></xsl:attribute>
							<xsl:attribute name="seasonId"><xsl:value-of select="$match/@seasonId" /></xsl:attribute>
							<xsl:attribute name="matchId"><xsl:value-of select="$match/@id" /></xsl:attribute>
						</xsl:element>
					</xsl:if>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="net-summary">
		<xsl:param name="summary-set" select="//match" />
		<xsl:param name="order" />
		
		<xsl:variable name="nodeset">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="$summary-set" />
			</xsl:call-template>
		</xsl:variable> 
		
		<xsl:call-template name="net-header" />
		<xsl:apply-templates select="exsl:node-set($nodeset)//result[@bat != 0 and @bowl != 0]" mode="net">
			<xsl:sort select="@net" order="{$order}" data-type="number" />
			<xsl:sort select="@player" order="ascending" data-type="text" />
		</xsl:apply-templates>			
	</xsl:template>

	<xsl:template match="result" mode="net">
		<xsl:if test="position() &lt;= $ind-row-count">
			<tr id="[{@player}]">
				<xsl:attribute name="class">
					<xsl:if test="position() mod 2 = 0">even</xsl:if>
					<xsl:if test="position() mod 2 = 1">odd</xsl:if>
				</xsl:attribute>
			
				<td class="numleft" nowrap="nowrap">
					<xsl:value-of select="position()" />
					<xsl:copy-of select="sw:is-new(@date)" />
				</td>
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number"><xsl:value-of select="@bat" /></td>
				<td class="number"><xsl:value-of select="@bowl" /></td>
				<td class="focus-col number"><xsl:value-of select="@net" /></td>
				<td class="number"><xsl:value-of select="format-number(@netrr, '0.00')" /></td>
				<xsl:copy-of select="sw:match-details-id(@matchId)" />
			</tr>
		</xsl:if>
	</xsl:template>

	<xsl:template name="net-runs">
		<xsl:param name="match-set" />
		
		<xsl:variable name="bat-set" select="$match-set/partnership/innings/ball" />
		<xsl:variable name="bowl-set" select="$match-set/over/ball" />
		
		<xsl:variable name="nodeset">
			<xsl:element name="results">
				<xsl:for-each select="//player">
					<xsl:variable name="id" select="@id" />
					<xsl:variable name="match-count" select="count($match-set[innings/@player = $id or over/@player = $id])" />
					<xsl:variable name="bat" select="sum($bat-set[../@player = $id]/@runs)" />
					<xsl:variable name="bowl" select="sum($bowl-set[../@player = $id]/@runs)" />
					<xsl:variable name="bf" select="count($bat-set[../@player = $id]/@runs)" />
					<xsl:variable name="bb" select="count($bowl-set[../@player = $id]/@runs)" />
					
					<xsl:if test="$match-count &gt; 0">
						<xsl:element name="result">
							<xsl:attribute name="id"><xsl:value-of select="$id" /></xsl:attribute>
							<xsl:attribute name="match-count"><xsl:value-of select="$match-count" /></xsl:attribute>
							<xsl:attribute name="bat"><xsl:value-of select="$bat" /></xsl:attribute>
							<xsl:attribute name="bowl"><xsl:value-of select="$bowl" /></xsl:attribute>
							<xsl:attribute name="bf"><xsl:value-of select="$bf" /></xsl:attribute>
							<xsl:attribute name="bb"><xsl:value-of select="$bb" /></xsl:attribute>
							<xsl:attribute name="net"><xsl:value-of select="$bat - $bowl" /></xsl:attribute>
							<xsl:attribute name="netrr"><xsl:value-of select="sw:netrr($bat, $bf, $bowl, $bb)" /></xsl:attribute>
						</xsl:element>
					</xsl:if>
				</xsl:for-each>
			</xsl:element>
		</xsl:variable> 

		
		<tr>
			<th class="numleft" rowspan="2">Rank</th>
			<th class="text"   rowspan="2">Player</th>
			<th class="number" rowspan="2">Matches</th>
			<th class="lb centre" colspan="3">Batting</th>
			<th class="lb centre" colspan="3">Bowling</th>
			<th class="lb number" rowspan="2">Net<br/>Runs</th>
			<th class="number" rowspan="2">Net<br/>RR</th>
			<th class="number" rowspan="2">Avg<br/>Net</th>
		</tr>
		<tr>
			<th class="lb">Runs</th>
			<th>Balls</th>
			<th>S/R</th>
			<th class="lb">Runs</th>
			<th>Balls</th>
			<th>E/R</th>
		</tr>
	
		<xsl:for-each select="exsl:node-set($nodeset)//result">
			<xsl:sort select="@net" data-type="number" order="descending" />
			<xsl:sort select="@netrr" data-type="number" order="descending" />
		
			<xsl:variable name="id" select="@id" />
			
			<tr id="[{$id}]">
				<xsl:attribute name="class">
					<xsl:if test="position() mod 2 = 0">even</xsl:if>
					<xsl:if test="position() mod 2 = 1">odd</xsl:if>
				</xsl:attribute>
				
				<td class="numleft"><xsl:value-of select="position()" /></td>
				<td class="text"><xsl:copy-of select="sw:fullname(@id)" /></td>
				<td class="number"><xsl:value-of select="@match-count" /></td>
				<td class="lb number"><xsl:value-of select="@bat" /></td>
				<td class="number"><xsl:value-of select="@bf" /></td>
				<td class="number"><xsl:value-of select="sw:perc(@bat, @bf, '0.0')" /></td>
				<td class="lb number"><xsl:value-of select="@bowl" /></td>
				<td class="number"><xsl:value-of select="@bb" /></td>
				<td class="number"><xsl:value-of select="sw:perc(@bowl, @bb, '0.0')" /></td>
				<td class="lb focus-col number"><xsl:value-of select="@bat - @bowl" /></td>
				<td class="number"><xsl:value-of select="sw:netrr(@bat, @bf, @bowl, @bb)" /></td>
				<td class="number"><xsl:value-of select="sw:divide(@net, @match-count)" /></td>
			</tr>
		</xsl:for-each>
	</xsl:template>

    <xsl:key name="bat-ball-freq" match="//ball[name(..) = 'innings']" use="@score" />
    <xsl:key name="bowl-ball-freq" match="//ball[name(..) = 'over']" use="@score" />
    <xsl:template name="bat-ball-freq">
    	<xsl:param name="bat-ballSet" select="//ball[name(..) = 'innings']" />
    	<xsl:param name="bowl-ballSet" select="//ball[name(..) = 'over']" />
    
    	<tr>
			<th class="centre" rowspan="2">Score</th>
			<th class="centre" colspan="2">For</th>
			<th class="centre" colspan="2">Against</th>
    	</tr>
    
		<tr>
			<th class="number">Freq</th>
			<th class="number">Total</th>
			<th class="number">Freq</th>
			<th class="number">Total</th>
		</tr>
    
        <xsl:for-each select="$bat-ballSet[generate-id() = generate-id(key('bat-ball-freq', @score)[1])]">
        	<xsl:sort select="@score" order="ascending" />
              
        	<xsl:variable name="score" select="@score" />
           	<xsl:variable name="bat-group" select="$bat-ballSet[@score = $score]" />
           	<xsl:variable name="bowl-group" select="$bowl-ballSet[@score = $score]" />
	
			<tr>
				<td class="focus-col skinny-text"><xsl:value-of select="@score"/></td>
				
				<td class="number"><xsl:value-of select="count($bat-group)"/></td>
				<td class="number"><xsl:value-of select="count($bat-group) * @runs"/></td>

				<td class="number"><xsl:value-of select="count($bowl-group)"/></td>
				<td class="number"><xsl:value-of select="count($bowl-group) * @runs"/></td>
			</tr>
		</xsl:for-each>
    </xsl:template>

</xsl:stylesheet>