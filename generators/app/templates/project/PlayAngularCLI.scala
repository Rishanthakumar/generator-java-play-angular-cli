import sbt._
import sbt.Keys._
import sbt.inc.Analysis
import play.sbt.PlayRunHook
import sbt.{SettingKey, TaskKey}
import play.sbt.PlayImport.PlayKeys._
import play.twirl.sbt.Import.TwirlKeys
import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._
import com.typesafe.sbt.web.Import._

object PlayAngularCLI {

    lazy val ngUIDirectory = SettingKey[File]("ng-ui-directory", "path of the UI directory")
    lazy val ngUIClean: TaskKey[Unit] = TaskKey[Unit]("ng-ui-clean", "clean UI distribution build")
    lazy val ngUIDist: TaskKey[Analysis] = TaskKey[Analysis]("ng-ui-dist", "create UI distribution")
    lazy val ngUICleanCommand: SettingKey[String] = SettingKey[String]("ng-ui-clean-command", "command to run when cleaning the ui distribution build")
    lazy val ngUIDistCommand: SettingKey[String] = SettingKey[String]("ng-ui-dist-command", "command to run when running ui distribution build")
    lazy val ngUIWatchCommand: SettingKey[String] = SettingKey[String]("ng-ui-watch-command", "command to watch ui changes")

    val playAngularCLISettings: Seq[Setting[_]] = Seq(
        // Specifies the location of the root directory of the Gulp project relative to the Play app root
        ngUIDirectory <<= (baseDirectory in Compile) { _ / "ui" },

        unmanagedResourceDirectories in Assets <+= (baseDirectory in Compile)(_ /"ui/dist"),

        // TODO:need write a task using gulp
        ngUICleanCommand := "",

        // this will get executed with compile sbt command
        ngUIDistCommand := "ng build",

        // this will executed with watch
        ngUIWatchCommand := "ng serve --open",

        ngUIClean := {
            println("Cleaning ng UI")
            runNGCommand((ngUIDirectory in Compile).value, ngUICleanCommand.value)
        },

        ngUIDist := {
            println("Creating UI dist")
            val result = runNGCommand((ngUIDirectory in Compile).value, ngUIDistCommand.value).exitValue()
            if (result == 0) {
                Analysis.Empty
            } else {
                Analysis.Empty
            }
        },

        // TODO:need write ui clean task using gulp
        // clean <<= clean dependsOn uiClean

        dist <<= dist dependsOn ngUIDist,

        stage <<= stage dependsOn ngUIDist,

        playRunHooks += NGWatch((ngUIDirectory in Compile).value, ngUIWatchCommand.value)
    )
    // method to run the ui commands
    private def runNGCommand(uiDirectory: File, ngCommand: String): Process = {

        if (System.getProperty("os.name").startsWith("Windows")) {
            val process: ProcessBuilder = Process("cmd /c " + ngCommand, uiDirectory)
            println(s"Will run: ${process.toString} in ${uiDirectory.getPath}")
            process.run()
        } else {
            val process: ProcessBuilder = Process(ngCommand, uiDirectory)
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