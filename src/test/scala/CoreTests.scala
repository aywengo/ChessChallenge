import melnyk.co.Core
import melnyk.co.model.Board
import org.scalatest.{FlatSpec, Matchers}

class CoreTests extends FlatSpec with Matchers {
  "Core " should "calculate all possible positions on the 2x2 table for one piece" in {
    val combinations = Board.getAllPossibleBoardPositions(2,2)

    combinations should not be empty
    combinations.size shouldBe 4
    combinations should contain (1,1)
    combinations should contain (1,2)
    combinations should contain (2,1)
    combinations should contain (2,2)
  }

  it should "find unique 4 configurations for 3x3 board containing 2 Kings and 1 Rook" in {
    val result = Core.findUniqueConfigurations(Core.parsePieces("KKR"),3,3)
    result.size shouldBe 4
  }

  it should "find unique 8 configurations for 4x4 board containing 2 Rooks and 4 Knights" in {
    val result = Core.findUniqueConfigurations(Core.parsePieces("RRNNNN"),4,4)
    result.size shouldBe 8
  }

  it should "group and count chess pieces from input" in {
    val pieces = Core.parsePieces("KKQQQR")

    pieces should not be empty
    pieces.size shouldBe 3
    pieces should contain ('K'->2)
    pieces should contain ('Q'->3)
    pieces should contain ('R'->1)
    pieces should not contain ('N'->1)
  }

  it should "group and count nothing from an empty input" in {
    Core.parsePieces("") shouldBe empty
  }

  it should "parse return default priority from an empty input" in {
    Core.parsePriority(null) shouldEqual Core.defaultPriority
    Core.parsePriority("") shouldEqual Core.defaultPriority
  }

  it should "parse return default priority from an incomplete input" in {
    Core.parsePriority("RNBQ") shouldEqual Core.defaultPriority // should contain 5 pieces
    Core.parsePriority("RNBQQ") shouldEqual Core.defaultPriority // duplicated QQ
    Core.parsePriority("K") shouldEqual Core.defaultPriority // only one when 5 is expected
  }

  it should "parse priority from an input" in {
    Core.parsePriority("RNBQK") shouldEqual Seq('R', 'N', 'B', 'Q', 'K')
  }
}
