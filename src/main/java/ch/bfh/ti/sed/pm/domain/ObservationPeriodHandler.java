package ch.bfh.ti.sed.pm.domain;

import ch.bfh.ti.sed.pm.domain.entities.Measurement;

import java.time.LocalDate;
import java.util.List;

public class ObservationPeriodHandler {

	private ObservationPeriodHelper helper;

	public ObservationPeriodHandler(ObservationPeriodHelper helper) {
		this.helper = helper;
	}


	public void startObservationPeriod(String idDoctor, int idPatient, int idDevice, LocalDate startDate, long frequency) throws IllegalArgumentException, NullPointerException {
		helper.startObservationPeriod(idDoctor, idPatient, idDevice, startDate, frequency);
	}

	public List<Measurement> consultObservationPeriod(String doctorID, int deviceID) throws InterruptedException {

		return this.helper.consultObsPeriod(doctorID, deviceID);
	}

	public void endObservationPeriod(String idDoctor, int idPatient, int idDevice) throws IllegalArgumentException, NullPointerException{
		helper.endObservationPeriod(idDoctor, idPatient, idDevice);
	}

}
