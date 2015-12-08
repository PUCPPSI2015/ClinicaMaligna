package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import model.FuncAdminModel;
import model.LoginModel;
import model.ProfSaudeModel;
import model.dbos.Acesso;
import model.dbos.FuncAdmin;
import model.dbos.ProfSaude;
import views.states.*;
import views.*;

public class ControllerLogin extends ControllerState {

	protected static StateLogin este;

	// helper para ver se string é int
	private static boolean isInteger(String s) {
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

	public static ActionListener loginAction(JanelaPrincipal janela_,
			StateLogin login) {
		este = login;

		return new LoginHandler(janela_);
	}

	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	static class LoginHandler implements ActionListener {
		char[] senha;
		String ID;
		JanelaPrincipal janela;
		Acesso resultado;

		public LoginHandler(JanelaPrincipal janela_) {
			super();
			janela = janela_;

		}

		public void actionPerformed(ActionEvent arg0) {

			if (ControllerPrincipal.isLogado())
				return;
			ID = este.getId();
			senha = este.getSenha();

			if (ID.equals("") || senha.equals("")) {
				JOptionPane.showMessageDialog(janela,
						"Favor preencher os campos");
			} else if (ID.matches("[a-zA-z]+([ '-][a-zA-Z]+)*") == true) {
				JOptionPane.showMessageDialog(janela,
						"Campo de id errado, nao use letras");
			} else {
				try {
					resultado = LoginModel.login(ID, senha);
					if (resultado == null)
						JOptionPane.showMessageDialog(janela,
								"Login e senha invalidos");
					else {

						// if is admin
						if (isInteger(resultado.getLogin())) {
							FuncAdmin recuperado = FuncAdminModel
									.getOne(Integer.parseInt(resultado
											.getLogin()));
							ControllerPrincipal.setAdmin(true);
							ControllerPrincipal.logar(recuperado);
						} else {
							char fc = resultado.getLogin().charAt(0);
							String subs = resultado.getLogin().substring(1);
							ControllerPrincipal.setAdmin(false);
							ProfSaude recuperado;
							if (fc == 'm') { // eh acesso com o id de profsaude
								recuperado = ProfSaudeModel.getOne(Integer
										.parseInt(subs));
							} else if (fc == 'c') { // ou com idclasse
								recuperado = ProfSaudeModel
										.getOneByIdClasse(subs);
							} else
								return;

							if (recuperado == null) {
								JOptionPane.showMessageDialog(janela,
										"Login e senha invalidos");
								return;
							}
							ControllerPrincipal.logar(recuperado);
						}

					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(janela,
							"Erro de conexão com o banco");
					String message = getStackTrace(e);
					System.out.println(message);
				}
			}
		}
	}

}
