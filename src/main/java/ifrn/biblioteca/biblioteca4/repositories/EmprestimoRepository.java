package ifrn.biblioteca.biblioteca4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.biblioteca.biblioteca4.models.Aluno;
import ifrn.biblioteca.biblioteca4.models.Emprestimo;
import ifrn.biblioteca.biblioteca4.models.Livro;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

	// Esse repository tem a capacidade de salvar, buscar... Alunos no meu banco de
	// dados
	// Isso porque ele extende o repositório JpaRepository que tem todas essas
	// funcionalidades

	List<Emprestimo> findByAluno(Aluno aluno); // Retorna uma lista de empréstimos associados a um aluno específico.

	List<Emprestimo> findByLivro(Livro livro); // Retorna uma lista de empréstimos associados a um livro específico.

	List<Emprestimo> findByAlunoAndDataDevolucaoIsNull(Aluno aluno); //Retorna uma lista de empréstimos ativos associados a um aluno específico.

	List<Emprestimo> findByLivroAndDataDevolucaoIsNull(Livro livro); //Retorna uma lista de empréstimos ativos associados a um livro específico.
}
