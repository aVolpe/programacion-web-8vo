package py.com.pg.webstock.gwt.client.access;

import java.util.Date;

import py.com.pg.webstock.entities.Proveedor;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ProveedorPA extends PropertyAccess<Proveedor> {
	/**
	 * Este ModelKeyProvider nos retorna un ID unico por cada instance, la
	 * variable PATH se usa para decir como llegar a ese valor
	 * 
	 * @return
	 */
	@Path("id")
	ModelKeyProvider<Proveedor> key();

	/**
	 * Este LabelProvicer nos muestra que sera mostrado cuando sea representada
	 * en pantalla, por ejemplo en un ComboBox, tiene le metodo getLabel que
	 * retorna el String que hace la magia!, es como un toString de GWT
	 * 
	 * @return
	 */
	@Path("nombre")
	LabelProvider<Proveedor> nameLabel();

	/**
	 * Estos son valores que el utiliza para acceder a los elementos, utiliza
	 * los metodos getNombre() y setNombre() para acceder, osea aca se pone el
	 * atributo y el utiliza los getters, lo mismo que hace Java Server Faces,
	 * el popular JSF
	 * 
	 * @return
	 */
	ValueProvider<Proveedor, String> nombre();

	ValueProvider<Proveedor, Integer> id();

	ValueProvider<Proveedor, String> ruc();

	ValueProvider<Proveedor, String> telefono();

	ValueProvider<Proveedor, Date> fechaAlta();

}
