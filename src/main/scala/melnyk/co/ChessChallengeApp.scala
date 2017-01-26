package melnyk.co

object ChessChallengeApp extends App {
  val piecesPattern = """^([KQBRN]+)$""".r
  val digitPattern = """^\d$""".r

  args.toList match {
    case digitPattern(m) :: digitPattern(n) :: piecesPattern(pieces) :: Nil =>
      println(s"$m x $n")
      println(pieces)
      val result: Int =
        Core.calculate(m.toInt, n.toInt, pieces).count(_.isDefined)

      println(result)

    case _ =>
      println("The expected input is:")
      println(" M N [pieces]")
      println
      println(" For example: 7 7 KKQQBRN ")
      println("Means: 7 x 7 board 2 Kings, 2 Quins, 1 Rock, 1 Bishop, 1 kNight")
  }
}
