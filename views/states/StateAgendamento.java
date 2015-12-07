package views.states;


import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import controllers.ControllerAgendamento;
import model.ConsultasModel.Consulta;
import model.PacientesModel.Paciente;
import model.dbos.ProfSaude;
import views.JanelaPrincipal;
import views.Calendario;

public class StateAgendamento extends State{
	JLabel lblEscolhaUmProfissional = new JLabel("Escolha um profissional");
	JComboBox cbxProfissional = new JComboBox<ProfSaude>(ControllerAgendamento.getAllProf());
	Calendario pnlCalendar = new Calendario();
	JPanel area1 = new JPanel();
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	
	//area2
	JPanel area2 = new JPanel();
	JLabel lblConsultasMaracadasPara = new JLabel("Consultas maracadas:");
	JList lstConsultas = new JList();
	JScrollPane scrConsultas = new JScrollPane();
	JButton btnMarcarNovaConsulta = new JButton("Marcar nova consulta");
	
	//area3
	JPanel area3 = new JPanel();
	JLabel lblEsp = new JLabel("Especialidade");
	JLabel label = new JLabel("Paciente:");
	JLabel lblProf = new JLabel("Profissional");
	JLabel lblDInicio = new JLabel("");
	JLabel lblDFim = new JLabel("");
	JLabel lblAtivo = new JLabel("");
	
	
	JComboBox<Paciente> cbxPac = new JComboBox<Paciente>(ControllerAgendamento.getAllPac());
	MeuSpiner 	tmIn = new MeuSpiner(), 
				tmOut = new MeuSpiner();
	JButton btnSalvar = new JButton("Salvar"),
			btnDesmarcar = new JButton("Desmarcar"),
			btnMarcar = new JButton("Marcar"),
			btnExcluir = new JButton("Excluir");
	
	Consulta editando = null;
	
	
	

