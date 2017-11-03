import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  lazy val mockito = "org.mockito" % "mockito-all" % "1.9.5" % "test"
}
