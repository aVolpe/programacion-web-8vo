package py.com.pg.webstock.gwt.client.access;

import py.com.pg.webstock.entities.DetalleVenta;
import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Venta;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DetalleVentaPA extends PropertyAccess<DetalleVenta> {
	/**
	 * Este ModelKeyProvider nos retorna un ID unico por cada instance, la
	 * variable PATH se usa para decir como llegar a ese valor
	 * 
	 * @return
	 */
	@Path("id")
	ModelKeyProvider<DetalleVenta> key();

	/**
	 * Estos son valores que el utiliza para acceder a los elementos, utiliza
	 * los metodos getNombre() y setNombre() para acceder, osea aca se pone el
	 * atributo y el utiliza los getters, lo mismo que hace Java Server Faces,
	 * el popular JSF
	 * 
	 * @return
	 */
	ValueProvider<DetalleVenta, Producto> producto();

	ValueProvider<DetalleVenta, Venta> venta();

	ValueProvider<DetalleVenta, Long> cantidad();

	ValueProvider<DetalleVenta, Double> precio();


}
