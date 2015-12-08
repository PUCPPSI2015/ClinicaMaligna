package views.states;

import javax.swing.JPanel;

import views.JanelaPrincipal;

public abstract class State {
	protected boolean ativo;
	protected JanelaPrincipal janela;

	protected JPanel myPainel = new JPanel();

	protected State(JanelaPrincipal janela_) {
		this.janela = janela_;
	}

	protected void montar() {
		ativo = true;
	}

	protected void desmontar() {
		ativo = false;

	}

	public JPanel getPainel() {
		return this.myPainel;
	}

	protected boolean isAtivo() {
		return ativo;
	}
}
