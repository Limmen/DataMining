package kth.se.id2222

import scala.collection.mutable.HashMap
import scala.io.Source


object DataUtils {

  def readData(path: String): List[Basket[Int]] = {
    Source.fromFile(path).getLines.toList.map((line)=> Basket[Int](line.split(" ").map(item => Item[Int](item.toInt)).toSet))
  }

  def support[A](items: List[Set[Item[A]]], baskets: List[Basket[A]]): HashMap[Set[Item[A]],Int] = {
    val hm = new HashMap[Set[Item[A]],Int]()
    baskets.foreach { basket =>
      items.foreach { item =>
        if (item.subsetOf(basket.items))
          if(hm isDefinedAt item)
            hm += (item -> (hm(item) + 1))
          else
            hm += (item -> 1)
      }
    }
    hm
  }

  def generateAllSetsOfSizeK[A](k: Int, items: Set[Item[A]]) : List[Set[Item[A]]]= {
    items.subsets(k).toList
  }
}

case class Item[A](item: A)

case class Basket[A](items: Set[Item[A]])
