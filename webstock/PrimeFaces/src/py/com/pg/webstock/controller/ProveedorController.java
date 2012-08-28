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

import py.com.pg.webstock.entities.Proveedor;

@ManagedBean
@ApplicationScoped
public class ProveedorController implements Serializable {

	private static final long serialVersionUID = 5740672086165352713L;

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	@Resource
	UserTransaction ut;

	private Proveedor proveedor;

	List<Proveedor> filtrados;

	private Proveedor seleccionado;

	public ProveedorController() {
		empezarNuevo();
	}

	public List<Proveedor> getProveedores() {
		System.out.println("GetProveedores llamado");
		List<Proveedor> proveedores = em.createQuery(
				"select p from Proveedor p", Proveedor.class).getResultList();
		for (Proveedor pro : proveedores) {
			System.out.println(pro.getId() + " - " + pro.getProductos().size());
		}
		return proveedores;
	}

	public void empezarNuevo() {
		proveedor = new Proveedor();
	}

	public void aceptar(ActionEvent actionEvent) throws NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		System.out.println("Creando nuevo Proveedor");
		ut.begin();
		em.merge(proveedor);
		// c.add(Proveedor);
		empezarNuevo();
		ut.commit();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Proveedor",
						"Se ha guardado correctamente"));
	}

	public void borrar(ActionEvent actionEvent) throws Exception {
		if (seleccionado == null) {
			System.out.println("No hay Proveedor seleccionado");
			return;
		} else {
			System.out.println("Borrando Proveedor: "
					+ seleccionado.getNombre() + "(" + seleccionado.getId()
					+ ")");
		}
		try {
			ut.begin();
			em.remove(em.merge(seleccionado));
			// c.remove(seleccionado);
			ut.commit();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Proveedor",
							"Se ha borrado correctamente"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Proveedor",
							"NO se puede borrar el proveedor"));
		}
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

}
