<?xml version="1.0"?>
  
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:xs="http://www.w3.org/2001/XMLSchema"
				xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes"
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				xmlns:Math="http://www.oracle.com/XSL/Transform/java/java.lang.Math"
				extension-element-prefixes="exsl" >
   

	<!-- ============================================================================================== -->
	<xsl:function name="sw:over-ordinal">
		<xsl:param name="ball-num" />
		
		<xsl:choose>
			<xsl:when test="$ball-num &lt;= 6">1</xsl:when>
			<xsl:when test="$ball-num &lt;= 12">2</xsl:when>
			<xsl:when test="$ball-num &lt;= 18">3</xsl:when>
			<xsl:otherwise>4</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	
   
	<!-- ============================================================================================== -->
	<xsl:function name="sw:weighting-by-match">
		<xsl:param name="matches" />
		
		<xsl:choose>
			<xsl:when test="$matches &lt; 2">0.2</xsl:when>
			<xsl:when test="$matches &lt; 3">0.4</xsl:when>
			<xsl:when test="$matches &lt; 4">0.6</xsl:when>
			<xsl:when test="$matches &lt; 5">0.8</xsl:when>
			<xsl:otherwise>1.0</xsl:otherwise>
		</xsl:choose>
	</xsl:function>   
   

	<!-- ============================================================================================== -->
   	<xsl:function name="sw:season-title">
   		<xsl:param name="season-id" />

		<xsl:variable name="season" select="$doc//season[@id = $season-id]" />
   		<xsl:value-of select="concat($season/@name, ' ', $season/@year, ' (', $season/@grade, ' grade)')" />
   	</xsl:function>

   
	<!-- ============================================================================================== -->
	<xsl:function name="sw:match-details-id">
		<xsl:param name="match-id" />
		<xsl:variable name="match" select="$doc//match[@id = $match-id]" />
		<xsl:copy-of select="sw:match-details($match)" />
	</xsl:function>
	
	<xsl:function name="sw:match-details">
		<xsl:param name="match" />
		
		<td class="text"><xsl:value-of select="$match/@opposition" /></td>
		<td class="text"><xsl:value-of select="sw:season-title($match/@seasonId)" /></td>
		<!-- 
		<td class="date"><xsl:value-of select="$match/@date" /></td>
		<td class="date"><xsl:value-of select="$match/@time" /></td>
		-->
	</xsl:function>

	<xsl:function name="sw:match-header">
		<th class="text">Opposition</th>
		<th class="text">Season</th>
		<!-- 
		<th class="date">Date</th>
		<th class="date">Time</th>
		-->
	</xsl:function>

	<!-- ============================================================================================== --> 
    <xsl:function name="sw:is-new">
   		<xsl:param name="n" />

		<xsl:variable name="last-date" select="$doc//match[position() = last()]/@date" />

   		<xsl:variable name="is-new">
   			<xsl:choose>
   				<xsl:when test="name($n) = 'date'">
   					<xsl:if test="$n = $last-date">
   						<xsl:value-of select="'true'" />
   					</xsl:if>
   					<!-- 
   					<xsl:if test="$n = '1/12/2008'">
   						<xsl:value-of select="'true'" />
   					</xsl:if>
   					 -->
   				</xsl:when>
   				<xsl:otherwise>
			   		<xsl:variable name="match" select="$n/ancestor::match" />
   					<xsl:if test="$match/@date = $last-date">
   						<xsl:value-of select="'true'" />
   					</xsl:if>
   					<!-- 
   					<xsl:if test="$match/@date = '1/12/2008'">
   						<xsl:value-of select="'true'" />
   					</xsl:if>
   					 -->
   				</xsl:otherwise>
		   	</xsl:choose>
		</xsl:variable>
   		
   		<xsl:if test="$is-new = 'true'">
   			<img src="images/new.gif" />
   		</xsl:if>
    </xsl:function>

   
	<!-- ============================================================================================== -->
	<xsl:function name="sw:rand">
		<xsl:param name="max" />
		<xsl:value-of select="round(Math:random() * $max + 0.5)" />
	</xsl:function>
	
	
	<!-- ============================================================================================== -->
	<xsl:function name="sw:over-to-ps-ord">
		<xsl:param name="num" />
		<xsl:value-of select="floor(($num - 1) div 4) + 1" />
	</xsl:function>
	

	<!-- ============================================================================================== -->
	<xsl:function name="sw:netrr">
		<xsl:param name="runs" />
		<xsl:param name="bf" />
		<xsl:param name="rc" />
		<xsl:param name="bb" />
		
		<xsl:choose>
			<xsl:when test="$bf &gt; 0 and $bb &gt; 0">
				<xsl:value-of select="format-number((($runs div $bf) - ($rc div $bb)) * 100, '##0.0')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0.0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	

	<!-- ============================================================================================== -->
	<xsl:function name="sw:divide">
		<xsl:param name="num" />
		<xsl:param name="den" />
		<xsl:param name="fmt" />
		
		<xsl:choose>
			<xsl:when test="$den = 0">
				<xsl:value-of select="format-number(0, $fmt)" />
			</xsl:when>
			<xsl:when test="$den != 0">
				<xsl:value-of select="format-number($num div $den, $fmt)" />
			</xsl:when>
		</xsl:choose>
	</xsl:function>
	
	<xsl:function name="sw:divide">
		<xsl:param name="num" />
		<xsl:param name="den" />
		<xsl:value-of select="sw:divide($num, $den, '0.00')" />
	</xsl:function>
	

	<!-- ============================================================================================== -->
	<xsl:function name="sw:perc">
		<xsl:param name="num" />
		<xsl:param name="den" />
		<xsl:param name="fmt" />
		
		<xsl:choose>
			<xsl:when test="$den = 0">
				<xsl:value-of select="format-number(0, $fmt)" />
			</xsl:when>
			<xsl:when test="$den != 0">
				<xsl:value-of select="format-number($num div $den * 100, $fmt)" />
			</xsl:when>
		</xsl:choose>
	</xsl:function>
	
	<xsl:function name="sw:perc">
		<xsl:param name="num" />
		<xsl:param name="den" />
		<xsl:value-of select="sw:perc($num, $den, '0.00')" />
	</xsl:function>
	

	<!-- ============================================================================================== -->
	<xsl:function name="sw:ordinal">
		<xsl:param name="num" />
		
		<xsl:if test="normalize-space(string($num)) != ''">
			<xsl:choose>
				<xsl:when test="$num = 1">1st</xsl:when>
				<xsl:when test="$num = 2">2nd</xsl:when>
				<xsl:when test="$num = 3">3rd</xsl:when>
				<xsl:otherwise><xsl:value-of select="concat($num, 'th')" /></xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:function>


	<!-- ============================================================================================== -->
	<xsl:function name="sw:fullname">
		<xsl:param name="id" />
		
		<xsl:variable name="player-id" select="normalize-space(string($id))" />
		<xsl:variable name="player" select="$doc//player[@id = $player-id]" />

		<xsl:element name="img">
			<xsl:attribute name="src">
				<xsl:value-of select="concat('images/', $player/flag)" />
			</xsl:attribute>
		</xsl:element>
		
		<xsl:copy-of select="sw:fullnameNoFlag($id)" />
	</xsl:function>
	
	<!-- ============================================================================================== -->
	<xsl:function name="sw:fullnameNoFlag">
		<xsl:param name="id" />
		
		<xsl:variable name="player-id" select="normalize-space(string($id))" />
		<xsl:variable name="player" select="$doc//player[@id = $player-id]" />

		<xsl:element name="a">
			<xsl:attribute name="title">
				<xsl:value-of select="sw:realname($player-id)" />
			</xsl:attribute>
			<xsl:attribute name="href">profile-<xsl:value-of select="$player-id" />.html</xsl:attribute>
			
			<xsl:choose>
				<xsl:when test="count($player/nicknames/nickname) &gt; 0">
					<xsl:variable name="rnd" select="number(sw:rand(count($player/nicknames/nickname)))" />
					<xsl:value-of select="$player/nicknames/nickname[$rnd]" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="sw:realname($player-id)" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>

	</xsl:function>


	<!-- ============================================================================================== -->
	<xsl:function name="sw:realname">
		<xsl:param name="id" />
		
		<xsl:variable name="player-id" select="normalize-space(string($id))" />
		<xsl:variable name="player" select="$doc//player[@id = $player-id]" />
		<xsl:variable name="full-name" select="$player/fullName" />

		<xsl:value-of select="$full-name" />
	</xsl:function>

</xsl:stylesheet>