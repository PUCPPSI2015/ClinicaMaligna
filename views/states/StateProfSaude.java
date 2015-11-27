package views.states;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controllers.ControllerProfSaude;
import model.Cargos;
import model.Cargos.Cargo;
import views.JanelaPrincipal;

public class StateProfSaude extends InternalState{
	
	//criando botoes
	private JButton btnAdd = new JButton("Adicionar novo");
		
	//criando campo pesquisa
	private JTextField txtFind = new JTextField(52);
	
	//meus elementos do friedman
	private static JPanel pnlEditorMeu = new JPanel();

	private static JTextField 	txtNome = new JTextField(),
								txtdfsd = new JTextField(),
								txtLogin = new JTextField(),
								txtCpf = new JTextField();

	private static JLabel 	lblNome = new JLabel("Nome"),
							lblCPF = new JLabel("CPF"),
							lblSenha = new JLabel("Senha"),
							lblLogin = new JLabel("Login"),
							lblDisponibilidades = new JLabel("Disponibilidades"),
							lblEspecializacoes = new JLabel("Especializacoes");

	private static JButton 	btnGerarNovaSenha = new JButton("Gerar nova senha"),
							btnSalvar = new JButton("Salvar"),
							btnExcluir = new JButton("Excluir");
	

	private static JCheckBox chckbxDomingo = new JCheckBox("Domingo"),
							 chckbxSegunda  = new JCheckBox("Segunda"),
							 chckbxTera = new JCheckBox("Ter\u00E7a"),
							 chckbxSexta = new JCheckBox("Sexta"),
							 chckbxQuarta = new JCheckBox("Quarta"),
							 chckbxQuinta = new JCheckBox("Quinta"),
							 chckbxSbado = new JCheckBox("S\u00E1bado");

	private static JSpinner tmOutTer = new MeuSpiner(),
							tmInTer = new MeuSpiner(),
							tmOutQua = new MeuSpiner(),
							tmInQua = new MeuSpiner(),
							tmOutQui = new MeuSpiner(),
							tmInQui = new MeuSpiner(),
							tmInSeg = new MeuSpiner(),
							tmOutSeg = new MeuSpiner(),
							tmOutSex = new MeuSpiner(),
							tmInSex = new MeuSpiner(),
							tmOutSab = new MeuSpiner(),
							tmInSab = new MeuSpiner(),
							tmOutDom = new MeuSpiner(),
							tmInDom = new MeuSpiner();

