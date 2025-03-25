package game.chess.org.logic

import game.chess.org.Constants._
import game.chess.org.board.Chessboard._
import game.chess.org.logic.ChessLogic._
import game.chess.org.piece._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ChessLogicSpec extends AnyFlatSpec with Matchers {

  "A rook" should "move horizontally and vertically" in {
    val board = initBoard()

    // Remove all pieces from b1 to h1
    for (i <- 1 until 8)
      board(0)(i) = None

    // Move white rook from a1 to d1
    validateMove(board, (0, 0), (0, 3)) shouldBe true

    // Update Rook position
    board(0)(0) = None
    board(0)(3) = Some(Rook(White))

    // Move white rook from d1 to h1
    validateMove(board, (0, 3), (0, 7)) shouldBe true

    // Update Rook position
    board(0)(3) = None
    board(0)(7) = Some(Rook(White))

    // Invalid move: pawn with same color at h2 position
    validateMove(board, (0, 7), (1, 7)) shouldBe false

    // Update board - free pawn with same color
    board(1)(7) = None

    // Move white rook from h1 to h7 - at opponent pawn position
    validateMove(board, (0, 7), (6, 7)) shouldBe true
  }

  "A knight" should "move in an L-shape" in {
    val board = initBoard()

    // Move white knight from b1 to c3
    validateMove(board, (0, 1), (2, 2)) shouldBe true

    // Move white knight from b1 to a3
    validateMove(board, (0, 1), (2, 0)) shouldBe true

    // Invalid move: straight, from b1 to d2
    validateMove(board, (0, 1), (0, 3)) shouldBe false

    // Invalid move: pawn with the same color at d2 position
    validateMove(board, (0, 1), (1, 3)) shouldBe false

    // Update board - free pawn with the same color at d2 position
    board(1)(3) = None

    // Move white knight from b1 to d2
    validateMove(board, (0, 1), (1, 3)) shouldBe true

    // Update board - set opponent pawn at d2 position
    board(1)(3) = Some(Pawn(Black))

    // Move white knight from b1 to d2
    validateMove(board, (0, 1), (1, 3)) shouldBe true
  }

  "A bishop" should "move diagonally" in {
    val board = initBoard()

    // Invalid move: pawn with the same color at d2 position
    validateMove(board, (0, 2), (3, 5)) shouldBe false

    // Update board - free pawn with the same color at d2 position
    board(1)(3) = None

    // Move white bishop from c1 to f4
    validateMove(board, (0, 2), (3, 5)) shouldBe true

    // Invalid move: pawn with the same color at b2 position
    validateMove(board, (0, 2), (2, 0)) shouldBe false

    // Update board - free pawn with the same color at b2 position
    board(1)(1) = None

    // Move white bishop from c1 to a3
    validateMove(board, (0, 2), (2, 0)) shouldBe true

    // Invalid move: horizontal
    validateMove(board, (0, 2), (0, 5)) shouldBe false
  }

  "A queen" should "move horizontally, vertically, and diagonally" in {
    val board = initBoard()

    // Invalid move: pawn with the same color at d2 position
    validateMove(board, (0, 3), (3, 3)) shouldBe false

    // Update board - free pawn with the same color at d2 position
    board(1)(3) = None

    // Move white queen from d1 to d4 (vertical)
    validateMove(board, (0, 3), (3, 3)) shouldBe true

    // Invalid move: pawn with the same color at e2 position
    validateMove(board, (0, 3), (4, 7)) shouldBe false

    // Update board - free pawn with the same color at e2 position
    board(1)(4) = None

    // Move white queen from d1 to h5 (diagonal)
    validateMove(board, (0, 3), (4, 7)) shouldBe true

    // Invalid move: bishop with the same color at c1 position
    validateMove(board, (0, 3), (0, 2)) shouldBe false

    // Update board - free bishop with the same color at c1 position
    board(0)(2) = None

    // Move white queen from d1 to c1 (horizontal)
    validateMove(board, (0, 3), (0, 2)) shouldBe true

    // Invalid move: L-shape (knight move)
    validateMove(board, (0, 3), (2, 4)) shouldBe false
  }

  "A king" should "move one square in any direction" in {
    val board = initBoard()

    // Invalid move: pawn with the same color at e2 position
    validateMove(board, (0, 4), (1, 4)) shouldBe false

    // Update board - free pawn with the same color at e2 position
    board(1)(4) = None

    // Move white king from e1 to e2  (vertical)
    validateMove(board, (0, 4), (1, 4)) shouldBe true

    // Invalid move: pawn with the same color at f2 position
    validateMove(board, (0, 4), (1, 5)) shouldBe false

    // Update board - free pawn with the same color at f2 position
    board(1)(5) = None

    // Move white king from e1 to f2 (diagonal)
    validateMove(board, (0, 4), (1, 5)) shouldBe true

    // Invalid move: pawn with the same color at d1 position
    validateMove(board, (0, 4), (0, 3)) shouldBe false

    // Update board - free queen with the same color at d1 position
    board(0)(3) = None

    // Move white king from e1 to f2 (horizontal)
    validateMove(board, (0, 4), (0, 3)) shouldBe true

    // Invalid move: two squares forward
    validateMove(board, (0, 4), (2, 4)) shouldBe false
  }

  "A pawn" should "move forward one or two squares on its first move" in {
    val board = initBoard()

    // Move white pawn from e2 to e3
    validateMove(board, (1, 4), (2, 4)) shouldBe true

    // Move white pawn from e2 to e4 (first move)
    validateMove(board, (1, 4), (3, 4)) shouldBe true

    // Invalid move - move white pawn from e2 to e5 (first move)
    validateMove(board, (1, 4), (4, 4)) shouldBe false

    // Invalid move - move diagonally white pawn from e2 to d3 when no opponent piece
    validateMove(board, (1, 4), (2, 3)) shouldBe false

    // Update board - add pawn with the same color at d3 position
    board(2)(3) = Some(Pawn(White))

    // Invalid move - move diagonally white pawn from e2 to d3 when piece with same color
    validateMove(board, (1, 4), (2, 3)) shouldBe false

    // Update board - add opponent pawn at d3 position
    board(2)(3) = Some(Pawn(Black))

    // Move white pawn from e2 to d3 - capturing diagonally opponent piece
    validateMove(board, (1, 4), (2, 3)) shouldBe true

    // Update board - add opponent pawn at f3 position
    board(2)(5) = Some(Pawn(Black))

    // Move white pawn from e2 to f3 - capturing diagonally opponent piece
    validateMove(board, (1, 4), (2, 5)) shouldBe true

    // Update board - add opponent pawn at f1 position
    board(0)(5) = Some(Pawn(Black))

    // Invalid move - move backward and diagonally white pawn from e2 to f1 when opponent piece
    validateMove(board, (1, 4), (0, 5)) shouldBe false

    // Update board - add opponent pawn at d1 position
    board(0)(3) = Some(Pawn(Black))

    // Invalid move - move backward and diagonally white pawn from e2 to d1 when opponent piece
    validateMove(board, (1, 4), (0, 3)) shouldBe false

    // Update board - add white queen at d1 position
    board(0)(3) = Some(Queen(White))

    // Invalid move - move backward and diagonally white pawn from e2 to d1 when piece with same color
    validateMove(board, (1, 4), (0, 3)) shouldBe false

    // Invalid move: backward
    validateMove(board, (1, 4), (0, 4)) shouldBe false
  }

  "A move" should "be invalid if the starting square is empty" in {
    val board = initBoard()

    // Attempt to move from an empty square
    validateMove(board, (3, 3), (4, 4)) shouldBe false
  }

  "A rook" should "capture opponent pieces" in {
    val board = initBoard()

    // Update board - add opponent queen at b1 position
    board(0)(1) = Some(Queen(Black))

    // Move white rook from a1 to b1 and capture opponent queen (horizontally)
    movePiece(board, (0, 0), (0, 1))

    board(0)(0) shouldBe None
    board(0)(1) shouldBe Some(Rook(White))

    // Update board - add opponent pawn at b2 position
    board(1)(1) = Some(Pawn(Black))

    // Move white rook from b1 to b2 and capture opponent queen (vertically)
    movePiece(board, (0, 1), (1, 1))

    board(0)(1) shouldBe None
    board(1)(1) shouldBe Some(Rook(White))

    // Move white rook from b1 to b7 and capture opponent queen (vertically)
    movePiece(board, (1, 1), (6, 1))

    board(1)(1) shouldBe None
    board(6)(1) shouldBe Some(Rook(White))

    // Do nothing - no piece at b2 position
    movePiece(board, (1, 1), (2, 1))

    board(1)(1) shouldBe None
    board(2)(1) shouldBe None
  }

  "A knight" should "capture opponent pieces" in {
    val board = initBoard()

    // Update board - add opponent pawn at c2 position
    board(2)(2) = Some(Pawn(Black))

    // Move white knight from b1 to c3 and capture opponent pawn
    movePiece(board, (0, 1), (2, 2))

    board(0)(1) shouldBe None
    board(2)(2) shouldBe Some(Knight(White))

    // Update board - add opponent bishop at a2 position
    board(1)(0) = Some(Bishop(Black))

    // Move white knight from c3 to a2 and capture opponent bishop
    movePiece(board, (2, 2), (1, 0))

    board(2)(2) shouldBe None
    board(1)(0) shouldBe Some(Knight(White))

    // Do nothing - no piece at c3 position
    movePiece(board, (2, 2), (3, 5))

    board(2)(2) shouldBe None
    board(3)(5) shouldBe None
  }

  "A bishop" should "capture opponent pieces" in {
    val board = initBoard()

    // Update board - add opponent pawn at a3, and free pawn from b2
    board(1)(1) = None
    board(2)(0) = Some(Pawn(Black))

    // Move white bishop from c1 to a3 and capture opponent pawn
    movePiece(board, (0, 2), (2, 0))

    board(0)(2) shouldBe None
    board(2)(0) shouldBe Some(Bishop(White))

    // Move white bishop from a3 to e7 and capture opponent pawn
    movePiece(board, (2, 0), (6, 4))

    board(2)(0) shouldBe None
    board(6)(4) shouldBe Some(Bishop(White))

    // Update board - add opponent knight at g5
    board(4)(6) = Some(Knight(Black))

    // Move white bishop from e7 to g5 and capture opponent pawn
    movePiece(board, (6, 4), (4, 6))

    board(6)(4) shouldBe None
    board(4)(6) shouldBe Some(Bishop(White))

    // Do nothing - no piece at a3 position
    movePiece(board, (2, 0), (1, 1))

    board(2)(0) shouldBe None
    board(1)(1) shouldBe None
  }

  "A queen" should "capture opponent pieces" in {
    val board = initBoard()

    // Update board - add black pawn to d6, and free pawn from d2
    board(1)(3) = None
    board(5)(3) = Some(Pawn(Black))

    // Move white queen from d1 to d6 and capture opponent pawn
    movePiece(board, (0, 3), (5, 3))

    board(0)(3) shouldBe None
    board(5)(3) shouldBe Some(Queen(White))

    // Move white queen from d6 to e7 and capture opponent pawn
    movePiece(board, (5, 3), (6, 4))

    board(5)(3) shouldBe None
    board(6)(4) shouldBe Some(Queen(White))

    // Move white queen from e7 to f7 and capture opponent pawn
    movePiece(board, (6, 4), (6, 5))

    board(6)(4) shouldBe None
    board(6)(5) shouldBe Some(Queen(White))

    // Update board - add black knight to f4
    board(3)(5) = Some(Knight(Black))

    // Move white queen from f7 to f4 and capture opponent knight
    movePiece(board, (6, 5), (3, 5))

    board(6)(5) shouldBe None
    board(3)(5) shouldBe Some(Queen(White))

    // Update board - add black bishop to e4
    board(3)(4) = Some(Bishop(Black))

    // Move white queen from f4 to e4 and capture opponent bishop
    movePiece(board, (3, 5), (3, 4))

    board(3)(5) shouldBe None
    board(3)(4) shouldBe Some(Queen(White))

    // Do nothing - no piece at f4 position
    movePiece(board, (3, 5), (3, 6))

    board(3)(5) shouldBe None
    board(3)(6) shouldBe None
  }

  "A king" should "capture opponent pieces" in {
    val board = initBoard()

    // Update board - add black pawn to e2
    board(1)(4) = Some(Pawn(Black))

    // Move white king from e1 to e2 and capture opponent pawn
    movePiece(board, (0, 4), (1, 4))

    board(0)(4) shouldBe None
    board(1)(4) shouldBe Some(King(White))

    // Update board - add black pawn to f2
    board(1)(5) = Some(Pawn(Black))

    // Move white king from e2 to f2 and capture opponent pawn
    movePiece(board, (1, 4), (1, 5))

    board(1)(4) shouldBe None
    board(1)(5) shouldBe Some(King(White))

    // Update board - add black pawn to e3
    board(2)(4) = Some(Pawn(Black))

    // Move white king from f2 to e3 and capture opponent pawn
    movePiece(board, (1, 5), (2, 4))

    board(1)(5) shouldBe None
    board(2)(4) shouldBe Some(King(White))

    // Do nothing - no piece at f4 position
    movePiece(board, (1, 5), (2, 6))

    board(1)(5) shouldBe None
    board(2)(6) shouldBe None
  }

  "A pawn" should "capture opponent pieces" in {
    val board = initBoard()

    // Update board - add black pawn to f3
    board(2)(5) = Some(Pawn(Black))

    // Move white pawn from g2 to f3 and capture opponent pawn
    movePiece(board, (1, 6), (2, 5))

    board(1)(6) shouldBe None
    board(2)(5) shouldBe Some(Pawn(White))

    // Update board - add black pawn to g4
    board(3)(6) = Some(Pawn(Black))

    // Move white pawn from f3 to g4 and capture opponent pawn
    movePiece(board, (2, 5), (3, 6))

    board(2)(5) shouldBe None
    board(3)(6) shouldBe Some(Pawn(White))

    // Do nothing - no piece at f3 position
    movePiece(board, (2, 5), (3, 4))

    board(2)(5) shouldBe None
    board(3)(4) shouldBe None
  }

  "ChessLogic" should "find piece on chessboard" in {
    val board = initBoard()

    // Move black king from e8 to g7
    movePiece(board, (7, 4), (6, 6))

    // Find black king at g7 position
    findPiece(board, King(Black)) shouldBe Some((6, 6))

    // Move black king from g7 to a1
    movePiece(board, (6, 6), (0, 0))

    // Find black king at a1 position
    findPiece(board, King(Black)) shouldBe Some((0, 0))

    // Move black king from a1 to h1
    movePiece(board, (0, 0), (0, 7))

    // Find black king at h1 position
    findPiece(board, King(Black)) shouldBe Some((0, 7))

    // Move black king from h1 to a8
    movePiece(board, (0, 7), (7, 0))

    // Find black king at a8 position
    findPiece(board, King(Black)) shouldBe Some((7, 0))

    // Move black king from a8 to h8
    movePiece(board, (7, 0), (7, 7))

    // Find black king at h8 position
    findPiece(board, King(Black)) shouldBe Some((7, 7))

    // Move black king from a8 to e5
    movePiece(board, (7, 7), (4, 4))

    // Find black king at h8 position
    findPiece(board, King(Black)) shouldBe Some((4, 4))

    // Move black king from e5 to b4
    movePiece(board, (4, 4), (3, 1))

    // Find black king at b4 position
    findPiece(board, King(Black)) shouldBe Some((3, 1))

    // Remove black king from chessboard
    board(3)(1) = None

    // Not find black king on chessboard
    findPiece(board, King(Black)) shouldBe None
  }

  "ChessLogic" should "check if a piece is under attack" in {
    val board = initBoard()

    // Move black pawn from e2 to d3
    movePiece(board, (1, 4), (2, 3))

    // Move black queen from d8 to e6
    movePiece(board, (7, 3), (5, 4))

    // Find white king at e1 position
    val whiteKingSquare = findPiece(board, King(White)).get

    // Check if white king is under opponent queen attack
    isPieceUnderAttack(board, whiteKingSquare, Black) shouldBe true

    // Move black rook from a8 to e6
    movePiece(board, (7, 0), (5, 4))

    // Check if white king is under opponent rook attack
    isPieceUnderAttack(board, whiteKingSquare, Black) shouldBe true

    // Move black bishop from f8 to h4
    movePiece(board, (7, 5), (3, 7))

    // Check if white king is under opponent bishop attack
    isPieceUnderAttack(board, whiteKingSquare, Black) shouldBe true

    // Move black knight from g8 to d3
    movePiece(board, (7, 6), (2, 3))
    board(1)(5) = Some(Pawn(White))

    // Check if white king is under opponent knight attack
    isPieceUnderAttack(board, whiteKingSquare, Black) shouldBe true

    // Remove knight from d3 position, queen from e6
    board(2)(3) = None
    board(5)(4) = None

    // White king is not under opponent bishop attack because of white pawn at the f2 position
    isPieceUnderAttack(board, whiteKingSquare, Black) shouldBe false
  }

  "ChessLogic" should "identify a `check`" in {
    val board = initBoard()

    // Move white pawn from e2 to e4
    movePiece(board, (1, 4), (3, 4))

    // Move black pawn from e7 to e5
    movePiece(board, (0, 5), (4, 4))

    // Move white bishop from f1 to c4
    movePiece(board, (0, 5), (3, 2))

    // Move black knight from b8 to c6
    movePiece(board, (7, 1), (5, 2))

    // Move white queen from d1 to f3
    movePiece(board, (0, 3), (2, 5))

    // Move from black pawn from d7 to d6
    movePiece(board, (6, 3), (5, 3))

    // Move white queen from f3 to f7 - check
    movePiece(board, (2, 5), (6, 5))

    // Find black king at
    val blackKingSquare = findPiece(board, King(Black)).get

    // Check if black king is under opponent queen attack
    isPieceUnderAttack(board, blackKingSquare, White) shouldBe true
  }
}
