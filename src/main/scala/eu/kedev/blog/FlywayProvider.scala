package eu.kedev.blog

import javax.sql.DataSource
import org.flywaydb.core.Flyway
import scala.collection.JavaConversions._


class FlywayProvider(url: String, user: String, password: String) {

  import FlywayConstants._

  def get() = {
    val f = new Flyway
    f.setDataSource(url, user, password)
    f.setLocations("db/migration")
    f.setPlaceholderPrefix(prefix)
    f.setPlaceholderSuffix(suffix)
    f.setPlaceholders(placeholders(idFor(f.getDataSource)))
    f
  }

  private def idFor(ds: DataSource) =
    driverToId(ds.getConnection.getMetaData.getDatabaseProductName)

}
