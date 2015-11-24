package views.states;

import javax.swing.JPanel;

import views.JanelaPrincipal;

import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;

public class InternalState extends State{
	
	protected JPanel taskPan = new JPanel();
	protected JPanel taskPanTitle = new JPanel();
	protected JPanel taskPanItems = new JPanel();
	protected String title;
	private final JLabel lblFuncionarios = new JLabel("Clinica maligna");
	
	protected JPanel resultPan = new JPanel();
	private final JSplitPane splResultados = new JSplitPane();
	protected JPanel marx = new JPanel();
	protected JPanel friedman = new JPanel();
	
		
	protected InternalState(JanelaPrincipal janela_) {
		super(janela_);
		//montando taskbar
		myPainel.setLayout(new BorderLayout(0, 0));
		
		
		
		
		taskPan.setBackground(new Color(245, 245, 245));
		taskPan.setLayout(new BorderLayout(0, 0));
		
		FlowLayout flowLayout = (FlowLayout) taskPanTitle.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		taskPan.add(taskPanTitle, BorderLayout.NORTH);
		lblFuncionarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		taskPanTitle.add(lblFuncionarios);
		
		FlowLayout flowLayout_1 = (FlowLayout) taskPanItems.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		taskPan.add(taskPanItems, BorderLayout.CENTER);
		
		
		
		myPainel.add(taskPan, BorderLayout.NORTH);
		
		
		
		
		
		resultPan.setBackground(new Color(245, 0, 245));
		resultPan.setLayout(new BorderLayout(0, 0));
		
		marx.setBackground(new Color(245, 25, 245));
		splResultados.setLeftComponent(marx);
		splResultados.setRightComponent(friedman);
		
		splResultados.setResizeWeight(.7d);
		splResultados.setEnabled( false );
		
		resultPan.add(splResultados);
		myPainel.add(resultPan, BorderLayout.CENTER);
				
		
	}
	public void setTitle(String novo){
		lblFuncionarios.setText(novo);
	}
	
	

}
