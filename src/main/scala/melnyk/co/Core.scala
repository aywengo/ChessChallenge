package melnyk.co

import melnyk.co.model.Piece

object Core {

  // extract all possible coordinates of positions
  def generatePairs(rows: Int, columns: Int): Iterable[(Int,Int)] = {
    for {
      n <- 1 to columns
      m <- 1 to rows
    } yield (n,m)
  }

  // configuration of board is expressed as sequence of pieces
  def combineGroups(p: Char, amount: Int, in: Iterable[(Int,Int)]): Iterator[Seq[Piece]] =
    reduceDangerCombinations (
      in.toSeq.combinations(amount)
        .map(i => i.map(j => Piece(p, j._1, j._2)))
    )

  // reduce combination where pieces are in danger positions to each other
  def reduceDangerCombinations(in: Iterator[Seq[Piece]]): Iterator[Seq[Piece]] = in.filter(checkForSafety)

  // check whether pieces are in danger positions to each other
  def checkForSafety(in: Seq[Piece]) =
    in.combinations(2).map(_.toList)
      .forall {
        case p1 :: p2 :: Nil => p1.isInSafe(p2) && p2.isInSafe(p1)
      }

  // parsing of an input parameter
  def countPieces(input: String) = input.groupBy(c => c.toUpper).map(e => e._1 -> e._2.length)

  // two or more pieces on the same cell
  def hasConflicts(in: Seq[Piece]) = in.groupBy(k => (k.n, k.m)).size != in.size

  // return all possible configurations
  def calculate(rows: Int, columns: Int, input: String) = {
    val inputMap = countPieces(input)

    def combinePieces(p: Char) = combineGroups(p,
      inputMap.getOrElse(p, 0),
      generatePairs(rows, columns))

    def predicate(configuration: Seq[Piece]) =
      configuration.size == input.length && // check is configuration is consistent
      !hasConflicts(configuration) &&       // check if every piece has own place
      checkForSafety(configuration)         // verify whether every piece is in safe

    for {
      queensCombinations <- combinePieces('Q') // it's supposed that combination of Queens has the lesser value
      rooksCombinations <- combinePieces('R')
      bishopsCombinations <- combinePieces('B')
      knightsCombinations <- combinePieces('N')
      kingsCombinations <- combinePieces('K')
    }
     yield
       if (predicate(queensCombinations ++ rooksCombinations ++ bishopsCombinations ++ knightsCombinations ++ kingsCombinations))
        Some(queensCombinations ++ rooksCombinations ++ bishopsCombinations ++ knightsCombinations ++ kingsCombinations)
      else None

  }
}

