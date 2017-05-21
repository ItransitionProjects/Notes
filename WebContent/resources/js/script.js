var colors = [ '#faefff', '#dde5ff', '#effff8', '#eff7ff', '#feffef', '#ffeff6' ];
function colorChange(e) {
	$(e).css('background-color',
			colors[Math.floor(Math.random() * (6 - 0 + 1)) + 0]);
}

function loadNote(posx,posy,col,heigth,width,text,noteId) {
	var innerHtml = "<div class='changecolor'></div>" +
					"<div class='closebutton'></div>" +
					"<textarea class='text' onchange='saveData()'>" + text + "</textarea>";
	var div = document.createElement('div');
	div.setAttribute("id", noteId);
	div.className = 'drag';
	div.style.top = posy;
	div.style.left = posx;
	div.style.paddingTop = '30px'
	div.style.marginTop = "30px";
	div.style.backgroundColor = col;
	div.style.height = heigth;
	div.style.width = width;
	div.innerHTML = innerHtml;
	document.body.appendChild(div);
	$(".drag").draggable().resizable();
	$('.drag').click(function(event) {event.stopPropagation();});
	$('.text').click(function(event) {event.stopPropagation();});
	$('.closebutton').click(function(event){
		deleteNote(div);
		$(this).closest('.drag').remove();
		event.stopPropagation();
		});
	$('.changecolor').click(function(event){
		colorChange($(this).closest('.drag'));
		event.stopPropagation();
		});
}

function addNote(e) {

	var innerHtml = "<div class='changecolor'></div>" +
					"<div class='closebutton'></div>" +
					"<textarea class='text' onchange='saveData()'></textarea>";
	var posx = e.pageX;
	var posy = e.pageY - 30;
	var div = document.createElement('div');
	div.setAttribute("id", "0");
	div.className = 'drag';
	div.style.top = posy + 'px';
	div.style.left = posx + 'px';
	div.style.paddingTop = '30px'
	div.style.marginTop = "30px";
	div.innerHTML = innerHtml;
	document.body.appendChild(div);
	$(".drag").draggable().resizable();
	$('.drag').click(function(event) {event.stopPropagation();});
	$('.text').click(function(event) {event.stopPropagation();});
	$('.closebutton').click(function(event){
		deleteNote(div);
		$(this).closest('.drag').remove();
		event.stopPropagation();
		});
	$('.changecolor').click(function(event){
		colorChange($(this).closest('.drag'));
		event.stopPropagation();
		});
}

function deleteNote(div) {
		if(div.getAttribute("id") == "0"){
			return;
		}
		var xmlHttpRequest = getXMLHttpRequest();
		xmlHttpRequest.onreadystatechange = getReadyStateHandler(xmlHttpRequest);
		xmlHttpRequest.open("POST", "NotesServlet.do", true);
		xmlHttpRequest.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");

		xmlHttpRequest.send("delete=true &noteId=" + div.getAttribute("id"));
	
}


function getXMLHttpRequest() {
	var xmlHttpReq;
	if (window.XMLHttpRequest) {
		xmlHttpReq = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		try {
			xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (exp1) {
			try {
				xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (exp2) {
				alert("Exception in getXMLHttpRequest()!");
			}
		}
	}
	return xmlHttpReq;
}

function getReadyStateHandler(xmlHttpRequest) {
	return function() {
		if (xmlHttpRequest.readyState == 4) {
			if (xmlHttpRequest.status == 200) {
				var divs = document.getElementsByClassName("drag");
				var i;
				for(i = 0 ; i < divs.length; ++i){				
					if(divs[i].getAttribute("id") == "0"){
						divs[i].setAttribute("id", xmlHttpRequest.responseText);
						break;
					}
				}
			} else {
				alert("Http error " + xmlHttpRequest.status + ":"
						+ xmlHttpRequest.statusText);
			}
		}
	};
}
