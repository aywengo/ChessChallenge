package melnyk.co

import melnyk.co.model.{Board, Piece}

import scala.annotation.tailrec

object Core {
  val defaultPriority: Seq[Char] = "QRBKN".toSeq // the order of calculation of combination

  @tailrec
  def findUniqueConfigurations(pieces:Map[Char, Int], rows: Int, columns: Int,
                               accumulatedPositions: Iterator[Board] = Iterator.empty,
                               priorityQueue: Seq[Char] = defaultPriority): Iterator[Board] =
    priorityQueue.headOption match {
      case None =>
        // end of calculations
        accumulatedPositions
          .filter(_.pieces.size == pieces.values.sum) // except incomplete boards
      case Some(piece) if pieces.contains(piece) && accumulatedPositions.hasNext =>
        // put new bunch of chess pieces and find safe chess board configurations
        findUniqueConfigurations(pieces, rows, columns, priorityQueue = priorityQueue.tail,
          accumulatedPositions = accumulatedPositions.flatMap(b => b.putFewPieces(piece.toString*pieces(piece))))
      case Some(piece) if pieces.contains(piece) =>
        // first chess piece from priority queue
        findUniqueConfigurations(pieces, rows, columns, priorityQueue = priorityQueue.tail,
          accumulatedPositions = Board(Set.empty[Piece],rows, columns)
            .putFewPieces(piece.toString*pieces(piece)).toIterator)
      case _ =>
        // keep accumulatedPositions, move to next chess piece in priority queue
        findUniqueConfigurations(pieces, rows, columns, accumulatedPositions, priorityQueue.tail)
  }

  // parsing of an input parameter of pieces
  def parsePieces(input: String): Map[Char, Int] = input.groupBy(c => c.toUpper).mapValues(_.length)

  // parsing of an input parameter of priority queue
  def parsePriority(input: String): Seq[Char] =
    if (input != null && input.distinct.length == 5) input.toSeq
    else defaultPriority
}
