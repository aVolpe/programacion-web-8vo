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

	private static final AccordionLayoutAppearance aparienciaBotonWestPanel = GWT
			.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);

	WebStockController controller = WebStockController.get();

	public WestPanel() {
		super(aparienciaBotonWestPanel);
		this.setAnimCollapse(false);
		this.setBodyStyleName("pad-text");
		this.setHeadingText("Menu");

		VerticalLayoutContainer con = new VerticalLayoutContainer();

		// BOTONES

		TextButton tbPersona = new TextButton("Personas");
		tbPersona.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.personasClicked();
			}
		});
		tbPersona.setIcon(Resources.IMAGES.add16());
		con.add(tbPersona, new VerticalLayoutData(1, -1));

		TextButton tbProveedor = new TextButton("Proveedores");
		tbProveedor.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				controller.personasClicked();
			}
		});
		tbProveedor.setIcon(Recursos.Util.getInstance().iconBanco());
		con.add(tbProveedor, new VerticalLayoutData(1, -1));

		add(con);
	}
}
