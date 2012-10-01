package py.com.pg.webstock.gwt.client.controller;

import py.com.pg.webstock.gwt.client.ClienteABM;
import py.com.pg.webstock.gwt.client.CompraABM;
import py.com.pg.webstock.gwt.client.NorthPanel;
import py.com.pg.webstock.gwt.client.PagoABM;
import py.com.pg.webstock.gwt.client.ProductoABM;
import py.com.pg.webstock.gwt.client.ProveedorABM;
import py.com.pg.webstock.gwt.client.SubirArchivo;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class WebStockController {

	private WebStockController() {
	}

	SimpleContainer centerSC;

	public SimpleContainer getCenterPanel() {
		if (centerSC == null) {
			centerSC = new SimpleContainer();
			centerSC.setResize(false);
		}
		return centerSC;
	}

	static WebStockController instancia = new WebStockController();

	public static WebStockController get() {
		return instancia;
	}

	public void iniciar() {
		final BorderLayoutContainer con = new BorderLayoutContainer();
		con.setBorders(false);

		NorthPanel west = new NorthPanel();

		BorderLayoutData westData = new BorderLayoutData(150);
		westData.setCollapsible(true);
		westData.setSplit(true);
		westData.setCollapseMini(true);
		westData.setMargins(new Margins(0, 5, 0, 5));

		// con.setNorthWidget(north, northData);
		con.setNorthWidget(west, westData);
		con.setCenterWidget(getCenterPanel(), new MarginData());
		con.setHeight((int) (Window.WINDOW_HEIGHT * 0.97f));
		con.setWidth((int) (Window.WINDOW_WIDTH * 0.97));
		SimpleContainer simple = new SimpleContainer();
		simple.add(con, new MarginData(10));
		RootPanel.get().clear();
		RootPanel.get().add(con);
		comprasClicked();
	}

	public void personasClicked() {
		getCenterPanel().clear();
		getCenterPanel().add(new ClienteABM());
	}

	public void proveedoresClicked() {
		getCenterPanel().clear();
		getCenterPanel().add(new ProveedorABM());
	}

	public void productosClicked() {
		getCenterPanel().clear();
		getCenterPanel().add(new ProductoABM());

	}

	public void comprasClicked() {
		getCenterPanel().clear();
		getCenterPanel().add(new CompraABM());

	}

	public void pagosClicked() {
		getCenterPanel().clear();
		getCenterPanel().add(new PagoABM());

	}

	public void spagosClicked() {
		Dialog d = new Dialog();
		d.add(new SubirArchivo());
		// una vez estube 45 minutos pensando por que no pasaba nada y era por
		// esto jaja
		d.show();
	}
}
