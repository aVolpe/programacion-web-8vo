package py.com.pg.cmd;

import java.util.List;

import py.com.pg.webstock.entities.Pago;

public interface Caller {

	public List<String> ejecutarPagos(Pago... pagos);
}
