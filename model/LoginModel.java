package model;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import model.dbos.Acesso;

public class LoginModel extends Model{
	private static ArrayList<Acesso> acessos = new ArrayList<Acesso>();
	
	public static Acesso login(String login, char[] senha_) {
		Acesso achado;
		String senha = new String(senha_);
		
		//pegar tudo do banco de novo e botar na lista
		listaRefresh();
		
		//procurar na lista
		for(int i = 0; i < acessos.size(); i++){
			Acesso este = acessos.get(i);
			if( (este.getLogin().equals(login)) && (este.getSenha().equals(senha)) ){
				System.out.println("achamos  " + este.getSenha() );
				return este;
			}
			
		}
		
		return null;
		
	}
	public static Acesso getAcesso(int id){
		for(int i = 0; i < acessos.size(); i++){
			Acesso este = acessos.get(i);
			if(Integer.parseInt(este.getLogin()) == id){
				return este;
			}
		}
		return null;
	}
	
	public static void listaRefresh() {
		
		acessos.clear();
		
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from acesso");
			while(rstExiste.next()){
				acessos.add(new Acesso(rstExiste.getInt("Id") ,rstExiste.getString("Login"), rstExiste.getString("Senha")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
