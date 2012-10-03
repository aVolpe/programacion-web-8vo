package py.com.pg.webstock.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.DetalleVenta;
import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Venta;
import py.com.pg.webstock.gwt.client.access.ClientePA;
import py.com.pg.webstock.gwt.client.access.DetalleVentaPA;
import py.com.pg.webstock.gwt.client.access.ProductoPA;
import py.com.pg.webstock.gwt.client.access.VentaPA;
import py.com.pg.webstock.gwt.client.images.Recursos;
import py.com.pg.webstock.gwt.client.service.ClienteService;
import py.com.pg.webstock.gwt.client.service.ClienteServiceAsync;
import py.com.pg.webstock.gwt.client.service.DetalleVentaService;
import py.com.pg.webstock.gwt.client.service.DetalleVentaServiceAsync;
import py.com.pg.webstock.gwt.client.service.ProductoService;
import py.com.pg.webstock.gwt.client.service.ProductoServiceAsync;
import py.com.pg.webstock.gwt.client.service.VentaService;
import py.com.pg.webstock.gwt.client.service.VentaServiceAsync;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.cell.core.client.form.SpinnerFieldCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent.CellClickHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.DoublePropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.LongPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * NO VER
 * 
 * @author Arturo
 * 
 */
public class VentaABM implements IsWidget {

	/**
	 * ver documetnacion de la clase
	 * 
	 */
	public static final VentaPA pa = GWT.create(VentaPA.class);
	public static final ClientePA paCliente = GWT.create(ClientePA.class);
	public static final DetalleVentaPA paDetalle = GWT
			.create(DetalleVentaPA.class);
	public static final ProductoPA paProducto = GWT.create(ProductoPA.class);

	// Aca le decimso al GWT qeu cree el servicio, y el hace la magia!
	// Atender qeu nos retorna un ASYNC pero le pasamos el no Asincronos
	VentaServiceAsync ventaService = GWT.create(VentaService.class);
	ClienteServiceAsync clienteService = GWT.create(ClienteService.class);
	DetalleVentaServiceAsync detalleService = GWT
			.create(DetalleVentaService.class);
	ProductoServiceAsync productoService = GWT.create(ProductoService.class);

	ListStore<DetalleVenta> detalles;
	ListStore<Venta> store;
	ListStore<Cliente> Clientees;
	ListStore<Producto> productos;
	ArrayList<DetalleVenta> eliminados;

	Grid<Venta> g;
	Grid<DetalleVenta> gridDetalles;

	ToolBar bb;
	ToolBar bbDetalles;

	GridRowEditing<Venta> editing;
	GridRowEditing<DetalleVenta> editingDetalle;

	DetalleVenta antiguoDetalle;
	private ContentPanel panelDetalle;
	private static final DateTimeFormat fmt = DateTimeFormat
			.getFormat("EEEE, dd 'de' MMMM 'del' yyyy");

