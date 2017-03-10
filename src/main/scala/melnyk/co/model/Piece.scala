package melnyk.co.model

abstract class Piece(val pieceSymbol: Char, val n: Int, val m: Int) {
  def sameRow(row: Int, column: Int): Boolean = n == row

  def sameColumn(row: Int, column: Int): Boolean = m == column

  def sameDiag(row: Int, column: Int): Boolean = (column - m).abs == (row - n).abs

  def nextTo(row: Int, column: Int): Boolean = (column - m).abs <= 1 && (row - n).abs <= 1

  def knightJump(row: Int, column: Int): Boolean =
    ((column - m).abs, (row - n).abs) match {
      case (1, 2) | (2, 1) | (0, 0) => true
      case _ => false
    }

  def isSafeFor(n: Int, m: Int): Boolean

  def isInSafeFrom(piece: Piece): Boolean = piece.isSafeFor(this.n, this.m)
}

object Piece {
  def apply(p: Char, n: Int, m: Int): Piece = p match {
    case 'Q' => Queen(n, m)
    case 'R' => Rook(n, m)
    case 'B' => Bishop(n, m)
    case 'K' => King(n, m)
    case 'N' => Knight(n, m)
  }
}

case class Queen(row: Int, column: Int) extends Piece('Q', row, column) {
  def isSafeFor(n: Int, m: Int): Boolean = !(sameRow(n, m) || sameColumn(n, m) || sameDiag(n, m))
}

case class Rook(row: Int, column: Int) extends Piece('R', row, column) {
  def isSafeFor(n: Int, m: Int): Boolean = !(sameRow(n, m) || sameColumn(n, m))
}

case class Bishop(row: Int, column: Int) extends Piece('B', row, column) {
  def isSafeFor(n: Int, m: Int): Boolean = !sameDiag(n, m)
}

case class King(row: Int, column: Int) extends Piece('K', row, column) {
  def isSafeFor(n: Int, m: Int): Boolean = !nextTo(n, m)
}

case class Knight(row: Int, column: Int) extends Piece('N', row, column) {
  def isSafeFor(n: Int, m: Int): Boolean = !knightJump(n, m)
}
