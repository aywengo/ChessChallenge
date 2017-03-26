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

package melnyk.co

import melnyk.co.model.{Board, Piece}

import scala.annotation.tailrec

object Core {
  val defaultPriority: Seq[Char] = "QRBKN".toSeq // the order of calculation of combinations

  @tailrec
  def findUniqueConfigurations(pieces: Map[Char, Int], rows: Int, columns: Int,
                               accumulatedPositions: Iterator[Board] = Iterator.empty,
                               priorityQueue: Seq[Char] = defaultPriority): Iterator[Board] =
    priorityQueue match {
      case Nil =>
        // end of calculations
        accumulatedPositions
          .filter(_.pieces.size == pieces.values.sum) // except incomplete boards
      case piece +: _ if pieces.contains(piece) && accumulatedPositions.hasNext =>
        // put new bunch of chess pieces and find safe chess board configurations
        findUniqueConfigurations(pieces, rows, columns, priorityQueue = priorityQueue.tail,
          accumulatedPositions = accumulatedPositions.flatMap(_.putFewPieces(piece.toString * pieces(piece))))
      case piece +: _ if pieces.contains(piece) =>
        // first chess piece from priority queue
        findUniqueConfigurations(pieces, rows, columns, priorityQueue = priorityQueue.tail,
          accumulatedPositions = Board(Set.empty[Piece], rows, columns)
            .putFewPieces(piece.toString * pieces(piece)).toIterator)
      case _ =>
        // keep accumulatedPositions, move to next chess piece in priority queue
        findUniqueConfigurations(pieces, rows, columns, accumulatedPositions, priorityQueue.tail)
    }

  // parsing of an input parameter of pieces
  def parsePieces(input: String): Map[Char, Int] = input.groupBy(_.toUpper).mapValues(_.length)

  // parsing of an input parameter of priority queue
  def parsePriority(input: String): Seq[Char] = {
    if (input != null && input.distinct.length == 5) {
      input.toSeq
    }
    else {
      defaultPriority
    }
  }
}
