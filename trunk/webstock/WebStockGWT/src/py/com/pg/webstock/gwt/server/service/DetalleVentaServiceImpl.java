package py.com.pg.webstock.gwt.server.service;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.DetalleVenta;
import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Venta;
import py.com.pg.webstock.gwt.client.service.DetalleVentaService;

public class DetalleVentaServiceImpl extends BaseDAOServiceImpl<DetalleVenta>
		implements DetalleVentaService {

	@Override
	public DetalleVenta add(DetalleVenta entidad) {
		try {
			ut.begin();
			em.merge(entidad);
			Producto p = em.find(Producto.class, entidad.getProducto().getId());
			System.out.println("AAAAAAAAAAA" + entidad.getCantidad());
			p.setCantidad(p.getCantidad() - entidad.getCantidad());
			em.merge(p);
			
			Cliente c = em.find(Cliente.class, entidad.getVenta().getCliente().getId());
			c.setSaldo(c.getSaldo() - entidad.getCantidad()*entidad.getPrecio());
			em.merge(c);
			
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entidad;
	}
	public List<DetalleVenta> getByVenta(Venta venta) {
		Venta entidad = em.find(Venta.class, venta.getId());
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<DetalleVenta> q = cb.createQuery(DetalleVenta.class);
		Root<DetalleVenta> root = q.from(DetalleVenta.class);
		ParameterExpression<Venta> parameterVenta = cb.parameter(Venta.class);
		q.select(root).where(cb.equal(root.get("venta"), parameterVenta));
		TypedQuery<DetalleVenta> query = em.createQuery(q);
		query.setParameter(parameterVenta, entidad);
		return filter(query.getResultList());
	}

	private static final long serialVersionUID = -5691759970760339697L;
}
