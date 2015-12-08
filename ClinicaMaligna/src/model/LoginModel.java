package model;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.ControllerPrincipal;


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
			if(ControllerPrincipal.isInteger(este.getLogin())){ //é funcadmin
				if( (este.getLogin().equals(login)) && (este.getSenha().equals(senha)) ){
					return este;
				}
			}else{ //é prf saude
				char fc = este.getLogin().charAt(0);
				
				if((fc == 'm')||(fc == 'c')){ //eh acesso com o id de profsaude ou idclasse
					String subs = este.getLogin().substring(1);
					if( (subs.equals(login)) && (este.getSenha().equals(senha)) ){
						System.out.println(este.getLogin());
						return este;
					}
				}
				
				
			}
			
			
		}
		
		return null;
		
	}

	public static Acesso getAcesso(String id){
		for(int i = 0; i < acessos.size(); i++){
			Acesso este = acessos.get(i);
			if(este.getLogin().equals(id) ){
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
			ControllerPrincipal.gritar("Erro de conexão com o banco de dados", "O banco não está aqui");
			e.printStackTrace();
		}
		
	}

}
