package kth.se.id2222

import org.scalatest._

class AssocRulesSuite extends FunSuite with Matchers {

  test("confidence"){
    assert(AssocRules.confidence(AssociationRule(Set(Item("Cat"), Item("dog")), Item("and")),TestData.testBaskets) == 2.0/4.0)
    assert(AssocRules.confidence(AssociationRule(Set(Item("Cat")), Item("kitten")),TestData.testBaskets) == 1.0/5.0)
    assert(AssocRules.confidence(AssociationRule(Set(Item("dog")), Item("Cat")),TestData.testBaskets) == 4.0/6.0)
  }

  test("interest"){
    assert(AssocRules.interest(AssociationRule(Set(Item("dog")), Item("Cat")),TestData.testBaskets) == (4.0/6.0) - (5.0/8.0))
    assert(AssocRules.interest(AssociationRule(Set(Item("Cat")), Item("kitten")),TestData.testBaskets) == (1.0/5.0) - (1.0/8.0))
  }

  test("findAllRules"){
    assert(AssocRules.findAllRules(2, 4.0/6.0, List((Set(Item("Cat")), 5), (Set(Item("dog")), 6)), List((Set(Item("Cat"), Item("dog")), 4)), TestData.testBaskets).length == 1)
    assert(AssocRules.findAllRules(2, 5.0/6.0, List((Set(Item("Cat")), 5), (Set(Item("dog")), 6)), List((Set(Item("Cat"), Item("dog")), 4)), TestData.testBaskets).length == 0)
    assert(AssocRules.findAllRules(8, 4.0/6.0, List((Set(Item("Cat")), 5), (Set(Item("dog")), 6)), List((Set(Item("Cat"), Item("dog")), 4)), TestData.testBaskets).length == 0)
  }
}
