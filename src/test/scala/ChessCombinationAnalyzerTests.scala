import melnyk.co.Core
import melnyk.co.model._
import org.scalatest.{FlatSpec, _}

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

class BoardTests extends FlatSpec with Matchers {
  "Board " should "have not safe with one Queen on the 2x2 table" in {
    Board(Set(Queen(1,1)),2,2).safe shouldBe empty
  }

  it should "have 2 safe places with one Queen on the 3x3 table" in {
    Board(Set(Queen(1,1)),3,3).safe.size shouldBe 2
  }

  it should "be able put new King with one Queen(1,1) on the 3x3 table" in {
    Board(Set(Queen(1,1)),3,3).putNewPiece('K') should not be empty
    Board(Set(Queen(1,1)),3,3).putNewPiece('K').size shouldBe 2
  }

  it should "NOT be able put new King with one Queen(1,1) on the 2x2 table" in {
    Board(Set(Queen(1,1)),2,2).putNewPiece('K') shouldBe empty
  }

  it should "be able put two Kings with one Queen(1,1) on the 4x4 table" in {
    Board(Set(Queen(1,1)),4,4).putFewPieces("KK") should not be empty
    Board(Set(Queen(1,1)),4,4).putFewPieces("KK").size shouldBe 7
  }

  it should "calculate all possible positions on the 2x3 table for one piece" in {
    val combinations = Board.getAllPossibleBoardPositions(2,3)

    combinations should not be empty
    combinations.size shouldBe 6
    combinations should contain (1,1)
    combinations should contain (1,2)
    combinations should contain (1,3)
    combinations should contain (2,1)
    combinations should contain (2,2)
    combinations should contain (2,3)
    combinations should not contain (3,1)
    combinations should not contain (3,2)
  }

  it should "extract a colection which contains every single safe position" in {
    val safeCollection = Board(Set(Queen(1,1)),3,3).safe.toSeq

    safeCollection should not be empty
    safeCollection.size shouldBe 2
    safeCollection should contain (2,3)
    safeCollection should contain (3,2)
    safeCollection should not contain (3,3)
  }
}

class PieceTests extends FlatSpec with Matchers {
  "Queen" should "be in safe on position (1,1) to the Queen on position (2,3)" in {
    Queen(1,1).isInSafeFrom(Queen(2,3)) shouldBe true
  }

  it should "be created from 'Q' symbol" in {
    Piece('Q',1,1) shouldBe a [Queen]
  }

  it should "be safe to other piece on position (2,3) either (3,2)" in {
    Queen(1,1).isSafeFor(2,3) shouldBe true
    Queen(1,1).isSafeFor(3,2) shouldBe true
  }

  it should "be unsafe to any piece on position (1,2) either (2,1), (2,2), (3,3), (1,3), (3,1)" in {
    Queen(1,1).isSafeFor(1,2) should not be true
    Queen(1,1).isSafeFor(2,1) should not be true
    Queen(1,1).isSafeFor(2,2) should not be true
    Queen(1,1).isSafeFor(3,3) should not be true
    Queen(1,1).isSafeFor(1,3) should not be true
    Queen(1,1).isSafeFor(3,1) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Queen(3,3).isSafeFor(3,3) should not be true
  }

  "Rook" should "be in safe on position (1,1) to the Rook on position (2,2)" in {
    Rook(1,1).isInSafeFrom(Rook(2,2)) shouldBe true
  }

  it should "be created from 'R' symbol" in {
    Piece('R',1,1) shouldBe a [Rook]
  }

  it should "be safe to other piece on position (2,2)" in {
    Rook(1,1).isSafeFor(2,2) shouldBe true
  }

  it should "be unsafe to any piece on position (1,2) either (2,1)" in {
    Rook(1,1).isSafeFor(1,2) should not be true
    Rook(1,1).isSafeFor(2,1) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Rook(3,3).isSafeFor(3,3) should not be true
  }

  "Bishop" should "be in safe on position (1,1) to the Bishop on position (1,2) either (2,1)" in {
    Bishop(1,1).isInSafeFrom(Bishop(2,1)) shouldBe true
    Bishop(1,1).isInSafeFrom(Bishop(1,2)) shouldBe true
  }

  it should "be created from 'B' symbol" in {
    Piece('B',1,1) shouldBe a [Bishop]
  }

  it should "be safe to other piece on position (1,2) either (2,1)" in {
    Bishop(1,1).isSafeFor(1,2) shouldBe true
    Bishop(1,1).isSafeFor(2,1) shouldBe true
  }

  it should "be unsafe to any piece on position (2,2)" in {
    Bishop(1,1).isSafeFor(2,2) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Bishop(3,3).isSafeFor(3,3) should not be true
  }

  "King" should "be in safe on position (1,1) to the King on position (1,3) either (3,1), (3,3)" in {
    King(1,1).isInSafeFrom(King(1,3)) shouldBe true
    King(1,1).isInSafeFrom(King(3,1)) shouldBe true
    King(1,1).isInSafeFrom(King(3,3)) shouldBe true
  }

  it should "be created from 'K' symbol" in {
    Piece('K',1,1) shouldBe a [King]
  }

  it should "be safe to other piece on position (1,3) either (3,1), (3,3)" in {
    King(1,1).isSafeFor(1,3) shouldBe true
    King(1,1).isSafeFor(3,1) shouldBe true
    King(1,1).isSafeFor(3,3) shouldBe true
  }

  it should "be unsafe to any piece on position (1,2) either (2,1), (2,2)" in {
    King(1,1).isSafeFor(1,2) should not be true
    King(1,1).isSafeFor(2,1) should not be true
    King(1,1).isSafeFor(2,2) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    King(3,3).isSafeFor(3,3) should not be true
  }

  "Knight" should "be in safe on position (1,1) to the Knight on position (1,2) either (2,1), (2,2)" in {
    Knight(1,1).isInSafeFrom(Knight(1,2)) shouldBe true
    Knight(1,1).isInSafeFrom(Knight(2,1)) shouldBe true
    Knight(1,1).isInSafeFrom(Knight(2,2)) shouldBe true
  }

  it should "be created from 'N' symbol" in {
    Piece('N',1,1) shouldBe a [Knight]
  }

  it should "be safe to other piece on position (1,2) either (2,1), (2,2)" in {
    Knight(1,1).isSafeFor(1,2) shouldBe true
    Knight(1,1).isSafeFor(2,2) shouldBe true
    Knight(1,1).isSafeFor(2,1) shouldBe true
  }

  it should "be unsafe to any piece on position (3,2) either (2,3)" in {
    Knight(1,1).isSafeFor(3,2) should not be true
    Knight(1,1).isSafeFor(2,3) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Knight(3,3).isSafeFor(3,3) should not be true
  }

}
