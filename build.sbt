import sbt.Keys._

name := """Rental Helper"""

version := "1.0-SNAPSHOT"

// --------  For docker stuff ----------
maintainer := "vuongnq.09@gmail.com"
dockerExposedPorts in Docker := Seq(9000, 9443)
// -------------------------------------

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  evolutions,
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "com.typesafe.play" %% "play-mailer" % "3.0.1",
  filters
)

resolvers ++= Seq(
  "Apache" at "http://repo1.maven.org/maven2/",
  "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  "Sonatype OSS Snasphots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Typesafe Ivy releases" at "https://repo.typesafe.com/typesafe/ivy-releases"
)
resolvers += "Typesafe Bintray Ivy releases" at "https://dl.bintray.com/typesafe/ivy-releases"

routesGenerator := InjectedRoutesGenerator

lazy val root = (project in file(".")).enablePlugins(play.PlayJava, PlayEbean)

fork in run := false