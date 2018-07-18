var elements = document.getElementById('sheetmenu').children;
for (var i = 0, len = elements.length; i < len; i++) {
	elements[i].onclick = function() {
		var id = this.getAttribute('linkto');
		for (var j = 0; j < len; j++) {
			elements[j].style.color = '#000000';
			elements[j].style.background = '#fff';
			document.getElementById(elements[j].getAttribute('linkto')).style.display = 'none';
		}
		document.getElementById(id).style.display = 'block';
		this.style.color = '#008ff3';
		this.style.background = 'lightgray';
	}
}