package game.chess.org.piece

import game.chess.org.util.Constants.KnightSymbol

case class Knight(c: Char) extends Piece { def symbol: Char = KnightSymbol }

object Knight {

  /** The method validates the knight's movement trajectory, which can be in L-shape. It receives the start position and the end position as
    * parameters, returning whether the movement condition correct as true or, if not, false.
    *
    * @from
    *   - start position from the chessboard where the piece is.
    * @to
    *   - end position from the chessboard where the piece should be moved.
    */
  def isValidKnightMove(from: (Int, Int), to: (Int, Int)): Boolean = {
    val (x1, y1) = from
    val (x2, y2) = to

    // Knight moves in an L-shape
    val dx = Math.abs(x2 - x1)
    val dy = Math.abs(y2 - y1)

    (dx == 2 && dy == 1) || (dx == 1 && dy == 2)
  }
}
