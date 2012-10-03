package py.com.pg.webstock.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import py.com.pg.webstock.entities.DetalleVenta;
import py.com.pg.webstock.entities.Venta;

public interface DetalleVentaServiceAsync extends BaseDAOServiceAsync<DetalleVenta>{

	void getByVenta(Venta venta, AsyncCallback<List<DetalleVenta>> callback);


}
