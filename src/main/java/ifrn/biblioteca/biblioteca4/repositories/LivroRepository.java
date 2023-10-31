package ifrn.biblioteca.biblioteca4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.biblioteca.biblioteca4.models.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

	//Esse repository tem a capacidade de salvar, buscar... Livros no meu banco de dados
	//Isso porque ele extende o repositório JpaRepository que tem todas essas funcionalidades
}
