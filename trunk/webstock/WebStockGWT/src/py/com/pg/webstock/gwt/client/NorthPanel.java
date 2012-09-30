package py.com.pg.webstock.gwt.client;

import py.com.pg.webstock.gwt.client.controller.WebStockController;
import py.com.pg.webstock.gwt.client.gxt.examples.Resources;
import py.com.pg.webstock.gwt.client.images.Recursos;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.FlexTable;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.widget.core.client.button.ButtonGroup;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class NorthPanel extends ButtonGroup {

	WebStockController controller = WebStockController.get();

	public NorthPanel() {
		this.setHeadingText("Bienvenido a WebStock");

		FlexTable table = new FlexTable();
		add(table);

		TextButton tbPersona = new TextButton("Clientes");
		tbPersona.setIconAlign(IconAlign.TOP);
		tbPersona.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.personasClicked();
			}
		});
		tbPersona.setIcon(Resources.IMAGES.add16());
		table.setWidget(0, 0, tbPersona);
		// table.getFlexCellFormatter().setRowSpan(0, 0, 3);

		TextButton tbProveedor = new TextButton("Proveedores");
		tbProveedor.setIconAlign(IconAlign.TOP);
		tbProveedor.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.proveedoresClicked();
			}
		});
		tbProveedor.setIcon(Recursos.Util.getInstance().iconBanco());
		table.setWidget(0, 1, tbProveedor);

		TextButton tbProducto = new TextButton("Productos");
		tbProducto.setIconAlign(IconAlign.TOP);
		tbProducto.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.productosClicked();
			}
		});
		tbProducto.setIcon(Recursos.Util.getInstance().iconListItems());
		table.setWidget(0, 2, tbProducto);

		TextButton tbCompra = new TextButton("Compras");
		tbCompra.setScale(ButtonScale.SMALL);
		tbCompra.setIconAlign(IconAlign.TOP);
		tbCompra.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.comprasClicked();
			}
		});
		table.setWidget(0, 3, tbCompra);
		cleanCells(table.getElement());
	}

	private void cleanCells(Element elem) {
		NodeList<Element> tds = elem.<XElement> cast().select("td");
		for (int i = 0; i < tds.getLength(); i++) {
			Element td = tds.getItem(i);

			if (!td.hasChildNodes() && td.getClassName().equals("")) {
				td.removeFromParent();
			}
		}
	}
}
