package views.states;

import javax.swing.JButton;
import javax.swing.JTextField;

import views.JanelaPrincipal;

public class StateFuncAdmin extends InternalState{
	
	//criando botoes
	private JButton btnAll = new JButton("Ver todos");
	private JButton btnAdd = new JButton("Adicionar novo");
	private JButton btnFind = new JButton("Pesquisar");
	
	//criando campo pesquisa
	private JTextField txtFind = new JTextField(52);
	
	protected StateFuncAdmin(JanelaPrincipal janela_) {
		super(janela_);
		this.setTitle("Funcionarios Administrativos");
		
		
		//montar botoes
		taskPanItems.add(btnAll);
		taskPanItems.add(btnAdd);
		taskPanItems.add(txtFind);
		taskPanItems.add(btnFind);
		
	}
}
