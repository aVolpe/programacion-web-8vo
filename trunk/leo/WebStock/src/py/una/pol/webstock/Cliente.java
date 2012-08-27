package py.una.pol.webstock;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
@Table(name="clientes")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	int id;
	String nombres;
	String apellidos;
	List<Cliente> clientes;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId(){
		return this.id;
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

	public Cliente(){
		
	}
	
	public Cliente(String nombres, String apellidos){
		this.nombres = nombres;
		this.apellidos = apellidos;
	}
	
	public String guardar(){
		System.out.println("Guardando cliente");
		
		ClienteDAO clienteDAO = new ClienteDAO(); 
        Cliente cliente = new Cliente(this.nombres, this.apellidos); 
        clienteDAO.guardarCliente(cliente);
        
		this.nombres="";
		this.apellidos="";
		
		return "faces/nuevoCliente";
	}
	
	@Transient
	public List<Cliente> getClientes() {
		
		ClienteDAO clienteDAO = new ClienteDAO();
		clientes = clienteDAO.obtenerListaClientes();
	 
		return clientes;
	}
	
	public String buscar(){
		ClienteDAO clienteDAO = new ClienteDAO(); 
        Cliente cliente = clienteDAO.obtenerCliente(this.id); 
        
		this.nombres=cliente.getNombres();
		this.apellidos=cliente.getApellidos();
		
		return "faces/modificarCliente";
	}

	public String actualizar(){
		ClienteDAO clienteDAO = new ClienteDAO();
		
		Cliente cliente = clienteDAO.obtenerCliente(this.id); 
		
		cliente.nombres = this.nombres;
		cliente.apellidos = this.apellidos;
		
		clienteDAO.actualizarCliente(cliente);
		
		this.nombres="";
		this.apellidos="";
		
		return "faces/modificarCliente";
	}
	
	public String eliminar(){
		ClienteDAO clienteDAO = new ClienteDAO();
		
		Cliente cliente = clienteDAO.obtenerCliente(this.id); 
		
		clienteDAO.eliminarCliente(cliente);		
		this.id = 0;
		this.nombres="";
		this.apellidos="";
		
		return "faces/eliminarCliente";
	}
	
}
