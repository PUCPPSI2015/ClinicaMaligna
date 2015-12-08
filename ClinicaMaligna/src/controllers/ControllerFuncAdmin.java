package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import views.AdicionarJanelaFunc;
import views.states.StateFuncAdmin;
import model.FuncAdminModel;
import model.LoginModel;
import model.dbos.FuncAdmin;
import model.dbos.Acesso;

public class ControllerFuncAdmin {
	private static Random rand;

	public static FuncAdmin[] getAll() {
		return FuncAdminModel.getAll();
	}

	public static void updateFriedman(StateFuncAdmin state, FuncAdmin ocara) {
		LoginModel.listaRefresh();
		Acesso acessoDoCara = LoginModel.getAcesso(ocara.getId() + "");

		state.updateFriedman(ocara.getNome(), ocara.getCargo(),
				acessoDoCara.getSenha(), acessoDoCara.getLogin());
	}

	private static char[] alphanumeric() {
		StringBuffer buf = new StringBuffer(128);
		for (int i = 48; i <= 57; i++)
			buf.append((char) i); // 0-9
		for (int i = 65; i <= 90; i++)
			buf.append((char) i); // A-Z
		for (int i = 97; i <= 122; i++)
			buf.append((char) i); // a-z
		return buf.toString().toCharArray();
	}

	public static String novaSenha(int len) {
		rand = new Random();
		StringBuffer out = new StringBuffer();
		char[] alphanumeric = alphanumeric();

		while (out.length() < len) {
			int idx = Math.abs((rand.nextInt() % alphanumeric.length));
			out.append(alphanumeric[idx]);
		}
		return out.toString();
	}

	public static ListSelectionListener mudouLista(StateFuncAdmin state) {
		return new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {

				@SuppressWarnings({ "unchecked", "rawtypes" })
				JList<FuncAdmin> list = (JList) listSelectionEvent.getSource();
				int selections[] = list.getSelectedIndices();
				java.util.List<FuncAdmin> selectionValues = list
						.getSelectedValuesList();
				int sel = -1;
				for (int i = 0, n = selections.length; i < n; i++) {
					sel = selectionValues.get(i).getId();

				}
				if (sel == -1)
					return;
				updateFriedman(state, FuncAdminModel.getOne(sel));

			}

		};
	}

	// ouvir botoes
	public static ActionListener btnNovaSenha(StateFuncAdmin state) {
		return new NovaSenhaAction(state);
	}

	private static class NovaSenhaAction implements ActionListener {
		private StateFuncAdmin s;

		public NovaSenhaAction(StateFuncAdmin state) {
			super();
			this.s = state;
		}

		public void actionPerformed(ActionEvent e) {
			this.s.setSenha(novaSenha(4));

		}

	}

	public static ActionListener btnNovaSenhaJanela(AdicionarJanelaFunc janela) {
		return new NovaSenhaJanelaAction(janela);
	}

	private static class NovaSenhaJanelaAction implements ActionListener {
		private AdicionarJanelaFunc j;

		public NovaSenhaJanelaAction(AdicionarJanelaFunc janela) {
			super();
			this.j = janela;
		}

		public void actionPerformed(ActionEvent e) {
			this.j.setSenha(novaSenha(4));

		}

	}

	public static ActionListener btnSalvar(StateFuncAdmin state) {
		return new SalvarAction(state);
	}

	private static class SalvarAction implements ActionListener {
		private StateFuncAdmin s;

		public SalvarAction(StateFuncAdmin state) {
			super();
			this.s = state;
		}

		public void actionPerformed(ActionEvent e) {
			int id = this.s.getId();
			String nome = this.s.getNome();
			String senha = this.s.getSenha();
			if ((nome.equals("")) || (nome.length() < 4)) {
				ControllerPrincipal.gritar("Nome muito curto");
				return;
			}
			if ((senha.equals("")) || (senha.length() < 4)) {
				ControllerPrincipal.gritar("Senha muito curta");
				return;
			}
			FuncAdminModel.updateFun(id, nome, this.s.getCargo(), senha);
			this.s.preencherLista(getAll());
			this.s.updateFriedman();
		}

	}

	public static ActionListener btnExcluir(StateFuncAdmin state) {
		return new Excluir(state);
	}

	private static class Excluir implements ActionListener {
		private StateFuncAdmin s;

		public Excluir(StateFuncAdmin state) {
			super();
			this.s = state;
		}

		public void actionPerformed(ActionEvent e) {
			FuncAdmin eu = (FuncAdmin) ControllerPrincipal.getObjLogado();
			if(eu.getId() == this.s.getId()){
				ControllerPrincipal.gritar("Voce nao pode se excluir a si mesmo!");
				return;
			}
			if (!ControllerPrincipal.perguntar(
					"Ter certeza que deseja excluir?", "Isto é irreversível"))
				return;
			
			FuncAdminModel.deletaFun(this.s.getId());
			this.s.preencherLista(getAll());
		}

	}

	public static ActionListener btnAdicionar(StateFuncAdmin state) {
		return new AdicionarFunc(state);
	}

	private static class AdicionarFunc implements ActionListener {
		private StateFuncAdmin s;

		public AdicionarFunc(StateFuncAdmin state) {
			super();
			this.s = state;
		}

		public void actionPerformed(ActionEvent e) {
			new AdicionarJanelaFunc(this.s);

		}

	}

	public static ActionListener btnSalvarNovo(AdicionarJanelaFunc janela,
			StateFuncAdmin state) {
		return new SalvarNovoAction(janela, state);
	}

	private static class SalvarNovoAction implements ActionListener {
		private AdicionarJanelaFunc j;
		private StateFuncAdmin s;

		public SalvarNovoAction(AdicionarJanelaFunc janela, StateFuncAdmin state) {
			super();
			this.s = state;
			this.j = janela;
		}

		public void actionPerformed(ActionEvent e) {
			String nome = this.j.getNome();
			String senha = this.j.getSenha();
			if ((nome.equals("")) || (nome.length() < 4)) {
				ControllerPrincipal.gritar("Nome muito curto");
				return;
			}
			if ((senha.equals("")) || (senha.length() < 4)) {
				ControllerPrincipal.gritar("Senha muito curta");
				return;
			}

			FuncAdminModel.insertFunc(this.j.getNome(), this.j.getCargo(),
					this.j.getSenha());
			this.s.preencherLista(getAll());
			this.s.updateFriedman();
			this.j.setVisible(false);
			this.j.dispose();
		}

	}

	public static void pesquisarFunc(StateFuncAdmin s) {
		s.preencherLista(FuncAdminModel.getPesquisa(s.getPesquisa()));
	}

}
