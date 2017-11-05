package limmen.kth.se

import kth.se.id2222.LSH
import org.scalatest._

/**
 * Test suite for LSH.scala
 */
class LSHSuite extends FunSuite with Matchers {
  val testData =
      List(
        ("S1", Vector(0,1)),
        ("S2", Vector(0,1)),
        ("S3", Vector(3,2)),
        ("S4", Vector(0,2))
      )

  test("LSH test") {
    val candidates = LSH(testData, 1, 2)
    assert(candidates.size == 4)
    assert(candidates.contains((("S1", Vector(0,1)), ("S2", Vector(0,1)))) || candidates.contains((("S2", Vector(0,1)), ("S1", Vector(0,1)))))
    assert(candidates.contains((("S1", Vector(0,1)), ("S4", Vector(0,2)))) || candidates.contains((("S4", Vector(0,2)), ("S1", Vector(0,1)))))
    assert(candidates.contains((("S2", Vector(0,1)), ("S4", Vector(0,2)))) || candidates.contains((("S4", Vector(0,2)), ("S2", Vector(0,1)))))
    assert(candidates.contains((("S3", Vector(3,2)), ("S4", Vector(0,2)))) || candidates.contains((("S4", Vector(0,2)), ("S3", Vector(3,2)))))
    assert(!candidates.contains((("S3", Vector(3,2)), ("S1", Vector(0,1)))) && !candidates.contains((("S1", Vector(0,1)), ("S3", Vector(3,2)))))
    assert(!candidates.contains((("S3", Vector(3,2)), ("S2", Vector(0,1)))) && !candidates.contains((("S2", Vector(0,1)), ("S3", Vector(3,2)))))
  }

}
