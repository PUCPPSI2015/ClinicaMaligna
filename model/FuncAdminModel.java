package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import model.dbos.Acesso;
import model.dbos.FuncAdmin;

public class FuncAdminModel extends Model{
	private static ArrayList<FuncAdmin> funcionarios = new ArrayList<FuncAdmin>();
	
	
	public static FuncAdmin[] getAll(){
		listaRefreshAll();
		FuncAdmin[] retorno =  new FuncAdmin[funcionarios.size()];
		funcionarios.toArray(retorno);
		return retorno;
	}
	
	public static void listaRefreshAll() {
		
		funcionarios.clear();
		
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from funcionarios");
			while(rstExiste.next()){
				funcionarios.add(new FuncAdmin(rstExiste.getInt("Id") ,rstExiste.getInt("Cargo"), rstExiste.getString("Nome")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static FuncAdmin getOne(int id){
		for(int i = 0; i < funcionarios.size(); i++){
			FuncAdmin este = funcionarios.get(i);
			if(este.getId() == id){
				return este;
			}
		}
		return null;
	}
	
}
