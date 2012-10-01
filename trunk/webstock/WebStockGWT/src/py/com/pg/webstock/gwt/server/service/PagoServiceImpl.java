package py.com.pg.webstock.gwt.server.service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.Pago;
import py.com.pg.webstock.gwt.client.service.PagoService;

public class PagoServiceImpl extends BaseDAOServiceImpl<Pago> implements
		PagoService {

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	@Resource
	UserTransaction ut;

	//este guardadra el pago y descontara al cliente 
	@Override
	public Pago add(Pago entidad) {// mejor hacemos todo de 0
		// iniciamos la transaccion
		try {
			ut.begin();
			// con esto guardamos el pago
			em.merge(entidad);
			//solo si fue exitoso el pago realizamos el descuento al cliente
			//retorna muy rapido, y todavia no guardo la entidad por eso no funcaba hace rato
			System.out.println(entidad.getEstado() + "");
			if (entidad.getEstado() != -1) {
				// obtenemos el cliente del pago
				Cliente c = entidad.getCliente();
				// obtenemos el saldo del cliente
				Double saldo = c.getSaldo();
				// sumamos el pago
				saldo = saldo + entidad.getMonto();
				// seteamos el saldo
				c.setSaldo(saldo);

				// con esto se guarda el cliente con saldo actualizado
				em.merge(c);
			}
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return entidad;
	}

	private static final long serialVersionUID = 2781113535320339578L;
}