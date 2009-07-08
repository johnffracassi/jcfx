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
    
    <xsl:variable name="w" select="700" />
    <xsl:variable name="h" select="300" />
    
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

    <xsl:template name="chart">
        <xsl:param name="metadata" />
        <xsl:param name="data" />
        <xsl:param name="title" />
        
        <xsl:result-document href="{concat('/home/jeff/Desktop/', 'worm-', 63, '.svg')}">
            <svg xml:space="preserve" width="{$w}" height="{$h}">
                <desc><xsl:value-of select="$metadata/chart/title" /></desc>
                <g style="stroke:#000000; stroke-width:0.5; font-family:Arial; font-size:7pt;">
                    <xsl:call-template name="setup">
                        <xsl:with-param name="metadata" select="$metadata" />
                        <xsl:with-param name="title" select="$title" />
                    </xsl:call-template>
                    
                    <xsl:call-template name="draw-chart">
                        <xsl:with-param name="data" select="$data"/>
                    </xsl:call-template>
                </g>
            </svg>
        </xsl:result-document>
    </xsl:template>
    
    <xsl:template name="draw-chart">
        <xsl:param name="data"/>
        
        <xsl:variable name="path">
            <xsl:for-each select="$data//ball">
               
                <xsl:variable name="x" select="@ordinal * 5"/>
                <xsl:variable name="y" select="sw:runsToY(min($data//ball/@cumulative) - 5, max($data//ball/@cumulative), @cumulative) + 5"/>
                
                <xsl:if test="position() = 1">
                    <xsl:value-of select="concat('M', $x, ' ', $y, ' ')" />
                </xsl:if>
                <xsl:if test="position() &gt; 1">
                    <xsl:value-of select="concat('L', $x, ' ', $y, ' ')" />
                </xsl:if>
            </xsl:for-each>
        </xsl:variable>
        
        <path d="{$path}" style="fill:none; stroke:red; stroke-width:1" />
    </xsl:template>
    
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
    
    
    
    <xsl:template match="/">
        <xsl:variable name="calc" select="calc:new()" />
        
        <xsl:variable name="bat-balls">
            <xsl:for-each select="//match[@id = 63]//innings/ball">
                <xsl:sort order="ascending" data-type="number" select="../../@id" />
                <xsl:sort order="ascending" data-type="number" select="@ordinal" />
                
                <xsl:comment><xsl:value-of select="calc:add($calc, @runs)" /></xsl:comment>
                
                <xsl:element name="ball">
                    <xsl:attribute name="ordinal">
                        <xsl:value-of select="@ordinal + (27 * (../../@ordinal - 1))" />
                    </xsl:attribute>
                    <xsl:attribute name="cumulative">
                        <xsl:value-of select="calc:total($calc)" />
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>
        </xsl:variable>
        
        <xsl:call-template name="chart">
            <xsl:with-param name="title" select="Worm"/>
            <xsl:with-param name="metadata" select="$chart-worm"/>
            <xsl:with-param name="data" select="$bat-balls"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:variable name="chart-worm">
        <chart>
            <title>Worm</title>
            <file>worm-</file>
            <max>200</max>
            <min>-30</min>
            <step>10</step>
            <gap>5</gap>
            <series>
                <series id="1" style="line" colour="#00007f" showvalue="false">Steamboat Willies</series>
                <series id="2" style="line" colour="#7f0000" showvalue="false">Opposition</series>
            </series>
        </chart>
    </xsl:variable>
    
    

</xsl:stylesheet>
