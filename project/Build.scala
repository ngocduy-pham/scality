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

  val buildSettings = Defaults.coreDefaultSettings ++ SbtScalariform.scalariformSettings ++ formatSettings ++
    Seq(
      version := Version.scalityVersion,
      scalaVersion in ThisBuild := Version.scalaVersion,
      scalacOptions in Compile ++= List("-Xmax-classfile-name", "143"), // ecryptfs limit, see https://bugs.launchpad.net/ecryptfs/+bug/344878 and https://issues.scala-lang.org/browse/SI-3623
      scalacOptions in Compile += "-feature",
      //scalacOptions in ThisBuild += "-Xprint:typer",
      scalacOptions += "-Dscalac.patmat.analysisBudget=off",
      scalacOptions += "-deprecation",
      scalacOptions += "-language:reflectiveCalls",
      scalaSource in Compile <<= baseDirectory(_ / "src"),
      scalaSource in Test <<= baseDirectory(_ / "test"),
      resolvers := Seq(
        Resolver.file("Local Ivy Repository", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns),
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases",
        "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases",
        "OSSH" at "https://oss.sonatype.org/content/groups/public"),
      libraryDependencies := Seq(
        "org.scala-lang" % "scala-library" % Version.scalaVersion,
        "org.scala-lang" % "scala-compiler" % Version.scalaVersion)
      ) ++ super.settings

  lazy val scality = Project(id = "scality", base = file("."), settings = buildSettings) aggregate (patterns, io, tools, specs)
  lazy val patterns = Project(id = "scality-patterns", base = file("components/patterns"), settings = buildSettings)
  lazy val io = Project(id = "scality-io", base = file("components/io"), settings = buildSettings) dependsOn patterns
  lazy val tools = Project(id = "scality-tools", base = file("components/tools"), settings = buildSettings) dependsOn io
  lazy val specs = Project(id = "scality-specs", base = file("components/specs"), settings = buildSettings)
}
