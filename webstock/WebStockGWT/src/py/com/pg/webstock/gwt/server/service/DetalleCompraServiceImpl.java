package py.com.pg.webstock.gwt.server.service;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import py.com.pg.webstock.entities.Compra;
import py.com.pg.webstock.entities.DetalleCompra;
import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.gwt.client.service.DetalleCompraService;

public class DetalleCompraServiceImpl extends BaseDAOServiceImpl<DetalleCompra>
		implements DetalleCompraService {

	@Override
	public DetalleCompra add(DetalleCompra entidad) {
		try {
			ut.begin();
			em.merge(entidad);
			Producto p = em.find(Producto.class, entidad.getProducto().getId());
			System.out.println("AAAAAAAAAAA" + entidad.getCantidad());
			p.setCantidad(p.getCantidad() + entidad.getCantidad());
			em.merge(p);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entidad;
	}

	public List<DetalleCompra> getByCompra(Compra compra) {
		Compra entidad = em.find(Compra.class, compra.getId());
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<DetalleCompra> q = cb.createQuery(DetalleCompra.class);
		Root<DetalleCompra> root = q.from(DetalleCompra.class);
		ParameterExpression<Compra> parameterCompra = cb
				.parameter(Compra.class);
		q.select(root).where(cb.equal(root.get("compra"), parameterCompra));
		TypedQuery<DetalleCompra> query = em.createQuery(q);
		query.setParameter(parameterCompra, entidad);
		return filter(query.getResultList());
	}

	@Override
	public DetalleCompra update(DetalleCompra entidad) {
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

	private static final long serialVersionUID = -5691759970760339697L;
}
