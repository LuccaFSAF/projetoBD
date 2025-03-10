package br.com.loja.assistec.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.loja.assistec.view.MessagemView;
import br.com.loja.assistec.view.PrincipalView;

public class PrincipalController {
	private PrincipalView principalView;
	protected String login;
	protected String perfil;

	public PrincipalController(String login, String perfil) {
		this.login = login;
		this.perfil = perfil;
		this.principalView = new PrincipalView();
		configurarJanela();
		// Configura o controlador para responder a eventos
		configurarListeners();

	}

	// Configura propriedades iniciais da janela principal
	private void configurarJanela() {
		principalView.setLocationRelativeTo(null);
		principalView.setVisible(true);
	}

	// Classe interna para gerenciar eventos de menu
	private class MenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String comando = e.getActionCommand();

			switch (comando) {
			case "MenuUsuariosAction":
				try {
					abrirListagemUsuarios();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "MenuSairAction":
				sairDoSistema();
				break;
			case "MenuSobreAction":
				mostrarInformacoesSobre();
				break;
			default:
				break;
			}
		}
	}

	// Configura os listeners para os itens de menu e eventos de janela
	private void configurarListeners() {
		principalView.addPrincipalViewListener(new MenuActionListener());
		principalView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				configurarPerfilUsuario();
			}
		});
	}

	// Abre a tela de listagem de usuários
	private void abrirListagemUsuarios() throws SQLException {
		new ListarUsuarioController();
	}

	// Exibe a mensagem de confirmação de saída e fecha o sistema se confirmado
	private void sairDoSistema() {
		//int confirmacao = principalView.confirmarFecharSistema();
		MessagemView view = new MessagemView("Tem certeza que deseja sair?");
		int confirmacao = view.getResposta();
		if (confirmacao == 1) {
			System.exit(0);
		}
	}

	// Exibe a tela "Sobre" do sistema
	private void mostrarInformacoesSobre() {
		//principalView.mostrarSobre();
		new MessagemView("Sistema de Gestão Assistec", 10);
	}

	// Configura o perfil do usuário e ajusta permissões no menu
	private void configurarPerfilUsuario() {
		ArrayList<String> listaPerfil = new ArrayList<>();
		if ("Admin".equalsIgnoreCase(perfil)) {
			listaPerfil.add("MenuRelatorio");
			listaPerfil.add("MenuCadastro");
		}
		principalView.configurarPerfilUsuario(login, listaPerfil);
	}

}