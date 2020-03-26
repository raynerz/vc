package ch.bfh.ti.sed.pm.domain;

import ch.bfh.ti.sed.pm.domain.entities.Doctor;
import ch.bfh.ti.sed.pm.domain.entities.Patient;

public class PatientHelper {

	public Doctor doctor;
	private IEntityManager em;

	public PatientHelper(Doctor doctor, IEntityManager em) {
		this.em = em;
		this.doctor = doctor;
	}

	/**
	 * Before adding a patient we check first whether the patient is already in the database
	 *
	 * @param insuranceNumber Primary key that describes the patient (This field is unique in the database
	 * @param name            Patient's name
	 * @param surname         Patient's Surname
	 * @throws IllegalArgumentException If the patient already exists we throw an exception
	 */
	public void addPatientToDb(int insuranceNumber, String name, String surname) throws IllegalArgumentException {

		Fetcher fetcher = new Fetcher(em);
		Patient p = fetcher.fetch(Patient.class, insuranceNumber);

		if (p != null) {
			throw new IllegalArgumentException("Patient already exists");

		} else {
			Patient patient = doctor.createPatient(insuranceNumber, name, surname);
			doctor.addPatient(patient);

			// We persist the doctor in the database so it will commit all changes in the class
			em.persist(doctor);
			System.out.println("Persisted Patient -> Added");
		}

	}

	@Override
	public String toString() {
		return "" + this.doctor.getUsername();
	}
}