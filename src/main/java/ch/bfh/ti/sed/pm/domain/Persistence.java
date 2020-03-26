package ch.bfh.ti.sed.pm.domain;

public abstract class Persistence implements IEntityManager {
    /**
     * Reference to the context.
     */
    protected String name;

    /**
     * Initializes the reference to the context. Must be called first.
     *
     * @param name for database
     */
    public void init(String name) {
        this.name = name;
    }

}