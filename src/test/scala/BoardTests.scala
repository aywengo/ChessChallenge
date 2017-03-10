import melnyk.co.model.{Board, Position, Queen}
import org.scalatest.{FlatSpec, Matchers}

class BoardTests extends FlatSpec with Matchers {
  "Board " should "have not safe with one Queen on the 2x2 table" in {
    Board(Set(Queen(Position(1, 1))), 2, 2).safe shouldBe empty
  }

  it should "have 2 safe places with one Queen on the 3x3 table" in {
    Board(Set(Queen(Position(1, 1))), 3, 3).safe.size shouldBe 2
  }

  it should "be able put new King with one Queen(1,1) on the 3x3 table" in {
    Board(Set(Queen(Position(1, 1))), 3, 3).putNewPiece('K') should not be empty
    Board(Set(Queen(Position(1, 1))), 3, 3).putNewPiece('K').size shouldBe 2
  }

  it should "NOT be able put new King with one Queen(1,1) on the 2x2 table" in {
    Board(Set(Queen(Position(1, 1))), 2, 2).putNewPiece('K') shouldBe empty
  }

  it should "be able put two Kings with one Queen(1,1) on the 4x4 table" in {
    Board(Set(Queen(Position(1, 1))), 4, 4).putFewPieces("KK") should not be empty
    Board(Set(Queen(Position(1, 1))), 4, 4).putFewPieces("KK").size shouldBe 7
  }

  it should "calculate all possible positions on the 2x3 table for one piece" in {
    val combinations = Board.getAllPossibleBoardPositions(2, 3)

    combinations should not be empty
    combinations.size shouldBe 6
    combinations should contain(Position(1, 1))
    combinations should contain(Position(1, 2))
    combinations should contain(Position(1, 3))
    combinations should contain(Position(2, 1))
    combinations should contain(Position(2, 2))
    combinations should contain(Position(2, 3))
    combinations should not contain Position(3, 1)
    combinations should not contain Position(3, 2)
  }

  it should "extract a collection which contains every single safe position" in {
    val safeCollection = Board(Set(Queen(Position(1, 1))), 3, 3).safe.toSeq

    safeCollection should not be empty
    safeCollection.size shouldBe 2
    safeCollection should contain(Position(2, 3))
    safeCollection should contain(Position(3, 2))
    safeCollection should not contain Position(3, 3)
  }
}
