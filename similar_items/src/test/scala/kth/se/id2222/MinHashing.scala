package limmen.kth.se

import kth.se.id2222.{CompareSignatures, Main, MinHashing}
import org.scalatest._
import scala.collection.immutable.SortedSet

/**
 * Test suite for MinHashing.scala
 */
class MinHashingSuite extends FunSuite with Matchers {
  val testData = Array(
    ("testPub",
      List(
        ("S1", SortedSet(1, 4)),
        ("S2", SortedSet(3)),
        ("S3", SortedSet(2, 5, 4)),
        ("S4", SortedSet(1, 4, 3))
      ))
  )

  test("hashfun test") {
    val fun = Main.universalHashing(5)
    (0 to 100).map(i => assert(fun(i) <= 5 && fun(i) >= 0))
  }

  test("minhash test") {
    val minHashedData = MinHashing(2, testData)
    assert(minHashedData.length == testData(0)._2.length)
    minHashedData.foreach(row => assert(row._2.length == 2))
    val buckets = minHashedData.map(row => row._2).flatten
    buckets.foreach(b => assert(b >= 0 && b <= testData(0)._2.length - 1))
  }

  test("compare signatures") {
    val minHashedData = MinHashing(2, testData)
    minHashedData.foreach(println)
    val similarities = minHashedData.map {
      case (doc1, sign1) =>
        minHashedData.map {
          case (doc2, sign2) =>
            if (!doc1.equals(doc2))
              CompareSignatures(sign1, sign2)
        }
    }.flatten
    similarities.foreach(x => {
      x match {
        case sim: Int => assert(sim <= 1 && sim >= 0)
        case _: Any => ;
      }
    })
  }

}
