package py.com.pg.cmd;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import py.com.pg.webstock.ejb.ws.PagoDTO;
import py.com.pg.webstock.entities.Pago;
import py.com.webstock.ejb.services.PagoServiceWS;

public class WSCaller implements Caller {
	PagoServiceWS pagoServiceWS;

	public WSCaller(PagoServiceWS pagoServiceWS) {
		super();
		this.pagoServiceWS = pagoServiceWS;
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
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(p.getfecha());
			try {
				XMLGregorianCalendar fec = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(gcal);
				dto.setFecha(fec);
			} catch (DatatypeConfigurationException e) {
				System.out.println("Fecha imparseable (?)");
			}
			pagosDTO.add(dto);
		}
		for (PagoDTO dto : this.pagoServiceWS.agregarPagos(pagosDTO)) {
			aRet.add(dto.toString());
		}
		return aRet;
	}

}
