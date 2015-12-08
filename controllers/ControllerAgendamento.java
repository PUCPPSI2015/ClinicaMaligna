package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ProfSaudeModel;
import model.dbos.ProfSaude;
import model.harddata.Especialidades;
import model.harddata.Especialidades.Especialidade;
import views.states.StateAgendamento;
import views.states.StateAgendamento.MeuSpiner;
import model.ConsultasModel;
import model.ConsultasModel.Consulta;
import model.DisponibilidadesModel;
import model.DisponibilidadesModel.Disponibilidade;
import model.PacientesModel;
import model.PacientesModel.Paciente;
import views.Calendario;

public class ControllerAgendamento {
	private static StateAgendamento state;
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	private static Date selectedData;
	private static ProfSaude selectedProf;
	private static Disponibilidade selectedDisp;

	public static ProfSaude[] getAllProf() {

		return ProfSaudeModel.getAll();
	}

	public static Paciente[] getAllPac() {
		return PacientesModel.getAll();
	}

	public static void start(StateAgendamento s) {
		state = s;
	}

	public static void preencheComboBoxPro(JComboBox<ProfSaude> cbx) {
		cbx.removeAllItems();

		ProfSaude[] profs = getAllProf();
		for (int i = 0; i < profs.length; i++) {
			cbx.addItem(profs[i]);
		}

	}

