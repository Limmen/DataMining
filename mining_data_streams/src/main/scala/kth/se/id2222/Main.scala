package kth.se.id2222

import scala.io.Source

/*
 * Dataset: http://konect.uni-koblenz.de/networks/subelj_euroroad
 * Algorithm: Triest-Impl from: http://www.kdd.org/kdd2016/papers/files/rfp0465-de-stefaniA.pdf
 */
object Main {

  val edgesPath = "src/resources/subelj_euroroad/out.subelj_euroroad_euroroad"

  /*
   * Project entrypoint, streams edges from file and applies Triest-improved
   * algorithm
   */
  def main(cmdlineArgs: Array[String]): Unit = {
    val edgesFile = Source.fromFile(edgesPath).getLines
    edgesFile.foreach(s => {
      val nodes = s.split(" ")
      val edge = Edge(Vertex(nodes(0).toInt), Vertex(nodes(1).toInt))
      Triest.streamingEvent(edge)
      val (globalCount, localCounts) = Triest.estimateAll()
      globalCount
    })
    val (globalCount, localCounts) = Triest.estimateAll()
    println(s"Global estimate: ${globalCount}")

    /*
    localCounts.foreach((kv) => {
      println(s"Local estimate, vertex: ${kv._1}, estimate: ${kv._2}")
    })
     */
  }
}
