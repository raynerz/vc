package ch.bfh.ti.sed.pm.domain;

import ch.bfh.ti.sed.pm.domain.entities.Doctor;

public class LoginHelper {


	private IEntityManager em;


	public LoginHelper(IEntityManager em) {
		this.em = em;
	}

	/**
	 * Logs in the User
	 *
	 * @param username doctor's username
	 * @param password doctor's Password
	 * @return doctor's instance that matched both password and username
	 * @throws IllegalArgumentException When username or password doesn't match the stored data
	 */

	public Doctor validation(String username, String password) throws IllegalArgumentException {
		//If em doesn't find anything we throw an exception that will be catch by the gui controller
		try {
			Fetcher fetcher = new Fetcher(em);
			Doctor doctor = fetcher.fetch(Doctor.class, username);

			if (doctor == null) {
				throw new IllegalArgumentException("Incorrect username");
			}
			//If we find the doctor
			if (doctor.checkPassword(password)) {
				doctor.setStatus(true);
				return doctor;
			} else {

				throw new IllegalArgumentException("Password doesn't match");
			}


		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Username doesn't match");
		}

	}


	/**
	 * Logs out the doctor using the Fetcher to fetch it from the database
	 *
	 * @param username is the id of the doctor who's logged in
	 * @return true is return when the logged-in user is logged out
	 */
	public boolean logout(String username) {
		Fetcher fetcher = new Fetcher(em);

		Doctor doctor = fetcher.fetch(Doctor.class, username);
		doctor.setStatus(false);

		return !doctor.getStatus();
	}

}
