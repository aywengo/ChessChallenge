//MIT License
//
//Copyright (c) 2017 Roman Melnyk
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

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
