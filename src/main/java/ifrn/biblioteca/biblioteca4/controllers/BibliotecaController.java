package ifrn.biblioteca.biblioteca4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ifrn.biblioteca.biblioteca4.models.Aluno;

@Controller
public class BibliotecaController {

	@RequestMapping("/biblioteca/form")
	public String form() {
		return "formAluno";
	}
	
	@PostMapping("/adicionarAluno")
	public String submetido(Aluno aluno) {

		System.out.println(aluno);

		return "redirect:/success";
	}

	@GetMapping("/success")
	public String successPage() {
		return "aluno-adicionado";
	}
}
