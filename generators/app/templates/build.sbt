import PlayAngularCLI._

name := "java-play-angular-cli"

version := "1.0-SNAPSHOT"
lazy val myProject = (project in file(".")).enablePlugins(PlayJava).settings(playAngularCLISettings)

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies ++= Seq(
  guice,
  filters,
  "org.assertj" % "assertj-core" % "3.6.2" % Test,
  "org.awaitility" % "awaitility" % "2.0.0" % Test,
  "com.h2database" % "h2" % "1.4.194",
)

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

//To Inject
routesGenerator := InjectedRoutesGenerator