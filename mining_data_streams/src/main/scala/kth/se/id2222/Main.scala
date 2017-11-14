package kth.se.id2222

import scala.io.Source

/*
 *
 */
object Main {

  val edgesPath = "src/resources/subelj_euroroad/out.subelj_euroroad_euroroad"

  /*
   *
   */
  def main(cmdlineArgs: Array[String]): Unit = {
    val edgesFile = Source.fromFile(edgesPath).getLines
    edgesFile.map(s => {
      val nodes = s.split(" ")
      val edge = Edge(Vertex(nodes(0).toInt), Vertex(nodes(1).toInt))
      Triest.streamingEvent(edge)
      val (globalCount, localCounts) = Triest.estimateAll()
      globalCount
    })
      .foreach(println)
  }
}

/*
  def main(cmdlineArgs: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val textStream: DataStream[String] = env.readTextFile(edgesPath)
    val edges = textStream
      .map(s => {
      val nodes = s.split(" ")
      val edge = Edge(Vertex(nodes(0).toInt), Vertex(nodes(1).toInt))
      Triest.streamingEvent(edge)
      val (globalCount, localCounts) = Triest.estimateAll()
      globalCount
    })
    env.execute("Triest DataMining")*/
