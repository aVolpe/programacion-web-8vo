Ext.require([ 'Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.state.*', 'Ext.container.*' ]);
var vp;
var win;
var contactos;
var iden;
var nombre;
var storeContactos;

var TOKEN_SEPARADOR_VALUES = '!';
var TOKEN_SEPARADOR = ':';
var TOKEN_SEPARADOR_USERS = '&';

Ext.onReady(function() {
	Ext.QuickTips.init();

	storeContactos = Ext.create('Ext.data.ArrayStore', {
		fields : [ {
			name : 'nombre'
		}, {
			name : 'identificador'
		}, ]
	});


	contactos = Ext.create('Ext.grid.Panel', {
		stateId : 'stateGrid',
		store : storeContactos,
		columns : [ {
			text : 'Nombre',
			flex : 1,
			sortable : true,
			dataIndex : 'identificador',
			renderer : renderizarContactos,
		} ],

		height : 350,
		width : 600,
		viewConfig : {
			stripeRows : true
		},
		listeners : {
			cellclick : function(grid, td, cellIndex, record) {
				// var miStore = Ext.create('storeMensaje')
				// var grilla = Ext.create('gridMensaje',{
				// store: miStore
				// });

				setTab(record.data.identificador, record.data.nombre);

				// tabs.add({
				// title : record.data.nombre,
				// items: [ grilla ],
				// closable : true
				// }).show();
			}
		}
	});

	// se manejara un tab por conversacion
	tabs = Ext.create('Ext.tab.Panel', {
		id : "tabs",
		items : [ {
			title : 'Todos'
		} ]
	});

	Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
	vp = Ext.create('Ext.panel.Panel', {
		height : Ext.getBody().getViewSize().height,
		width : Ext.getBody().getViewSize().width, // 80% title:
		// 'Border Layout',
		layout : 'border',
		items : [ {
			// xtype: 'panel' implied by default
			title : 'Contactos',
			region : 'west',
			xtype : 'panel',
			margins : '5 0 0 5',
			width : 200,
			split : true,
			collapsible : true, // make collapsible
			id : 'west-region-container',
			layout : 'fit',
			items : [ contactos ]
		}, {
			title : 'Conversaciones',
			region : 'center', // center region is required, no width/height
			// specified
			xtype : 'panel',
			layout : 'fit',
			margins : '5 5 0 0',
			items : [ tabs ],
			dockedItems : [ {
				xtype : 'toolbar',
				items : [ {

					xtype : 'textfield',
					id : 'mensaje'

				}, {
					xtype : 'button',
					text : 'Enviar',
					handler : function() {
						var msg = Ext.getCmp('mensaje');
						var tabActivo = tabs.getActiveTab();
						var gridActivo = tabActivo.down('grid');
						var storeActivo = gridActivo.getStore();
						storeActivo.add({
							nombre : "Yo",
							mensaje : msg.value,
						});
						// no se me registra pues el store del nuevo
						var record = storeContactos.findRecord('nombre', tabActivo.title);
						send("mensaje:" + record.data.identificador + "!" + miID + "!" + msg.value);
					}
				} ]
			} ],
		} ],
		renderTo : Ext.getBody()
	});
	var colorPicker = Ext.create('Ext.ux.ColorPickerCombo');

	win = Ext.create('widget.window', {
		title : 'Ingrese su nombre',
		closable : true,
		closeAction : 'hide',
		width : 300,
		height : 150,
		layout : 'border',
		bodyStyle : 'padding: 5px;',
		items : [ {
			region : 'center',
			items : [ {
				xtype : 'textfield',
				fieldLabel : 'Nombre de usuario',
				emptyText : 'Ingrese su nombre',
				name : 'first',
				id : "user",
				allowBlank : false
			}, {
				id : 'color',
				xtype : 'colorcbo',
				fieldLabel : 'Color'
			} ]
		} ],
		buttons : [ {
			text : 'Login',
			handler : login
		} ],

	});

	vp.disable();
	win.show();

});

var miID = '';

