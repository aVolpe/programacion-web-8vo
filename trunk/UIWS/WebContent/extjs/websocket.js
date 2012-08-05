
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
	recibirMensaje(evt);
};
function onError(evt) {
};
function send(message) {
	websocket.send(message);
};
window.addEventListener("load", init, false);
