package py.una.pol.webstock.daos;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import py.una.pol.webstock.HibernateUtil;
import py.una.pol.webstock.entities.BaseEntity;

public abstract class BaseDAO<T extends BaseEntity> {

	private Session sesion;
	private Transaction tx;

	public BaseDAO() {

	}

	@SuppressWarnings("unchecked")
	public Class<T> getClase() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	private void iniciaOperacion() throws HibernateException {
		sesion = HibernateUtil.getSessionFactory().openSession();
		tx = sesion.beginTransaction();
	}

	public long create(T entidad) {
		long id = 0;

		try {
			iniciaOperacion();
			id = (Integer) sesion.save(entidad);
			tx.commit();
		} catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			sesion.close();
		}
		return id;
	}

	public void update(T entidad) throws HibernateException {
		try {
			iniciaOperacion();
			sesion.update(entidad);
			tx.commit();
		} catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			sesion.close();
		}
	}

	public void remove(T entidad) throws HibernateException {
		try {
			iniciaOperacion();
			sesion.delete(entidad);
			tx.commit();
		} catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			sesion.close();
		}
	}

	@SuppressWarnings("unchecked")
	public T getByID(int id) {
		T entidad = null;

		try {
			iniciaOperacion();
			entidad = (T) sesion.get(getClase(), id);
		} finally {
			sesion.close();
		}
		return entidad;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		sesion = HibernateUtil.getSessionFactory().openSession();
		return (List<T>) sesion.createCriteria(getClase()).list();
	}

	private void manejaExcepcion(HibernateException he)
			throws HibernateException {
		tx.rollback();
		throw new HibernateException(
				"Ocurrió un error en la capa de acceso a datos", he);
	}

}
