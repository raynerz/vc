package ch.bfh.ti.sed.pm.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Defines for how long and how often a patient should be observed
 */
@Entity
public class ObservationPeriod {

	@Transient
	ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
	/* Frequency is defined in seconds */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double frequency;
	private long frequencyL;
	private LocalDate startDate;
	private LocalDate endDate;
	private Device device;
	private Patient patient;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Measurement> measurements;

	public ObservationPeriod() {
	}

	public ObservationPeriod(double frequency, LocalDate startDate, LocalDate endDate) {
		this.frequency = frequency;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ObservationPeriod(long frequency, LocalDate startDate, Device d, Patient p) {
		this.frequencyL = frequency;
		this.startDate = startDate;
		this.endDate = null;
		this.device = d;
		this.patient = p;
		this.measurements = new ArrayList<Measurement>();
	}

	/*Setter methods*/
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	/**
	 * Starts the device thread
	 */
	public void startObservationPeriod() throws NullPointerException {
		if (device == null) {
			throw new NullPointerException("Device is not present");
		}
		executorService.scheduleAtFixedRate(device, 0, frequencyL, TimeUnit.SECONDS);
	}

	/**
	 * Ends the devices thread
	 * @throws NullPointerException
	 * @throws IllegalStateException
	 */
	public void stopObservationPeriod() throws NullPointerException, IllegalStateException {
		if (device == null) {
			throw new NullPointerException("Device is not present");
		}
		if (executorService.isShutdown()) {
			throw new IllegalStateException("Device is already shutdown");
		}
		executorService.shutdown();
	}

	/**
	 * Starts the device thread
	 */
	public void consultObservation() throws NullPointerException, InterruptedException {
		if (device == null) {
			throw new NullPointerException("Device is not present");
		}
		System.out.println("Just before executor!!");
		Thread thread = new Thread();
		thread.start();
		executorService.scheduleWithFixedDelay(device, 6, 6, TimeUnit.SECONDS);
		thread.join();
	}

	/**
	 * Shuts down the executor service where all the devices are running
	 */
	public void shutdownExecutorService() {
		executorService.shutdown();
	}

	/* Getter Methods*/
	public double getFrequencyDouble() {
		return frequency;
	}

	public long getFrequencyLong() {return frequencyL;}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Device getDevice() {return device;}

	public Patient getPatient() {return patient;}

	public List<Measurement> getMeasurements() {
		return this.measurements;
	}

	public ScheduledExecutorService getExecutorService() { return executorService; }

	@Override
	public String toString() {
		return "ObservationPeriod: " +
			   " |\tid=" + id +
			   " |\tfrequency=" + frequency +
			   " |\tstartDate=" + startDate +
			   " |\tendDate=" + endDate;
	}
}
