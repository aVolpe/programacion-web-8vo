package py.com.pg.webstock.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.pg.webstock.entities.Compra;
import py.com.pg.webstock.entities.Proveedor;
import py.com.pg.webstock.gwt.client.access.CompraPA;
import py.com.pg.webstock.gwt.client.access.ProveedorPA;
import py.com.pg.webstock.gwt.client.images.Recursos;
import py.com.pg.webstock.gwt.client.service.CompraService;
import py.com.pg.webstock.gwt.client.service.CompraServiceAsync;
import py.com.pg.webstock.gwt.client.service.ProveedorService;
import py.com.pg.webstock.gwt.client.service.ProveedorServiceAsync;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * NO VER
 * 
 * @author Arturo
 * 
 */
public class CompraABM implements IsWidget {

	/**
	 * ver documetnacion de la clase
	 * 
	 */
	public static final CompraPA pa = GWT.create(CompraPA.class);
	public static final ProveedorPA paProveedor = GWT.create(ProveedorPA.class);

	// Aca le decimso al GWT qeu cree el servicio, y el hace la magia!
	// Atender qeu nos retorna un ASYNC pero le pasamos el no Asincronos
	CompraServiceAsync compraService = GWT.create(CompraService.class);
	ProveedorServiceAsync proveedorService = GWT.create(ProveedorService.class);

	ListStore<Compra> store;
	ListStore<Proveedor> proveedores;

	Grid<Compra> g;

	ToolBar bb;

	GridRowEditing<Compra> editing;

	private static final DateTimeFormat fmt = DateTimeFormat
			.getFormat("EEEE, dd 'de' MMMM 'del' yyyy");

	@Override
	public Widget asWidget() {
		// Este contenedor es lo mas, muestra de arriba a abajo todo lo que le
		// agregamos
		VerticalLayoutContainer con = new VerticalLayoutContainer();
		// Aca se almacenan todos los valores que seran mostrados en el grid
		store = new ListStore<Compra>(pa.key());
		// este es el modlo de las columnas, usamos el por defecto par ano armar
		// bardo
		ColumnModel<Compra> cm = new ColumnModel<Compra>(getColumnConfig());
		// Grilla en si, le pasamos el store donde estan los componentes y la
		// configuracion de las columnas
		g = new Grid<Compra>(store, cm);
		//

		// esto le dice que la columna NOMBRE se exapndira cuando hya mucho
		// espacio
		g.getView().setAutoExpandColumn(proveedor);

		cargarStores();
		configEditing();
		crearToolBar();

		// TODO ver como hacer dinamico
		g.setHeight(800);
		// agregamos el toolbar y el grid a la vista, lo agregara arriba
		Document.get().setTitle("Compras - WebStock");
		con.add(bb);
		con.add(g);
		return con;
	}

	private void cargarStores() {
		compraService.getEntidades(new AsyncCallback<List<Compra>>() {
			@Override
			public void onSuccess(List<Compra> result) {
				store.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Info.display("Compras", "no se pudo cargar Compras");
			}
		});

		proveedores = new ListStore<Proveedor>(paProveedor.key());
		proveedorService.getEntidades(new AsyncCallback<List<Proveedor>>() {
			@Override
			public void onSuccess(List<Proveedor> result) {
				proveedores.addAll(result);

			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Compras", "no se pudo cargar proveedores");
			}
		});

	}

	ColumnConfig<Compra, Proveedor> proveedor;
	ColumnConfig<Compra, Double> total;
	ColumnConfig<Compra, String> factura;
	ColumnConfig<Compra, Date> fecha;

