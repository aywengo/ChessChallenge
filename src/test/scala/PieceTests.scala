import melnyk.co.model._
import org.scalatest.{FlatSpec, _}

class PieceTests extends FlatSpec with Matchers {
  "Queen" should "be in safe on position (1,1) to the Queen on position (2,3)" in {
    Queen(Position(1, 1)).isInSafeFrom(Queen(Position(2, 3))) shouldBe true
  }

  it should "be created from 'Q' symbol" in {
    Piece('Q', Position(1, 1)) shouldBe a[Queen]
  }

  it should "be safe to other piece on position (2,3) either (3,2)" in {
    Queen(Position(1, 1)).isSafeFor(Position(2, 3)) shouldBe true
    Queen(Position(1, 1)).isSafeFor(Position(3, 2)) shouldBe true
  }

  it should "be unsafe to any piece on position (1,2) either (2,1), (2,2), (3,3), (1,3), (3,1)" in {
    Queen(Position(1, 1)).isSafeFor(Position(1, 2)) should not be true
    Queen(Position(1, 1)).isSafeFor(Position(2, 1)) should not be true
    Queen(Position(1, 1)).isSafeFor(Position(2, 2)) should not be true
    Queen(Position(1, 1)).isSafeFor(Position(3, 3)) should not be true
    Queen(Position(1, 1)).isSafeFor(Position(1, 3)) should not be true
    Queen(Position(1, 1)).isSafeFor(Position(3, 1)) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Queen(Position(3, 3)).isSafeFor(Position(3, 3)) should not be true
  }

  "Rook" should "be in safe on position (1,1) to the Rook on position (2,2)" in {
    Rook(Position(1, 1)).isInSafeFrom(Rook(Position(2, 2))) shouldBe true
  }

  it should "be created from 'R' symbol" in {
    Piece('R', Position(1, 1)) shouldBe a[Rook]
  }

  it should "be safe to other piece on position (2,2)" in {
    Rook(Position(1, 1)).isSafeFor(Position(2, 2)) shouldBe true
  }

  it should "be unsafe to any piece on position (1,2) either (2,1)" in {
    Rook(Position(1, 1)).isSafeFor(Position(1, 2)) should not be true
    Rook(Position(1, 1)).isSafeFor(Position(2, 1)) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Rook(Position(3, 3)).isSafeFor(Position(3, 3)) should not be true
  }

  "Bishop" should "be in safe on position (1,1) to the Bishop on position (1,2) either (2,1)" in {
    Bishop(Position(1, 1)).isInSafeFrom(Bishop(Position(2, 1))) shouldBe true
    Bishop(Position(1, 1)).isInSafeFrom(Bishop(Position(1, 2))) shouldBe true
  }

  it should "be created from 'B' symbol" in {
    Piece('B', Position(1, 1)) shouldBe a[Bishop]
  }

  it should "be safe to other piece on position (1,2) either (2,1)" in {
    Bishop(Position(1, 1)).isSafeFor(Position(1, 2)) shouldBe true
    Bishop(Position(1, 1)).isSafeFor(Position(2, 1)) shouldBe true
  }

  it should "be unsafe to any piece on position (2,2)" in {
    Bishop(Position(1, 1)).isSafeFor(Position(2, 2)) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Bishop(Position(3, 3)).isSafeFor(Position(3, 3)) should not be true
  }

  "King" should "be in safe on position (1,1) to the King on position (1,3) either (3,1), (3,3)" in {
    King(Position(1, 1)).isInSafeFrom(King(Position(1, 3))) shouldBe true
    King(Position(1, 1)).isInSafeFrom(King(Position(3, 1))) shouldBe true
    King(Position(1, 1)).isInSafeFrom(King(Position(3, 3))) shouldBe true
  }

  it should "be created from 'K' symbol" in {
    Piece('K', Position(1, 1)) shouldBe a[King]
  }

  it should "be safe to other piece on position (1,3) either (3,1), (3,3)" in {
    King(Position(1, 1)).isSafeFor(Position(1, 3)) shouldBe true
    King(Position(1, 1)).isSafeFor(Position(3, 1)) shouldBe true
    King(Position(1, 1)).isSafeFor(Position(3, 3)) shouldBe true
  }

  it should "be unsafe to any piece on position (1,2) either (2,1), (2,2)" in {
    King(Position(1, 1)).isSafeFor(Position(1, 2)) should not be true
    King(Position(1, 1)).isSafeFor(Position(2, 1)) should not be true
    King(Position(1, 1)).isSafeFor(Position(2, 2)) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    King(Position(3, 3)).isSafeFor(Position(3, 3)) should not be true
  }

  "Knight" should "be in safe on position (1,1) to the Knight on position (1,2) either (2,1), (2,2)" in {
    Knight(Position(1, 1)).isInSafeFrom(Knight(Position(1, 2))) shouldBe true
    Knight(Position(1, 1)).isInSafeFrom(Knight(Position(2, 1))) shouldBe true
    Knight(Position(1, 1)).isInSafeFrom(Knight(Position(2, 2))) shouldBe true
  }

  it should "be created from 'N' symbol" in {
    Piece('N', Position(1, 1)) shouldBe a[Knight]
  }

  it should "be safe to other piece on position (1,2) either (2,1), (2,2)" in {
    Knight(Position(1, 1)).isSafeFor(Position(1, 2)) shouldBe true
    Knight(Position(1, 1)).isSafeFor(Position(2, 2)) shouldBe true
    Knight(Position(1, 1)).isSafeFor(Position(2, 1)) shouldBe true
  }

  it should "be unsafe to any piece on position (3,2) either (2,3)" in {
    Knight(Position(1, 1)).isSafeFor(Position(3, 2)) should not be true
    Knight(Position(1, 1)).isSafeFor(Position(2, 3)) should not be true
  }

  it should "should be unsafe to any other piece on the same position" in {
    Knight(Position(3, 3)).isSafeFor(Position(3, 3)) should not be true
  }

}
