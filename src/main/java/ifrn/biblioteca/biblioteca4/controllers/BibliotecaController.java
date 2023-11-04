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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String formLivro(Livro livro) {
		return "biblioteca/formLivro";
	}

	@PostMapping("/adicionarLivro")
	public String adicionarLivro(Livro livro, RedirectAttributes attributes) {

		System.out.println(livro);
		lr.save(livro);
		attributes.addFlashAttribute("mensagem", "Livro salvo com sucesso!");
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
	public String adicionarAluno(Aluno aluno) {
		return "biblioteca/formAluno";
	}

	@PostMapping("/biblioteca/adicionarAluno")
	public String adicionarAluno(Long idLivro,Aluno aluno, RedirectAttributes attributes) {

		System.out.println(aluno);
		ar.save(aluno);
		attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
		return "redirect:/biblioteca/listaAlunos";
	}

	// ADICIONAR UM EMPRESTIMO

	@GetMapping("/biblioteca/emprestimo")
	public String formEmprestimo() {
		return "biblioteca/formEmprestimo";
	}

	@PostMapping("/biblioteca/emprestimo")
	public String adicionarEmprestimo(Long idLivro, Long idAluno, RedirectAttributes attributes) {
		Optional<Livro> livroOpt = lr.findById(idLivro);
		Optional<Aluno> alunoOpt = ar.findById(idAluno);

		if (livroOpt.isPresent() && alunoOpt.isPresent()) {
			Livro livro = livroOpt.get();
			Aluno aluno = alunoOpt.get();

			// VERIFICAR SE O ALUNO JÁ POSSUI 3 LIVROS EMPRESTADOS

			if (er.findByAluno(aluno).size() > 2) {
				System.out.println("O aluno atingiu o limite de empréstimos!");
				attributes.addFlashAttribute("mensagem", "O aluno atingiu o limite de empréstimos!");
				return "redirect:/biblioteca/finalizarEmprestimo";
			}

			// VERIFICAR SE O LIVRO JÁ ESTÁ EMPRESTADO

			if (!er.findByLivro(livro).isEmpty()) {
				System.out.println("Este livro já se encontra emprestado!");
				attributes.addFlashAttribute("mensagem", "Este livro já se encontra emprestado!");
				return "redirect:/biblioteca/finalizarEmprestimo";
			}

			// CRIAR O EMPRÉSTIMO

			Emprestimo emprestimo = new Emprestimo();
			emprestimo.setAluno(aluno);
			emprestimo.setLivro(livro);
			emprestimo.setDataEmprestimo(LocalDate.now());
			emprestimo.setDataDevolucao(LocalDate.now().plusDays(14));

			er.save(emprestimo);
			System.out.println("EMPRÉSTIMO FINALIZADO");
			attributes.addFlashAttribute("mensagem", "Empréstimo realizado com sucesso!");
			return "redirect:/biblioteca/finalizarEmprestimo";
		} else {
			attributes.addFlashAttribute("mensagem", "O aluno ou livro não se encontra cadastrado no sistema!");
			System.out.println("EMPRÉSTIMO CANCELADO");
			return "redirect:/biblioteca/finalizarEmprestimo";
		}
	}

	// FINALIZAR EMPÉSTIMO
	@PostMapping("/biblioteca/finalizarEmprestimo/{id}")
	public String finalizarEmprestimo(Long id, RedirectAttributes attributes) {
		Optional<Emprestimo> emprestimoOpt = er.findById(id);

		

		if (emprestimoOpt.isPresent()) {
			Emprestimo emprestimo = emprestimoOpt.get();

			System.out.println("AGORA FOI: ");
			er.delete(emprestimo);
			attributes.addFlashAttribute("mensagem", "O empréstimo foi finalizado!");
		} else {
			System.out.println("AGORA NÃO");
			attributes.addFlashAttribute("mensagem", "O ID do empréstimo informado não existe!");
			return "redirect:/biblioteca/finalizarEmprestimo";
		}

		return "redirect:/biblioteca/finalizarEmprestimo";
	}

	// APAGAR ALUNO

	@GetMapping("/biblioteca/removerAluno/{id}")
	public String apagarAluno(@PathVariable Long id, RedirectAttributes attributes) {
		Optional<Aluno> opt = ar.findById(id);
		Aluno aluno = opt.get();

		if (!er.findByAluno(aluno).isEmpty()) {
			System.out.println("Possui empréstimos");
			attributes.addFlashAttribute("mensagem", "O aluno possui empréstimos ativos!");
			return "redirect:/biblioteca/listaAlunos";
		}

		if (!opt.isEmpty()) {
			ar.delete(aluno);
			attributes.addFlashAttribute("mensagem", "Aluno removido com sucesso!");
		}

		return "redirect:/biblioteca/listaAlunos";
	}

	// APAGAR LIVRO

	@GetMapping("/biblioteca/removerLivro/{id}")
	public String apagarLivro(@PathVariable Long id, RedirectAttributes attributes) {
		Optional<Livro> opt = lr.findById(id);
		Livro livro = opt.get();

		if (!er.findByLivro(livro).isEmpty()) {
			System.out.println("Possui empréstimos");
			attributes.addFlashAttribute("mensagem", "O livro se encontra emprestado no momento!");
			return "redirect:/biblioteca/listaLivros";
		}

		if (!opt.isEmpty()) {
			lr.delete(livro);
			attributes.addFlashAttribute("mensagem", "Livro removido com sucesso!");
		}

		return "redirect:/biblioteca/listaLivros";
	}

	// SELECIONAR LIVRO

	@GetMapping("/biblioteca/selecionarLivro/{id}")
	public ModelAndView selecionarLivro(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Livro> opt = lr.findById(id);

		if (opt.isEmpty()) {
			md.setViewName("redirect:/biblioteca/listaLivros");
			return md;
		}

		Livro livro = opt.get();

		md.setViewName("biblioteca/formLivro");
		md.addObject("livro", livro);

		return md;

	}

	// SELECIONAR ALUNO

	@GetMapping("/biblioteca/selecionarAluno/{id}")
	public ModelAndView selecionarAluno(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Aluno> opt = ar.findById(id);

		if (opt.isEmpty()) {
			md.setViewName("redirect:/biblioteca/listaAlunos");
			return md;
		}

		Aluno aluno = opt.get();
		md.setViewName("biblioteca/formAluno");
		md.addObject("aluno", aluno);

		return md;

	}

}
