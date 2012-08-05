package py.com.pol.pw.websocket;

import org.eclipse.jetty.websocket.WebSocket.Connection;

public class Usuario {
	Integer id;
	String nombre;
	String color;
	Connection conn;

	public Usuario(String nombre) {
		this.nombre = nombre;
		this.id = ChatWebSocket.contadorUser++;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(nombre);
		sb.append(ChatWebSocket.TOKEN_SEPARADOR_VALUES);
		sb.append(id);
		if (color != null) {
			sb.append(ChatWebSocket.TOKEN_SEPARADOR_VALUES);
			sb.append(color);
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Usuario)) {
			return false;
		} else {
			return ((Usuario) obj).id == id;
		}
	}
}