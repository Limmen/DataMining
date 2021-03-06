package kth.se.id2222

import java.util.concurrent.ThreadLocalRandom
import scala.collection.mutable.{ HashMap, Set }
import scala.util.Random

/*
 * Implementation of Triest-Improved
 * Paper: http://www.kdd.org/kdd2016/papers/files/rfp0465-de-stefaniA.pdf
 */
object Triest {

  val sample: Set[Edge] = Set.empty
  var t = 0
  var tau = 0
  val M = 750
  val rnd = new Random()
  val localCounts = new HashMap[Vertex, Double]()

  def streamingEvent(edge: Edge): Unit = {
    t += 1
    updateCounters(PlusEdge, edge)
    if (sampleEdge(edge)) {
      sample += edge
    }
  }

  def sampleEdge(edge: Edge): Boolean = {
    if (t <= M) {
      return true
    } else if(flipBiasedCoin(M/t.toDouble)) {
      val randomEdge = sample.toVector(rnd.nextInt(sample.size))
      sample -= randomEdge
      //updateCounters(MinusEdge, edge)
      return true
    }
    return false
  }

  def updateCounters(symb: Symbol, edge: Edge): Unit = {
    val combinedNeighboorhood = getNeighborHood(edge.node1) intersect getNeighborHood(edge.node2)
    combinedNeighboorhood.foreach(c => {
      symb match {
        case PlusEdge => {
          tau += 1
          List(c, edge.node1, edge.node2).foreach(c2 => {
            if(localCounts isDefinedAt c2){
              localCounts += (c2 -> (localCounts(c2) + estimateFactor()))
            } else {
              localCounts += (c2 -> estimateFactor())
            }
          })
        }
        case MinusEdge => {
          tau -= 1
          List(c, edge.node1, edge.node2).foreach(c2 => {
            if((localCounts isDefinedAt c2) && (localCounts(c2) > 0)){
              localCounts += (c2 -> (localCounts(c2) -1))
            } else {
              localCounts += (c2 -> 0)
            }
          })
        }
      }
    })
  }

  def flipBiasedCoin(k: Double): Boolean = {
    val coinToss = ThreadLocalRandom.current().nextDouble(0, 1)
    if(coinToss <= k)
      return true
    else
      return false
  }

  def getNeighborHood(node: Vertex) : Set[Vertex] = {
    sample.toList.foldLeft(Set[Vertex]())((acc, edge) => {
      edge match {
      case Edge(node3, node2) if(node3.id == node.id) => acc+node2
      case Edge(node2, node3) if(node3.id == node.id) => acc+node2
      case _ => acc
      }
    })
  }

  def estimateAll() : (Double, HashMap[Vertex, Double]) = {
    val globalEstimate = tau* estimateFactor()
    val localEstimates = localCounts.map((kv) => {
      (kv._1, kv._2 * estimateFactor())
    })
    (globalEstimate, localEstimates)
  }

  def estimateFactor() : Double = {
    Math.max(1.0, ((t*(t-1)*(t-2))/(M*(M-1)*(M-2))).toDouble)
  }
}

trait Symbol

object PlusEdge extends Symbol

object MinusEdge extends Symbol

case class Vertex(id: Int)

case class Edge(node1: Vertex, node2: Vertex)
