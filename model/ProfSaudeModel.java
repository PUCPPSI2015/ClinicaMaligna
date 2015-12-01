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
		listaRefreshAll();
		for(int i = 0; i < profissionais.size(); i++){
			ProfSaude este = profissionais.get(i);
			if(este.getId() == id){
				return este;
			}
		}
		return null;
	}
	public static ProfSaude getOneByIdClasse(String id){
		listaRefreshAll();
		for(int i = 0; i < profissionais.size(); i++){
			ProfSaude este = profissionais.get(i);
			if(este.getIdClass().equals(id)){
				return este;
			}
		}
		return null;
	}
	public static void insertProf(String nome, String cpf, String senha, String idClasse, int[] especializacoes){
		try {
			ResultSet rstExiste;
			Integer resultado = -1;
			String insercao = 	"insert into "+ tabelanome + " " +
								"(CPF,  Nome, IdClasse) " +
								"values (" + cpf + ", '" + nome  + "', '" + idClasse  + "' )";
			System.out.println(insercao);
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
	        
	        
	        
	        //especializacoes
	        //adicionar novas
			for(int i = 0; i< especializacoes.length; i++){
				
						myStm.executeUpdate("insert into especializacoes " + 
	        					"(IdEspecialidade, IdProfissional) " + 
	        					"values(" + especializacoes[i] + "," + resultado + ")");
					
			
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void updateProf(int id, String nome, String senha, String cpf, String idClasseAntiga, String idClasse, int[] especializacoes){
		ResultSet rstExiste;
		String updeitap = 	"update "+ tabelanome + " " + 
				"set Nome='" + nome + "',CPF='" + cpf + "',IdClasse='" + idClasse + "' "+ 
				"where Id=" + id + "";
		
		
		String updeitaa = 	"update acesso " + 
				"set Senha='" + senha + "' " + 
				"where Login='m" + id + "'";
	
		String updeitaapc = 	"update acesso " + 
				"set Login='c" + idClasse + "' " + 
				"where Login='c" + idClasseAntiga + "'";
		
		
		String updeitaap = 	"update acesso " + 
				"set Senha='" + senha + "' " + 
				"where Login='c" + idClasse + "'";

		System.out.println("Atualizando "+ nome + "  com class antiga de "+ idClasseAntiga + " e nova de "+ idClasse + " e senha "+ senha + ";");
		
		//especializacoes
		//deletar tudo
		try {
			myStm.executeUpdate("delete from especializacoes " +
					"where IdProfissional=" + id + "");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//adicionar novas
		for(int i = 0; i< especializacoes.length; i++){
			//ver se já existe
			try {
				rstExiste = myStm.executeQuery("select * from especializacoes where IdEspecialidade=" + especializacoes[i] +" and IdProfissional=" + id +"");
				if (!rstExiste.next()) { //se não, gravar novas
					myStm.executeUpdate("insert into especializacoes " + 
        					"(IdEspecialidade, IdProfissional) " + 
        					"values(" + especializacoes[i] + "," + id + ")");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		try {
			myStm.executeUpdate(updeitap);
			myStm.executeUpdate(updeitaa);
			if(!idClasse.equals(idClasseAntiga))
				myStm.executeUpdate(updeitaapc);
			myStm.executeUpdate(updeitaap);
			listaRefreshAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void deletaProf(int id, String idClasse){
		String deletaf = 	"delete from "+ tabelanome + " " +
				"where Id=" + id + "",
			   deletaa = 	"delete from acesso " + 
				"where Login='m" + id + "'";
		String deletaap = 	"delete from acesso " + 
				"where Login='c" + idClasse + "'";
		
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
