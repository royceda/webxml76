function createXhrObject() {
    if (window.XMLHttpRequest)
	return new XMLHttpRequest();
    
    if (window.ActiveXObject) {
	var names = [ "Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.3.0",
		      "Msxml2.XMLHTTP", "Microsoft.XMLHTTP" ];
	for ( var i in names) {
	    try {
		return new ActiveXObject(names[i]);
	    } catch (e) {
	    }
	}
    }
    alert("Votre navigateur ne prend pas en charge l'objet XMLHTTPRequest.");
    return null; // non supporté
}

function getXMLDocument(xmlAsText) {
    alert("getting doc");
    // if (false && xhr.responseXML != null) {
    // return xhr.responseXML;
    //
    // } else {
    var xhr = createXhrObject();
    if (window.ActiveXObject) {
	xhr.loadXML(xmlAsText);
	return xhr.responseXML;
    } else {
	alert("getting doc FF:" + xmlAsText);
	parser = new DOMParser();
	xmlDoc = parser.parseFromString(xmlAsText, "text/xml");
	alert("xmlDoc:" + xmlDoc);
	alert("xmlDoc.documentElement:" + xmlDoc.documentElement);
	
	return xmlDoc;
    }
    // }
}
