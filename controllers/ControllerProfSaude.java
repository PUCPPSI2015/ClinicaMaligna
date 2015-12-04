package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import controllers.ControllerProfSaude.MeuJCombo;
import model.LoginModel;
import model.ProfSaudeModel;
import model.EspecializacoesModel;
import model.DisponibilidadesModel;
import model.DisponibilidadesModel.Disponibilidade;
import model.dbos.Acesso;
import model.dbos.ProfSaude;
import model.harddata.Especialidades;
import model.harddata.Profissoes;
import model.harddata.Especialidades.Especialidade;
import model.harddata.Profissoes.Profissao;
import views.AdicionarJanelaProf;
import views.states.StateProfSaude;
import views.states.StateProfSaude.MeuSpiner;

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
		
		arvore.selecionarEspecialidade(Especialidades.getByArrayDeIds(especializacoes));
		
		//disponibilidades
		//preencher as combobox
		state.preencherCbx(oid);

		//resetar spinners
		state.resetSpinners();
		
		//ver se ele tem algum no domungo
		if(state.setCbx( 1, ( DisponibilidadesModel.getByProfAndDia(oid, 1) != null))){
			Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(oid, 1); 
			state.setInOutEsp(disp.getDiaDaSemana(), disp.getInicio(), disp.getFim(),Especialidades.getOne(disp.getIdEspecialidade()));
		}
		//na segunda
		if(state.setCbx( 2, ( DisponibilidadesModel.getByProfAndDia(oid, 2) != null))){
			Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(oid, 2); 
			state.setInOutEsp(disp.getDiaDaSemana(), disp.getInicio(), disp.getFim(),Especialidades.getOne(disp.getIdEspecialidade()));
		}
		
		//na TERÇA
		if(state.setCbx( 3, ( DisponibilidadesModel.getByProfAndDia(oid, 3) != null))){
			Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(oid, 3); 
			state.setInOutEsp(disp.getDiaDaSemana(), disp.getInicio(), disp.getFim(),Especialidades.getOne(disp.getIdEspecialidade()));
		}
		//na QUARTA
		if(state.setCbx( 4, ( DisponibilidadesModel.getByProfAndDia(oid, 4) != null))){
			Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(oid, 4); 
			state.setInOutEsp(disp.getDiaDaSemana(), disp.getInicio(), disp.getFim(),Especialidades.getOne(disp.getIdEspecialidade()));
		}
		//na quinta
		if(state.setCbx( 5, ( DisponibilidadesModel.getByProfAndDia(oid, 5) != null))){
			Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(oid, 5); 
			state.setInOutEsp(disp.getDiaDaSemana(), disp.getInicio(), disp.getFim(),Especialidades.getOne(disp.getIdEspecialidade()));
		}
		//na sexta
		if(state.setCbx( 6, ( DisponibilidadesModel.getByProfAndDia(oid, 6) != null))){
			Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(oid, 6); 
			state.setInOutEsp(disp.getDiaDaSemana(), disp.getInicio(), disp.getFim(),Especialidades.getOne(disp.getIdEspecialidade()));
		}
		//no sabado
		if(state.setCbx( 7, ( DisponibilidadesModel.getByProfAndDia(oid, 7) != null))){
			Disponibilidade disp = DisponibilidadesModel.getByProfAndDia(oid, 7); 
			state.setInOutEsp(disp.getDiaDaSemana(), disp.getInicio(), disp.getFim(),Especialidades.getOne(disp.getIdEspecialidade()));
		}
		
		;
		
		
		
		
		
		

		state.updateFriedman(ocara.getNome(), ocara.getCpf(), acessoDoCara.getSenha(), ocara.getId(), ocara.getIdClasse());
		
		//!!atualizar lista de especializacoes, disponibilidades e profissionais
		
		
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
    	state.updateFriedman();
    	return new SalvarAction(state);
    }
    private static class SalvarAction implements ActionListener{
    	private StateProfSaude s;
    	public SalvarAction(StateProfSaude state){
    		this.s = state; 
    	}
    	
    	public void actionPerformed(ActionEvent e) {
    		TreePath[] sel =  s.getArvore().getQuem();
    		int[] espes;
    		Disponibilidade[] disponibilidades = s.getDisponibilidades();
    		if(sel.length >= 0){
    			espes = new int[sel.length];
    			for(int i = 0; i < sel.length; i++){
        			String esta = sel[i].getLastPathComponent().toString();
        			espes[i] = Especialidades.getOneByName(esta).getId();
        		}
    		}
    		else{
    			espes = new int[1];
    			espes[0] = 0;
    		}

    		ProfSaudeModel.updateProf(s.getId(), s.getNome(), s.getSenha(), s.getCpf(), s.getIdClasseA(), s.getIdClasse(),espes, disponibilidades);
    		this.s.preencherLista(getAll());
    		EspecializacoesModel.listaRefresh();
    		this.s.hideFriedman();
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
    		ProfSaudeModel.inativaProf(this.s.getId());
    		this.s.preencherLista(getAll());
    		this.s.hideFriedman();
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
    		Disponibilidade[] disponibilidades = j.getDisponibilidades();
    		ProfSaudeModel.insertProf(this.j.getNome(), this.j.getCpf(), this.j.getSenha(), this.j.getIdClasse(), espes, disponibilidades);
    		this.s.preencherLista(getAll());
    		this.s.hideFriedman();
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
    public static ItemListener checkDiasSemana(AdicionarJanelaProf s, JCheckBox ch, JSpinner in, JSpinner out) {
    	return new CheckDiasSemanaListener(s, ch, in, out);
	}
    public static ItemListener checkDiasSemana(StateProfSaude s, JCheckBox ch, JSpinner in, JSpinner out, MeuJCombo esp){
		return new CheckDiasSemanaListener(s, ch, in, out, esp);
	}
	public static class CheckDiasSemanaListener implements ItemListener{
		private AdicionarJanelaProf janela;
		private StateProfSaude state;
		private JCheckBox ch;
		private JSpinner in, out;
		private MeuJCombo esp;
		public CheckDiasSemanaListener(StateProfSaude s, JCheckBox ch_, JSpinner i, JSpinner ou, MeuJCombo es){
			state = s;
			ch = ch_;
			in = i;
			out = ou;
			esp = es;
		}
		public CheckDiasSemanaListener(AdicionarJanelaProf s, JCheckBox ch_, JSpinner i, JSpinner ou){
			janela = s;
			ch = ch_;
			in = i;
			out = ou;
			esp = null;
		}
		public void itemStateChanged(ItemEvent arg0) {
			in.setEnabled(ch.isSelected());
			out.setEnabled(ch.isSelected());
			if(esp != null)
				esp.setEnabled(ch.isSelected());
		}
	}
	public static MeuJCombo novoComboBox(StateProfSaude s, int dia){
		return new MeuJCombo<Especialidade>(s, dia);
	}
	public static MeuJCombo novoComboBox(AdicionarJanelaProf s, int dia){
		return new MeuJCombo<Especialidade>(s, dia);
	}
	public static class MeuJCombo<E> extends JComboBox{
		private static final long serialVersionUID = 1L;
		private StateProfSaude state;
		private int dia;
		private AdicionarJanelaProf janela;
		public MeuJCombo(StateProfSaude s, int dia_){
			super();
			this.state = s;
			this.dia = dia_;
		}
		public MeuJCombo(AdicionarJanelaProf s, int dia_){
			super();
			this.janela = s;
			this.dia = dia_;
		}
		@SuppressWarnings("unchecked")
		public void preencher(int prof){
			System.out.println("Preencher para o " + prof);
			int[] especializacoes = EspecializacoesModel.getProfEsp(prof);
			Especialidade[] especialidadesDesseCara = Especialidades.getByArrayDeIds(especializacoes);

			this.setModel(new DefaultComboBoxModel<Especialidade>(especialidadesDesseCara));
		}
		
	}
	
	 
}
