import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "hello-cats",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.typelevel" %% "cats-core" % "2.0.0",
      "org.typelevel" %% "cats-macros" % "2.0.0",
      "org.typelevel" %% "cats-kernel" % "2.0.0"
    ),
    scalacOptions ++= Seq(
      // "-Ypartial-unification", 
      "-language:higherKinds"
      )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
