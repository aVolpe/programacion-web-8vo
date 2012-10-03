package py.com.pg.webstock.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.Pago;
import py.com.pg.webstock.gwt.client.access.ClientePA;
import py.com.pg.webstock.gwt.client.access.PagoPA;
import py.com.pg.webstock.gwt.client.images.Recursos;
import py.com.pg.webstock.gwt.client.service.ClienteService;
import py.com.pg.webstock.gwt.client.service.ClienteServiceAsync;
import py.com.pg.webstock.gwt.client.service.PagoService;
import py.com.pg.webstock.gwt.client.service.PagoServiceAsync;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.cell.core.client.form.SpinnerFieldCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
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
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.DoublePropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Clase que implementa la interfaz de un ABM de un cliente, para poder agregar
 * a contenedores de widgets, debe implementar la interfaz IsWidget o extender
 * de Widget, como extender es menos flexible, usamos IsWidget
 * 
 * @author Arturo
 * 
 */
public class PagoABM implements IsWidget {

	/**
	 * ver documetnacion de la clase
	 * 
	 */
	public static final PagoPA pa = GWT.create(PagoPA.class);
	public static final ClientePA clientePA = GWT.create(ClientePA.class);
	// Aca le decimso al GWT qeu cree el servicio, y el hace la magia!
	// Atender qeu nos retorna un ASYNC pero le pasamos el no Asincronos
	PagoServiceAsync pagoService = GWT.create(PagoService.class);
	ClienteServiceAsync clienteServicePaElCombo = GWT
			.create(ClienteService.class);

	ListStore<Pago> store;
	ListStore<Cliente> clientesPaElCombo;

	Grid<Pago> g;

	ToolBar bb;

	GridRowEditing<Pago> editing;

	private static final DateTimeFormat fmt = DateTimeFormat
			.getFormat("EEEE, dd 'de' MMMM 'del' yyyy");

	@Override
	public Widget asWidget() {
		// Este contenedor es lo mas, muestra de arriba a abajo todo lo que le
		// agregamos
		VerticalLayoutContainer con = new VerticalLayoutContainer();
		// Aca se almacenan todos los valores que seran mostrados en el grid
		store = new ListStore<Pago>(pa.key());
		// este es el modlo de las columnas, usamos el por defecto par ano armar
		// bardo
		ColumnModel<Pago> cm = new ColumnModel<Pago>(getColumnConfig());
		// Grilla en si, le pasamos el store donde estan los componentes y la
		// configuracion de las columnas
		g = new Grid<Pago>(store, cm);
		//

		// esto le dice que la columna NOMBRE se exapndira cuando hya mucho
		// espacio
		g.getView().setAutoExpandColumn(cliente);

		// en este metodo seria bueno poner todas las llamadas, asi es mas
		// ordenado, solo por eso, no es nada preestablecido
		cargarStores();
		configEditing();
		crearToolBar();

		// TODO ver como hacer dinamico
		g.setHeight(800);
		// agregamos el toolbar y el grid a la vista, lo agregara arriba
		Document.get().setTitle("Pagos - WebStock");
		con.add(bb);
		con.add(g);
		return con;
	}

	private void cargarStores() {
		pagoService.getEntidades(new AsyncCallback<List<Pago>>() {
			@Override
			public void onSuccess(List<Pago> result) {
				store.addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Pagos", "no se pudo cargar pagos");
			}
		});
		// creamos un store vacio!.. pero nos pide un KeyAccess (un objeto que
		// dado un cliente, nos de un id unico para este objeto)
		clientesPaElCombo = new ListStore<Cliente>(clientePA.key());
		// ahora llamamos al servicio!
		clienteServicePaElCombo
				.getEntidades(new AsyncCallback<List<Cliente>>() {

					@Override
					public void onSuccess(
							List<Cliente> clientesRetornadosDelServicio) {
						// cargamos al STORE
						clientesPaElCombo.addAll(clientesRetornadosDelServicio);
					}

					@Override
					public void onFailure(Throwable arg0) {
						Info.display("Pagos", "no se pudo cargar clientes");
					}
				});

	}

	ColumnConfig<Pago, Integer> cod_pago;
	ColumnConfig<Pago, Cliente> cliente;
	ColumnConfig<Pago, Double> monto;
	ColumnConfig<Pago, Integer> estado;
	ColumnConfig<Pago, String> mensaje;
	ColumnConfig<Pago, Date> fecha;

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
	public List<ColumnConfig<Pago, ?>> getColumnConfig() {
		cod_pago = new ColumnConfig<Pago, Integer>(pa.codPago(), 150,
				"Codigo Pago");
		cliente = new ColumnConfig<Pago, Cliente>(pa.cliente(), 150, "Cliente");
		cliente.setCell(new AbstractCell<Cliente>() {
			/**
			 * Recibe tres argumentos, el contexto que es la pagina que se esta
			 * mostrando, El cliente que el recupero con el property access, y
			 * un SafeHTMLBuilder que es el que esta construyendo la pagina HTML
			 */
			@Override
			public void render(Context arg0, Cliente arg1, SafeHtmlBuilder arg2) {
				// si no hay cliente, no hacemos nada, esto hara que se muestre
				// en blanco!
				if (arg1 == null)
					return;
				else {
					// agregamso al documento el nombre del cliente, y lo
					// agregamos con appendEscaped para que elimine los
					// caracteres que no podesmo smostrar como "<", ">", "/" y
					// otros que arman bardo en una pagina html
					arg2.appendEscaped(arg1.getNombre());
				}
			}
		});
		monto = new ColumnConfig<Pago, Double>(pa.monto(), 150, "Monto");
		estado = new ColumnConfig<Pago, Integer>(pa.estado(), 150, "Estado");
		mensaje = new ColumnConfig<Pago, String>(pa.mensaje(), 150, "Mensaje");
		fecha = new ColumnConfig<Pago, Date>(pa.fecha(), 250, "Fecha de alta");
		// aca vos le decis que formatee la fecha, con ese DateCELL! y le pasas
		// el formato chusco ese que esta mas arriba, vamos a hacer uno a mano
		// para cliente!
		fecha.setCell(new DateCell(fmt));

		List<ColumnConfig<Pago, ?>> aRet = new ArrayList<ColumnConfig<Pago, ?>>();
		aRet.add(cod_pago);
		aRet.add(cliente);
		aRet.add(monto);
		aRet.add(estado);
		aRet.add(mensaje);
		aRet.add(fecha);
		return aRet;

	}

