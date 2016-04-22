package eu.kedev.blog


class Main extends App {

  val url = System.getProperty("flyway.url")
  val user = System.getProperty("flyway.user")
  val password = System.getProperty("flyway.password")

  val provider = new FlywayProvider(url, user, password)

  val flyway = provider.get()

  flyway.migrate()
}
