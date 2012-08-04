function WebSocketChat() {
};
WebSocketChat.prototype.onOpen = function() {
	alert("CONECTADO");
};

WebSocketChat.prototype.onClose = function() {
	// aca inicializar de nuevo todo
	alert("Cerrado");
	this.ws = null;
};

WebSocketChat.prototype.onMessage = function(m) {
	alert(m);
};

WebSocketChat.prototype.send = function(s) {
	if (this.ws)
		this.ws.send(s);
};
WebSocketChat.prototype.join = function(name) {
	this.usuario = name;
	// var location = document.location.toString().replace('http://', 'ws://')
	// .replace('https://', 'wss://');
	var location = ('ws://localhost:8080/WebSocket/WebSocketChatServlet');
	this.ws = new WebSocket(location);
	this.ws.onopen = this.opOpen;
	this.ws.onmessage = this.onMessage;
	this.ws.onclose = this.onClose;
	alert("Conectado");
};
