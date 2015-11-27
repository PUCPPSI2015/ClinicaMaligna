package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.dbos.Acesso;
import views.states.State;

public class Cargos extends Model{
	//mapa associativo para cargos
	private static Map<Integer, String> cargos = new HashMap<Integer, String>();
	
	public static void preencher(){
		cargos.clear();
		ResultSet rstCargos;
		try {
			rstCargos = myStm.executeQuery("select * from cargos");
			while(rstCargos.next()){
				cargos.put( rstCargos.getInt("Id") , rstCargos.getString("Nome") );
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String get(int i){
		return cargos.get(i);
	}
	public static int getCargoIndex(int id){
		int i = 0;
		for (int key: cargos.keySet()) {
			if(key == id) return i;
			i++;
		}
		return -1;
	}
	
	public static Cargo[] getAll(){
		Cargo[] retorno = new Cargo[cargos.size()];
		int i = 0;
		for (int key: cargos.keySet()) {
			retorno[i] = new Cargo(key, cargos.get(key));
			i++;
		}
		return retorno;
	}
	
	//innerclass para cargos
	public static class Cargo{
		String nome;
		int id;
		public Cargo(int i, String n){
			this.id = i;
			this.nome = n;
		}
		public String toString(){
			return this.nome;
		}
		public int getId(){
			return this.id;
		}
	}
}