	public static FocusAdapter cbxExpand(@SuppressWarnings("rawtypes") JComboBox cbx) {

		return new FocusAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void focusGained(FocusEvent e) {
				preencheComboBoxPro(cbx);
				cbx.showPopup();
			}
		};
	}

	public static ActionListener cbxChange(@SuppressWarnings("rawtypes") JComboBox cbx, Calendario c) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// pegar prof selecionado
				selectedProf = (ProfSaude) cbx.getSelectedItem();
				if (selectedProf == null)
					return;
				// pegar suas disponibildiades
				DisponibilidadesModel.listaRefresh();
				Disponibilidade dom, seg, ter, qua, qui, sex, sab;
				dom = DisponibilidadesModel.getByProfAndDia(
						selectedProf.getId(), 1);
				seg = DisponibilidadesModel.getByProfAndDia(
						selectedProf.getId(), 2);
				ter = DisponibilidadesModel.getByProfAndDia(
						selectedProf.getId(), 3);
				qua = DisponibilidadesModel.getByProfAndDia(
						selectedProf.getId(), 4);
				qui = DisponibilidadesModel.getByProfAndDia(
						selectedProf.getId(), 5);
				sex = DisponibilidadesModel.getByProfAndDia(
						selectedProf.getId(), 6);
				sab = DisponibilidadesModel.getByProfAndDia(
						selectedProf.getId(), 7);
				// ver se tem domingo segudna terca quarta
				c.ativarTudo();
				if (!dom.getAtivoBool())
					c.desativar(0);
				if (!seg.getAtivoBool())
					c.desativar(1);
				if (!ter.getAtivoBool())
					c.desativar(2);
				if (!qua.getAtivoBool())
					c.desativar(3);
				if (!qui.getAtivoBool())
					c.desativar(4);
				if (!sex.getAtivoBool())
					c.desativar(5);
				if (!sab.getAtivoBool())
					c.desativar(6);
				try {
					state.updateA3();
				} catch (Exception e) {

				}

			}
		};
	}

	public static MouseAdapter calendarSel(Calendario c) {
		return new java.awt.event.MouseAdapter() {
			JTable tabela = c.getCalendarTable();
			Consulta[] consultas;

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tabela.rowAtPoint(evt.getPoint());
				int col = tabela.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) { // verificar se é uma data válida
					// criar data
					Integer dia = (Integer) tabela.getValueAt(row, col), mes = c
							.getMes() + 1, ano = c.getAno();
					String strdata = "" + ano + "-" + mes + "-" + dia;
					selectedData = null;
					selectedDisp = null;
					state.updateA3();
					if ((dia == null) || (c.isIndisponivel(col))) {

						return;
					}

					try {
						selectedData = new Date((dt.parse(strdata)).getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						selectedData = null;
						e.printStackTrace();
					}

					// pegar disponibilidade com esse dia da semana e esse
					// profissional

					selectedDisp = DisponibilidadesModel.getByProfAndDia(
							selectedProf.getId(), col + 1);
					// pegar consultas com essa disponibilidade
					consultas = ConsultasModel.getByDispoData(
							selectedDisp.getId(), selectedData);

					// pegar consultas e listar na area2
					state.fillConsultas(consultas);

					//

				}
			}
		};
	}

	public static java.util.Date formatar(MeuSpiner sp) {
		try {
			return df.parse(df.format(sp.getValue()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updateA3(Consulta c) {
		int idDisponibilidade = c.getIdDisponibilidade();
		Disponibilidade d = DisponibilidadesModel.getById(idDisponibilidade);
		Especialidade e = Especialidades.getOne(d.getIdEspecialidade());
		ProfSaude p = ProfSaudeModel.getOne(d.getIdProfissional());
		Paciente pac = PacientesModel.getById(c.getIdPaciente());

		state.updateA3(c, p.getNome(), e.getNome(), pac, d.getInicio(),
				d.getFim(), c.getInicio(), c.getFim(), false);
	}

	public static ListSelectionListener mudouLista() {
		return new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if (!listSelectionEvent.getValueIsAdjusting()) {

					@SuppressWarnings({ "unchecked", "rawtypes" })
					JList<Consulta> list = (JList) listSelectionEvent
							.getSource();
					int selections[] = list.getSelectedIndices();
					List<Consulta> selectionValues = list
							.getSelectedValuesList();
					int sel = -1;
					for (int i = 0, n = selections.length; i < n; i++) {
						sel = selectionValues.get(i).getId();
					}
					if (sel == -1)
						return;
					updateA3(ConsultasModel.getOne(sel));
				}
			}
		};
	}

	public static ActionListener btnExcluir() {
		return new ExcluirAction();
	}

	private static class ExcluirAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!ControllerPrincipal.perguntar(
					"Ter certeza que deseja excluir?", "Isto é irreversível"))
				return;
			Consulta c = state.getEditando();
			ConsultasModel.excluirConsulta(c.getId());
			Consulta[] consultas = ConsultasModel.getByDispoData(
					c.getIdDisponibilidade(), c.getData());

			state.fillConsultas(consultas);
			state.updateA3();

		}

	}

	public static ActionListener btnDesmarcar() {
		return new DesmarcarAction();
	}

	private static class DesmarcarAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Consulta c = state.getEditando();
			ConsultasModel.desmarcarConsulta(c.getId());
			Consulta[] consultas = ConsultasModel.getByDispoData(
					c.getIdDisponibilidade(), c.getData());
			state.fillConsultas(consultas);
			state.updateA3();
		}

	}

	public static ActionListener btnMarcar() {
		return new MarcarAction();
	}

	private static class MarcarAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Consulta c = state.getEditando();

			if ((c.getInicio().before(selectedDisp.getInicio()))
					|| (c.getFim().after(selectedDisp.getFim()))) {
				ControllerPrincipal
						.gritar("O horario excede a disponibilidade deste profissional",
								"Erro no horário");
				return;
			}

			// validar com cponsultas
			Consulta[] conflitantes = ConsultasModel.getByDispoData(
					c.getIdDisponibilidade(), c.getData());
			for (int h = 0; h < conflitantes.length; h++) {

				Consulta comparada = conflitantes[h];
				if ((c.getId() != comparada.getId())
						&& (comparada.getAtivoBool())) {
					if (c.getInicio().after(comparada.getInicio())) {
						if (comparada.getFim().after(c.getInicio())) {
							ControllerPrincipal
									.gritar("O horario inserido entra em comflito com outras consultas marcadas para este dia, insira outro horario",
											"Erro no horário");
							return;
						}
					} else {
						if (c.getFim().after(comparada.getInicio())) {
							ControllerPrincipal
									.gritar("O horario inserido entra em comflito com outras consultas marcadas para este dia, insira outro horario",
											"Erro no horário");
							return;
						}
					}
				}
			}

			ConsultasModel.marcarConsulta(c.getId());
			Consulta[] consultas = ConsultasModel.getByDispoData(
					c.getIdDisponibilidade(), c.getData());
			state.fillConsultas(consultas);
			state.updateA3();
		}

	}

	public static ActionListener btnNovo(Calendario c) {
		return new NovoAction(c);
	}

	private static class NovoAction implements ActionListener {
		@SuppressWarnings("unused")
		JTable tabela;

		public NovoAction(Calendario c) {
			super();
			tabela = c.getCalendarTable();
		}

		public void actionPerformed(ActionEvent ev) {
			if ((selectedData == null) || (selectedProf == null)
					|| (selectedDisp == null)) {
				ControllerPrincipal
						.gritar("Selecione um profissional e uma data no painel a esquerda.");
				return;
			}
			Consulta nova = new Consulta(-1, 0, selectedDisp.getId(),
					selectedDisp.getInicio(), selectedDisp.getFim(),
					selectedData);
			Especialidade e = Especialidades.getOne(selectedDisp
					.getIdEspecialidade());
			ProfSaude p = ProfSaudeModel.getOne(selectedDisp
					.getIdProfissional());
			Paciente pac = PacientesModel.getById(nova.getIdPaciente());

			state.updateA3(nova, p.getNome(), e.getNome(), pac,
					selectedDisp.getInicio(), selectedDisp.getFim(),
					nova.getInicio(), nova.getFim(), true);
		}

	}

	public static ActionListener btnSalvar() {

		return new SalvarAction();
	}

	private static class SalvarAction implements ActionListener {

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			if ((selectedData == null) || (selectedProf == null)
					|| (selectedDisp == null)) {
				state.updateA3();
				return;
			}
			Consulta c = state.getEditando();
			java.util.Date inicio = formatar(state.getTmIn());
			java.util.Date fim = formatar(state.getTmOut());
			Time i;
			Time f;
			if (inicio.before(fim)) {
				i = new Time(inicio.getHours(), inicio.getMinutes(),
						inicio.getSeconds());
				f = new Time(fim.getHours(), fim.getMinutes(), fim.getSeconds());
			} else {
				f = new Time(inicio.getHours(), inicio.getMinutes(),
						inicio.getSeconds());
				i = new Time(fim.getHours(), fim.getMinutes(), fim.getSeconds());
			}
			if (state.getCbxPac().getSelectedItem() == null) {
				ControllerPrincipal.gritar(
						"Forneça o paciente para poder macrar a consulta",
						"Quem é o paciente?");
				return;
			}
			int paciente = ((Paciente) state.getCbxPac().getSelectedItem())
					.getId();

			// validar com disponibilidade
			if ((i.before(selectedDisp.getInicio()))
					|| (f.after(selectedDisp.getFim()))) {
				ControllerPrincipal
						.gritar("O horario excede a disponibilidade deste profissional",
								"Erro no horário");
				return;
			}
			// validar com consultas

			// procurar oputras consultas nessa disponibilidade neste dia
			Consulta[] conflitantes = ConsultasModel.getByDispoData(
					c.getIdDisponibilidade(), c.getData());
			for (int h = 0; h < conflitantes.length; h++) {

				Consulta comparada = conflitantes[h];
				if ((c.getId() != comparada.getId())
						&& (comparada.getAtivoBool())) {
					if (i.after(comparada.getInicio())) {
						if (comparada.getFim().after(i)) {
							ControllerPrincipal
									.gritar("O horario inserido entra em comflito com outras consultas marcadas para este dia, insira outro horario",
											"Erro no horário");
							return;
						}
					} else {
						if (f.after(comparada.getInicio())) {
							ControllerPrincipal
									.gritar("O horario inserido entra em comflito com outras consultas marcadas para este dia, insira outro horario",
											"Erro no horário");
							return;
						}
					}
				}
			}

			// ver se or horarios se conflituam
			// se o inicio da atual vem depois da comparada
			// se o fim da comparada vem depois do incio da atual
			// caso nao
			// se o fim da atual vem depois do inicio da comparada

			if (c.getId() != -1) {
				ConsultasModel.updateConsultaParcial(c.getId(), c.getData(), i,
						f, paciente, c.getIdDisponibilidade());
			} else {
				ConsultasModel.novaConsultaParcial(c.getData(), i, f, paciente,
						c.getIdDisponibilidade());
			}

			Consulta[] consultas = ConsultasModel.getByDispoData(
					c.getIdDisponibilidade(), c.getData());
			state.fillConsultas(consultas);
			state.updateA3();
		}

	}

}
