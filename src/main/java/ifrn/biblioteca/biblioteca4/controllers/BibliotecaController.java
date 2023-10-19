package ifrn.biblioteca.biblioteca4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BibliotecaController {

	@RequestMapping("/biblioteca/form")
	public String form() {
		return "formAluno";
	}
}
