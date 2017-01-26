package melnyk.co

import melnyk.co.model._

object ChessChallengeApp extends App {
  val piecesPattern = """^([KQBRN]+)$""".r
  val digitPattern = """^\d$""".r

  args.toList match {
    case digitPattern(m) :: digitPattern(n) :: piecesPattern(pieces) :: Nil =>
      println(s"$m x $n")
      println(pieces)
      val result: Int =
        Configuration
          .positions(m.toInt, n.toInt, pieces.toIterable)
          .map(i => i.map(p => Piece(p._2, p._1._1, p._1._2)).toList)
          .count(
            _.combinations(2)
              .forall {
                case List(p1, p2) => p1.isInSafe(p2) && p2.isInSafe(p1)
              })

      println(result)


    case _ =>
      println("The expected input is:")
      println(" M N [pieces]")
      println
      println(" For example: 7 7 KKQQBRN ")
      println("Means: 7 x 7 board 2 Kings, 2 Quins, 1 Rock, 1 Bishop, 1 kNight")
  }
}
