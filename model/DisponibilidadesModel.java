package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;



public class DisponibilidadesModel  extends Model{
	private static ArrayList<Disponibilidade> disponibilidades = new ArrayList<Disponibilidade>();
	private static String tabelanome = "disponibilidades";
	
	public static void listaRefresh() {
		disponibilidades.clear();
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from " + tabelanome);
			while(rstExiste.next()){
				disponibilidades.add(new Disponibilidade(rstExiste.getInt("Id"), rstExiste.getInt("DiaDaSemana"), rstExiste.getInt("IdProfissional"), rstExiste.getInt("IdEspecialidade"), rstExiste.getTime("Inicio"), rstExiste.getTime("Fim") ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Disponibilidade getByProfAndDia(int prof, int dia){
		listaRefresh();
		for (int key = 0; key < disponibilidades.size(); key++ ) {
			Disponibilidade esta = disponibilidades.get(key); 
			if((esta.getIdProfissional() == prof)&&(esta.getDiaDaSemana() == dia))
				return esta;
		}
		return null;		
	}
	
	
	public static class Disponibilidade{
		int id,diaDaSemana,idProfissional,idEspecialidade;
		Time inicio, fim;

		public Disponibilidade(int id, int diaDaSemana, int idProfissional, int idEspecialidade, Time inicio, Time fim) {
			this.id = id;
			this.diaDaSemana = diaDaSemana;
			this.idProfissional = idProfissional;
			this.idEspecialidade = idEspecialidade;
			if(inicio.before(fim)){
				this.inicio = inicio;
				this.fim = fim;
			}else{
				this.inicio = fim;
				this.fim = inicio;
			}

		}
		@SuppressWarnings("deprecation")
		public Disponibilidade(int diaDaSemana,  int idEspecialidade, Date inicio, Date fim) {
			this.diaDaSemana = diaDaSemana;
			this.idEspecialidade = idEspecialidade;
			if(inicio.before(fim)){
				this.inicio = new Time(inicio.getHours(), inicio.getMinutes(), inicio.getSeconds());
				this.fim = new Time(fim.getHours(), fim.getMinutes(), fim.getSeconds());
			}else{
				this.inicio = new Time(fim.getHours(), fim.getMinutes(), fim.getSeconds());
				this.fim = new Time(inicio.getHours(), inicio.getMinutes(), inicio.getSeconds());
			}
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getDiaDaSemana() {
			return diaDaSemana;
		}
		public void setDiaDaSemana(int diaDaSemana) {
			this.diaDaSemana = diaDaSemana;
		}
		public int getIdProfissional() {
			return idProfissional;
		}
		public void setIdProfissional(int idProfissional) {
			this.idProfissional = idProfissional;
		}
		public int getIdEspecialidade() {
			return idEspecialidade;
		}
		public void setIdEspecialidade(int idEspecialidade) {
			this.idEspecialidade = idEspecialidade;
		}
		public Time getInicio() {
			return inicio;
		}
		public void setInicio(Time inicio) {
			this.inicio = inicio;
		}
		public Time getFim() {
			return fim;
		}
		public void setFim(Time fim) {
			this.fim = fim;
		}
		public String toString(){
			return "Disponibilidade de " + ProfSaudeModel.getOne(idProfissional) + " no dia " + new DiaSemana(diaDaSemana).toString()  + ";";
		}
	}
	public static class DiaSemana{
		int numero;
		public DiaSemana(int i){
			this.numero = i;
		}
		public int getNumero() {
			return numero;
		}
		public void setNumero(int numero) {
			this.numero = numero;
		}
		public String toString(){
			switch (this.numero){
				case 1: return "Domingo"; 
				case 2: return "Segunda"; 
				case 3: return "Terça"; 
				case 4: return "Quarta"; 
				case 5: return "Quinta"; 
				case 6: return "Sexta"; 
				case 7: return "Sábado"; 
			}
			return "indefinido";
		}
	}
}
