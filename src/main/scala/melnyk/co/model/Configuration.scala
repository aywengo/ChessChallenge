package melnyk.co.model

object Configuration {
  // iterates through unique configurations of pieces
  def positions(rows: Int, columns: Int, pieces: Iterable[Char]): Iterable[Map[(Int, Int), Char]] = ???

  def startCombination(pieces: Seq[Char], length:Int, depth:Int):Map[(Int, Int), Char] =
    pieces.zipWithIndex
      .foldLeft(Map.empty[(Int, Int), Char])((m, p) => m ++ Map(((p._2/length) + 1,p._2 % length) -> p._1))

  def result(in: Iterable[List[Piece]]): Int =
    in.count(
      _.combinations(2)
        .forall {
          case List(p1, p2) => p1.isInSafe(p2) && p2.isInSafe(p1)
        })
}


case class Piece(p: Char, n: Int, m: Int) {
  def sameRow(p: Piece) = n == p.n
  def sameColumn(p: Piece) = m == p.m
  def sameDiag(p: Piece) = (p.m - m).abs == (p.n - n).abs
  def nextTo(p: Piece, dist:Int = 1) = (p.m - m).abs == dist && (p.n - n).abs == dist

  def isInSafe(piece: Piece): Boolean = {
    piece.p match {
      case 'Q' => !sameRow(piece) && !sameColumn(piece) && !sameDiag(piece)
      case 'R' => !sameRow(piece) && !sameColumn(piece)
      case 'B' => !sameDiag(piece)
      case 'K' => !nextTo(piece)
      case 'N' => !nextTo(piece, 2) || (sameRow(piece) || sameColumn(piece) || sameDiag(piece) || nextTo(piece))
    }
  }
}