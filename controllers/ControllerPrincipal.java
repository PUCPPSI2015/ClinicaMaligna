package controllers;



import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import views.JanelaPrincipal;
import views.states.States;

@SuppressWarnings("unused")
public class ControllerPrincipal {
	
	private static boolean logado = false; 
	private static int id;
	private static String usuario, stateAtivo;
	private static States states; 
	
	
	private static JanelaPrincipal janela = new JanelaPrincipal();
	

	public static void main(String[] args) throws Exception{
		
		try {
			UIManager.setLookAndFeel(
				UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {

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
	 * metodos relacionados com a janela
	 * */
	
	

	
	/*
	 * metodos de logon e logoff
	 * */
	public static void logar(String nome, int id_){
		id = id_;
		usuario = nome;
		logado = true;
		janela.definirLogado();
	}
	public static void deslogar(){
		id = 0;
		usuario = "";
		logado = false;
		janela.definirDeslogado();
		states.go("login");
	}
	public static boolean isLogado(){
		return logado;
	} 
	
	/*
	 * metodos relacionados com states
	 * */
	public static void irParaState(String state){
		
	}
	
	
	
	

}
