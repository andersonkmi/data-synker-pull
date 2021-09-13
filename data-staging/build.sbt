import sbtrelease.Version
import Dependencies._

ThisBuild / version := "0.0.1"

resolvers += Resolver.sonatypeRepo("public")
scalaVersion := "2.13.6"
releaseNextVersion := { ver =>
  Version(ver).map(_.bumpMinor.string).getOrElse("Error")
}
assembly / assemblyJarName := "data-staging_0.0.1.jar"

lazy val root = (project in file("."))
  .settings(
    name := "data-staging",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "3.10.0",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-log4j2" % "1.2.0",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.12.62",
    libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.2",
    libraryDependencies += "org.json4s" %% "json4s-jackson" % "4.0.2"
  )

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
