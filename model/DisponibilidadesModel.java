package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.ConsultasModel.Consulta;
import controllers.ControllerPrincipal;



public class DisponibilidadesModel  extends Model{
	private static ArrayList<Disponibilidade> disponibilidades = new ArrayList<Disponibilidade>();
	private static String tabelanome = "disponibilidades";
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	
	public static void listaRefresh() {
		disponibilidades.clear();
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from " + tabelanome);
			while(rstExiste.next()){
				disponibilidades.add(new Disponibilidade(rstExiste.getInt("Id"), rstExiste.getInt("DiaDaSemana"), rstExiste.getInt("IdProfissional"), rstExiste.getInt("IdEspecialidade"), rstExiste.getTime("Inicio"), rstExiste.getTime("Fim"), rstExiste.getInt("ativo")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Disponibilidade getById(int id) {
		listaRefresh();
		for (int key = 0; key < disponibilidades.size(); key++ ) {
			Disponibilidade esta = disponibilidades.get(key); 
			if(esta.getId() == id)
				return esta;
		}
		return null;
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
	public static void insertDisp(int resultado, Disponibilidade[] disponibilidades){
		try {
		//iterar entre dias da semana
		for(int j = 0; j< disponibilidades.length; j++){
			//insert
				myStm.executeUpdate("insert into disponibilidades " + 
					"(ativo, DiaDaSemana, Inicio, Fim, IdProfissional, IdEspecialidade) " + 
					"values(" + disponibilidades[j].getAtivo() + "," + disponibilidades[j].getDiaDaSemana() + ",'" + df.format(disponibilidades[j].getInicio()) + "','" + df.format(disponibilidades[j].getFim()) + "'," + resultado + "," + disponibilidades[j].getIdEspecialidade() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	public static void updateDisp(int idprof,  Disponibilidade[] alteradas){
		try {

			if(alteradas.length > 0)
				for(int a = 0; a < alteradas.length; a++){
					Disponibilidade atual = alteradas[a];
					
					//ver se esta desmarcando
						//procurar consultas nessa disponibilidade,
							//alertar desmarcacao
							//desmarcar
					//caso nao
						//procurar consultas nessa disponibilidade,
							//verificar se esta consulta ficará fora do horario novo
								//alertar desmarcacao
								//desmarcar
					Consulta[] conflitantes = ConsultasModel.getByDispo(atual.getId());
					if(!alteradas[a].getAtivoBool()){
						for(int h = 0; h < conflitantes.length; h++){ //procurar consultas nesta disponibilidade
							Consulta comparada =  conflitantes[h];
							if(comparada.getAtivoBool()){
								ControllerPrincipal.gritar("A " + comparada.toString() + " está maracda para uma disponibilidade inativa, então será desmarcada.","Consulta nesta disponibilidade");
								ConsultasModel.desmarcarConsulta(comparada.getId());
							}
						}
					}else{						
						for(int h = 0; h < conflitantes.length; h++){ //procurar consultas nesta disponibilidade
							Consulta comparada =  conflitantes[h];
							if(comparada.getAtivoBool()){
								if((comparada.getInicio().before(atual.getInicio())) || (comparada.getFim().after(atual.getFim()))){
									ControllerPrincipal.gritar("A " + comparada.toString() + " está maracda para um horário que ficará fora de disponibildiade, então será desmarcada.","Consulta nesta disponibilidade");
									ConsultasModel.desmarcarConsulta(comparada.getId());
								}
							}
						}
					}
					//fazer alteracao
					myStm.executeUpdate("update "+ tabelanome + " " +
							"set ativo=" + alteradas[a].getAtivo() + ", DiaDaSemana=" + alteradas[a].getDiaDaSemana()+ ",  Inicio='" + alteradas[a].getInicio()+ "', Fim='" + alteradas[a].getFim()+ "', IdProfissional=" + idprof + ", IdEspecialidade=" + alteradas[a].getIdEspecialidade()+ " "+ 
							"where Id=" + alteradas[a].getId() + " ");

					
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public static class Disponibilidade{
		int id,diaDaSemana,idProfissional,idEspecialidade, ativo = 1;
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
		
		public Disponibilidade(int id, int diaDaSemana, int idProfissional, int idEspecialidade, Time inicio, Time fim, int ativo){
			this(id, diaDaSemana,idProfissional,idEspecialidade, inicio, fim);
			this.ativo = ativo;
		} 
		public Disponibilidade(int id, int diaDaSemana, int idProfissional, int idEspecialidade, Time inicio, Time fim, boolean ativo){
			this(id, diaDaSemana,idProfissional,idEspecialidade, inicio, fim);
			if(ativo) this.ativo = 1;
			else this.ativo = 0;
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
		public Disponibilidade(int diaDaSemana,  int idEspecialidade, Date inicio, Date fim, int ativo) {
			this(diaDaSemana, idEspecialidade,inicio,fim);
			this.ativo = ativo;
		}
		public Disponibilidade(int diaDaSemana,  int idEspecialidade, Date inicio, Date fim, boolean ativo) {
			this(diaDaSemana, idEspecialidade,inicio,fim);
			if(ativo) this.ativo = 1;
			else this.ativo = 0;
			
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
		public void setInicio(Date inicio) {
			this.inicio = new Time(inicio.getHours(), inicio.getMinutes(), inicio.getSeconds());
		}
		public Time getFim() {
			return fim;
		}
		public void setFim(Time fim) {
			this.fim = fim;
		}
		public void setFim(Date fim) {
			this.fim = new Time(fim.getHours(), fim.getMinutes(), fim.getSeconds());
		}
		public void setAtivo(boolean a){
			if(a) ativo = 1;
			else ativo = 0;
		}
		public boolean getAtivoBool(){
			if(ativo > 0) return true;
			return false;
		}
		public void setAtivo(int a){
			this.ativo = a;
		}
		public int getAtivo(){
			return this.ativo;
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
