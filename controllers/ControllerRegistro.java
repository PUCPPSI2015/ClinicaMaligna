package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ConsultasModel;
import model.DisponibilidadesModel;
import model.PacientesModel;
import model.ProfSaudeModel;
import model.ConsultasModel.Consulta;
import model.DisponibilidadesModel.Disponibilidade;
import model.PacientesModel.Paciente;
import model.dbos.ProfSaude;
import model.harddata.Especialidades;
import model.harddata.Especialidades.Especialidade;
import views.states.StateProfSaude;
import views.states.StateRegistro;

public class ControllerRegistro {
	static StateRegistro state;

	public static void start(StateRegistro r) {
		state = r;
	}

	public static void logou() {
		state.logou();
	}

	public static Consulta[] getPassadas() {
		return ConsultasModel
				.getAtivasPassadasByProf(((ProfSaude) ControllerPrincipal
						.getObjLogado()).getId());
	}

	public static Consulta[] getFuturas() {
		return ConsultasModel
				.getAtivasFuturasByProf(((ProfSaude) ControllerPrincipal
						.getObjLogado()).getId());
	}

	public static Consulta[] getAll() {
		return ConsultasModel
				.getAtivasFuturasByProf(((ProfSaude) ControllerPrincipal
						.getObjLogado()).getId());
	}

	public static void updateFriedman(Consulta c) {
		int idDisponibilidade = c.getIdDisponibilidade();
		Disponibilidade d = DisponibilidadesModel.getById(idDisponibilidade);
		Especialidade e = Especialidades.getOne(d.getIdEspecialidade());
		Paciente pac = PacientesModel.getById(c.getIdPaciente());

		state.updateFriedman(c, pac.getNome(), e.getNome(), c.getData(),
				c.getInicio(), c.getFim(), c.getObservacoes(),
				c.getPedidosExame(), c.getPrescricoes(), c.getRecomendacoes());
	}

	// listeners
	public static ListSelectionListener mudouLista() {
		return new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if (!listSelectionEvent.getValueIsAdjusting()) {

					@SuppressWarnings("unchecked")
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
					updateFriedman(ConsultasModel.getOne(sel));
				}
			}
		};
	}

	public static ActionListener btnPass() {
		return new LodarPassadas();
	}

	private static class LodarPassadas implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			state.preencherLista(getPassadas());
		}

	}

	public static ActionListener btnFutu() {
		return new LodarFuturas();
	}

	private static class LodarFuturas implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			state.preencherLista(getFuturas());
		}

	}

	public static ActionListener btnSalvar() {
		return new Salvar();
	}

	private static class Salvar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String obs = state.getObservacao();
			String exm = state.getExames();
			String pre = state.getPrescri();
			String rec = state.getRec();
			Consulta c = state.getEditando();
			if (obs.length() > 1024)
				obs = obs.substring(0, 1024);
			if (exm.length() > 1024)
				exm = exm.substring(0, 1024);
			if (pre.length() > 1024)
				pre = pre.substring(0, 1024);
			if (rec.length() > 1024)
				rec = rec.substring(0, 1024);

			ConsultasModel.updateConsultaMeta(c.getId(), obs, exm, pre, rec);
			state.updateFriedman();
		}

	}

	public static void pesquisarPac() {
		state.preencherLista(ConsultasModel.getPesquisaPac(state.getPesquisa()));
	}
}
