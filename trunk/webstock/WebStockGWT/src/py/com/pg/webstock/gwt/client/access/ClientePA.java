package py.com.pg.webstock.gwt.client.access;

import java.util.Date;

import py.com.pg.webstock.entities.Cliente;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ClientePA extends PropertyAccess<Cliente> {
	/**
	 * Este ModelKeyProvider nos retorna un ID unico por cada instance, la
	 * variable PATH se usa para decir como llegar a ese valor
	 * 
	 * @return
	 */
	@Path("id")
	//esta clase hace eso!.. dado un cliente el retorna un ID unico!
	ModelKeyProvider<Cliente> key();

	/**
	 * Este LabelProvicer nos muestra que sera mostrado cuando sea representada
	 * en pantalla, por ejemplo en un ComboBox, tiene le metodo getLabel que
	 * retorna el String que hace la magia!, es como un toString de GWT
	 * 
	 * @return
	 */
	@Path("nombre")
	LabelProvider<Cliente> nameLabel();

	/**
	 * Estos son valores que el utiliza para acceder a los elementos, utiliza
	 * los metodos getNombre() y setNombre() para acceder, osea aca se pone el
	 * atributo y el utiliza los getters, lo mismo que hace Java Server Faces,
	 * el popular JSF
	 * 
	 * @return
	 */
	ValueProvider<Cliente, String> nombre();

	ValueProvider<Cliente, Integer> id();

	ValueProvider<Cliente, String> ruc();

	ValueProvider<Cliente, String> telefono();

	ValueProvider<Cliente, Double> saldo();

	ValueProvider<Cliente, Date> fechaAlta();

}
