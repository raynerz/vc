package ch.bfh.ti.sed.pm.domain;

public class PatientHandler {

	private PatientHelper helper;

	public PatientHandler(PatientHelper helper) { this.helper = helper; }

	/**
	 * Adds a new patient to the database through the helper class.
	 *
	 * @param insuranceNumber the insurance number of the new patient
	 * @param name            the name of the new patient
	 * @param surname         the surname of the new patient
	 * @throws IllegalArgumentException if the patient cannot be added
	 */
	public void addPatient(int insuranceNumber, String name, String surname) throws IllegalArgumentException {
		try {
			helper.addPatientToDb(insuranceNumber, name, surname);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "" + helper;
	}
}
