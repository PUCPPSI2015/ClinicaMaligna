package views.states;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;

import model.dbos.EmpregadoClinica;
import controllers.ControllerPrincipal;
import views.JanelaPrincipal;

/*
 * Aqui wrapa e controlla os states
 * */
public class States {
	private static Map<String, State> states = new HashMap<String, State>();
	private JanelaPrincipal janela;
	private CardLayout layout;

	public States(JanelaPrincipal janela_) {
		this.janela = janela_;
		this.layout = (CardLayout) janela_.getAreaStates().getLayout();

		/*
		 * criando states
		 */

		adicionarState("login", new StateLogin(this.janela));
		adicionarState("inicio", new StateInicio(this.janela));
		adicionarState("funcionarios administrativos", new StateFuncAdmin(
				this.janela));
		adicionarState("profissionais saude", new StateProfSaude(this.janela));
		adicionarState("agendamento", new StateAgendamento(this.janela));
		adicionarState("registro", new StateRegistro(this.janela));

	}

	public void adicionarState(String nome, State este) {

		this.janela.getAreaStates().setBackground(Color.black);
		este.getPainel().setBackground(Color.white);
		states.put(nome, este);
		this.janela.getAreaStates().add(este.getPainel(), BorderLayout.CENTER);
		this.janela.getAreaStates().getLayout()
				.addLayoutComponent(nome, este.getPainel());

	}

	public void go(String nome_) {
		EmpregadoClinica logado = ControllerPrincipal.getObjLogado();
		boolean isLogado = ControllerPrincipal.isLogado();
		if (!isLogado && (!nome_.equals("login"))) {
			ControllerPrincipal.deslogar();
			return;
		}
		if (logado != null) {
			if (logado.isFuncadmin()) {
				if (nome_.equals("registro"))
					return;
			} else if (logado.isProfsaude()) {
				if ((nome_.equals("funcionarios administrativos"))
						|| (nome_.equals("profissionais saude"))
						|| (nome_.equals("agendamento")))
					return;

			}
		}
		states.get(nome_).montar();
		this.layout.show(janela.getAreaStates(), nome_);
	}
}
