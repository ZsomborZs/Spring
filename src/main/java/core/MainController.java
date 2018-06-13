package core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;

import struct.Gender;
import struct.Person;
import struct.Text;

/**
 * Controller for the webpage
 */
@Controller
public class MainController {
	Context context;
	String wtexts;
	Map<String, Object> cmap;

	private Person newperson = new Person();

	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping(value = { "/", "/page" })
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "homepage";
	}

	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "Web") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@RequestMapping("/getpeople")
	public String getPeople(@RequestParam(value = "name", required = false, defaultValue = "Jack") String name,
			Model model) {
		model.addAttribute("fname", Application.gateway.getPerson(name).getFirstName());
		model.addAttribute("lname", Application.gateway.getPerson(name).getLastName());
		model.addAttribute("sex", Application.gateway.getPerson(name).getSex());
		model.addAttribute("bdate", Application.gateway.getPerson(name).getBirthDate());
		return "people";
		// return Application.gateway.getPerson(name);
	}

	@RequestMapping("/finder")
	public String getPeople() {
		return "finder";
	}

	// Get form
	@GetMapping(value = { "/form", "/form_expand" })
	public String searchForm(Model model) {
		// model.addAttribute("comment", new Comment());
		model.addAttribute("person", new Person());
		model.addAttribute("newperson", newperson);
		return "form";
	}

	// Post form
	@PostMapping("/form") // params={"submit"}
	public String searchSubmit(@ModelAttribute Person content, Model model) {
		try {
			newperson = Application.gateway.getPerson(content.getFirstName());
			model.addAttribute("newperson", newperson);
			return "form_expand";
		} catch (org.springframework.core.convert.ConverterNotFoundException e) {
		}
		return "form";
	}

	@RequestMapping("/list")
	public String getAllPeople(String name, Model model) {
		model.addAttribute("persons", Application.gateway.listAllPeople());
		return "list";
	}

	@GetMapping("/ang")
	public String angForm(Map<String, Object> model) {
		// String now = new
		// SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		// context = new Context();context.setVariable("gend",wtext);
		model.put("person", new Person());
		model.put("gend", new Text());
		return "angular";
	}

	@PostMapping("/ang") // params={"submit"}
	public String angSubmit(@ModelAttribute Person content, @ModelAttribute Text wtext, Model model) {
		// System.out.println("wtext "+wtext.getText());
		try {
			Person person = new Person(content.getFirstName(), content.getLastName(), Gender.valueOf(wtext.getText()),
					content.getBirthDate(), null, null, null);
			Application.gateway.addPerson(person);
		} catch (java.lang.IllegalArgumentException e) {
		}
		// cmap=context.getVariables();
		// System.out.println(cmap.get("gend"));

		return "redirect:/list";
	}

	@GetMapping("/ang2")
	public String ang2Form(Map<String, Object> model) {
		return "pictures";
	}

	@GetMapping("/ang3")
	public String ang3Form(Map<String, Object> model) {
		return "angt";
	}

	@RequestMapping("/red")
	public String getDirect() {
		return "redirect:/form";
	}
}
