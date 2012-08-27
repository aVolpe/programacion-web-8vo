package py.una.pol.webstock.controller;

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

import py.una.pol.webstock.daos.ProductoDAO;
import py.una.pol.webstock.entities.Producto;

@ManagedBean
@ApplicationScoped
public class ProductoController {

	private Producto producto;

	List<Producto> filtrados;

	private ProductoDAO dao;
	private Producto seleccionado;

	public ProductoController() {
		dao = new ProductoDAO();
		empezarNuevo();
	}

	public List<Producto> getProductos() {
		System.out.println("GetProductos llamado");
		return dao.getAll();
	}

	public void empezarNuevo() {
		producto = new Producto();
	}

	public void aceptar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		System.out.println("Creando nuevo Producto");
		dao.create(producto);
		empezarNuevo();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto",
						"Se ha guardado correctamente"));
	}

	public void borrar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		if (seleccionado == null) {
			System.out.println("No hay Producto seleccionado");
			return;
		} else {
			System.out.println("Borrando Producto: " + seleccionado.getNombre()
					+ "(" + seleccionado.getId() + ")");
		}
		dao.remove(seleccionado);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto",
						"Se ha borrado correctamente"));
	}

	public void editar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		RequestContext context = RequestContext.getCurrentInstance();
		if (seleccionado == null) {
			System.out.println("No hay Producto seleccionado");
			context.addCallbackParam("puedeEditarProductoABM", false);
			return;
		}
		System.out.println("Editando Producto: " + seleccionado.getNombre()
				+ "(" + seleccionado.getId() + ")");
		context.addCallbackParam("puedeEditarProductoABM", true);
		producto = seleccionado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto Producto) {
		this.producto = Producto;
	}

	public Producto getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Producto seleccionados) {
		this.seleccionado = seleccionados;
	}

	public List<Producto> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Producto> filtrados) {
		this.filtrados = filtrados;
	}
}
