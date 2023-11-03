package ifrn.biblioteca.biblioteca4.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ifrn.biblioteca.biblioteca4.models.Aluno;
import ifrn.biblioteca.biblioteca4.models.Emprestimo;
import ifrn.biblioteca.biblioteca4.models.Livro;
import ifrn.biblioteca.biblioteca4.repositories.AlunoRepository;
import ifrn.biblioteca.biblioteca4.repositories.EmprestimoRepository;
import ifrn.biblioteca.biblioteca4.repositories.LivroRepository;

@Controller
public class BibliotecaController {

	@Autowired // Define automaticamente a variável abaixo como um objeto
	private AlunoRepository ar;
	@Autowired
	private LivroRepository lr;
	@Autowired
	private EmprestimoRepository er;

	// ADICIONANDO LIVRO

	@GetMapping("/biblioteca/formLivro")
	public String formLivro() {
		return "biblioteca/formLivro";
	}

	@PostMapping("/adicionarLivro")
	public String adicionarLivro(Livro livro) {

		System.out.println(livro);
		lr.save(livro);

		return "redirect:/successLivro";
	}

	@GetMapping("/successLivro")
	public String successLivro() {
		return "redirect:/biblioteca/listaLivros";
	}

	// LISTANDO ALUNOS

	@GetMapping("/biblioteca/listaAlunos")
	public ModelAndView listarAluno() {

		List<Aluno> alunos = ar.findAll();
		ModelAndView mv = new ModelAndView("biblioteca/listaAlunos");
		mv.addObject("alunos", alunos);
		return mv;

	}

	// LISTANDO LIVROS

	@GetMapping("/biblioteca/listaLivros")
	public ModelAndView listarLivro() {

		List<Livro> livros = lr.findAll();
		ModelAndView mv = new ModelAndView("biblioteca/listaLivros");
		mv.addObject("livros", livros);
		return mv;

	}

	// LISTANDO EMPRÉSTIMOS

	@GetMapping("/biblioteca/finalizarEmprestimo")
	public ModelAndView listarEmprestimo() {

		List<Emprestimo> emprestimos = er.findAll();
		ModelAndView mv = new ModelAndView("biblioteca/finalizarEmprestimo");
		mv.addObject("emprestimos", emprestimos);
		return mv;

	}

	// DETALHANDO LIVRO
	// TIRAR DÚVIDAAAAA!!!

	@GetMapping("/biblioteca/Livro/{id}")
	public ModelAndView detalharLivro(@PathVariable Long id) {

		ModelAndView md = new ModelAndView();
		Optional<Livro> opt = lr.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/biblioteca/listaLivros");
			return md;
		}

		md.setViewName("biblioteca/detalhes");
		Livro livro = opt.get();
		md.addObject("livro", livro);

		return md;
	}

	// ADICIONANDO ALUNO
	
	@GetMapping("/biblioteca/adicionarAluno")
	public String adcionarAluno() {
		return "biblioteca/formAluno";
	}

	@PostMapping("/biblioteca/adicionarAluno")
	public String adicionarAluno(Aluno aluno) {

		System.out.println(aluno);
		ar.save(aluno);
		return "redirect:/biblioteca/listaAlunos";
	}

	// ADICIONAR UM EMPRESTIMO

	@GetMapping("/biblioteca/emprestimo")
	public String formEmprestimo() {
		return "biblioteca/formEmprestimo";
	}

	@PostMapping("/biblioteca/emprestimo")
	public String adicionarEmprestimo(Long idLivro, Long idAluno) {
		Optional<Livro> livroOpt = lr.findById(idLivro);
		Optional<Aluno> alunoOpt = ar.findById(idAluno);

		if (livroOpt.isPresent() && alunoOpt.isPresent()) {
			Livro livro = livroOpt.get();
			Aluno aluno = alunoOpt.get();

			// VERIFICAR SE O ALUNO JÁ POSSUI 3 LIVROS EMPRESTADOS

			if (er.findByAluno(aluno).size() > 2) {
				System.out.println("O aluno atingiu o limite de empréstimos!");
				return "biblioteca/falhaAlunoLimite";
			}

			// VERIFICAR SE O LIVRO JÁ ESTÁ EMPRESTADO

			if (!er.findByLivro(livro).isEmpty()) {
				System.out.println("Este livro já se encontra emprestado!");
				return "biblioteca/falhaLivroEmprestado";
			}

			// CRIAR O EMPRÉSTIMO

			Emprestimo emprestimo = new Emprestimo();
			emprestimo.setAluno(aluno);
			emprestimo.setLivro(livro);
			emprestimo.setDataEmprestimo(LocalDate.now());
			emprestimo.setDataDevolucao(LocalDate.now().plusDays(14));

			er.save(emprestimo);
			System.out.println("EMPRÉSTIMO FINALIZADO");
			return "redirect:/biblioteca/finalizarEmprestimo";
		} else {
			System.out.println("EMPRÉSTIMO CANCELADO");
			return "biblioteca/falhaLivroAlunoNaoEncontrado";
		}
	}

	// FINALIZAR EMPÉSTIMO

	@PostMapping("/biblioteca/finalizarEmprestimo/{id}")
	public String finalizarEmprestimo(Long id) {
		Optional<Emprestimo> emprestimoOpt = er.findById(id);

		if (emprestimoOpt.isPresent()) {
			Emprestimo emprestimo = emprestimoOpt.get();
			emprestimo.setDataDevolucao(LocalDate.now());

			System.out.println("AGORA FOI: " + emprestimo.getDataEmprestimo());
			er.save(emprestimo);
		} else {
			System.out.println("AGORA NÃO");
			return "redirect:/biblioteca/listaLivros";
		}

		return "redirect:/biblioteca/finalizarEmprestimo";
	}

	// APAGAR ALUNO

	@GetMapping("/biblioteca/removerAluno/{id}")
	public String apagarAluno(@PathVariable Long id) {
		Optional<Aluno> opt = ar.findById(id);

		if (!opt.isEmpty()) {
			Aluno aluno = opt.get();

			List<Emprestimo> emprestimos = er.findByAluno(aluno);

			er.deleteAll(emprestimos);

			ar.delete(aluno);
		}

		return "redirect:/biblioteca/listaAlunos";
	}

	// APAGAR LIVRO

	@GetMapping("/biblioteca/removerLivro/{id}")
	public String apagarLivro(@PathVariable Long id) {
		Optional<Livro> opt = lr.findById(id);

		if (!opt.isEmpty()) {
			Livro livro = opt.get();
			lr.delete(livro);

			List<Emprestimo> emprestimos = er.findByLivro(livro);
			er.deleteAll(emprestimos);
		}

		return "redirect:/biblioteca/listaLivros";
	}

	// APAGAR EMPRESTIMO

	@GetMapping("/biblioteca/removerEmprestimo/{id}")
	public String apagarEmprestimo(@PathVariable Long id) {
		Optional<Emprestimo> opt = er.findById(id);

		if (!opt.isEmpty()) {
			Emprestimo emprestimo = opt.get();
			er.delete(emprestimo);

		}

		return "redirect:/biblioteca/finalizarEmprestimo";
	}

}
