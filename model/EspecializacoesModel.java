package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.harddata.Especialidades.Especialidade;
import controllers.ControllerPrincipal;


public class EspecializacoesModel extends Model{
	private static ArrayList<Especializacao> especializacoes = new ArrayList<Especializacao>();
	
	public static void listaRefresh() {
		
		especializacoes.clear();
		
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from especializacoes");
			while(rstExiste.next()){
				especializacoes.add(new Especializacao(rstExiste.getInt("IdProfissional") ,rstExiste.getInt("IdEspecialidade")));
			}
		} catch (SQLException e) {
			ControllerPrincipal.gritar("Erro de conexão com o banco de dados", "O banco não está aqui");
			e.printStackTrace();
		}
	}
	public static int[] getProfEsp(int prof){
		listaRefresh();
		ArrayList<Especializacao> minhasEspecializacoes = new ArrayList<Especializacao>();
		for (int key = 0; key < especializacoes.size(); key++ ) {
			Especializacao esta = especializacoes.get(key); 
			if(esta.getProfessional() == prof)
				minhasEspecializacoes.add( new Especializacao( esta.getProfessional() , esta.getEsp()) );
		}
		int[] retorno = new int[minhasEspecializacoes.size()];
		int i = 0;
		for (int keyo = 0; keyo < minhasEspecializacoes.size(); keyo++ ) {
			retorno[i] = minhasEspecializacoes.get(keyo).getEsp();
			i++;
		}
		return retorno;
		
	}
	
	
	public static class Especializacao{
		private int prof, esp; 
		public Especializacao(int p, int e){
			this.prof = p;
			this.esp = e;
		}
		public int getProfessional(){
			return this.prof;
		}
		public int getEsp(){
			return this.esp;
		}
	}
}
