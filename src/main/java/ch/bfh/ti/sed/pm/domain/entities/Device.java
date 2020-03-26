package ch.bfh.ti.sed.pm.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <code>Device</code> is the class where the <code>Measurement</code>s and <code>Patient</code> relationship and assignments are carried out.
 */
@Entity
public class Device extends Thread implements Comparable<Device> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_id", updatable = false, nullable = false)
	private int device_id;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Measurement> measurements = Collections.synchronizedList(new ArrayList<Measurement>());


	private long frequency;
	/**
	 * flag for stopping the thread
	 */


	@OneToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	public Device() {
	}

	public Device(int device_id) {
		this.device_id = device_id;
	}

	public Device(List<Measurement> measurements) {
		this.measurements = measurements;
	}

	/**
	 * Device (this constructor converts automatically seconds to milliseconds
	 *
	 * @param frequency seconds
	 */
	public Device(double frequency, int device_id) {
		this.frequency = Double.valueOf(frequency * 1000)
							   .longValue();
	}


	/**
	 * The device IS is returned
	 *
	 * @return this device ID
	 */
	public int getDevice_id() {
		return this.device_id;
	}

	/**
	 * Setting the device  id only used for testing and nothing  else because JPA does that automatically
	 *
	 * @param id to parsed when testing. Not the actual ID.
	 */
	public void setDevice_id(int id) {
		this.device_id = id;
	}

	/**
	 * The patient object is return
	 *
	 * @return <code>Patient</code> who is currently assigned the device
	 */
	@OneToOne
	public Patient getPatient() {
		return this.patient;
	}


	/**
	 * The patient object is set
	 *
	 * @param patient is parsed in and assigned a device
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Link between patient and device.
	 *
	 * @param patient The patient given in parameter isn't null, then this device is removed from the patient.
	 *                Otherwise the device is assigned to a new patient, and the device is assigned to this patient.
	 */
	@OneToOne
	public void assignPatient(Patient patient) {
		if (this.patient != null) {
			this.patient.removeDevice();
		}
		this.patient = patient;
		if (this.patient != null) {
			this.patient.addDevice(this);
		}
	}

	/**
	 * Removes the patient from a device
	 */
	public void removePatient() {
		this.patient = null;
	}

	/**
	 * Verifies if the device is available. If the device has a patient assigned, it's not available.
	 *
	 * @return boolean is return to determine if the Device is available or not
	 */
	public boolean isAvailable() {
		return (patient == null);
	}


	/**
	 * @return A List of the measures that were taken by the device
	 */
	public synchronized List<Measurement> getMeasurements() {
		return measurements;
	}

	/**
	 * The hashcode to compare the object is returned
	 *
	 * @param that The Device that will be compared to
	 * @return hashcode for the correct placement in the <code>Set</code>
	 */
	@Override
	public int compareTo(Device that) {
		return (this.hashCode() - that.hashCode());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Device)
			return super.equals(obj);
		return false;
	}

	@Override
	public String toString() {
		return "Device ID: " + this.device_id;
	}

	/**
	 * Method used for generating measurements automatically
	 */
	@Override
	public void run() {
		//Implement polling technique: https://blog.codecentric.de/en/2018/09/implement-responsive-java-polling/
		System.out.println("Device: " + Thread.currentThread()
											  .getName() + "-> New Measure - ListSize" + measurements.size());
		Random ra = new Random(System.currentTimeMillis());
		Measurement m = new Measurement(ra.nextDouble() * 10);
		measurements.add(m);
	}

	public List<Measurement> poll() {
		synchronized (this) {
			System.out.println("Polling");
			System.out.printf("Thread running: %s | %s\n", Thread.currentThread()
																 .getName(), Thread.currentThread()
																				   .getId());


			System.out.println("Thread resumed");
		}
		return this.measurements;
	}

}
