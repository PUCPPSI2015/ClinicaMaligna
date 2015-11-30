package model.harddata;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import model.Model;
import model.harddata.Profissoes.Profissao;



public class Especialidades extends Model implements HardData{
	private static Map<Integer, Especialidade> especialidades = new HashMap<Integer, Especialidade>();
	public static void preencher(){
		especialidades.clear();
		ResultSet rstCargos;
		try {
			rstCargos = myStm.executeQuery("select * from especialidades");
			while(rstCargos.next()){
				especialidades.put( rstCargos.getInt("Id") , new Especialidade(rstCargos.getInt("Id"), rstCargos.getString("Nome"), rstCargos.getInt("IdProfissao")) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Especialidade[] getAll(){
		Especialidade[] retorno = new Especialidade[especialidades.size()];
		int i = 0;
		for (int key: especialidades.keySet()) {
			retorno[i] = especialidades.get(key);
			i++;
		}
		return retorno;
	}
	public static Especialidade[] getThisProf(int prof){
		Map<Integer, Especialidade> estasEspecialidades = new HashMap<Integer, Especialidade>();
		
		for (int key: especialidades.keySet()) {
			Especialidade esta = especialidades.get(key); 
			if(esta.getIdProf() == prof)
				estasEspecialidades.put( esta.getId() , new Especialidade(esta.getId(), esta.getNome(), esta.getIdProf()) );
		}
		
		Especialidade[] retorno = new Especialidade[estasEspecialidades.size()];
		int i = 0;
		for (int key: estasEspecialidades.keySet()) {
			retorno[i] = estasEspecialidades.get(key);
			i++;
		}
		return retorno;
	}
	public static class Especialidade {
		private int id;
		private String nome;
		private int idProf;
		public Especialidade(int i, String n, int ip){
			this.id = i;
			this.nome = n;
			this.idProf = ip;
		}
		public int getId(){
			return this.id;
		}
		public String getNome(){
			return this.nome;
		}
		public int getIdProf(){
			return this.idProf;
		}
		public void setId(int ip){
			this.id = ip;
		}
		public void setNome(String n){
			this.nome = n;
		}
		public void setIdProf(int ip){
			this.idProf = ip;
		}
		
		public String toString(){
			return this.nome;
		}
	}

}
