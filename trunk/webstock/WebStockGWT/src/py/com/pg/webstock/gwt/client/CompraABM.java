package py.com.pg.webstock.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.pg.webstock.entities.Compra;
import py.com.pg.webstock.entities.DetalleCompra;
import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Proveedor;
import py.com.pg.webstock.gwt.client.access.CompraPA;
import py.com.pg.webstock.gwt.client.access.DetalleCompraPA;
import py.com.pg.webstock.gwt.client.access.ProductoPA;
import py.com.pg.webstock.gwt.client.access.ProveedorPA;
import py.com.pg.webstock.gwt.client.images.Recursos;
import py.com.pg.webstock.gwt.client.service.CompraService;
import py.com.pg.webstock.gwt.client.service.CompraServiceAsync;
import py.com.pg.webstock.gwt.client.service.DetalleCompraService;
import py.com.pg.webstock.gwt.client.service.DetalleCompraServiceAsync;
import py.com.pg.webstock.gwt.client.service.ProductoService;
import py.com.pg.webstock.gwt.client.service.ProductoServiceAsync;
import py.com.pg.webstock.gwt.client.service.ProveedorService;
import py.com.pg.webstock.gwt.client.service.ProveedorServiceAsync;

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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
import com.sencha.gxt.widget.core.client.form.TextField;
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
public class CompraABM implements IsWidget {

	/**
	 * ver documetnacion de la clase
	 * 
	 */
	public static final CompraPA pa = GWT.create(CompraPA.class);
	public static final ProveedorPA paProveedor = GWT.create(ProveedorPA.class);
	public static final DetalleCompraPA paDetalle = GWT
			.create(DetalleCompraPA.class);
	public static final ProductoPA paProducto = GWT.create(ProductoPA.class);

	// Aca le decimso al GWT qeu cree el servicio, y el hace la magia!
	// Atender qeu nos retorna un ASYNC pero le pasamos el no Asincronos
	CompraServiceAsync compraService = GWT.create(CompraService.class);
	ProveedorServiceAsync proveedorService = GWT.create(ProveedorService.class);
	DetalleCompraServiceAsync detalleService = GWT
			.create(DetalleCompraService.class);
	ProductoServiceAsync productoService = GWT.create(ProductoService.class);

	ListStore<DetalleCompra> detalles;
	ListStore<Compra> store;
	ListStore<Proveedor> proveedores;
	ListStore<Producto> productos;
	ArrayList<DetalleCompra> eliminados;

	Grid<Compra> g;
	Grid<DetalleCompra> gridDetalles;

	ToolBar bb;
	ToolBar bbDetalles;

	GridRowEditing<Compra> editing;
	GridRowEditing<DetalleCompra> editingDetalle;

	DetalleCompra antiguoDetalle;
	private ContentPanel panelDetalle;
	private static final DateTimeFormat fmt = DateTimeFormat
			.getFormat("EEEE, dd 'de' MMMM 'del' yyyy");

