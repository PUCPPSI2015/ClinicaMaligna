package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import model.LoginModel;
import model.dbos.Acesso;
import views.states.*;
import views.*;

public class ControllerLogin extends ControllerState{
	
	protected static StateLogin este;
	
	public static ActionListener loginAction(JanelaPrincipal janela_, StateLogin login){
		este = login;
		
		return new LoginHandler(janela_);
	}
	public static String getStackTrace(final Throwable throwable) {
	     final StringWriter sw = new StringWriter();
	     final PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     return sw.getBuffer().toString();
	}
	
	static class LoginHandler implements ActionListener{
		char[] senha;
		String ID;
		JanelaPrincipal janela;
		Acesso resultado;
		
		public LoginHandler(JanelaPrincipal janela_){
			super();
			janela = janela_;
			
		}
		
		public void actionPerformed(ActionEvent arg0) {
			ID = este.getId();
			senha = este.getSenha();
			
			if ( ID.equals("") || senha.equals("")  )
			{
				JOptionPane.showMessageDialog( janela, "Favor preencher os campos");
			}	
			else if ( ID.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*") == true )
			{
				JOptionPane.showMessageDialog( janela, "Campo de id errado, nao use letras");			
			}
			else{
				try {
					resultado = LoginModel.login(ID, senha);
					if(resultado==null)
						JOptionPane.showMessageDialog( janela, "Login e senha invalidos");
					else{
						ControllerPrincipal.logar(resultado.getLogin());
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog( janela, "Erro de conexão com o banco");
					String message = getStackTrace(e);
					System.out.println(message);
				}
			}
		}
	}
	
}
