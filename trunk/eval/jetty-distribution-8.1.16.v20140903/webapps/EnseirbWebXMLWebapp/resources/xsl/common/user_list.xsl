<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.or/1999/XSL/Transform">

<xsl:param name="pageTitle" />
<xsl:param name="htmlTitle" />


<xsl:template match="/">
 <!-- <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'> -->
  <html>
    <head>
      <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	  <meta content="user-scalable=no,width=device-width" name="viewport">
	    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0b2/jquery.mobile-1.0b2.min.css">
	      <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
	      <script src="http://code.jquery.com/mobile/1.0b2/jquery.mobile-1.0b2.min.js"></script>
    <title>Liste des utilisateurs
    </title>
  </head>
  <body>
    <div data-role="header"><h1>						Utilisateurs 					</h1>
    <a title="Retour" href="#" data-icon="back" data-iconpos="notext" data-rel="back">Retour</a>
    </div>
    <div data-role="content">
      <!--
      	  tri par nom des utilisateurs
      -->

<!--
Modification en boucle  <xsl: for-each select"user[@name=...]"/>
-->
      <ul data-role="listview">
        <li>
          <a href="../task/list?ownerNameFilter=fherbret" target="_self">fherbret</a>
        </li>
        <li>
          <a href="../task/list?ownerNameFilter=groupe" target="_self">groupe</a>
        </li>
        <li>
          <a href="../task/list?ownerNameFilter=matLo" target="_self">matLo</a>
        </li>
        <li>
          <a href="../task/list?ownerNameFilter=paresseux" target="_self">paresseux</a>
        </li>
      </ul>
    </div>
  </body>
</html>


</xsl:template>

</xsl:stylesheet>
