package py.com.pol.pw.websocket;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

/**
 * Servlet implementation class WebSocketChatServlet
 */
@WebServlet("/WebSocketChatServlet")
public class WebSocketChatServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getNamedDispatcher("default").forward(request,
				response);
	}

	public WebSocket doWebSocketConnect(HttpServletRequest request,
			String protocol) {
		System.out.println("Creando ChatWebSocket");
		return new ChatWebSocket();
	}


}