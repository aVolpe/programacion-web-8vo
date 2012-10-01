package py.com.pg.webstock.gwt.server;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.Session;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.info.Info;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.Pago;
import py.com.pg.webstock.gwt.client.ClienteABM.GuardarCallBack;
import py.com.pg.webstock.gwt.client.service.ClienteService;
import py.com.pg.webstock.gwt.client.service.ClienteServiceAsync;
import py.com.pg.webstock.gwt.client.service.PagoService;
import py.com.pg.webstock.gwt.client.service.PagoServiceAsync;

public class SubirArchivoServlet extends UploadAction {
	
	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	@Resource
	UserTransaction ut;
	
	
	SimpleDateFormat formatoFecha = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

	private static final long serialVersionUID = 1L;

	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String response = "Received file:";
		System.out.println("ME SHAMARONNNN");
		for (FileItem item : sessionFiles) {
			System.out.println("Cantidad archivos: " + sessionFiles.size());
			if (!item.isFormField()) {
				try {

					// we can save the received file
					// File file = File.createTempFile("receivedFile", ".tmp",
					// new File("C:\\"));
					// item.write(file);

					// response += " " + file.getPath();

					response += " " + item.getName() + ", size=  "
							+ item.getSize() + ", ContentType= "
							+ item.getContentType();
					// como se hacia?
					InputStreamReader isr = new InputStreamReader(
							item.getInputStream());
					BufferedReader br = new BufferedReader(isr);
					
					String linea = br.readLine();

					String partes[];
					while (linea != null && linea != "") {
						Pago nuevo = new Pago();
						partes = linea.split(";");
						nuevo.setCodPago(Integer.parseInt(partes[0]));
						Cliente c = em.find(Cliente.class, Integer.parseInt(partes[1]));
						nuevo.setCliente(c);
						nuevo.setMonto(Double.parseDouble(partes[2]));
						nuevo.setFecha(formatoFecha.parse(partes[3]));
						agregarPago(nuevo);
						linea = br.readLine();
					}
					
					
					
					
					
					//////////////////////
					
				} catch (Exception e) {
					throw new UploadActionException(e.getMessage());
				}
			}
		}

		try {
			// Remove files from session
			removeSessionFileItems(request);
		} catch (Exception e) {
			throw new UploadActionException(e.getMessage());
		}

		// Send information of the received files to the client.
		return response;
	}
	
	public void agregarPago(Pago nuevo) throws Exception {
		ut.begin();

		Cliente c = nuevo.getCliente();
		nuevo = em.merge(nuevo);
		if (nuevo.getEstado() != 1) {
			Double saldoCliente = c.getSaldo();
			saldoCliente = saldoCliente + nuevo.getMonto();
			c.setSaldo(saldoCliente);
			em.merge(c);
		}
		ut.commit();
	}
	
}
