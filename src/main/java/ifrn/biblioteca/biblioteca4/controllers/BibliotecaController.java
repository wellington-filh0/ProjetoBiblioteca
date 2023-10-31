package ifrn.biblioteca.biblioteca4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ifrn.biblioteca.biblioteca4.models.Aluno;
import ifrn.biblioteca.biblioteca4.models.Livro;
import ifrn.biblioteca.biblioteca4.repositories.AlunoRepository;
import ifrn.biblioteca.biblioteca4.repositories.LivroRepository;

@Controller
public class BibliotecaController {
	
	@Autowired //Define automaticamente a variável abaixo como um objeto
	private AlunoRepository ar;
	@Autowired
	private LivroRepository lr;
	//ADICIONANDO ALUNO

	@RequestMapping("/biblioteca/formAluno")
	public String formAluno() {
		return "biblioteca/formAluno";
	}
	
	@PostMapping("/adicionarAluno") //Post e Get do formAluno.html
	public String submetidoAluno(Aluno aluno) {

		System.out.println(aluno);
		ar.save(aluno);

		return "redirect:/successAluno";
	}

	@GetMapping("/successAluno")
	public String successAluno() {
		return "biblioteca/alunoAdicionado";
	}
	
	//ADICIONANDO LIVRO
	
	@RequestMapping("/biblioteca/formLivro")
	public String formLivro() {
		return "biblioteca/formLivro";
	}
	
	@PostMapping("/adicionarLivro") //Post e Get do formLivro.html
	public String submetidoLivro(Livro livro) {

		System.out.println(livro);
		lr.save(livro);

		return "redirect:/successLivro";
	}

	@GetMapping("/successLivro")
	public String successLivro() {
		return "biblioteca/livroAdicionado";
	}
	
	
}
