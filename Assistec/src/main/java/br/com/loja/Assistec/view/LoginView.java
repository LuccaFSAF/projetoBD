package br.com.loja.Assistec.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.loja.controller.LoginController;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

	private JPanel contentPane;
	public JTextField txtUsuario;
	public JPasswordField password;
	public JLabel lblStatus;
	public JButton btnLogin;

	public LoginView() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				LoginController lc = new LoginController();
				try {
					if (lc.verificarBancoOnLine()) {
						lblStatus.setIcon(
								new ImageIcon(getClass().getResource("/br/com/loja/assistec/icones/dbok.png")));
					} else {

						lblStatus.setIcon(
								new ImageIcon(getClass().getResource("/br/com/loja/assistec/icones/dberror.png")));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Usuario:");

		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Senha:");

		btnLogin = new JButton("Entrar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onClickLogin();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		password = new JPasswordField();

		lblStatus = new JLabel("");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(62)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblNewLabel_1)
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(password))
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblNewLabel)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtUsuario,
														GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblStatus)
										.addComponent(btnLogin)))
						.addContainerGap(226, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(67)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						txtUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(27)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
						.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(lblStatus).addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
				.addComponent(btnLogin).addGap(44)));
		contentPane.setLayout(gl_contentPane);
	}

	protected void onClickLogin() throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<String> autenticado = new ArrayList();

		if (txtUsuario.getText() != null && !txtUsuario.getText().isEmpty()
				&& String.valueOf(password.getPassword()) != null
				&& !String.valueOf(password.getPassword()).isEmpty()) {
			LoginController lc = new LoginController();
			try {
				autenticado = lc.autenticar(txtUsuario.getText(), new String(password.getPassword()));
				if (autenticado.get(0) != null) {
					JOptionPane.showMessageDialog(this,
							"Bem vindo!" + " " + autenticado.get(0) + " " + "Acesso Liberado!", "Atenção",
							JOptionPane.INFORMATION_MESSAGE);

					this.dispose();
					PrincipalView prnc = new PrincipalView(autenticado.get(0), autenticado.get(1));
					prnc.setLocationRelativeTo(prnc);
					prnc.setVisible(true);
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(btnLogin, "Usuario ou Senha incorretos!!", "Aviso",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(btnLogin, "Verifique os dados inseridos!", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}