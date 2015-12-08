package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;






public class PacientesModel extends Model{
	private static ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
	private static String tabelanome = "pacientes";
	
	public static void listaRefresh() {
		pacientes.clear();
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from " + tabelanome);
			while(rstExiste.next()){
				pacientes.add(new Paciente(rstExiste.getInt("Id"), 
											rstExiste.getInt("CEP"), 
											rstExiste.getInt("Numero"), 
											rstExiste.getString("Nome"),
											rstExiste.getString("Senha"),
											rstExiste.getString("Complemento"),
											rstExiste.getInt("Disable")
										)
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Paciente[] getAll() {
		listaRefresh();
		Paciente[] retorno =  new Paciente[pacientes.size()];
		pacientes.toArray(retorno);
		return retorno;
	}
	public static Paciente getById(int id){
		for (int key = 0; key < pacientes.size(); key++ ) {
			Paciente esta = pacientes.get(key); 
			if(esta.getId() == id)
				return esta;
		}
		return null;		
	}
	public static Paciente[] getPesquisa(String qual) {
		listaRefreshPesquisa(qual);
		Paciente[] retorno =  new Paciente[pacientes.size()];
		pacientes.toArray(retorno);
		return retorno;
	}

	public static void listaRefreshPesquisa(String qual) {
		pacientes.clear();
		
		ResultSet rstExiste, rstExiste2, rstExiste3;
		try {
			rstExiste = myStm.executeQuery("select * from "+ tabelanome + " where Nome like '%" + qual + "%'");
			while(rstExiste.next()){
				pacientes.add(new Paciente(rstExiste.getInt("Id"), 
						rstExiste.getInt("CEP"), 
						rstExiste.getInt("Numero"), 
						rstExiste.getString("Nome"),
						rstExiste.getString("Senha"),
						rstExiste.getString("Complemento"),
						rstExiste.getInt("Disable")
					)
				);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static class Paciente{
		int id, cep, numero;
		String nome, senha, complemento;
		int ativo = 1;
		public Paciente(int id, int cep, int numero, String nome, String senha, String complemento) {
			super();
			this.id = id;
			this.cep = cep;
			this.numero = numero;
			this.nome = nome;
			this.senha = senha;
			this.complemento = complemento;
		}
		public Paciente(int id, int cep, int numero, String nome, String senha, String complemento, int ativo) {
			this(id, cep, numero, nome, senha, complemento);
			this.ativo = ativo;
		}
		public Paciente(int id, int cep, int numero, String nome, String senha, String complemento, boolean ativo) {
			this(id, cep, numero, nome, senha, complemento);
			if(ativo)this.ativo = 1;
			else this.ativo = 0;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getCep() {
			return cep;
		}
		public void setCep(int cep) {
			this.cep = cep;
		}
		public int getNumero() {
			return numero;
		}
		public void setNumero(int numero) {
			this.numero = numero;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getSenha() {
			return senha;
		}
		public void setSenha(String senha) {
			this.senha = senha;
		}
		public String getComplemento() {
			return complemento;
		}
		public void setComplemento(String complemento) {
			this.complemento = complemento;
		}
		public int getAtivo() {
			return ativo;
		}
		public void setAtivo(int ativo) {
			this.ativo = ativo;
		}
		public String toString(){
			return nome;
		}
	}

	

	
}
