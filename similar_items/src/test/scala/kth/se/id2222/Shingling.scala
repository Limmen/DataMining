package limmen.kth.se

import kth.se.id2222.{ Main, Shingling }
import org.scalatest._

/**
 * Test suite for Shingling.scala
 */
class ShinglingSuite extends FunSuite with Matchers {

  /**
    * Test case from coursebook for k-shingling
    */
  test("shingling test") {
    val shingled = Shingling(2, Main.hashFun, ("DOC1", "abcdabd"))
    assert(shingled._2.size == 5)
    assert(shingled._2.contains(Main.hashFun("ab")))
    assert(shingled._2.contains(Main.hashFun("bc")))
    assert(shingled._2.contains(Main.hashFun("cd")))
    assert(shingled._2.contains(Main.hashFun("da")))
    assert(shingled._2.contains(Main.hashFun("bd")))
  }

}
