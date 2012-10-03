package py.com.pg.webstock.gwt.server.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import py.com.pg.webstock.entities.BaseEntity;
import py.com.pg.webstock.gwt.client.service.BaseDAOService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class BaseDAOServiceImpl<T extends BaseEntity> extends
		RemoteServiceServlet implements BaseDAOService<T> {

	private static final long serialVersionUID = -432284516858356051L;

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	// @PersistenceContext(unitName = "WebStockJPA")
	// Session session;

	@Resource
	UserTransaction ut;

	@Override
	public T get(int id) {
		return em.find(getClase(), id);
	}

	/**
	 * <code>Session</code> es de hibernate, si no podes poner como platform
	 * hibernate usa <code>EntityManager</code>
	 */
	@Override
	public List<T> getEntidades() {
		// Obtiene el criteriaBuilder, es un coso para hacer select sin escribir
		// select, es mas complicado, pero podes hacer generico
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		// le decimos que haga un select de nuestra clase! (esto quiere decir
		// que retornara una lista del tipo T (nuestraclase)) o select
		// NUESTRACLASE
		CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(getClase());
		// le dice que la raiz va a ser de nuestra calse (from NUESTRACLASE)
		Root<T> from = criteriaQuery.from(getClase());
		// aca creamos el select en si
		CriteriaQuery<T> select = criteriaQuery.select(from);
		// aca hacemos el query que va a la BD, no hace falta habia sido
		TypedQuery<T> typedQuery = em.createQuery(select);
		return filter(typedQuery.getResultList());
		// espera que busco como se hacia
		// Y TODo ESO ES LAS SIGUIENTE LINEA CON HIBERNATE, por eso la gente le
		// quiere a hibernate, continua nomas ah cierto tenia que crea cliente
		// return filter(session.createCriteria(getClase()).list());
	}

	@Override
	public T add(T entidad) {
		try {
			ut.begin();
			em.merge(entidad);
			ut.commit();
			System.out.println("Creada entidad con id " + entidad.getId() + "-"
					+ getClase().getSimpleName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entidad;
	}

	@Override
	public T remove(T entidad) {
		try {
			ut.begin();
			em.remove(em.merge(entidad));
			System.out.println("Eliminada entidad con id " + entidad.getId());
			ut.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entidad;
	}

	@Override
	public T remove(int id) {
		T entidad = get(id);
		try {
			ut.begin();
			em.persist(entidad);
			System.out.println("Eliminada entidad con id " + entidad.getId());
			ut.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entidad;
	}

	@Override
	public T update(T entidad) {
		try {
			ut.begin();
			em.merge(entidad);
			ut.commit();
			System.out.println("Modificando " + getClase().getSimpleName()
					+ " con id " + entidad.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entidad;
	}

	@Override
	public Long getCount() {
		// return (Long) session.createCriteria(getClase())
		// .setProjection(Projections.rowCount()).uniqueResult();
		return 0L;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getClase() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();

		return (Class<T>) type.getActualTypeArguments()[0];
	}

	public List<T> getByExample(T example) {
		// return filter(session.createCriteria(getClase())
		// .add(Example.create(example)).list());
		return null;
	}

	/**
	 * Ve para borrar las listas y cosas asi que no pueden ser enviadas al
	 * cliente pues no pueden ser serializadas, pues son lazys! tipo el examen
	 * 
	 * @param lista
	 * @return
	 */
	public List<T> filter(List<T> lista) {
		try {
			ArrayList<Field> aCerar = null;
			ArrayList<Field> aLimpiar = null;
			for (T entidad : lista) {
				if (aCerar == null) {
					aCerar = new ArrayList<Field>();
					aLimpiar = new ArrayList<Field>();
					Class<T> clase = getClase();
					// esto esta mal, no retorna lso fields de entidades
					// heredadas.
					for (Field f : clase.getDeclaredFields()) {
						if (f.getType() == List.class) {
							f.setAccessible(true);
							aCerar.add(f);
						}
						if (BaseEntity.class.isAssignableFrom(f.getType())) {
							f.setAccessible(true);
							aLimpiar.add(f);
						}
					}
				}
				for (Field f : aCerar) {
					f.set(entidad, null);
				}
				for (Field f : aLimpiar) {
					f.set(entidad, crearInstanciaLimpia(f.get(entidad)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public Object crearInstanciaLimpia(Object objeto) {
		if (objeto == null)
			return null;
		try {
			Class<?> clase = (Class<?>) objeto.getClass();
			for (Field f : clase.getDeclaredFields()) {
				// si es estatico omito
				if (Modifier.isStatic(f.getModifiers()))
					continue;
				// si es una lista cero
				if (f.getType() == List.class) {
					f.setAccessible(true);
					f.set(objeto, getEmptyList());
				}
				if (BaseEntity.class.isAssignableFrom(f.getType())) {
					f.setAccessible(true);
					f.set(objeto, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objeto;
	}

	public List getEmptyList() {
		return new ArrayList();
	}
}
