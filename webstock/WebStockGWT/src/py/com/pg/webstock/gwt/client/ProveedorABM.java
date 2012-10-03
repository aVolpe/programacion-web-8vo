package py.com.pg.webstock.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.pg.webstock.entities.Proveedor;
import py.com.pg.webstock.gwt.client.access.ProveedorPA;
import py.com.pg.webstock.gwt.client.images.Recursos;
import py.com.pg.webstock.gwt.client.service.ProveedorService;
import py.com.pg.webstock.gwt.client.service.ProveedorServiceAsync;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
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
public class ProveedorABM implements IsWidget {

	/**
	 * ver documetnacion de la clase
	 * 
	 */
	public static final ProveedorPA pa = GWT.create(ProveedorPA.class);

	// Aca le decimso al GWT qeu cree el servicio, y el hace la magia!
	// Atender qeu nos retorna un ASYNC pero le pasamos el no Asincronos
	ProveedorServiceAsync clienteService = GWT.create(ProveedorService.class);

	ListStore<Proveedor> store;

	Grid<Proveedor> g;

	ToolBar bb;

	GridRowEditing<Proveedor> editing;

	private static final DateTimeFormat fmt = DateTimeFormat
			.getFormat("EEEE, dd 'de' MMMM 'del' yyyy");

	@Override
	public Widget asWidget() {
		// Este contenedor es lo mas, muestra de arriba a abajo todo lo que le
		// agregamos
		VerticalLayoutContainer con = new VerticalLayoutContainer();
		// Aca se almacenan todos los valores que seran mostrados en el grid
		store = new ListStore<Proveedor>(pa.key());
		// este es el modlo de las columnas, usamos el por defecto par ano armar
		// bardo
		ColumnModel<Proveedor> cm = new ColumnModel<Proveedor>(
				getColumnConfig());
		// Grilla en si, le pasamos el store donde estan los componentes y la
		// configuracion de las columnas
		g = new Grid<Proveedor>(store, cm);
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
		Document.get().setTitle("Proveedores - WebStock");
		return con;
	}

	private void cargarStores() {
		clienteService.getEntidades(new AsyncCallback<List<Proveedor>>() {
			@Override
			public void onSuccess(List<Proveedor> result) {
				store.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Proveedors", "no se pudo cargar clientes");
			}
		});

	}

	ColumnConfig<Proveedor, String> nombre;
	ColumnConfig<Proveedor, String> ruc;
	ColumnConfig<Proveedor, String> telefono;
	ColumnConfig<Proveedor, Date> fecha;

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
	public List<ColumnConfig<Proveedor, ?>> getColumnConfig() {
		nombre = new ColumnConfig<Proveedor, String>(pa.nombre(), 400, "Nombre");
		ruc = new ColumnConfig<Proveedor, String>(pa.ruc(), 250, "RUC");
		telefono = new ColumnConfig<Proveedor, String>(pa.telefono(), 250,
				"Telefono");
		fecha = new ColumnConfig<Proveedor, Date>(pa.fechaAlta(), 400,
				"Fecha de alta");

		fecha.setCell(new DateCell(fmt));

		List<ColumnConfig<Proveedor, ?>> aRet = new ArrayList<ColumnConfig<Proveedor, ?>>();
		aRet.add(nombre);
		aRet.add(ruc);
		aRet.add(telefono);
		aRet.add(fecha);
		return aRet;

	}

	/**
	 * Ahora hacemos a las celdas editables esto es re magico y lo mas
	 * interesante de esta clase del orte
	 */
	public void configEditing() {
		editing = new GridRowEditing<Proveedor>(g);

		TextField tfNombre = new TextField();
		tfNombre.setEmptyText("Ingrese el nombre");
		editing.addEditor(nombre, tfNombre);

		TextField tfRuc = new TextField();
		tfRuc.setEmptyText("Ingrese el RUC");
		editing.addEditor(ruc, tfRuc);

		TextField tfTelefono = new TextField();
		tfTelefono.setEmptyText("Ingrese el Telefono");
		editing.addEditor(telefono, tfTelefono);

		DateField dateField = new DateField(new DateTimePropertyEditor(fmt));
		dateField.setClearValueOnParseError(false);
		dateField.setEmptyText("Seleccione la fecha");
		editing.addEditor(fecha, dateField);

		editing.addCompleteEditHandler(new CompleteEditHandler<Proveedor>() {

			@Override
			public void onCompleteEdit(CompleteEditEvent<Proveedor> event) {
				// guarda todos lso cambios en el store, asi podemos obtener el
				// cilnete cambiado
				store.commitChanges();
				/**
				 * esta linea me tomo 3 meses construir, lo que hace es: <Br>
				 * 1. Obtiene la fila que se esta editando <br>
				 * 2. La busca en el store, el store se actualiza
				 * automaticamente y su orden es el orden donde se muestra
				 */
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
				Proveedor nuevo = new Proveedor();
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

	public void editadoCompleto(final Proveedor c) {
		// nuevo
		if (c.getId() == 0)
			clienteService.add(c, new GuardarCallBack(c));
		else
			clienteService.update(c, new GuardarCallBack(c));
	}

	public void eliminar(final Proveedor entidad) {
		clienteService.remove(entidad, new AsyncCallback<Proveedor>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Proveedors", "Eliminacion fallada, reintente");
			}

			@Override
			public void onSuccess(Proveedor result) {
				Info.display("Proveedors", "Eliminacion correcta");
				store.remove(entidad);
				editing.cancelEditing();
			}
		});
	}

	public static class GuardarCallBack implements AsyncCallback<Proveedor> {
		Proveedor c;

		public GuardarCallBack(Proveedor c) {
			this.c = c;
		}

		@Override
		public void onSuccess(Proveedor result) {
			c.setId(result.getId());
			Info.display("Proveedors", "Guardado");
		}

		@Override
		public void onFailure(Throwable caught) {
			Info.display("Proveedors", "Imposible guardar, reintente");
		}
	}
}
