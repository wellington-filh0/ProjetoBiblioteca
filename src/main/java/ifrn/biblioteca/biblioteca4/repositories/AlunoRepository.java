package ifrn.biblioteca.biblioteca4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.biblioteca.biblioteca4.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	//Esse repository tem a capacidade de salvar, buscar... Alunos no meu banco de dados
	//Isso porque ele extende o repositório JpaRepository que tem todas essas funcionalidades
	
}
