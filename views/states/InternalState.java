package views.states;

import javax.swing.JPanel;

import views.JanelaPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JSplitPane;

public class InternalState extends State {

	protected JPanel taskPan = new JPanel();
	protected JPanel taskPanTitle = new JPanel();
	protected JPanel taskPanItems = new JPanel();
	protected String title;
	protected JLabel lblFuncionarios = new JLabel("Clinica maligna");

	protected JPanel resultPan = new JPanel();
	protected JSplitPane splResultados = new JSplitPane();
	protected JPanel marx = new JPanel(new BorderLayout(0, 0));
	protected JPanel friedman = new JPanel(new BorderLayout(0, 0));

	// lista de resultados
	protected JList<Object> lstResults;
	protected JScrollPane scrResults;

	// elementos do friedman
	protected JLabel lblTitleFriedman = new JLabel("Detalhes");
	JPanel pnlEditorMor = new JPanel(new BorderLayout(0, 0));

	protected InternalState(JanelaPrincipal janela_) {
		super(janela_);

		// ajustando o myPainel
		myPainel.setLayout(new BorderLayout(0, 0));

		// montando taskbar - acima
		taskPan.setBackground(new Color(245, 245, 245));
		taskPan.setLayout(new BorderLayout(20, 0));

		// taskbar titulo
		FlowLayout flowLayout = (FlowLayout) taskPanTitle.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		taskPan.add(taskPanTitle, BorderLayout.NORTH);
		lblFuncionarios.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		taskPanTitle.add(lblFuncionarios);

		// taskbar botoes
		FlowLayout flowLayout_1 = (FlowLayout) taskPanItems.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		taskPan.add(taskPanItems, BorderLayout.CENTER);

		myPainel.add(taskPan, BorderLayout.NORTH);

		// barra de resultados - abaixo
		resultPan.setLayout(new BorderLayout(0, 0));

		// splitpan
		splResultados.setResizeWeight(.6d);
		splResultados.setBorder(BorderFactory.createEmptyBorder());

		// esquerda
		marx.setBorder(BorderFactory.createEmptyBorder());
		splResultados.setLeftComponent(marx);
		// direita
		friedman.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		splResultados.setRightComponent(friedman);

		resultPan.add(splResultados);
		myPainel.add(resultPan, BorderLayout.CENTER);

		// montando elementos do friedman
		lblTitleFriedman.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		friedman.add(lblTitleFriedman, BorderLayout.NORTH);

		pnlEditorMor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		friedman.add(pnlEditorMor, BorderLayout.CENTER);

		// montando lista de resultados
		lstResults = new JList<Object>();
		lstResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstResults.setLayoutOrientation(JList.VERTICAL);
		lstResults.setVisibleRowCount(-1);
		lstResults.setCellRenderer(new RenderizadorResultados());
		lstResults.setFixedCellHeight(50);
		lstResults.setBorder(BorderFactory.createEmptyBorder());
		// o scroller dela
		scrResults = new JScrollPane(lstResults);
		scrResults.setBackground(new Color(5, 245, 5));
		scrResults.setBorder(BorderFactory.createEmptyBorder());
		marx.add(scrResults, BorderLayout.CENTER);

		//

	}

	public void setTitle(String novo) {
		lblFuncionarios.setText(novo);
	}

	// preenchendo lista de resultados
	public void preencherLista(Object[] dados) {

		lstResults.setListData(dados);
	}

	// renderizador da lista de resultados
	public class RenderizadorResultados extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;
		private Font fntResults = new Font("Segoe UI", Font.PLAIN, 24);

		public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);
			label.setLayout(new BorderLayout(0, 0));
			label.setHorizontalTextPosition(JLabel.RIGHT);
			label.setFont(fntResults);
			label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			return label;
		}
	}

}
