package views.states;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import views.JanelaPrincipal;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;

import controllers.ControllerPrincipal;

public class StateInicio extends State {

	// variaveis de painel menu
	JPanel pnlInicio = new JPanel();
	static JButton btnFuncAdmin = new JButton("Funcionarios administrativos"),
			btnProfSaude = new JButton("Profissionais de Saude"),
			btnAgConsultas = new JButton("Agendamento de consultas"),
			btnRgConsultas = new JButton("Registro de consultas");

	protected StateInicio(JanelaPrincipal janela_) {
		super(janela_);

		// ajustar mypainel
		myPainel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// montar painel menu
		pnlInicio.setBorder(null);
		pnlInicio.setBackground(new Color(255, 255, 255));

		pnlInicio.setPreferredSize(new Dimension(500, 318));
		pnlInicio.setLayout(null);

		JLabel lblIcone = new JLabel("");
		lblIcone.setIcon(new ImageIcon(StateInicio.class
				.getResource("/material/logo medio.jpg")));
		lblIcone.setBounds(173, 1, 150, 150);
		pnlInicio.add(lblIcone);

		btnFuncAdmin.setForeground(new Color(255, 0, 0));
		btnFuncAdmin.setBackground(new Color(255, 255, 255));
		btnFuncAdmin.setBounds(10, 161, 235, 50);
		pnlInicio.add(btnFuncAdmin);

		btnProfSaude.setForeground(Color.RED);
		btnProfSaude.setBackground(Color.WHITE);
		btnProfSaude.setBounds(255, 161, 235, 50);
		pnlInicio.add(btnProfSaude);

		btnAgConsultas.setForeground(Color.RED);
		btnAgConsultas.setBackground(Color.WHITE);
		btnAgConsultas.setBounds(10, 221, 235, 50);
		pnlInicio.add(btnAgConsultas);

		btnRgConsultas.setForeground(Color.RED);
		btnRgConsultas.setBackground(Color.WHITE);
		btnRgConsultas.setBounds(255, 221, 235, 50);
		pnlInicio.add(btnRgConsultas);

		btnFuncAdmin.addActionListener(ControllerPrincipal
				.mudador("funcionarios administrativos"));
		btnProfSaude.addActionListener(ControllerPrincipal
				.mudador("profissionais saude"));
		btnAgConsultas.addActionListener(ControllerPrincipal
				.mudador("agendamento"));
		btnRgConsultas.addActionListener(ControllerPrincipal
				.mudador("registro"));

		// montar
		myPainel.add(pnlInicio, gbc);
	}

	public static void ativarProfSaude() {

		btnFuncAdmin.setVisible(false);
		btnProfSaude.setVisible(false);
		btnAgConsultas.setVisible(false);
		btnRgConsultas.setVisible(true);

	}

	public static void ativarFuncadmin() {

		btnFuncAdmin.setVisible(true);
		btnProfSaude.setVisible(true);
		btnAgConsultas.setVisible(true);
		btnRgConsultas.setVisible(false);

	}
}
