package py.com.pol.pw.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.jetty.websocket.WebSocket;

class ChatWebSocket implements WebSocket.OnTextMessage {

	public static final char TOKEN_SEPARADOR_VALUES = '|';
	public static final char TOKEN_SEPARADOR_USERS = '&';
	public static HashMap<Integer, Usuario> contactos;
	public static int contadorUser = 3;
	public Connection conn;

	@Override
	public void onClose(int arg0, String arg1) {
		Usuario aborrar = null;

		for (Entry<Integer, Usuario> entry : contactos.entrySet()) {
			if (entry.getValue().conn == conn) {
				aborrar = entry.getValue();
				break;
			}
		}
		if (aborrar != null) {
			contactos.remove(aborrar.id);
			System.out.println("Cliente " + aborrar.toString() + " eliminado.");
		}
	}

	@Override
	public void onOpen(Connection conn) {
		this.conn = conn;
		if (contactos == null) {
			contactos = new HashMap<>();
			contactos.put(1, new Usuario("Mirta"));
			contactos.put(2, new Usuario("beatriz"));
			contactos.put(3, new Usuario("mirtita"));
		}
		System.out.println("Chat web socket conectado");

	}

	/**
	 * Posibles mensajes: <br>
	 * <b>"usuarios"</b> : pide lista de usuarios <br>
	 * <b>"login:nombre|color" </b>: registra un usuario <br>
	 * <b>"mensaje:id|mensaje"</b> : envia un mensaje al id, si id = 0, envia a
	 * todos
	 */
	@Override
	public void onMessage(String mensaje) {
		System.out.println(mensaje);
		if (mensaje.startsWith("u") == true) {
			System.out.println("Enviando lista de contactos");
			enviarListaContactos();
			return;
		}
		if (mensaje.startsWith("l")) {
			System.out.println("Creando nuevo usuario");
			String partes[] = ChatWebSocket.partir(mensaje.split(":")[1], '|');
			System.out.println(mensaje.split(":")[1]);
			String nombre = partes[0];
			System.out.println(nombre);
			Usuario nuevo = new Usuario(nombre);
			nuevo.color = partes[1];
			nuevo.id = contadorUser++;
			nuevo.conn = conn;
			enviarMensaje(conn, "Logueado:" + nuevo.id);
			contactos.put(nuevo.id, nuevo);
			System.out.println("Nuevo usuario creado" + nuevo.toString());
			// avisarATodos(nuevo);
			return;
		}
		if (mensaje.startsWith("m")) {
			System.out.println("Enviando mensaje");
			String partes[] = mensaje.split(":")[1].split("|");
			Integer id = Integer.parseInt(partes[0]);
			if (contactos.get(id) != null) {
				enviarMensaje(contactos.get(id).conn, partes[1]);
			}
		}
	}

	public void enviarListaContactos() {
		if (contactos == null)
			return;
		String mensaje = "Usuarios:";
		for (Entry<Integer, Usuario> entry : contactos.entrySet()) {
			mensaje += entry.getValue().toString() + TOKEN_SEPARADOR_USERS;
		}
		enviarMensaje(conn, mensaje.substring(0, mensaje.length() - 1));
	}

	/**
	 * Posibles mensajes a enviar: <br>
	 * <b>Usuarios:nombre|id|color&nombre2|id2|color2</b> : lista de usuarios<br>
	 * <b>NUsuario:nombre|id|color</b> : lista de usuarios<br>
	 * <b>Mensajes:id|mensaje</b> : mensaje del id ID<br>
	 * <b>Logueado:id</b> : id > 0 para exito, <0 para reintento
	 * 
	 * @param mensaje
	 */
	public void enviarMensaje(Connection conn, String mensaje) {
		try {
			conn.sendMessage(mensaje);

		} catch (IOException e) {
			System.out.println("No pude enviar");
			e.printStackTrace();
		}
	}

	public void avisarATodos(Usuario nuevo) {
		if (contactos == null)
			return;
		for (Entry<Integer, Usuario> entry : contactos.entrySet()) {
			String mensaje = "NUsuario:" + entry.getValue().toString();
			enviarMensaje(entry.getValue().conn, mensaje);
		}
	}

	static class Usuario {
		Integer id;
		String nombre;
		String color;
		Connection conn;

		public Usuario(String nombre) {
			this.nombre = nombre;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(nombre);
			sb.append(TOKEN_SEPARADOR_VALUES);
			sb.append(id);
			if (color != null) {
				sb.append(TOKEN_SEPARADOR_VALUES);
				sb.append(color);
			}
			return sb.toString();
		}
	}

	public static String[] partir(String cadena, char letra) {
		ArrayList<String> resultado = new ArrayList<String>();
		String cadenaString = "";
		int idInicio = 0;
		for (int i = 0; i < cadena.length(); i++) {
			if (cadena.charAt(i) == letra) {
				resultado.add(cadena.substring(idInicio, i));
				idInicio = i;
			}
		}
		return resultado.toArray(new String[1]);
	}
}