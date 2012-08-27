package py.una.pol.webstock.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.primefaces.context.RequestContext;

import py.una.pol.webstock.daos.ProductoDAO;
import py.una.pol.webstock.daos.ProveedorDAO;
import py.una.pol.webstock.entities.Producto;
import py.una.pol.webstock.entities.Proveedor;

@ManagedBean
@ViewScoped
public class ProveedorController {

	private Proveedor proveedor;

	private Producto productoSeleccionado;

	List<Proveedor> filtrados;

	ProveedorDAO dao;
	ProductoDAO productoDAO;
	private Proveedor seleccionado;

	private Producto nuevoProducto;

	public ProveedorController() {
		empezarNuevo();
		dao = new ProveedorDAO();
		productoDAO = new ProductoDAO();
	}

	public List<Producto> getProductos() {
		return productoDAO.getAll();
	}

	public List<Proveedor> getProveedores() {
		System.out.println("GetProveedores llamado");
		return dao.getAll();
	}

	public void empezarNuevo() {
		proveedor = new Proveedor();
	}

	public void aceptarButtonNewProduct(ActionEvent actionEvent)
			throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		System.out.println("Creando nuevo Proveedor");

		proveedor.getProductos().add(nuevoProducto);
		dao.update(proveedor);
	}

	public void aceptar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		System.out.println("Creando nuevo Proveedor");

		dao.create(proveedor);
		empezarNuevo();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Proveedor",
						"Se ha guardado correctamente"));
	}

	public void borrar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		if (seleccionado == null) {
			System.out.println("No hay Proveedor seleccionado");
			return;
		} else {
			System.out.println("Borrando Proveedor: "
					+ seleccionado.getNombre() + "(" + seleccionado.getId()
					+ ")");
		}
		dao.remove(seleccionado);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Proveedor",
						"Se ha borrado correctamente"));
	}

	public void editar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		RequestContext context = RequestContext.getCurrentInstance();
		if (seleccionado == null) {
			System.out.println("No hay Proveedor seleccionado");
			context.addCallbackParam("puedeEditarProveedorABM", false);
			return;
		}
		System.out.println("Editando Proveedor: " + seleccionado.getNombre()
				+ "(" + seleccionado.getId() + ")");
		context.addCallbackParam("puedeEditarProveedorABM", true);
		proveedor = seleccionado;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Proveedor getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Proveedor seleccionados) {
		this.seleccionado = seleccionados;
	}

	public List<Proveedor> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Proveedor> filtrados) {
		this.filtrados = filtrados;
	}

	public Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(Producto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public Producto getNuevoProducto() {
		return nuevoProducto;
	}

	public void setNuevoProducto(Producto nuevoProducto) {
		this.nuevoProducto = nuevoProducto;
	}
}
