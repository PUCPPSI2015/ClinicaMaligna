package model;

import java.sql.*;

import javax.swing.JOptionPane;

public class Conexao {
	
	
	
	private final static String 	DBDRIVER = "com.mysql.jdbc.Driver",
					DBURL = "jdbc:mysql://localhost:3306/clinicamaligna",
					DBUSER = "root",
					DBPASS = "";
	
	private static Connection con;

	
	public static boolean test(){
		try{
			//tentar conexao;
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			//critar statment
			Statement stmTeste = con.createStatement();
			//executar query
			ResultSet rstTeste = stmTeste.executeQuery("select * from acesso");
			
			//processar resultados
			while(rstTeste.next()){}
			System.out.println("Deu tudo certo na conexao");
			return true;
			
		}catch(Exception e){
			System.out.println("Deu xabu na conexao");
			return false;
		}
		
	}
	
	public static void iniciar(){
		try {
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			//preencher hard datas
			Cargos.preencher();
		} catch (SQLException e) {
			System.out.println("Deu xabu na conexao");
		}	
	}
	public static Connection getCon(){
		return con;
	}
	public static Statement getStm(Connection c){
		try {
			return c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