	/**
	 * Ahora hacemos a las celdas editables esto es re magico y lo mas
	 * interesante de esta clase del orte
	 */
	public void configEditing() {
		editing = new GridRowEditing<Pago>(g);

		// esto es para qeu valide automaticamente qeu es un numero, y para que
		// puedas tocar la flecha de arriba y sume uno!
		SpinnerFieldCell<Integer> sfcCodigo = new SpinnerFieldCell<Integer>(
				new IntegerPropertyEditor());

		NumberField<Integer> nfCodigo = new NumberField<Integer>(sfcCodigo,
				sfcCodigo.getPropertyEditor());
		nfCodigo.setEmptyText("Ingrese el Codigo");
		editing.addEditor(cod_pago, nfCodigo);

		// TextField tfClieahnte = new TextField();
		// tfCliente.setEmptyText("Ingrese el Cliente");
		// editing.addEditor(cliente, tfCliente);

		SpinnerFieldCell<Double> sfc = new SpinnerFieldCell<Double>(
				new DoublePropertyEditor());

		TextField tfMonto = new TextField();
		NumberField<Double> nfMonto = new NumberField<Double>(sfc,
				sfc.getPropertyEditor());
		tfMonto.setEmptyText("Ingrese el Monto");
		editing.addEditor(monto, nfMonto);

		// TextField tfEstado = new TextField();
		// tfEstado.setEmptyText("Ingrese el Estado");
		// editing.addEditor(estado, tfEstado);

		// TextField tfMensaje = new TextField();
		// tfMensaje.setEmptyText("Ingrese el Mensaje");
		// editing.addEditor(mensaje, tfMensaje);

		DateField dateField = new DateField(new DateTimePropertyEditor(fmt));
		dateField.setClearValueOnParseError(false);

		editing.addEditor(fecha, dateField);
		// PARTE DE CLIENTES
		// simplecombobox es lo mejor de sencha.. te hace la vida mas facil!..
		// al contrario de todo el resto que le rodea jajaj
		SimpleComboBox<Cliente> combo = new SimpleComboBox<Cliente>(
				clientePA.nameLabel());
		// le decimos al combo de donde va a quitar sus opciones
		combo.setStore(clientesPaElCombo);
		// y decimos que la columna sera editable con ese combo!
		editing.addEditor(cliente, combo);

		editing.addCompleteEditHandler(new CompleteEditHandler<Pago>() {

			@Override
			public void onCompleteEdit(CompleteEditEvent<Pago> event) {
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
				Pago nuevo = new Pago();
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
		
		TextButton tbSubir = new TextButton("Subir Pagos");
		tbSubir.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Dialog d = new Dialog();
				d.setHeadingText("Subir Archivo - Pagos");
				d.setHideOnButtonClick(true);
				d.add(new SubirArchivo());
				d.show();
			}
		});
		tbSubir.setIcon(Recursos.Util.getInstance().iconAdd());
		tbSubir.setIconAlign(IconAlign.RIGHT);
		
		
		
		
		
		bb.add(tbAdd);
		bb.add(tbRemove);
		bb.add(tbSubir);

	}

	public void editadoCompleto(final Pago p) {
		// cuando le demos uno nuevo, le va a llmar a pagoservice.add, que es el
		// que acabamos de sobreescribirmir
		if (p.getId() == 0)
			pagoService.add(p, new GuardarCallBack(p));
		else
			pagoService.update(p, new GuardarCallBack(p));
	}

	public void eliminar(final Pago entidad) {
		pagoService.remove(entidad, new AsyncCallback<Pago>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Pagos", "Eliminacion fallada, reintente");
			}

			@Override
			public void onSuccess(Pago result) {
				Info.display("Pagos", "Eliminacion correcta");
				store.remove(entidad);
				editing.cancelEditing();
			}
		});
	}

	public static class GuardarCallBack implements AsyncCallback<Pago> {
		Pago p;

		public GuardarCallBack(Pago p) {
			this.p = p;
		}

		@Override
		public void onSuccess(Pago result) {
			p.setId(result.getId());
			Info.display("Pagos", "Guardado");
		}

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Info.display("Pagos", "Imposible guardar, reintente");
		}
	}
}
