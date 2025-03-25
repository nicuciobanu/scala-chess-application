package game.chess.org.board

import game.chess.org.Constants._
import game.chess.org.board.Chessboard._
import game.chess.org.piece._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ChessboardSpec extends AnyFlatSpec with Matchers {
  "The chess board" should "be initialized correctly" in {
    val board = initBoard()

    // Check that the white pieces are in the correct positions
    board(0)(0) shouldEqual Some(Rook(White))
    board(0)(1) shouldEqual Some(Knight(White))
    board(0)(2) shouldEqual Some(Bishop(White))
    board(0)(3) shouldEqual Some(Queen(White))
    board(0)(4) shouldEqual Some(King(White))
    board(0)(5) shouldEqual Some(Bishop(White))
    board(0)(6) shouldEqual Some(Knight(White))
    board(0)(7) shouldEqual Some(Rook(White))
    for (i <- 0 until 8) board(1)(i) shouldEqual Some(Pawn(White))

    // Check that the black pieces are in the correct positions
    board(7)(0) shouldEqual Some(Rook(Black))
    board(7)(1) shouldEqual Some(Knight(Black))
    board(7)(2) shouldEqual Some(Bishop(Black))
    board(7)(3) shouldEqual Some(Queen(Black))
    board(7)(4) shouldEqual Some(King(Black))
    board(7)(5) shouldEqual Some(Bishop(Black))
    board(7)(6) shouldEqual Some(Knight(Black))
    board(7)(7) shouldEqual Some(Rook(Black))
    for (i <- 0 until 8) board(6)(i) shouldEqual Some(Pawn(Black))

    // Check that the middle of the board is empty
    for {
      i <- 2 until 6
      j <- 0 until 8
    } board(i)(j) shouldEqual None
  }

  "The chess board" should "render correctly" in {

    val pawnSymbol   = PawnSymbol.toLower
    val rookSymbol   = RookSymbol.toLower
    val knightSymbol = KnightSymbol.toLower
    val bishopSymbol = BishopSymbol.toLower
    val queenSymbol  = QueenSymbol.toLower
    val kingSymbol   = KingSymbol.toLower

    val board    = initBoard()
    val rendered = renderBoard(board)

    // Check that the rendered board contains the correct pieces
    rendered should include("  a b c d e f g h")
    rendered should include(s"1 $RookSymbol $KnightSymbol $BishopSymbol $QueenSymbol $KingSymbol $BishopSymbol $KnightSymbol $RookSymbol 1")
    rendered should include(s"2 $PawnSymbol $PawnSymbol $PawnSymbol $PawnSymbol $PawnSymbol $PawnSymbol $PawnSymbol $PawnSymbol 2")
    rendered should include("6 . . . . . . . . 6")
    rendered should include("5 . . . . . . . . 5")
    rendered should include("4 . . . . . . . . 4")
    rendered should include("3 . . . . . . . . 3")
    rendered should include(s"7 $pawnSymbol $pawnSymbol $pawnSymbol $pawnSymbol $pawnSymbol $pawnSymbol $pawnSymbol $pawnSymbol 7")
    rendered should include(s"8 $rookSymbol $knightSymbol $bishopSymbol $queenSymbol $kingSymbol $bishopSymbol $knightSymbol $rookSymbol 8")
  }
}
