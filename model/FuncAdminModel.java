package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;





import com.mysql.jdbc.Statement;

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
	public static void insertFunc(String nome, int cargo, String senha){
		try {
			Integer resultado = -1;
			String insercao = 	"insert into funcionarios " +
								"(Cargo, Nome) " +
								"values (" + cargo + ", '" + nome  + "' )";
			myStm.executeUpdate(insercao, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = myStm.getGeneratedKeys();
			//inserri no acesso e coisa e tal
			
			
			
	        if (rs.next()){
	            resultado = rs.getInt(1);
	        }
	        rs.close();
	        
	        //inserir no acesso
	        String insercao2 =	"insert into acesso " + 
	        					"(Login, Senha) " + 
	        					"values('" + resultado + "','" + senha + "')";
			
	        myStm.executeUpdate(insercao2);
	        
	        //atualiza
	        listaRefreshAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updateFun(int id, String nome, int cargo, String senha){
		String updeitaf = 	"update funcionarios " +
							"set Nome='" + nome + "',Cargo=" + cargo + " "+ 
							"where Id=" + id + "",
			   updeitaa = 	"update acesso " + 
							"set Senha='" + senha + "' " + 
							"where Id=" + id + "";
		
		try {
			myStm.executeUpdate(updeitaf);
			
			myStm.executeUpdate(updeitaa);
			listaRefreshAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public static void deletaFun(int id){
		String deletaf = 	"delete from funcionarios " +
							"where Id=" + id + "",
			   deletaa = 	"delete from acesso " + 
							"where Id=" + id + "";
		
		try {
			myStm.executeUpdate(deletaf);
			myStm.executeUpdate(deletaa);
			listaRefreshAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
	//pesquisa

	public static FuncAdmin[] getPesquisa(String qual){
		listaRefreshPesquisa(qual);
		FuncAdmin[] retorno =  new FuncAdmin[funcionarios.size()];
		funcionarios.toArray(retorno);
		return retorno;
	}
	
	public static void listaRefreshPesquisa(String qual) {
		
		funcionarios.clear();
		
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from funcionarios where Nome like '%" + qual + "%'");
			while(rstExiste.next()){
				funcionarios.add(new FuncAdmin(rstExiste.getInt("Id") ,rstExiste.getInt("Cargo"), rstExiste.getString("Nome")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
