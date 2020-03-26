package ch.bfh.ti.sed.pm.domain;

import ch.bfh.ti.sed.pm.domain.entities.Device;
import ch.bfh.ti.sed.pm.domain.entities.Doctor;
import ch.bfh.ti.sed.pm.domain.entities.Patient;
import ch.bfh.ti.sed.pm.technical.PersistenceManager;
import ch.bfh.ti.sed.pm.view.IInitializer;
import ch.bfh.ti.sed.pm.domain.Persistence;


/**
 * This class is the initializer for all accessors.
 * Can be used for getting the simulations.
 */

public class Initializer implements IInitializer {
    private IEntityManager em;
    private LoginHandler loginHandler;
    private PatientHandler patientHandler;
    private ObservationPeriodHandler opHandler;
    protected Persistence persistence;

    /**
     * Initializes the entity manager with a standard doctor (for simulation purposes) and the loginHandler as an entry point of our system
     */
    public void init(Factory fac) throws Exception {
        this.persistence = fac.createPersitence("pm");
        this.em = this.persistence;
        Doctor d = createDoctor();
        Device dev = createDevice();
        Patient p = createPatient();

        d.retrieveDevices()
                .add(dev);
        d.retrievePatients()
                .add(p);

        this.em.persist(d);

        this.loginHandler = new LoginHandler(new LoginHelper(this.em));
    }

    /**
     * Instantiates the patient handler for a particular doctor
     *
     * @param username primary key of the concerned doctor
     * @throws IllegalArgumentException if the doctor is not found
     */
    @Override
    public void createPatientHandler(String username) throws IllegalArgumentException {
        Doctor d = retrieveDoctor(username);
        this.patientHandler = new PatientHandler(new PatientHelper(d, em));
    }

    /**
     * Instantiates the ObservationPeriodHandler for a particular doctor
     *
     * @param username primary key of the concerned doctor
     * @throws IllegalArgumentException if the doctor is not found
     */
    @Override
    public void createObservationPeriodHandler(String username) {
        this.opHandler = new ObservationPeriodHandler(new ObservationPeriodHelper(em));
    }

    // TODO: Javadoc missing
    @Override
    public ObservationPeriodHandler retrieveObservationPeriodHandler() throws IllegalArgumentException, NullPointerException {
        return opHandler;
    }

    /**
     * This method searches a specific doctor's instance in the database
     *
     * @param username the primary key of our doctor
     * @return doctor.class
     * @throws IllegalArgumentException
     */
    @Override
    public Doctor retrieveDoctor(String username) throws IllegalArgumentException {
        Doctor d = em.find(Doctor.class, username);
        return d;
    }

    /**
     * Simulation for testing purposes
     *
     * @return doctor.class
     */
    @Override
    public Doctor createDoctor() {
        Doctor d = new Doctor("James", "Cameron");
        d.setUsername("my.name@bfh.ch");
        d.setPassword("mypassword");
        return d;
    }

    /**
     * Simulation for testing purposes
     *
     * @return Device.class
     */
    @Override
    public Device createDevice() {
        Device d = new Device(1);
        return d;
    }

    /**
     * Simulation for testing purposes
     *
     * @return Patient.class
     */
    @Override
    public Patient createPatient() {
        Patient p = new Patient(911, "Sylvain", "Barthe");
        return p;
    }

    // TODO: Javadoc missing
    @Override
    public LoginHandler retrieveLoginHandler() {
        return this.loginHandler;
    }

    // TODO: Javadoc missing
    @Override
    public PatientHandler retrievePatientHandler() {
        return this.patientHandler;
    }
}
