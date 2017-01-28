package melnyk.co.model

case class Piece(p: Char, n: Int, m: Int) {
  def sameRow(p: Piece): Boolean    = n == p.n
  def sameColumn(p: Piece): Boolean = m == p.m
  def sameDiag(p: Piece): Boolean   = (p.m - m).abs == (p.n - n).abs
  def nextTo(p: Piece): Boolean     = (p.m - m).abs <= 1 && (p.n - n).abs <= 1
  def knightJump(p: Piece): Boolean =
    ((p.m - m).abs, (p.n - n).abs) match {
      case (1,2) | (2,1) | (0,0) => true
      case _ => false
    }

  def isInSafe(piece: Piece): Boolean = {
    piece.p match {
      case 'Q' => !sameRow(piece) && !sameColumn(piece) && !sameDiag(piece)
      case 'R' => !sameRow(piece) && !sameColumn(piece)
      case 'B' => !sameDiag(piece)
      case 'K' => !nextTo(piece)
      case 'N' => !knightJump(piece)
    }
  }
}
