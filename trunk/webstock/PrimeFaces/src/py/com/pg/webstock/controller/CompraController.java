package py.com.pg.webstock.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

	Compra nuevo;

	int idProveedor;
	int idProducto;

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
	
	public List<Producto> getProductos(){
		if (idProveedor == 0) {
			return null;
		}
		Proveedor p = em.find(Proveedor.class, idProveedor);
		return null;
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

	public void empezarNuevo(ActionEvent actionEvent) {
		nuevo = new Compra();
		nuevo.setFecha(new Date());
		List<DetalleCompra> detalles = new ArrayList<DetalleCompra>();
		DetalleCompra d = new DetalleCompra();
		d.setCantidad(200L);
		d.setPrecio(200D);
		detalles.add(d);
		nuevo.setTotal(d.getCantidad() * d.getPrecio());
		nuevo.setDetalles(detalles);
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

}
