package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;

import model.DisponibilidadesModel.Disponibilidade;
import model.harddata.Especialidades.Especialidade;
import controllers.ControllerProfSaude;
import controllers.ControllerProfSaude.MeuJCombo;
import controllers.ControllerProfSaude.ProfissaoArvore;
import views.states.StateProfSaude;
import views.states.StateProfSaude.MeuSpiner;

public class AdicionarJanelaProf extends JFrame {
	private Container cntForm;
	// criando campo pesquisa
	private JTextField txtFind = new JTextField(52);

	// criando botoes
	private JButton btnAdd = new JButton("Adicionar novo");

	// meus elementos do friedman
	private static JPanel pnlEditorMeu = new JPanel();

	private static JTextField txtNome = new JTextField(),
			txtdfsd = new JTextField(), txtIdClasse = new JTextField(),
			txtCpf = new JTextField();

	private static JLabel lblNome = new JLabel("Nome"), lblCPF = new JLabel(
			"CPF"), lblIdClasse = new JLabel("Id da Classe(CRM, CRO...)"),
			lblSenha = new JLabel("Senha"), lblDisponibilidades = new JLabel(
					"Disponibilidades"), lblEspecializacoes = new JLabel(
					"Especializacoes");

	private static JButton btnGerarNovaSenha = new JButton("Gerar nova senha"),
			btnSalvar = new JButton("Salvar");

	private static JCheckBox chckbxDomingo = new JCheckBox("Domingo"),
			chckbxSegunda = new JCheckBox("Segunda"),
			chckbxTera = new JCheckBox("Ter\u00E7a"),
			chckbxSexta = new JCheckBox("Sexta"), chckbxQuarta = new JCheckBox(
					"Quarta"), chckbxQuinta = new JCheckBox("Quinta"),
			chckbxSbado = new JCheckBox("S\u00E1bado");

	private static MeuSpiner tmOutTer = new MeuSpiner(),
			tmInTer = new MeuSpiner(), tmOutQua = new MeuSpiner(),
			tmInQua = new MeuSpiner(), tmOutQui = new MeuSpiner(),
			tmInQui = new MeuSpiner(), tmInSeg = new MeuSpiner(),
			tmOutSeg = new MeuSpiner(), tmOutSex = new MeuSpiner(),
			tmInSex = new MeuSpiner(), tmOutSab = new MeuSpiner(),
			tmInSab = new MeuSpiner(), tmOutDom = new MeuSpiner(),
			tmInDom = new MeuSpiner();

	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

	private static ProfissaoArvore treEspecializacoes = ControllerProfSaude
			.montarArvore();
	private static JScrollPane scrEsp = new JScrollPane(treEspecializacoes);
	private StateProfSaude s;