	@Override
	public Widget asWidget() {
		// Este contenedor es lo mas, muestra de arriba a abajo todo lo que le
		// agregamos
		VerticalLayoutContainer con = new VerticalLayoutContainer();
		// Aca se almacenan todos los valores que seran mostrados en el grid
		store = new ListStore<Compra>(pa.key());
		detalles = new ListStore<DetalleCompra>(paDetalle.key());
		// este es el modlo de las columnas, usamos el por defecto par ano armar
		// bardo
		ColumnModel<Compra> cm = new ColumnModel<Compra>(getColumnConfig());
		ColumnModel<DetalleCompra> cmd = new ColumnModel<DetalleCompra>(
				getDetalleColumnConfig());
		// Grilla en si, le pasamos el store donde estan los componentes y la
		// configuracion de las columnas
		g = new Grid<Compra>(store, cm);
		gridDetalles = new Grid<DetalleCompra>(detalles, cmd);
		//

		// esto le dice que la columna NOMBRE se exapndira cuando hya mucho
		// espacio
		g.getView().setAutoExpandColumn(proveedor);
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
		Document.get().setTitle("Compras - WebStock");
		ContentPanel panelCompra = new ContentPanel();
		panelCompra.setHeadingHtml("Compra");
		VerticalLayoutContainer compraVLC = new VerticalLayoutContainer();
		compraVLC.setBorders(true);
		compraVLC.add(bb);
		compraVLC.add(g);
		panelCompra.setWidget(compraVLC);
		con.add(panelCompra);

		panelDetalle = new ContentPanel();
		panelDetalle.setHeadingText("Detalle de la compra");
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

		productos = new ListStore<Producto>(paProducto.key());
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

	ColumnConfig<DetalleCompra, Producto> producto;
	ColumnConfig<DetalleCompra, Long> cantidad;
	ColumnConfig<DetalleCompra, Double> precio;

	public List<ColumnConfig<DetalleCompra, ?>> getDetalleColumnConfig() {
		producto = new ColumnConfig<DetalleCompra, Producto>(
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
		cantidad = new ColumnConfig<DetalleCompra, Long>(paDetalle.cantidad(),
				400, "Cantidad");
		precio = new ColumnConfig<DetalleCompra, Double>(paDetalle.precio(),
				400, "Precio");

		List<ColumnConfig<DetalleCompra, ?>> aRet = new ArrayList<ColumnConfig<DetalleCompra, ?>>(
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
		editing = new GridRowEditing<Compra>(g);

		editing.setClicksToEdit(ClicksToEdit.TWO);

		DateField dateField = new DateField(new DateTimePropertyEditor(fmt));
		dateField.setClearValueOnParseError(false);
		editing.addEditor(fecha, dateField);

		TextField nroFactura = new TextField();
		nroFactura.setEmptyText("Ingrese el numero de factura");
		nroFactura.setAllowBlank(false);
		editing.addEditor(factura, nroFactura);

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

		editingDetalle = new GridRowEditing<DetalleCompra>(gridDetalles);

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
				.addBeforeStartEditHandler(new BeforeStartEditHandler<DetalleCompra>() {
					@Override
					public void onBeforeStartEdit(
							BeforeStartEditEvent<DetalleCompra> event) {
						if (((DetalleCompra) detalles.get(event.getEditCell()
								.getRow())).getId() == 0) {
							antiguoDetalle = null;
						} else {
							antiguoDetalle = (DetalleCompra) detalles.get(event
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
										.getPrecioCompra());
							}
						});
					}
				});

		editingDetalle
				.addCompleteEditHandler(new CompleteEditHandler<DetalleCompra>() {
					@Override
					public void onCompleteEdit(
							CompleteEditEvent<DetalleCompra> event) {
						detalles.commitChanges();
						actualizarTotal(detalles.get(
								event.getEditCell().getRow()).getCompra());
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
				Compra nuevo = new Compra();
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
				DetalleCompra nuevo = new DetalleCompra();
				nuevo.setCompra(g.getSelectionModel().getSelectedItem());
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

	public void editadoCompleto(final Compra c) {
		if (c.getId() == 0)
			compraService.add(c, new GuardarCallBack(c));
		else
			compraService.update(c, new GuardarCallBack(c));
	}

	public void guardarDetalleClick() {
		editingDetalle.cancelEditing();

		Compra c = g.getSelectionModel().getSelectedItem();
		actualizarTotal(c);
		editadoCompleto(c);
		for (DetalleCompra dc : detalles.getAll()) {
			if (dc.getId() == 0)
				detalleService.add(dc, new GuardarDetalleCallBack(dc));
			else
				detalleService.update(dc, new GuardarDetalleCallBack(dc));
		}
		for (DetalleCompra dc : eliminados) {
			detalleService.remove(dc, new GuardarDetalleCallBack(dc));
		}
		eliminados = new ArrayList<DetalleCompra>();
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

	private void actualizarTotal(Compra c) {
		Compra compra = store.findModel(c);
		Double total = 0D;
		for (DetalleCompra detalle : detalles.getAll()) {
			total += detalle.getCantidad() * detalle.getPrecio();
		}
		compra.setTotal(total);
		store.update(compra);
	}

	private void eliminarDetalle(DetalleCompra selectedItem) {
		detalles.remove(selectedItem);
		actualizarTotal(selectedItem.getCompra());
		if (selectedItem.getId() != 0)
			eliminados.add(selectedItem);
	}

	public void cargarDetalle(Compra c) {
		panelDetalle.setEnabled(true);
		detalleService.getByCompra(c, new AsyncCallback<List<DetalleCompra>>() {
			@Override
			public void onSuccess(List<DetalleCompra> result) {
				detalles.clear();
				detalles.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Compras", "NO se pudo cargar detalles");
			}
		});

		productoService.getProductosByProveedor(c.getProveedor(),
				new AsyncCallback<List<Producto>>() {

					@Override
					public void onSuccess(List<Producto> result) {
						if (result.size() == 0)
							Info.display("Compras", "Proveedor sin productos");
						productos.clear();
						productos.addAll(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						Info.display("Compras", "no se pudo cargar productos");
					}
				});

		eliminados = new ArrayList<DetalleCompra>();
	}

	public class GuardarDetalleCallBack implements AsyncCallback<DetalleCompra> {
		DetalleCompra dc;

		public GuardarDetalleCallBack(DetalleCompra dc) {
			this.dc = dc;
		}

		@Override
		public void onFailure(Throwable caught) {
			Info.display("ERROR", "Imposible guardar detalle");
		}

		@Override
		public void onSuccess(DetalleCompra result) {
			dc.setId(result.getId());
		}
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
