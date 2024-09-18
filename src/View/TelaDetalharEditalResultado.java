package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.EditalController;
import DTO.EditalDeMonitoriaDTO;
import Model.DisciplinaModel;
import Model.EditalDeMonitoriaModel;
import Model.InscricaoModel;

public class TelaDetalharEditalResultado extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditalDeMonitoriaModel edital;
	private JTable tabela;
	private String usuario;

	public TelaDetalharEditalResultado(String titulo, String tituloPadrao, String usuario, EditalDeMonitoriaModel edital) {
		super(titulo, tituloPadrao);
		this.edital = edital;
		this.usuario = usuario;
		adicionarTabela();
		adicionarBotoes();
		setVisible(true);
	}

	private class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Voltar":
				//verificar quem é o usuario e abre a tela respectiva
				if (usuario.equals("coordenador")) {
					new TelaListarEditais();
				} else {
					new TelaListarEditaisAlunos(usuario);
				}
				dispose();
				break;
			// Gerando o relatorio
			case "Gerar Relatório":
//				GeradorRelatorio.gerarRelatorioDeResultado(edital.getSituacaoDoEdital(), edital.getId(), central);
//				JOptionPane.showMessageDialog(null, "Relatótio de Resultado gerado!");
				break;
			//fechar o edital
			case "Fechar Edital":
				EditalDeMonitoriaDTO editalDto = new EditalDeMonitoriaDTO(edital.getNumeroDoEdital(),edital.getQtdDeInscricaoPorAluno() ,
						LocalDate.now(),edital.getDataFim() ,edital.getPesoCRE(),
						edital.getPesoNota(), edital.getDisciplinas());
				editalDto.setSituacaoDoEdital("Resultado Final");
				editalDto.setId(edital.getId());
				EditalController editalController = new EditalController();
				
				if(editalController.editarEdital(editalDto)) {
					JOptionPane.showMessageDialog(null, "Edital finalizado!");
				}
				new TelaListarEditais();
				dispose();
				break;
			case "Desistir da Vaga":
				int linhaSelecionada = tabela.getSelectedRow();
				if (linhaSelecionada == -1) {
					JOptionPane.showMessageDialog(null, "Selecione a Disciplina que deseja Desistir!");
				} else {
					//Encontrar a inscrição na parcistencia 
					int cont = 0;
//					EditalDeMonitoriaModel edt = central.recuperarEditalPorID(edital.getId());
//					for (DisciplinaModel disciplina : edt.getDisciplinas()) {
//						for (InscricaoModel i : disciplina.getInscricoes()) {
//							if (cont++ == linhaSelecionada) {
//								if (i.getAluno().getMatricula().equals(central.getAlunoLogado().getMatricula())) {
//									//verificado a situação do edital
//									if (i.getResultado().equals("Desistiu")) {
//										JOptionPane.showMessageDialog(null, "Você ja desistiu!");
//									} else {
//										int op = JOptionPane.showConfirmDialog(null, "Você realmente deseja desistir?",
//												"Pense Bem!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//										if (op == JOptionPane.YES_OPTION) {
//											//Desistir da vaga do edital
//											i.setResultado("Desistiu");
//											Persistencia.getInstance().salvar(central);
//											new JanelaListarEditaisAlunos();
//											dispose();
//										}
//
//									}
//
//								} else {
//									JOptionPane.showMessageDialog(null, "Selecione uma Disciplin que pertence a Você!");
//								}
//							}
//						}
//					}

				}
				break;
			case "Contatar Aluno": // contatar aluno só aparece no coordenador e no resultando final
				int alunoSelecionado = tabela.getSelectedRow();
				if (alunoSelecionado == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um aluno para contatar!");
				} else {
					//Procurando o aluno na lista da disciplina
//					int cont = 0;
//					EditalDeMonitoriaModel edt = central.recuperarEditalPorID(edital.getId());
//					for (DisciplinaModel disciplina : edt.getDisciplinas()) {
//						for (InscricaoModel i : disciplina.getInscricoes()) {
//							if (cont++ == alunoSelecionado) {
//								//Abrindo a janela para contatar o aluno
//								new JanelaContatarAluno(i.getAluno(), edital);
//								dispose();
//								break;
//							}
//						}
//					}

				}
			}
		}
	}
	
	private void adicionarTabela() {

		DefaultTableModel modelo = new DefaultTableModel();
		// definir as colunas
		modelo.addColumn("Nome do Aluno");
		modelo.addColumn("Disciplina");
		modelo.addColumn("Resultado");

		for (DisciplinaModel disciplina : edital.getDisciplinas()) {
			for (InscricaoModel i : disciplina.getInscricoes()) {
				Object[] linha = new Object[4];
				linha[0] = i.getAluno().getNome();
				linha[1] = i.getDisciplina().getNomeDaDisciplina();
				linha[2] = i.getResultado();
				modelo.addRow(linha);
			}
		}

		tabela = new JTable(modelo);
		JScrollPane jsInscricoes = new JScrollPane(tabela);
		jsInscricoes.setBounds(90, 130, 500, 170);
		jsInscricoes.createVerticalScrollBar();
		add(jsInscricoes);

	}

	public void adicionarBotoes() {

		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(520, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

		JButton btDoMeio;
		// Usuario Coordenador
		if (usuario.equals("coordenador")) {
			// contatar aluno só aparece no coordenador
			JButton btContatarAluno = new JButton("Contatar Aluno");
			btContatarAluno.setBounds(210, 350, 130, 30);
			btContatarAluno.addActionListener(ouvinte);
			add(btContatarAluno);
			// Gerar Relatorio só aparece no coordenador
			btDoMeio = new JButton("Gerar Relatório");

			if (edital.getSituacaoDoEdital().equals("Resultado Preliminar")) {
				// Fechar edital só aparece no coordenador e no resultando preliminar
				JButton btFecharEdital = new JButton("Fechar Edital");
				btFecharEdital.setBounds(210, 310, 130, 30);
				btFecharEdital.addActionListener(ouvinte);
				add(btFecharEdital);
			}
			// Usuario Aluno
		} else {
			// Desistir da Vaga so aparece no Aluno e no resultado preliminar
			btDoMeio = new JButton("Desistir da Vaga");
		}
		// Logica que me garante, o btDoMeio vai aparecer no local certo e com o nome
		// Certo
		if (!(usuario.equals("aluno") && edital.getSituacaoDoEdital().equals("Resultado Final"))) {
			btDoMeio.setBounds(350, 350, 150, 30);
			btDoMeio.addActionListener(ouvinte);
			add(btDoMeio);
		}

	}

}