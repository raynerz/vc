package ch.bfh.ti.sed.pm.domain;

import ch.bfh.ti.sed.pm.domain.entities.Doctor;

public class LoginHandler {


	private LoginHelper helper;

	public LoginHandler(LoginHelper helper) {
		this.helper = helper;
	}

	/**
	 * Logs in the doctor
	 *
	 * @param username doctor's username
	 * @param password doctor's Password
	 * @return doctor's instance that matched both password and username
	 * @throws IllegalArgumentException When username or password doesn't match the stored data
	 */
	public Doctor login(String username, String password) throws IllegalArgumentException {
		Doctor doctor = helper.validation(username, password);
		return doctor;
	}

//    /**
//     * Logs out the doctor
//     *
//     * @param doctor doctor that needs to be logged out
//     * @return true when the doctor is logged out
//     */
//    public boolean logout(Doctor doctor) {
//
//        return helper.logout(doctor);
//    }
//
	// TODO: MERGE THESE TESTS TOGETHER

	/**
	 * Logs out the doctor using the Fetcher to fetch it from the database
	 *
	 * @param username of the doctor that will be logged out
	 * @return true when the doctor is logged out
	 */
	public boolean logout(String username) {

		return helper.logout(username);
	}
}
