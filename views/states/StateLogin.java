package views.states;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import views.JanelaPrincipal;
import controllers.ControllerLogin;

import javax.swing.border.LineBorder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.DropMode;
import javax.swing.ImageIcon;

public class StateLogin extends State {

	// variaveois de subpainel
	private JPanel loginPanel = new JPanel();

	// variaveois de campo de login
	private JTextField txtId = new JTextField();

	// variaveois de campo de senha
	private JPasswordField pswSenha = new JPasswordField();

	// variaveois de botão
	private JButton btnLogar = new JButton("Logar");
	private final JLabel lblIcone = new JLabel("");

	protected StateLogin(JanelaPrincipal janela_) {
		super(janela_);

		// ajustar mypainel
		myPainel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// montar subpainel
		loginPanel.setBackground(new Color(255, 255, 255));
		loginPanel.setBorder(new LineBorder(new Color(225, 225, 225)));
		loginPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		loginPanel.setPreferredSize(new Dimension(340, 350));
		loginPanel.setLayout(null);

		// montar componentes
		txtId.setBounds(10, 162, 320, 45);
		txtId.setToolTipText("Seu id ou registro profissional");

		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setColumns(4);
		loginPanel.add(txtId);

		pswSenha.setBounds(10, 227, 320, 45);
		pswSenha.setToolTipText("Sua senha");
		pswSenha.setHorizontalAlignment(SwingConstants.CENTER);
		loginPanel.add(pswSenha);

		btnLogar.setBounds(10, 292, 320, 45);
		loginPanel.add(btnLogar);

		lblIcone.setBounds(10, 11, 320, 140);
		lblIcone.setVerticalAlignment(SwingConstants.TOP);
		lblIcone.setIcon(new ImageIcon(StateLogin.class
				.getResource("/material/logo medio.jpg")));
		lblIcone.setForeground(new Color(192, 192, 192));
		lblIcone.setFont(new Font("Corbel", Font.PLAIN, 17));
		lblIcone.setHorizontalAlignment(SwingConstants.CENTER);
		loginPanel.add(lblIcone);

		myPainel.add(loginPanel, gbc);

		// listeners
		btnLogar.addActionListener(ControllerLogin.loginAction(janela, this));

		janela.getRootPane().setDefaultButton(btnLogar);

		// OnButtonClickListener handle = new OnButtonClickListener();
		// btnLogar.addActionListener(handle);
	}

	public char[] getSenha() {
		char[] coisinha = pswSenha.getPassword();
		pswSenha.setText("");
		return coisinha;
	}

	public String getId() {
		return txtId.getText();
	}
}
