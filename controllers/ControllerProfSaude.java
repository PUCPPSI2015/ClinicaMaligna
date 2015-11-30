package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.LoginModel;
import model.ProfSaudeModel;
import model.EspecializacoesModel;
import model.dbos.Acesso;
import model.dbos.ProfSaude;
import model.harddata.Especialidades;
import model.harddata.Profissoes;
import model.harddata.Especialidades.Especialidade;
import model.harddata.Profissoes.Profissao;
import views.AdicionarJanelaProf;
import views.states.StateProfSaude;

public class ControllerProfSaude {
	private static Random rand;
	public static ProfSaude[] getAll(){
		return  ProfSaudeModel.getAll();
	}
	public static void updateFriedman(StateProfSaude state, ProfSaude ocara){

		int oid = ocara.getId();
		LoginModel.listaRefresh();
		Acesso acessoDoCara = LoginModel.getAcesso("m" + oid);
		
		
		EspecializacoesModel.listaRefresh();
		int[] especializacoes = EspecializacoesModel.getProfEsp(ocara.getId());
		JTree arvore = state.getTree();
		TreeSelectionModel sm = arvore.getSelectionModel();
		;
		
		

		state.updateFriedman(ocara.getNome(), ocara.getCpf(), acessoDoCara.getSenha(), ocara.getId(), ocara.getIdClasse());
	}
	private static char[] alphanumeric(){
        StringBuffer buf=new StringBuffer(128);
        for(int i=48; i<= 57;i++)buf.append((char)i); // 0-9
        for(int i=65; i<= 90;i++)buf.append((char)i); // A-Z
        for(int i=97; i<=122;i++)buf.append((char)i); // a-z
        return buf.toString().toCharArray();
    }
	public static String novaSenha(int len){
		rand = new Random();
		StringBuffer out=new StringBuffer();
		char[] alphanumeric=alphanumeric();
		
        while(out.length() < len){
            int idx=Math.abs(( rand.nextInt() % alphanumeric.length ));
            out.append(alphanumeric[idx]);
        }
        return out.toString();
	}
	public static ListSelectionListener mudouLista(StateProfSaude state){
		return new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent listSelectionEvent) {
		          JList<ProfSaude> list = (JList) listSelectionEvent.getSource();
		          int selections[] = list.getSelectedIndices();
		          java.util.List<ProfSaude> selectionValues = list.getSelectedValuesList();
		          int sel = -1;
		          for (int i = 0, n = selections.length; i < n; i++) {
		        	sel = selectionValues.get(i).getId();
		            
		          }
		          if(sel == -1) return;
		          updateFriedman(state, ProfSaudeModel.getOne(sel));
		        }
		};
	}
	//ouvir botoes
	public static ActionListener btnNovaSenha(StateProfSaude state){
		

		return new NovaSenhaAction(state);
	}
	private static class NovaSenhaAction implements ActionListener{
		private StateProfSaude s;
		public NovaSenhaAction(StateProfSaude state){
			super();
			this.s = state; 
		}
		
		public void actionPerformed(ActionEvent e) {
			this.s.setSenha(novaSenha(4));
			
		}	
	}
	public static ActionListener btnNovaSenhaJanela(AdicionarJanelaProf janela){


		return new NovaSenhaJanelaAction(janela);
	}
	private static class NovaSenhaJanelaAction implements ActionListener{
		private AdicionarJanelaProf j;
		public NovaSenhaJanelaAction(AdicionarJanelaProf janela){
			super();
			this.j = janela; 
		}
		
		public void actionPerformed(ActionEvent e) {
			this.j.setSenha(novaSenha(4));
			
		}
		
	}
	public static ActionListener btnSalvar(StateProfSaude state){
		return new SalvarAction(state);
	}
	private static class SalvarAction implements ActionListener{
		private StateProfSaude s;
		public SalvarAction(StateProfSaude state){
			super();
			this.s = state; 
		}
		
		public void actionPerformed(ActionEvent e) {
			ProfSaudeModel.updateProf(s.getId(), s.getNome(), s.getSenha(), s.getCpf(), s.getCpfA());
			this.s.preencherLista(getAll());
			this.s.updateFriedman();
		}
		
	}
	public static ActionListener btnExcluir(StateProfSaude state){
		return new Excluir(state);
	}
	private static class Excluir implements ActionListener{
		private StateProfSaude s;
		public Excluir(StateProfSaude state){
			super();
			this.s = state; 
		}
		
		public void actionPerformed(ActionEvent e) {
			ProfSaudeModel.deletaProf(this.s.getId(), this.s.getCpfA());
			this.s.preencherLista(getAll());
		}
		
	}
	public static ActionListener btnAdicionar(StateProfSaude state){
		return new AdicionarProf(state);
	}
	private static class AdicionarProf implements ActionListener{
		private StateProfSaude s;
		public AdicionarProf(StateProfSaude state){
			super();
			this.s = state; 
		}
		
		public void actionPerformed(ActionEvent e) {
			new AdicionarJanelaProf(this.s);
			
		}
		
	}
	public static ActionListener btnSalvarNovo(AdicionarJanelaProf janela, StateProfSaude state){
		return new SalvarNovoAction(janela, state);
	}
	private static class SalvarNovoAction implements ActionListener{
		private AdicionarJanelaProf j;
		private StateProfSaude s;
		public SalvarNovoAction(AdicionarJanelaProf janela, StateProfSaude state){
			super();
			this.s = state; 
			this.j = janela; 
		}
		
		public void actionPerformed(ActionEvent e) {
			ProfSaudeModel.insertProf(this.j.getNome(), this.j.getCpf(), this.j.getSenha());
			this.s.preencherLista(getAll());
			this.s.updateFriedman();
			this.j.setVisible(false); 
			this.j.dispose();
		}
		
	}

	public static void pesquisarProf(StateProfSaude s){
		s.preencherLista(ProfSaudeModel.getPesquisa(s.getPesquisa()));
	}
	
	//montar arvore
	public static JTree montarArvore(){
		//montar profissoes
		Profissao[] tpf =  Profissoes.getAll();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		for(int i = 0; i < tpf.length; i++){
			DefaultMutableTreeNode profPasta = new DefaultMutableTreeNode(tpf[i]);
			Especialidade[] msp = Especialidades.getThisProf(tpf[i].getId());
			for(int j = 0; j < msp.length; j++){
				profPasta.add(new DefaultMutableTreeNode(msp[j]));
			}
			root.add(profPasta);
		}

		
		return new JTree(root);
		 
	}
}
