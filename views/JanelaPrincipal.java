package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuBar;




public class JanelaPrincipal extends JFrame{
	
	/**
	 * serial que eu nao sei pra queue serve
	 */
	private static final long serialVersionUID = 1L;

	//variaveios de container
	private Container cntForm;
	
	//variaveis de painel principal
	private JPanel pnlBotoes = new JPanel();
	private FlowLayout flwBotoes = new FlowLayout(FlowLayout.LEFT); 
	
	//variaveis de painel de states
	private JPanel areaStates = new JPanel(new CardLayout()); 
	
	
	//variaveis de menu
	private JMenu mnuLogin   = new JMenu ("Login"),	
	mnuAdm  = new JMenu("Funcionarios administrativos"),
	mnuSaude  = new JMenu("Profissionais da saude"),
	mnuAgConsultas = new JMenu("Agendamento de Consultas"),
	mnuRgConsultas = new JMenu("Registro de Consultas");
	JMenuBar mbarMain = new JMenuBar();
	private JMenuItem 	smnuAdmListar = new JMenuItem("Buscar"),
						smnuAdmIn = new JMenuItem("Excluir"),
						smnuSaudeListar = new JMenuItem("Buscar"),
						smnuSaudeIn = new JMenuItem("Excluir");
	
						
	
	
	
	public JanelaPrincipal(){
		
		//montando janela
		super("Clinica maligna");
		this.setSize(1200,800);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.addWindowListener(fechar());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cntForm = this.getContentPane();
		cntForm.setLayout (new BorderLayout());
	
		
		//montando paineis
		pnlBotoes.setLayout (flwBotoes);
		cntForm.add (pnlBotoes,  BorderLayout.NORTH);
		cntForm.add (areaStates,  BorderLayout.CENTER);
		


		
		//montando menus
		
		mbarMain.add(mnuLogin);
		
			mnuAdm.add(smnuAdmListar);
			mnuAdm.add(smnuAdmIn);
		mbarMain.add(mnuAdm);
		
			mnuSaude.add(smnuSaudeListar);
			mnuSaude.add(smnuSaudeIn);
		mbarMain.add(mnuSaude);
		
		mbarMain.add(mnuAgConsultas);
		mbarMain.add(mnuRgConsultas);
		pnlBotoes.add(mbarMain);
		
		
		
		//pack
		this.pack();
		
		
	}
	
	/*
	 * operacoes coma janela
	 * */
	private FechamentoDeJanela fechar(){
		return new FechamentoDeJanela();
	}
	
	private class FechamentoDeJanela extends WindowAdapter{
		public void windowClosing (WindowEvent e)
		{
			System.exit(0);
		}
	}
	
	public void definirLogado() {

		
	}
	public void definirDeslogado() {
		mnuAdm.setVisible(false);
		mnuSaude.setVisible(false);
		mnuAgConsultas.setVisible(false);
		mnuRgConsultas.setVisible(false);
		
	}
	
	
	/*
	 * relacionada a area de states
	 * */
	public JPanel getAreaStates(){
		return areaStates;
	}
	public void limparArea(){
		areaStates.removeAll(); 
		areaStates.updateUI();
	}
	
	

}
