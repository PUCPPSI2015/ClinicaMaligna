package views.states;


import java.awt.BorderLayout;

import javax.swing.JPanel;

import views.JanelaPrincipal;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;



public abstract class State {
	protected boolean ativo;
	protected JanelaPrincipal janela;

	protected JPanel myPainel = new JPanel();
	
	protected State(JanelaPrincipal janela_){
		
		this.janela = janela_;
	}
	
	protected void montar(){
		ativo = true;

		System.out.println("Chegou aqui");
	}
	protected void desmontar(){
		ativo  = false;
		
	}
	public JPanel getPainel(){
		return this.myPainel;
	}
	protected boolean isAtivo(){
		return ativo;
	}
}
