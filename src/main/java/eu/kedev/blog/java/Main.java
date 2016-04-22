package eu.kedev.blog.java;


import org.flywaydb.core.Flyway;

public class Main {

    public static void main(final String... args) {
        final String url = System.getProperty("flyway.url");
        final String user = System.getProperty("flyway.user");
        final String password = System.getProperty("flyway.password");

        final FlywayProvider provider = new FlywayProvider(url, user, password);

        final Flyway flyway = provider.get();

        flyway.migrate();
    }

}
