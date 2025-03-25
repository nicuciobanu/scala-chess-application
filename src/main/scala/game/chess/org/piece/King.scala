package game.chess.org.piece

import game.chess.org.Constants.KingSymbol

case class King(c: Char) extends Piece { def symbol: Char = KingSymbol }

object King {

  /** The method validates whether the king's move is correct. The king's distance can be a maximum of one square on the forward-backward,
    * left-right, diagonal-forward, diagonal-backward trajectories, which can also be left or right. It receives two parameters, the
    * starting position and the final position where the piece should be moved. As a response it will return true if the move position is
    * correct or false otherwise.
    *
    * @from
    *   - start position from the chessboard where the piece is.
    * @to
    *   - end position from the chessboard where the piece should be moved.
    */
  def isValidKingMove(from: (Int, Int), to: (Int, Int)): Boolean = {
    val (x1, y1) = from
    val (x2, y2) = to

    // King moves one square in any direction
    Math.abs(x2 - x1) <= 1 && Math.abs(y2 - y1) <= 1
  }
}
