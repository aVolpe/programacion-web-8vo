package py.com.pg.webstock.gwt.client;

import java.util.ArrayList;
import java.util.List;

import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Proveedor;
import py.com.pg.webstock.gwt.client.access.ProductoPA;
import py.com.pg.webstock.gwt.client.access.ProveedorPA;
import py.com.pg.webstock.gwt.client.images.Recursos;
import py.com.pg.webstock.gwt.client.service.ProductoService;
import py.com.pg.webstock.gwt.client.service.ProductoServiceAsync;
import py.com.pg.webstock.gwt.client.service.ProveedorService;
import py.com.pg.webstock.gwt.client.service.ProveedorServiceAsync;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.SpinnerFieldCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.DoublePropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.LongPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Copia sucia de ClienteABM no mirar!! mirar la otra
 * 
 * @author Arturo
 * 
 */
public class ProductoABM implements IsWidget {

	/**
	 * ver documetnacion de la clase
	 * 
	 */
	public static final ProductoPA pa = GWT.create(ProductoPA.class);
	public static final ProveedorPA paProveedor = GWT.create(ProveedorPA.class);

	// Aca le decimso al GWT qeu cree el servicio, y el hace la magia!
	// Atender qeu nos retorna un ASYNC pero le pasamos el no Asincronos
	ProductoServiceAsync productoService = GWT.create(ProductoService.class);
	ProveedorServiceAsync proveedorService = GWT.create(ProveedorService.class);

	ListStore<Producto> store;
	ListStore<Proveedor> storeProveedores;

	Grid<Producto> g;

	ToolBar bb;

	GridRowEditing<Producto> editing;

	@Override
	public Widget asWidget() {
		// Este contenedor es lo mas, muestra de arriba a abajo todo lo que le
		// agregamos
		VerticalLayoutContainer con = new VerticalLayoutContainer();
		// Aca se almacenan todos los valores que seran mostrados en el grid
		store = new ListStore<Producto>(pa.key());
		storeProveedores = new ListStore<Proveedor>(paProveedor.key());
		// este es el modlo de las columnas, usamos el por defecto par ano armar
		// bardo
		ColumnModel<Producto> cm = new ColumnModel<Producto>(getColumnConfig());
		// Grilla en si, le pasamos el store donde estan los componentes y la
		// configuracion de las columnas
		g = new Grid<Producto>(store, cm);
		//

		// esto le dice que la columna NOMBRE se exapndira cuando hya mucho
		// espacio
		g.getView().setAutoExpandColumn(nombre);

		cargarStores();
		configEditing();
		crearToolBar();

		// TODO ver como hacer dinamico
		g.setHeight(800);
		// agregamos el toolbar y el grid a la vista, lo agregara arriba
		con.add(bb);
		con.add(g);
		Document.get().setTitle("Productos - WebStock");
		return con;
	}

	private void cargarStores() {
		productoService.getEntidades(new AsyncCallback<List<Producto>>() {
			@Override
			public void onSuccess(List<Producto> result) {
				store.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Productos", "no se pudo cargar productos");
			}
		});
		proveedorService.getEntidades(new AsyncCallback<List<Proveedor>>() {
			@Override
			public void onSuccess(List<Proveedor> result) {
				storeProveedores.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Productos", "no se pudo cargar productos");
			}
		});

	}

