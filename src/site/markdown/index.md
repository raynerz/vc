About
-----

To generate this site, go into the root directory
of the project (where the `pom.xml` file is) and then type:

> `mvn clean site`

You'll find the generated site `index.html` in directory target/site.

To compile and test your source code, still in the root directory, type:

> ` mvn clean package`

To generate the JPA Database
> `mvn compile && mvn exec:java`

To run the site
> `mvn jetty:run`

To see the actual overall test coverage
> `clear && mvn clean package site && mvn verify`

