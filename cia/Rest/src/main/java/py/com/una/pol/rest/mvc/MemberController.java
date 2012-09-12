package py.com.una.pol.rest.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MemberController {
//	@Autowired
//	private MemberDao memberDao;
//
//	@RequestMapping(method = RequestMethod.GET)
//	public String displaySortedMembers(Model model) {
//		// model.addAttribute("newMember", new Member());
//		// model.addAttribute("members", memberDao.findAllOrderedByName());
//		return "index";
//	}
//
//	@RequestMapping(method = RequestMethod.POST)
//	public String registerNewMember(BindingResult result, Model model) {
//		if (!result.hasErrors()) {
//			// memberDao.register(newMembCer);
//			return "redirect:/";
//		} else {
//			// model.addAttribute("members", memberDao.findAllOrderedByName());
//			return "index";
//		}
//	}
}
