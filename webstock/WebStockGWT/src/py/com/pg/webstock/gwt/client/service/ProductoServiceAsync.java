package py.com.pg.webstock.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Proveedor;

public interface ProductoServiceAsync extends BaseDAOServiceAsync<Producto>{

	void getProductosByProveedor(Proveedor p,
			AsyncCallback<List<Producto>> callback);


}
