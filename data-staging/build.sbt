import Dependencies._
import sbtassembly.Log4j2MergeStrategy
import sbtrelease.Version

resolvers += Resolver.sonatypeRepo("public")
scalaVersion := "2.13.6"
releaseNextVersion := { ver =>
  Version(ver).map(_.bumpMinor.string).getOrElse("Error")
}

lazy val root = (project in file("."))
  .settings(
    name := "data-staging",
    version := "1.0.0",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "3.10.0",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-log4j" % "1.0.1",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.12.62",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.12.62",
    libraryDependencies += "org.json" % "json" % "20210307"
  )

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Xfatal-warnings"
)

assembly / assemblyMergeStrategy := {
  case PathList(ps @ _*) if ps.last == "Log4j2Plugins.dat" => Log4j2MergeStrategy.plugincache
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}