package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import views.states.*;
import views.*;

public class ControllerLogin extends ControllerState{
	
	protected static StateLogin este;
	
	public static ActionListener loginAction(JanelaPrincipal janela_, StateLogin login){
		este = login;
		
		return new LoginHandler(janela_);
	}
	
	static class LoginHandler implements ActionListener{
		char[] Senha;
		String ID;
		JanelaPrincipal janela;
		
		public LoginHandler(JanelaPrincipal janela_){
			super();
			janela = janela_;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			ID = este.getId();
			Senha = este.getSenha();
			
			if ( ID.equals("") || Senha.equals("")  )
			{
				JOptionPane.showMessageDialog( janela, "Favor preencher os campos");
			}	
			else if ( ID.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*") == true )
			{
				JOptionPane.showMessageDialog( janela, "Campo de id errado, nao use letras");			
			}
			else{
				ControllerPrincipal.logar(ID);
			}
		}
	}
	
}
