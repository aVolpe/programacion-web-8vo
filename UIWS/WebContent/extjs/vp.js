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
			sortable : false,
			dataIndex : 'nombre'
		} ],
		height : 350,
		width : 600,
		viewConfig : {
			stripeRows : true
		}
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
			margins : '5 5 0 0'
		} ],
		renderTo : Ext.getBody()
	});
	win = Ext.create('widget.window', {
		title : 'Ingrese su nombre',
		closable : true,
		closeAction : 'hide',
		width : 600,
		height : 200,
		layout : 'border',
		bodyStyle : 'padding: 5px;',
		items : [ {
			region : 'center',
			xtype : 'textfield',
			fieldLabel : 'Nombre de usuario',
			emptyText : 'Ingrese su nombre',
			name : 'first',
			id : "user",
			allowBlank : false
		} ],
		buttons : [ {
			text : 'Login',
			handler : login
		} ],

	});

	vp.disable();
	win.show();

});

var login = function() {
	var nombre = Ext.getCmp('user');
	send("login:" + nombre.getValue() + "!000");
};

var recibirListaContactos = function(contactos) {
	console.log(contactos)
	for ( var i = 0; i < contactos.length; i++) {
		contacto = contactos[i];
		var bandera = false;
		console.log(contacto)
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
				identificador : partes[1]
			});
		}
	}
};

var recibirChat = function(usuario, mensaje) {

}

var recibirMensaje = function(mensaje) {
	var enString = mensaje.data.toString();
	console.log(enString);
	if (enString.charAt(0) == 'U') {
		// lista de usuarios
		var usuarios = enString.split(TOKEN_SEPARADOR)[1];
		var lista = usuarios.split(TOKEN_SEPARADOR_USERS);
		console.log("LISTA = " + lista);
		recibirListaContactos(lista)
	}
	if (enString.charAt(0) == 'L') {

		var user = Ext.getCmp('user');
		nombre = user.getValue();
		iden = mensaje.data.split(TOKEN_SEPARADOR)[1];
		if (iden != "0") {
			win.close();
			vp.enable();
			storeContactos.add({
				nombre : nombre,
				identificador : iden
			});
			send("usuarios");
		} else {
			alert("Nombre incorrecto");
		}

	}
}