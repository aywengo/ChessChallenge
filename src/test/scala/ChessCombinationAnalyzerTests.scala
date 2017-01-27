import melnyk.co.Core
import melnyk.co.model._
import org.scalatest.{FlatSpec, _}

class CoreTests extends FlatSpec with Matchers {
  "Core " should "calculate all possible positions on the 2x2 table for one piece" in {
    val combinations = Core.generatePairs(2,2)

    combinations should not be empty
    combinations.size shouldBe 4
    combinations should contain (1,1)
    combinations should contain (1,2)
    combinations should contain (2,1)
    combinations should contain (2,2)
  }

  it should "find unique 4 configurations for 3x3 board containing 2 Kings and 1 Rook" in {
    val result = Core.findUniqueConfigurations(Core.countPieces("KKR"),3,3)
    result.size shouldBe 4
  }

  it should "find unique 8 configurations for 4x4 board containing 2 Rooks and 4 Knights" in {
    val result = Core.findUniqueConfigurations(Core.countPieces("RRNNNN"),4,4)
    result.size shouldBe 8
  }
}

class ChessCombinationAnalyzerTests extends FlatSpec with Matchers {

  "Analyzer " should "calculate all possible positions on the 2x3 table for one piece" in {
    val combinations = Core.generatePairs(2,3)

    combinations should not be empty
    combinations.size shouldBe 6
    combinations should contain (1,1)
    combinations should contain (1,2)
    combinations should contain (2,1)
    combinations should contain (2,2)
    combinations should contain (3,1)
    combinations should contain (3,2)
    combinations should not contain (2,3)
    combinations should not contain (2,4)
  }

  it should "group and count chess pieces from input" in {
    val pieces = Core.countPieces("KKQQQR")

    pieces should not be empty
    pieces.size shouldBe 3
    pieces should contain ('K'->2)
    pieces should contain ('Q'->3)
    pieces should contain ('R'->1)
    pieces should not contain ('N'->1)
  }

  it should "group and count nothing from an empty input" in {
    Core.countPieces("") shouldBe empty
  }

  it should "reduce combinations where chess pieces are in danger positions" in {
    val combinations = List(
      Seq(Piece('K',1,1),Piece('K',1,2)), // two Kings next to each other
      Seq(Piece('K',1,1),Piece('K',2,2)), // two Kings next to each other on diag
      Seq(Piece('Q',1,1),Piece('Q',2,2)), // two Queens on the same diag
      Seq(Piece('Q',2,1),Piece('Q',2,1)), // two Queens on the same column
      Seq(Piece('Q',1,1),Piece('Q',1,2)),  // two Queens on the same row
      Seq(Piece('N',1,1),Piece('N',2,3)),  // two Queens on the same row
      Seq(Piece('N',1,1),Piece('N',3,2))  // two Queens on the same row
    ).filter(i => Core.checkForSafety(i))

    combinations shouldBe empty
  }

  it should "not reduce combinations where chess pieces are in safe positions" in {
    val input = List(
      Seq(Piece('K',1,3),Piece('K',1,1)), // two Kings next to each other by two cells
      Seq(Piece('K',3,3),Piece('K',1,1)), // two Kings next to each other on diag by two cells
      Seq(Piece('Q',1,1),Piece('Q',2,3)), // two Queens not on the same diag nor column nor row
      Seq(Piece('N',1,1),Piece('N',2,2)),  // two knights next to each other
      Seq(Piece('N',1,1),Piece('N',4,4))  // two knights far to each other
    )
    val combinations = input.filter(i => Core.checkForSafety(i))

    combinations.size shouldBe input.size
  }

}
