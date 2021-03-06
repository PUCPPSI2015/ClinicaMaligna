package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import model.Conexao;
import model.ProfSaudeModel;
import model.dbos.EmpregadoClinica;
import views.JanelaPrincipal;
import views.states.StateInicio;
import views.states.StateLogin;
import views.states.States;

@SuppressWarnings("unused")
public class ControllerPrincipal {

	private static boolean logado = true;
	private static String id;
	private static States states;
	private static boolean admin = false;
	private static EmpregadoClinica objLogado = null;

	private static JanelaPrincipal janela = new JanelaPrincipal();

	public static void main(String[] args) throws Exception {

		if (!Conexao.test())
			return;
		Conexao.iniciar();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		states = new States(janela);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				janela.setVisible(true);
				deslogar();
			}
		});

		System.out.println("Iniciando app!");
	}

	/*
	 * metodos de logon e logoff
	 */
	public static void logar(EmpregadoClinica id_) {
		if (id_ == null)
			return;
		objLogado = id_;
		logado = true;
		janela.definirLogado();
		janela.setTitle("Bem vindo: " + objLogado.toString());

		if (id_.isProfsaude()) {
			StateInicio.ativarProfSaude();
			ControllerRegistro.logou();
			janela.ativarProfSaude();
		} else if (id_.isFuncadmin()) {
			StateInicio.ativarFuncadmin();
			janela.ativarFuncadmin();
		}

		states.go("inicio");

	}

	public static void deslogar() {
		id = "";
		objLogado = null;
		logado = false;
		janela.definirDeslogado();
		states.go("login");

	}

	public static boolean isLogado() {
		return logado;
	}

	public static void setAdmin(boolean q) {
		admin = q;
	}

	private boolean isAdmin() {
		return admin;
	}

	public static EmpregadoClinica getObjLogado() {
		return objLogado;
	}

	public static void setObjLogado(EmpregadoClinica objLogado) {
		ControllerPrincipal.objLogado = objLogado;
	}

	/*
	 * munca de states
	 */
	public static ActionListener mudador(String onde) {
		return new Mudador(onde);
	}

	static class Mudador implements ActionListener {
		private String destino;

		public Mudador(String onde) {
			super();
			destino = onde;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (destino.equals("sair")) {
				deslogar();
			} else {
				states.go(destino);
			}
		}

	}

	// janelas
	public static void gritar(String oq, String titulo) {
		JOptionPane.showMessageDialog(janela, oq, titulo,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void gritar(String oq) {
		JOptionPane.showMessageDialog(janela, oq);
	}

	public static boolean perguntar(String oq, String titulo) {
		int escolha = JOptionPane.showConfirmDialog(janela, oq, titulo,
				JOptionPane.OK_CANCEL_OPTION);
		if (escolha == JOptionPane.OK_OPTION) {
			return true;
		}
		return false;
	}

	// helpers
	// helper para ver se string � int
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	// helper para ver se string � log
	public static boolean isLong(String s) {
		try {
			Long.parseLong(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

}
