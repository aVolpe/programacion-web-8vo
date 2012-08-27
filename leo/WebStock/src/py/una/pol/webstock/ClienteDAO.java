package py.una.pol.webstock;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClienteDAO {
	private Session sesion;
	private Transaction tx;

	private void iniciaOperacion() throws HibernateException {
		sesion = HibernateUtil.getSessionFactory().openSession();
		tx = sesion.beginTransaction();
	}

	private void manejaExcepcion(HibernateException he)
			throws HibernateException {
		tx.rollback();
		throw new HibernateException(
				"Ocurrió un error en la capa de acceso a datos", he);
	}

	public long guardarCliente(Cliente2 cliente) {
		long id = 0;

		try {
			iniciaOperacion();
			id = (Integer) sesion.save(cliente);
			tx.commit();
		} catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			sesion.close();
		}
		return id;
	}

	public void actualizarCliente(Cliente2 cliente) throws HibernateException {
		try {
			iniciaOperacion();
			sesion.update(cliente);
			tx.commit();
		} catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			sesion.close();
		}
	}

	public void eliminarCliente(Cliente2 cliente) throws HibernateException {
		try {
			iniciaOperacion();
			sesion.delete(cliente);
			tx.commit();
		} catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			sesion.close();
		}
	}

	public Cliente2 obtenerCliente(int idCliente) throws HibernateException {
		Cliente2 cliente = null;

		try {
			iniciaOperacion();
			cliente = (Cliente2) sesion.get(Cliente2.class, idCliente);
		} finally {
			sesion.close();
		}
		return cliente;
	}

	public List<Cliente2> obtenerListaClientes() throws HibernateException {
		List<Cliente2> listaClientes = null;

		try {
			iniciaOperacion();
			listaClientes = sesion.createQuery("from Cliente").list();
		} finally {
			sesion.close();
		}

		return listaClientes;
	}
}
