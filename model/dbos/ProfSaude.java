package model.dbos;

public class ProfSaude implements EmpregadoClinica{
	private int Id;
	long cpf;
	String idClass, Nome; 
	public ProfSaude(int i, long l, String ic, String n){
		this.Id = i;
		this.cpf = l;
		this.idClass = ic;
		this.Nome = n;
	}
	public int getId(){
		return this.Id;
	}
	public long getCpf(){
		return this.cpf;
	}
	public String getIdClass(){
		return this.idClass;
	}
	public String getNome(){
		return this.Nome;
	}
	public String getIdClasse(){
		return this.idClass;
	}
	public String toString(){
		return this.Nome;
	}
	@Override
	public boolean isFuncadmin() {
		return false;
	}
	@Override
	public boolean isProfsaude() {
		return true;
	}
	
}