	@Override
	public Widget asWidget() {
		// Este contenedor es lo mas, muestra de arriba a abajo todo lo que le
		// agregamos
		VerticalLayoutContainer con = new VerticalLayoutContainer();
		// Aca se almacenan todos los valores que seran mostrados en el grid
		store = new ListStore<Venta>(pa.key());
		detalles = new ListStore<DetalleVenta>(paDetalle.key());
		// este es el modlo de las columnas, usamos el por defecto par ano armar
		// bardo
		ColumnModel<Venta> cm = new ColumnModel<Venta>(getColumnConfig());
		ColumnModel<DetalleVenta> cmd = new ColumnModel<DetalleVenta>(
				getDetalleColumnConfig());
		// Grilla en si, le pasamos el store donde estan los componentes y la
		// configuracion de las columnas
		g = new Grid<Venta>(store, cm);
		gridDetalles = new Grid<DetalleVenta>(detalles, cmd);
		//

		// esto le dice que la columna NOMBRE se exapndira cuando hya mucho
		// espacio
		g.getView().setAutoExpandColumn(cliente);
		gridDetalles.getView().setAutoExpandColumn(producto);

		g.addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				cargarDetalle(store.get(event.getRowIndex()));
			}
		});
		cargarStores();
		configEditing();
		crearToolBar();

		g.setHeight(200);
		gridDetalles.setHeight(200);
		// agregamos el toolbar y el grid a la vista, lo agregara arriba
		Document.get().setTitle("Ventas - WebStock");
		ContentPanel panelVenta = new ContentPanel();
		panelVenta.setHeadingHtml("Venta");
		VerticalLayoutContainer VentaVLC = new VerticalLayoutContainer();
		VentaVLC.setBorders(true);
		VentaVLC.add(bb);
		VentaVLC.add(g);
		panelVenta.setWidget(VentaVLC);
		con.add(panelVenta);

		panelDetalle = new ContentPanel();
		panelDetalle.setHeadingText("Detalle de la Venta");
		VerticalLayoutContainer containerDetalle = new VerticalLayoutContainer();
		containerDetalle.setBorders(true);
		containerDetalle.add(bbDetalles);
		containerDetalle.add(gridDetalles);

		panelDetalle.setWidget(containerDetalle);
		panelDetalle.setEnabled(false);
		con.add(panelDetalle);
		return con;
	}

	private void cargarStores() {
		ventaService.getEntidades(new AsyncCallback<List<Venta>>() {
			@Override
			public void onSuccess(List<Venta> result) {
				store.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Info.display("Ventas", "no se pudo cargar Ventas");
			}
		});

		Clientees = new ListStore<Cliente>(paCliente.key());
		clienteService.getEntidades(new AsyncCallback<List<Cliente>>() {
			@Override
			public void onSuccess(List<Cliente> result) {
				Clientees.addAll(result);

			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Ventas", "no se pudo cargar Clientees");
			}
		});

		productos = new ListStore<Producto>(paProducto.key());
	}

	ColumnConfig<Venta, Cliente> cliente;
	ColumnConfig<Venta, Double> total;
	ColumnConfig<Venta, Date> fecha;

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
	public List<ColumnConfig<Venta, ?>> getColumnConfig() {
		cliente = new ColumnConfig<Venta, Cliente>(pa.cliente(), 500, "Cliente");
		cliente.setCell(new AbstractCell<Cliente>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					Cliente value, SafeHtmlBuilder sb) {
				if (value == null)
					return;
				else
					sb.appendEscaped(value.getNombre());
			}
		});

		fecha = new ColumnConfig<Venta, Date>(pa.fecha(), 400,
				"Fecha de Compra");

		fecha.setCell(new DateCell(fmt));

		total = new ColumnConfig<Venta, Double>(pa.total(), 400,
				"Total factura:");
		NumberFormat nf = NumberFormat.getFormat("000000.00 'Gs.'");
		total.setCell(new NumberCell<Double>(nf));
		List<ColumnConfig<Venta, ?>> aRet = new ArrayList<ColumnConfig<Venta, ?>>();
		aRet.add(cliente);
		aRet.add(fecha);
		aRet.add(total);
		return aRet;

	}

	ColumnConfig<DetalleVenta, Producto> producto;
	ColumnConfig<DetalleVenta, Long> cantidad;
	ColumnConfig<DetalleVenta, Double> precio;

	public List<ColumnConfig<DetalleVenta, ?>> getDetalleColumnConfig() {
		producto = new ColumnConfig<DetalleVenta, Producto>(
				paDetalle.producto(), 600, "Producto");
		producto.setCell(new AbstractCell<Producto>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					Producto value, SafeHtmlBuilder sb) {
				if (value == null)
					return;
				sb.appendEscaped(value.getNombre());
			}
		});
		cantidad = new ColumnConfig<DetalleVenta, Long>(paDetalle.cantidad(),
				400, "Cantidad");
		precio = new ColumnConfig<DetalleVenta, Double>(paDetalle.precio(),
				400, "Precio");

		List<ColumnConfig<DetalleVenta, ?>> aRet = new ArrayList<ColumnConfig<DetalleVenta, ?>>(
				3);
		aRet.add(producto);
		aRet.add(cantidad);
		aRet.add(precio);
		return aRet;
	}

	/**
	 * Ahora hacemos a las celdas editables esto es re magico y lo mas
	 * interesante de esta clase del orte
	 */
	public void configEditing() {
		editing = new GridRowEditing<Venta>(g);

		editing.setClicksToEdit(ClicksToEdit.TWO);

		DateField dateField = new DateField(new DateTimePropertyEditor(fmt));
		dateField.setClearValueOnParseError(false);
		editing.addEditor(fecha, dateField);

		SimpleComboBox<Cliente> scb = new SimpleComboBox<Cliente>(
				paCliente.nameLabel());
		scb.setStore(Clientees);
		editing.addEditor(cliente, scb);
		editing.addCompleteEditHandler(new CompleteEditHandler<Venta>() {
			@Override
			public void onCompleteEdit(CompleteEditEvent<Venta> event) {
				store.commitChanges();
				editadoCompleto(store.get(event.getEditCell().getRow()));
			}
		});

		editingDetalle = new GridRowEditing<DetalleVenta>(gridDetalles);

		SimpleComboBox<Producto> scbProducto = new SimpleComboBox<Producto>(
				paProducto.nameLabel());
		scbProducto.setStore(productos);
		scbProducto.setEmptyText("Ingrese el producto");
		editingDetalle.addEditor(producto, scbProducto);

		SpinnerFieldCell<Long> sfcCantidad = new SpinnerFieldCell<Long>(
				new LongPropertyEditor());
		NumberField<Long> nfCantidad = new NumberField<Long>(sfcCantidad,
				sfcCantidad.getPropertyEditor());
		nfCantidad.setEmptyText("Ingrese la cantidad");
		editingDetalle.addEditor(cantidad, nfCantidad);

		SpinnerFieldCell<Double> sfc = new SpinnerFieldCell<Double>(
				new DoublePropertyEditor());

		NumberField<Double> nfPrecio = new NumberField<Double>(sfc,
				sfc.getPropertyEditor());
		nfPrecio.setEditable(false);
		nfPrecio.setEmptyText("Seleccione un producto");
		editingDetalle.addEditor(precio, nfPrecio);

		editingDetalle
				.addBeforeStartEditHandler(new BeforeStartEditHandler<DetalleVenta>() {
					@Override
					public void onBeforeStartEdit(
							BeforeStartEditEvent<DetalleVenta> event) {
						if (((DetalleVenta) detalles.get(event.getEditCell()
								.getRow())).getId() == 0) {
							antiguoDetalle = null;
						} else {
							antiguoDetalle = (DetalleVenta) detalles.get(event
									.getEditCell().getRow());
						}
						Field<Producto> f = editingDetalle.getEditor(producto);
						Field<Double> fPrecio = editingDetalle
								.getEditor(precio);
						final NumberField<Double> nfPrecio = (NumberField<Double>) fPrecio;
						final SimpleComboBox<Producto> scb = (SimpleComboBox<Producto>) f;
						scb.addSelectionHandler(new SelectionHandler<Producto>() {
							@Override
							public void onSelection(
									SelectionEvent<Producto> event) {
								nfPrecio.setValue(event.getSelectedItem()
										.getPrecioVenta());
							}
						});
					}
				});

		editingDetalle
				.addCompleteEditHandler(new CompleteEditHandler<DetalleVenta>() {
					@Override
					public void onCompleteEdit(
							CompleteEditEvent<DetalleVenta> event) {
						detalles.commitChanges();
						actualizarTotal(detalles.get(
								event.getEditCell().getRow()).getVenta());
					}
				});
	}

	public void crearToolBar() {
		if (bb != null)
			return;
		bb = new ToolBar();
		bb.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		bb.setPack(BoxLayoutPack.CENTER);
		TextButton tbAdd = new TextButton("Agregar");
		tbAdd.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Venta nuevo = new Venta();
				nuevo.setTotal(0D);
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

		bbDetalles = new ToolBar();
		bbDetalles.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		bbDetalles.setPack(BoxLayoutPack.CENTER);
		TextButton tbAddDetalle = new TextButton("Agregar detalle");
		tbAddDetalle.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				DetalleVenta nuevo = new DetalleVenta();
				nuevo.setVenta(g.getSelectionModel().getSelectedItem());
				detalles.add(nuevo);
				int index = detalles.indexOf(nuevo);
				editingDetalle.startEditing(new GridCell(index, 0));
			}
		});
		tbAddDetalle.setIcon(Recursos.Util.getInstance().iconAdd());
		tbAddDetalle.setIconAlign(IconAlign.RIGHT);

		TextButton tbRemoveDetalle = new TextButton("Borrar detalle");
		tbRemoveDetalle.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (gridDetalles.getSelectionModel().getSelectedItem() == null)
					return;
				else
					eliminarDetalle(gridDetalles.getSelectionModel()
							.getSelectedItem());
			}

		});
		tbRemoveDetalle.setIcon(Recursos.Util.getInstance().iconDelete());

		TextButton tbGuardar = new TextButton("Guardar Cambios");
		tbGuardar.setIcon(Recursos.IMAGES.iconOpen());
		tbGuardar.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				guardarDetalleClick();
			}
		});
		bbDetalles.add(tbAddDetalle);
		bbDetalles.add(tbRemoveDetalle);
		bbDetalles.add(new SeparatorToolItem());
		bbDetalles.add(tbGuardar);
	}

	public void editadoCompleto(final Venta c) {
		if (c.getId() == 0)
			ventaService.add(c, new GuardarCallBack(c));
		else
			ventaService.update(c, new GuardarCallBack(c));
	}

	public void guardarDetalleClick() {
		editingDetalle.cancelEditing();

		Venta c = g.getSelectionModel().getSelectedItem();
		actualizarTotal(c);
		editadoCompleto(c);
		for (DetalleVenta dc : detalles.getAll()) {
			if (dc.getId() == 0)
				detalleService.add(dc, new GuardarDetalleCallBack(dc));
			else
				detalleService.update(dc, new GuardarDetalleCallBack(dc));
		}
		for (DetalleVenta dc : eliminados) {
			detalleService.remove(dc, new GuardarDetalleCallBack(dc));
		}
		eliminados = new ArrayList<DetalleVenta>();
	}

	public void eliminar(final Venta entidad) {
		ventaService.remove(entidad, new AsyncCallback<Venta>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Ventas", "Eliminacion fallada, reintente");
			}

			@Override
			public void onSuccess(Venta result) {
				Info.display("Ventas", "Eliminacion correcta");
				store.remove(entidad);
				editing.cancelEditing();
			}
		});
	}

	private void actualizarTotal(Venta c) {
		Venta Venta = store.findModel(c);
		Double total = 0D;
		for (DetalleVenta detalle : detalles.getAll()) {
			total += detalle.getCantidad() * detalle.getPrecio();
		}
		Venta.setTotal(total);
		store.update(Venta);
	}

	private void eliminarDetalle(DetalleVenta selectedItem) {
		detalles.remove(selectedItem);
		actualizarTotal(selectedItem.getVenta());
		if (selectedItem.getId() != 0)
			eliminados.add(selectedItem);
	}

	public void cargarDetalle(Venta c) {
		panelDetalle.setEnabled(true);
		detalleService.getByVenta(c, new AsyncCallback<List<DetalleVenta>>() {
			@Override
			public void onSuccess(List<DetalleVenta> result) {
				detalles.clear();
				detalles.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Ventas", "NO se pudo cargar detalles");
			}
		});

		productoService.getEntidades(new AsyncCallback<List<Producto>>() {
			@Override
			public void onSuccess(List<Producto> result) {
				if (result.size() == 0)
					Info.display("Ventas", "Cliente sin productos");
				productos.clear();
				productos.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Ventas", "no se pudo cargar productos");
			}
		});

		eliminados = new ArrayList<DetalleVenta>();
	}

	public class GuardarDetalleCallBack implements AsyncCallback<DetalleVenta> {
		DetalleVenta dc;

		public GuardarDetalleCallBack(DetalleVenta dc) {
			this.dc = dc;
		}

		@Override
		public void onFailure(Throwable caught) {
			Info.display("ERROR", "Imposible guardar detalle");
		}

		@Override
		public void onSuccess(DetalleVenta result) {
			dc.setId(result.getId());
		}
	}

	public class GuardarCallBack implements AsyncCallback<Venta> {
		Venta c;

		public GuardarCallBack(Venta c) {
			this.c = c;
		}

		@Override
		public void onSuccess(Venta result) {
			if (result.getId() == 0) {
				store.clear();
				cargarStores();
			}
			c.setId(result.getId());
			Info.display("Ventas", "Guardado");
		}

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Info.display("Ventas", "Imposible guardar, reintente");
		}
	}
}
