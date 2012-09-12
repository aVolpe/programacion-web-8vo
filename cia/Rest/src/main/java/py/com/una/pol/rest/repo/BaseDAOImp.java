package py.com.una.pol.rest.repo;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;

import py.com.una.pol.rest.domain.EntidadBase;

@SuppressWarnings("unchecked")
public class BaseDAOImp<T extends EntidadBase> implements BaseDAO<T> {

	/**
	 * See
	 * http://stackoverflow.com/questions/4699381/best-way-to-inject-hibernate
	 * -session-by-spring-3 TODO usar otra forma para probar!!
	 */
	// @PersistenceContext(name = "primary")
	// Session session;
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EntityManager em;

	public Session getSession() {

		return sessionFactory.openSession();
	}

	public Class<T> getClase() {
		return (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T getById(int id) {
		return em.find(getClase(), id);
	}

	@Override
	public List<T> getAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> q = cb.createQuery(getClase());
		Root<T> c = q.from(getClase());
		q.select(c);
		TypedQuery<T> query = em.createQuery(q);
		return query.getResultList();

		// return getSession().createCriteria(getClase()).list();
	}

	@Override
	public T guardar(T entidad) {
		return (T) em.merge(entidad);
	}

	@Override
	public T eliminar(int id) {
		T entidad = getById(id);
		em.remove(entidad);
		return entidad;
	}

	@Override
	public T eliminar(T entidad) {
		em.merge(entidad);
		em.remove(entidad);
		return entidad;
	}

	@Override
	public List<T> getByExample(T ejemplo) {
		return getSession().createCriteria(getClass())
				.add(Example.create(ejemplo)).list();
	}

}
