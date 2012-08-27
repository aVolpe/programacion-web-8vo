package py.com.pg.webstock.services;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DBManager {

	static EntityManager em;

	private DBManager() {

	}

	public static EntityManager getManager() {
		if (em == null)
			em = Persistence.createEntityManagerFactory("WebStockJPA")
					.createEntityManager();
		return em;
	}
}
