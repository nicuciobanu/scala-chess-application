package game.chess.org.piece

import game.chess.org.Board
import game.chess.org.util.Constants.{PawnSymbol, White}

case class Pawn(c: Char) extends Piece { def symbol: Char = PawnSymbol }
object Pawn {

  /** The method validates the pawn's move, which can be one square forward or two squares backward if it moves for the first time on the
    * chessboard. The pawn can also move forward on the left or right diagonal if it finds an opposing piece it will capture. It receives as
    * parameters the chessboard, the pawn, the pawn's starting position, and the pawn's final position. If the conditions are met, true is
    * returned, otherwise false is returned.
    *
    * @board
    *   - chessboard with all pieces.
    * @pawn
    *   - the piece that performs the movement.
    * @from
    *   - start position from the chessboard where the piece is.
    * @to
    *   - end position from the chessboard where the piece should be moved.
    */
  def isValidPawnMove(board: Board, pawn: Pawn, from: (Int, Int), to: (Int, Int)): Boolean = {
    val (x1, y1) = from
    val (x2, y2) = to

    val direction            = if (pawn.color == White) 1 else -1
    val initialStartPosition = if (pawn.color == White) 1 else 6
    // Both pawns must be on the fifth rank at position 4 (if you're playing white)
    // or the fourth rank at position 3 (if you're playing black). In array numbering starts from 0.
    val enPassantPosition = if (pawn.color == White) 4 else 3
    // When pawn moves two squares, check if there is no piece
    val squaresAreEmpty = (board(x1 + direction)(y1), board(x2)(y2)) match {
      case (None, None) => true
      case _            => false
    }
    val isVerticalDirection = y1 == y2

    if (isVerticalDirection) {
      if (x2 == x1 + direction && board(x2)(y2).isEmpty) true
      // Moving vertically two squares
      else if (x2 == x1 + 2 * direction && x1 == initialStartPosition && squaresAreEmpty) true
      else false
    } else {
      if (Math.abs(y2 - y1) == 1 && x2 == x1 + direction && x1 == enPassantPosition && board(x2)(y2) == None)
        board(x1)(y2).exists(_.color != pawn.color)
      else if (Math.abs(y2 - y1) == 1 && x2 == x1 + direction)
        board(x2)(y2).exists(_.color != pawn.color)
      else false
    }
  }
}
