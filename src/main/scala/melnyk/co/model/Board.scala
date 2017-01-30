package melnyk.co.model

import scala.annotation.tailrec

case class Board (pieces: Set[Piece], rows:Int, columns:Int, safe: Iterable[(Int, Int)]) {
  // generates new configuration with one new put chess piece
  def putNewPiece(p: Char): Set[Board] =
    safe
      .map(op => Piece(p, op._1, op._2))
      .filter(np => pieces.forall(_.isInSafeFrom(np))) // existing pieces should remain being in safe positions
      .map(np => Board(pieces ++ Seq(np),rows, columns,
        safe = safe.filter(pos => np.isSafeFor(pos._1, pos._2)))) // filter unsafe positions form the safe list
      .toSet // new Board should be unique this is why they're presented as Set

  @tailrec
  final def putFewPieces(pieces: Seq[Char], acc: Set[Board] = Set.empty[Board]): Set[Board] = {
    pieces.headOption match {
      case Some(p) if acc.isEmpty => putFewPieces(pieces.tail, putNewPiece(p)) // first piece
      case Some(p) => putFewPieces(pieces.tail, acc.flatMap(b => b.putNewPiece(p)))
      case None => acc
    }
  }
}

object Board {
  def apply(pieces: Set[Piece], rows: Int, columns: Int): Board =
    new Board(pieces, rows, columns, extractSafePositions(pieces,rows, columns))

  // extract all possible coordinates of positions
  def getAllPossibleBoardPositions(rows: Int, columns: Int): Iterable[(Int,Int)] =
    for {
      n <- 1 to rows
      m <- 1 to columns
    } yield (n,m)


  def extractSafePositions(pieces: Set[Piece],rows: Int, columns: Int): Iterable[(Int, Int)] =
    Board.getAllPossibleBoardPositions(rows, columns)
      .filter(gp => pieces.forall(_.isSafeFor(gp._1, gp._2))) // skip danger positions
}
