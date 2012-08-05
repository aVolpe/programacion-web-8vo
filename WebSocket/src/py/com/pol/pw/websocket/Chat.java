package py.com.pol.pw.websocket;

import java.util.ArrayList;
import java.util.List;

public class Chat {

	public static List<Chat> chats = new ArrayList<Chat>();
	public int id;
	private List<Usuario> usuarios;
	private ChatWebSocket socket;
	public Chat(ChatWebSocket socket) {
		usuarios = new ArrayList<Usuario>();
		this.socket = socket;
		this.id = ChatWebSocket.contadorUser++;
	}
	public void addUser(Usuario nuevo){
		usuarios.add(nuevo);
	}
	
	
	public void removeUser(Usuario user){
		notificarSalida(user);
		usuarios.remove(user);
	}
	private void notificarSalida(Usuario salido){
		for (Usuario user : usuarios){
			if (user != salido){
				socket.enviarMensaje(user.conn, "Salido:" + salido.id);
			}
		}
	}
	
}
