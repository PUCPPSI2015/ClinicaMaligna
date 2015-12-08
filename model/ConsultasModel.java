package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;






import java.util.Calendar;

import model.PacientesModel.Paciente;
import model.harddata.Especialidades;
import model.harddata.Especialidades.Especialidade;





public class ConsultasModel extends Model{
	private static ArrayList<Consulta> consultas = new ArrayList<Consulta>();
	private static String tabelanome = "consultas";
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat uf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Calendar cal = Calendar.getInstance();
	
	public static java.util.Date formatarCompleta(String sr){
		try {
			
			return uf.parse(sr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void listaRefresh() {
		consultas.clear();
		ResultSet rstExiste;
		try {
			rstExiste = myStm.executeQuery("select * from " + tabelanome + " order by Data ASC, Inicio ASC");
			while(rstExiste.next()){
				consultas.add(new Consulta(rstExiste.getInt("Id"), 
											rstExiste.getInt("IdPaciente"), 
											rstExiste.getInt("IdDisponibilidade"), 
											rstExiste.getString("Observacoes"),
											rstExiste.getString("PedidosDeExame"),
											rstExiste.getString("Prescricoes"),
											rstExiste.getString("Recomendacoes"),
											rstExiste.getTime("Inicio"),
											rstExiste.getTime("Fim"),
											rstExiste.getDate("Data"),
											rstExiste.getInt("ativo")
											)
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Consulta[] getAll(int id) {
		listaRefresh();
		Consulta[] retorno = new Consulta[consultas.size()];
		
		if(consultas.size() > 0)//converter para array
			for (int j = 0; j < consultas.size(); j++ ) {
			
				retorno[j] = consultas.get(j);
			}
		return retorno;	//retornar
	}
	public static Consulta[] getAllAtivas(int id) {
		listaRefresh();
		ArrayList<Consulta> estasconsultas = new ArrayList<Consulta>();
		for (int key = 0; key < consultas.size(); key++ ) {
			Consulta esta = consultas.get(key); 
			if(esta.getAtivoBool()){
					estasconsultas.add(esta);
			}
				
		}
		
		Consulta[] retorno = new Consulta[estasconsultas.size()];
		
		if(estasconsultas.size() > 0)
			for (int j = 0; j < estasconsultas.size(); j++ ) {
			
				retorno[j] = estasconsultas.get(j);
			}
		return retorno;	
	}
	public static Consulta getOne(int id) {
		listaRefresh();
		for (int key = 0; key < consultas.size(); key++ ) {
			Consulta esta = consultas.get(key); 
			if(esta.getId() == id)
				return esta;
		}
		return null;
	}
	public static Consulta[] getByDispoData(int disp, Date data){
		listaRefresh();
		ArrayList<Consulta> estasconsultas = new ArrayList<Consulta>();
		for (int key = 0; key < consultas.size(); key++ ) {
			Consulta esta = consultas.get(key); 
			if((esta.getIdDisponibilidade() == disp) && (data.equals(esta.getData()))){
					estasconsultas.add(esta);
			}
				
		}
		
		Consulta[] retorno = new Consulta[estasconsultas.size()];
		
		if(estasconsultas.size() > 0)
			for (int j = 0; j < estasconsultas.size(); j++ ) {
			
				retorno[j] = estasconsultas.get(j);
			}
		return retorno;	
	}
	public static Consulta[] getByDispo(int disp){
		listaRefresh();
		ArrayList<Consulta> estasconsultas = new ArrayList<Consulta>();
		for (int key = 0; key < consultas.size(); key++ ) {
			Consulta esta = consultas.get(key); 
			if(esta.getIdDisponibilidade() == disp){
					estasconsultas.add(esta);
			}
				
		}
		
		Consulta[] retorno = new Consulta[estasconsultas.size()];
		
		if(estasconsultas.size() > 0)
			for (int j = 0; j < estasconsultas.size(); j++ ) {
			
				retorno[j] = estasconsultas.get(j);
			}
		return retorno;	
	}
	public static Consulta[] getByPac(int pac){
		listaRefresh();
		ArrayList<Consulta> estasconsultas = new ArrayList<Consulta>();
		for (int key = 0; key < consultas.size(); key++ ) {
			Consulta esta = consultas.get(key); 
			if(esta.getIdPaciente() == pac){
					estasconsultas.add(esta);
			}
				
		}
		
		Consulta[] retorno = new Consulta[estasconsultas.size()];
		
		if(estasconsultas.size() > 0)
			for (int j = 0; j < estasconsultas.size(); j++ ) {
			
				retorno[j] = estasconsultas.get(j);
			}
		return retorno;	
	}
	public static Consulta[] getAtivasPassadasByProf(int id) {
		java.util.Date hoje = formatarCompleta(uf.format(cal.getTime())); //montar data de hoje com horario até os segundo
		listaRefresh();
		ArrayList<Consulta> estasconsultas = new ArrayList<Consulta>();
		for (int key = 0; key < consultas.size(); key++ ) {   //procurar em todas as consultas
			Consulta esta = consultas.get(key); 
			Time horario = esta.getFim();
			Date dia = esta.getData();
			String shorario = df.format(horario);
			String sdia = dt.format(dia);
			java.util.Date daConsulta = formatarCompleta(sdia + " " + shorario);
			int idprof = DisponibilidadesModel.getById(esta.getIdDisponibilidade()).getIdProfissional(); 
			
			
			if	( esta.getAtivoBool() &&
				 (idprof == id) &&
				 (daConsulta.before(hoje))
				){ //se for ativa, e id do profissional procurado e for antes da data de hoje
					estasconsultas.add(esta); //adicionar
			}
				
		}
		
		Consulta[] retorno = new Consulta[estasconsultas.size()];
		
		if(estasconsultas.size() > 0)//converter para array
			for (int j = 0; j < estasconsultas.size(); j++ ) {
			
				retorno[j] = estasconsultas.get(j);
			}
		return retorno;	//retornar
		
		
		

	}
	public static Consulta[] getAtivasFuturasByProf(int id) {
		java.util.Date hoje = formatarCompleta(uf.format(cal.getTime())); //montar data de hoje com horario até os segundo
		listaRefresh();
		ArrayList<Consulta> estasconsultas = new ArrayList<Consulta>();
		System.out.println("Hoje: " + uf.format(cal.getTime()));
		for (int key = 0; key < consultas.size(); key++ ) {   //procurar em todas as consultas
			Consulta esta = consultas.get(key); 
			Time horario = esta.getFim();
			Date dia = esta.getData();
			String shorario = df.format(horario);
			String sdia = dt.format(dia);
			java.util.Date daConsulta = formatarCompleta(sdia + " " + shorario);
			System.out.println("Dia: " + sdia + " " + shorario);
			System.out.print("Ativa: ");
			int idprof = DisponibilidadesModel.getById(esta.getIdDisponibilidade()).getIdProfissional(); 
			
			
			if	( esta.getAtivoBool() &&
				 (idprof == id) &&
				 (daConsulta.after(hoje) || daConsulta.equals(hoje))
				){ //se for ativa, e id do profissional procurado e for antes da data de hoje
					estasconsultas.add(esta); //adicionar
			}
				
		}
		
		Consulta[] retorno = new Consulta[estasconsultas.size()];
		
		if(estasconsultas.size() > 0)//converter para array
			for (int j = 0; j < estasconsultas.size(); j++ ) {
			
				retorno[j] = estasconsultas.get(j);
			}
		return retorno;	//retornar

		
	}
	public static void updateConsultaParcial(int id, Date d, Time i, Time f, int p, int dd){
		
		try {
			myStm.executeUpdate("update "+ tabelanome + " " +
					"set Data='" + d + "', Inicio='" + i + "', Fim='" + f+ "', IdPaciente='" + p + "', "+
					"IdDisponibilidade='" + dd+ "' "+
					"where Id=" + id + " ");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void updateConsultaMeta(int id, String observacoes, String Exames, String prescri, String recomendacoes){
		
		try {
			myStm.executeUpdate("update "+ tabelanome + " " +
					"set Observacoes='" + observacoes + "', PedidosDeExame='" + Exames + "', Prescricoes='" + prescri + "', Recomendacoes='" + recomendacoes + "' "+
					"where Id=" + id + " ");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void novaConsultaParcial(Date d, Time i, Time f, int p, int dd){
		
		//criar consulta
		String insercao = 	"insert into "+ tabelanome + " " +
				"(Data, Inicio, Fim, IdPaciente, IdDisponibilidade) " +
				"values ('" + d + "', '" + i + "', '" + f + "', " + p + ", " + dd + " )";
		
		try {
			myStm.executeUpdate(insercao);
			listaRefresh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void excluirConsulta(int id) {
		String deletacao =  "delete from "+ tabelanome + " " +
				"where Id=" + id + "";
		try {
			myStm.executeUpdate(deletacao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void desmarcarConsulta(int id){
		
		try {
			myStm.executeUpdate("update "+ tabelanome + " " +
					"set ativo=0 " +
					"where Id=" + id + " ");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void marcarConsulta(int id){
		
		try {
			myStm.executeUpdate("update "+ tabelanome + " " +
					"set ativo=1 " +
					"where Id=" + id + " ");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Consulta[] getPesquisaPac(String pesquisa) {
		Paciente[] paciente = PacientesModel.getPesquisa(pesquisa);//pegar pacientes coma pesquisa
		Consulta[] retorno = null;
		for(int pa = 0; pa < paciente.length; pa++){//iterar em cada um deles
			
			retorno = getByPac(paciente[pa].getId());
	
		}
		return retorno;
		
	}
	
	
	
	
	public static class Consulta{
		int id, idPaciente, idDisponibilidade, ativo;
		String observacoes, pedidosExame, prescricoes, recomendacoes;
		Time inicio, fim;
		Date data;
		public Consulta(int id, int idPaciente, int idDisponibilidade,
				Time inicio, Time fim, Date data) {
			super();
			this.id = id;
			this.idPaciente = idPaciente;
			this.idDisponibilidade = idDisponibilidade;
			this.inicio = inicio;
			this.fim = fim;
			this.data = data;
		}
		public Consulta(int id, int idPaciente, int idDisponibilidade, Time inicio, Time fim, Date data, int ativo){
			this( id,idPaciente, idDisponibilidade, inicio, fim, data);
			this.ativo = ativo;
		}
		public Consulta(int id, int idPaciente, int idDisponibilidade, Time inicio, Time fim, Date data, boolean ativo){
			this( id,idPaciente, idDisponibilidade, inicio, fim, data);
			if(ativo) this.ativo = 1;
			else this.ativo = 0;
		}
		public Consulta(int id, int idPaciente, int idDisponibilidade, String observacoes, String pedidosExame, String prescricoes, String recomendacoes, Time inicio, Time fim, Date data) {
			this(id,idPaciente, idDisponibilidade, inicio, fim, data);
			this.observacoes = observacoes;
			this.pedidosExame = pedidosExame;
			this.prescricoes = prescricoes;
			this.recomendacoes = recomendacoes;
		}
		public Consulta(int id, int idPaciente, int idDisponibilidade, String observacoes, String pedidosExame, String prescricoes, String recomendacoes, Time inicio, Time fim, Date data, int ativo) {
			this(id, idPaciente, idDisponibilidade, observacoes, pedidosExame, prescricoes, recomendacoes, inicio, fim, data);
			this.ativo = ativo;
		}
		public Consulta(int id, int idPaciente, int idDisponibilidade, String observacoes, String pedidosExame, String prescricoes, String recomendacoes, Time inicio, Time fim, Date data, boolean ativo) {
			this(id, idPaciente, idDisponibilidade, observacoes, pedidosExame, prescricoes, recomendacoes, inicio, fim, data);
			if(ativo) this.ativo = 1;
			else this.ativo = 0;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getIdPaciente() {
			return idPaciente;
		}
		public void setIdPaciente(int idPaciente) {
			this.idPaciente = idPaciente;
		}
		public int getIdDisponibilidade() {
			return idDisponibilidade;
		}
		public void setIdDisponibilidade(int idDisponibilidade) {
			this.idDisponibilidade = idDisponibilidade;
		}
		public int getAtivo() {
			return ativo;
		}
		public boolean getAtivoBool() {
			return (ativo != 0);
		}
		public void setAtivo(int ativo) {
			this.ativo = ativo;
		}
		public String getObservacoes() {
			return observacoes;
		}
		public void setObservacoes(String observacoes) {
			this.observacoes = observacoes;
		}
		public String getPedidosExame() {
			return pedidosExame;
		}
		public void setPedidosExame(String pedidosExame) {
			this.pedidosExame = pedidosExame;
		}
		public String getPrescricoes() {
			return prescricoes;
		}
		public void setPrescricoes(String prescricoes) {
			this.prescricoes = prescricoes;
		}
		public String getRecomendacoes() {
			return recomendacoes;
		}
		public void setRecomendacoes(String recomendacoes) {
			this.recomendacoes = recomendacoes;
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
		public Date getData() {
			return data;
		}
		public void setData(Date data) {
			this.data = data;
		}
		public String toString(){
			String aStr;
			if(ativo == 0) aStr = "desmarcada";
			else aStr = "marcada";
			return "Consulta " + aStr + " " + PacientesModel.getById(idPaciente).toString() +" : " + Especialidades.getOne( DisponibilidadesModel.getById(idDisponibilidade).getIdEspecialidade() ).toString();
		}
		
	}



	


	
	



	


	


	
}
