//function WebSocketChat() {
//};
//WebSocketChat.prototype.onOpen = function() {
//	//NO SE USA
//	alert("Conectado");
//};
//
//WebSocketChat.prototype.onClose = function() {
//	// aca inicializar de nuevo todo
//	alert("Cerrado");
//	this.ws = null;
//};
//
//WebSocketChat.prototype.onError = function(error){
//	alert(error);
//};
//
//WebSocketChat.prototype.onMessage = function(m) {
//	alert(m.data);
//};
//
//WebSocketChat.prototype.send = function(s) {
//	if (this.ws)
//		this.ws.send(s);
//};
//WebSocketChat.prototype.join = function(name) {
//	this.usuario = name;
//	// var location = document.location.toString().replace('http://', 'ws://')
//	// .replace('https://', 'wss://');
//	var location = ('ws://localhost:8080/WebSocket/WebSocketChatServlet');
//	this.ws = new WebSocket(location);
//	this.ws.onopen = this.opOpen;
//	this.ws.onmessage = this.onMessage;
//	this.ws.onclose = this.onClose;
//	this.ws.onError = this.onError;
//	this.ws.send("EEEEEEE");
//	alert("Conectado");
//};

var wsUri = "ws://localhost:8080/WebSocket/WebSocketChatServlet";
function init() {
	testWebSocket(wsUri);
};

function testWebSocket(URL) {
	websocket = new WebSocket(URL);
	websocket.onopen = function(evt) {
		onOpen(evt);
	};
	websocket.onclose = function(evt) {
		onClose(evt);
	};
	websocket.onmessage = function(evt) {
		onMessage(evt);
	};
	websocket.onerror = function(evt) {
		onError(evt);
	};
}
function onOpen(evt) {
	send("Como estas parte java");
};
function onClose(evt) {
};
function onMessage(evt) {
	websocket.close();
};
function onError(evt) {
};
function send(message) {
	websocket.send(message);
};
window.addEventListener("load", init, false);
