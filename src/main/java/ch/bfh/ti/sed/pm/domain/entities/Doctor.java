package ch.bfh.ti.sed.pm.domain.entities;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;


/**
 * The <code>Doctor</code> class is the main part through which assignments and retrieval will be done.
 */
@Entity
public class Doctor {
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name="doctor_id")
	private String DOCTOR_ID;
	private String firstName, lastName;

	//@OneToMany(mappedBy = "idNumberPatient")
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Patient> patients = new TreeSet<>();
	//@OneToMany(mappedBy = "DID")
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Device> devices = new TreeSet<>();
	@Id
	private String username;
	private String password;
	private boolean isOnline = false;
	@OneToMany(cascade = CascadeType.ALL)
	private List<ObservationPeriod> observationPeriodList = new ArrayList<>();

	public Doctor() {
	}

	/**
	 * Constructor for the <code>Doctor</code>.
	 *
	 * @param firstName is the doctor's first-name
	 * @param lastName  is the doctor's last-name
	 */
	public Doctor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		//this.DOCTOR_ID = generateDoctorID();
	}

	/**
	 * Constructor for the <code>Doctor</code>. Created to test the database without auto generated ID
	 *
	 * @param firstName is the doctor's first-name
	 * @param lastName  is the doctor's last-name
	 * @param id        is the doctor's ID
	 */
	public Doctor(String firstName, String lastName, String id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOCTOR_ID = id;
	}

	/**
	 * The first-name is returned
	 *
	 * @return first-name is returned
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * The last-name is returned
	 *
	 * @return last-name is returned
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * The full-name is returned.
	 *
	 * @return full-name is returned.
	 */
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	/**
	 * The Doctor's ID is returned.
	 * The <code>final</code> keyword is used for both the assignment of the instance and the method to prevent any modification that someone might want to do.
	 *
	 * @return the ID of the <code>Doctor</code>.
	 */
	public final String getDOCTOR_ID() {
		return DOCTOR_ID;
	}

	/**
	 * Used only for testing. JPA auto-generates the ID on Database creation.
	 *
	 * @param id is the id set for testing but not required.
	 */
	public void setDOCTOR_ID(String id) {
		DOCTOR_ID = id;

	}

	/**
	 * Generates an ID specific to the doctor
	 *
	 * @return an unmodifiable Doctor's ID that is assigned. <code>null</code> is parsed as parameter so that the insurance is not required.
	 */
	public String generateDoctorID() {
		return (this.firstName.substring(0, 2)
							  .toUpperCase() +
				this.lastName.substring(lastName.length() - 2)
							 .toUpperCase());
	}

	/**
	 * Returns the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * The username of a doctor is set.
	 *
	 * @param username is a valid e-mail address
	 * @throws IllegalArgumentException if the e-mail address is not valid
	 */
	public void setUsername(String username) {
		if (Pattern.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", username)) {
			this.username = username;
		} else {
			throw new IllegalArgumentException("This e-mail address is not valid");
		}
	}

	/**
	 * The password is set.
	 *
	 * @param password is the doctor's personal password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * The given password is compared with the stored password.
	 *
	 * @param password that was given by the doctor
	 * @return true if correct, false if not
	 */

	public boolean checkPassword(String password) {
//        password.hashCode();
		return (this.password.equals(password));
	}

	/**
	 * The status of the doctor is returned.
	 *
	 * @return True if online, else false.
	 */
	public boolean getStatus() {
		return isOnline;
	}

	/**
	 * The status of the doctor is set in the system.
	 *
	 * @param status of the doctor. True if online, else false.
	 */
	public void setStatus(boolean status) {
		isOnline = status;
	}

	/**
	 * Creates a patient
	 *
	 * @param name            Patient's name
	 * @param surname         Patient's surname
	 * @param insuranceNumber Primary key that describes the patient's AHV number or Insurance Number
	 * @return patient that was created;
	 */
	public Patient createPatient(int insuranceNumber, String name, String surname) {

		return new Patient(insuranceNumber, name, surname);
	}

	/**
	 * This method adds a patient to the tree set of patients, notice that this needs to be unique, otherwise the patient can't be added
	 *
	 * @param patient a <code>new</code> Patient that is not on the datase
	 */
	public void addPatient(Patient patient) {
		this.patients.add(patient);
	}

	/**
	 * Creates an <code>ObservationPeriod</code> for when the <code>Patient</code> will be monitored.
	 *
	 * @param freq      sets the rate at which the <code>ObservationPeriod</code> should be done.
	 * @param startDate is the start date at which the Observation starts.
	 * @param endDate   is the end date at which the Observation starts.
	 * @return a <code>new ObservationPeriod</code> which can be used to assign to the device.
	 */
	@OneToMany
	public ObservationPeriod createObservationPeriod(double freq, final LocalDate startDate, final LocalDate endDate) {
		if (freq == 0.0 || startDate == null || endDate == null) {
			throw new NullPointerException("Cannot have \"null\" parameters.");
		}
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("START DATE cannot be after END DATE!!!");
		}
		return new ObservationPeriod(freq, startDate, endDate);
	}

	/**
	 * Creates an <code>ObservationPeriod</code> for when the <code>Patient</code> will be monitored.
	 *
	 * @param freq      sets the rate at which the <code>ObservationPeriod</code> should be done.
	 * @param startDate is the start date at which the Observation starts.
	 * @param d         Device concerned by the observation period
	 * @param p         Patient concerced by the observation period
	 * @return a <code>new ObservationPeriod</code> which can be used to assign to the device.
	 */
	@OneToMany
	public ObservationPeriod createObservationPeriod(long freq, final LocalDate startDate, Device d, Patient p) {
		if (freq == 0.0 || startDate == null) {
			throw new NullPointerException("Cannot have \"null\" parameters.");
		}
		ObservationPeriod op = new ObservationPeriod(freq, startDate, d, p);
		observationPeriodList.add(op);
		System.out.println("List should add: " + op);
		System.out.println("List Size here was:" + observationPeriodList.size());
		return op;
	}

	public void endObservationPeriod(Device device, Patient patient) {
		if(device == null || patient == null) throw new NullPointerException("Patient or device not existent");
		patient.removeDevice();
		device.removePatient();
	}

	/**
	 * <p>
	 * This method assigns a device to a patient and verifies that the deviceNum parsed as parameter is found in the Set of devices before assignment.
	 * </p>
	 * <p>
	 * The patient is checked that it could be found before being assigned. An <code>null</code> Patient cannot be assigned.
	 * </p>
	 *
	 * @param deviceNum is the Device ID which is parsed as parameter.
	 * @param patient   is the patient which
	 * @return upon success of deviceNum assignment to patient return true else false.
	 */
	@OneToMany
	public boolean assignDevice(Device deviceNum, Patient patient) {
		if (deviceNum == null || patient == null) {
			throw new NullPointerException("Cannot contain 'null' parameters");
		}

		if (!devices.contains(deviceNum)) {
			System.err.printf("Device number: %s could not be found\n", deviceNum.getDevice_id());
			return false;
		}
		if (deviceNum.isAvailable() && !patient.hasDeviceAssigned()) {
			deviceNum.assignPatient(patient);
			return true;
		} else {
			if (!deviceNum.isAvailable()) {
				System.err.printf("Device: %s is not available!\n", deviceNum.getDevice_id());
				return false;
			}
			System.err.printf("Patient: %s could not be found in the database!\n", patient.getInsuranceNumber());
			return false;
		}


	}

	/**
	 * The patient list is only a retrieval of all the patients assigned to a doctor.
	 * It cannot be modified. Use the <code>retrievePatients()</code> method for modifications.
	 *
	 * @return an <code>unmodifiableSet</code> of patients. Meant for readonly purposes.
	 */
	@OneToMany
	public Set<Patient> getPatients() {
		return Collections.unmodifiableSet(this.patients);
	}


	/**
	 * Using <code>Set</code>-interface so that we don't have to get any duplicates where Patients can be added and removed.
	 *
	 * @return a <code>Patient</code> Set which can be modified using the <code>add</code> method.
	 */

	@OneToMany
	public Set<Patient> retrievePatients() {
		return this.patients;
	}

	/**
	 * This method only return a copy of the <code>devices</code> which cannot be modified.
	 * Use <code>retrieveDevices()</code> for modifications.
	 *
	 * @return an unmodifiableSet of <code>devices</code>.
	 */
	public Set<Device> getDevices() {
		return Collections.unmodifiableSet(this.devices);
	}

	/**
	 * The actual <code>Set</code> of <code>Devices</code>is returned thus allowing modifications to be made.
	 *
	 * @return the <code>Set</code> of <code>devices</code>
	 */
	public Set<Device> retrieveDevices() {
		return this.devices;
	}

	/**
	 * Compares two objects
	 *
	 * @param obj object to be compared
	 * @return true if equal, else false
	 */
	/**
	 * Returns the observation period list
	 *
	 * @return a list with all the observation periods of the doctor
	 */
	@OneToMany
	public List<ObservationPeriod> retrieveObservationPeriodList() {
		return this.observationPeriodList;
	}

	@OneToMany
	public void setObservationPeriodList(List<ObservationPeriod> list) {
		this.observationPeriodList = list;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * Creates a hashCode to hash the sensible data
	 *
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Doctor: " + username +
			   "\t| First Name: " + firstName +
			   "\t| Last Name: " + lastName +
			   "\n\tPatients:\n\t " + patients +
			   "\n\tDevices: \n\t" + devices;
	}

}