	private static JTree treEspecializacoes = new JTree();
		
	
	protected StateProfSaude(JanelaPrincipal janela_) {
		super(janela_);

		this.setTitle("Profissionais da saude");
		
		//montar botoes
		btnAdd.addActionListener(ControllerProfSaude.btnAdicionar(this));
		taskPanItems.add(btnAdd);
		taskPanItems.add(txtFind);
		
		StateProfSaude eu = this;
		
		//preencher lista ao carregar
		preencherLista(ControllerProfSaude.getAll());
		
		//montar fridman
		criarFriedman();
		updateFriedman();
		
		//lista de resultados - ouvinte de mudança
		lstResults.addListSelectionListener(ControllerProfSaude.mudouLista(this));
		
		//ouvir mudnaca valor pesquisa
		txtFind.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				ControllerProfSaude.pesquisarProf(eu);
			}
			public void removeUpdate(DocumentEvent e) {
				ControllerProfSaude.pesquisarProf(eu);
			}
			public void insertUpdate(DocumentEvent e) {
				ControllerProfSaude.pesquisarProf(eu);
			}
		});
		
	}
	public void criarFriedman(){



		pnlEditorMeu.setPreferredSize(new Dimension(300, 500));

				
		
				//nome
				lblNome.setBounds(10, 11, 27, 14);
				lblNome.setHorizontalAlignment(SwingConstants.LEFT);
				pnlEditorMeu.add(lblNome);
				
				txtNome.setBounds(84, 7, 168, 23);
				txtNome.setColumns(10);
				pnlEditorMeu.add(txtNome);
				

				//cpf
				lblCPF.setBounds(10, 45, 29, 14);
				pnlEditorMeu.add(lblCPF);

				txtCpf.setColumns(10);
				txtCpf.setBounds(84, 41, 170, 23);
				pnlEditorMeu.add(txtCpf);

				

				//senha
				lblSenha.setBounds(10, 77, 30, 14);
				pnlEditorMeu.add(lblSenha);

				txtdfsd.setBounds(84, 73, 86, 23);
				txtdfsd.setColumns(10);
				pnlEditorMeu.add(txtdfsd);

				btnGerarNovaSenha.setBounds(179, 73, 119, 23);
				btnGerarNovaSenha.addActionListener(ControllerProfSaude.btnNovaSenha(this));
				pnlEditorMeu.add(btnGerarNovaSenha);



				//login
				lblLogin.setBounds(10, 112, 25, 14);
				pnlEditorMeu.add(lblLogin);

				txtLogin.setBounds(84, 108, 86, 23);
				txtLogin.setEditable(false);
				txtLogin.setHorizontalAlignment(SwingConstants.CENTER);
				txtLogin.setColumns(10);
				pnlEditorMeu.add(txtLogin);
				
				
				//especializacoes
				lblEspecializacoes.setBounds(10, 153, 91, 14);
				pnlEditorMeu.add(lblEspecializacoes);

				treEspecializacoes.setBounds(94, 152, 204, 128);
				pnlEditorMeu.add(treEspecializacoes);



				//disponibilidades
				lblDisponibilidades.setBounds(10, 291, 91, 14);
				pnlEditorMeu.add(lblDisponibilidades);

					//domingo
					chckbxDomingo.setBounds(10, 312, 75, 23);
					pnlEditorMeu.add(chckbxDomingo);

					tmInDom.setBounds(10, 342, 75, 23);
					pnlEditorMeu.add(tmInDom);

					tmOutDom.setBounds(10, 376, 75, 23);
					pnlEditorMeu.add(tmOutDom);

					//segunda
					chckbxSegunda.setBounds(94, 312, 75, 23);
					pnlEditorMeu.add(chckbxSegunda);

					tmInSeg.setBounds(94, 342, 75, 23);
					pnlEditorMeu.add(tmInSeg);

					tmOutSeg.setBounds(94, 376, 75, 23);
					pnlEditorMeu.add(tmOutSeg);

					//terca
					chckbxTera.setBounds(179, 312, 75, 23);
					pnlEditorMeu.add(chckbxTera);

					tmInTer.setBounds(179, 342, 75, 23);
					pnlEditorMeu.add(tmInTer);

					tmOutTer.setBounds(179, 376, 75, 23);
					pnlEditorMeu.add(tmOutTer);

					//quarta
					chckbxQuarta.setBounds(264, 312, 75, 23);
					pnlEditorMeu.add(chckbxQuarta);

					tmInQua.setBounds(264, 342, 75, 23);
					pnlEditorMeu.add(tmInQua);

					tmOutQua.setBounds(264, 376, 75, 23);
					pnlEditorMeu.add(tmOutQua);

					//quinta
					chckbxQuinta.setBounds(353, 312, 75, 23);
					pnlEditorMeu.add(chckbxQuinta);

					tmInQui.setBounds(353, 342, 75, 23);
					pnlEditorMeu.add(tmInQui);

					tmOutQui.setBounds(353, 376, 75, 23);
					pnlEditorMeu.add(tmOutQui);


					//sexta
					chckbxSexta.setBounds(440, 312, 75, 23);
					pnlEditorMeu.add(chckbxSexta);

					tmInSex.setBounds(440, 342, 75, 23);
					pnlEditorMeu.add(tmInSex);

					tmOutSex.setBounds(440, 376, 75, 23);
					pnlEditorMeu.add(tmOutSex);

					//sabado
					chckbxSbado.setBounds(521, 312, 75, 23);
					pnlEditorMeu.add(chckbxSbado);

					tmInSab.setBounds(521, 342, 75, 23);
					pnlEditorMeu.add(tmInSab);

					tmOutSab.setBounds(521, 376, 75, 23);
					pnlEditorMeu.add(tmOutSab);


	

				//botoes
				btnSalvar.setBounds(10, 417, 110, 40);
				btnSalvar.addActionListener(ControllerProfSaude.btnSalvar(this));
				pnlEditorMeu.add(btnSalvar);

				btnExcluir.setBounds(130, 417, 110, 40);
				btnExcluir.setForeground(Color.RED);
				btnExcluir.addActionListener(ControllerProfSaude.btnExcluir(this));
				pnlEditorMeu.add(btnExcluir);

				
				

				
		//painel
		pnlEditorMeu.setLayout(null);

	}
	public void updateFriedman(String nome, int cargo, String senha, int Id){
		txtNome.setText(nome);
		txtdfsd.setText(senha);
		txtLogin.setText("" + Id);
		System.out.println("Eu quero: " + cargo + " Achei no: " + Cargos.getCargoIndex(cargo));
		pnlEditorMeu.setVisible(true);
	}
	public void updateFriedman(){}
	//getters e setters para fridman
	public void setSenha(String s){
		txtdfsd.setText(s);
	}
	public int getId(){
		return Integer.parseInt(txtLogin.getText());
	}
	public String getNome(){
		return txtNome.getText();
	}

	public String getSenha(){
		return txtdfsd.getText();
	}
	public String getPesquisa(){
		return txtFind.getText();
	}
	private static class MeuSpiner extends JSpinner{
		private JSpinner.DateEditor timedin;
		public MeuSpiner(){
			super(new SpinnerDateModel());
			timedin = new JSpinner.DateEditor(this, "HH:mm:ss");
			this.setEditor(timedin);
			this.setValue(new Date(0, 0, 0));
		}

	}

}
