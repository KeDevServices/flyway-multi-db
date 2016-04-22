package eu.kedev.blog.java;


import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.SQLException;


public class FlywayProvider {

    private final String url;
    private final String user;
    private final String password;

    public FlywayProvider(final String url, final String user, final String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Flyway get() {
        final Flyway f = new Flyway();
        f.setDataSource(url, user, password);
        f.setLocations("db/migration");
        f.setPlaceholderPrefix(FlywayConstants.prefix);
        f.setPlaceholderSuffix(FlywayConstants.suffix);
        f.setPlaceholders(FlywayConstants.placeholders(idFor(f.getDataSource())));
        return f;
    }

    private static String idFor(final DataSource ds) {
        try {
            return FlywayConstants.driverToId.get(ds.getConnection().getMetaData().getDatabaseProductName());
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}