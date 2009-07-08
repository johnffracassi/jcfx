<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:Color="http://www.oracle.com/XSL/Transform/java/java.awt.Color"
	xmlns:Integer="http://www.oracle.com/XSL/Transform/java/java.lang.Integer"
	xmlns:Math="http://www.oracle.com/XSL/Transform/java/java.lang.Math"
	xmlns:sw="http://www.zendurl.com/sw"
	xmlns:exsl="http://exslt.org/common" 
	xmlns:calc="java:com.jeff.sw.XSLCalc"
	exclude-result-prefixes="Color Integer calc exsl sw Math">

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

	<xsl:variable name="w" select="850" />
	<xsl:variable name="h" select="350" />


	<!-- =========================================================================== -->
	<!-- Driver template -->
	<!-- =========================================================================== -->
	<xsl:template match="/">
		<xsl:variable name="net-set">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="//match" />
			</xsl:call-template>
		</xsl:variable> 

		<xsl:for-each select="//player">
			<xsl:variable name="player" select="@id" />

			<xsl:call-template name="chart">
				<xsl:with-param name="title" select="concat(exsl:node-set($chart-bat)//title, ' - ', sw:fullnameNoFlag($player))" />
				<xsl:with-param name="metadata" select="exsl:node-set($chart-bat)" />
				<xsl:with-param name="data">
					<xsl:call-template name="bat-dataset">
						<xsl:with-param name="set" select="//innings[@player = $player]" />
					</xsl:call-template>
				</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="chart">
				<xsl:with-param name="title" select="concat(exsl:node-set($chart-bowl)//title, ' - ', sw:fullnameNoFlag($player))" />
				<xsl:with-param name="metadata" select="exsl:node-set($chart-bowl)" />
				<xsl:with-param name="data">
					<xsl:call-template name="bowl-dataset">
						<xsl:with-param name="set" select="//over[@player = $player]" />
					</xsl:call-template>
				</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="chart">
				<xsl:with-param name="title" select="concat(exsl:node-set($chart-net)//title, ' - ', sw:fullnameNoFlag($player))" />
				<xsl:with-param name="metadata" select="exsl:node-set($chart-net)" />
				<xsl:with-param name="data">
					<xsl:call-template name="net-dataset">
						<xsl:with-param name="set" select="$net-set//result[@player = $player and (@bf + @bb) &gt; 0]" />
					</xsl:call-template>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>


	<!-- =========================================================================== -->
	<!-- Draw all charts -->
	<!-- =========================================================================== -->
	<xsl:template name="chart">
		<xsl:param name="metadata" />
		<xsl:param name="data" />
		<xsl:param name="title" />
		
		<xsl:result-document href="{concat('bin/app/charts/svg/', $metadata/chart/file, $data/data/@file, '.svg')}">
			<svg xml:space="preserve" width="{$w}" height="{$h}">
				<desc><xsl:value-of select="$metadata/chart/title" /></desc>
				<g style="stroke:#000000; stroke-width:0.5; font-family:Arial; font-size:7pt;">
					<xsl:call-template name="setup">
						<xsl:with-param name="metadata" select="$metadata" />
						<xsl:with-param name="title" select="$title" />
					</xsl:call-template>
					
					<xsl:apply-templates select="$data//series[@id]" mode="bar">
						<xsl:sort select="@id" order="descending" data-type="number" />
						<xsl:with-param name="metadata" select="$metadata" />
					</xsl:apply-templates>
					
					<xsl:apply-templates select="$data//series[@id]" mode="avgline">
						<xsl:sort select="@id" order="descending" data-type="number" />
						<xsl:with-param name="metadata" select="$metadata" />
					</xsl:apply-templates>
				</g>
			</svg>
		</xsl:result-document>
	</xsl:template>

	
	<xsl:template name="setup">
		<xsl:param name="metadata" />
		<xsl:param name="title" />
	
		<rect x="1" y="1" width="{$w - 2}" height="{$h - 2}" style="fill:#dddddd;" />

		<xsl:variable name="min" select="($metadata/chart/min)" />
		<xsl:variable name="max" select="($metadata/chart/max)" />
		<xsl:variable name="step" select="($metadata/chart/step)" />

		<xsl:call-template name="draw-lines">
			<xsl:with-param name="min" select="$min" />
			<xsl:with-param name="max" select="$max" />
			<xsl:with-param name="step" select="$step" />
			<xsl:with-param name="start" select="$min - ($min mod $step)" />
			<xsl:with-param name="end" select="$max - ($max mod $step)" />
		</xsl:call-template>
		
		<text x="{$w div 2}" y="20" style="text-anchor:middle; font-size: 14pt;">
			<xsl:value-of select="$title" />
		</text>
	</xsl:template>
	
	<xsl:template name="draw-lines">
		<xsl:param name="min" />
		<xsl:param name="max" />
		<xsl:param name="step" />
		<xsl:param name="start" />
		<xsl:param name="end" />
		
		<xsl:copy-of select="sw:yvalLine($min, $max, $start)" />
		
		<xsl:if test="$start + $step &lt;= $end">
			<xsl:call-template name="draw-lines">
				<xsl:with-param name="min" select="$min" />
				<xsl:with-param name="max" select="$max" />
				<xsl:with-param name="step" select="$step" />
				<xsl:with-param name="start" select="$start + $step" />
				<xsl:with-param name="end" select="$end" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template match="series" mode="bar">
		<xsl:param name="metadata" />

		<xsl:apply-templates select="point" mode="bar">
			<xsl:sort select="@index" order="ascending" data-type="number" />
			<xsl:with-param name="metadata" select="$metadata" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="series" mode="avgline">
		<xsl:param name="metadata" />

		<xsl:variable name="gap" select="number($metadata/chart/gap)" />
		<xsl:variable name="min" select="number($metadata/chart/min)" />
		<xsl:variable name="max" select="number($metadata/chart/max)" />

		<xsl:variable name="series-id" select="../@id" />
		<xsl:variable name="series" select="$metadata//series[@id = $series-id]" />

		<xsl:variable name="bar-width"  select="($w div (count(point) + 1)) - $gap" />

		<xsl:variable name="calc" select="calc:new()" />

		<xsl:variable name="path">
			<xsl:for-each select="point">
				<xsl:comment><xsl:value-of select="calc:add($calc, @value)" /></xsl:comment>

				<xsl:if test="position() = 1">
					<xsl:value-of select="concat('M', position() * ($bar-width + $gap) + ($bar-width div 2), ' ', sw:runsToY($min, $max, calc:avg($calc)), ' ')" />
				</xsl:if>
				<xsl:if test="position() &gt; 1">
					<xsl:value-of select="concat('L', position() * ($bar-width + $gap) + ($bar-width div 2), ' ', sw:runsToY($min, $max, calc:avg($calc)), ' ')" />
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>

		<path d="{$path}" style="fill:none; stroke:red; stroke-width:1" />
	</xsl:template>

	<xsl:template match="point" mode="bar">
		<xsl:param name="metadata" />
		<xsl:param name="series-offset" select="4" />
	
		<xsl:variable name="gap" select="number($metadata/chart/gap)" />
		<xsl:variable name="min" select="number($metadata/chart/min)" />
		<xsl:variable name="max" select="number($metadata/chart/max)" />

		<xsl:variable name="series-id" select="../@id" />
		<xsl:variable name="series" select="$metadata//series[@id = $series-id]" />

		<xsl:variable name="zero" select="number(sw:runsToY($min, $max, 0))" />

		<xsl:variable name="bar-width"  select="($w div (../count(point) + 1)) - $gap" />
		<xsl:variable name="x1" select="@index * ($bar-width + $gap) + ((../@id - 1) * $series-offset)" />

		<xsl:variable name="y1" select="Math:min($zero, sw:runsToY($min, $max, @value))" />
		<xsl:variable name="y2" select="Math:max($zero, sw:runsToY($min, $max, @value))" />
		<xsl:variable name="bar-height" select="$y2 - $y1" />
		
		<rect x="{$x1}" y="{$y1}" height="{$bar-height}" width="{$bar-width}" style="fill:{$series/@colour};" />
		
		<xsl:if test="$series/@showvalue = 'true'">
			<text x="{$x1}" y="{$y1 - 3}">
				<xsl:value-of select="@value" />
			</text>
		</xsl:if>
	</xsl:template>


	<!-- =========================================================================== -->
	<!-- Functions -->
	<!-- =========================================================================== -->
	<xsl:function name="sw:scale">
		<xsl:param name="min" />
		<xsl:param name="max" />
		<xsl:param name="value" />

		<xsl:variable name="scale" select="$h div ($max - $min)" />
		
		<xsl:value-of select="$value * $scale" />
	</xsl:function>

	<xsl:function name="sw:runsToY">
		<xsl:param name="min" />
		<xsl:param name="max" />
		<xsl:param name="value" />
		
		<xsl:variable name="zero" select="$h - ((0.0 - $min) div ($max - $min) * $h)" />
		
		<xsl:value-of select="$zero - sw:scale($min, $max, $value)" />
	</xsl:function>

	<xsl:function name="sw:yvalLine">
		<xsl:param name="min" />
		<xsl:param name="max" />
		<xsl:param name="runs" />
		
		<g style="stroke:#888888; font-family:Arial; font-size:6pt;">
			<line x1="1" y1="{sw:runsToY($min, $max, $runs)}" x2="{$w}" y2="{sw:runsToY($min, $max, $runs)}" style="stroke:#aaaaaa;" />
			<text x="5" y="{sw:runsToY($min, $max, $runs) - 2}">
				<xsl:value-of select="$runs" />
			</text>
		</g>
	</xsl:function>


	<!-- =========================================================================== -->
	<!-- Generate data sets -->
	<!-- =========================================================================== -->
	<xsl:template name="bat-dataset">
		<xsl:param name="set" />
		<xsl:element name="data">
			<xsl:attribute name="file"><xsl:value-of select="concat('career-', $set[1]/@player)" /></xsl:attribute>
			<xsl:element name="series">
				<xsl:attribute name="id">1</xsl:attribute>
				<xsl:for-each select="$set/@score">
					<point index="{position()}" value="{.}" />
				</xsl:for-each>
			</xsl:element>
			<xsl:element name="series">
				<xsl:attribute name="id">2</xsl:attribute>
				<xsl:for-each select="$set/../@score">
					<point index="{position()}" value="{.}" />
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="bowl-dataset">
		<xsl:param name="set" />
		<xsl:element name="data">
			<xsl:attribute name="file"><xsl:value-of select="concat('career-', $set[1]/@player)" /></xsl:attribute>
			<xsl:element name="series">
				<xsl:attribute name="id">1</xsl:attribute>
				<xsl:for-each select="$set/@score">
					<point index="{position()}" value="{.}" />
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="net-dataset">
		<xsl:param name="set" />
		<xsl:element name="data">
			<xsl:attribute name="file"><xsl:value-of select="concat('career-', $set[1]/@player)" /></xsl:attribute>
			<xsl:element name="series">
				<xsl:attribute name="id">1</xsl:attribute>
				<xsl:for-each select="$set/@net">
					<point index="{position()}" value="{.}" />
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>



	<!-- =========================================================================== -->
	<!-- Chart metadata -->
	<!-- =========================================================================== -->
	<xsl:variable name="chart-bat">
		<chart>
			<title>Batting History</title>
			<file>bat-history-</file>
			<max>80</max>
			<min>-25</min>
			<step>10</step>
			<gap>8</gap>
			<series>
				<series id="1" style="bar" colour="#0000aa" showvalue="true">Score</series>
				<series id="2" style="bar" colour="#aaaaff" showvalue="false">Partnership</series>
			</series>
		</chart>
	</xsl:variable>

	<xsl:variable name="chart-bowl">
		<chart>
			<title>Bowling History</title>
			<file>bowl-history-</file>
			<max>25</max>
			<min>-25</min>
			<step>5</step>
			<gap>2</gap>
			<series>
				<series id="1" style="bar" colour="#00007f" showvalue="false">Runs Off Over</series>
			</series>
		</chart>
	</xsl:variable>

	<xsl:variable name="chart-net">
		<chart>
			<title>Net Runs</title>
			<file>net-history-</file>
			<max>65</max>
			<min>-45</min>
			<step>10</step>
			<gap>5</gap>
			<series>
				<series id="1" style="bar" colour="#00007f" showvalue="false">Net Runs</series>
			</series>
		</chart>
	</xsl:variable>

</xsl:stylesheet>
