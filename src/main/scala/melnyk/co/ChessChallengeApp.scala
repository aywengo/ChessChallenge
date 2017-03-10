package melnyk.co

import melnyk.co.model.{Board, Piece}

import scala.annotation.tailrec
import scala.concurrent.duration._

object ChessChallengeApp extends App {
  val inputPattern = """(\d+)\s*(\d+)\s*([KQBRN]+)\s*(\d*)?\s*([KQBRN]{5})?""".r

  args.mkString(" ") match {
    case inputPattern(m, n, pieces, step, priority) =>
      println(s"Dimension of the chess board: $m x $n")
      val ic = Core.parsePieces(pieces)
      println(s"Pieces: ${ic.mkString(", ")}")
      val s = if (step.nonEmpty && step.toInt > 0) step.toInt else 1
      println(s"Step of log: one per $s")
      val cp = Core.parsePriority(priority)
      println(s"Priority of computation: ${drawPriorityQueue(cp)}")

      measureTime {
        val t: Int =
          Core
            .findUniqueConfigurations(ic, m.toInt, n.toInt, priorityQueue = cp)
            .foldLeft(0) {
              (counter, conf) => {
                if (counter % s == 0) {
                  println(s"Configuration # ${counter + 1}")
                  printConfigurations(n.toInt, m.toInt, conf.pieces)
                  println
                }
                counter + 1
              }
            }
        println(s"Found: $t solutions")
      }

    case _ =>
      println("The expected input is:")
      println(" M N pieces [step] [priority]")
      println
      println(" For example: '7 7 KKQQBRN 1000000 QRBKN'")
      println("Means: 7 x 7 board 2 Kings, 2 Quins, 1 Rook, 1 Bishop, 1 kNight with stepping once in 100000 " +
        "and with priority Quin->Rook->Bishop->King->kNight")
  }


  private def printConfigurations(rows: Int, columns: Int, conf: Set[Piece]): Unit =
    Board.getAllPossibleBoardPositions(rows, columns).foreach(p => {
      conf.find(piece => piece.position.column == p.column && piece.position.row == p.row)
      match {
        case Some(piece) => print(s" ${piece.pieceSymbol} ")
        case _ => print(" - ")
      }
      if (p.column == columns) println()
    })

  @tailrec
  private def drawPriorityQueue(in: Seq[Char], out: String = ""): String =
    in match {
      case p +: tail => drawPriorityQueue(tail, s"$out -> $p")
      case Nil => out
    }

  private def measureTime[R](block: => R): R = {
    val t0 = System.currentTimeMillis()
    val result = block
    val t1 = System.currentTimeMillis()
    val elapsed = DurationLong(t1 - t0).millis
    println(s"Elapsed time: $elapsed ms")
    result
  }
}
