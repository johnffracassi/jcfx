<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw" 
				extension-element-prefixes="exsl" >

	<xsl:template name="net-history-all">
		<xsl:call-template name="output">
			<xsl:with-param name="file">net-history-all</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>History</caption>
					<xsl:call-template name="net-history">
						<xsl:with-param name="match-set" select="//match" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="output">
			<xsl:with-param name="file">finals</xsl:with-param>
			<xsl:with-param name="doc">
				<table class="stats" border="0" cellspacing="1" cellpadding="3">
					<caption>Finals Selections</caption>
					<xsl:call-template name="finals">
						<xsl:with-param name="match-set" select="//match" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>


	<xsl:template name="finals">
		<xsl:param name="match-set" />
		<xsl:variable name="pts-per-match" select="15" />
		
		<xsl:variable name="net-new-unsorted">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="$match-set" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="net-new">
			<results>
				<xsl:for-each-group select="$net-new-unsorted/results/result" group-by="@player">
					<xsl:sort select="sum(current-group()/@bat) - sum(current-group()/@bowl) + count(current-group()) * $pts-per-match + number(//player[@id = current-group()[1]/@player]/registered) * 100" order="descending" data-type="number" />

					<xsl:variable name="player" select="current-group()[1]/@player" />
					<xsl:variable name="reg">
						<xsl:choose>
							<xsl:when test="$doc//player[@id = $player]/registered &gt; 0">
								<xsl:value-of select="$doc//player[@id = $player]/registered" />
							</xsl:when>
							<xsl:otherwise>0</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					
					<xsl:element name="result">
						<xsl:attribute name="player"><xsl:value-of select="$player" /></xsl:attribute>
						<xsl:attribute name="bat"><xsl:value-of select="sum(current-group()/@bat)" /></xsl:attribute>
						<xsl:attribute name="bf"><xsl:value-of select="sum(current-group()/@bf)" /></xsl:attribute>
						<xsl:attribute name="bowl"><xsl:value-of select="sum(current-group()/@bowl)" /></xsl:attribute>
						<xsl:attribute name="bb"><xsl:value-of select="sum(current-group()/@bb)" /></xsl:attribute>
						<xsl:attribute name="reg"><xsl:value-of select="$reg * 100" /></xsl:attribute>
						<xsl:attribute name="games"><xsl:value-of select="count(current-group()) * $pts-per-match" /></xsl:attribute>
						<xsl:attribute name="net"><xsl:value-of select="sum(current-group()/@bat) - sum(current-group()/@bowl)" /></xsl:attribute>
						<xsl:attribute name="pts"><xsl:value-of select="sum(current-group()/@bat) - sum(current-group()/@bowl) + count(current-group()) * $pts-per-match + $reg * 100" /></xsl:attribute>
					</xsl:element>
				</xsl:for-each-group>
			</results>
		</xsl:variable>

		<tr>
			<th class="numleft">#</th>
			<th class="text">Player</th>
			<th class="number">Net</th>
			<th class="number">Game Bonus<br/><small>(<xsl:value-of select="$pts-per-match" />pts/game)</small></th>
			<th class="number">Registered</th>
			<th class="number">Total</th>
		</tr>

		<xsl:for-each select="$net-new/results/result">
			<xsl:sort select="@pts" order="descending" data-type="number" />
			
			<xsl:variable name="player" select="@player" />

			<xsl:variable name="row-class">
				<xsl:choose>
					<xsl:when test="position() &lt;= 8">firsts</xsl:when>
					<xsl:when test="position() &lt;= 16">seconds</xsl:when>
					<xsl:otherwise>thirds</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>

			<tr id="[{$player}]" class="{$row-class}">
				<td class="numleft"><xsl:value-of select="sw:ordinal(position())" /></td>
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number"><xsl:value-of select="@net" /></td>
				<td class="number"><xsl:value-of select="@games" /></td>
				<td class="number"><xsl:value-of select="@reg" /></td>
				<td class="number focus-col"><xsl:value-of select="@pts" /></td>
			</tr>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="net-history">
		<xsl:param name="match-set" />

		<xsl:variable name="last-match-date" select="$match-set[position() = last()]/@date" />
		<xsl:variable name="net-old-unsorted">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="$match-set[@date != $last-match-date]" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="net-old">
			<results>
				<xsl:for-each-group select="$net-old-unsorted/results/result" group-by="@player">
					<xsl:sort select="sw:netrr(sum(current-group()/@bat), sum(current-group()/@bf), sum(current-group()/@bowl), sum(current-group()/@bf))" data-type="number" order="descending" />

					<xsl:element name="result">
						<xsl:attribute name="player"><xsl:value-of select="current-group()[1]/@player" /></xsl:attribute>
						<xsl:attribute name="pos"><xsl:value-of select="position()" /></xsl:attribute>
						<xsl:attribute name="bat"><xsl:value-of select="sum(current-group()/@bat)" /></xsl:attribute>
						<xsl:attribute name="bf"><xsl:value-of select="sum(current-group()/@bf)" /></xsl:attribute>
						<xsl:attribute name="bowl"><xsl:value-of select="sum(current-group()/@bowl)" /></xsl:attribute>
						<xsl:attribute name="bb"><xsl:value-of select="sum(current-group()/@bb)" /></xsl:attribute>
						<xsl:attribute name="netrr"><xsl:value-of select="sw:netrr(sum(current-group()/@bat), sum(current-group()/@bf), sum(current-group()/@bowl), sum(current-group()/@bb))" /></xsl:attribute>
						<xsl:attribute name="batsr"><xsl:value-of select="sw:perc(sum(current-group()/@bat), sum(current-group()/@bf))" /></xsl:attribute>
						<xsl:attribute name="bowlsr"><xsl:value-of select="sw:perc(sum(current-group()/@bowl), sum(current-group()/@bb))" /></xsl:attribute>
					</xsl:element>
				</xsl:for-each-group>
			</results>
		</xsl:variable>

		<xsl:variable name="net-new-unsorted">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="$match-set" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="net-new">
			<results>
				<xsl:for-each-group select="$net-new-unsorted/results/result" group-by="@player">
					<xsl:sort select="sw:netrr(sum(current-group()/@bat), sum(current-group()/@bf), sum(current-group()/@bowl), sum(current-group()/@bf))" data-type="number" order="descending" />
					<xsl:element name="result">
						<xsl:attribute name="player"><xsl:value-of select="current-group()[1]/@player" /></xsl:attribute>
						<xsl:attribute name="pos"><xsl:value-of select="position()" /></xsl:attribute>
						<xsl:attribute name="bat"><xsl:value-of select="sum(current-group()/@bat)" /></xsl:attribute>
						<xsl:attribute name="bf"><xsl:value-of select="sum(current-group()/@bf)" /></xsl:attribute>
						<xsl:attribute name="bowl"><xsl:value-of select="sum(current-group()/@bowl)" /></xsl:attribute>
						<xsl:attribute name="bb"><xsl:value-of select="sum(current-group()/@bb)" /></xsl:attribute>
						<xsl:attribute name="netrr"><xsl:value-of select="sw:netrr(sum(current-group()/@bat), sum(current-group()/@bf), sum(current-group()/@bowl), sum(current-group()/@bb))" /></xsl:attribute>
						<xsl:attribute name="batsr"><xsl:value-of select="sw:perc(sum(current-group()/@bat), sum(current-group()/@bf))" /></xsl:attribute>
						<xsl:attribute name="bowlsr"><xsl:value-of select="sw:perc(sum(current-group()/@bowl), sum(current-group()/@bb))" /></xsl:attribute>
					</xsl:element>
				</xsl:for-each-group>
			</results>
		</xsl:variable>


		<tr>
			<th class="text">Player</th>
			<th class="number">Net</th>
			<th class="number">Bat SR</th>
			<th class="number">Bowl SR</th>
			<th class="number">Net RR</th>
			<th class="number">This Week</th>
			<th class="number">Last Week</th>
			<th class="number">Change</th>
		</tr>

		<xsl:for-each select="$net-new/results/result">
			<xsl:sort select="@netrr" data-type="number" order="descending" />
		
			<xsl:variable name="player" select="@player" />
			<xsl:variable name="old-pos">
				<xsl:for-each select="$net-old/results/result">
					<xsl:if test="@player = $player">
						<xsl:value-of select="position()" />
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>

			<tr id="[{$player}]">
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number"><xsl:value-of select="@bat - @bowl" /></td>
				<td class="number"><xsl:value-of select="@batsr" /></td>
				<td class="number"><xsl:value-of select="@bowlsr" /></td>
				<td class="number focus-col"><xsl:value-of select="@netrr" /></td>
				<td class="number"><xsl:value-of select="sw:ordinal(position())" /></td>

				<xsl:choose>
					<xsl:when test="normalize-space(string($old-pos)) != ''">
						<td class="number"><xsl:value-of select="sw:ordinal($old-pos)" /></td>
						<td class="number">
							<xsl:choose>
								<xsl:when test="$old-pos - position() &gt; 0">
									<xsl:value-of select="$old-pos - position()" /><img src="images/up.png" />
								</xsl:when>
								<xsl:when test="$old-pos - position() &lt; 0">
									<xsl:value-of select="position() - $old-pos" /><img src="images/down.png" />
								</xsl:when>
								<xsl:otherwise>
									<img src="images/steady.png" />
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</xsl:when>
					<xsl:otherwise>
						<td class="number">-</td>
						<td class="number"><img src="images/new.gif" /></td>
					</xsl:otherwise>
				</xsl:choose>
			</tr>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>