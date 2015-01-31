<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:param name="pageTitle" />
  <xsl:param name="htmlTitle" />
  
  <xsl:template match="/">
    <!-- <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'> -->
    <html>
      <head>
	<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
	<meta name='viewport' content='user-scalable=no,width=device-width' />
	<script type="text/javascript" src="../js/toolkit.js"></script>
	<script type="text/javascript" src="../js/create_user.js"></script>
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
		<xsl:attribute name="for">fName</xsl:attribute>
		Nom
	      </xsl:element>
	      <xsl:element name="input">
		<xsl:attribute name="id">fName</xsl:attribute>
		<xsl:attribute name="type">text</xsl:attribute>
	      </xsl:element>
	    </div>
	  </form>
	</div>
	<a data-role="button" href="javascript:postUser(document.getElementById('fName').value);">Ajouter</a>	
	<div id="creationResult"></div>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
