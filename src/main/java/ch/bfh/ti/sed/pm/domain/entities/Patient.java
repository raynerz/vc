package ch.bfh.ti.sed.pm.domain.entities;

import javax.persistence.*;

/**
 * This class creates a patient with an ID number, a full name and an insurance number.
 * A device can be assigned to a patient and later on removed.
 */
@Entity
public class Patient implements Comparable<Patient> {
	/**
	 * Instances of for accessors
	 */
	@Column(name = "patient_id")
	private int idNumberPatient;
	@Id
	private int insuranceNumber;
	private String name;
	private String surname;
	@OneToOne
	@JoinColumn(name = "device_id")
	private Device device;

	/**
	 * Constructor for JPA
	 */
	public Patient() {}

	/**
	 * Constructor for creating a new Patient
	 *
	 * @param insuranceNumber InsuranceId for the patient
	 * @param name            Patient's First Name
	 * @param surname         Patient's Last Name
	 */
	public Patient(int insuranceNumber, String name, String surname) {
		this.insuranceNumber = insuranceNumber;
		this.name = name;
		this.surname = surname;
	}

	/**
	 * @return Returns the insuranceNumber of the Patient
	 */
	public int getInsuranceNumber() {
		return insuranceNumber;
	}

	/**
	 * @param insuranceNumber Allows to set the insurance number manually
	 */
	public void setInsuranceNumber(int insuranceNumber) {
		if (insuranceNumber <= 0) {
			throw new IllegalArgumentException("Negative number not allowed");
		}
		this.insuranceNumber = insuranceNumber;
	}

	/**
	 * @return Returns the First-name of the Patient
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name Name is the first-name of the Patient which can be set as a parameter
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the Device assigned to the Patient
	 */
	@OneToOne
	public Device getDevice() {
		return device;
	}

	/**
	 * Sets the device to assign to the Patient
	 *
	 * @param device Device number is parsed as parameter for assignment
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * @return Returns the Full-name of the Patient
	 */
	public String getFullName() {
		return "" + name + " " + surname;
	}

	/**
	 * @param name    Sets the first-name of the Patient
	 * @param surname Sets the last-name of the Patient
	 */
	public void setFullName(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	/**
	 * @param d Assigning a new Device to the Patient
	 */
	@OneToOne
	public void addDevice(Device d) {
		this.device = d;
	}

	/**
	 * Removes the Device assigned to the Patient and sets it to <code>null</code>
	 */
	public void removeDevice() {
		this.device = null;
	}

	/**
	 * @return Returns the last-name of the Patient
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname Surname is the last-name of the Patient
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}


	/**
	 * This method alerts whether the patient already has a device or not
	 *
	 * @return <code>false</code> if has no assigned device <code>true</code> if it has a device
	 */
	@OneToOne
	public boolean hasDeviceAssigned() {
		return this.getDevice() != null;
	}

	/**
	 * @param that <code>that</code> compares the new Patient and puts him in the correct order in the <code>Set</code>
	 * @return Returns an integer based on the <code>hashcode</code> value for order in the <code>Set</code>
	 */
	@Override
	public int compareTo(Patient that) {
		return (this.hashCode() - that.hashCode());
	}

	/**
	 * @param obj is the Patient to be used to determine what to do next
	 * @return boolean for the equality of the Objects being parsed-in
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Patient) {
			return super.equals(obj);
		}

		return false;
	}

	/**
	 * @return int to determine the proper index/position where to put the patient
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * @return String with the ID of the patient as well as the name
	 */
	@Override
	public String toString() {
		return "ID: " + insuranceNumber +
			   "\n\tname: " + name + " " + surname + '\n';
	}


}