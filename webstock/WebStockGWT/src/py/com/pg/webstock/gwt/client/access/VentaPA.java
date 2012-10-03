package py.com.pg.webstock.gwt.client.access;

import java.util.Date;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.Venta;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface VentaPA extends PropertyAccess<Venta> {
	/**
	 * Este ModelKeyProvider nos retorna un ID unico por cada instance, la
	 * variable PATH se usa para decir como llegar a ese valor
	 * 
	 * @return
	 */
	@Path("id")
	ModelKeyProvider<Venta> key();

	/**
	 * Estos son valores que el utiliza para acceder a los elementos, utiliza
	 * los metodos getNombre() y setNombre() para acceder, osea aca se pone el
	 * atributo y el utiliza los getters, lo mismo que hace Java Server Faces,
	 * el popular JSF
	 * 
	 * @return
	 */
	ValueProvider<Venta, Date> fecha();

	ValueProvider<Venta, Cliente> cliente();

	ValueProvider<Venta, Double> total();

}
