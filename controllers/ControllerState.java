package controllers;

public abstract class ControllerState {
	protected static boolean ativo;
	protected void start(){
		ativo = true;
	}
}
