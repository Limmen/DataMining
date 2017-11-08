package kth.se.id2222

object Apriori {

  /**
   * Count items (use triangular matrix or triple table)
   */
  def firstPass[A](baskets: List[Basket[A]]): List[(Set[Item[A]], Int)] = ???

  /**
   * Count k-sets of items that are frequent only.
   * Migh use DataUtils.generateAllSetsOfSizeK(k, frequentItems) and
   * DataUtils.support(items, baskets)
   */
  def kthPass[A](k: Int, supportThreshold: Int, countedItemSets: List[(Set[Item[A]], Int)]): List[(Set[Item[A]], Int)] = ???

}
