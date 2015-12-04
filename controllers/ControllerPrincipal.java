package controllers;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import model.Conexao;
import views.JanelaPrincipal;
import views.states.StateLogin;
import views.states.States;

@SuppressWarnings("unused")
public class ControllerPrincipal {
	
	private static boolean logado = true; 
	private static String id;
	private static States states; 
	private static boolean admin = false;
	private static Object objLogado;
	
	
	private static JanelaPrincipal janela = new JanelaPrincipal();
	

	public static void main(String[] args) throws Exception{
		
		if(!Conexao.test()) return;
		Conexao.iniciar();
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
        		//deslogar();
            	logar("teste");
            }
        });

		System.out.println("Iniciando app!");
	}


	
	/*
	 * metodos de logon e logoff
	 * */
	public static void logar(Object id_){
			objLogado = id_;
			logado = true;
			janela.definirLogado();
			janela.setTitle("Bem vindo: " + objLogado.toString());
			//states.go("inicio");
			states.go("profissionais saude");
	}
	public static void deslogar(){
			id = "";
			logado = false;
			janela.definirDeslogado();
			states.go("login");
		
	}
	public static boolean isLogado(){
		return logado;
	}
	public static void setAdmin(boolean q){
		admin = q;
	} 
	private boolean isAdmin(){
		return admin;
	}
	
	
	/*
	 * munca de states
	 * */
	public static ActionListener mudador(String onde){
		return new Mudador(onde);
	}
	static class Mudador implements ActionListener{
		private String destino;
		public Mudador(String onde){
			super();
			destino = onde;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(destino.equals("sair")){
				deslogar();
			}
			else{
				states.go(destino);
			}
		}
		
	}
	

	
	
	

}
