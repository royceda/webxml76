<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="pageTitle" />
	<xsl:param name="htmlBody" />

	<xsl:template match="/">
		<!-- <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'> -->
		<html>
			<head>
				<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
				<meta name='viewport' content='user-scalable=no,width=device-width' />
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
						<xsl:value-of select="$pageTitle" />
					</h1>
					<a data-rel="back" data-iconpos="notext" data-icon="back" href="#"
						title="Retour">Retour</a>
				</div>
				<div data-role="content">
					<xsl:value-of select="$htmlBody" />
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>