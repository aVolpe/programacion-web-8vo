package py.com.una.pol.rest.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import py.com.una.pol.rest.domain.Llamada;
import py.com.una.pol.rest.repo.LlamadaDAO;

@Controller
@RequestMapping("/rest/llamadas")
public class LlamadasRestController {
	@Autowired
	LlamadaDAO dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public List<Llamada> listAllMembers() {
		return dao.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Llamada byId(@PathVariable("id") int id) {
		Llamada ll = dao.getById(id);
		System.out.println(ll.getDuracion());
		ll.getTelefono().setLlamadas(null);
		ll.getTelefono().setPersona(null);
		return ll;
	}
}
