package ch.bfh.ti.sed.pm.domain;

import ch.bfh.ti.sed.pm.domain.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ObservationPeriodHelper {

	private IEntityManager em;
	private ObservationPeriod op = null;

	public ObservationPeriodHelper(IEntityManager em) { this.em = em; }

	public void startObservationPeriod(String idDoctor, int idPatient, int idDevice, LocalDate startDate,
			long frequency) throws IllegalArgumentException, NullPointerException {
		try {
			Fetcher fetcher = new Fetcher(em);

			Doctor doctor = fetcher.fetch(Doctor.class, idDoctor);
			// All of the code below can be implemented in another class to make the code pretty -> there is no time

			Patient patient = null;
			Device device = null;
			Set<Patient> patients = doctor.retrievePatients();
			for (Patient p : patients) {
				if (p.getInsuranceNumber() == idPatient) {
					patient = p;
				}
			}

			Set<Device> devices = doctor.retrieveDevices();
			for (Device d : devices) {
				if (d.getDevice_id() == idDevice) {
					device = d;
				}
			}

			if (patient == null) {
				throw new NullPointerException("Patient not found");
			}

			if (device == null) {
				throw new NullPointerException("Device not found");
			}
			// All of the code above can be implemented in another class to make the code pretty -> there is no time
			doctor.assignDevice(device, patient);

			this.op = doctor.createObservationPeriod(frequency, startDate, device, patient);

			op.startObservationPeriod();

			em.persist(doctor);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		} catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage());
		}


	}

	public List<Measurement> consultObsPeriod(String doctorID, int deviceID) throws InterruptedException {
		Fetcher fetcher = new Fetcher(this.em);

		Doctor doctor = fetcher.fetch(Doctor.class, doctorID);
		Set<Device> devices = doctor.getDevices();
		Device device = null;
		for (Device d : devices) {
			if (d.getDevice_id() == deviceID) {
				device = d;
			}
		}

		if (device == null)
			throw new NullPointerException("Device not found!!!");

		this.op.consultObservation();
		this.em.persist(doctor);

		return device.poll();
	}

	public void endObservationPeriod(String idDoctor, int idPatient, int idDevice) throws IllegalArgumentException, NullPointerException{
		try{
			Fetcher fetcher = new Fetcher(em);

			Doctor doctor = fetcher.fetch(Doctor.class, idDoctor);

			Patient patient = null;
			Device device = null;

			Set<Patient> allPatients = doctor.retrievePatients();
			for (Patient p : allPatients) {
				if (p.getInsuranceNumber() == idPatient) {
					patient = p;
				}
			}

			Set<Device> allDevices = doctor.retrieveDevices();
			for (Device d : allDevices) {
				if (d.getDevice_id() == idDevice) {
					device = d;
				}
			}

			if (patient == null) {
				throw new NullPointerException("Patient not found");
			}

			if (device == null) {
				throw new NullPointerException("Device not found");
			}


			this.op.stopObservationPeriod();
			doctor.endObservationPeriod(device, patient);
			this.em.persist(doctor);

		} catch (IllegalArgumentException e){
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		} catch (NullPointerException e){
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
	}
}
