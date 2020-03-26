package ch.bfh.ti.sed.pm.domain;

import java.io.InputStream;
import java.util.Properties;
import ch.bfh.ti.sed.pm.technical.PersistenceManager;
import ch.bfh.ti.sed.pm.domain.Persistence;

/**
 * A factory allowing to create and get Persistence objects. The class names from
 * which the Persistence have to be instantiated are retrieved from a fixed
 * configuration file.
 */
public class EMFactoryImpl implements Factory {
    /**
     * The name of the configuration file name relative to a component of the
     * CLASSPATH.
     */
    public final static String CONFIG_FILENAME = "pm.properties";

    /**
     * The class denoting the Persistence Manager.
     */
    public final static String EM_CLASSNAME = "entitymanager.classname";

    /**
     * The properties loaded from the configuration file.
     */
    private Properties props = null;

    /**
     * Creates an instance of a factory for Persistences.
     */
    public EMFactoryImpl() throws Exception {
        this.props = loadProperties(CONFIG_FILENAME);
    }

    /**
     * Loads the properties of the given configuration file.
     *
     * @param fileName
     *            the name of a file
     * @return the properties
     * @throws IllegalArgumentException
     *             if the given file could not be loaded
     */
    private Properties loadProperties(String fileName)
            throws IllegalArgumentException {
        Properties props = null;
        props = new Properties();
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(fileName);
        try {
            props.load(is);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Cannot load configuration file for Persistence factory: "
                            + fileName);
        }
        return props;
    }

    /**
     * Creates and returns a Persistence.
     *
     * @param name
     *            the name of a Persistence class
     * @return a Persistence
     * @throws Exception
     *             if the class found in the properties file can not be
     *             initialized
     */
    private Persistence create(String name) throws Exception {//TODO change
        String classname = this.props.getProperty(name);
        return initialize(classname);
    }

    /**
     * Instantiates and initializes a command factory.
     *
     * @param classname
     *            the name of a class denoting a Persistence
     * @return a Persistence
     * @throws an exception if there is a problem
     */
    private Persistence initialize(String classname) throws Exception {
        Persistence instance = null;
        Class<?> clazz = null;
        if (classname != null) {
            try {
                clazz = Class.forName(classname);
                instance = (Persistence) clazz.newInstance();
            } catch (InstantiationException e) {
                throw new Exception("Cannot instantiate class.", e);
            } catch (IllegalAccessException e) {
                throw new Exception("Not privileged to access instance.", e);
            } catch (Exception e) {
                throw new Exception("Cannot load class: " + classname, e);
            }
        }
        return instance;
    }

    @Override
    public Persistence createPersitence(String name) throws Exception {//TODO
        Persistence s = create(EM_CLASSNAME);
        s.init(name);
        return s;
    }

}
