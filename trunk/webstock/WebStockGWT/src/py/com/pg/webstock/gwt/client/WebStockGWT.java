package py.com.pg.webstock.gwt.client;

import py.com.pg.webstock.gwt.client.controller.WebStockController;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebStockGWT implements EntryPoint {
	public void onModuleLoad() {
		WebStockController.get().iniciar();
	}
}
