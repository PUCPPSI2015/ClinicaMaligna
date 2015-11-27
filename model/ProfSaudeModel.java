package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;





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
	public static void insertProf(){
		
	}
	public static void updateProf(){
		
	}
	public static void deletaProf(int id){
		
	}
	
	//pesquisa
	public static ProfSaude[] getPesquisa(String qual){
		listaRefreshPesquisa(qual);
		ProfSaude[] retorno =  new ProfSaude[profissionais.size()];
		profissionais.toArray(retorno);
		return retorno;
	}

	public static void listaRefreshPesquisa(String qual) {}


}
