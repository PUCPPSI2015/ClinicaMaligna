package views.states;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.ConsultasModel.Consulta;
import model.dbos.ProfSaude;
import views.JanelaPrincipal;
import controllers.ControllerPrincipal;
import controllers.ControllerProfSaude;
import controllers.ControllerRegistro;

public class StateRegistro extends InternalState {
	// criando campo pesquisa
	private JTextField txtFind = new JTextField(52);

	private ProfSaude eu;

	// criando botoes
	private JButton btnPass = new JButton("Consultas passadas");
	private JButton btnFutu = new JButton("Consultas futuras");

	// Criar objetos friedman
	private static JPanel pnlEditorMeu = new JPanel();

	private JLabel lblEspecialdiade = new JLabel("Especialidade:"),
			lblPaciente = new JLabel("Paciente:"),
			lblData = new JLabel("Data:"), lblObservacoes = new JLabel(
					"Observacoes:"), lblExames = new JLabel(
					"Pedidos\n de exames:"), lblPrescricoes = new JLabel(
					"Prescricoes:"), lblRecomendacoes = new JLabel(
					"Recomendacoes:");

	private LabelMutavel lbmEspecialidade = new LabelMutavel(""),
			lbmPaciente = new LabelMutavel("Paciente da silva"),
			lbmData = new LabelMutavel("20/12/5015 das 09:12 ate as 10:30");

	private JScrollPane scrObservacoes = new JScrollPane(),
			scrExames = new JScrollPane(), scrPrescricoes = new JScrollPane(),
			scrRecomendacoes = new JScrollPane();

	private JTextPane txpObservacoes = new JTextPane(),
			txpExames = new JTextPane(), txpPrescricoes = new JTextPane(),
			txpRecomendacoes = new JTextPane();

	private JButton btnSalvar = new JButton("Salvar");

	private Consulta editando = null;

	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat nt = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat uf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat nf = new SimpleDateFormat(
			"dd/MM/yyyy as HH:mm:ss");

	protected StateRegistro(JanelaPrincipal janela_) {
		super(janela_);
		this.setTitle("Minhas consultas ");
		// iniciar controller
		ControllerRegistro.start(this);

		// montar botoes e pesquisa
		// montar botoes
		// taskPanItems.add(txtFind);
		btnPass.addActionListener(ControllerRegistro.btnPass());
		taskPanItems.add(btnPass);
		btnFutu.addActionListener(ControllerRegistro.btnFutu());
		taskPanItems.add(btnFutu);

		// preencher lista ao carregar

		// montar friedman
		criarFriedman();
		updateFriedman();

		// ouvinte da lista
		lstResults.addListSelectionListener(ControllerRegistro.mudouLista());

		// ouvir mudnaca valor pesquisa
		txtFind.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				ControllerRegistro.pesquisarPac();
			}

			public void removeUpdate(DocumentEvent e) {
				ControllerRegistro.pesquisarPac();
			}

