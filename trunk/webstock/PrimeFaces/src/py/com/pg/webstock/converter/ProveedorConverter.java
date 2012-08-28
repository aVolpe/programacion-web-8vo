package py.com.pg.webstock.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import py.com.pg.webstock.entities.Producto;
import py.com.pg.webstock.entities.Proveedor;

@FacesConverter("proveedorConverter")
public class ProveedorConverter implements Converter {

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		System.out.println("------------");
		System.out.println(value);
		System.out.println("------------");
		if (value == null || value.length() == 0) {
			return null;
		}
		Integer id = Integer.parseInt(value);

		return em.find(Proveedor.class, id);
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		return value instanceof Producto ? ((Producto) value).getId() + "" : "";
	}
}