	/**
	 * ColumnConfig es la configuracion de Columnas, debe ser una lista donde
	 * cada componente es una columna Cada columan recibe tres objetos, los
	 * cuales son: <br>
	 * 1. ValueProvider: que le dice que atributo de esa clase se mostrara en
	 * esa fila <br>
	 * 2. Width: int, ancho de la fila <br>
	 * 3. Header: String, cabecera <br>
	 * <b>Los pasos para mostrar una fila son los siguientes</b> <br>
	 * 1. Obtiene el registro del store con el PropertyAccess<br>
	 * 2. Renderiza con el cell de cada columna, con el metodo doRender, este
	 * metodo por defecto hace toString, pero se peude reescribir, vease la
	 * fecha que se sobreescrie <br>
	 * 3. Muestra
	 * 
	 * @return
	 */
	public List<ColumnConfig<Compra, ?>> getColumnConfig() {
		proveedor = new ColumnConfig<Compra, Proveedor>(pa.proveedor(), 200,
				"Proveedor");
		proveedor.setCell(new AbstractCell<Proveedor>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					Proveedor value, SafeHtmlBuilder sb) {
				if (value == null)
					return;
				else
					sb.appendEscaped(value.getNombre());
			}
		});

		fecha = new ColumnConfig<Compra, Date>(pa.fecha(), 250,
				"Fecha de compra");

		fecha.setCell(new DateCell(fmt));

		factura = new ColumnConfig<Compra, String>(pa.nroFactura(), 250,
				"Factura Numero");
		factura.setAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		total = new ColumnConfig<Compra, Double>(pa.total(), 250,
				"Total factura:");
		NumberFormat nf = NumberFormat.getFormat("000000.00 'Gs.'");
		total.setCell(new NumberCell<Double>(nf));
		List<ColumnConfig<Compra, ?>> aRet = new ArrayList<ColumnConfig<Compra, ?>>();
		aRet.add(proveedor);
		aRet.add(fecha);
		aRet.add(factura);
		aRet.add(total);
		return aRet;

	}

	/**
	 * Ahora hacemos a las celdas editables esto es re magico y lo mas
	 * interesante de esta clase del orte
	 */
	public void configEditing() {
		editing = new GridRowEditing<Compra>(g);

		editing.setClicksToEdit(ClicksToEdit.TWO);

		DateField dateField = new DateField(new DateTimePropertyEditor(fmt));
		dateField.setClearValueOnParseError(false);
		editing.addEditor(fecha, dateField);

		TextField nroFactura = new TextField();
		nroFactura.setEmptyText("Ingrese el numero de factura");
		nroFactura.setAllowBlank(false);

		SimpleComboBox<Proveedor> scb = new SimpleComboBox<Proveedor>(
				paProveedor.nameLabel());
		scb.setStore(proveedores);
		editing.addEditor(proveedor, scb);
		editing.addCompleteEditHandler(new CompleteEditHandler<Compra>() {
			@Override
			public void onCompleteEdit(CompleteEditEvent<Compra> event) {
				store.commitChanges();
				editadoCompleto(store.get(event.getEditCell().getRow()));
			}
		});
	}

	public void crearToolBar() {
		if (bb != null)
			return;
		bb = new ToolBar();
		bb.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		bb.setPack(BoxLayoutPack.CENTER);
		// TODO cambiar
		TextButton tbAdd = new TextButton("Agregar");
		tbAdd.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Compra nuevo = new Compra();
				store.add(nuevo);
				int index = store.indexOf(nuevo);
				editing.startEditing(new GridCell(index, 0));
			}
		});
		tbAdd.setIcon(Recursos.Util.getInstance().iconAdd());
		tbAdd.setIconAlign(IconAlign.RIGHT);

		TextButton tbRemove = new TextButton("Borrar");
		tbRemove.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (g.getSelectionModel().getSelectedItem() == null)
					return;
				else
					eliminar(g.getSelectionModel().getSelectedItem());
			}
		});
		tbRemove.setIcon(Recursos.Util.getInstance().iconDelete());
		bb.add(tbAdd);
		bb.add(tbRemove);

	}

	public void editadoCompleto(final Compra c) {
		// nuevo
		if (c.getId() == 0)
			compraService.add(c, new GuardarCallBack(c));
		else
			compraService.update(c, new GuardarCallBack(c));
	}

	public void eliminar(final Compra entidad) {
		compraService.remove(entidad, new AsyncCallback<Compra>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Compras", "Eliminacion fallada, reintente");
			}

			@Override
			public void onSuccess(Compra result) {
				Info.display("Compras", "Eliminacion correcta");
				store.remove(entidad);
				editing.cancelEditing();
			}
		});
	}

	public static class GuardarCallBack implements AsyncCallback<Compra> {
		Compra c;

		public GuardarCallBack(Compra c) {
			this.c = c;
		}

		@Override
		public void onSuccess(Compra result) {
			c.setId(result.getId());
			Info.display("Compras", "Guardado");
		}

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Info.display("Compras", "Imposible guardar, reintente");
		}
	}
}
