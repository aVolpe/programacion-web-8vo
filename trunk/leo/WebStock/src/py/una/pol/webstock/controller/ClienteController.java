package py.una.pol.webstock.controller;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.primefaces.context.RequestContext;

import py.una.pol.webstock.daos.ClienteDAO;
import py.una.pol.webstock.entities.Cliente;

@ManagedBean
@ApplicationScoped
public class ClienteController {

	ClienteDAO clienteDAO = new ClienteDAO();
	private Cliente cliente;
	List<Cliente> c;
	List<Cliente> filtrados;
	private Cliente seleccionado;

	public ClienteController() {
		empezarNuevo();
	}

	public List<Cliente> getClientes() {
		System.out.println("GetClientes llamado");
		// if (c == null)
		c = clienteDAO.getAll();
		return c;
	}

	public void empezarNuevo() {
		cliente = new Cliente();
		cliente.setFechaAlta(new Date());
	}

	public void aceptar(ActionEvent actionEvent) {
		System.out.println("Creando nuevo cliente");
		clienteDAO.create(cliente);
		empezarNuevo();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente",
						"Se ha guardado correctamente"));
	}

	public void borrar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		if (seleccionado == null) {
			System.out.println("No hay cliente seleccionado");
			return;
		} else {
			System.out.println("Borrando cliente: " + seleccionado.getNombre()
					+ "(" + seleccionado.getId() + ")");
		}
		clienteDAO.remove(seleccionado);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente",
						"Se ha borrado correctamente"));
	}

	public void editar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		RequestContext context = RequestContext.getCurrentInstance();
		if (seleccionado == null) {
			System.out.println("No hay cliente seleccionado");
			context.addCallbackParam("puedeEditarClienteABM", false);
			return;
		}
		System.out.println("Editando cliente: " + seleccionado.getNombre()
				+ "(" + seleccionado.getId() + ")");
		context.addCallbackParam("puedeEditarClienteABM", true);
		cliente = seleccionado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Cliente seleccionados) {
		this.seleccionado = seleccionados;
	}

	public List<Cliente> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Cliente> filtrados) {
		this.filtrados = filtrados;
	}
}
