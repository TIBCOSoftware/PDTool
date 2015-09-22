<?xml version='1.0' ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:ns2="http://www.dvbu.cisco.com/ps/deploytool/modules">
	<!-- The CIS_PREV_VERSION and CIS_NEW_VERSION represent the portion of the various CIS paths within the Server Attribute XML file.
	        If CIS previous and new install are installed on the same base path then it will only be necessary to change the version as 
			shown below.  However, if they are installed on different paths, then the full base path should be provided for both variables. -->
	<!-- CIS Path Version 1 -->
	<xsl:variable name="CIS_PATH_PREV_VERSION_1" select="'CIS_6.2'"/>
	<xsl:variable name="CIS_PATH_NEW_VERSION_1" select="'CIS_7.0'"/>
	<!-- CIS Path Version 2 -->
	<xsl:variable name="CIS_PATH_PREV_VERSION_2" select="'6.2.5'"/>
	<xsl:variable name="CIS_PATH_NEW_VERSION_2" select="'7.0.0'"/>
	<!-- CIS Path Version 3 -->
	<xsl:variable name="CIS_PATH_PREV_VERSION_3" select="'6.2.6'"/>
	<xsl:variable name="CIS_PATH_NEW_VERSION_3" select="'7.0.0'"/>
	<!-- CIS Path Version 4 -->
	<xsl:variable name="CIS_PATH_PREV_VERSION_4" select="'_NO_TRANSFORM_OPERATION_'"/>
	<xsl:variable name="CIS_PATH_NEW_VERSION_4" select="'_NO_TRANSFORM_OPERATION_'"/>
	<!-- CIS Path Version 5 -->
	<xsl:variable name="CIS_PATH_PREV_VERSION_5" select="'_NO_TRANSFORM_OPERATION_'"/>
	<xsl:variable name="CIS_PATH_NEW_VERSION_5" select="'_NO_TRANSFORM_OPERATION_'"/>
	
	<!-- Perform the XSL Transformation -->
	<xsl:template match="/">
		<ns2:ServerAttributeModule xmlns:ns2="http://www.dvbu.cisco.com/ps/deploytool/modules" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		<xsl:for-each select="ns2:ServerAttributeModule/serverAttribute">

		  <xsl:choose>
			<!-- IGNORE all "name" fields in this list.  
					Any Server Attributes in this list are not mapped from the previous version to the new version.  
					There may be several reasons for not performing the map from the previous version to the new version:
					  1) The attributes are no longer supported in the new version.
					  2) The CIS Administrator decides they do not want to map certain attributes.
					  3) The attributes may have changed in such a way that they should not be mapped.
					  4) The values in the attributes are not the same in the new version and therefore should not be mapped. 
			-->
			<!-- Administrator has chosen not to bring over display and license information. -->
			<xsl:when test="name = '/server/config/info/displayName'"></xsl:when>
			<xsl:when test="name = '/server/config/license/licenseInfo'"></xsl:when>
			<xsl:when test="name = '/server/config/license/licenseManager'"></xsl:when>
			<!-- Administrator has chosen not to bring over ports by choice. -->		
			<xsl:when test="name = '/server/webservices/communications/http/portOnServerRestart'"></xsl:when>
			<!-- Administrator has chosen not to bring over passwords by choice. -->		
			<xsl:when test="name = '/monitor/server/connection/password'"></xsl:when>
			<xsl:when test="name = '/server/communications/keystorePasswordOnServerRestart'"></xsl:when>
			<xsl:when test="name = '/server/communications/strongKeystorePasswordOnServerRestart'"></xsl:when>
			<xsl:when test="name = '/server/communications/strongTruststorePasswordOnServerRestart'"></xsl:when>
			<xsl:when test="name = '/server/communications/truststorePasswordOnServerRestart'"></xsl:when>
			<xsl:when test="name = '/server/config/database/encryption/keystorePassword'"></xsl:when>
			<xsl:when test="name = '/server/config/email/smtpAuthUserPassword'"></xsl:when>
			<xsl:when test="name = '/server/config/net/proxy/http/proxyPassword'"></xsl:when>
			<xsl:when test="name = '/server/config/net/proxy/https/proxyPassword'"></xsl:when>
			<xsl:when test="name = '/sources/communications/keystorePassword'"></xsl:when>
			<xsl:when test="name = '/sources/communications/strongKeystorePassword'"></xsl:when>
			<xsl:when test="name = '/sources/communications/strongTruststorePassword'"></xsl:when>
			<xsl:when test="name = '/sources/communications/truststorePassword'"></xsl:when>

			<!-- Administrator has chosen not to bring over entries that do not exist in 7.0. -->
			<xsl:when test="name = '/server/sql/optimizations/enableCheckForNestedAggregates'"></xsl:when>

			<!-- SELECT all "name" fields not in the above ignore list. -->
			<xsl:otherwise>
				<serverAttribute>
				  <!-- SELECT "id" as is. -->
				  <id><xsl:value-of select="id"/></id>
				  
				  <!-- TRANSFORM "name" STRING 
						 The attributes in this list require a transformation of the name from the previous version to the new version.
						 This information was learned by doing a complete "diff" comparison using notepad++ Compare Plugin which easily
						 identifies line-by-line differences between a sample previous version attribute file and a new version attribute file. -->
				  <xsl:choose>
					<xsl:when test="name = '/server/communications/generateSSLDiagnostics'">
					   <name>/server/communications/generateSSLDiagnosticsOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/config/debug/useFifoRepositoryCache'">
					   <name>/server/config/debug/useFifoRepositoryCacheOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/config/metadata/optimizeDatabaseOnStartup'">
					   <name>/server/config/metadata/optimizeDatabaseOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/config/net/nioConnectorMaxIdleTime'">
					   <name>/server/config/net/nioConnectorMaxIdleTimeOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/config/net/useBlockingIOConnectors'">
					   <name>/server/config/net/useBlockingIOConnectorsOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/processing/requests/maxRequestsTracked'">
					   <name>/server/processing/requests/maxRequestsTrackedOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/processing/transactions/maxTransactionsTracked'">
					   <name>/server/processing/transactions/maxTransactionsTrackedOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/webservices/communications/http/headerBufferSize'">
					   <name>/server/webservices/communications/http/headerBufferSizeOnServerRestart</name>
					</xsl:when>
					<xsl:when test="name = '/server/webservices/communications/https/headerBufferSize'">
					   <name>/server/webservices/communications/https/headerBufferSizeOnServerRestart</name>
					</xsl:when>
					<xsl:otherwise>
					   <name><xsl:value-of select="name"/></name>
					</xsl:otherwise>
				  </xsl:choose>

				  <!-- SELECT "type" as is. -->
				  <type><xsl:value-of select="type"/></type>
				  
				  <!-- CHOOSE different value formats. -->
				  <xsl:choose>
						<xsl:when test="type = 'LIST'">
							<valueList>
								<xsl:for-each select="valueList/item">
									<item>
									  <type><xsl:value-of select="type"/></type>
									  <value><xsl:value-of select="value"/></value>
									</item>
								</xsl:for-each>
							</valueList>
						</xsl:when>
										  
						<xsl:when test="type = 'MAP'">
						  <valueMap>
							<xsl:for-each select="valueMap/entry">
								<entry>
								  <key>
									<type><xsl:value-of select="key/type"/></type>
									<value><xsl:value-of select="key/value"/></value>
								  </key>
								  <value>
									<type><xsl:value-of select="value/type"/></type>
									<value><xsl:value-of select="value/value"/></value>
								  </value>
								</entry>
							</xsl:for-each>
						</valueMap>
					</xsl:when>
										  
					<xsl:when test="type='STRING_ARRAY' or type='INT_ARRAY' or type='BYTE_ARRAY'">
					  <valueArray>
						<xsl:for-each select="valueArray/item">
							<item>
							<xsl:value-of select="."/>
							</item>
						</xsl:for-each>
					  </valueArray>
					</xsl:when>
					
					<xsl:otherwise>
						<!-- Replace any values that contain a previous path version with a new path version. -->
						<value>
							<xsl:choose>
								<xsl:when test="contains(value, $CIS_PATH_PREV_VERSION_1)">
									<xsl:call-template name="replace-string">
									  <xsl:with-param name="text" select="value"/>
									  <xsl:with-param name="replace" select="$CIS_PATH_PREV_VERSION_1" />
									  <xsl:with-param name="with" select="$CIS_PATH_NEW_VERSION_1"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:when test="contains(value, $CIS_PATH_PREV_VERSION_2)">
									<xsl:call-template name="replace-string">
									  <xsl:with-param name="text" select="value"/>
									  <xsl:with-param name="replace" select="$CIS_PATH_PREV_VERSION_2" />
									  <xsl:with-param name="with" select="$CIS_PATH_NEW_VERSION_2"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:when test="contains(value, $CIS_PATH_PREV_VERSION_3)">
									<xsl:call-template name="replace-string">
									  <xsl:with-param name="text" select="value"/>
									  <xsl:with-param name="replace" select="$CIS_PATH_PREV_VERSION_3" />
									  <xsl:with-param name="with" select="$CIS_PATH_NEW_VERSION_3"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:when test="contains(value, $CIS_PATH_PREV_VERSION_4)">
									<xsl:call-template name="replace-string">
									  <xsl:with-param name="text" select="value"/>
									  <xsl:with-param name="replace" select="$CIS_PATH_PREV_VERSION_4" />
									  <xsl:with-param name="with" select="$CIS_PATH_NEW_VERSION_4"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:when test="contains(value, $CIS_PATH_PREV_VERSION_5)">
									<xsl:call-template name="replace-string">
									  <xsl:with-param name="text" select="value"/>
									  <xsl:with-param name="replace" select="$CIS_PATH_PREV_VERSION_5" />
									  <xsl:with-param name="with" select="$CIS_PATH_NEW_VERSION_5"/>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="value"/>
								</xsl:otherwise>
							</xsl:choose>
						</value>
					</xsl:otherwise>
				  </xsl:choose>
				  
				</serverAttribute>
			</xsl:otherwise>
		    </xsl:choose>
		</xsl:for-each>
		</ns2:ServerAttributeModule>
	</xsl:template>
	
	<!-- Search and Replace String -->
	<xsl:template name="replace-string">
		<xsl:param name="text"/>
		<xsl:param name="replace"/>
		<xsl:param name="with"/>
		<xsl:choose>
		  <xsl:when test="contains($text,$replace)">
			<xsl:value-of select="substring-before($text,$replace)"/>
			<xsl:value-of select="$with"/>
			<xsl:call-template name="replace-string">
			  <xsl:with-param name="text" select="substring-after($text,$replace)"/>
			  <xsl:with-param name="replace" select="$replace"/>
			  <xsl:with-param name="with" select="$with"/>
			</xsl:call-template>
		  </xsl:when>
		  <xsl:otherwise>
			<xsl:value-of select="$text"/>
		  </xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
</xsl:stylesheet>
