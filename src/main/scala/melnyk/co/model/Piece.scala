package melnyk.co.model

abstract class Piece(val pieceSymbol: Char, val position:Position) {
  def sameRow(pos:Position): Boolean = position.row == pos.row

  def sameColumn(pos:Position): Boolean = position.column == pos.column

  def sameDiag(pos:Position): Boolean = (pos.column - position.column).abs == (pos.row - position.row).abs

  def nextTo(pos:Position): Boolean = (pos.column - position.column).abs <= 1 && (pos.row - position.row).abs <= 1

  def knightJump(pos:Position): Boolean =
    ((pos.column - position.column).abs, (pos.row - position.row).abs) match {
      case (1, 2) | (2, 1) | (0, 0) => true
      case _ => false
    }

  def isSafeFor(position:Position): Boolean

  def isInSafeFrom(piece: Piece): Boolean = piece.isSafeFor(position)
}

object Piece {
  def apply(p: Char, position:Position): Piece = p match {
    case 'Q' => Queen(position)
    case 'R' => Rook(position)
    case 'B' => Bishop(position)
    case 'K' => King(position)
    case 'N' => Knight(position)
  }
}

case class Queen(onPosition:Position) extends Piece('Q', onPosition) {
  def isSafeFor(pos:Position): Boolean = !(sameRow(pos) || sameColumn(pos) || sameDiag(pos))
}

case class Rook(onPosition:Position) extends Piece('R', onPosition) {
  def isSafeFor(pos:Position): Boolean = !(sameRow(pos) || sameColumn(pos))
}

case class Bishop(onPosition:Position) extends Piece('B', onPosition) {
  def isSafeFor(pos:Position): Boolean = !sameDiag(pos)
}

case class King(onPosition:Position) extends Piece('K', onPosition) {
  def isSafeFor(pos:Position): Boolean = !nextTo(pos)
}

case class Knight(onPosition:Position) extends Piece('N', onPosition) {
  def isSafeFor(pos:Position): Boolean = !knightJump(pos)
}

case class Position(row: Int, column: Int)
