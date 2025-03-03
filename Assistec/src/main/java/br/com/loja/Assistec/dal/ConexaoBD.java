package br.com.loja.Assistec.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/lojabd";

	private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

	private static final String USER = "root";

	private static final String PASSWORD = "aluno";

	// método para estabelecer conexão com o BD
	public static Connection conectar() {

		// Estabelecer a conexao com o DB
		try {
			Class.forName(DRIVER_CLASS);
			return DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		}
		return null;
	}
	
	public void desconectar(Connection conexao) {
		try {
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
