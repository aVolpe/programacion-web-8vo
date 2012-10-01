package py.com.pg.webstock.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import py.com.pg.webstock.entities.Compra;
import py.com.pg.webstock.entities.DetalleCompra;

public interface DetalleCompraServiceAsync extends
		BaseDAOServiceAsync<DetalleCompra> {

	void getByCompra(Compra compra, AsyncCallback<List<DetalleCompra>> callback);

}
