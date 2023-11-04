package ifrn.biblioteca.biblioteca4.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity // Informando para criar uma tabela com os atributos dessa classe
public class Livro {

	@Id // Para o Spring Data saber que esse é o id da tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Toda tabela no banco de dados precisa de um id
	
	@NotBlank //Informando que o campo não pode estar em branco
	private String titulo;
	@NotBlank
	private String autor;
	@NotBlank
	private String editora;
	@NotNull
	private LocalDate anoPublicado;
	@NotBlank
	private String edicao;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public LocalDate getAnoPublicado() {
		return anoPublicado;
	}

	public void setAnoPublicado(LocalDate anoPublicado) {
		this.anoPublicado = anoPublicado;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", editora=" + editora
				+ ", anoPublicado=" + anoPublicado + ", edicao=" + edicao + "]";
	}
	
	

}
