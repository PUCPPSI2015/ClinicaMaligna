package model.dbos;

import model.harddata.Cargos;

public class FuncAdmin implements EmpregadoClinica{
	private String nome;
	private int id, cargo;
	public FuncAdmin(int i, int c, String n){
		this.cargo = c;
		this.nome = n;
		this.id = i;
	}
	public void setCargo(int c){
		this.cargo = c;
	}
	public void setNome(String n){
		this.nome = n;
	}
	public int getId(){
		return this.id;
	}
	public String getIdString(){
		return "" + this.id;
	}
	public int getCargo(){
		return this.cargo;
	}
	public String getNome(){
		return this.nome;
	}
	
	//para a lista
	public String toString(){
		return this.nome + " - " + Cargos.get(this.cargo);
	}
	public boolean isFuncadmin() {
		return true;
	}
	public boolean isProfsaude() {
		return false;
	}
	
}
