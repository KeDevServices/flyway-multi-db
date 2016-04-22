import scala.language.postfixOps

name := "flyway-multi-db"

version := "0.1"

scalaVersion in ThisBuild := "2.11.7"

    libraryDependencies ++= Seq(
      "org.flywaydb" % "flyway-core" % "4.0",
      "com.h2database" % "h2" % "1.4.191",
      "org.postgresql" % "postgresql" % "9.4.1208",
      "org.scalatest"  %% "scalatest" % "2.2.6" % Test

//      "com.google.inject" % "guice" % "4.0",
//      "net.codingwell" %% "scala-guice" % "4.0.0",
//      "com.typesafe" % "config" % "1.3.0",
//        "org.slf4j" % "slf4j-api" % "1.7.12",
//      "com.google.inject" % "guice" % "4.0",

  )


