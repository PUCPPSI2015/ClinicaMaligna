package views.states;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controllers.ControllerFuncAdmin;
import views.JanelaPrincipal;
import model.*;
import model.Cargos.Cargo;

public class StateFuncAdmin extends InternalState{
	
	//criando botoes
	private JButton btnAll = new JButton("Ver todos");
	private JButton btnAdd = new JButton("Adicionar novo");
	private JButton btnFind = new JButton("Pesquisar");
	
	//criando campo pesquisa
	private JTextField txtFind = new JTextField(52);
	
	//meus elementos do friedman
	private static JPanel pnlEditorMeu = new JPanel();
	private static JTextField txtNome, txtdfsd,  txtLogin;
	private static JLabel lblNome, lblCargo, lblSenha, lblLogin;
	private static JComboBox<Cargo> comboBox;
	private static JButton btnGerarNovaSenha, btnSalvar, btnExcluir;
	
	public StateFuncAdmin(JanelaPrincipal janela_) {
		super(janela_);
		this.setTitle("Funcionarios Administrativos");
		

		//montar botoes
		taskPanItems.add(btnAll);
		taskPanItems.add(btnAdd);
		taskPanItems.add(txtFind);
		taskPanItems.add(btnFind);
		
		preencherLista(ControllerFuncAdmin.getAll());
		
		//opcoes friedman
		criarFriedman();
		updateFriedman();
		
		//lista de resultados
		lstResults.addListSelectionListener(ControllerFuncAdmin.mudouLista(this));
	}
	public void criarFriedman(){

		pnlEditorMeu.setPreferredSize(new Dimension(300, 500));
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 11, 27, 14);
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtNome = new JTextField();
		txtNome.setBounds(86, 11, 168, 37);
		txtNome.setColumns(10);
		
		lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(10, 54, 29, 14);
		
		comboBox = new JComboBox<Cargo>(Cargos.getAll());
		comboBox.setBounds(84, 54, 211, 20);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 105, 30, 14);
		
		lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 137, 25, 14);
		
		txtdfsd = new JTextField();
		txtdfsd.setBounds(84, 95, 86, 34);
		txtdfsd.setEditable(false);
		txtdfsd.setText("25dfsd22");
		txtdfsd.setColumns(10);
		
		btnGerarNovaSenha = new JButton("Gerar nova senha");
		btnGerarNovaSenha.setBounds(176, 97, 119, 31);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(84, 137, 86, 34);
		txtLogin.setEditable(false);
		txtLogin.setHorizontalAlignment(SwingConstants.CENTER);
		txtLogin.setText("256");
		txtLogin.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(10, 205, 110, 40);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(130, 205, 110, 40);
		btnExcluir.setForeground(Color.RED);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		pnlEditorMeu.setLayout(null);
		pnlEditorMeu.add(lblLogin);
		pnlEditorMeu.add(btnSalvar);
		pnlEditorMeu.add(btnExcluir);
		pnlEditorMeu.add(lblCargo);
		pnlEditorMeu.add(lblSenha);
		pnlEditorMeu.add(txtdfsd);
		pnlEditorMeu.add(btnGerarNovaSenha);
		pnlEditorMeu.add(txtLogin);
		pnlEditorMeu.add(comboBox);
		pnlEditorMeu.add(lblNome);
		pnlEditorMeu.add(txtNome);
		pnlEditorMor.add(pnlEditorMeu);

	}
	public void updateFriedman(String nome, int cargo, String senha, int Id){
		txtNome.setText(nome);
		txtdfsd.setText(senha);
		txtLogin.setText("" + Id);
		System.out.println("Eu quero: " + cargo + " Achei no: " + Cargos.getCargoIndex(cargo));
		comboBox.setSelectedIndex(Cargos.getCargoIndex(cargo));
		pnlEditorMeu.setVisible(true);
	}
	public void updateFriedman(){
		//pnlEditorMeu.setVisible(false);
	}
	
	
	
	
}
