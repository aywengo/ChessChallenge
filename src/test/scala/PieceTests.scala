import melnyk.co.model._
import org.scalatest.{FlatSpec, _}

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
