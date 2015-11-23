package views.states;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;

import views.JanelaPrincipal;

/*
 * Aqui controlla os states
 * */
public class States {
	private static Map<String, State> states = new HashMap<String, State>();
	private JanelaPrincipal janela;
	private CardLayout layout;
	
	
	public States(JanelaPrincipal janela_){
		this.janela = janela_;
		this.layout = (CardLayout)janela_.getAreaStates().getLayout();
		
		/*
		 * criando states
		 * */
		
		adicionarState("login", new StateLogin(this.janela));
		
	} 
	public void adicionarState(String nome, State este){
		


		this.janela.getAreaStates().setBackground(Color.black);
		este.getPainel().setBackground(Color.white);
		states.put(nome, este);
		this.janela.getAreaStates().add(este.getPainel(),BorderLayout.CENTER);	
		this.janela.getAreaStates().getLayout().addLayoutComponent(nome, este.getPainel());



	}
	
	
	public void go(String nome_){
		System.out.println("Indo para " + nome_);
		this.janela.limparArea();
		states.get(nome_).montar(); 
		this.layout.show(janela.getAreaStates(), nome_);
		
	}
}
