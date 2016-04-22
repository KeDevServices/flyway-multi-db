package eu.kedev.blog

import org.flywaydb.core.internal.util.PlaceholderReplacer
import org.scalatest.{MustMatchers, FreeSpec}
import scala.collection.JavaConversions._

class ReplacementSpec extends FreeSpec with MustMatchers {

  val original =
    """
      |/*[postgres:start]*/
      |alter table shopping_cart rename column amont to amount;
      |/*[postgres:end]*/
      |
      |/*[h2:start]*/
      |alter table shopping_cart alter column amont rename to amount;
      |/*[h2:end]*/
    """.stripMargin

  val `expected for postgres` =
      """
      |
      |alter table shopping_cart rename column amont to amount;
      |
      |
      |/*
      |alter table shopping_cart alter column amont rename to amount;
      |*/
    """.stripMargin

  val `expected for h2` =
      """
      |/*
      |alter table shopping_cart rename column amont to amount;
      |*/
      |
      |
      |alter table shopping_cart alter column amont rename to amount;
      |
    """.stripMargin

  val `expected for other` =
      """
      |/*
      |alter table shopping_cart rename column amont to amount;
      |*/
      |
      |/*
      |alter table shopping_cart alter column amont rename to amount;
      |*/
    """.stripMargin

  "replacing for h2 works as expected" in {
    import FlywayConstants._
    val replaced = new PlaceholderReplacer(placeholders("h2"), prefix, suffix).replacePlaceholders(original)
    replaced mustEqual `expected for h2`
  }

  "replacing for postgres works as expected" in {
    import FlywayConstants._
    val replaced = new PlaceholderReplacer(placeholders("postgres"), prefix, suffix).replacePlaceholders(original)
    replaced mustEqual `expected for postgres`
  }

  "replacing for other works as expected" in {
    import FlywayConstants._
    val replaced = new PlaceholderReplacer(placeholders("dummy"), prefix, suffix).replacePlaceholders(original)
    replaced mustEqual `expected for other`
  }
}
