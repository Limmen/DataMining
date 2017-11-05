import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  lazy val mockito = "org.mockito" % "mockito-all" % "1.9.5" % "test"
  lazy val guava = "com.google.guava" % "guava" % "17.0"
  lazy val sizeof = "com.madhukaraphatak" %% "java-sizeof" % "0.1"
}
