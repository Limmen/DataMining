import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "kth.se.id2222",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "mining_data_streams",
    libraryDependencies ++= Seq(
      scalaTest,
      flinkCore,
      flinkScalaStreaming
    ),
    scalacOptions := Seq("-unchecked", "-deprecation")
  )
