package views.states;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controllers.ControllerProfSaude;
import controllers.ControllerProfSaude.MeuJCombo;
import controllers.ControllerProfSaude.ProfissaoArvore;
import views.JanelaPrincipal;
import model.harddata.Especialidades.Especialidade;
import model.DisponibilidadesModel.Disponibilidade;

public class StateProfSaude extends InternalState{
	
	//criando campo pesquisa
	private JTextField txtFind = new JTextField(52);
	
	//criando botoes
	private JButton btnAdd = new JButton("Adicionar novo");

	private static String idClasseA;
	
	//meus elementos do friedman
	private static JPanel pnlEditorMeu = new JPanel();

	private static JTextField 	txtNome = new JTextField(),
								txtdfsd = new JTextField(),
								txtLogin = new JTextField(),
								txtCpf = new JTextField(),
								txtIdClasse = new JTextField();

	private static JLabel 	lblNome = new JLabel("Nome"),
							lblCPF = new JLabel("CPF"),
							lblIdClasse = new JLabel("Id da Classe(CRM, CRO...)"),
							lblSenha = new JLabel("Senha"),
							lblLogin = new JLabel("Login"),
							lblDisponibilidades = new JLabel("Disponibilidades"),
							lblEspecializacoes = new JLabel("Especializacoes");

	private static JButton 	btnGerarNovaSenha = new JButton("Gerar nova senha"),
							btnSalvar = new JButton("Salvar"),
							btnExcluir = new JButton("Excluir");
	

	private static JCheckBox 	chckbxDomingo = new JCheckBox("Domingo"),
								chckbxSegunda  = new JCheckBox("Segunda"),
								chckbxTera = new JCheckBox("Ter\u00E7a"),
								chckbxSexta = new JCheckBox("Sexta"),
								chckbxQuarta = new JCheckBox("Quarta"),
								chckbxQuinta = new JCheckBox("Quinta"),
								chckbxSbado = new JCheckBox("S\u00E1bado");

	private static MeuSpiner 	tmOutTer = new MeuSpiner(),
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

	private static ProfissaoArvore treEspecializacoes = ControllerProfSaude.montarArvore();

	private static JScrollPane scrEsp = new JScrollPane(treEspecializacoes);
	
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

