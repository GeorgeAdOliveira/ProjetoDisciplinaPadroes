package DTO;

public class CoordenadorDTO {

	private String nome;
	private String email;
	private String senha;
	private boolean coordenadorExiste;

	public CoordenadorDTO() {

	}

	public CoordenadorDTO(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.setSenha(senha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getCoordenadorExiste() {
		return coordenadorExiste;
	}

	public void setCoordenadorExiste(boolean coordenadorExiste) {
		this.coordenadorExiste = coordenadorExiste;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
