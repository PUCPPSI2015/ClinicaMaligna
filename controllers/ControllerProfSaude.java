package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
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
		ProfissaoArvore arvore = state.getArvore();
		LoginModel.listaRefresh();
		Acesso acessoDoCara = LoginModel.getAcesso("m" + oid);
		
		
		arvore.setSelectionPath(new TreePath((TreeNode) arvore.getModel().getRoot())); //esvaziar selecao arvore
		EspecializacoesModel.listaRefresh();
		int[] especializacoes = EspecializacoesModel.getProfEsp(ocara.getId());

		TreeSelectionModel sm = arvore.getSelectionModel();
		System.out.println("Vamos entrar na funcao");
		arvore.selecionarEspecialidade(Especialidades.getByArrayDeIds(especializacoes));
		
		
		
		

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
    			if (!listSelectionEvent.getValueIsAdjusting()) {
    				
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
    		TreePath[] sel =  s.getArvore().getQuem();
    		int[] espes = new int[sel.length];
    		for(int i = 0; i < sel.length; i++){
    			String esta = sel[i].getLastPathComponent().toString();
    			espes[i] = Especialidades.getOneByName(esta).getId();
    		}
    		
    		ProfSaudeModel.updateProf(s.getId(), s.getNome(), s.getSenha(), s.getCpf(), s.getIdClasseA(), s.getIdClasse(),espes);
    		this.s.preencherLista(getAll());
    		EspecializacoesModel.listaRefresh();
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
    		ProfSaudeModel.deletaProf(this.s.getId(), this.s.getIdClasseA());
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
    		TreePath[] sel =  this.j.getArvore().getQuem();
    		int[] espes = new int[sel.length];
    		for(int i = 0; i < sel.length; i++){
    			String esta = sel[i].getLastPathComponent().toString();
    			espes[i] = Especialidades.getOneByName(esta).getId();
    		}
    		
    		ProfSaudeModel.insertProf(this.j.getNome(), this.j.getCpf(), this.j.getSenha(), this.j.getIdClasse(), espes);
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
    public static ProfissaoArvore montarArvore(){
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

    	
    	return new ProfissaoArvore(root);
    	
    }
    public static class ProfissaoArvore extends JTree{
    	public ProfissaoArvore(DefaultMutableTreeNode root){
    		super(root);
    	}
    	public void selecionarEspecialidade(Object[] object) {
    		fechaTudo();
    		TreeNode rootNode  = (TreeNode)this.getModel().getRoot();
    		TreePath[] path = new TreePath[object.length];
    		boolean ade = false;
    		for(int p=0; p<object.length; p++) {   
    			path[p] = new TreePath(rootNode);

    			for(int i=0; i<rootNode.getChildCount(); i++) { 
    				TreeNode profissao = rootNode.getChildAt(i);
    				for(int j=0; j<profissao.getChildCount(); j++) { 
    					TreeNode especialidade = profissao.getChildAt(j);
						if(especialidade.toString().equals(object[p].toString())){
							
							path[p] = path[p].pathByAddingChild(profissao);
							ade = true;
							
							path[p] = path[p].pathByAddingChild(especialidade);
							break;
						}
					}

				}

				
			}
			
			this.setExpandsSelectedPaths(true);
			this.setSelectionPaths(path);

		}
		public TreePath[] getQuem(){
			return this.getSelectionPaths();
		}
		public void fechaTudo() {
			TreeNode root = (TreeNode) this.getModel().getRoot();
			fechaTudo(new TreePath(root));
		}

		private void fechaTudo(TreePath parent) {
			TreeNode node = (TreeNode) parent.getLastPathComponent();
			if (node.getChildCount() >= 0) {
				for (Enumeration e = node.children(); e.hasMoreElements();) {
					TreeNode n = (TreeNode) e.nextElement();
					TreePath path = parent.pathByAddingChild(n);
					fechaTudo(path);
				}
			}
			this.collapsePath(parent);
		}
	}
}
