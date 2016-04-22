
Flyway mit mehreren Datenbanksystemen verwenden
===============================================

Link zum Blogeintrag: [Kedev Blog](https://blog.kedev.eu/flyway-multi-db/)
Link zu Github: [FlywayMultiDatabase](https://blog.kedev.eu/flyway-multi-db/)

Wir haben bisher schon in einigen Projekten erfolgreich mit
[Flyway](https://flywaydb.org/) gearbeitet, wenn es um Schemaevolution
ging. Ich persönlich liebe an Flyway die Einfachheit -- es konzentriert
sich auf die Kernproblematik und bietet darüberhinaus nur wenig
Funktionalität. Die Lernkurve ist flach und kurz, was in vielen Projekten
(u.U. mit großer Personalfluktuation) von Vorteil ist.

Eine Funktionalität, die (by design) in Flyway fehlt ist die Unterstützung
von Projekten, die für die gleichen Migrationsskripte unterschiedliche
Datenbanksysteme als Ziel haben.
Während produktiv beispielsweise PostgreSQL, DB2, oder Oracle eingesetzt
werden, reicht für Unittests oft eine H2.
Im Netz findet
man für solche Fälle in der Regel den Vorschlag, Unterordner für die
unterschiedlichen Zielsysteme anzulegen und die Skripte zu trennen, das
kann dann so aussehen:

    /db
        /migration
            /common
                V1__create_schema.sql
                V2__initial_data.sql
            /h2
                V3__some_schema_changes.sql
            /postgres
                V3__some_schema_changes.sql

An dieser Vorgehensweise ist prinzipiell nichts auszusetzen. Leider ist es
oft so, dass die Syntaxunterschiede zwischen Datenbanken nicht so groß sind.
Wegen wenigen Zeilen Unterschied muss trotzdem das vollständige Skript
dupliziert und angepasst werden. Alternativ dazu kann man ein Migrationsskript
auch in mehrere aufteilen. Das kann wegen der Transaktionalität problematisch
sein und generiert deutlich mehr Skriptdateien, was wiederum der Übersicht
schadet.

Vor einiger Zeit kam ich auf eine andere Lösungsmöglichkeit, die mit den
Placeholders von Flyway arbeitet. Den Code dazu gibt es auf Github hier:
[FlywayMultiDatabase](https://github.com/KeDevServices/flyway-multi-db)

Zuerst wird das Präfix und Suffix für die Platzhalter umdefiniert:

    flyway.setPlaceholderPrefix("/*[")
    flyway.setPlaceholderSuffix("]*/")

Damit sind erstmal alle Platzhalter in einem Skript ein Kommentar, und stören
kein Syntax-Highlighting in einem Editor. Nun führen wir für jedes unterstützte
Datenbanksystem die Platzhalter `system:start` und `system:end` ein. In einem
Migrationsskript sieht das so aus:

    /*[postgres:start]*/
    alter table shopping_cart rename column amont to amount;
    /*[postgres:end]*/
    
    /*[h2:start]*/
    alter table shopping_cart alter column amont rename to amount;
    /*[h2:end]*/

Nun müssen vor dem Starten der Migration durch Flyway die Platzhalterersetzungen
so definiert werden, dass für das Zielsystem die Platzhalter durch den Leerstring,
und für alle anderen Systeme durch Kommentar-Start und -Ende ersetzt werden.

    ids flatMap {
        case id@`chosenId` => List(s"$id:start" -> "",   s"$id:end" -> "")
        case id            => List(s"$id:start" -> "/*", s"$id:end" -> "*/")
    }

Nun kann ein Skript die Statements für alle unterstützten Datenbanksysteme enthalten.
Falls es an einigen Stellen Unterschiede gibt, können systemspezifische Statements
verwendet werden. Nett ist auch, dass diese dann in der Regel direkt untereinander
stehen, so dass bei Änderungen (in der Entwicklung!) die Wahrscheinlichkeit, alles
konsistent zueinander zu ändern deutlich größer ist.  
