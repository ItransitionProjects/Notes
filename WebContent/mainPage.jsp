<!DOCTYPE html>
<%@page import="java.util.Vector"%>
<%@page import="components.Note"%>
<html>
<head>
<title>Dragable example</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="//ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="resources/css/style.css"
	media="all" />
<script src="resources/js/script.js"></script>
</head>
<body id="body" ondblclick="addNote(event)">

	<%
		Vector<Note> notes = (Vector<Note>) request.getAttribute("notes");
		for (Note note : notes) {
	%>
	<script>
		var posx = "<%=note.getPosX()%>";
		var posy = "<%=note.getPosY()%>";
		var col = "<%=note.getColor()%>";
		var heigth = "<%=note.getHeigth()%>";
		var width = "<%=note.getWidth()%>";
		var text = "<%=note.getText()%>";
		var noteId = <%=note.getNoteId()%>;
		loadNote(posx, posy, col, heigth, width, text, noteId);

		function saveData(e) {

			var i;
			var allDivs = document.getElementsByClassName("drag");
			for (i = 0; i < allDivs.length; ++i) {
				var xmlHttpRequest = getXMLHttpRequest();
				xmlHttpRequest.onreadystatechange = getReadyStateHandler(xmlHttpRequest);
				xmlHttpRequest.open("POST", "NotesServlet.do", true);
				xmlHttpRequest.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded");

				var posx = allDivs[i].style.left;
				var posy = allDivs[i].style.top;
				var col = allDivs[i].style.backgroundColor;
				var hei = allDivs[i].style.height;
				var wid = allDivs[i].style.width;
				var nId = allDivs[i].getAttribute("id");
				var txt;
				for (var j = 0; j < allDivs[i].childNodes.length; j++) {
				    if (allDivs[i].childNodes[j].className == "text") {
				      txt = allDivs[i].childNodes[j].value;
				      break;
				    }        
				}
				var note = {posX: posx, posY: posy, color: col, heigth: hei, width: wid, text: txt, noteId: nId};
				xmlHttpRequest.send("note=" + JSON.stringify(note));
			}
		}
	</script>

	<%
		}
	%>

</body>
</html>