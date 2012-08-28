package py.com.pg.webstock.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import py.com.pg.webstock.entities.Compra;
import py.com.pg.webstock.entities.DetalleCompra;
import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Proveedor;

@ManagedBean
@ApplicationScoped
public class CompraController {

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	@Resource
	UserTransaction ut;

	Compra seleccionado;
	DetalleCompra detalleSeleccionado;

	Compra nuevo;

	int idProveedor;
	int idProducto;
	Long cantidad;

	DetalleCompra borrarDetalle;
	List<DetalleCompra> detalles;

	public List<DetalleCompra> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCompra> detalles) {
		this.detalles = detalles;
	}

	List<Compra> filtrados;

	public List<Compra> getCompras() {
		System.out.println("Get Compras llamado");
		return em.createQuery("Select c from Compra c", Compra.class)
				.getResultList();
	}

	public List<Proveedor> getProveedores() {
		return em.createQuery("Select p from Proveedor p", Proveedor.class)
				.getResultList();
	}

	public List<Producto> getProductos() {
		if (idProveedor == 0) {
			return null;
		}
		Proveedor p = em.find(Proveedor.class, idProveedor);
		TypedQuery<Producto> q = em.createQuery(
				"Select p from Producto p where p.proveedor=:proveedor",
				Producto.class);
		q.setParameter("proveedor", p);
		return q.getResultList();
	}

	public Compra getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Compra seleccionado) {
		this.seleccionado = seleccionado;
	}

	public List<Compra> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Compra> filtrados) {
		this.filtrados = filtrados;
	}

	public void borrar(ActionEvent actionEvent) {
		if (seleccionado == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra",
							"Seleccione una compra"));
			return;
		}
		try {
			ut.begin();
			List<DetalleCompra> detalles = em
					.createQuery(
							"select d from DetalleCompra d where d.compra=:compra",
							DetalleCompra.class)
					.setParameter("compra", seleccionado).getResultList();
			for (DetalleCompra detalle : detalles) {
				em.remove(detalle);
			}
			em.remove(em.find(Compra.class, seleccionado.getId()));
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra",
							"Borrado con exito"));
			ut.commit();
			seleccionado = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra",
							"No se pudo borrar"));
			e.printStackTrace();
		}
	}

	public void aceptar(ActionEvent actionEvent) throws Exception {

		ut.begin();
		nuevo.setProveedor(em.find(Proveedor.class, idProveedor));
		nuevo = em.merge(nuevo);
		for (DetalleCompra dc : detalles) {
			dc.setCompra(nuevo);
			em.merge(dc);
			Producto p = dc.getProducto();
			p.setCantidad(p.getCantidad() + dc.getCantidad());
			em.merge(p);
		}
		ut.commit();
	}

	public void empezarNuevo(ActionEvent actionEvent) {
		nuevo = new Compra();
		nuevo.setFecha(new Date());
		nuevo.setTotal(0D);
		detalles = new ArrayList<DetalleCompra>();
	}

	public void agregarDetalle(ActionEvent actionEvent) {
		DetalleCompra nuevo = new DetalleCompra();
		nuevo.setProducto(em.find(Producto.class, idProducto));
		nuevo.setCantidad(cantidad);
		nuevo.setPrecio(nuevo.getProducto().getPrecioCompra());
		detalles.add(nuevo);
		Double total = this.nuevo.getTotal();
		if (total == null)
			total = new Double(0D);
		total += nuevo.getPrecio() * nuevo.getCantidad();
		this.nuevo.setTotal(total);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra",
						"Detalle agregado"));
	}

	public DetalleCompra getBorrarDetalle() {
		return borrarDetalle;
	}

	public void setBorrarDetalle(DetalleCompra detalle) {
		borrarDetalle = detalle;
		nuevo.setTotal(nuevo.getTotal() - detalle.getPrecio()
				* detalle.getCantidad());
		detalles.remove(detalle);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra",
						"Detalle eliminado"));
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Compra getNuevo() {
		return nuevo;
	}

	public void setNuevo(Compra nuevo) {
		this.nuevo = nuevo;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public DetalleCompra getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DetalleCompra detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

}
