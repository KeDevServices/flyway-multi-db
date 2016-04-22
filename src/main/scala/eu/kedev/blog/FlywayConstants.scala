package eu.kedev.blog


object FlywayConstants {

  val prefix = "/*["
  val suffix = "]*/"

  val ids = List("h2", "postgres")

  val driverToId = Map(
    "H2" -> "h2",
    "PostgreSQL" -> "postgres"
  )

  private[blog] def placeholders(chosenId: String) = ids.flatMap {
    case id@`chosenId` => List(s"$id:start" -> "",   s"$id:end" -> "")
    case id            => List(s"$id:start" -> "/*", s"$id:end" -> "*/")
  }.toMap

}
