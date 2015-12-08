package model.dbos;

public class Acesso{
	private String login;
	private String senha;
	private int id;
	public Acesso(int i, String l, String s){
		this.login = l;
		this.senha = s;
		this.id = i;
	}
	public void setLogin(String l){
		this.login = l;
	}
	public void setSenha(String s){
		this.senha = s;
	}
	public int getId(){
		return this.id;
	}
	public String getLogin(){
		return this.login;
	}
	public String getSenha(){
		return this.senha;
	}
	
}
