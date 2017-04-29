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

import scala.annotation.tailrec

case class Board(pieces: Set[Piece], rows: Int, columns: Int, safe: Iterable[Position]) {
  // generates new configuration with one new put chess piece
  def putNewPiece(p: Char): Set[Board] =
    safe
      .map(op => Piece(p, op))
      .filter(np => pieces.forall(_.isInSafeFrom(np))) // existing pieces should remain being in safe positions
      .map(np => Board(pieces + np, rows, columns, safe = safe.filter(pos => np.isSafeFor(pos)))) // filter unsafe positions form the safe list
      .toSet // new Board should be unique this is why they're presented as Set

  @tailrec
  final def putFewPieces(pieces: Seq[Char], acc: Set[Board] = Set.empty[Board]): Set[Board] =
    pieces match {
      case p +: tail if acc.isEmpty => putFewPieces(tail, putNewPiece(p)) // first piece
      case p +: tail => putFewPieces(tail, acc.flatMap(b => b.putNewPiece(p)))
      case Nil => acc
    }
}

object Board {
  def apply(pieces: Set[Piece], rows: Int, columns: Int): Board =
    new Board(pieces, rows, columns, extractSafePositions(pieces, rows, columns))

  // extract all possible coordinates of positions
  def getAllPossibleBoardPositions(rows: Int, columns: Int): Iterable[Position] =
    for {
      n <- 1 to rows
      m <- 1 to columns
    } yield Position(n, m)

  def extractSafePositions(pieces: Set[Piece], rows: Int, columns: Int): Iterable[Position] =
    Board
      .getAllPossibleBoardPositions(rows, columns)
      .filter(gp => pieces.forall(_.isSafeFor(gp))) // skip danger positions
}
