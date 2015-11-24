package views.states;

import javax.swing.JButton;
import javax.swing.JTextField;

import views.JanelaPrincipal;

public class StateProfSaude extends InternalState{
	
	//criando botoes
	private JButton btnAll = new JButton("Ver todos");
	private JButton btnAdd = new JButton("Adicionar novo");
	private JButton btnFind = new JButton("Pesquisar");
		
	//criando campo pesquisa
	private JTextField txtFind = new JTextField(52);
	
	protected StateProfSaude(JanelaPrincipal janela_) {
		super(janela_);

		this.setTitle("Profissionais da saude");
		
		//montar botoes
		taskPanItems.add(btnAll);
		taskPanItems.add(btnAdd);
		taskPanItems.add(txtFind);
		taskPanItems.add(btnFind);
		
	}

}