	@SuppressWarnings("unchecked")
	private MeuJCombo<Especialidade> 	cbxDom = ControllerProfSaude.novoComboBox(this, 1),
										cbxSeg = ControllerProfSaude.novoComboBox(this, 2),
										cbxTer = ControllerProfSaude.novoComboBox(this, 3),
										cbxQua = ControllerProfSaude.novoComboBox(this, 4),
										cbxQui = ControllerProfSaude.novoComboBox(this, 5),
										cbxSex = ControllerProfSaude.novoComboBox(this, 6),
										cbxSab = ControllerProfSaude.novoComboBox(this, 7);
	

	
	protected StateProfSaude(JanelaPrincipal janela_) {
		super(janela_);

		this.setTitle("Profissionais da saude");
		
		//montar botoes
		btnAdd.addActionListener(ControllerProfSaude.btnAdicionar(this));
		taskPanItems.add(txtFind);
		taskPanItems.add(btnAdd);
		
		
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


				//idclasse
		lblIdClasse.setBounds(285, 21, 168, 14);
		pnlEditorMeu.add(lblIdClasse);

		txtIdClasse.setColumns(10);
		txtIdClasse.setBounds(285, 41, 170, 23);
		pnlEditorMeu.add(txtIdClasse);


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

		scrEsp.setBounds(94, 152, 354, 128);
		treEspecializacoes.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		pnlEditorMeu.add(scrEsp);

				//disponibilidades
		lblDisponibilidades.setBounds(10, 291, 91, 14);
		pnlEditorMeu.add(lblDisponibilidades);

					//domingo
		chckbxDomingo.setBounds(10, 312, 75, 23);
		chckbxDomingo.addItemListener(ControllerProfSaude.checkDiasSemana(this,chckbxDomingo, tmInDom, tmOutDom, cbxDom));
		pnlEditorMeu.add(chckbxDomingo);

		tmInDom.setBounds(10, 342, 75, 23);
		pnlEditorMeu.add(tmInDom);

		tmOutDom.setBounds(10, 376, 75, 23);
		pnlEditorMeu.add(tmOutDom);

		tmInDom.setEnabled(chckbxDomingo.isSelected());
		tmOutDom.setEnabled(chckbxDomingo.isSelected());
		cbxDom.setEnabled(chckbxDomingo.isSelected());

		cbxDom.setBounds(10, 410, 75, 20);
		pnlEditorMeu.add(cbxDom);

					//segunda
		chckbxSegunda.setBounds(94, 312, 75, 23);
		chckbxSegunda.addItemListener(ControllerProfSaude.checkDiasSemana(this,chckbxSegunda, tmInSeg, tmOutSeg, cbxSeg));
		pnlEditorMeu.add(chckbxSegunda);

		tmInSeg.setBounds(94, 342, 75, 23);
		pnlEditorMeu.add(tmInSeg);

		tmOutSeg.setBounds(94, 376, 75, 23);
		pnlEditorMeu.add(tmOutSeg);

		tmInSeg.setEnabled(chckbxSegunda.isSelected());
		tmOutSeg.setEnabled(chckbxSegunda.isSelected());
		cbxSeg.setEnabled(chckbxSegunda.isSelected());

		cbxSeg.setBounds(95, 410, 75, 20);
		pnlEditorMeu.add(cbxSeg);

					//terca
		chckbxTera.setBounds(179, 312, 75, 23);
		chckbxTera.addItemListener(ControllerProfSaude.checkDiasSemana(this,chckbxTera, tmInTer, tmOutTer, cbxTer));
		pnlEditorMeu.add(chckbxTera);

		tmInTer.setBounds(179, 342, 75, 23);
		pnlEditorMeu.add(tmInTer);

		tmOutTer.setBounds(179, 376, 75, 23);
		pnlEditorMeu.add(tmOutTer);

		tmInTer.setEnabled(chckbxTera.isSelected());
		tmOutTer.setEnabled(chckbxTera.isSelected());
		cbxTer.setEnabled(chckbxTera.isSelected());

		cbxTer.setBounds(179, 410, 75, 20);
		pnlEditorMeu.add(cbxTer);

					//quarta
		chckbxQuarta.setBounds(264, 312, 75, 23);
		chckbxQuarta.addItemListener(ControllerProfSaude.checkDiasSemana(this,chckbxQuarta, tmInQua, tmOutQua, cbxQua));
		pnlEditorMeu.add(chckbxQuarta);

		tmInQua.setBounds(264, 342, 75, 23);
		pnlEditorMeu.add(tmInQua);

		tmOutQua.setBounds(264, 376, 75, 23);
		pnlEditorMeu.add(tmOutQua);

		tmInQua.setEnabled(chckbxQuarta.isSelected());
		tmOutQua.setEnabled(chckbxQuarta.isSelected());
		cbxQua.setEnabled(chckbxQuarta.isSelected());

		cbxQua.setBounds(264, 410, 75, 20);
		pnlEditorMeu.add(cbxQua);

					//quinta
		chckbxQuinta.setBounds(353, 312, 75, 23);
		chckbxQuinta.addItemListener(ControllerProfSaude.checkDiasSemana(this,chckbxQuinta, tmInQui, tmOutQui, cbxQui));
		pnlEditorMeu.add(chckbxQuinta);

		tmInQui.setBounds(353, 342, 75, 23);
		pnlEditorMeu.add(tmInQui);

		tmOutQui.setBounds(353, 376, 75, 23);
		pnlEditorMeu.add(tmOutQui);

		tmInQui.setEnabled(chckbxQuinta.isSelected());
		tmOutQui.setEnabled(chckbxQuinta.isSelected());
		cbxQui.setEnabled(chckbxQuinta.isSelected());

		cbxQui.setBounds(353, 410, 75, 20);
		pnlEditorMeu.add(cbxQui);

					//sexta
		chckbxSexta.setBounds(440, 312, 75, 23);
		chckbxSexta.addItemListener(ControllerProfSaude.checkDiasSemana(this,chckbxSexta, tmInSex, tmOutSex, cbxSex));
		pnlEditorMeu.add(chckbxSexta);

		tmInSex.setBounds(440, 342, 75, 23);
		pnlEditorMeu.add(tmInSex);

		tmOutSex.setBounds(440, 376, 75, 23);
		pnlEditorMeu.add(tmOutSex);

		tmInSex.setEnabled(chckbxSexta.isSelected());
		tmOutSex.setEnabled(chckbxSexta.isSelected());
		cbxSex.setEnabled(chckbxSexta.isSelected());

		cbxSex.setBounds(440, 410, 75, 20);
		pnlEditorMeu.add(cbxSex);

					//sabado
		chckbxSbado.setBounds(521, 312, 75, 23);
		chckbxSbado.addItemListener(ControllerProfSaude.checkDiasSemana(this,chckbxSbado, tmInSab, tmOutSab, cbxSab));
		pnlEditorMeu.add(chckbxSbado);

		tmInSab.setBounds(521, 342, 75, 23);
		pnlEditorMeu.add(tmInSab);

		tmOutSab.setBounds(521, 376, 75, 23);
		pnlEditorMeu.add(tmOutSab);

		tmInSab.setEnabled(chckbxSbado.isSelected());
		tmOutSab.setEnabled(chckbxSbado.isSelected());
		cbxSab.setEnabled(chckbxSbado.isSelected());

		cbxSab.setBounds(521, 410, 75, 20);
		pnlEditorMeu.add(cbxSab);


				//botoes
		btnSalvar.setBounds(10, 447, 110, 40);
		btnSalvar.addActionListener(ControllerProfSaude.btnSalvar(this));
		pnlEditorMeu.add(btnSalvar);

		btnExcluir.setBounds(130, 447, 110, 40);
		btnExcluir.setForeground(Color.RED);
		btnExcluir.addActionListener(ControllerProfSaude.btnExcluir(this));
		pnlEditorMeu.add(btnExcluir);


		//painel
		pnlEditorMeu.setLayout(null);
		pnlEditorMor.add(pnlEditorMeu);
		
	}
	public void updateFriedman(String nome, int cpf, String senha, int id, String idClasse){

		txtNome.setText(nome);
		this.setIdClasseA("" + idClasse);
		txtCpf.setText("" + cpf);
		txtdfsd.setText(senha);
		txtLogin.setText("" + id);
		txtIdClasse.setText(idClasse);
		pnlEditorMeu.setVisible(true);
		txtNome.setEnabled(true);
		txtCpf.setEnabled(true);
		txtdfsd.setEnabled(true);
		txtLogin.setEnabled(true);
		txtIdClasse.setEnabled(true);
		treEspecializacoes.setEnabled(true);
		setCbx(10,true);

	}
	public void updateFriedman(){
		txtNome.setText("");
		this.setIdClasseA("");
		txtCpf.setText("");
		txtdfsd.setText("");
		txtLogin.setText("");
		txtIdClasse.setText("");

		txtNome.setEnabled(false);
		txtCpf.setEnabled(false);
		txtdfsd.setEnabled(false);
		txtLogin.setEnabled(false);
		txtIdClasse.setEnabled(false);
		treEspecializacoes.setEnabled(false);
		this.setIdClasseA("");
		setCbx(0,false);
		setCbx(10,false);
		resetSpinners();

	}
	public void hideFriedman(){
		pnlEditorMeu.setVisible(false);
	}
	//getters e setters para fridman
	public void setSenha(String s){
		txtdfsd.setText(s);
	}
	private void setIdClasseA(String s){
		idClasseA = s;
	}
	public String getIdClasseA(){
		return idClasseA;
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
	public String getCpf(){
		return txtCpf.getText();
	}
	public String getPesquisa(){
		return txtFind.getText();
	}
	public String getIdClasse(){
		return txtIdClasse.getText();
	}
	public ProfissaoArvore getArvore(){
		return StateProfSaude.treEspecializacoes;
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
	public void preencherCbx(int id){
		cbxDom.preencher(id);
		cbxSeg.preencher(id);
		cbxTer.preencher(id);
		cbxQua.preencher(id);
		cbxQui.preencher(id);
		cbxSex.preencher(id);
		cbxSab.preencher(id);

	}
	public boolean setCbx(int dia, boolean s){

		switch (dia){
			case 1: chckbxDomingo.setSelected(s);
			break; 
			case 2: chckbxSegunda.setSelected(s); 
			break;
			case 3: chckbxTera.setSelected(s); 
			break;
			case 4: chckbxQuarta.setSelected(s); 
			break;
			case 5: chckbxQuinta.setSelected(s); 
			break;
			case 6: chckbxSexta.setSelected(s); 
			break;
			case 7: chckbxSbado.setSelected(s); 
			break;
			case 0: chckbxDomingo.setSelected(s);
			chckbxSegunda.setSelected(s); 
			chckbxTera.setSelected(s); 
			chckbxQuarta.setSelected(s); 
			chckbxQuinta.setSelected(s); 
			chckbxSexta.setSelected(s); 
			chckbxSbado.setSelected(s);
			break;
			case 10: chckbxDomingo.setEnabled(s);
			chckbxSegunda.setEnabled(s); 
			chckbxTera.setEnabled(s); 
			chckbxQuarta.setEnabled(s); 
			chckbxQuinta.setEnabled(s); 
			chckbxSexta.setEnabled(s); 
			chckbxSbado.setEnabled(s);
			break;
		}
		return s;
	}
	public void setInOutEsp(int dia, Time in, Time out, Especialidade esp, boolean ativo){
		switch (dia){
			case 1: tmInDom.setValue(in);
					tmOutDom.setValue(out);
					cbxDom.setSelectedItem(esp);
					setCbx(1,ativo);
			break;
			case 2: tmInSeg.setValue(in);
					tmOutSeg.setValue(out);
					cbxSeg.setSelectedItem(esp);
					setCbx(2,ativo);
			break;
			case 3: tmInTer.setValue(in);
					tmOutTer.setValue(out);
					cbxTer.setSelectedItem(esp);
					setCbx(3,ativo);
			break;
			case 4: tmInQua.setValue(in);
					tmOutQua.setValue(out);
					cbxQua.setSelectedItem(esp);
					setCbx(4,ativo);
			break;
			case 5: tmInQui.setValue(in);
					tmOutQui.setValue(out);
					cbxQui.setSelectedItem(esp);
					setCbx(5,ativo);
			break;
			case 6: tmInSex.setValue(in);
					tmOutSex.setValue(out);
					cbxSex.setSelectedItem(esp);
					setCbx(6,ativo);
			break;
			case 7: tmInSab.setValue(in);
					tmOutSab.setValue(out);
					cbxSab.setSelectedItem(esp);
					setCbx(7,ativo);
			break;
		}
	}
	public void resetSpinners(){
		tmOutTer.reseta();
		tmInTer.reseta();
		tmOutQua.reseta();
		tmInQua.reseta();
		tmOutQui.reseta();
		tmInQui.reseta();
		tmInSeg.reseta();
		tmOutSeg.reseta();
		tmOutSex.reseta();
		tmInSex.reseta();
		tmOutSab.reseta();
		tmInSab.reseta();
		tmOutDom.reseta();
		tmInDom.reseta();
	}
	public Date formatar(MeuSpiner sp){
		try {
			return df.parse(df.format(sp.getValue()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	public Disponibilidade[] getDisponibilidades( Disponibilidade dom, Disponibilidade seg, Disponibilidade ter, Disponibilidade qua, Disponibilidade qui, Disponibilidade sex, Disponibilidade sab){
		
		Disponibilidade[] retorno;
		ArrayList<Disponibilidade> disponibilidades = new ArrayList<Disponibilidade>();
		//pegar domingo
		dom.setAtivo(chckbxDomingo.isSelected());
		dom.setDiaDaSemana(1);
		dom.setIdEspecialidade(((Especialidade) cbxDom.getSelectedItem()).getId());
		dom.setInicio(formatar(tmInDom));
		dom.setFim(formatar(tmOutDom));
		disponibilidades.add(dom);
		
		//pegar segunda
		seg.setAtivo(chckbxSegunda.isSelected());
		seg.setDiaDaSemana(2);
		seg.setIdEspecialidade(((Especialidade) cbxSeg.getSelectedItem()).getId());
		seg.setInicio(formatar(tmInSeg));
		seg.setFim(formatar(tmOutSeg));
		disponibilidades.add(seg);
		
		//pegar terca
		ter.setAtivo(chckbxTera.isSelected());
		ter.setDiaDaSemana(3);
		ter.setIdEspecialidade(((Especialidade) cbxTer.getSelectedItem()).getId());
		ter.setInicio(formatar(tmInTer));
		ter.setFim(formatar(tmOutTer));
		disponibilidades.add(ter);

		//pegar quarta
		qua.setAtivo(chckbxQuarta.isSelected());
		qua.setDiaDaSemana(4);
		qua.setIdEspecialidade(((Especialidade) cbxQua.getSelectedItem()).getId());
		qua.setInicio(formatar(tmInQua));
		qua.setFim(formatar(tmOutQua));
		disponibilidades.add(qua);



		//pegar quinta
		qui.setAtivo(chckbxQuinta.isSelected());
		qui.setDiaDaSemana(5);
		qui.setIdEspecialidade(((Especialidade) cbxQui.getSelectedItem()).getId());
		qui.setInicio(formatar(tmInQui));
		qui.setFim(formatar(tmOutQui));
		disponibilidades.add(qui);

		

		//pegar sexta
		sex.setAtivo(chckbxSexta.isSelected());
		sex.setDiaDaSemana(6);
		sex.setIdEspecialidade(((Especialidade) cbxSex.getSelectedItem()).getId());
		sex.setInicio(formatar(tmInSex));
		sex.setFim(formatar(tmOutSex));
		disponibilidades.add(sex);

		

		//pegar sabado
		sab.setAtivo(chckbxSbado.isSelected());
		sab.setDiaDaSemana(7);
		sab.setIdEspecialidade(((Especialidade) cbxSab.getSelectedItem()).getId());
		sab.setInicio(formatar(tmInSab));
		sab.setFim(formatar(tmOutSab));
		disponibilidades.add(sab);

		

		retorno = new Disponibilidade[disponibilidades.size()];
		int i = 0;
		for (int keyo = 0; keyo < disponibilidades.size(); keyo++ ) {
			retorno[i] = disponibilidades.get(keyo);
			i++;
		}
		return retorno;
	}
	
	

}
