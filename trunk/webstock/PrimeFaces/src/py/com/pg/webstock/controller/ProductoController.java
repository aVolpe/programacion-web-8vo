package py.com.pg.webstock.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.primefaces.context.RequestContext;

import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Proveedor;

@ManagedBean
@ApplicationScoped
public class ProductoController implements Serializable {

	private static final long serialVersionUID = -2704809198045749780L;

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	@Resource
	UserTransaction ut;

	private Producto producto;

	private int proveedor;

	List<Producto> filtrados;

	private Producto seleccionado;

	public ProductoController() {
		empezarNuevo();
	}

	public List<Producto> getProductos() {
		System.out.println("GetProductos llamado");
		List<Producto> p = em.createQuery("select p from Producto p",
				Producto.class).getResultList();
		System.out.println("--------------------");
		System.out.println(p.size());
		return p;
	}

	public List<Proveedor> getProveedores() {
		return em.createQuery("select p from Proveedor p", Proveedor.class)
				.getResultList();
	}

	public void empezarNuevo() {
		producto = new Producto();
	}

	public void aceptar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		System.out.println("Creando nuevo Producto");
		ut.begin();
		Proveedor p = em.find(Proveedor.class, proveedor);
		producto.setProveedor(p);
		em.merge(producto);

		// c.add(Producto);
		empezarNuevo();
		ut.commit();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto",
						"Se ha guardado correctamente"));
	}

	public void borrar(ActionEvent actionEvent) throws Exception {
		try {
			if (seleccionado == null) {
				System.out.println("No hay Producto seleccionado");
				return;
			} else {
				System.out.println("Borrando Producto: "
						+ seleccionado.getNombre() + "(" + seleccionado.getId()
						+ ")");
			}
			ut.begin();
			em.remove(em.merge(seleccionado));
			// c.remove(seleccionado);
			ut.commit();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto",
							"Se ha borrado correctamente"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Producto",
							"No se pudo borrar. " + e.getMessage()));
			e.printStackTrace();
		}
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

	public int getProveedor() {
		return proveedor;
	}

	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}

}
