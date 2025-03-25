package game.chess.org.board

import game.chess.org.Board
import game.chess.org.Constants.{Black, White}
import game.chess.org.piece._

object Chessboard {
  // Initialize the board with the standard chess setup
  def initBoard(): Board = {
    val board: Board = Array.ofDim[Option[Piece]](8, 8)

    // Place the pieces for player 1 (white)
    board(0)(0) = Some(Rook(White))
    board(0)(1) = Some(Knight(White))
    board(0)(2) = Some(Bishop(White))
    board(0)(3) = Some(Queen(White))
    board(0)(4) = Some(King(White))
    board(0)(5) = Some(Bishop(White))
    board(0)(6) = Some(Knight(White))
    board(0)(7) = Some(Rook(White))
    for (i <- 0 until 8) board(1)(i) = Some(Pawn(White))

    // Place the pieces for player 2 (black)
    board(7)(0) = Some(Rook(Black))
    board(7)(1) = Some(Knight(Black))
    board(7)(2) = Some(Bishop(Black))
    board(7)(3) = Some(Queen(Black))
    board(7)(4) = Some(King(Black))
    board(7)(5) = Some(Bishop(Black))
    board(7)(6) = Some(Knight(Black))
    board(7)(7) = Some(Rook(Black))
    for (i <- 0 until 8) board(6)(i) = Some(Pawn(Black))

    // Fill the rest of the board with empty squares
    for {
      i <- 2 until 6
      j <- 0 until 8
    } board(i)(j) = None

    board
  }

  // Render the board in ASCII
  def renderBoard(board: Board): String = {
    val sb = new StringBuilder
    for (i <- 0 until 8) {
      for (j <- 0 until 8) {
        val piece = board(i)(j) match {
          case Some(p) => if (p.color == White) p.symbol.toUpper else p.symbol.toLower
          case None    => '.'
        }
        sb.append(s"$piece ")
      }
      sb.append("\n")
    }
    sb.toString()
  }
}
