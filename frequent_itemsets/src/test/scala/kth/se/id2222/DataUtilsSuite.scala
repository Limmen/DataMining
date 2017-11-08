package kth.se.id2222

import org.scalatest._

class DataUtilsSuite extends FunSuite with Matchers {

  val testBaskets = List(
    Basket(Set(Item("Cat"), Item("and"), Item("dog"), Item("bites"))),
    Basket(Set(Item("Yahoo"), Item("news"), Item("claims"), Item("a"), Item("Cat"), Item("mated"), Item("with"), Item("a"), Item("dog"), Item("and"), Item("produced"), Item("viable"), Item("offspring"))),
    Basket(Set(Item("Cat"), Item("Killer"), Item("likely"), Item("is"), Item("a"), Item("big"), Item("dog"))),
    Basket(Set(Item("Professional"), Item("free"), Item("advice"), Item("on"), Item("dog"), Item("training"), Item("puppy"), Item("training"))),
    Basket(Set(Item("Cat"), Item("and"), Item("kitten"), Item("training"), Item("and"), Item("behavior"))),
    Basket(Set(Item("dog"), Item("&"), Item("Cat"), Item("provides"), Item("dog"), Item("training"), Item("in"), Item("Eugene"), Item("Oregon"))),
    Basket(Set(Item("dog, and, Cat"), Item("is"), Item("a"), Item("slang"), Item("term"), Item("used"), Item("by"), Item("police"), Item("officers"), Item("for"), Item("a"), Item("male-female"), Item("relationship"))),
    Basket(Set(Item("Shop"), Item("for"), Item("your"), Item("show"), Item("dog"), Item("grooming"), Item("and"), Item("pet"), Item("supplies")))
  )
  val testCounted = List((Set(Item("a")), 4), (Set(Item("b")), 2), (Set(Item("c")), 8))

  test("readData") {
    assert(DataUtils.readData("src/resources/T10I4D100K.dat").length == 100000)
  }

  test("support") {
    assert(Main.filterItemSets(testCounted, 3).length == 2)
    assert(DataUtils.support(Set(Item("dog")),testBaskets) == 6)
    assert(DataUtils.support(Set(Item("Cat")),testBaskets) == 5)
    assert(DataUtils.support(Set(Item("and")),testBaskets) == 4)
    assert(DataUtils.support(Set(Item("dog, and, Cat")),testBaskets) == 1)
  }

  test("generate subsets"){
    assert(DataUtils.generateAllSetsOfSizeK(2, Set(Item(1), Item(2), Item(3))).length == 3)
  }

}
