package melnyk.co

import melnyk.co.model.Piece
import scala.annotation.tailrec

object Core {
  val priority: Seq[Char] = "QRBNK" // the order of calculation of combination

  @tailrec
  def findUniqueConfigurations(inputMap:Map[Char, Int],
                               rows: Int, columns: Int,
                               accum: Iterator[Seq[Piece]] = Iterator.empty,
                               priority: Seq[Char] = priority): Iterator[Seq[Piece]] =
    priority.headOption match {
      case None => // end of calculations
        accum.filter(_.size == inputMap.values.sum)
      case Some(piece) if inputMap.contains(piece) =>
        val accumulator =
          if (!accum.hasNext) { // first chess piece from priority queue
            combinePieces(piece, inputMap, rows, columns)
                .filter(k => checkForSafety(k))
                .map(Some(_))
          }
          else
            for {
              accumulated: Seq[Piece] <- accum
              combined: Seq[Piece] <- combinePieces(piece, inputMap, rows, columns)
            } yield {
              if (checkForSafety(combined, accumulated)) Some(accumulated ++ combined)
              else None
            }
        findUniqueConfigurations(inputMap, rows, columns, accumulator.flatten, priority.tail)
      case _ =>
        findUniqueConfigurations(inputMap, rows, columns, accum, priority.tail)
  }


  def combinePieces(p: Char, inputMap:Map[Char, Int], rows: Int, columns: Int): Iterator[Seq[Piece]] =
    if (inputMap.contains(p))
      generatePairs(rows, columns)
        .toSeq.combinations(inputMap(p))
        .map(i => i.map(j => Piece(p, j._1, j._2)))
    else Iterator.empty


  // extract all possible coordinates of positions
  def generatePairs(rows: Int, columns: Int): Iterable[(Int,Int)] = {
    for {
      n <- 1 to columns
      m <- 1 to rows
    } yield (n,m)
  }

  // check whether pieces are in danger positions to each other
  @tailrec
  def checkForSafety(in: Seq[Piece], add: Seq[Piece] = Seq.empty[Piece]): Boolean =
    in.headOption match {
      case Some(p1) if add.exists(p => p1.samePosition(p)) || in.tail.exists(p => !p1.isInSafe(p) || !p.isInSafe(p1)) => false
      case Some(p1) if in.tail.nonEmpty && add.isEmpty => in.tail.forall(p2 => p1.isInSafe(p2) && p2.isInSafe(p1)) && checkForSafety(in.tail)
      case Some(p1) if in.tail.nonEmpty => add.forall(p2 => p1.isInSafe(p2) && p2.isInSafe(p1)) && checkForSafety(in.tail, add)
      case Some(p1) => add.forall(p2 => p1.isInSafe(p2) && p2.isInSafe(p1))
      case _ => false
    }

  // parsing of an input parameter
  def countPieces(input: String): Map[Char, Int] = input.groupBy(c => c.toUpper).map(e => e._1 -> e._2.length)

  // two or more pieces on the same cell
  def hasConflicts(in: Seq[Piece]): Boolean = in.map(k => (k.n, k.m)).distinct.size != in.size

}

