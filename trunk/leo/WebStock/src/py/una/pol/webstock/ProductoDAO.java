package py.una.pol.webstock;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductoDAO {
	private Session sesion;
	private Transaction tx;
	

	private void iniciaOperacion() throws HibernateException {
		sesion = HibernateUtil.getSessionFactory().openSession();
		tx = sesion.beginTransaction();
	}
	
	private void manejaExcepcion(HibernateException he) throws HibernateException
	{
	    tx.rollback();
	    throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
	}
	
	public long guardarProducto(Producto producto)
	{ 
	    long id = 0;  

	    try 
	    { 
	        iniciaOperacion(); 
	        id = (Integer)sesion.save(producto); 
	        tx.commit(); 
	    }catch(HibernateException he) 
	    { 
	        manejaExcepcion(he);
	        throw he; 
	    }finally 
	    { 
	        sesion.close(); 
	    }  
	    return id; 
	}
	
	public void actualizarProducto(Producto producto) throws HibernateException 
	{ 
	    try 
	    { 
	        iniciaOperacion(); 
	        sesion.update(producto); 
	        tx.commit(); 
	    }catch (HibernateException he) 
	    { 
	        manejaExcepcion(he); 
	        throw he; 
	    }finally 
	    { 
	        sesion.close(); 
	    } 
	}
	
	public void eliminarProducto(Producto producto) throws HibernateException 
	{ 
	    try 
	    { 
	        iniciaOperacion(); 
	        sesion.delete(producto); 
	        tx.commit(); 
	    } catch (HibernateException he) 
	    { 
	        manejaExcepcion(he); 
	        throw he; 
	    }finally 
	    { 
	        sesion.close(); 
	    } 
	}
	

	public Producto obtenerProducto(int idProducto) throws HibernateException {
		Producto producto = null;

		try {
			iniciaOperacion();
			producto = (Producto) sesion.get(Producto.class, idProducto);
		} finally {
			sesion.close();
		}
		return producto;
	}
	
	public List<Producto> obtenerListaProductos() throws HibernateException 
	{ 
	    List<Producto> listaProductos = null;  
	    
	    try 
	    { 
	        iniciaOperacion(); 
	        listaProductos = sesion.createQuery("from Producto").list(); 
	    }finally 
	    { 
	        sesion.close(); 
	    }  

	    return listaProductos; 
	}
}