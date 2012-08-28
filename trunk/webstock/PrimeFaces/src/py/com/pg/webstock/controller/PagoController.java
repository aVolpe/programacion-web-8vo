package py.com.pg.webstock.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.primefaces.event.FileUploadEvent;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.Pago;

@ManagedBean
@ApplicationScoped
public class PagoController {

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	@Resource
	UserTransaction ut;

	int idCliente;
	Pago nuevo;

	List<Pago> filtrados;
	Pago seleccionado;

	SimpleDateFormat formatoFecha = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

	public void cargarArchivo(FileUploadEvent event) throws Exception {

		InputStreamReader isr = new InputStreamReader(event.getFile()
				.getInputstream());
		BufferedReader bf = new BufferedReader(isr);
		String linea = bf.readLine();
		String partes[];
		while (linea != null && linea != "") {
			Pago nuevo = new Pago();
			partes = linea.split(";");
			nuevo.setCodPago(Integer.parseInt(partes[0]));
			Cliente c = em.find(Cliente.class, Integer.parseInt(partes[1]));
			nuevo.setCliente(c);
			nuevo.setMonto(Double.parseDouble(partes[2]));
			nuevo.setfecha(formatoFecha.parse(partes[3]));
			agregarPago(nuevo);
			linea = bf.readLine();
		}
		FacesMessage msg = new FacesMessage("El archivo", event.getFile()
				.getFileName() + " ha sido subido exitosamente.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void agregarPago(Pago nuevo) throws Exception {
		ut.begin();

		Cliente c = nuevo.getCliente();
		nuevo = em.merge(nuevo);
		if (nuevo.getEstado() > 0) {
			Double saldoCliente = c.getSaldo();
			saldoCliente = saldoCliente + nuevo.getMonto();
			c.setSaldo(saldoCliente);
			em.merge(c);
		}
		ut.commit();
	}

	public void aceptar() throws Exception {
		Cliente c = em.find(Cliente.class, idCliente);
		nuevo.setCliente(c);
		agregarPago(nuevo);
		empezarNuevo();

	}

	public void empezarNuevo() {
		nuevo = new Pago();
		nuevo.setfecha(new Date());
	}

	public List<Cliente> getClientes() {
		return em.createQuery("Select c from Cliente c", Cliente.class)
				.getResultList();
	}

	public void borrar() throws Exception {

		ut.begin();
		em.remove(em.find(Pago.class, seleccionado.getId()));
		ut.commit();

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Pagos", "Pago Eliminado con exito"));
	}

	public List<Pago> getPagos() {
		return em.createQuery("Select p from Pago p", Pago.class)
				.getResultList();
		// return null;
	}

	public List<Pago> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Pago> filtrados) {
		this.filtrados = filtrados;
	}

	public Pago getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Pago seleccionado) {
		this.seleccionado = seleccionado;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public Pago getNuevo() {
		return nuevo;
	}

	public void setNuevo(Pago nuevo) {
		this.nuevo = nuevo;
	}
}
