import PlayAngularCLI._

name := "play2-angular-cli"

version := "1.0-SNAPSHOT"
lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean).settings(playAngularCLISettings)
scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  javaWs,
  filters,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.h2database" % "h2" % "1.4.192",
  "mysql" % "mysql-connector-java" % "5.1.18"
)

//To Inject
routesGenerator := InjectedRoutesGenerator