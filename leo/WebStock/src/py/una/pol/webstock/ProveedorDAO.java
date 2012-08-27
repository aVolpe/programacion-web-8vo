package py.una.pol.webstock;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProveedorDAO {
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
	
	public long guardarProveedor(Proveedor proveedor)
	{ 
	    long id = 0;  

	    try 
	    { 
	        iniciaOperacion(); 
	        id = (Integer)sesion.save(proveedor); 
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
	
	public void actualizarProveedor(Proveedor proveedor) throws HibernateException 
	{ 
	    try 
	    { 
	        iniciaOperacion(); 
	        sesion.update(proveedor); 
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
	
	public void eliminarProveedor(Proveedor proveedor) throws HibernateException 
	{ 
	    try 
	    { 
	        iniciaOperacion(); 
	        sesion.delete(proveedor); 
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
	

	public Proveedor obtenerProveedor(int idProveedor) throws HibernateException {
		Proveedor proveedor = null;

		try {
			iniciaOperacion();
			proveedor = (Proveedor) sesion.get(Proveedor.class, idProveedor);
		} finally {
			sesion.close();
		}
		return proveedor;
	}
	
	public List<Proveedor> obtenerListaProveedores() throws HibernateException 
	{ 
	    List<Proveedor> listaProveedores = null;  
	    
	    try 
	    { 
	        iniciaOperacion(); 
	        listaProveedores = sesion.createQuery("from Proveedor").list(); 
	    }finally 
	    { 
	        sesion.close(); 
	    }  

	    return listaProveedores; 
	}
}
