package py.una.pol.webstock;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
@Entity
@Table(name="productos")
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	int id;
	String descripcion;
	int cantidad;
	
	List<Producto> productos;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="producto_id")
	public int getId(){
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	
	public Producto(){
		
	}
	
	public Producto(String descripcion){
		this.descripcion= descripcion;
	}
	
	public String guardar(){
		System.out.println("Guardando producto");
		
		ProductoDAO productoDAO = new ProductoDAO(); 
        Producto producto = new Producto(this.descripcion); 
        productoDAO.guardarProducto(producto);
        
		this.descripcion="";
		
		return "faces/nuevoProducto";
	}
	
	@Transient
	public List<Producto> getProductos() {
		
		ProductoDAO productoDAO = new ProductoDAO();
		productos = productoDAO.obtenerListaProductos();
	 
		return productos;
	}
	
	public String buscar(){
		ProductoDAO productoDAO = new ProductoDAO(); 
        Producto producto = productoDAO.obtenerProducto(this.id); 
        
		this.descripcion=producto.getDescripcion();
		
		return "faces/modificarProducto";
	}

	public String actualizar(){
		ProductoDAO productoDAO = new ProductoDAO();
		
		Producto producto = productoDAO.obtenerProducto(this.id); 
		
		producto.descripcion = this.descripcion;
		
		productoDAO.actualizarProducto(producto);
		
		this.descripcion="";
		
		return "faces/modificarProducto";
	}
	
	public String eliminar(){
		ProductoDAO productoDAO = new ProductoDAO();
		
		Producto producto = productoDAO.obtenerProducto(this.id); 
		
		productoDAO.eliminarProducto(producto);		
		this.id = 0;
		this.descripcion="";
		
		return "faces/eliminarProducto";
	}
	
}
