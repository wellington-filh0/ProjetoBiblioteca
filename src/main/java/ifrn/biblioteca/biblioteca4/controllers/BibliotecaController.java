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
	
	@PostMapping("/submit")
	public String submetido(Aluno aluno) {
		System.out.println("Dados do Aluno:");
		System.out.println("Nome: " + aluno.getNome());
		System.out.println("Matrícula: " + aluno.getMatricula());
		System.out.println("Cpf: " + aluno.getCpf());
		System.out.println("Nascimento: " + aluno.getNascimento());
		System.out.println("Endereço: " + aluno.getEndereco());

		return "redirect:/success";
	}

	@GetMapping("/success")
	public String successPage() {
		return "success";
	}
}
