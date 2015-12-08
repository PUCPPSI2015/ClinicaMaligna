package controllers;

import views.states.*;

public abstract class ControllerState {
	protected static boolean ativo;
	protected static State este;

	protected void start() {
		ativo = true;
	}
}
