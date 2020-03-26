package ch.bfh.ti.sed.pm.domain;

public class Fetcher {
	private IEntityManager em;

	public Fetcher(IEntityManager em) {
		this.em = em;
	}

	/**
	 * Method used to retrieve one particular entity within the database
	 *
	 * @param entity     entity
	 * @param primaryKey primary key
	 * @param <T>        Type that we want
	 * @return If it finds a match, it will retrieve an object of the specified class
	 * @throws IllegalArgumentException When object is not found
	 */
	public <T> T fetch(Class<T> entity, Object primaryKey) throws IllegalArgumentException {
		return em.find(entity, primaryKey);
	}
}
