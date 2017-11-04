import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "kth.se.id2222",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "similar_items",
    libraryDependencies ++= Seq(
      scalaTest,
      mockito,
      guava
    )
  )
