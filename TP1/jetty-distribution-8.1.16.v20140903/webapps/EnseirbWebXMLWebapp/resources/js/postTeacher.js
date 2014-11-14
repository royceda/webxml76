function postTeacher(teacher){
    var request = createXhrObject();
    request.open('post', '../about/teacher/post', false);
    request.send(teacher);
    request.onreadystatechange = function () {
	if (request.readyState == 4) {
	    if (request.status == 200) {
		var xml = request.responseXML;
	    } else {
		alert( "There was a problem with the URL." );
	    }
	    http_request = null;
	}
    };
}