	ColumnConfig<Producto, String> nombre;
	ColumnConfig<Producto, Long> cantidad;
	ColumnConfig<Producto, Proveedor> proveedor;
	ColumnConfig<Producto, Double> precioCompra;
	ColumnConfig<Producto, Double> precioVenta;

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
	public List<ColumnConfig<Producto, ?>> getColumnConfig() {
		nombre = new ColumnConfig<Producto, String>(pa.nombre(), 150, "Nombre");
		cantidad = new ColumnConfig<Producto, Long>(pa.cantidad(), 150,
				"Cantidad");
		proveedor = new ColumnConfig<Producto, Proveedor>(pa.proveedor(), 150,
				"Proveedor");
		proveedor.setCell(new AbstractCell<Proveedor>() {
			@Override
			public void render(Context context, Proveedor value,
					SafeHtmlBuilder sb) {
				if (value == null)
					return;
				else
					sb.appendEscaped(value.getNombre());
			}
		});
		precioCompra = new ColumnConfig<Producto, Double>(pa.precioCompra(),
				250, "Precio de compra");
		precioVenta = new ColumnConfig<Producto, Double>(pa.precioVenta(), 250,
				"Precio de venta");

		List<ColumnConfig<Producto, ?>> aRet = new ArrayList<ColumnConfig<Producto, ?>>();
		aRet.add(nombre);
		aRet.add(cantidad);
		aRet.add(proveedor);
		aRet.add(precioCompra);
		aRet.add(precioVenta);
		return aRet;

	}

	/**
	 * Ahora hacemos a las celdas editables esto es re magico y lo mas
	 * interesante de esta clase del orte
	 */
	public void configEditing() {
		editing = new GridRowEditing<Producto>(g);

		TextField tfNombre = new TextField();
		tfNombre.setEmptyText("Ingrese el nombre");
		editing.addEditor(nombre, tfNombre);

		SpinnerFieldCell<Long> sfc = new SpinnerFieldCell<Long>(
				new LongPropertyEditor());

		NumberField<Long> nfCantidad = new NumberField<Long>(sfc,
				sfc.getPropertyEditor());

		nfCantidad.setEmptyText("Ingrese la cantidad");

		editing.addEditor(cantidad, nfCantidad);

		SpinnerFieldCell<Double> sfcPrecioCompra = new SpinnerFieldCell<Double>(
				new DoublePropertyEditor());

		NumberField<Double> nfPrecioCompra = new NumberField<Double>(
				sfcPrecioCompra, sfcPrecioCompra.getPropertyEditor());

		editing.addEditor(precioCompra, nfPrecioCompra);

		SpinnerFieldCell<Double> sfcPrecioVenta = new SpinnerFieldCell<Double>(
				new DoublePropertyEditor());

		NumberField<Double> nfPrecioVenta = new NumberField<Double>(
				sfcPrecioVenta, sfcPrecioVenta.getPropertyEditor());

		editing.addEditor(precioVenta, nfPrecioVenta);

		ComboBox<Proveedor> comboProveedor = new ComboBox<Proveedor>(
				storeProveedores, paProveedor.nameLabel());
		comboProveedor.setTriggerAction(TriggerAction.ALL);
		comboProveedor.setEditable(false);

		editing.addEditor(proveedor, comboProveedor);

		editing.addCompleteEditHandler(new CompleteEditHandler<Producto>() {

			@Override
			public void onCompleteEdit(CompleteEditEvent<Producto> event) {
				// guarda todos lso cambios en el store, asi podemos obtener el
				// cilnete cambiado
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
				Producto nuevo = new Producto();
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

	public void editadoCompleto(final Producto c) {
		// nuevo
		if (c.getId() == 0)
			productoService.add(c, new GuardarCallBack(c));
		else
			productoService.update(c, new GuardarCallBack(c));
	}

	public void eliminar(final Producto entidad) {
		productoService.remove(entidad, new AsyncCallback<Producto>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Productos", "Eliminacion fallada, reintente");
			}

			@Override
			public void onSuccess(Producto result) {
				Info.display("Productos", "Eliminacion correcta");
				store.remove(entidad);
				editing.cancelEditing();
			}
		});
	}

	public static class GuardarCallBack implements AsyncCallback<Producto> {
		Producto c;

		public GuardarCallBack(Producto c) {
			this.c = c;
		}

		@Override
		public void onSuccess(Producto result) {
			c.setId(result.getId());
			Info.display("Productos", "Guardado");
		}

		@Override
		public void onFailure(Throwable caught) {
			Info.display("Productos", "Imposible guardar, reintente");
		}
	}
}
