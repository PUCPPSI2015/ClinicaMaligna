package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.ControllerProfSaude;
import views.states.StateProfSaude;


public class AdicionarJanelaProf  extends JFrame{
	private Container cntForm ;
	private static JPanel pnlEditorMeu;
	private static JTextField txtNome, txtdfsd,  txtLogin;
	private static JLabel lblNome, lblCargo, lblSenha, lblLogin;
	private static JButton btnGerarNovaSenha, btnSalvar, btnExcluir;
	private StateProfSaude s;
	public AdicionarJanelaProf(StateProfSaude state){
		super("Adicionar funcionario Administrativo");
		this.s = state; 
		this.setSize(450,500);
		cntForm = this.getContentPane();
		cntForm.setLayout (new BorderLayout());
		
		pnlEditorMeu = new JPanel();
		pnlEditorMeu.setPreferredSize(new Dimension(300, 500));

		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 11, 27, 14);
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtNome = new JTextField();
		txtNome.setBounds(86, 11, 168, 37);
		txtNome.setColumns(10);
		
		lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(10, 54, 29, 14);
		

		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 105, 30, 14);
		
		lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 137, 25, 14);
		
		txtdfsd = new JTextField();
		txtdfsd.setBounds(84, 95, 86, 34);


		txtdfsd.setColumns(10);
		
		btnGerarNovaSenha = new JButton("Gerar nova senha");
		btnGerarNovaSenha.setBounds(176, 97, 119, 31);
		btnGerarNovaSenha.addActionListener(ControllerProfSaude.btnNovaSenhaJanela(this));
		
		txtLogin = new JTextField();
		txtLogin.setBounds(84, 137, 86, 34);
		txtLogin.setEditable(false);
		txtLogin.setHorizontalAlignment(SwingConstants.CENTER);
		txtLogin.setColumns(10);
		
		btnSalvar = new JButton("Salvar novo");
		btnSalvar.setBounds(10, 205, 110, 40);
		btnSalvar.addActionListener(ControllerProfSaude.btnSalvarNovo(this, this.s));
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(130, 205, 110, 40);
		btnExcluir.setForeground(Color.RED);
		btnExcluir.addActionListener(ControllerProfSaude.btnExcluir(s));
		
		pnlEditorMeu.setLayout(null);
		pnlEditorMeu.add(lblLogin);
		pnlEditorMeu.add(btnSalvar);
		pnlEditorMeu.add(btnExcluir);
		pnlEditorMeu.add(lblCargo);
		pnlEditorMeu.add(lblSenha);
		pnlEditorMeu.add(txtdfsd);
		pnlEditorMeu.add(btnGerarNovaSenha);
		pnlEditorMeu.add(txtLogin);
		pnlEditorMeu.add(lblNome);
		pnlEditorMeu.add(txtNome);

		cntForm.add(pnlEditorMeu, BorderLayout.WEST);
		this.setVisible(true);
	}
	public void setSenha(String s){
		txtdfsd.setText(s);
	}
	public String getNome(){
		return txtNome.getText();
	}

	public String getSenha(){
		return txtdfsd.getText();
	}
	
}




