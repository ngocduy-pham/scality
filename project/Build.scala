import java.io.File
import sbt._
import Keys._
import Process._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

object ScalityBuild extends Build {

  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formattingPreferences,
    ScalariformKeys.preferences in Test := formattingPreferences
  )

  def formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences()
      .setPreference(RewriteArrowSymbols, false)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
  }

  lazy val scalaOrg = "org.scala-lang"
  // scala version + resolver
  lazy val scalaSettings = Defaults.defaultSettings ++ formatSettings ++ Seq(
    // scalaHome := Some(file(Path.userHome + "/work/devl/scalac/scala-virtualized/build/pack")),
    // scalaOrganization := scalaOrg,
    scalaVersion := "2.10.2",
    scalacOptions ++= List("-unchecked", "-deprecation"),
    resolvers in ThisBuild += ScalaToolsSnapshots,
    resolvers += "OSSH" at "https://oss.sonatype.org/content/groups/public",
    resolvers += Resolver.sonatypeRepo("snapshots"),

    // paths - so we don't need to have src/main/scala ... just src/ test/ and resources/
    scalaSource in Compile <<= baseDirectory(_ / "src"),
    scalaSource in Test <<= baseDirectory(_ / "test"),
    // resourceDirectory in Compile <<= baseDirectory(_ / "resources"),

    // target directory outside of base directory will cause sbteclipse to crash
    // target := new File("D:/junk/scala/scality/target"),

    // sbteclipse needs some info on source directories:
    unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(Seq(_)),
    unmanagedSourceDirectories in Test <<= (scalaSource in Test)(Seq(_)),

    // add the library, reflect and the compiler as libraries
    libraryDependencies <<= scalaVersion(ver => Seq(
      scalaOrg % "scala-library" % ver,
      // scalaOrg % "scala-reflect" % ver,
      scalaOrg % "scala-compiler" % ver
      // "org.scalatest" % "scalatest_2.10" % "2.0.M6-SNAP7" % "test",
      // "junit" % "junit" % "4.8.1" % "test" // we need JUnit explicitly
    )),

    // testing
    parallelExecution in Test := false
  )

  lazy val scality = Project(id = "scality", base = file("."), settings = scalaSettings) aggregate (designPattern, fileIO)
  lazy val designPattern = Project(id = "scality-design-pattern", base = file("components/design"), settings = scalaSettings)
  lazy val fileIO = Project(id = "scality-file-io", base = file("components/fileio"), settings = scalaSettings) dependsOn designPattern

}
