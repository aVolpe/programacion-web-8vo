package py.com.pol.pw.websocket;

import java.io.IOException;


import org.eclipse.jetty.websocket.WebSocket;

class ChatWebSocket implements WebSocket.OnTextMessage {

	@Override
	public void onClose(int arg0, String arg1) {
		System.out.println("Chat web socket cerrado");
	}

	
	@Override
	public void onOpen(Connection conn) {
		System.out.println("Chat web socket conectado");
		try {
			conn.sendMessage("EEEEEEEEEE");
			
		} catch (IOException e) {
			System.out.println("No pude enviar");
			e.printStackTrace();
		}
	}


	@Override
	public void onMessage(String arg0) {
		System.out.println(arg0);
		
	}

}