package ch.bfh.ti.sed.pm.view;

import ch.bfh.ti.sed.pm.domain.Initializer;
import ch.bfh.ti.sed.pm.domain.LoginHandler;
import ch.bfh.ti.sed.pm.domain.ObservationPeriodHandler;
import ch.bfh.ti.sed.pm.domain.PatientHandler;
import ch.bfh.ti.sed.pm.domain.entities.Device;
import ch.bfh.ti.sed.pm.domain.entities.Doctor;
import ch.bfh.ti.sed.pm.domain.entities.Patient;
import ch.bfh.ti.sed.pm.domain.Initializer;
import ch.bfh.ti.sed.pm.domain.Factory;


public interface IInitializer {

	Initializer INSTANCE = new Initializer();

	static Initializer getInstance() {
		return INSTANCE;
	}

	Doctor createDoctor();

	LoginHandler retrieveLoginHandler();

	PatientHandler retrievePatientHandler();

	void createPatientHandler(String username);

	Doctor retrieveDoctor(String username) throws IllegalArgumentException;

	Device createDevice();

    void init(Factory fac) throws Exception;


	Patient createPatient();

	void createObservationPeriodHandler(String username);

	ObservationPeriodHandler retrieveObservationPeriodHandler();


}
