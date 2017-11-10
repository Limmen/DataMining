package kth.se.id2222.spark

/**
 * Solve the second sub-problem,
 * i.e., develop and implement an algorithm for generating
 * association rules between frequent itemsets discovered by
 * using the Apriori algorithm in a dataset of sales transactions.
 * The rules must have support at least s and confidence at least c,
 * where s and c are given as input parameters.
 */
object AssocRules {

  def confidence[A](rule: AssociationRule[A], baskets: List[Basket[A]]): Double = ???

  def interest[A](rule: AssociationRule[A], baskets: List[Basket[A]]): Double = ???

  def findAllRules[A](supportThreshold: Double, confidenceThreshold: Double, countedItemSets: List[(Set[Item[A]], Int)]) : List[AssociationRule[A]] = ???
}

case class AssociationRule[A](subject: Set[Item[A]], proposition: Item[A])
