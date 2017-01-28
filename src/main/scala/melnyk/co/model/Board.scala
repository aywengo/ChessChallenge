package melnyk.co.model

import scala.annotation.tailrec

case class Board (pieces: Set[Piece], rows:Int, columns:Int) {
  // generates new configuration with one new put chess piece
  def putNewPiece(p: Char): Set[Board] =
    safe
      .map(op => Piece(p, op._1, op._2))
      .filter(np => pieces.forall(_.isInSafe(np))) // existing pieces should remain being in safe positions
      .map(np => Board(pieces ++ Seq(np),rows, columns)).toSet // new Board should be unique this is why they're presented as Set

  @tailrec
  final def putFewPieces(pieces: Seq[Char], acc: Set[Board] = Set.empty[Board]): Set[Board] = {
    pieces.headOption match {
      case Some(p) if acc.isEmpty => putFewPieces(pieces.tail, putNewPiece(p)) // first piece
      case Some(p) => putFewPieces(pieces.tail, acc.flatMap(b => b.putNewPiece(p)))
      case None => acc
    }
  }

  lazy val safe: Iterable[(Int, Int)] =
    Board.getAllPossibleBoardPositions(rows, columns)
      .filter(gp => pieces.forall(p => Piece('_',gp._1, gp._2).isInSafe(p))) // skip danger positions
}

object Board {
  // extract all possible coordinates of positions
  def getAllPossibleBoardPositions(rows: Int, columns: Int): Iterable[(Int,Int)] =
    for {
      n <- 1 to rows
      m <- 1 to columns
    } yield (n,m)
}