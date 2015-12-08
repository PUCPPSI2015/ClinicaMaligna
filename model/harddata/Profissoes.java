package model.harddata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.Model;

import controllers.ControllerPrincipal;

public class Profissoes extends Model implements HardData {
	private static Map<Integer, String> profissoes = new HashMap<Integer, String>();

	public static void preencher() {
		profissoes.clear();
		ResultSet rstCargos;
		try {
			rstCargos = myStm.executeQuery("select * from profissoes");
			while (rstCargos.next()) {
				profissoes.put(rstCargos.getInt("Id"),
						rstCargos.getString("Nome"));
			}

		} catch (SQLException e) {
			ControllerPrincipal.gritar("Erro de conexão com o banco de dados",
					"O banco não está aqui");
			e.printStackTrace();
		}
	}

	public static Profissao[] getAll() {
		Profissao[] retorno = new Profissao[profissoes.size()];
		int i = 0;
		for (int key : profissoes.keySet()) {
			retorno[i] = new Profissao(key, profissoes.get(key));
			i++;
		}
		return retorno;
	}

	public static class Profissao {
		private String nome;
		private int id;

		public Profissao(int i, String n) {
			this.nome = n;
			this.id = i;
		}

		public String getNome() {
			return this.nome;
		}

		public int getId() {
			return this.id;
		}

		public void setNome(String n) {
			this.nome = n;
		}

		public void setId(int ip) {
			this.id = ip;
		}

		public String toString() {
			return this.nome;
		}
	}
}
