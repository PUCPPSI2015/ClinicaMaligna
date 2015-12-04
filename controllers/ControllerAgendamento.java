package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import model.EspecializacoesModel;
import model.ProfSaudeModel;
import model.dbos.ProfSaude;
import model.harddata.Especialidades;
import model.harddata.Especialidades.Especialidade;
import views.states.StateAgendamento;
import views.states.StateAgendamento.MeuSpiner;
import views.states.StateProfSaude;
import model.ConsultasModel;
import model.ConsultasModel.Consulta;
import model.DisponibilidadesModel;
import model.DisponibilidadesModel.Disponibilidade;
import model.PacientesModel;
import model.PacientesModel.Paciente;
import views.Calendario;


public class ControllerAgendamento {
	private static StateAgendamento state;
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static ProfSaude[] getAllProf(){
		
		return  ProfSaudeModel.getAll();
	}
	public static Paciente[] getAllPac(){
		return  PacientesModel.getAll();
	}
	public static void start(StateAgendamento s){
		state = s;
	}
	public static void preencheComboBoxPro(JComboBox<ProfSaude> cbx){
		cbx.removeAllItems();
		
		ProfSaude[] profs = getAllProf();
		for(int i = 0; i < profs.length; i++ ){
			cbx.addItem(profs[i]);
		}
		
	} 
	public static FocusAdapter cbxExpand(JComboBox cbx){
		
		return new FocusAdapter() {
			   @Override
			   public void focusGained(FocusEvent e) {
				   preencheComboBoxPro(cbx);
			      cbx.showPopup();
			   }
		};
	}

	public static ActionListener cbxChange(JComboBox cbx, Calendario c){
		return new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				//pegar prof selecionado
				ProfSaude prof = (ProfSaude)cbx.getSelectedItem();
				if(prof == null) return;
				//pegar suas disponibildiades
				DisponibilidadesModel.listaRefresh();
				Disponibilidade dom, seg, ter, qua, qui, sex, sab;
				dom = DisponibilidadesModel.getByProfAndDia(prof.getId(), 1);
				seg = DisponibilidadesModel.getByProfAndDia(prof.getId(), 2);
				ter = DisponibilidadesModel.getByProfAndDia(prof.getId(), 3);
				qua = DisponibilidadesModel.getByProfAndDia(prof.getId(), 4);
				qui = DisponibilidadesModel.getByProfAndDia(prof.getId(), 5);
				sex = DisponibilidadesModel.getByProfAndDia(prof.getId(), 6);
				sab = DisponibilidadesModel.getByProfAndDia(prof.getId(), 7);
				//ver se tem domingo segudna terca quarta
				c.ativarTudo();
				if(!dom.getAtivoBool()) c.desativar(0);
				if(!seg.getAtivoBool()) c.desativar(1);
				if(!ter.getAtivoBool()) c.desativar(2);
				if(!qua.getAtivoBool()) c.desativar(3);
				if(!qui.getAtivoBool()) c.desativar(4);
				if(!sex.getAtivoBool()) c.desativar(5);
				if(!sab.getAtivoBool()) c.desativar(6);
			}
		};
	}
	public static MouseAdapter calendarSel( Calendario c){
		return new java.awt.event.MouseAdapter() {
            JTable tabela = c.getCalendarTable();
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabela.rowAtPoint(evt.getPoint());
                int col = tabela.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                	//criar data
                	Integer dia = (Integer) tabela.getValueAt(row, col),
                			mes = c.getMes() + 1,
                			ano = c.getAno();
                	String strdata = "" + ano + "-" + mes + "-" +dia;
                	
                	Date data;
					try {
						data = new Date( (dt.parse(strdata)).getTime() );
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						data = null;
						e.printStackTrace();
					}
                	
	
                	//pegar disponibilidade com esse dia da semana e esse profissional
                	ProfSaude prof = (ProfSaude)state.getCbxPro().getSelectedItem();
                	Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(prof.getId(), col+1);
                	//pegar consultas com essa disponibilidade
                	Consulta[] consultas = ConsultasModel.getByDispoData(disp.getId(), data);
                	if(consultas.length > 0)
                		System.out.println(consultas[0]);
                	
                	//pegar consultas e listar na area2
                	state.fillConsultas(consultas);
                	
                	
                	//
                	
                    
                    
                }
            }
        };
	}
	public static java.util.Date formatar(MeuSpiner sp){
		try {
			return df.parse(df.format(sp.getValue()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void updateA3(Consulta c){
		int idDisponibilidade = c.getIdDisponibilidade();
		Disponibilidade d = DisponibilidadesModel.getById(idDisponibilidade);
		Especialidade e = Especialidades.getOne(d.getIdEspecialidade());
		ProfSaude p = ProfSaudeModel.getOne(d.getIdProfissional());
		Paciente pac = PacientesModel.getById(c.getIdPaciente());
		

		state.updateA3(c, p.getNome(), e.getNome(), pac, d.getInicio(), d.getFim(), c.getInicio(), c.getFim());
	}
	public static ListSelectionListener mudouLista(){
    	return new ListSelectionListener() {
    		public void valueChanged(ListSelectionEvent listSelectionEvent) {
    			if (!listSelectionEvent.getValueIsAdjusting()) {
    				
    				JList<Consulta> list = (JList) listSelectionEvent.getSource();
    				int selections[] = list.getSelectedIndices();
    				List<Consulta> selectionValues = list.getSelectedValuesList();
    				int sel = -1;
    				for (int i = 0, n = selections.length; i < n; i++) {
    					sel = selectionValues.get(i).getId();
    				}
    				if(sel == -1) return;
    				updateA3(ConsultasModel.getOne(sel));
    			}
    		}
    	};
    }
	 public static ActionListener btnSalvar(){
	
		return new SalvarAction();
	}

	private static class SalvarAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Consulta c = state.getEditando();
			java.util.Date inicio = formatar(state.getTmIn()); 
			java.util.Date fim = formatar(state.getTmOut());
			Time i;
			Time f;
			if(inicio.before(fim)){
				i =  new Time(inicio.getHours(), inicio.getMinutes(), inicio.getSeconds());
				f =  new Time(fim.getHours(), fim.getMinutes(), fim.getSeconds());	
			}else{
				f =  new Time(inicio.getHours(), inicio.getMinutes(), inicio.getSeconds());
				i =  new Time(fim.getHours(), fim.getMinutes(), fim.getSeconds());	
			}
			int paciente = ((Paciente) state.getCbxPac().getSelectedItem()).getId(); 
			
			ConsultasModel.updateConsultaParcial(c.getId(), c.getData(), i, f, paciente, c.getIdDisponibilidade());
			Consulta[] consultas = ConsultasModel.getByDispoData(c.getIdDisponibilidade(), c.getData());
			state.fillConsultas(consultas);
			state.updateA3();
		}

	}
	
}
