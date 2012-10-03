package py.com.pg.webstock.gwt.server.service;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Proveedor;
import py.com.pg.webstock.gwt.client.service.ProductoService;

public class ProductoServiceImpl extends BaseDAOServiceImpl<Producto> implements
		ProductoService {

	public List<Producto> getProductosByProveedor(Proveedor p) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Producto> q = cb.createQuery(Producto.class);
		Root<Producto> root = q.from(Producto.class);
		ParameterExpression<Proveedor> parameterProveedor = cb
				.parameter(Proveedor.class);
		q.select(root).where(
				cb.equal(root.get("proveedor"), parameterProveedor));
		TypedQuery<Producto> query = em.createQuery(q);
		query.setParameter(parameterProveedor, p);
		return filter(query.getResultList());
	}

	private static final long serialVersionUID = 2781113535320339578L;
}
