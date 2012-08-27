package py.una.pol.webstock;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;

@ManagedBean
@SessionScoped
@Entity
@Table(name = "proveedores")
public class Proveedor2 implements Serializable {

	private static final long serialVersionUID = 1L;

	public int id;
	public String nombres;
	public String apellidos;
	List<Proveedor2> proveedores;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "proveedor_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Proveedor2() {

	}

	public Proveedor2(String nombres, String apellidos) {
		this.nombres = nombres;
		this.apellidos = apellidos;
	}

	public String guardar() {
		System.out.println("Guardando proveedor");

		ProveedorDAO proveedorDAO = new ProveedorDAO();
		Proveedor2 proveedor = new Proveedor2(this.nombres, this.apellidos);
		proveedorDAO.guardarProveedor(proveedor);

		this.nombres = "";
		this.apellidos = "";

		return "faces/nuevoProveedor";
	}

	@Transient
	public List<Proveedor2> getProveedores() {

		ProveedorDAO proveedorDAO = new ProveedorDAO();
		proveedores = proveedorDAO.obtenerListaProveedores();

		return proveedores;
	}

	public String buscar() {
		ProveedorDAO proveedorDAO = new ProveedorDAO();
		Proveedor2 proveedor = proveedorDAO.obtenerProveedor(this.id);

		this.nombres = proveedor.getNombres();
		this.apellidos = proveedor.getApellidos();

		return "faces/modificarProveedor";
	}

	public String actualizar() {
		ProveedorDAO proveedorDAO = new ProveedorDAO();

		Proveedor2 proveedor = proveedorDAO.obtenerProveedor(this.id);

		proveedor.nombres = this.nombres;
		proveedor.apellidos = this.apellidos;

		proveedorDAO.actualizarProveedor(proveedor);

		this.nombres = "";
		this.apellidos = "";

		return "faces/modificarProveedor";
	}

	public String eliminar() {
		ProveedorDAO proveedorDAO = new ProveedorDAO();

		Proveedor2 proveedor = proveedorDAO.obtenerProveedor(this.id);

		proveedorDAO.eliminarProveedor(proveedor);
		this.id = 0;
		this.nombres = "";
		this.apellidos = "";

		return "faces/eliminarProveedor";
	}
}
