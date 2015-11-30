package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;











import com.mysql.jdbc.Statement;

import model.dbos.FuncAdmin;
import model.dbos.ProfSaude;
import model.dbos.ProfSaude;

public class ProfSaudeModel extends Model{
	private static ArrayList<ProfSaude> profissionais = new ArrayList<ProfSaude>();
	private static String tabelanome = "profissionaissaude";
	public static ProfSaude[] getAll(){
		listaRefreshAll();
		ProfSaude[] retorno =  new ProfSaude[profissionais.size()];
		profissionais.toArray(retorno);
		return retorno;
	}
	public static void listaRefreshAll() {
		profissionais.clear();
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from " + tabelanome);
			while(rstExiste.next()){
				profissionais.add(new ProfSaude(rstExiste.getInt("Id") , rstExiste.getInt("CPF"), rstExiste.getString("IdClasse"), rstExiste.getString("Nome") ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ProfSaude getOne(int id){
		for(int i = 0; i < profissionais.size(); i++){
			ProfSaude este = profissionais.get(i);
			if(este.getId() == id){
				return este;
			}
		}
		return null;
	}
	public static void insertProf(String nome, String cpf, String senha){
		try {
			Integer resultado = -1;
			String insercao = 	"insert into "+ tabelanome + " " +
								"(CPF,  Nome) " +
								"values (" + cpf + ", '" + nome  + "' )";
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
	        					"values('m" + resultado + "','" + senha + "')";
	        String insercao3 =	"insert into acesso " + 
								"(Login, Senha) " + 
								"values('c" + cpf + "','" + senha + "')";
			
	        myStm.executeUpdate(insercao2);
	        myStm.executeUpdate(insercao3);
	        
	        //atualiza
	        listaRefreshAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void updateProf(int id, String nome, String senha, String cpf, String cpfAntiga){
		String updeitap = 	"update "+ tabelanome + " " + 
				"set Nome='" + nome + "',CPF='" + cpf + "',IdClasse='" + 44 + "' "+ 
				"where Id=" + id + "";
		
		
		String updeitaa = 	"update acesso " + 
				"set Senha='" + senha + "' " + 
				"where Login='m" + id + "'";
	
		String updeitaapc = 	"update acesso " + 
				"set Login='c" + cpf + "' " + 
				"where Login='c" + cpfAntiga + "'";
		
		
		String updeitaap = 	"update acesso " + 
				"set Senha='" + senha + "' " + 
				"where Login='c" + cpf + "'";

		try {
			myStm.executeUpdate(updeitap);
			myStm.executeUpdate(updeitaa);
			if(!cpf.equals(cpfAntiga))
				myStm.executeUpdate(updeitaapc);
			myStm.executeUpdate(updeitaap);
			listaRefreshAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void deletaProf(int id, String cpf){
		String deletaf = 	"delete from "+ tabelanome + " " +
				"where Id=" + id + "",
			   deletaa = 	"delete from acesso " + 
				"where Login='m" + id + "'";
		String deletaap = 	"delete from acesso " + 
				"where Login='c" + cpf + "'";
		
		try {
			myStm.executeUpdate(deletaf);
			myStm.executeUpdate(deletaa);
			myStm.executeUpdate(deletaap);
			listaRefreshAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//pesquisa
	public static ProfSaude[] getPesquisa(String qual){
		listaRefreshPesquisa(qual);
		ProfSaude[] retorno =  new ProfSaude[profissionais.size()];
		profissionais.toArray(retorno);
		return retorno;
	}

	public static void listaRefreshPesquisa(String qual) {
		profissionais.clear();
		
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from "+ tabelanome + " where Nome like '%" + qual + "%'");
			while(rstExiste.next()){
				profissionais.add(new ProfSaude(rstExiste.getInt("Id") , rstExiste.getInt("CPF"), rstExiste.getString("IdClasse"), rstExiste.getString("Nome") ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
