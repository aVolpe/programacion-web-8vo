package py.com.pg.cmd;

import java.util.ArrayList;
import java.util.List;

import py.com.pg.webstock.dto.PagoDTO;
import py.com.pg.webstock.entities.Pago;
import py.com.webstock.ejb.services.local.PagoServiceRemote;

public class EJBCaller implements Caller {

	PagoServiceRemote service;

	public EJBCaller(PagoServiceRemote service) {
		super();
		this.service = service;
	}

	@Override
	public List<String> ejecutarPagos(Pago... pagos) {
		List<PagoDTO> pagosDTO = new ArrayList<PagoDTO>(pagos.length);
		List<String> aRet = new ArrayList<String>(pagos.length);
		for (Pago p : pagos) {
			PagoDTO dto = new PagoDTO();
			dto.setCodPago(p.getCodPago());
			dto.setIdCliente(p.getCliente().getId());
			dto.setMonto(p.getMonto());
			dto.setFecha(p.getfecha());
			pagosDTO.add(dto);
		}
		for (PagoDTO dto : this.service.agregarPagos(pagosDTO
				.toArray(new PagoDTO[pagos.length]))) {
			aRet.add(dto.toString());
		}
		return aRet;
	}

}
