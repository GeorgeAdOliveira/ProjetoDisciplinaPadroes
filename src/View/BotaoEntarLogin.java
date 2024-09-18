package View;

import javax.swing.JOptionPane;

import Controller.AlunoController;
import Controller.CoordenadorController;
import DTO.AlunoDTO;
import DTO.CoordenadorDTO;

public class BotaoEntarLogin implements Button {

	private String email;
	private String senha;
	
	public BotaoEntarLogin(String email, String senha) {
		this.email = email;
		this.senha = senha;
		
	}

	public void logica() {
		// coordenador para fazer a verificação
		CoordenadorDTO coordenadorDto = new CoordenadorDTO();
		coordenadorDto.setEmail(email);
		coordenadorDto.setSenha(senha);
		CoordenadorController coordenadorController = new CoordenadorController();
		// aluno para fazer a verificação
		AlunoDTO alunoDto = new AlunoDTO();
		alunoDto.setEmail(email);
		alunoDto.setSenha(senha);
		AlunoController alunoController = new AlunoController();

		// Verificar se o usuário é o coordenador
		if (coordenadorController.loginCoordenador(coordenadorDto)) {
			new TelaMenuCoordenador();
			
			// Verificar se o usuário é aluno
		} else if (alunoController.loginAluno(alunoDto)) {
			String matricula = alunoController.recuperarMatricula(alunoDto);
			new TelaMenuAluno(matricula);
		}else {
			JOptionPane.showMessageDialog(null, "Usuario Não encontrado!");
			new TelaLogin();
		}
	}
}
