package game.chess.org.board

import game.chess.org.Board
import game.chess.org.util.Constants.{Black, White}
import game.chess.org.piece._

object Chessboard {

  /** The method initializes the 8x8 matrix with the pieces in their initial position.
    */
  // Initialize the board with the standard chess setup
  def initBoard(): Board = {
    val board = Array.ofDim[Option[Piece]](8, 8)

    // Place white pieces (uppercase) at the bottom (rows 0-1)
    board(0)(0) = Some(Rook(White))   // a1
    board(0)(1) = Some(Knight(White)) // b1
    board(0)(2) = Some(Bishop(White)) // c1
    board(0)(3) = Some(Queen(White))  // d1
    board(0)(4) = Some(King(White))   // e1
    board(0)(5) = Some(Bishop(White)) // f1
    board(0)(6) = Some(Knight(White)) // g1
    board(0)(7) = Some(Rook(White))   // h1
    for (i <- 0 until 8) board(1)(i) = Some(Pawn(White)) // a2-h2

    // Place black pieces (lowercase) at the top (rows 6-7)
    board(7)(0) = Some(Rook(Black))   // a8
    board(7)(1) = Some(Knight(Black)) // b8
    board(7)(2) = Some(Bishop(Black)) // c8
    board(7)(3) = Some(Queen(Black))  // d8
    board(7)(4) = Some(King(Black))   // e8
    board(7)(5) = Some(Bishop(Black)) // f8
    board(7)(6) = Some(Knight(Black)) // g8
    board(7)(7) = Some(Rook(Black))   // h8
    for (i <- 0 until 8) board(6)(i) = Some(Pawn(Black)) // a7-h7

    // Fill the rest of the board with empty squares
    for {
      i <- 2 until 6
      j <- 0 until 8
    } board(i)(j) = None

    board
  }

  /** The method draws a chessboard in the console with the position of the chess pieces on it.
    *
    * @board
    *   - double dimensional array with 8 rows and 8 columns representing the chessboard.
    */
  def renderBoard(board: Board): String = {
    val sb = new StringBuilder

    // Add column letters (a-h) at the top
    sb.append("  ")
    for (i <- 0 until 8) sb.append((i + 'a').toChar + " ")
    sb.append("\n")

    // Render each row with row numbers (8-1) on the left
    for (i <- 0 until 8) {
      sb.append(s"${8 - i} ") // Row number (1-8)
      for (j <- 0 until 8) {
        val piece = board(i)(j) match {
          case Some(p) => if (p.color == 'W') p.symbol.toLower else p.symbol.toUpper
          case None    => '.'
        }
        sb.append(s"$piece ")
      }
      sb.append(s"${8 - i}\n") // Row number (1-8) on the right
    }

    // Add column letters (a-h) at the bottom
    sb.append("  ")
    for (j <- 0 until 8) sb.append((j + 'a').toChar + " ")
    sb.append("\n")

    sb.toString()
  }
}
