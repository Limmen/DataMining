package kth.se.id2222

import scala.collection.immutable.SortedSet

/*
 * A class MinHashing that builds a minHash signature
 * (in the form of a vector or a set) of a given
 * length n from a given set of integers (a set of hashed shingles).
 */
object MinHashing {

  def apply(n: Int, shingledData: Array[(String, List[(String, SortedSet[Int])])]): List[(String, Vector[Int])] = {
    val rows = shingledData.flatMap {
      case (publisher, shingledDocs) =>
        shingledDocs.flatten {
          case (document, shingles) =>
            shingles
        }
    }.distinct

    val columns = shingledData.flatMap {
      case (publisher, shingledDocs) =>
        shingledDocs
    }

    val hashFuns = (0 to n - 1).map((_) => Main.universalHashing(rows.length - 1)).toList

    val placeHolderMatrix = Array.tabulate(n, columns.length)((x, y) => Integer.MAX_VALUE)

    (0 to rows.size - 1).map((i) => {
      val rowHashes = hashFuns.map(h => h(i))
      (0 to columns.size - 1).map((j) => {
        val col = columns(j)
        if (col._2.contains(rows(i))) {
          (0 to n - 1).map(k => {
              if (rowHashes(k) < placeHolderMatrix(k)(j)) {
                placeHolderMatrix(k)(j) = rowHashes(k)
              }
          })
        }
      })
    })

    (0 to columns.size - 1).map((i) => {
      val col = columns(i)
      val minhash = (0 to placeHolderMatrix.length - 1).map((j) => placeHolderMatrix(j)(i)).toVector
      (col._1, minhash)
    }).toList
  }

}
