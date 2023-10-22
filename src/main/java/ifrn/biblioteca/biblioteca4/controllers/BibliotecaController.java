package ifrn.biblioteca.biblioteca4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ifrn.biblioteca.biblioteca4.models.Aluno;
import ifrn.biblioteca.biblioteca4.repositories.AlunoRepository;

@Controller
public class BibliotecaController {
	
	@Autowired //Define automaticamente a variável abaixo como um objeto
	private AlunoRepository ar;

	@RequestMapping("/biblioteca/form")
	public String form() {
		return "biblioteca/formAluno";
	}
	
	@PostMapping("/adicionarAluno")
	public String submetido(Aluno aluno) {

		System.out.println(aluno);
		ar.save(aluno);

		return "redirect:/success";
	}

	@GetMapping("/success")
	public String successPage() {
		return "biblioteca/aluno-adicionado";
	}
	
	@PostMapping("/alunoLogin")
	public String login(Aluno aluno, Long Long) {

		System.out.println(aluno);
		boolean verificacao = ar.existsById(Long);
		
		if(verificacao == true) {
			return "redirect:/alunoConectado";
		}

		return "biblioteca/formAluno";
	}

	@GetMapping("/alunoConectado")
	public String loginAceito() {
		return "biblioteca/alunoLogin";
	}
}
