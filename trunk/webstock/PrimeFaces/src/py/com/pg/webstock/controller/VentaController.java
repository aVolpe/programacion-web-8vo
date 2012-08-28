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

import org.primefaces.context.RequestContext;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.DetalleVenta;
import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Venta;

@ManagedBean
@ApplicationScoped
public class VentaController {

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	@Resource
	UserTransaction ut;

	Venta seleccionado;
	DetalleVenta detalleSeleccionado;

	Venta nuevo;

	int idCliente;
	int idProducto;
	Long cantidad;

	DetalleVenta borrarDetalle;
	List<DetalleVenta> detalles;

	public List<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVenta> detalles) {
		this.detalles = detalles;
	}

	List<Venta> filtrados;

	public List<Venta> getVentas() {
		System.out.println("Get Ventas llamado");
		return em.createQuery("Select c from Venta c", Venta.class)
				.getResultList();
	}

	public List<Cliente> getClientes() {
		return em.createQuery("Select c from Cliente c", Cliente.class)
				.getResultList();
	}

	public List<Producto> getProductos() {
		TypedQuery<Producto> q = em.createQuery("Select p from Producto p",
				Producto.class);
		return q.getResultList();
	}

	public Venta getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Venta seleccionado) {
		this.seleccionado = seleccionado;
	}

	public List<Venta> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Venta> filtrados) {
		this.filtrados = filtrados;
	}

	public void aceptar(ActionEvent actionEvent) throws Exception {
		// validacion
		boolean fallo = false;
		for (DetalleVenta dc : detalles) {
			Producto p = dc.getProducto();
			if (p.getCantidad() < dc.getCantidad()) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, p
								.getNombre(),
								"No hay suficiente Stock, Actual: "
										+ p.getCantidad()));
				fallo = true;
			}
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("fallo", fallo);
		if (fallo) {
			return;
		}

		ut.begin();
		nuevo.setCliente(em.find(Cliente.class, idCliente));
		nuevo = em.merge(nuevo);
		for (DetalleVenta dc : detalles) {
			dc.setVenta(nuevo);
			em.merge(dc);
			Producto p = dc.getProducto();
			p.setCantidad(p.getCantidad() - dc.getCantidad());
			em.merge(p);
		}
		Cliente c = nuevo.getCliente();

		Double saldo = c.getSaldo() == null ? 0D : c.getSaldo();
		saldo -= nuevo.getTotal();
		c.setSaldo(saldo);
		em.merge(c);
		ut.commit();
	}

	public void empezarNuevo(ActionEvent actionEvent) {
		nuevo = new Venta();
		nuevo.setFecha(new Date());
		nuevo.setTotal(0D);
		detalles = new ArrayList<DetalleVenta>();
	}

	public void agregarDetalle(ActionEvent actionEvent) {

		Producto p = em.find(Producto.class, idProducto);
		if (p.getCantidad() < cantidad) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, p.getNombre(),
							"No hay suficiente Stock, Actual: "
									+ p.getCantidad()));
			return;
		}
		DetalleVenta nuevo = new DetalleVenta();
		nuevo.setProducto(p);
		nuevo.setCantidad(cantidad);
		nuevo.setPrecio(nuevo.getProducto().getPrecioVenta());
		detalles.add(nuevo);
		Double total = this.nuevo.getTotal();
		if (total == null)
			total = new Double(0D);
		total += nuevo.getPrecio() * nuevo.getCantidad();
		this.nuevo.setTotal(total);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta",
						"Detalle agregado"));
	}

	public DetalleVenta getBorrarDetalle() {
		return borrarDetalle;
	}

	public void setBorrarDetalle(DetalleVenta detalle) {
		borrarDetalle = detalle;
		nuevo.setTotal(nuevo.getTotal() - detalle.getPrecio()
				* detalle.getCantidad());
		detalles.remove(detalle);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta",
						"Detalle eliminado"));
	}

	public Venta getNuevo() {
		return nuevo;
	}

	public void setNuevo(Venta nuevo) {
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

	public DetalleVenta getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DetalleVenta detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

}
