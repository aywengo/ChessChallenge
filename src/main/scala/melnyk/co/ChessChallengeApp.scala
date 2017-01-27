package melnyk.co

import melnyk.co.model.Piece

object ChessChallengeApp extends App {
  val inputPattern = """(\d+)\s*(\d+)\s*([KQBRN]+)""".r

  args.mkString(" ") match {
    case inputPattern(m, n, pieces) =>
      println(s"$m x $n")
      println(pieces)
      time {
        val t: Int = Core
          .findUniqueConfigurations(Core.countPieces(pieces), m.toInt, n.toInt)
          .foldLeft(0) { (counter, conf) => {
            if (counter % 1000000 == 0) {
              println(s"Configuration # ${counter + 1}")
              printConfigurations(n.toInt, m.toInt, conf)
              println
            }
            counter + 1
            }
          }
      println(s"$t solutions")
      }

    case _ =>
      println("The expected input is:")
      println(" M N [pieces]")
      println
      println(" For example: '7 7 KKQQBRN'")
      println("Means: 7 x 7 board 2 Kings, 2 Quins, 1 Rook, 1 Bishop, 1 kNight")
  }


  def printConfigurations(rows: Int, columns: Int, conf: Seq[Piece]): Unit =
    Core.generatePairs(rows, columns).foreach(p => {
      conf.find(piece => piece.m == p._1 && piece.n == p._2)
      match {
        case Some(piece) => print(piece.p)
        case _ => print('-')
      }
      if (p._2 == columns) println()
    })

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }
}
