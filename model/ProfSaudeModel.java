package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;













import com.mysql.jdbc.Statement;

import model.dbos.FuncAdmin;
import model.dbos.ProfSaude;
import model.dbos.ProfSaude;
import model.DisponibilidadesModel.Disponibilidade;

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
				ProfSaude este = new ProfSaude(rstExiste.getInt("Id") , rstExiste.getInt("CPF"), rstExiste.getString("IdClasse"), rstExiste.getString("Nome") );
				int ativo = rstExiste.getInt("ativo");
				if(ativo != 0)
					profissionais.add(este);
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
	
	public static void insertProf(String nome, String cpf, String senha, String idClasse, int[] especializacoes, Disponibilidade[] disponibilidades){
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
			
			
			//disponibilidades
			DisponibilidadesModel.insertDisp(resultado, disponibilidades);
				
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void updateProf(int id, String nome, String senha, String cpf, String idClasseAntiga, String idClasse, int[] especializacoes, Disponibilidade[] alteradas){
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


		
		//especializacoes
		//deletar tudo
		try {
			myStm.executeUpdate("delete from especializacoes " +
				"where IdProfissional=" + id + "");
			//adicionar novas
			for(int i = 0; i< especializacoes.length; i++){
			//ver se já existe
				myStm.executeUpdate("insert into especializacoes " + 
					"(IdEspecialidade, IdProfissional) " + 
					"values(" + especializacoes[i] + "," + id + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		//disponibilidades
		DisponibilidadesModel.updateDisp(id, alteradas);
		
		
		/*
		
		//deletar tudo
		try {
			myStm.executeUpdate("delete from disponibilidades " +
				"where IdProfissional=" + id + "");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//iterar entre cada uma
		try {
			
			for(int j = 0; j< disponibilidades.length; j++){
				
			//insert
				myStm.executeUpdate("insert into disponibilidades " + 
					"(DiaDaSemana, Inicio, Fim, IdProfissional, IdEspecialidade) " + 
					"values(" + disponibilidades[j].getDiaDaSemana() + ",'" + df.format(disponibilidades[j].getInicio()) + "','" + df.format(disponibilidades[j].getFim()) + "'," + id + "," + disponibilidades[j].getIdEspecialidade() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		*/
		
		
		
		
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
		String deletae = "delete from especializacoes " +
		"where IdProfissional=" + id + "";
		String deletaf = 	"delete from "+ tabelanome + " " +
		"where Id=" + id + "",
		deletaa = 	"delete from acesso " + 
		"where Login='m" + id + "'";
		String deletaap = 	"delete from acesso " + 
		"where Login='c" + idClasse + "'";
		String deletaad = 	"delete from disponibilidades " +
				"where IdProfissional=" + id + "";
		
		try {
			myStm.executeUpdate(deletae);
			myStm.executeUpdate(deletaa);
			myStm.executeUpdate(deletaap);
			myStm.executeUpdate(deletaad);
			myStm.executeUpdate(deletaf);
			listaRefreshAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void inativaProf(int id){
		String inativaf = 	"update "+ tabelanome + " " + 
				"set ativo=0 "+ 
				"where Id=" + id + "";

		try {
			myStm.executeUpdate(inativaf);
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
		
		ResultSet rstExiste, rstExiste2, rstExiste3;
		try {
			rstExiste = myStm.executeQuery("select * from "+ tabelanome + " where Nome like '%" + qual + "%' or IdClasse like '%" + qual + "%'");
			while(rstExiste.next()){
				profissionais.add(new ProfSaude(rstExiste.getInt("Id") , rstExiste.getInt("CPF"), rstExiste.getString("IdClasse"), rstExiste.getString("Nome") ));
			}
			//pesquizar por especializacoes
			rstExiste = myStm.executeQuery("select * from especialidades where Nome like '%" + qual + "%'");
			int idesp;
			int idProf;
			while(rstExiste.next()){
				idesp = rstExiste.getInt("Id");
				System.out.println("select * from especializacoes where IdEspecialidade=" + idesp + "");
				rstExiste2 = myStm.executeQuery("select * from especializacoes where IdEspecialidade=" + idesp  + "");
				
				while(rstExiste2.next()){
					idProf = rstExiste2.getInt("IdProfissional");
					rstExiste3 = myStm.executeQuery("select * from "+ tabelanome + " where Id=" + idProf + "");
					while(rstExiste3.next()){
						profissionais.add(new ProfSaude(rstExiste3.getInt("Id") , rstExiste3.getInt("CPF"), rstExiste3.getString("IdClasse"), rstExiste3.getString("Nome") ));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
