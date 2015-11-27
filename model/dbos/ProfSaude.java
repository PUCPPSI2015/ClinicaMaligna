package model.dbos;

public class ProfSaude {
	private int Id, cpf;
	String idClass, Nome; 
	public ProfSaude(int i, int c, String ic, String n){
		this.Id = i;
		this.cpf = c;
		this.idClass = ic;
		this.Nome = n;
	}
	public int getId(){
		return this.Id;
	}
	public int getCpf(){
		return this.cpf;
	}
	public String getIdClass(){
		return this.idClass;
	}
	public String getNome(){
		return this.Nome;
	}
	public String toString(){
		return this.Nome;
	} 
}
