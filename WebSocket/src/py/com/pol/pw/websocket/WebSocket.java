package py.com.pol.pw.websocket;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WebSocket
 */
@WebServlet("/WebSocket")
public class WebSocket extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public static int ix = 0;

	public WebSocket() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		String forward = "";

		String url = "websocket.jsp"; // relative url for display jsp page
//		ServletContext sc = getServletContext();
		RequestDispatcher rd = request.getRequestDispatcher(url);
		System.out.println("HOLAAAAAAA");
		// request.setAttribute("accountList", accounts );
		request.setAttribute("HOLA", ix++);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	

}
