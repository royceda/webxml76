<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:param name="pageTitle" />
  <xsl:param name="htmlTitle" />
  
  <xsl:param name="pageState" /><!-- "READONLY" / "MODIFY" / "CREATE" -->
  
  <xsl:template match="/task">
    <!-- <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'> -->
    <html>
      <head>
	<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
	<meta name='viewport' content='user-scalable=no,width=device-width' />
	<script type="text/javascript" src="../js/toolkit.js"></script>
	<script type="text/javascript" src="../js/crud_task.js"></script>
	<link href="../jquery.mobile-1.2.0/jquery.mobile-1.2.0.min.css"
	      rel="stylesheet" />
	<script src="../jquery.mobile-1.2.0/jquery-1.8.2.min.js"></script>
	<script src="../jquery.mobile-1.2.0/jquery.mobile-1.2.0.min.js"></script>
	<title>
	  <xsl:value-of select="$pageTitle" />
	</title>
      </head>
      <body>
	<div data-role="header">
	  <h1>
	    <xsl:value-of select="$htmlTitle" />
	  </h1>
	  <a data-rel="back" data-iconpos="notext" data-icon="back" href="#"
	     title="Retour">Retour</a>
	</div>
	<div data-role="content">
	  <form method="post" action="#">
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fId</xsl:attribute>
		ID
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fId</xsl:attribute>
		<xsl:attribute name="type">text</xsl:attribute>
		
		<xsl:attribute name="readonly">readonly</xsl:attribute>
		<xsl:if test="'CREATE'!=pageState">
		  <xsl:attribute name="hidden">hidden</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="value"><xsl:value-of
		select="@id" /></xsl:attribute>
	      </xsl:element>
	    </div>
	    
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fCreationDate</xsl:attribute>
		Date de création
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fCreationDate</xsl:attribute>
		<xsl:attribute name="type">text</xsl:attribute>
		
		<xsl:attribute name="readonly">readonly</xsl:attribute>
		<xsl:if test="'CREATE'!=pageState">
		  <xsl:attribute name="hidden">hidden</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="value"><xsl:value-of
		select="@creationDate" /></xsl:attribute>
	      </xsl:element>
	    </div>
	    
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fTitle</xsl:attribute>
		Titre
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fTitle</xsl:attribute>
		<xsl:attribute name="type">text</xsl:attribute>
		<xsl:if test="'READONLY'=$pageState">
		  <xsl:attribute name="readonly">readonly</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="value"><xsl:value-of
		select="@title" /></xsl:attribute>
	      </xsl:element>
	    </div>
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fDescription</xsl:attribute>
		Description
	      </xsl:element>
	      <xsl:element name="textarea">
		<xsl:attribute name="id">fDescription</xsl:attribute>
		<xsl:attribute name="rows">3</xsl:attribute>
		<xsl:if test="'READONLY'=$pageState">
		  <xsl:attribute name="readonly">readonly</xsl:attribute>
		</xsl:if>
		<xsl:value-of select="description" />
	      </xsl:element>
	    </div>
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fAsker</xsl:attribute>
		Demandeur
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fAsker</xsl:attribute>
		<xsl:attribute name="type">text</xsl:attribute>
		<xsl:if test="'READONLY'=$pageState">
		  <xsl:attribute name="readonly">readonly</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="value"><xsl:value-of
		select="asker" /></xsl:attribute>
	      </xsl:element>
	      
	    </div>
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fOwner</xsl:attribute>
		Responsable
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fOwner</xsl:attribute>
		<xsl:attribute name="type">text</xsl:attribute>
		<xsl:if test="'READONLY'=$pageState">
		  <xsl:attribute name="readonly">readonly</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="value"><xsl:value-of
		select="owner" /></xsl:attribute>
	      </xsl:element>
	    </div>
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fDeadline</xsl:attribute>
		Deadline
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fDeadline</xsl:attribute>
		<xsl:attribute name="type">text</xsl:attribute>
		<xsl:if test="'READONLY'=$pageState">
		  <xsl:attribute name="readonly">readonly</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="value"><xsl:value-of
		select="@deadline" /></xsl:attribute>
	      </xsl:element>
	    </div>
	    <div data-role="fieldcontain">
	      <xsl:element name="label">
		<xsl:attribute name="for">fPriority</xsl:attribute>
		Priorité
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fPriority</xsl:attribute>
		<xsl:attribute name="type">range</xsl:attribute>
		<xsl:attribute name="min">1</xsl:attribute>
		<xsl:attribute name="max">5</xsl:attribute>
		<xsl:if test="'READONLY'=$pageState">
		  <xsl:attribute name="readonly">readonly</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="value"><xsl:value-of
		select="@priority" /></xsl:attribute>
	      </xsl:element>
	      
	    </div>
	    <div data-role="fieldcontain">
	      <fieldset data-role="controlgroup">
		<xsl:element name="label">
		  <xsl:attribute name="for">fDone</xsl:attribute>
		  Finie
		</xsl:element>
		
		<xsl:element name="input">
		  <xsl:attribute name="id">fDone</xsl:attribute>
		  <xsl:attribute name="type">checkbox</xsl:attribute>
		  <xsl:if test="'READONLY'=$pageState">
		    <xsl:attribute name="readonly">readonly</xsl:attribute>
		  </xsl:if>
		  <xsl:if test="@done='true'">
		    <xsl:attribute name="checked">checked</xsl:attribute>
		  </xsl:if>
		</xsl:element>
	      </fieldset>
	    </div>
	  </form>
	</div>
	<xsl:if test="'MODIFY'=$pageState">
	  <a data-role="button" href="javascript:postTask();">Mettre à jour</a>
	</xsl:if>
	<xsl:if test="'CREATE'=$pageState">
	  <a data-role="button" href="javascript:postTask();">Ajouter</a>
	</xsl:if>
	<xsl:if test="'READONLY'=$pageState">
	  <xsl:element name="a">
	    <xsl:attribute name="href">modify?id=<xsl:value-of
	    select="@id" /></xsl:attribute>
	    <xsl:attribute name="target">_self</xsl:attribute>
	    <xsl:attribute name="data-role">button</xsl:attribute>
	    Accès en édition
	  </xsl:element>
	</xsl:if>
	
	<div id="creationResult"></div>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
