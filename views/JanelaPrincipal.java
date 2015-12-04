package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuBar;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTabbedPane;

import controllers.*;


import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;




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
	private JButton mnuInicio  = new MeuBtn("Inicio"),
					mnuAdm  = new MeuBtn("Funcionarios administrativos"),
					mnuSaude  = new MeuBtn("Profissionais da saude"),
					mnuAgConsultas = new MeuBtn("Agendamento de Consultas"),
					mnuRgConsultas = new MeuBtn("Registro de Consultas"),
					mnuSair  = new MeuBtn("Sair");

	

						
	
	
	
	public JanelaPrincipal(){
		
		//montando janela
		super("Clinica maligna");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JanelaPrincipal.class.getResource("/material/icon.png")));
		this.setSize(1200,800);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.addWindowListener(fechar());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cntForm = this.getContentPane();
		cntForm.setLayout (new BorderLayout());
	
	
	
		
		//montando paineis
		pnlBotoes.setLayout (flwBotoes);
		cntForm.add (pnlBotoes,  BorderLayout.NORTH);
		
		
		areaStates.setBackground(new Color(255, 0, 0));
		cntForm.add (areaStates,  BorderLayout.CENTER);
		

		
		
		
		
		
		
		//montando os botoes
		mnuInicio.addActionListener(ControllerPrincipal.mudador("inicio"));
		mnuAdm.addActionListener(ControllerPrincipal.mudador("funcionarios administrativos"));
		mnuSaude.addActionListener(ControllerPrincipal.mudador("profissionais saude"));
		mnuAgConsultas.addActionListener(ControllerPrincipal.mudador("agendamento"));
		mnuSair.addActionListener(ControllerPrincipal.mudador("sair"));
		
		pnlBotoes.add(mnuInicio);
		pnlBotoes.add(mnuAdm);
		pnlBotoes.add(mnuSaude);
		pnlBotoes.add(mnuAgConsultas);
		pnlBotoes.add(mnuRgConsultas);
		pnlBotoes.add(mnuSair);
		pnlBotoes.setBackground(Color.WHITE);
		
		
		
		//pack
		this.pack();
		
		
	}
	/*
	 * inner class meu btn
	 */
	private class MeuBtn extends JButton{
		private Color hoverBackgroundColor;
        private Color pressedBackgroundColor;

		public MeuBtn(String nome){
			super(nome);
			this.setBackground(new Color(255, 255, 255));
			this.setForeground(new Color(255, 0, 0));
			this.setBorderPainted(false);
		}
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
		pnlBotoes.setVisible(true);

		
	}
	public void definirDeslogado() {
		pnlBotoes.setVisible(false);

		
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
