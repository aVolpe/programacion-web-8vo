package py.com.pg.webstock.gwt.client;

import py.com.pg.webstock.gwt.client.controller.WebStockController;
import py.com.pg.webstock.gwt.client.gxt.examples.Resources;
import py.com.pg.webstock.gwt.client.images.Recursos;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class WestPanel extends ContentPanel {

	WebStockController controller = WebStockController.get();

	public static final AccordionLayoutAppearance appearance = GWT
			.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);

	public WestPanel() {
		super(appearance);
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		setHeadingHtml("WebStock Opciones");
		// FlexTable table = new FlexTable();
		// add(table);
		add(vlc);
		TextButton tbPersona = new TextButton("Clientes");
		// tbPersona.setIconAlign(IconAlign.TOP);
		tbPersona.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.personasClicked();
			}
		});
		tbPersona.setIcon(Resources.IMAGES.add16());
		vlc.add(tbPersona, new VerticalLayoutData(1, -1));
		// table.setWidget(0, fila++, tbPersona);
		// table.getFlexCellFormatter().setRowSpan(0, 0, 3);

		TextButton tbProveedor = new TextButton("Proveedores");
		// tbProveedor.setIconAlign(IconAlign.TOP);
		tbProveedor.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.proveedoresClicked();
			}
		});
		tbProveedor.setIcon(Recursos.Util.getInstance().iconBanco());
		// table.setWidget(0, fila++, tbProveedor);
		vlc.add(tbProveedor, new VerticalLayoutData(1, -1));

		TextButton tbProducto = new TextButton("Productos");
		 tbProducto.setIcon(Resources.IMAGES.list_items());
		tbProducto.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.productosClicked();
			}
		});
		// tbProducto.setIcon(Recursos.Util.getInstance().iconListItems());
		// table.setWidget(0, fila++, tbProducto);
		vlc.add(tbProducto, new VerticalLayoutData(1, -1));

		TextButton tbCompra = new TextButton("Compras");
		tbCompra.setIcon(Resources.IMAGES.menu_show());
		tbCompra.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.comprasClicked();
			}
		});
		// table.setWidget(0, fila++, tbCompra);
		vlc.add(tbCompra, new VerticalLayoutData(1, -1));
		TextButton tbVenta = new TextButton("Ventas");
		tbVenta.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.ventasClicked();
			}
		});
		tbVenta.setIcon(Recursos.IMAGES.iconAdd());
		vlc.add(tbVenta, new VerticalLayoutData(1, -1));
		// table.setWidget(0, fila++, tbVenta);

		TextButton tbPago = new TextButton("Pagos");
		tbPago.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.pagosClicked();
			}
		});
		tbPago.setIcon(Recursos.IMAGES.iconSave());
		vlc.add(tbPago, new VerticalLayoutData(1, -1));
		// esto es asi, como ahora es una grilla, le dice, en la fila 0, columna
		// 4 poneme este boton
		// table.setWidget(0, fila++, tbPago);

		TextButton tbSPago = new TextButton("Subir Pagos");
		tbSPago.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.spagosClicked();
			}
		});
		tbSPago.setIcon(Resources.IMAGES.add16());
		vlc.add(tbSPago, new VerticalLayoutData(1, -1));
		// table.setWidget(0, fila++, tbSPago);
		// esto no tengo idea de que hace, pero si no poenes sale re feo, (es
		// luego feo)
		// cleanCells(table.getElement());
	}

}