	protected StateAgendamento(JanelaPrincipal janela_) {
		super(janela_);
		
		//myp
		myPainel.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		myPainel.setBackground(Color.WHITE);
		
		/*
		 * Area 1
		 * 
		 * */
		area1.setBorder(null);
		area1.setBackground(Color.WHITE);
		area1.setBounds(0, 0, 397, 734);
		
		lblEscolhaUmProfissional.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblEscolhaUmProfissional.setHorizontalAlignment(SwingConstants.LEFT);
		lblEscolhaUmProfissional.setBounds(10, 28, 148, 20);
		
		cbxProfissional.addFocusListener(ControllerAgendamento.cbxExpand(cbxProfissional));
		cbxProfissional.addActionListener(ControllerAgendamento.cbxChange(cbxProfissional, pnlCalendar));
		cbxProfissional.setBounds(10, 54, 377, 20);
		
		cbxProfissional.setSelectedIndex(0);
		
		pnlCalendar.getCalendarTable().addMouseListener(ControllerAgendamento.calendarSel( pnlCalendar));
		pnlCalendar.setBounds(10, 92, 377, 354);
		pnlCalendar.setBackground(Color.WHITE);
		
		area1.setLayout(null);
		area1.add(pnlCalendar);
		area1.add(cbxProfissional);
		area1.add(lblEscolhaUmProfissional);
		myPainel.add(area1);
		
		/*
		 * Area 2
		 * 
		 * */
		area2.setBounds(403, 0, 468, 723);
		area2.setBorder(null);
		area2.setBackground(Color.WHITE);
		
	
		lblConsultasMaracadasPara.setBounds(10, 32, 148, 20);
		lblConsultasMaracadasPara.setHorizontalAlignment(SwingConstants.LEFT);
		lblConsultasMaracadasPara.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		
		lstConsultas.addListSelectionListener(ControllerAgendamento.mudouLista());
		scrConsultas.setBounds(10, 70, 371, 375);
		scrConsultas.setViewportView(lstConsultas);
		scrConsultas.setBorder(BorderFactory.createEmptyBorder(00,00,00,00));
		
		btnMarcarNovaConsulta.setBounds(172, 33, 135, 23);
		btnMarcarNovaConsulta.addActionListener(ControllerAgendamento.btnNovo(pnlCalendar));
		area2.add(btnMarcarNovaConsulta);
		
		area2.setLayout(null);
		area2.add(scrConsultas);
		area2.add(lblConsultasMaracadasPara);
		myPainel.add(area2);
		
		
		/*
		 * Area 3
		 * 
		 * */
		area3.setBounds(877, 0, 397, 723);
		area3.setBorder(null);
		area3.setBackground(Color.WHITE);
		
		
		lblEsp.setBounds(10, 67, 148, 20);
		lblEsp.setHorizontalAlignment(SwingConstants.LEFT);
		lblEsp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		area3.setLayout(null);
		area3.add(lblEsp);
		
		
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		label.setBounds(10, 98, 148, 20);
		area3.add(label);
		
		
		lblProf.setHorizontalAlignment(SwingConstants.LEFT);
		lblProf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblProf.setBounds(10, 36, 148, 20);
		area3.add(lblProf);
		
		
		cbxPac.setBounds(10, 122, 377, 34);
		area3.add(cbxPac);
		
		
		tmIn.setBounds(10, 167, 124, 26);
		area3.add(tmIn);
		
		lblDInicio.setBounds(144, 167, 184, 26);
		area3.add(lblDInicio);
		
		
		tmOut.setBounds(10, 203, 124, 26);
		area3.add(tmOut);
		
		lblDFim.setBounds(144, 203, 184, 26);
		area3.add(lblDFim);
		
		
		lblAtivo.setBounds(10, 240, 184, 34);
		area3.add(lblAtivo);
	
		
		
		btnSalvar.setBounds(10, 280, 124, 34);
		btnSalvar.addActionListener(ControllerAgendamento.btnSalvar());
		area3.add(btnSalvar);
		
		
		btnDesmarcar.setBounds(144, 280, 100, 34);
		btnDesmarcar.setForeground(Color.RED);
		btnDesmarcar.addActionListener(ControllerAgendamento.btnDesmarcar());
		area3.add(btnDesmarcar);
		
		btnMarcar.setBounds(144, 280, 100, 34);
		btnMarcar.addActionListener(ControllerAgendamento.btnMarcar());
		btnMarcar.setVisible(false);
		area3.add(btnMarcar);
		
		
		btnExcluir.setBounds(254, 280, 84, 34);
		btnExcluir.setForeground(Color.RED);
		btnExcluir.addActionListener(ControllerAgendamento.btnExcluir());
		area3.add(btnExcluir);
		
		
		myPainel.add(area3);
		
		
		myPainel.setLayout(null);
		
		ControllerAgendamento.start(this);
		area3.setVisible(false);
		

	}
	
	
	public JComboBox getCbxPro(){
		return cbxProfissional;
	}
	public JComboBox getCbxPac(){
		return cbxPac;
	}
	public void fillConsultas(Consulta[] consultas){
		lstConsultas.setListData(consultas);
	}
	public void updateA3(Consulta c, String prof, String Esp, Paciente pac, Comparable inicio, Comparable fim, Comparable cinicio, Comparable cfim, boolean ehNovo){
		area3.setVisible(true);
		lblProf.setText(prof);
		lblEsp.setText(Esp);
		/*((SpinnerDateModel) tmOut.getModel()).setStart(inicio);
		((SpinnerDateModel) tmOut.getModel()).setEnd(fim);
		((SpinnerDateModel) tmIn.getModel()).setStart(inicio);
		((SpinnerDateModel) tmIn.getModel()).setEnd(fim);*/
		cbxPac.setSelectedItem(pac);
		
		lblDInicio.setText("Horario de entrada: " + df.format(inicio));
		lblDFim.setText("Horario de Saída: " + df.format(fim));
		
		tmIn.setValue(cinicio);
		tmOut.setValue(cfim);
		editando = c;		
		
		lblAtivo.setVisible(!ehNovo);
		btnDesmarcar.setVisible(!ehNovo);
		btnMarcar.setVisible(!ehNovo);
		btnExcluir.setVisible(!ehNovo);
		
		
		//ativo ou nao
		if(!ehNovo){
			if(c.getAtivoBool()){
				lblAtivo.setText("Consulta marcada");
			}
			else{
				lblAtivo.setText("Consulta desmarcada");
			} 
			btnDesmarcar.setVisible(c.getAtivoBool());
			btnMarcar.setVisible(!c.getAtivoBool());
		}
		
		
	}
	public void updateA3(){
		editando = null;
		lblAtivo.setText("");
		area3.setVisible(false);
	}
	public static class MeuSpiner extends JSpinner{
		private static final long serialVersionUID = 1L;
		private JSpinner.DateEditor timedin;
		public MeuSpiner(){
			super(new SpinnerDateModel());
			timedin = new JSpinner.DateEditor(this, "HH:mm:ss");
			this.setEditor(timedin);
			this.setValue(new Date(0, 0, 0));
		}
		public void reseta(){
			this.setValue(new Date(0, 0, 0));
		}

	}
	public Consulta getEditando(){
		return this.editando;
	}
	public MeuSpiner getTmIn(){
		return tmIn;
	}
	public MeuSpiner getTmOut(){
		return tmOut;
	}


	public JList<Consulta> getListaConsultas() {
		return lstConsultas;
	}
}
