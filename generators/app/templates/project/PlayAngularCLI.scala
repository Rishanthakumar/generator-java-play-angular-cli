import sbt._
import sbt.Keys._

import play.sbt.PlayRunHook
import sbt.{SettingKey, TaskKey}
import play.sbt.PlayImport.PlayKeys._
import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._
import com.typesafe.sbt.web.Import._
import scala.sys.process.Process

object PlayAngularCLI {

  lazy val ngUIDirectory = SettingKey[File]("ng-ui-directory", "path of the UI directory")
  lazy val ngUIClean: TaskKey[Unit] = TaskKey[Unit]("ng-ui-clean", "clean UI distribution build")
  lazy val ngUIDist: TaskKey[Unit] = TaskKey[Unit]("ng-ui-dist", "create UI distribution")
  lazy val ngUITest = TaskKey[Unit]("ng-ui-test", "run UI tests")

  lazy val ngUICleanCommand: SettingKey[String] = SettingKey[String]("ng-ui-clean-command", "command to run when cleaning the ui distribution build")
  lazy val ngUIDistCommand: SettingKey[String] = SettingKey[String]("ng-ui-dist-command", "command to run when running ui distribution build")
  lazy val ngUIWatchCommand: SettingKey[String] = SettingKey[String]("ng-ui-watch-command", "command to watch ui changes")
  lazy val ngUITesCommand: SettingKey[String] = SettingKey[String]("ng-ui-test-command", "command to test  ui")

  val playAngularCLISettings: Seq[Setting[_]] = Seq(
    // Specifies the location of the root directory of the Gulp project relative to the Play app root
    ngUIDirectory := (baseDirectory in Compile) {
      _ / "ui"
    }.value,

    unmanagedResourceDirectories in Assets += (baseDirectory in Compile) (_ / "ui/dist").value,

    // TODO:need write a task using gulp
    ngUICleanCommand := "",

    // this will get executed with compile sbt command
    ngUIDistCommand := "npm run build",

    // this will executed with watch
    ngUIWatchCommand := "npm run start",

    ngUITesCommand := "npm run test",

    ngUIClean := {
      println("Cleaning ng UI")
      runNGCommand((ngUIDirectory in Compile).value, ngUICleanCommand.value)
    },

    ngUITest := {
      println("Running UI  tests")
      runNGCommand((ngUIDirectory in Compile).value, ngUITesCommand.value).exitValue()
    },

    ngUIDist := {
      println("Creating UI dist")
      runNGCommand((ngUIDirectory in Compile).value, ngUIDistCommand.value).exitValue()
    },

    // TODO:need write ui clean task using gulp
    // clean := (clean dependsOn uiClean).value

    dist := (dist dependsOn ngUIDist).value,

    stage := (stage dependsOn ngUIDist).value,

    test := ((test in Test) dependsOn ngUITest).value,

    playRunHooks += NGWatch((ngUIDirectory in Compile).value, ngUIWatchCommand.value)
  )

  // method to run the ui commands
  private def runNGCommand(uiDirectory: File, ngCommand: String): Process = {

    if (System.getProperty("os.name").startsWith("Windows")) {
      val process = Process("cmd /c " + ngCommand, uiDirectory)
      println(s"Will run: ${process.toString} in ${uiDirectory.getPath}")
      process.run()
    } else {
      val process = Process(ngCommand, uiDirectory)
      println(s"Will run: ${process.toString} in ${uiDirectory.getPath}")
      process.run()
    }
  }

  // watch
  object NGWatch {

    def apply(uiDirectory: File, watchCommand: String): PlayRunHook = {

      object NGSubProcessHook extends PlayRunHook {

        var watchProcess: Option[Process] = None

        override def beforeStarted(): Unit = {
          watchProcess = Some(runNGCommand(uiDirectory, watchCommand))
        }

        override def afterStopped(): Unit = {
          watchProcess.foreach(_.destroy())
          watchProcess = None
        }
      }

      NGSubProcessHook
    }
  }

}