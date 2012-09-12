package py.com.una.pol.rest.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import py.com.una.pol.rest.domain.Persona;
import py.com.una.pol.rest.domain.Telefono;
import py.com.una.pol.rest.repo.PersonaDAO;

@Controller
@RequestMapping("/rest/personas")
public class PersonaRestController {

	@Autowired
	PersonaDAO dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Persona> listAllMembers() {
		List<Persona> personas = dao.getAll();
		for (Persona p : personas) {
			p.setTelefonos(null);
		}
		return personas;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Persona byId(@PathVariable("id") int id) {
		Persona p = dao.getById(id);
		p.setTelefonos(null);
		return p;
	}

	@RequestMapping(value = "/{id}/telefonos", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Telefono getTelefonos(@PathVariable("id") int idPersona) {
		return null;
	}
}
