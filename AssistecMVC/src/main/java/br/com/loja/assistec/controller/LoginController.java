package br.com.loja.assistec.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.loja.assistec.model.LoginDAO;
import br.com.loja.assistec.model.Usuario;
import br.com.loja.assistec.view.LoginView;
import br.com.loja.assistec.view.MessagemView;
import br.com.loja.assistec.view.PrincipalView;

public class LoginController {
	private LoginDAO dao;
	private LoginView view;
	private ArrayList<String> listaDadosView;

	public LoginController(LoginDAO dao, LoginView view) {
		this.dao = dao;
		this.view = view;
		configurarListeners();
		this.view.setLocationRelativeTo(null);
		this.view.setVisible(true);
	}

	private class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ("BotaoLoginAction".equals(e.getActionCommand())) {
				processarLogin();
				PrincipalView pv = new PrincipalView();
				pv.setLocationRelativeTo(pv);
				pv.setVisible(true);
			}
		}
	}

	private void configurarListeners() {
		view.addLoginListener(new LoginListener());

		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				view.mostrarBancoOnline(dao.bancoOnline());
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (listaDadosView != null && !listaDadosView.isEmpty()) {
					new PrincipalController(listaDadosView.get(0), listaDadosView.get(1));
				}
			}
		});
	}

	public Boolean verificarBancoOnline() throws SQLException {
		return this.dao.bancoOnline();
	}

	public void processarLogin() {
		String login = view.getLogin();
		String senha = view.getSenha();
		try {
			if (!dao.bancoOnline()) {
				//view.mostrarMensagem("Banco de dados desconectado!", "Erro");
				new MessagemView("Banco de Dados desconectado!", 0); 
			} else if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				listaDadosView = autenticar(login, senha);
				if (listaDadosView != null) {
					//view.mostrarMensagem("Bem vindo " + listaDadosView.get(0) + " acesso liberado!", "Informação");
					new MessagemView("Bem Vindo" + listaDadosView.get(0) + "acesso liberado", 3);
					view.dispose();
				} else {
					//view.mostrarMensagem("Usuário ou senha inválidos!", "Atenção");
					new MessagemView("Usuario ou senha incorretos!", 2);
				}
			} else {
				//view.mostrarMensagem("Verifique as informações!", "Atenção");
				new MessagemView("Verifique as infotmações!", 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> autenticar(String login, String senha) throws SQLException {
		Usuario user = dao.autenticar(login, senha);
		if (user != null) {
			ArrayList<String> listaDados = new ArrayList<>();
			listaDados.add(user.getNome());
			listaDados.add(user.getPerfil());
			return listaDados;
		}
		return null;
	}
}