var login = function() {
	var nombre = Ext.getCmp('user');
	var color = Ext.getCmp('color');
	send("login:" + nombre.getValue() + "!" + color.getValue());
};

var recibirListaContactos = function(contactos) {
	console.log(contactos);
	for ( var i = 0; i < contactos.length; i++) {
		contacto = contactos[i];
		var bandera = false;
		console.log(contacto);
		var partes = contacto.split(TOKEN_SEPARADOR_VALUES);
		console.log(partes);
		storeContactos.each(function(record) {
			if (record.get('identificador') == partes[1]) {
				bandera = true;
			}
		}, this);
		if (bandera == false) {
			storeContactos.add({
				nombre : partes[0],
				identificador : partes[1],
				color : partes[2]
				
			});
		}
	}
};

var recibirChat = function(usuario, mensaje) {
};

var recibirMensaje = function(mensaje) {
	var enString = mensaje.data.toString();
	console.log(enString);

	if (enString.charAt(0) == 'N') {
		var usuario = enString.split(TOKEN_SEPARADOR)[1];
		console.log(usuario);
		var nombre = usuario.split(TOKEN_SEPARADOR_VALUES)[0];
		var iden = usuario.split(TOKEN_SEPARADOR_VALUES)[1];
		storeContactos.add({
			nombre : nombre,
			identificador : iden
		});

	}

	if (enString.charAt(0) == 'R') {
		// var mensajeCompleto = enString.split(TOKEN_SEPARADOR)[1];
		// console.log(mensajeCompleto);
		var id_envio = enString.split(TOKEN_SEPARADOR)[1];
		var msg = enString.split(TOKEN_SEPARADOR)[2];
		// console.log("me mandaron esto " + msg);
		console.log(storeContactos);
		var record = storeContactos.findRecord('identificador', id_envio);
		setTab(id_envio, record.data.nombre);

		var tabActivo = tabs.getActiveTab();
		var gridActivo = tabActivo.down('grid');
		var storeActivo = gridActivo.getStore();
		storeActivo.add({
			nombre : record.data.nombre,
			mensaje : msg
		});
	}

	// alert(enString);
	if (enString.charAt(0) == 'U') {
		// lista de usuarios
		var usuarios = enString.split(TOKEN_SEPARADOR)[1];
		var lista = usuarios.split(TOKEN_SEPARADOR_USERS);
		console.log("LISTA = " + lista);
		recibirListaContactos(lista);
	}
	;
	if (enString.charAt(0) == 'L') {

		var user = Ext.getCmp('user');
		nombre = user.getValue();
		iden = mensaje.data.split(TOKEN_SEPARADOR)[1];
		if (iden != "0") {
			win.close();
			vp.enable();
			// storeContactos.add({
			// nombre : nombre,
			// identificador : iden
			// });
			miID = iden;
			send("usuarios");
		} else {
			alert("Nombre incorrecto");
		}

	}
};

function renderizarContactos(contacto){
	console.log(contacto);
	var con = storeContactos.findRecord('identificador', contacto);
	return '<span style="color:' + con.data.color + ';">' + con.data.nombre + '</span>';
};

var setTab = function(identificador, titulo) {
	var tab = Ext.getCmp(identificador);
	var pestanhas = Ext.getCmp('tabs');

	if (tab == undefined) {
		var miStore = Ext.create('storeMensaje');
		var grilla = Ext.create('gridMensaje', {
			store : miStore
		});

		pestanhas.add({
			title : titulo,
			id : identificador,
			items : [ grilla ],
			closable : true,
		});
	}
	var tab = Ext.getCmp(identificador);
	pestanhas.setActiveTab(tab);
};

Ext.define('storeMensaje', {
	extend : 'Ext.data.Store',
	fields : [ {
		name : 'nombre'
	}, {
		name : 'mensaje'
	} ]
});

Ext.define('gridMensaje', {
	extend : 'Ext.grid.Panel',

	store : '',
	columns : [ {
		dataIndex : 'nombre',
		text : 'Usuario'
	}, {
		text : 'Mensaje',
		dataIndex : 'mensaje',
		flex : 1,
	} ]
});