	public AdicionarJanelaProf(StateProfSaude state) {
		super("Adicionar funcionario Administrativo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				JanelaPrincipal.class.getResource("/material/icon.png")));

		this.s = state;
		this.setSize(650, 500);
		cntForm = this.getContentPane();
		cntForm.setLayout(new BorderLayout());

		pnlEditorMeu.setPreferredSize(new Dimension(650, 500));

		// nome
		lblNome.setBounds(10, 11, 27, 14);
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblNome);

		txtNome.setText("");
		txtNome.setBounds(84, 7, 168, 23);
		txtNome.setColumns(10);
		pnlEditorMeu.add(txtNome);

		// cpf
		lblCPF.setBounds(10, 45, 29, 14);
		pnlEditorMeu.add(lblCPF);

		txtCpf.setText("");
		txtCpf.setColumns(10);
		txtCpf.setBounds(84, 41, 170, 23);
		pnlEditorMeu.add(txtCpf);

		// idclasse
		lblIdClasse.setBounds(285, 21, 168, 14);
		pnlEditorMeu.add(lblIdClasse);

		txtIdClasse.setText("");
		txtIdClasse.setColumns(10);
		txtIdClasse.setBounds(285, 41, 170, 23);
		pnlEditorMeu.add(txtIdClasse);

		// senha
		lblSenha.setBounds(10, 77, 30, 14);
		pnlEditorMeu.add(lblSenha);

		txtdfsd.setText("");
		txtdfsd.setBounds(84, 73, 86, 23);
		txtdfsd.setColumns(10);
		pnlEditorMeu.add(txtdfsd);

		btnGerarNovaSenha.setBounds(179, 73, 119, 23);
		btnGerarNovaSenha.addActionListener(ControllerProfSaude
				.btnNovaSenhaJanela(this));
		pnlEditorMeu.add(btnGerarNovaSenha);

		// especializacoes
		lblEspecializacoes.setBounds(10, 153, 91, 14);
		pnlEditorMeu.add(lblEspecializacoes);

		scrEsp.setBounds(94, 152, 354, 128);
		treEspecializacoes.setBorder(BorderFactory.createEmptyBorder(10, 10,
				10, 10));

		pnlEditorMeu.add(scrEsp);

		// disponibilidades
		lblDisponibilidades.setBounds(10, 291, 91, 14);
		pnlEditorMeu.add(lblDisponibilidades);

		// domingo
		chckbxDomingo.setBounds(10, 312, 75, 23);
		chckbxDomingo.addItemListener(ControllerProfSaude.checkDiasSemana(this,
				chckbxDomingo, tmInDom, tmOutDom));
		pnlEditorMeu.add(chckbxDomingo);

		tmInDom.setBounds(10, 342, 75, 23);
		pnlEditorMeu.add(tmInDom);

		tmOutDom.setBounds(10, 376, 75, 23);
		pnlEditorMeu.add(tmOutDom);

		tmInDom.setEnabled(chckbxDomingo.isSelected());
		tmOutDom.setEnabled(chckbxDomingo.isSelected());

		// segunda
		chckbxSegunda.setBounds(94, 312, 75, 23);
		chckbxSegunda.addItemListener(ControllerProfSaude.checkDiasSemana(this,
				chckbxSegunda, tmInSeg, tmOutSeg));
		pnlEditorMeu.add(chckbxSegunda);

		tmInSeg.setBounds(94, 342, 75, 23);
		pnlEditorMeu.add(tmInSeg);

		tmOutSeg.setBounds(94, 376, 75, 23);
		pnlEditorMeu.add(tmOutSeg);

		tmInSeg.setEnabled(chckbxSegunda.isSelected());
		tmOutSeg.setEnabled(chckbxSegunda.isSelected());

		// terca
		chckbxTera.setBounds(179, 312, 75, 23);
		chckbxTera.addItemListener(ControllerProfSaude.checkDiasSemana(this,
				chckbxTera, tmInTer, tmOutTer));
		pnlEditorMeu.add(chckbxTera);

		tmInTer.setBounds(179, 342, 75, 23);
		pnlEditorMeu.add(tmInTer);

		tmOutTer.setBounds(179, 376, 75, 23);
		pnlEditorMeu.add(tmOutTer);

		tmInTer.setEnabled(chckbxTera.isSelected());
		tmOutTer.setEnabled(chckbxTera.isSelected());

		// quarta
		chckbxQuarta.setBounds(264, 312, 75, 23);
		chckbxQuarta.addItemListener(ControllerProfSaude.checkDiasSemana(this,
				chckbxQuarta, tmInQua, tmOutQua));
		pnlEditorMeu.add(chckbxQuarta);

		tmInQua.setBounds(264, 342, 75, 23);
		pnlEditorMeu.add(tmInQua);

		tmOutQua.setBounds(264, 376, 75, 23);
		pnlEditorMeu.add(tmOutQua);

		tmInQua.setEnabled(chckbxQuarta.isSelected());
		tmOutQua.setEnabled(chckbxQuarta.isSelected());

		// quinta
		chckbxQuinta.setBounds(353, 312, 75, 23);
		chckbxQuinta.addItemListener(ControllerProfSaude.checkDiasSemana(this,
				chckbxQuinta, tmInQui, tmOutQui));
		pnlEditorMeu.add(chckbxQuinta);

		tmInQui.setBounds(353, 342, 75, 23);
		pnlEditorMeu.add(tmInQui);

		tmOutQui.setBounds(353, 376, 75, 23);
		pnlEditorMeu.add(tmOutQui);

		tmInQui.setEnabled(chckbxQuinta.isSelected());
		tmOutQui.setEnabled(chckbxQuinta.isSelected());

		// sexta
		chckbxSexta.setBounds(440, 312, 75, 23);
		chckbxSexta.addItemListener(ControllerProfSaude.checkDiasSemana(this,
				chckbxSexta, tmInSex, tmOutSex));
		pnlEditorMeu.add(chckbxSexta);

		tmInSex.setBounds(440, 342, 75, 23);
		pnlEditorMeu.add(tmInSex);

		tmOutSex.setBounds(440, 376, 75, 23);
		pnlEditorMeu.add(tmOutSex);

		tmInSex.setEnabled(chckbxSexta.isSelected());
		tmOutSex.setEnabled(chckbxSexta.isSelected());

		// sabado
		chckbxSbado.setBounds(521, 312, 75, 23);
		chckbxSbado.addItemListener(ControllerProfSaude.checkDiasSemana(this,
				chckbxSbado, tmInSab, tmOutSab));
		pnlEditorMeu.add(chckbxSbado);

		tmInSab.setBounds(521, 342, 75, 23);
		pnlEditorMeu.add(tmInSab);

		tmOutSab.setBounds(521, 376, 75, 23);
		pnlEditorMeu.add(tmOutSab);

		tmInSab.setEnabled(chckbxSbado.isSelected());
		tmOutSab.setEnabled(chckbxSbado.isSelected());

		// botoes
		btnSalvar.setBounds(10, 407, 110, 40);
		btnSalvar.addActionListener(ControllerProfSaude.btnSalvarNovo(this, s));
		pnlEditorMeu.add(btnSalvar);

		// painel
		pnlEditorMeu.setLayout(null);

		cntForm.add(pnlEditorMeu, BorderLayout.WEST);
		this.setVisible(true);
	}

