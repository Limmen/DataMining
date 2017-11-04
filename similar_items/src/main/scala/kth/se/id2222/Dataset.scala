package kth.se.id2222

import java.io.File

import scala.io.Source

final case class Dataset(inputPath: String) {

  def getListOfSubDirectories: Array[String] = {
    new File(inputPath)
      .listFiles
      .filter(_.isDirectory)
      .map(_.getPath)
  }

  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def readFiles(paths: List[String]): List[String] = {
    for {
      path <- paths
    } yield Source.fromFile(path, "ISO-8859-1").getLines.mkString
  }

}
