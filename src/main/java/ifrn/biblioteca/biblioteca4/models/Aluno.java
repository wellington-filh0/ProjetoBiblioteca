package ifrn.biblioteca.biblioteca4.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity // Informando para criar uma tabela com os atributos dessa classe
public class Aluno {

	@Id // Para o Spring Data saber que esse é o id da tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Toda tabela no banco de dados precisa de um id

	@NotBlank  //Informando que o campo não pode estar em branco
	private String nome;
	@NotBlank
	private String matricula;
	@NotBlank
	private String cpf;
	@NotNull
	private LocalDate nascimento;
	@NotBlank
	private String endereco;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", matricula=" + matricula + ", cpf=" + cpf + ", nascimento="
				+ nascimento + ", endereco=" + endereco + "]";
	}

}