	public void setSenha(String s) {
		txtdfsd.setText(s);
	}

	public String getNome() {
		return txtNome.getText();
	}

	public String getSenha() {
		return txtdfsd.getText();
	}

	public String getCpf() {
		return txtCpf.getText();
	}

	public String getIdClasse() {
		return txtIdClasse.getText();
	}

	public ProfissaoArvore getArvore() {
		return this.treEspecializacoes;
	}

	public Date formatar(MeuSpiner sp) {
		try {
			return df.parse(df.format(sp.getValue()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Disponibilidade[] getDisponibilidades() {

		Disponibilidade[] retorno;
		ArrayList<Disponibilidade> disponibilidades = new ArrayList<Disponibilidade>();
		// pegar domingo
		disponibilidades.add(new Disponibilidade(1, 1, formatar(tmInDom),
				formatar(tmOutDom), chckbxDomingo.isSelected()));

		// pegar segunda
		disponibilidades.add(new Disponibilidade(2, 1, formatar(tmInSeg),
				formatar(tmOutSeg), chckbxSegunda.isSelected()));

		// pegar terca
		disponibilidades.add(new Disponibilidade(3, 1, formatar(tmInTer),
				formatar(tmOutTer), chckbxTera.isSelected()));

		// pegar quarta
		disponibilidades.add(new Disponibilidade(4, 1, formatar(tmInQua),
				formatar(tmOutQua), chckbxQuarta.isSelected()));

		// pegar quinta
		disponibilidades.add(new Disponibilidade(5, 1, formatar(tmInQui),
				formatar(tmOutQui), chckbxQuinta.isSelected()));

		// pegar sexta
		disponibilidades.add(new Disponibilidade(6, 1, formatar(tmInSex),
				formatar(tmOutSex), chckbxSexta.isSelected()));

		// pegar sabado
		disponibilidades.add(new Disponibilidade(7, 1, formatar(tmInSab),
				formatar(tmOutSab), chckbxSbado.isSelected()));

		retorno = new Disponibilidade[disponibilidades.size()];
		int i = 0;
		for (int keyo = 0; keyo < disponibilidades.size(); keyo++) {
			retorno[i] = disponibilidades.get(keyo);
			i++;
		}
		return retorno;
	}

	public void resetSpinners() {
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

	public boolean setCbx(int dia, boolean s) {

		switch (dia) {
		case 1:
			chckbxDomingo.setSelected(s);
			break;
		case 2:
			chckbxSegunda.setSelected(s);
			break;
		case 3:
			chckbxTera.setSelected(s);
			break;
		case 4:
			chckbxQuarta.setSelected(s);
			break;
		case 5:
			chckbxQuinta.setSelected(s);
			break;
		case 6:
			chckbxSexta.setSelected(s);
			break;
		case 7:
			chckbxSbado.setSelected(s);
			break;
		case 0:
			chckbxDomingo.setSelected(s);
			chckbxSegunda.setSelected(s);
			chckbxTera.setSelected(s);
			chckbxQuarta.setSelected(s);
			chckbxQuinta.setSelected(s);
			chckbxSexta.setSelected(s);
			chckbxSbado.setSelected(s);
			break;
		case 10:
			chckbxDomingo.setEnabled(s);
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
}
