import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  lazy val flinkCore = "org.apache.flink" % "flink-core" % "1.3.2"
  lazy val flinkScalaStreaming = "org.apache.flink" % "flink-streaming-scala_2.11" % "1.3.2"
}
