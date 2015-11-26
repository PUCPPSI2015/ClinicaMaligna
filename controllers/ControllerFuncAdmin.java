package controllers;


import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import views.states.StateFuncAdmin;
import model.Cargos;
import model.FuncAdminModel;
import model.LoginModel;
import model.dbos.FuncAdmin;
import model.dbos.Acesso;

public class ControllerFuncAdmin {
	
	
	public static FuncAdmin[] getAll(){
		return  FuncAdminModel.getAll();
	}
	
	public static void updateFriedman(StateFuncAdmin state, FuncAdmin ocara){
		LoginModel.listaRefresh();
		Acesso acessoDoCara = LoginModel.getAcesso(ocara.getId());
		
		
		state.updateFriedman(ocara.getNome(), ocara.getCargo(), acessoDoCara.getSenha(), ocara.getId());
	}
	
	public String novaSenha(){
		return Long.toHexString(Double.doubleToLongBits(Math.random()));
	}
	public static ListSelectionListener mudouLista(StateFuncAdmin state){
		return new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent listSelectionEvent) {

			        
		          JList list = (JList) listSelectionEvent.getSource();
		          int selections[] = list.getSelectedIndices();
		          ArrayList<FuncAdmin> selectionValues = (ArrayList) list.getSelectedValuesList();
		          int sel = -1;
		          for (int i = 0, n = selections.length; i < n; i++) {
		        	sel = selectionValues.get(i).getId();
		            
		          }
		          if(sel == -1) return;
		          updateFriedman(state, FuncAdminModel.getOne(sel));

		        }
		      

		};
	}
	
	
	//ouvir botoes
	public ActionListener btnNovaSenha(StateFuncAdmin state){
		return new NovaSenhaAction(state);
	}
	private class NovaSenhaAction implements ActionListener{
		private s;
		public NovaSenhaAction(StateFuncAdmin state){
			super();
			this.s = state; 
		}
		
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
	}

}
