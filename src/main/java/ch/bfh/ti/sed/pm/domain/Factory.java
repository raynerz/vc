package ch.bfh.ti.sed.pm.domain;
import ch.bfh.ti.sed.pm.domain.Persistence;
public interface Factory {

    /**
     * Creates the zero (initial) state.
     *
     * @param name of the persistance
     * @return a Zero state
     * @throws Exception
     *             if the instance for this em cannot be created
     */
    public Persistence createPersitence(String name) throws Exception;


}