			public void insertUpdate(DocumentEvent e) {
				ControllerRegistro.pesquisarPac();
			}
		});

	}

	private void criarFriedman() {
		// painel
		pnlEditorMeu.setLayout(null);
		pnlEditorMor.add(pnlEditorMeu);
		pnlEditorMeu.setPreferredSize(new Dimension(300, 550));

		// especialidades
		lblEspecialdiade.setBounds(10, 0, 80, 14);
		lblEspecialdiade.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblEspecialdiade);

		lbmEspecialidade.setBounds(10, 14, 198, 23);
		pnlEditorMeu.add(lbmEspecialidade);

		// paciente
		lblPaciente.setBounds(200, 0, 80, 14);
		lblPaciente.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblPaciente);

		lbmPaciente.setBounds(200, 14, 168, 23);
		pnlEditorMeu.add(lbmPaciente);

		// data
		lblData.setBounds(10, 50, 80, 14);
		lblData.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblData);

		lbmData.setBounds(10, 64, 330, 23);
		pnlEditorMeu.add(lbmData);

		// observacoes
		lblObservacoes.setBounds(10, 100, 90, 14);
		lblObservacoes.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblObservacoes);

		scrObservacoes.setBounds(100, 100, 350, 70);
		scrObservacoes.setViewportView(txpObservacoes);
		txpObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pnlEditorMeu.add(scrObservacoes);

		// EXAMES
		lblExames.setBounds(10, 180, 90, 14);
		lblExames.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblExames);

		scrExames.setBounds(100, 180, 350, 70);
		scrExames.setViewportView(txpExames);
		txpExames.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pnlEditorMeu.add(scrExames);

		// PRESCRICOES
		lblPrescricoes.setBounds(10, 260, 90, 14);
		lblPrescricoes.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblPrescricoes);

		scrPrescricoes.setBounds(100, 260, 350, 70);
		scrPrescricoes.setViewportView(txpPrescricoes);
		txpPrescricoes.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pnlEditorMeu.add(scrPrescricoes);

		// RECOMENDACOES
		lblRecomendacoes.setBounds(10, 340, 90, 14);
		lblRecomendacoes.setHorizontalAlignment(SwingConstants.LEFT);
		pnlEditorMeu.add(lblRecomendacoes);

		scrRecomendacoes.setBounds(100, 340, 350, 70);
		scrRecomendacoes.setViewportView(txpRecomendacoes);
		txpRecomendacoes.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		pnlEditorMeu.add(scrRecomendacoes);

		btnSalvar.setBounds(10, 440, 110, 40);
		btnSalvar.addActionListener(ControllerRegistro.btnSalvar());
		pnlEditorMeu.add(btnSalvar);

	}

	public void logou() {
		preencherLista(ControllerRegistro.getPassadas());
		eu = (ProfSaude) ControllerPrincipal.getObjLogado();
		this.setTitle("Minhas consultas " + eu.getNome());
	}

	public static class LabelMutavel extends JLabel {
		public LabelMutavel(String s) {
			super(s);
			this.setHorizontalAlignment(SwingConstants.LEFT);
			this.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		}
	}

	public void updateFriedman(Consulta c, String nome, String nomee,
			Date data, Time inicio, Time fim, String observacoes,
			String pedidosExame, String prescricoes, String recomendacoes) {
		this.editando = c;
		this.lbmPaciente.setText(nome);
		this.lbmPaciente.setEnabled(true);
		this.lbmEspecialidade.setText(nomee);
		this.lbmEspecialidade.setEnabled(true);

		String shorario = df.format(fim);
		String sdia = nt.format(data);

		this.lbmData.setText(sdia + " as " + shorario);
		this.lbmData.setEnabled(true);

		this.txpObservacoes.setText(observacoes);
		this.txpExames.setText(pedidosExame);
		this.txpPrescricoes.setText(prescricoes);
		this.txpRecomendacoes.setText(recomendacoes);

		this.txpObservacoes.setEnabled(true);
		this.txpExames.setEnabled(true);
		this.txpPrescricoes.setEnabled(true);
		this.txpRecomendacoes.setEnabled(true);

	}

	public void updateFriedman() {
		this.editando = null;
		this.lbmPaciente.setText("");
		this.lbmPaciente.setEnabled(false);
		this.lbmEspecialidade.setText("");
		this.lbmEspecialidade.setEnabled(false);

		this.lbmData.setText("");
		this.lbmData.setEnabled(false);

		this.txpObservacoes.setText("");
		this.txpExames.setText("");
		this.txpPrescricoes.setText("");
		this.txpRecomendacoes.setText("");

		this.txpObservacoes.setEnabled(false);
		this.txpExames.setEnabled(false);
		this.txpPrescricoes.setEnabled(false);
		this.txpRecomendacoes.setEnabled(false);

	}

	public String getObservacao() {
		return this.txpObservacoes.getText();
	}

	public String getExames() {
		return this.txpExames.getText();
	}

	public String getPrescri() {
		return this.txpPrescricoes.getText();
	}

	public String getRec() {
		return this.txpRecomendacoes.getText();
	}

	public Consulta getEditando() {

		return this.editando;
	}

	public String getPesquisa() {
		return this.txtFind.getText();
	}

}
